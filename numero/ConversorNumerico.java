package numero;

public class ConversorNumerico {

	/*
	 * Retorna o binario (em complemento de dois) de um inteiro em decimal
	 */
	public static int[] decimalParaBinario(int decimal) throws OverflowException{
		boolean negativo = false;		
		if(decimal < 0) {
			negativo = true;
			decimal *= -1;
		}
 
		int expoente = 0;
		int potencia = 1;
		int soma = 1;
		while(soma < decimal) {
			expoente++;
			potencia *= 2;
			soma += potencia;
		}

		if(expoente + 2 > Inteiro.BITS_INTEIRO) throw new OverflowException("Overflow");

		// Incrementa 1 para ajustar o vetor de acordo com o expoente e mais 1 para o bit de sinal
		int[] binario = new int[expoente + 2];

		// Ajuda o bit de sinal
		binario[0] = (negativo ? 1 : 0);

		if(negativo) {
			// Insere os bits em complemento de 1
			for(int j = binario.length - 1; j >= 0; j--) {
				binario[j] = ((decimal % 2) == 1 ? 0 : 1); // Inverte o valor dos bits
				decimal /= 2;
			}

			// Soma um no binario para virar complemento de dois
			int anterior = 0;
			int excesso = 1;
			for(int i = binario.length - 1; i >= 0 && excesso == 1; i--) {
				anterior = binario[i]; // Salva o valor do bit antes da modificacao
				binario[i] = (binario[i] + excesso) % 2;
				excesso = (anterior + excesso) / 2; // Calcula o excesso usando o valor do bit antes da modificacao
			}
		} else {
			// Insere os bits
			for(int j = binario.length - 1; j > 0; j--) {
				binario[j] = (decimal % 2);
				decimal /= 2;
			}
		}

		return binario;
	}

	public static int binarioParaDecimal(int[] binario) {
		return -1;
	}

	/*
	 * Metodo para converter um objeto Inteiro de um numero binario em um numero decimal inteiro.
	 * Esse metodo suporta conversor de no maximo 32 bits devido a variavel usada para conveter o valor ser
	 * um inteiro que possui 32 bits.
	 */
	public static int binarioParaDecimal(Inteiro a) {
		if(a.tamanho() > 32) throw new IllegalArgumentException("Numero nao suportado");

		int potencia = 1;
		int soma = 0;
		for(int i = a.tamanho() - 1; i > 0; i--) {
			soma += potencia * a.bit(i);
			potencia *= 2;
		}

		soma -= a.bit(0) * potencia;
		return soma;
	}

	public static int binarioParaDecimal(Float a) {
		return -1;
	}
}
