import numero_binario.*;

public class TesteMetodos {

	public static int[] obterBits(String stringBinaria) {
		int[] binario = new int[stringBinaria.length()];

		for(int i = 0; i < binario.length; i++) binario[i] =  (stringBinaria.charAt(i) - '0');

		return binario;
	}

	public static boolean binariosIguais(int[] a, int[] b) {
		//if(a.length != b.length) return false;

		for(int i = 0; i < a.length; i++) if(a[i] != b[i]) return false;

		return true;
	}
        
        public static void imprimirInteiro(Inteiro inteiro) {
            for(int i = 0; i < inteiro.tamanho(); i++) System.out.print(inteiro.bit(i));
            System.out.println();
        }

	public static void imprimirFloat(numero_binario.Float numero) {
                Inteiro expoente = numero.expoente();
                Inteiro mantissa = numero.mantissa();
                
                System.out.print(mantissa.sinal());
                for(int i = 1; i < expoente.tamanho(); i++) System.out.print(expoente.bit(i));
		for(int i = 2; i < mantissa.tamanho(); i++) System.out.print(mantissa.bit(i));
		System.out.println();
	}

	static String[][] somaInteiros =  {
					// NUMERO A | NUMERO B | RESULTADO | {0 = OVERFLOW N ESPERADO, 1 = OVERFLOW ESPERADO}
					/*{00000000000000000000000000000000, 00000000000000000000000000000000, 00000000000000000000000000000000, 0},
					{00000000000000000000000000000000, 00000000000000000000000000000000, 00000000000000000000000000000000, 0},
					{00000000000000000000000000000000, 00000000000000000000000000000000, 00000000000000000000000000000000, 0},
					{00000000000000000000000000000000, 00000000000000000000000000000000, 00000000000000000000000000000000, 0},
					{00000000000000000000000000000000, 00000000000000000000000000000000, 00000000000000000000000000000000, 0},
					{00000000000000000000000000000000, 00000000000000000000000000000000, 00000000000000000000000000000000, 0},
					{00000000000000000000000000000000, 00000000000000000000000000000000, 00000000000000000000000000000000, 0},
					{00000000000000000000000000000000, 00000000000000000000000000000000, 00000000000000000000000000000000, 0},
					{00000000000000000000000000000000, 00000000000000000000000000000000, 00000000000000000000000000000000, 0},
					{00000000000000000000000000000000, 00000000000000000000000000000000, 00000000000000000000000000000000, 0},
					{0000000000000000, 0000000000000000, 0000000000000000, 0},
					{0000000000000000, 0000000000000000, 0000000000000000, 0},
					{0000000000000000, 0000000000000000, 0000000000000000, 0},
					{0000000000000000, 0000000000000000, 0000000000000000, 0},
					{0000000000000000, 0000000000000000, 0000000000000000, 0},
					{0000000000000000, 0000000000000000, 0000000000000000, 0},
					{0000000000000000, 0000000000000000, 0000000000000000, 0},
					{0000000000000000, 0000000000000000, 0000000000000000, 0},
					{0000000000000000, 0000000000000000, 0000000000000000, 0},
					{0000000000000000, 0000000000000000, 0000000000000000, 0},
					{00000000, 00000000, 00000000, 0},
					{00000000, 00000000, 00000000, 0},
					{00000000, 00000000, 00000000, 0},
					{00000000, 00000000, 00000000, 0},
					{00000000, 00000000, 00000000, 0},*/
					{"00000000", "00000000", "00000000", "0"},
					{"01010101", "01001101", "10100010", "1"}, // 85 + 77 = overflow
					{"01010101", "10101011", "00000000", "0"}, // 85 - 85 = 0
					{"10000000", "10000000", "00000000", "1"}, // -128 - 128 = overflow
					{"01111111", "01111111", "11111110", "1"}, // 127 + 127 = overflow
					{"0010", "0001", "0011", "0"}, // 1 + 2 = 3 
					{"0111", "1000", "1111", "0"}, // 7 - 8 = -1
					{"0010", "1101", "1111", "0"}, // 2 - 3 = -1
					{"0011", "0010", "0101", "0"}, // 4 + 1 = 5
					{"0111", "0111", "1110", "1"}, // 7 + 7 = overflow
					{"1000", "1000", "0000", "1"}, // -8 -8 = overflow
					{"0011", "1101", "0000", "0"}, // 3 - 3 = 0
					{"10", "00", "10", "0"}, // -2 + 0 = -2
					{"11", "11", "10", "0"}, // -1 - 1 = -2
					{"01", "11", "00", "0"}, // 1 - 1 = 0
					{"10", "10", "00", "1"}, // -2 -2 = overflow
					{"01", "01", "10", "1"}  // 1 + 1 = overflow
				};


