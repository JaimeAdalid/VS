import java.util.Random;
import java.util.concurrent.Semaphore;

public class Jogador extends Thread {
	
	public int id;
	public Random random = new Random();
	
	public int jogada = 0; // 0 = Pedra, 1 = Papel, 2 = Tesoura
	public int pontos = 0;
	
	public static int rodadaAtual = 0;
	public static int rodadaFinal;
	
	public static Jogador[] jogador;
	
	private static Semaphore semaphore;
	
	public Jogador(int id, Semaphore semaphore, Jogador[] jogador, int rodadaFinal) {
		this.id = id;
		this.semaphore = semaphore;
		this.jogador = jogador;
		this.rodadaFinal = rodadaFinal;
		
		System.out.println("Jogador " + (this.id + 1) + " entrou na brincadeira!");
	}
	
	/*MÉTODO PLAYROUND 
	 * Responsável por fazer as jogadas.
	 * Ele irá rolar o valor da jogada de cada jogador envolvido e fará uma comparação.*/
	public void comparaJogada(Jogador oponente) {
		if (this.jogada == 0) {
			switch (oponente.jogada) {
			case 0:
				System.out.println("Jogador " + this.id + " empatou com o Jogador  " + oponente.id);
				break;
			case 1:
				System.out.println("Jogador " + this.id + " perdeu para Jogador " + oponente.id);
				oponente.pontos++;
				break;
			case 2:
				System.out.println("Jogador " + this.id + " venceu o Jogador " + oponente.id);
				this.pontos++;
				break;
			}	
		}
		
		if (this.jogada == 1) {			
			switch (oponente.jogada) {
		case 0:
			System.out.println("Jogador " + this.id + " venceu o Jogador " + oponente.id);
			this.pontos++;
			break;
		case 1:
			System.out.println("Jogador " + this.id + " empatou com o Jogador " + oponente.id);
			break;
		case 2:
			System.out.println("Jogador " + this.id + " perdeu para Jogador " + oponente.id);
			oponente.pontos++;
			break;
			}
		}
		
		if (this.jogada == 2) {
			switch (oponente.jogada) {
		case 0:
			System.out.println("Jogador " + this.id + " perdeu para Jogador " + oponente.id);
			oponente.pontos++;
			break;
		case 1:
			System.out.println("Jogador " + this.id + " venceu o Jogador " + oponente.id);
			this.pontos++;
			break;
		case 2:
			System.out.println("Jogador " + this.id + " empatou com o Jogador  " + oponente.id);
			break;
			}
		}

	}
	
	/*MÉTODO JOGADA
	Ele tentará pegar o semáforo e executar o método comparaJogada para cada outro jogador.
	Após isso, ele irá dormir por um tempo. */
	public void jogada() {
		try {
				while(rodadaAtual < rodadaFinal) {
					
				
				rodadaAtual++;
				System.out.println("[ ROUND " + rodadaAtual + " ] \n");
				semaphore.acquire();
				this.jogada = random.nextInt(3);
				this.mostraJogada();
			
				for (int i = 0; i < jogador.length; i++) {
					if (i != this.id) {
						jogador[i].jogada = random.nextInt(3);
						jogador[i].mostraJogada();
						Thread.sleep(200);
					}
				}
				
				for (int i = 0; i < jogador.length; i++) {
					if (i != this.id)this.comparaJogada(jogador[i]);
				}
				
				switch (this.id) {
				case 0: jogador[1].comparaJogada(jogador[2]); break;
				case 1: jogador[0].comparaJogada(jogador[2]); break;
				case 2: jogador[0].comparaJogada(jogador[1]); break;
				}
				
				System.out.println("\n * Fim de Round *\n \n");
				mostraPontos();
				semaphore.release(); 
				Thread.sleep(200 + (this.id));
		
			}

		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}	
	
	
	public void run() {
			jogada();
	}

	public void mostraJogada() {
		switch (jogada) {
		case 0:
			System.out.println("Jogador " + this.id + " jogou Pedra");
			break;
		case 1:
			System.out.println("Jogador " + this.id + " jogou Papel");
			break;
		case 2:
			System.out.println("Jogador " + this.id + " jogou Tesoura");
			break;
			
		}
	}

	/* MÉTODO MOSTRA PONTOS
	 * Imprime o placar dos jogadores*/
	public void mostraPontos(){
		System.out.println("\n [   PLACAR   ]");
		for (int i = 0; i < jogador.length; i++) {
			System.out.println("Jogador " + jogador[i].id + ": " + jogador[i].pontos + " pontos");
		}
		System.out.println("\n");
	}
}
