package numero_binario;

/*
  Esta classe representa um numero binario atraves de um vetor para armazenar um conjunto de bits 
  sem distincao se eles sao sinalizados ou nao. Tambem existem metodos estaticos para trabalhar
  com numeros binarios "crus", isto eh, representados atraves de um vetor de inteiros com 0'se 1's. 
*/

public class NumeroBinario {

	protected int[] binario;



	/*
	 * Retorna a quantidade de bits usada para armazenar o numero
	 */
	public int tamanho() {
		return this.binario.length;
	}

	/*
	 * Retorna o valor do bit na posicao indicada
	 */
	public int bit(int posicao) {
		return this.binario[posicao];
	}

	/*
	 * Retorna um copia do vetor de bits
	 */
	public int[] bits() {
		int[] v = new int[this.binario.length];
		for(int i = 0; i < v.length; i++) v[i] = this.binario[i];
		return v;
	}



	/*
	 * ###########################################################
	 * #################### METODOS ESTATICOS ####################
	 * ###########################################################
	 */



	/*
	 * Desloca o numero binario para a direita preenchendo o espaco vazio a esquerda com 0's
	 * Retorna true caso o numero resultante seja nao nulo e false caso seja nulo (igual a zero)
	 */
	public static boolean deslocarUmParaDireita(int[] binario) {
		boolean binarioDiferenteZero = false;
		for(int i = binario.length - 1; i > 0; i--) {
			binario[i] = binario[i - 1];
			if(binario[i] == 1) binarioDiferenteZero = true;
		}
		binario[0] = 0;

		return binarioDiferenteZero;
	}

	/*
	 * Desloca o numero binario para a esquerda preenchendo o espaco vazio a direita com 0's
	 * Retorna true caso o numero resultado seja nao nulo e false caso seja nulo (igual a zero)
	 */
	public static boolean deslocarUmParaEsquerda(int[] binario) {
		boolean binarioDiferenteZero = false;
		for(int i = 0; i < binario.length - 1; i++) {
			binario[i] = binario[i + 1];
			if(binario[i] == 1) binarioDiferenteZero = true;
		}
		binario[binario.length - 1] = 0;

		return binarioDiferenteZero;
	}

	/*
	 * Soma um unidade a um binario. A soma funciona tanto para numeros sinalizador quanto par numeros
	 * nao sinalizados, mas deve ser indicado se o numero eh sinalizado ou nao para ser possivel verificar
	 * se houve ou nao overflow.
	 */
	public static void somarUm(int[] binario, boolean sinalizado) throws OverflowException {
		boolean positivo = (binario[0] == 0);

		int valorAnterior = 0;		
		int excesso = 1;
		for(int i = binario.length - 1; i >= 0 && excesso == 1; i--) {
			valorAnterior = binario[i]; // Salva o valor do bit antes da modificacao
			binario[i] = (binario[i] + excesso) % 2;
			excesso = (valorAnterior + excesso) / 2; // Calcula o excesso usando o valor do bit antes da modificacao
		}

		// Se o numero for sinalizador e houver mudanca de sinal ou nao for sinalizado e sobrar 1 no excesso, entao houve overflow
		if((sinalizado && positivo && binario[0] == 1) || (!sinalizado && excesso == 1)) throw new OverflowException("Nao eh possivei incrementar uma unidade");
	}

	/*
	 * Soma um unidade a um binario. A soma funciona tanto para numeros sinalizador quanto par numeros
	 * nao sinalizados, mas deve ser indicado se o numero eh sinalizado ou nao para ser possivel verificar
	 * se houve ou nao overflow.
	 */
	public static void subtrairUm(int[] binario, boolean sinalizado) throws OverflowException {
		boolean negativo = (binario[0] == 1);

		int valorAnterior = 0;
		int excesso = 0;
		for(int i = binario.length - 1; i >= 0; i--) {
			valorAnterior = binario[i];
			binario[i] = (binario[i] + 1 + excesso) % 2;
			excesso = (valorAnterior + 1 + excesso) / 2;
		}

		/*
		  Se o numero for sinalizador e houver mudanca de sinal ou nao for sinalizado e sobrar 1 no excesso, entao houve 
		  overflow. No caso de o numero nao ser sinalizado assume-se que o numero eh positivo para poder realizar a operacao
		  com complemento de dois.
		*/
		if((sinalizado && negativo && binario[0] == 0) || (!sinalizado && ((excesso + 1) % 2) == 1)) throw new OverflowException("Nao eh possivel decrementar uma unidade");
	}

	/*
	 * Verifica se um binario eh menor do que o outro de mesmo tamanho, considerando que os dois binarios
	 * nao sao numeros sinalizados.
	 */
	public static boolean menorDoQue(int[] binarioA, int[] binarioB) {
		if(binarioA.length != binarioB.length) return false;

		for(int i = 0; i < binarioA.length; i++) {
			if(binarioA[i] < binarioB[i]) return true; // A eh menor do que B
			else if(binarioA[i] > binarioB[i]) return false; // A eh maior do que B
		}

		// Sao iguais
		return false;
	}
}
