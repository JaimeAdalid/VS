import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Jokenpo {
	
	public static Semaphore semaphore = new Semaphore(1);
	
	public static int maximoRodadas;
	
	public static Jogador[] jogador = new Jogador[3];
	
	public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in);
		System.out.println("Quantas partidas serão jogadas?");
		maximoRodadas = input.nextInt();
		
		for (int i = 0; i < jogador.length; i++ ) {
			jogador[i] = new Jogador(i, semaphore, jogador, maximoRodadas);
		}
		
		for (int i = 0; i < jogador.length; i++ ) {
			jogador[i].start();
			
			try {
				jogador[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
