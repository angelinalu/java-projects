package probability;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

public class ProbModel{
//	public HashMap<String, HashMap<String, Integer>> wordMap;
//	public HashMap<List<String>, HashMap<String, Integer>> wordMapK;
	public HashMap<List<String>, HashMap<String, Integer>> wordMapKGeneral;
	
	public void createWordMapKGeneral(ArrayList<String> data, int kval){
		wordMapKGeneral = new HashMap<List<String>, HashMap<String, Integer>>();
		for(int i = 0; i < data.size()-kval; i++){
			/**
			 * add k words together to become key
			 */
			List<String> words = new ArrayList<String>();
			for (int j = i; j < i+kval; j++){
				words.add(data.get(j));
			}

			String nextWord = data.get(i+kval);
			if(wordMapKGeneral.containsKey(words)){
				/**find key for data.get(i+kval) in the key's leaderboard, update frequency 
				 * by either increasing by 1  
				 */
				HashMap <String, Integer> leaderboard = wordMapKGeneral.get(words);
				if (leaderboard.containsKey(nextWord)){
					int frequency = leaderboard.get(nextWord);
					leaderboard.put(nextWord, frequency+1);
				}
				/**or creating new key value pair in the key's leaderboard
				 */
				else{
					leaderboard.put(nextWord, 1);
				}
			}
			/**
			 * create new leaderboard if words arent in the wordmap
			 */
			else{
				HashMap<String, Integer> leaderboard = new HashMap<String, Integer>();
				leaderboard.put(nextWord, 1);
				wordMapKGeneral.put(words, leaderboard);
			}
		}
//		System.out.println(wordMapKGeneral);
	}	

	public String wordReturnerK(List<String> starter){
		/**pick highest scoring word*/
		HashMap<String, Integer> leaderboard = wordMapKGeneral.get(starter);
		String highscoreKey = "";
		int highscoreValue = 0;
//		System.out.println(leaderboard);
		Set<Entry<String, Integer>> nextWordSet = leaderboard.entrySet();
		/**
		 * pick the highest scoring word by going through the entries and comparing the
		 * score of current entry to highscore
		 */
		for(Entry<String, Integer> entry : nextWordSet){
			if (entry.getValue() > highscoreValue){
				highscoreKey = entry.getKey();
				highscoreValue = entry.getValue();
			}
		}
		return highscoreKey;
		/**weighted random*/
//		int completeWeight = 0;
//		for(Entry<String, Integer> entry : nextWordSet){
//			completeWeight += entry.getValue();
//		}
//		Random random = new Random();
//		int choose = random.nextInt(completeWeight);
//		int countWeight = 0;
//		for(Entry<String, Integer> entry : nextWordSet){
//			countWeight += entry.getValue();
//			if(countWeight > choose){
//				return entry.getKey();
//			}
//		}
//		//fallback case, never return this
//		return "!!!ERROR!!!";
	}

}