	/*
	  1,010110101011011111000

	 .,10110101011011111000 * 2^10010011

	   01001001110110101011011111000



	  1011011100110101000 = 1,011011100110101000 * 2^18 = [11011100110101000] * 2^17 (10010000) = 11001000011011100110101000
	*/

	static String[][] somaFloats = {
					// NUMERO A | NUMERO B | RESULTADO | {0 = OVERFLOW N ESPERADO, 1 = OVERFLOW ESPERADO}
					//sEEEEEEEEVVVVVVVVVVVVVVVVVVVVVVV    sEEEEEEEEVVVVVVVVVVVVVVVVVVVVVVV    sEEEEEEEEVVVVVVVVVVVVVVVVVVVVVVV
					//{"01000011100000000000000000000000", "01000011100000000000000000000000", "00000000000000000000000000000000", "0"},
					{"00000000000000000000000000000000", "00000000000000000000000000000000", "00000000000000000000000000000000", "0"},
					{"01000110111111011100000000000000", "00000000000000000000000000000000", "01000110111111011100000000000000", "0"},
					{"00000000000000000000000000000000", "01000110111111011100000000000000", "01000110111111011100000000000000", "0"},
					{"01000010010010000000000000000000", "01000001110010000000000000000000", "01000010100101100000000000000000", "0"}, // 50 + 25 = 75
					{"01000001110010000000000000000000", "01000001110010000000000000000000", "01000010010010000000000000000000", "0"},
					{"01000001111000000000000000000000", "01000100000011011100000000000000", "01000100000101001100000000000000", "0"}, // 28 + 567 =  
					{"01000010010010000000000000000000", "11000010110010000000000000000000", "11000010010010000000000000000000", "0"}, // 50 - 100 = -50
					{"11000100011000101100000000000000", "01000100001010001100000000000000", "11000011011010000000000000000000", "0"}, // -907 + 675 = 
					{"00111111110000000000000000000000", "00111111000000000000000000000000", "01000000000000000000000000000000", "0"}, // 1.5 + 0.5 = 
					//{"00000000000000000000000000000000", "00000000000000000000000000000000", "00000000000000000000000000000000", "0"},
					//{"00000000000000000000000000000000", "00000000000000000000000000000000", "00000000000000000000000000000000", "0"},
					//{"00000000000000000000000000000000", "00000000000000000000000000000000", "00000000000000000000000000000000", "0"},
				      };

	/*
	 *
	 *  METODOS DE TESTE
	 *
	 */

	public static void testarInteiro(int operacao, String[][] vetorDeTeste) {
		int testes = 0;
		int erros = 0;

		Inteiro resultado = new Inteiro(new int[Inteiro.MAXIMO_BITS]);

		boolean errado;

		for(int i = 0; i < vetorDeTeste.length; i++) {
			errado = false;
			try {
				int[] binarioA = obterBits(vetorDeTeste[i][0]);
				int[] binarioB = obterBits(vetorDeTeste[i][1]);
				int[] resultadoEsperado = obterBits(vetorDeTeste[i][2]);

				switch(operacao) {
					case 0:
						resultado = Inteiro.somar(new Inteiro(binarioA, binarioA.length), new Inteiro(binarioB, binarioB.length));
						break;

					case 1:
						resultado = Inteiro.subtrair(new Inteiro(binarioA, binarioA.length), new Inteiro(binarioB, binarioB.length));
						break;

					case 2:
						resultado = Inteiro.multiplicar(new Inteiro(binarioA, binarioA.length), new Inteiro(binarioB, binarioB.length));
						break;

					case 3:
						resultado = Inteiro.dividir(new Inteiro(binarioA, binarioA.length), new Inteiro(binarioB, binarioB.length));
						break;

					default:
						throw new IllegalArgumentException("Escolha uma operacao: 0, 1, 2 ou 3; nao " + operacao + "!!!!!!!");
				}

				if(!binariosIguais(resultado.bits(), resultadoEsperado)) {
					errado = true;
					erros++;
				}
			} catch(OverflowException oe) {
				resultado = new Inteiro(new int[Inteiro.MAXIMO_BITS]);
				if(vetorDeTeste[i][3].equals("1")) {
					// Overflow esperado, n houve erro
					errado = false;
					System.out.print("[!] Overflow esperado\t");
				} else {
					errado = true;
					erros++;
					System.out.print("[!] Overflow NÃO esperado (" + oe.getMessage() + ")\t");
				}
			} finally {
				testes++;

				System.out.print(vetorDeTeste[i][0] + " + " + vetorDeTeste[i][1] + " = ");
				imprimirInteiro(resultado);
				System.out.println("Errado? " + errado + "\t Erros: " + erros + "/" + testes + "\t Resultado esperado: " + vetorDeTeste[i][2] + "\n");
			}
		}

		System.out.println("Final\nErros: " + erros + "\nTestes: " + testes + "\n");
	}

