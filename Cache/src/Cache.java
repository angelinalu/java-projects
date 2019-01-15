//Angelina Lu
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Cache {

	public static void main(String[] args) throws FileNotFoundException { 
		//if no inputs
		String inputFile = "s1.tr";
		int setBits = 1;
		int numLinesE = 1;
		int blockBits = 1;
		//figure out inputs from command line args
		if (args.length > 0) {
			setBits = Integer.parseInt(args[0]);
			numLinesE = Integer.parseInt(args[1]);
			blockBits = Integer.parseInt(args[2]);
			inputFile = args[3];
		}
		int numSetsS = (int) Math.pow(2, setBits);
		int numBlocksB = (int) Math.pow(2, blockBits);

		int misses = 0;
		int hits = 0;
		int evictions = 0;

		//create CacheSet
		CacheSet[] sets = new CacheSet[numSetsS];
		for(int i = 0; i < numSetsS; i++) {
			sets[i] = new CacheSet(numLinesE);
		}
		//reading trace from file line by line
		File name = new File(inputFile);
		Scanner reader = new Scanner(name);
		Index index = null;
		while (reader.hasNext()) {
			String line = reader.nextLine();
			//ignore lines with I's
			if (line.indexOf("I") == -1) {
				index = new Index(line, numBlocksB, blockBits, numSetsS, setBits);
				CacheSet currentSet = sets[index.setIndex];
				//figure out if theres a line in set with same tag index
				int iFlag = 0;
				for(int i = 0; i < numLinesE; i	++) {
					
					//arraylist lru keeps track of least recent by storing it in lru.get(0),
					CacheLine currentLine = currentSet.lines[currentSet.lru.get(i)];
					if (index.tagIndex == currentLine.tag) {
						iFlag = i;
					}
				}
				int lineNumber = currentSet.lru.get(iFlag);
				//when something gets used it gets removed and put back on the end
				currentSet.lru.remove(iFlag);
				currentSet.lru.add(lineNumber);
				CacheLine currentLine = currentSet.lines[lineNumber];
				
				String message = currentLine.update(index.tagIndex);
				
				if (message.equals("miss")) {
					misses++;
				}else if (message.equals("hit")) {
					hits++;
				}else {
					evictions++;
				}
				System.out.println(line + " " + message );
			}
		}
		reader.close();
		misses = misses + evictions;
		System.out.println("hits: " + hits + " misses: " + misses + " evictions: " + evictions);
	}

}
