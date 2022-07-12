import java.util.ArrayList;
import java.util.Random;

public class Driver {
	private static final int A = 0, B = -5, C = 5, STOP = 100; 
	private static final int PM = 40, PK = 75; 
	public static Population pop = new Population();
	private static double bestValue = 0;
	private static int bestX;
	
	public static ArrayList<Chromosome> child = new ArrayList<>();
	private static ArrayList<Integer> punktMutacji = new ArrayList<>();
	private static ArrayList<Integer> punktKrzyzowania = new ArrayList<>();
	public static ArrayList<Double> score = new ArrayList<>();
	public static ArrayList<Double> selection = new ArrayList<>();
	public static double summaryScore;

	public static ArrayList<Chromosome> newParent = new ArrayList<>();
	
	public static ArrayList<Double> ruletka = new ArrayList<>();
	public static ArrayList<Integer> checkNewParent = new ArrayList<>();
	public static Random rand = new Random();
	
	
	public static void main(String[] args) {
		int check = 0;
		while(check < STOP) {
		child.clear();
		punktKrzyzowania.clear();
		punktMutacji.clear();
		score.clear();
		ruletka.clear();
		checkNewParent.clear();
		newParent.clear();
		selection.clear();
		
		System.out.print("Populacja poczatkowa: ");
		for(int i = 0; i < pop.getPopulation().size(); i++) {
			System.out.print(pop.getPopulation().get(i).getDec() + ", " );
		} System.out.println();
		
		
			//Obliczanie funkcji przystosowania - najlepszy zawodnik
			for(int i = 0; i < pop.getPopulation().size(); i++) {
				double f = function(pop.getPopulation().get(i).getDec(), A, B, C);
				if(f > bestValue) {
					bestValue = f;	
					bestX = pop.getPopulation().get(i).getDec();
					//check = 0;
				} else {
					//check++;
				}
				score.add(f);
			}
			
			
				
			System.out.println("Uzyskane wyniki: " +score);
			
			summaryScore = sumScore();
			
			System.out.println("Suma: " + summaryScore);
			
			
		
			
			
			selectionValue();
			System.out.println("Udzia³ procentowy, po kolei: " + selection);
			System.out.println("Suma procentowa: " + sumScoreSel());

			setRuletka();
			System.out.println("Kolo fortuny: " + ruletka);
			
			generateNewParent();
			System.out.print("Nowi rodzice, wedlug kola: ");
			for(int i = 0; i < pop.getPopulation().size(); i++) {
				System.out.print(pop.getPopulation().get(i).getDec() + ", " );
			} System.out.println();

			findKrzyzowanie();
			System.out.println("Punkty przeciecia (-1 = brak): " + punktKrzyzowania);
		
			generateChild();
			System.out.print("Dzieci, po krzyzowaniu: ");
			for(int i = 0; i < pop.getPopulation().size(); i++) {
				System.out.print(child.get(i).getDec() + ", " );
			} System.out.println();
			
			findMutacja();
			System.out.println("Punkty mutacji (-1 - brak mutacji): " + punktMutacji);
			
			
			mutacja();
			System.out.print("Dzieci, po mutacji: ");
			for(int i = 0; i < pop.getPopulation().size(); i++) {
				System.out.print(child.get(i).getDec() + ", " );
			} System.out.println();
			
			
			saveChildAsParent();
			
			//Obliczanie funkcji przystosowania - najlepszy zawodnik
			for(int i = 0; i < pop.getPopulation().size(); i++) {
				double f = function(pop.getPopulation().get(i).getDec(), A, B, C);
				if(f > bestValue) {
					bestValue = f;	
					bestX = pop.getPopulation().get(i).getDec();
					check = 0;
				} else {
					check++;
				}
				score.add(f);
			}
			
			
				
			System.out.println("Uzyskane wyniki: " +score);
			
			summaryScore = sumScore();
			
			System.out.println();
			System.out.println();
			System.out.println();
		}	
		
		System.out.println();
		System.out.println("Najlepszy chromosom:" + bestX + ", wynik: " + bestValue);
		
		
	}
	
	private static void saveChildAsParent() {
		for(int i = 0; i < child.size(); i++) {
			pop.getPopulation().get(i).setBin(child.get(i).getBin());
			pop.getPopulation().get(i).setDec(child.get(i).getDec());
		}
	}
	
	
	private static void mutacja() {
		String bin, get, start, end;
		
		for(int i = 0; i < child.size(); i++) {
			if(punktMutacji.get(i) >= 0) {
				bin = child.get(i).getBin();
				get = bin.substring(punktMutacji.get(i)-1, punktMutacji.get(i));
				start = bin.substring(0, punktMutacji.get(i)-1);
				end = bin.substring(punktMutacji.get(i), 8);


				if(get.equals("0")) {
					get = "1";
				} else {
					get = "0";
				}

				bin = start + get + end;
				child.get(i).setBin(bin);
				child.get(i).setDec(binaryToDecimal(bin));
				
			} else {
				
			}
			
			
			
		}
		
		
		
		
		
		
	}
	
	
	
