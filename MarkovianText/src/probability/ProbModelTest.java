package probability;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

public class ProbModelTest {
	


	@Test
	public void testcreateWordMapKGeneral() {
		
		ProbModel testProbModel = new ProbModel();
		ArrayList<String> testK = new ArrayList<String>();
		testK.add("I");
		testK.add("am");
		testK.add("Sean");
		testK.add("and");
		testK.add("I");
		testK.add("am");
		testK.add("happy");
		
		///Test for when the K is One 
		testProbModel.createWordMapKGeneral(testK, 1);
		//System.out.println(testProbModel.wordMapKGeneral);
		
		
		
		HashMap<List<String>, HashMap<String, Integer>> testHashmap = new HashMap<List<String>, HashMap<String, Integer>>();
		List<String> words = new ArrayList<String>();
		words.add("I");
		HashMap <String, Integer> testLeaderboardOne = new HashMap<String, Integer>();
		testLeaderboardOne.put("am", 2);
		testHashmap.put(words, testLeaderboardOne);
		
		List<String> word2 = new ArrayList<String>();
		word2.add("am");
		HashMap <String, Integer> testLeaderBoardTwo = new HashMap<String, Integer>();
		testLeaderBoardTwo.put("happy", 1);
		testLeaderBoardTwo.put("Sean", 1);
		testHashmap.put(word2, testLeaderBoardTwo);
		
		List<String> word3 = new ArrayList<String>();
		word3.add("Sean");
		HashMap <String, Integer> testLeaderBoardThree = new HashMap<String, Integer>();
		testLeaderBoardThree.put("and", 1);
		testHashmap.put(word3, testLeaderBoardThree);
		
		List<String> word4 = new ArrayList<String>();
		word4.add("and");
		HashMap <String, Integer> testLeaderBoardFour = new HashMap<String, Integer>();
		testLeaderBoardFour.put("I", 1);
		testHashmap.put(word4, testLeaderBoardFour);
		
	//	List<String> word5 = new ArrayList<String>();
	//	word5.add("happy");
	//	HashMap <String, Integer> testLeaderBoardFive = new HashMap<String, Integer>();
	//	testLeaderBoardFive.put("I", 1);
	//	testHashmap.put(word5, testLeaderBoardFive);
		
		assertEquals(testHashmap, testProbModel.wordMapKGeneral);
		
		//Test For When the K Is Two 
		testProbModel.createWordMapKGeneral(testK, 2);
		HashMap<List<String>, HashMap<String, Integer>> testHashmapK2 = new HashMap<List<String>, HashMap<String, Integer>>();

		List<String> wordOneKTwo = new ArrayList<String>();
		wordOneKTwo.add("I");
		wordOneKTwo.add("am");
		HashMap <String, Integer> testLeaderboardOneKTwo = new HashMap<String, Integer>();
		testLeaderboardOneKTwo.put("happy", 1);
		testLeaderboardOneKTwo.put("Sean", 1);		
		testHashmapK2.put(wordOneKTwo, testLeaderboardOneKTwo);
		
		
		List<String> wordTwoKTwo = new ArrayList<String>();
		wordTwoKTwo.add("am");
		wordTwoKTwo.add("Sean");
		HashMap <String, Integer> testLeaderboardTwoKTwo = new HashMap<String, Integer>();
		testLeaderboardTwoKTwo.put("and", 1);		
		testHashmapK2.put(wordTwoKTwo, testLeaderboardTwoKTwo);
				

		List<String> wordThreeKTwo = new ArrayList<String>();
		wordThreeKTwo.add("Sean");
		wordThreeKTwo.add("and");
		HashMap <String, Integer> testLeaderboardThreeKTwo = new HashMap<String, Integer>();
		testLeaderboardThreeKTwo.put("I", 1);		
		testHashmapK2.put(wordThreeKTwo, testLeaderboardThreeKTwo);
		
		List<String> wordFourKTwo = new ArrayList<String>();
		wordFourKTwo.add("and");
		wordFourKTwo.add("I");
		HashMap <String, Integer> testLeaderboardFourKTwo = new HashMap<String, Integer>();
		testLeaderboardFourKTwo.put("am", 1);		
		testHashmapK2.put(wordFourKTwo, testLeaderboardFourKTwo);
		
	//	List<String> wordFiveKTwo = new ArrayList<String>();
	//	wordFiveKTwo.add("am");
	//	wordFiveKTwo.add("happy");
	//	HashMap <String, Integer> testLeaderboardFiveKTwo = new HashMap<String, Integer>();
	//	testLeaderboardFiveKTwo.put("I", 1);		
	//	testHashmapK2.put(wordFiveKTwo, testLeaderboardFiveKTwo);
		
		assertEquals(testHashmapK2, testProbModel.wordMapKGeneral);
		
	//	Test For When K is Five
		testProbModel.createWordMapKGeneral(testK, 5);
		HashMap<List<String>, HashMap<String, Integer>> testHashmapK5 = new HashMap<List<String>, HashMap<String, Integer>>();
		
		List<String> wordsOneKFive = new ArrayList<String>();
		wordsOneKFive.add("I");
		wordsOneKFive.add("am");
		wordsOneKFive.add("Sean");
		wordsOneKFive.add("and");
		wordsOneKFive.add("I");
		HashMap <String, Integer> testLeaderboardOneKFive = new HashMap<String, Integer>();
		testLeaderboardOneKFive.put("am", 1);
		testHashmapK5.put(wordsOneKFive, testLeaderboardOneKFive);
		
		
		List<String> wordsTwoKFive = new ArrayList<String>();
		wordsTwoKFive.add("am");
		wordsTwoKFive.add("Sean");
		wordsTwoKFive.add("and");
		wordsTwoKFive.add("I");
		wordsTwoKFive.add("am");
		HashMap <String, Integer> testLeaderboardTwoKFive = new HashMap<String, Integer>();
		testLeaderboardTwoKFive.put("happy", 1);
		testHashmapK5.put(wordsTwoKFive, testLeaderboardTwoKFive);
		
		
		assertEquals(testHashmapK5, testProbModel.wordMapKGeneral);
	
	
	
	}
	
	@Test
	public void testwordReturnerK() {
		ProbModel testProbModel = new ProbModel();
		ArrayList<String> testK = new ArrayList<String>();
		testK.add("I");
		testK.add("am");
		testK.add("Sean");
		testK.add("and");
		testK.add("I");
		testK.add("am");
		testK.add("happy");
		
		testProbModel.createWordMapKGeneral(testK, 1);
		System.out.println(testProbModel.wordMapKGeneral);
		
		ArrayList<String> testListOne = new ArrayList<String>();
		testListOne.add("I");
		String returnedWord = testProbModel.wordReturnerK(testListOne);
		assertEquals("am", returnedWord);
		
		ArrayList<String> testListTwo = new ArrayList<String>();
		testListTwo.add("am");
		String returnedWordTwo = testProbModel.wordReturnerK(testListTwo);
		assertEquals("happy", returnedWordTwo);
		
		ArrayList<String> testListThree = new ArrayList<String>();
		testListThree.add("Sean");
		String returnedWordThree = testProbModel.wordReturnerK(testListThree);
		assertEquals("and", returnedWordThree);
		
		ArrayList<String> testListFour = new ArrayList<String>();
		testListFour.add("and");
		String returnedWordFour = testProbModel.wordReturnerK(testListFour);
		assertEquals("I", returnedWordFour);
		
		
		
	}
		
}