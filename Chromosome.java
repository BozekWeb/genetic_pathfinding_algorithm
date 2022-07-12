
public class Chromosome {
	private int dec;
	private String bin;	
	
	public Chromosome(int dec, String bin) {
		this.dec = dec;
		this.bin = bin;
	}

	public int getDec() {
		return dec;
	}

	public void setDec(int dec) {
		this.dec = dec;
	}

	public String getBin() {
		return bin;
	}

	public void setBin(String bin) {
		this.bin = bin;
	}
}