	private static void findMutacja() {
		for(int i = 0; i < child.size(); i++) {
			int value = rand.nextInt(101);
			if(value > PM) punktMutacji.add(-1);
			else {
				value = rand.nextInt(5) + 1;
				punktMutacji.add(value);
			}
		}
	}
	
	
	
	public static void generateChild() {
		String bin1, bin2, get1, get2, first1, first2;
		int a, b;
		
		int k = 0; int i = 1;
		
		while(k < 10 && i < 20) {
			if(punktKrzyzowania.get(k) >= 0) {
				bin1 = pop.getPopulation().get(i-1).getBin();
				bin2 = pop.getPopulation().get(i).getBin();
				first1 = bin1.substring(0, punktKrzyzowania.get(k));
				first2 = bin2.substring(0, punktKrzyzowania.get(k));
				get1 = bin1.substring(punktKrzyzowania.get(k), 8);
				get2 = bin2.substring(punktKrzyzowania.get(k), 8);
				bin1 = first1 + get2;
				a = binaryToDecimal(bin1);
		
				bin2 = first2 + get1;
				b = binaryToDecimal(bin2);
				
				
				
				
				
				
				
	
				child.add(new Chromosome(a, bin1));
				child.add(new Chromosome(b, bin2));
				i++;
				k++;
			} else if(punktKrzyzowania.get(k) < 0) {
				child.add(new Chromosome(pop.getPopulation().get(i-1).getDec() , pop.getPopulation().get(i-1).getBin()));
				child.add(new Chromosome(pop.getPopulation().get(i).getDec() , pop.getPopulation().get(i).getBin()));
				i++;
				k++;
			}
		}
		
			
			
			
		}
		
		
		
	public static int binaryToDecimal(String bin){
		int dec = 0;
		for(int i = 0; i < bin.length(); i++) {
			dec += Integer.valueOf(bin.substring(i, i+1)) * Math.pow(2, 7-i);
		}
		return dec;
	}
	
	

	private static void findKrzyzowanie() {
		for(int i = 0; i < pop.getPopulation().size()/2; i++) {
			int value = rand.nextInt(101);
			if(value > PK) punktKrzyzowania.add(-1);
			else {
				value = rand.nextInt(7) + 1;
				punktKrzyzowania.add(value);
			}
		}
	}
	
	
	
	private static void selectionValue() {
		for(int i = 0; i < score.size(); i++) {
			selection.add(score.get(i)/summaryScore*100);
		}
	}
	
	private static double sumScoreSel() {
		double sum = 0;
		for(int i = 0; i < score.size(); i++) sum += selection.get(i);
		return sum;
	}
	
	
		
	private static double function(int x, int a, int b, int c) {
		double f;
		f = ((a*Math.pow(x, a)) - Math.sin(Math.pow(b, x))) / Math.pow(Math.E, Math.sin(c));
		
		if(Double.isInfinite(f)) return -1;
		else if(Double.isNaN(f)) return -1;
		else return f;
	}
	
	private static double sumScore() {
		double sum = 0;
		for(int i = 0; i < score.size(); i++) sum += score.get(i);
		return sum;
	}
	
	private static void setRuletka() {
		double sum = 0;
		for(int i = 0; i < selection.size(); i++) {
			sum += selection.get(i);
			ruletka.add(sum);
		}
	}
	
	private static void generateNewParent() {
		newParent.clear();
		for(int i = 0; i < pop.getPopulation().size(); i++) {
			int n = rand.nextInt(101);
			int it = 0;
			while(true) {
				if(n <= ruletka.get(it)) {
					checkNewParent.add(n);
					String bin;
					bin = Integer.toBinaryString(pop.getPopulation().get(it).getDec());
					while(bin.length() < 8) {
							bin = "0" + bin;
					}
					newParent.add(new Chromosome(pop.getPopulation().get(it).getDec(), bin));
					break;
				} else {
					if(it == 19) {
						newParent.add(new Chromosome(0, "00000000"));
						break;
					}
					else it++;
				}
			}
			
		}
		
		//Nowa populacja
		for(int i = 0; i < pop.getPopulation().size(); i++) {
			pop.getPopulation().get(i).setDec(newParent.get(i).getDec());
			pop.getPopulation().get(i).setBin(newParent.get(i).getBin());
		}
		
		
	}
	
	
	
	
}
