package numero;

public class Inteiro extends Numero {

	public static final int BITS_INTEIRO = 32;

	private int[] binario;

	/*
	 * Cria um objeto para representar um numero inteiro em binario dado um vetor de 0s e 1s representando um numero binario.
	 * Os numeros binarios passados por parametro devem estar representados na forma de complemento de dois.
	 */
	public Inteiro(int[] binario) {
		if(binario.length > BITS_INTEIRO) throw new IllegalArgumentException("Quantidade de bits maior do que a suportada");

		this.binario = new int[Inteiro.BITS_INTEIRO];

		// Diferenca entre a quantidade de bits do numero passado a quantidade padrao do tipo numerico
		int diferenca = this.binario.length - binario.length;

		// Ajusta o bit de sinal
		this.binario[0] = binario[0];

		// Ajusta os bits de valor
		for(int i = binario.length - 1; i > 0; i--) this.binario[i + diferenca] = binario[i];
	
		// Caso o numero seja negativo ele deve ser completado com bits de valor '1' (pois esta em complemento de dois)
		if(this.binario[0] == 1) for(int i = 1; i <= diferenca; i++) this.binario[i] = 1;
	}

	public Inteiro(int numero) {
		int[] binario = ConversorNumerico.decimalParaBinario(numero);

		if(binario.length > BITS_INTEIRO) throw new IllegalArgumentException("Quantidade de bits maior do que a suportada");

		this.binario = new int[Inteiro.BITS_INTEIRO];

		// Diferenca entre a quantidade de bits do numero passado a quantidade padrao do tipo numerico
		int diferenca = this.binario.length - binario.length;

		// Ajusta o bit de sinal
		this.binario[0] = binario[0];

		// Ajusta os bits de valor
		for(int i = binario.length - 1; i > 0; i--) this.binario[i + diferenca] = binario[i];

		// Caso o numero seja negativo ele deve ser completado com bits de valor '1' (pois esta em complemento de dois)
		if(this.binario[0] == 1) for(int i = 1; i <= diferenca; i++) this.binario[i] = 1;
	}

	/*
	 * Retorna o bit na posicao i do vetor de bits
	 */
	public int bit(int posicao) {
		return this.binario[posicao];
	}

	/*
	 * Retorna o tamanho do numero em tamanho de BITS
	 */
	public int tamanho() {
		return this.binario.length;
	}

}
