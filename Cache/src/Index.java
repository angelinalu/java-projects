
//to figure out index given address
public class Index {
	int blockIndex = 0;
	int setIndex = 0;
	int tagIndex = 0;
	String operation = "";

	public Index(String input, int numBlocksB, int blockBits, int numSetsS, int setBits) {
		String[] line = input.split(" |,");

		operation = line[1];
		long address = Long.parseLong(line[2], 16);

		blockIndex = (int) address & (numBlocksB-1);

		address = address >> blockBits;
		setIndex = (int) (address & (numSetsS-1));

		tagIndex = (int) (address >> setBits);

	}
}