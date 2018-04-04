package numero_binario;

public class ConversaoBinarioDecimal {

	public static final int TAMANHO_MAXIMO_INTEIRO = 32;



	/*
	 * Converte um vetor binario em um numero decimal inteiro.
	 * Esse metodo suporta conversor de no maximo 32 bits devido a variavel usada para converter o valor ser
	 * um inteiro que possui 32 bits.
	 */
	public static int binarioParaDecimal(int[] binario, boolean sinalizado) {
		if(binario.length > ConversaoBinarioDecimal.TAMANHO_MAXIMO_INTEIRO) throw new IllegalArgumentException("O numero nao pode ter mais do que " + ConversaoBinarioDecimal.TAMANHO_MAXIMO_INTEIRO + " bits");

		int potencia = 1;
		int soma = 0;
		for(int i = binario.length - 1; i >= (sinalizado ? 1 : 0); i--) { // Se o numero for sinalizado o bit de sinal nao eh contado
			soma += (potencia * binario[i]);
			potencia *= 2;
		}

		if(sinalizado && binario[0] == 1) soma *= -1;

		return soma;
	}

	/*
	 * Metodo para converter um objeto Inteiro de um numero binario em um numero decimal inteiro.
	 * Esse metodo suporta conversor de no maximo 32 bits devido a variavel usada para converter o valor ser
	 * um inteiro que possui 32 bits.
	 */
	public static int binarioInteiroParaDecimal(Inteiro numero) {
		if(numero.tamanho() > ConversaoBinarioDecimal.TAMANHO_MAXIMO_INTEIRO) throw new IllegalArgumentException("O numero nao pode ter mais do que " + ConversaoBinarioDecimal.TAMANHO_MAXIMO_INTEIRO + " bits");

		int potencia = 1;
		int soma = 0;
		for(int i = numero.tamanho() - 1; i > 0; i--) {
			soma += potencia * numero.bit(i);
			potencia *= 2;
		}

		soma -= numero.bit(0) * potencia;
		return soma;
	}	

	/*
	 * Transforma um float decimal em um float decimal 
	 * Baseado no passo-a-passo: http://class.ece.iastate.edu/arun/CprE281_F05/ieee754/ie5.html
	 */
	public static double binarioFloatParaDecimal(Float numero) {
		int expoente = ConversaoBinarioDecimal.binarioParaDecimal(numero.expoente(), false) - Float.BIAS;

		int[] mantissaBinario = numero.mantissa();

		double potencia = 2;
		double mantissa = 0;
		for(int i = 0; i < mantissaBinario.length; i++) {
			mantissa += (mantissaBinario[i] / potencia);
			potencia *= 2;
		}

		if(mantissa == 0) if(expoente == -Float.BIAS) return 0;
		return (float)((1 + mantissa) * Math.pow(2, expoente) * (numero.bit(0) == 1 ? -1 : 1));
	}

	/*
	 * Tranforma um numero inteiro no sistema decimal em um numero binario inteiro
	 */
	public static Inteiro decimalParaInteiroBinario(int numero) throws OverflowException {
		boolean negativo = (numero < 0);

		// Transforma o numero em positivo
		if(negativo) numero *= -1;

		int expoente = 0;
		int potencia = 1;
		int soma = 1;
		while(soma < numero) {
			expoente++;
			potencia *= 2;
			soma += potencia;
		}

		if(expoente + 2 > Inteiro.MAXIMO_BITS) throw new OverflowException("Nao eh possivel converter o numero " + numero + " para binario, eh necessario mais do que " + Inteiro.MAXIMO_BITS + "bits");

		// Incrementa 1 para ajustar o vetor de acordo com o expoente e mais 1 para o bit de sinal
		int[] binario = new int[expoente + 2];

		// Ajuda o bit de sinal
		binario[0] = (negativo ? 1 : 0);

		// Se for negativo armazena o binario em complemento de 2
		if(negativo) {
			// Insere os bits em complemento de 1
			for(int j = binario.length - 1; j >= 0; j--) {
				//binario[j] = ((numero % 2) == 1 ? 0 : 1); // Inverte o valor dos bits
				binario[j] = ((numero % 2) + 1) % 2; // Inverte o valor dos bits
				numero /= 2;
			}

			// Soma um no binario para virar complemento de dois
			NumeroBinario.somarUm(binario, true);
			/*int anterior = 0;
			int excesso = 1;
			for(int i = binario.length - 1; i >= 0 && excesso == 1; i--) {
				anterior = binario[i]; // Salva o valor do bit antes da modificacao
				binario[i] = (binario[i] + excesso) % 2;
				excesso = (anterior + excesso) / 2; // Calcula o excesso usando o valor do bit antes da modificacao
			}*/
		} else {
			// Insere os bits
			for(int j = binario.length - 1; j > 0; j--) {
				binario[j] = (numero % 2);
				numero /= 2;
			}
		}

		return new Inteiro(binario);
	}

}
