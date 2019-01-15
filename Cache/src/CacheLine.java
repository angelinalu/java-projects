public class CacheLine {
	boolean valid;
	int tag;

	public CacheLine() {
		this.valid = false;
		this.tag = 0;
	}

	public String update(int currentTag) {
		if (!valid) {
			this.valid = true;
			tag = currentTag;
			return "miss";
			
		}
		else if (currentTag == tag){
			return "hit";
		}
		else{
			tag = currentTag;
			return "miss eviction";
		}
	}
}