	public static void testarFloat(int operacao, String[][] vetorDeTeste) {
		int testes = 0;
		int erros = 0;

		numero_binario.Float resultado;
		int[] binarioA;
		int[] binarioB;
		int[] resultadoEsperado;

		boolean errado;

		for(int i = 0; i < vetorDeTeste.length; i++) {
			errado = false;

			binarioA = obterBits(vetorDeTeste[i][0]);
			binarioB = obterBits(vetorDeTeste[i][1]);
			resultadoEsperado = obterBits(vetorDeTeste[i][2]);
			resultado = new numero_binario.Float(new int[numero_binario.Float.MAXIMO_BITS]);

			try {
				switch(operacao) {
					case 0:
						resultado = numero_binario.Float.somar(new numero_binario.Float(binarioA), new numero_binario.Float(binarioB));
						break;

					case 1:
						resultado = numero_binario.Float.subtrair(new numero_binario.Float(binarioA), new numero_binario.Float(binarioB));
						break;

					case 2:
						resultado = numero_binario.Float.multiplicar(new numero_binario.Float(binarioA), new numero_binario.Float(binarioB));
						break;

					case 3:
						resultado = numero_binario.Float.dividir(new numero_binario.Float(binarioA), new numero_binario.Float(binarioB));
						break;

					default:
						throw new IllegalArgumentException("Escolha uma operacao: 0, 1, 2 ou 3; nao " + operacao + "!!!!!!!");
				}

				System.out.println(ConversaoBinarioDecimal.binarioFloatParaDecimal(new numero_binario.Float(binarioA)) + " + " + ConversaoBinarioDecimal.binarioFloatParaDecimal(new numero_binario.Float(binarioB)) + " = " + ConversaoBinarioDecimal.binarioFloatParaDecimal(resultado));

				if(!binariosIguais(resultado.bits(), resultadoEsperado)) {
					errado = true;
					erros++;
				}
			} catch(OverflowException oe) {
				resultado = new numero_binario.Float(new int[numero_binario.Float.MAXIMO_BITS]);
				if(vetorDeTeste[i][3].equals("1")) {
					// Overflow esperado, n houve erro
					errado = false;
					System.out.print("[!] Overflow esperado\t");
				} else {
					errado = true;
					erros++;
					System.out.print("[!] Overflow NÃO esperado (" + oe.getMessage() + ")\t");
				}
			} finally {
				testes++;

				System.out.print(vetorDeTeste[i][0] + " + " + vetorDeTeste[i][1] + " = ");
				imprimirFloat(resultado);
				System.out.println("Errado? " + errado + "\t Erros: " + erros + "/" + testes + "\t Resultado esperado: " + vetorDeTeste[i][2] + "\n");
			}
		}

		System.out.println("Final\nErros: " + erros + "\nTestes: " + testes + "\n");
	}

	public static void main(String[] args) {

		// Testa soma de inteiros
		//testarInteiro(0, somaInteiros);

		// 1 10001110 10001110001000000000000

		testarFloat(0, somaFloats);

		int[] expoente = {1, 0, 0, 0, 0, 0, 0, 1};
		int[] mantissa = {1, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 1, 0};

		//numero_binario.Float numero = new numero_binario.Float(1, expoente, mantissa);

		//System.out.println(ConversaoBinarioDecimal.binarioParaFloat(numero));
	}

}
