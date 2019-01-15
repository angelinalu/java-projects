package edu.elon.contact;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MysqlAccessTest {
        DatabaseModel jdbc = new DatabaseModel();

        @BeforeEach
        void setUp() throws Exception {
                jdbc.connect("root", "mysqluser", "127.0.0.1", "guestbook","Contact");
        }

        @AfterEach
        void tearDown() throws Exception {
                jdbc.clear();
        }

        @Test
        void testConnect() {
                String table = jdbc.table;
                Assertions.assertEquals("Contact", table, "wrong table name");
        }

        @Test
        void testAddEntry() throws SQLException {
                int pk = jdbc.addEntry("test_first", "test_middle",
                                "test_last", "test@email.com", "test_major");
                System.out.println("pk is "+ pk);
                String sql = "SELECT * FROM Contact WHERE pk = " + pk + ";";
                ResultSet rset = jdbc.stmt.executeQuery(sql);
                rset.next();
                String name = rset.getString("first_name");
                Assertions.assertEquals("test_first", name, "wrong entry");
        }

        @Test
        void testRemoveEntry() throws SQLException {
                int pk = jdbc.addEntry("rm_first", "rm_middle", "rm_last", "email@email", "major");
                jdbc.removeEntry(Integer.toString(pk));
                String sql = "SELECT * FROM Contact WHERE first_name = \"rm_first\"";
                System.out.println("rm pk is " + pk);
                ResultSet rset = jdbc.stmt.executeQuery(sql);
                boolean empty = rset.next();
                Assertions.assertEquals(empty, false, "entry wasnt removed");
        }

        @Test
        void testClear() throws SQLException {
                jdbc.clear();

                Assertions.assertThrows(SQLException.class, () -> jdbc.scrollTable("1"));
        }

        @Test
        void testScrollTable() throws SQLException {
                int pk = jdbc.addEntry("test1", "test1", "test1", "test1@email.com", "cs");
                String[] actual = jdbc.scrollTable(Integer.toString(pk));
                String[] expected = new String[5];
                expected[0] = "test1";
                expected[1] = "test1";
                expected[2] = "test1";
                expected[3] = "test1@email.com";
                expected[4] = "cs";
                Assertions.assertArrayEquals(expected, actual, "scrollTable not working");
        }
        @Test
        void testUpdate() {

        }
}