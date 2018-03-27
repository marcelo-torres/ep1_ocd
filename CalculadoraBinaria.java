import java.util.Scanner;
import java.util.InputMismatchException;

import numero.Numero;
import numero.Inteiro;
import numero.Float;
import numero.ConversorNumerico;

import numero.OverflowException;

public class CalculadoraBinaria {

	String menuOpcoes = "+----------------------+\n" +
			    "| 1 - somar (+)        |\n" +
			    "| 2 - subrair (-)      |\n" +
			    "| 3 - multiplicar (x)  |\n" +
			    "| 4 - dividir (/)      |\n" +
			    "| 0 - sair             |\n" +
			    "+----------------------+\n";

	public void exibirMenu() {
		System.out.print(this.menuOpcoes);
	}

	private int operar(Inteiro a, Inteiro b, int operacao) throws OverflowException {
		switch(operacao) {
			case 1: // Somar
				return ConversorNumerico.binarioParaDecimal(Numero.somar(a, b));

			case 2: // Subtrair
				return ConversorNumerico.binarioParaDecimal(Numero.subtrair(a, b));

			case 3: // Multiplicar
				return ConversorNumerico.binarioParaDecimal(Numero.multiplicar(a, b));

			case 4: // Dividir
				return ConversorNumerico.binarioParaDecimal(Numero.dividir(a, b));

			default:
				System.out.println("[!] Ocorreu algum erro!");
		}

		return 0;
	}

	private int operar(Float a, Float b, int operacao) throws OverflowException {
		switch(operacao) {
			case 1: // Somar
				return ConversorNumerico.binarioParaDecimal(Numero.somar(a, b));

			case 2: // Subtrair
				return ConversorNumerico.binarioParaDecimal(Numero.subtrair(a, b));

			case 3: // Multiplicar
				return ConversorNumerico.binarioParaDecimal(Numero.multiplicar(a, b));

			case 4: // Dividir
				return ConversorNumerico.binarioParaDecimal(Numero.dividir(a, b));

			default:
				System.out.println("[!] Ocorreu algum erro!");
		}

		return 0;
	}

	public void iniciar() {
		Scanner entrada = new Scanner(System.in);
		Scanner entradaNumerica = new Scanner(System.in);

		int opcao = 0;

		boolean operacaoInteiros;

		double valor1 = 0;
		double valor2 = 0;

		int resultado = 0;

		do {
			System.out.println();
			this.exibirMenu();

			System.out.print("Selecione uma opcao: ");
			try {
				opcao = entrada.nextInt();
			} catch(InputMismatchException ime) {
				System.out.println("[!] Ops, isto nao eh uma opcao valida!");
				entrada.next();
				continue;
			}

			if(opcao < 0 || opcao > 4) System.out.println("[!] Ops, isto nao eh uma opcao valida!");
			else if(opcao == 0) break;

			do {
				operacaoInteiros = true;
				int escolha = 1;

				try {

					// Lembrar de tratar overflow na entrada

					if(entradaNumerica.hasNextInt()) {
						valor1 = entradaNumerica.nextInt();
					} else {
						valor1 = entradaNumerica.nextFloat();
						operacaoInteiros = false;
					}

					if(entradaNumerica.hasNextInt()) {
						valor2 = entradaNumerica.nextInt();
					} else {
						valor2 = entradaNumerica.nextFloat();
						operacaoInteiros = false;
					}

					break;
				} catch(InputMismatchException ime) {
					System.out.println("[!] Digite valores validos");
					entradaNumerica.next();
					continue;
				}
			} while(true);

			try {
				if(operacaoInteiros)
					// Operacao com inteiros
					resultado = this.operar(new Inteiro((int)valor1), new Inteiro((int)valor2), opcao);
				else
					// Operacao com floats
					resultado = this.operar(new Float((float)valor1), new Float((float)valor2), opcao);
			} catch(OverflowException oe) {
				System.out.println("[!] Overflow: " + oe.getMessage());
			}

			System.out.println("Resultado: " + resultado); 

		} while(true);

	}

	public static void main(String args[]) {

		CalculadoraBinaria calculadora = new CalculadoraBinaria();
		calculadora.iniciar();

	}
}
