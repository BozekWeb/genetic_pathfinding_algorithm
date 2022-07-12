import java.util.ArrayList;
import java.util.Random;

public class Population {
	private final int POP_SIZE = 20;
	private final int MAX_VALUE = 128; //(x - 1 : liczy od zera)
	private final int BIN_MAX_SIZE = 8; 
	
	private ArrayList<Chromosome> population = new ArrayList<>();
	private Random rand = new Random();
	

	
	public Population() {
		int dec;
		String bin;
		for(int i = 0; i < POP_SIZE; i++) {
			dec = rand.nextInt(MAX_VALUE);
			bin = Integer.toBinaryString(dec);
			while(bin.length() < BIN_MAX_SIZE) {
				bin = "0" + bin;
			}
			population.add(new Chromosome(dec, bin));
		}
	}

	public ArrayList<Chromosome> getPopulation() {
		return population;
	}

	public void setPopulation(ArrayList<Chromosome> population) {
		this.population = population;
	}
	
	
	
	
	
	
}
