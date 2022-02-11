
public class Programa {
	
	public static final int PRODUCERS_COUNT = 100;
	public static final int CONSUMERS_COUNT = 100;

	public static void main(String[] args) {
		
		for(int i = 0; i < PRODUCERS_COUNT; i++) {
			new  Productor();
		}
		
		for(int i = 0; i < CONSUMERS_COUNT; i++) {
			new  Consumidor();
		}
	}

}
