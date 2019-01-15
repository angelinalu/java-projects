import java.util.ArrayList;

public class CacheSet {
	int numLines = 4;
	
	CacheLine[] lines = new CacheLine[numLines];
	ArrayList<Integer> lru = new ArrayList<>(); 
  
	public CacheSet(int numLines) {
		this.numLines = numLines;
		for(int i = 0; i < numLines; i++) {
			lru.add(i);
			lines[i] = new CacheLine();
		}
	}
	
//	public int update(int) {
//		lru.get();
//	}
}
