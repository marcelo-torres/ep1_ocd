package numero_binario;

public class ConversaoBinarioDecimal {

	/*
	 * Metodo para converter um objeto Inteiro de um numero binario em um numero decimal inteiro.
	 * Esse metodo suporta conversor de no maximo 32 bits devido a variavel usada para converter o valor ser
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

	/*
	 * Converte um vetor binario em um numero decimal inteiro.
	 * Esse metodo suporta conversor de no maximo 32 bits devido a variavel usada para converter o valor ser
	 * um inteiro que possui 32 bits.
	 */
	public static int binarioParaDecimal(int[] binario) {
		if(binario.length > 32) throw new IllegalArgumentException("Numero nao suportado");

		int potencia = 1;
		int soma = 0;
		for(int i = binario.length - 1; i >= 0; i--) {
			soma += potencia * binario[i];
			potencia *= 2;
		}

		return soma;
	}	

	public static float binarioParaFloat(Float numero) {
		int[] expoenteBinario = numero.expoente();

		int valorExpoente = -127;
		int potencia = 1;
		for(int i = expoenteBinario.length - 1; i >= 0; i--) {
			valorExpoente += (potencia * expoenteBinario[i]);
			potencia *= 2;
		}

		
	}

}
