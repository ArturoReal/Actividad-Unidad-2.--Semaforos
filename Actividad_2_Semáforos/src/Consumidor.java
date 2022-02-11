import java.util.Random;

public class Consumidor extends Thread {
	
	private static int consumersCount = 0;
	private int consumerId;

	
	public Consumidor() {
		consumerId = ++consumersCount;
		start();
	}
	
	public void libera(int c) {
		Random rnmNum = new Random();
		int sleepTime = rnmNum.nextInt(250 - 25 + 1) + 25;
		int consumedNumber;
		
		try {
			sleep(sleepTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//Consumir c elementos del buffer
		for(int i = 0; i < c; i ++){
			synchronized(Buffer.getretenido()){
				consumedNumber = Buffer.getretenido().poll();
				System.out.println("Consumidor_" + consumerId +": Numero " + consumedNumber + " consumido.");
			}
		}
		
	}
	
	@Override
	public void run() {
		
		Productor salvador = new Productor();
		Random rnmNum = new Random();
		
		while(true) {
			
			int numC = rnmNum.nextInt(Buffer.bsize)+1;
			
			if(Buffer.getretenido().size() < numC) {
				
				System.out.println("Consumidor_" + consumerId +": El buffer está vacío, esperando a que el productor trabaje.");
				salvador.reservar(numC - Buffer.getretenido().size());
				
				try {
					Buffer.getsVacio().acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				libera(numC);
				
				Buffer.getsLleno().release();
			}
		}
	}
	
}
