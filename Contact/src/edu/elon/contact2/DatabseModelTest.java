package edu.elon.contact2;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DatabseModelTest {
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
		jdbc.add("test_first", "test_middle",
				"test_last", "test@email.com", "test_major");
		String sql = "SELECT * FROM Contact;";
		ResultSet rset = jdbc.stmt.executeQuery(sql);
		rset.next();
		String name = rset.getString("first_name");
		Assertions.assertEquals("test_first", name, "wrong entry");
	}

	@Test
	void testRemoveEntry() throws SQLException {
		jdbc.add("rm_first", "rm_middle", "rm_last", "email@email", "major");
		jdbc.remove();
		String sql = "SELECT * FROM Contact WHERE first_name = \"rm_first\"";
		ResultSet rset = jdbc.stmt.executeQuery(sql);
		boolean empty = rset.next();
		Assertions.assertEquals(empty, false, "entry wasnt removed");
	}

	@Test
	void testClear() throws SQLException {
		jdbc.add("test2", "test1", "test1", "test1@email.com", "cs");

		jdbc.clear();
		String sql = "SELECT * FROM Contact;";
		ResultSet rset = jdbc.stmt.executeQuery(sql);
		boolean empty = rset.next();
		Assertions.assertEquals(empty, false, "database not cleared");

	}

	@Test
	void testPrevious() throws SQLException {
		jdbc.add("test1", "test1", "test1", "test1@email.com", "cs");
		jdbc.add("test2", "test2", "test2", "test2@email.com", "cs");
		jdbc.next();
		jdbc.previous();
		String[] expected = new String[10];
		expected[0] = "First Name";
		expected[1] = "Middle Name";
		expected[2] = "Last Name";
		expected[3] = "Email";
		expected[4] = "Major";
		expected[5] = "test1";
		expected[6] = "test1";
		expected[7] = "test1";
		expected[8] = "test1@email.com";
		expected[9] = "cs";
		String[] actual = jdbc.getValues();
		Assertions.assertArrayEquals(expected, actual, "previous not working");
	}
	@Test
	void testNext() throws SQLException {
		jdbc.add("test1", "test1", "test1", "test1@email.com", "cs");
		jdbc.add("test2", "test2", "test2", "test2@email.com", "cs");
		jdbc.next();
		String[] expected = new String[10];
		expected[0] = "First Name";
		expected[1] = "Middle Name";
		expected[2] = "Last Name";
		expected[3] = "Email";
		expected[4] = "Major";
		expected[5] = "test2";
		expected[6] = "test2";
		expected[7] = "test2";
		expected[8] = "test2@email.com";
		expected[9] = "cs";
		String[] actual = jdbc.getValues();
		Assertions.assertArrayEquals(expected, actual, "next not working");
	}
	@Test
	void testUpdate() throws SQLException {
		jdbc.add("test1", "test1", "test1", "test1@email.com", "cs");
		jdbc.update("testUpdate", "testUpdate", "testUpdate", "testUpdate@email", "cs");
		String[] expected = new String[10];
		expected[0] = "First Name";
		expected[1] = "Middle Name";
		expected[2] = "Last Name";
		expected[3] = "Email";
		expected[4] = "Major";
		expected[5] = "testUpdate";
		expected[6] = "testUpdate";
		expected[7] = "testUpdate";
		expected[8] = "testUpdate@email.com";
		expected[9] = "cs";
	}
}