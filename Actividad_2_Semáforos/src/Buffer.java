import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Buffer {
	
	private static Queue<Integer> retenido = new LinkedList<Integer>();
	
	public static final int bsize = 10;
	
	private static Semaphore Vacio = new Semaphore(0, true);
	private static Semaphore Lleno = new Semaphore(bsize, true);
	
	public static Queue<Integer> getretenido() {
		return retenido;
	}
	
	public static Semaphore getsVacio() {
		return Vacio;
	}
	
	public static Semaphore getsLleno() {
		return Lleno;
	}
	
	

}