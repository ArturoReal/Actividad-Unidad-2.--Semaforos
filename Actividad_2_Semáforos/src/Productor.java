import java.util.Random;

public class Productor extends Thread{
	
	private static int contadorreservas = 0;
	private int Idreserva;
	
	public Productor() {
		Idreserva = ++contadorreservas;
		start();
	}
	
	public void reservar(int r) {
		
		Random rdmNum = new Random();
		int numN = rdmNum.nextInt(Buffer.bsize)+1;
		int sleepTime = rdmNum.nextInt(250 - 25 + 1) +25; //Rango (max - min +1) + min
		
		try {
			sleep(sleepTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Productor_"+ Idreserva + ": Reserva " + r + " productos.");
		
		//Añadir r elementos al buffer
		for(int i = 0; i < r; i++) {
			synchronized(Buffer.getretenido()) {
				Buffer.getretenido().add(numN);
				numN = rdmNum.nextInt(Buffer.bsize)+1;
			}
			
		}
		
		
		
	}
	
	@Override
	public void run() {
		
		Random rdmNum = new Random();
		Consumidor auxiliar = new Consumidor();
		
		while(true) {
			
			int numP = rdmNum.nextInt(Buffer.bsize)+1;
			
			if (numP+Buffer.getretenido().size() >= Buffer.bsize) {
				System.out.println("Reserva_" + Idreserva + ": El buffer esta lleno, esperando a que el consumidor trabaje");
				auxiliar.libera(Buffer.getretenido().size()-numP);
			}
			
			try {
				Buffer.getsLleno().acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			reservar(numP);
			
			Buffer.getsVacio().release();
		}
		
	}

}