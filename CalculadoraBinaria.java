import java.util.Scanner;

import numero_binario.NumeroBinario;
import numero_binario.Inteiro;
import numero_binario.Float;

import numero_binario.OverflowException;

public class CalculadoraBinaria {

	private void imprimirNumeroBinario(NumeroBinario numero) {
		for(int i = 0; i < numero.tamanho(); i++) System.out.print(numero.bit(i));
		System.out.println();
	}

	private int[] obterBits(String stringBinaria) {
		int[] binario = new int[stringBinaria.length()];

		for(int i = 0; i < binario.length; i++) binario[i] = (stringBinaria.charAt(i) - '0');

		return binario;
	}

	public void iniciar() {

		Scanner entrada = new Scanner(System.in);

		String stringBinaria;

		do {
			System.out.println("Binario 1\n####****");
			stringBinaria = entrada.nextLine();
			Inteiro a = new Inteiro(this.obterBits(stringBinaria), stringBinaria.length());

			System.out.println("\nBinario 2\n####****");
			stringBinaria = entrada.nextLine();
			Inteiro b = new Inteiro(this.obterBits(stringBinaria), stringBinaria.length());

			Inteiro c = null;

			try {
				c = Inteiro.somar(a, b);
			} catch(OverflowException oe) {

			}

			System.out.println("\nResultado: ");
			this.imprimirNumeroBinario(c);


		} while(false);
	}

	public static void main(String[] args) {
		(new CalculadoraBinaria()).iniciar();
	}

}
