package numero_binario;

/*
  Esta classe representa um numero inteiro sinalizado em binario, onde o primeiro bit eh o de sinal. Todas as operacoes envolvendo
  instancias dessa classe assumem que o binario armazenado esta em complemento de dois. 
*/

public class Inteiro extends NumeroBinario {

	// Numero maximo de bits que pode ser usado para representar um inteiro
	public static final int MAXIMO_BITS = 32;



	/*
	 * Inicializa o vetor de bits a partir de um numero binario no formato de complemento de dois
	 */
	private void criarInteiro(int[] binario, int numeroBits) {
		if(binario.length > Inteiro.MAXIMO_BITS || numeroBits > Inteiro.MAXIMO_BITS) throw new IllegalArgumentException("Nao eh possivel usar mais do que " + Inteiro.MAXIMO_BITS + " bits");
		if(binario.length > numeroBits) throw new IllegalArgumentException("O numero de bits indicado eh menor do que o tamanho do vetor de bits");

		super.binario = new int[numeroBits];

		// Diferenca entre a quantidade de bits do numero passado a quantidade padrao do tipo numerico
		int diferenca = super.binario.length - binario.length;

		// Ajusta o bit de sinal
		super.binario[0] = binario[0];

		// Ajusta os bits de valor
		for(int i = binario.length - 1; i > 0; i--) super.binario[i + diferenca] = binario[i];
	
		// Caso o numero seja negativo ele deve ser completado com bits de valor '1' (pois esta em complemento de dois)
		if(this.binario[0] == 1) for(int i = 1; i <= diferenca; i++) super.binario[i] = 1;	
	}

	/*
	 * Cria um Inteiro com um numero binario de tamanho indicado no paramatro
	 */
	public Inteiro(int[] binario, int numeroBits) {
		this.criarInteiro(binario, numeroBits);
	}

	/*
	 * Cria um Inteiro com um numero binario de tamanho maximo da permitido
	 */
	public Inteiro(int[] binario) {
		this.criarInteiro(binario, Inteiro.MAXIMO_BITS);
	}



	/*
	 * Devolve o valor do bit de sinal
	 */	
	public int sinal() {
		return super.binario[0];
	}



	/*
	 * ###########################################################
	 * ################# OPERADORES ARITMETICOS ##################
	 * ###########################################################
	 */



	/*
	 * Realiza a soma de dois binarios no formato de complemento de dois.
	 */
	public static Inteiro somar(Inteiro a, Inteiro b) throws OverflowException {
		int[] c = new int[(a.tamanho() > b.tamanho() ? a.tamanho() : b.tamanho())];

		// Excesso de uma soma (no caso de 1 + 1 excesso = 1)
		int excesso = 0;

		// Soma os bits
		for(int i = c.length - 1; i >= 0; i--) {
			c[i] = (a.bit(i) + b.bit(i) + excesso) % 2;
			excesso = (a.bit(i) + b.bit(i) + excesso) / 2;
		}

		// Se o numero 'a' e o numero 'b' possuem o mesmo sinal, entao houve um 'overflow' se, e somente se, 
		// o sinal do numero resultante da soma for diferente
		if(a.sinal() == b.sinal() && a.sinal() != c[0]) throw new OverflowException(""); 

		return new Inteiro(c, c.length);
	}

	public static Inteiro subtrair(Inteiro a, Inteiro b) throws OverflowException {
		return a;
	}

	public static Inteiro multiplicar(Inteiro a, Inteiro b) throws OverflowException {
		return a;
	}

	public static Inteiro dividir(Inteiro a, Inteiro b) throws OverflowException {
		return a;
	}

}
