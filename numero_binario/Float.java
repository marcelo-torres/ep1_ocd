package numero_binario;

/*
  Esta classe representa um numero com ponto flutuante em binario no formato IEEE754. O primeiro bit eh de sinal, os bits em seguida
  representam o expoente enquanto os bits restantes representam a mantissa.
*/

public class Float extends NumeroBinario {

	// Numero maximo de bits que podem ser usados para representar um binario inteiro
	public static final int MAXIMO_BITS = 32;

	// Indice do bit no qual comeca o expoente
	public static final int COMECO_EXPOENTE = 1;

	// Indice do bit no qual comeca a mantissa
	public static final int COMECO_MANTISSA = 9;

	public static final int TAMANHO_EXPOENTE = Float.COMECO_MANTISSA - Float.COMECO_EXPOENTE;
	public static final int TAMANHO_MANTISSA = Float.MAXIMO_BITS - Float.COMECO_MANTISSA;

	public static final int BIAS = (int)Math.pow(2, Float.TAMANHO_EXPOENTE - 1) - 1;

	/*
	 * Cria um numero float distribuindo os bits de forma adequada.
	 */
	private void criarFloat(int sinal, int[] expoente, int[] mantissa) {
		if(expoente.length != Float.TAMANHO_EXPOENTE) throw new IllegalArgumentException("O tamanho do expoene eh diferente de " + Float.TAMANHO_EXPOENTE);
		if(mantissa.length != Float.TAMANHO_MANTISSA) throw new IllegalArgumentException("O tamanho da mantissa eh diferente de " + Float.TAMANHO_MANTISSA);

		super.binario = new int[Float.MAXIMO_BITS];

		super.binario[0] = sinal;

		// Preenche a parte do expoente do binario
		for(int i = Float.COMECO_EXPOENTE; i < Float.COMECO_MANTISSA; i++) super.binario[i] = expoente[i - Float.COMECO_EXPOENTE];

		// Preenche a parte do valor do binario
		for(int i = Float.COMECO_MANTISSA; i < Float.MAXIMO_BITS; i++) super.binario[i] = mantissa[i - Float.COMECO_MANTISSA];
	}

	/*
	 * Cria um Float a partir do sinal, expoente e mantissa passados separadamente
	 */
	public Float(int sinal, int[] expoente, int[] mantissa) {
		this.criarFloat(sinal, expoente, mantissa);
	}

	/*
	 * Cria um Float a partir de um numero binario de tamanho 32
	 */
	public Float(int[] binario) {
		if(binario.length != Float.MAXIMO_BITS) throw new IllegalArgumentException("O tamanho do vetor de bits eh diferente de " + MAXIMO_BITS);

		super.binario = new int[Float.MAXIMO_BITS];

		// Ajusta o bit de sinal
		super.binario[0] = binario[0];

		// Preenche o expoente
		for(int i = Float.COMECO_EXPOENTE; i < Float.COMECO_MANTISSA; i++) super.binario[i] = binario[i];

		// Preenche a mantissa
		for(int i = Float.COMECO_MANTISSA; i < Float.MAXIMO_BITS; i++) super.binario[i] = binario[i];
	}

	/*
	 * Cria um Float a partir de um outro float
	 */
	public Float(Float numero) {
		int sinal = numero.sinal();

		int[] expoente = new int[Float.TAMANHO_EXPOENTE];
		for(int i = 0; i < expoente.length; i++) expoente[i] = numero.bit(i + Float.COMECO_EXPOENTE);

		int[] mantissa = new int[Float.TAMANHO_MANTISSA];
		for(int i = 0; i < mantissa.length; i++) mantissa[i] = numero.bit(i + Float.COMECO_MANTISSA);

		this.criarFloat(sinal, expoente, mantissa);
	}



	/*
	 * Devolve o valor do bit de sinal
	 */	
	public int sinal() {
		return super.binario[0];
	}

	/*
	 * Devolve um vetor binario representando o expoente 
	 */
	public int[] expoente() {
		int[] expoente = new int[Float.TAMANHO_EXPOENTE];

		for(int i = 0; i < expoente.length; i++) expoente[i] = super.binario[i + Float.COMECO_EXPOENTE];

		return expoente;
	}

	/*
	 * Devolve um vetor binario representando a mantissa
	 */
	public int[] mantissa() {
		int[] mantissa = new int[Float.TAMANHO_MANTISSA];

		for(int i = 0; i < mantissa.length; i++) mantissa[i] = super.binario[i + Float.COMECO_MANTISSA];

		return mantissa;
	}

	/*
	 * Retorna true se o expoente for 0, false caso contrario
	 */
	public boolean expoenteZero() {
		for(int i = Float.COMECO_EXPOENTE; i < Float.COMECO_MANTISSA; i++) if(super.binario[i] == 1) return false;
		return true;
	}

	/*
	 * Retorna true se a mantissa for 0, false caso contrario
	 */
	public boolean mantissaZero() {
		for(int i = Float.COMECO_MANTISSA; i < Float.MAXIMO_BITS; i++) if(super.binario[i] == 1) return false;
		return true;
	}



	/*
	 * ###########################################################
	 * ################# OPERADORES ARITMETICOS ##################
	 * ###########################################################
	 */



	/*
	 * Soma dois Floats.
	 */
	public static Float somar(Float a, Float b) throws OverflowException {
		// Verifica se um dos numeros eh zero e retorna o outro caso seja
		if(a.expoenteZero() && a.mantissaZero()) return new Float(b);
		if(b.expoenteZero() && b.mantissaZero()) return new Float(a);

		/* Separa o expoente de cada numero */
		int[] expoenteA = new int[Float.TAMANHO_EXPOENTE];
		for(int i = Float.COMECO_EXPOENTE; i < Float.COMECO_MANTISSA; i++) expoenteA[i - Float.COMECO_EXPOENTE] = a.bit(i);

		int[] expoenteB = new int[Float.TAMANHO_EXPOENTE];
		for(int i = Float.COMECO_EXPOENTE; i < Float.COMECO_MANTISSA; i++) expoenteB[i - Float.COMECO_EXPOENTE] = b.bit(i);

		/* Separa a mantissa de cada numero */
		int[] mantissaA = new int[Float.TAMANHO_MANTISSA + 4]; // +1 para o bit de sinal, +1 para o bit 1 da mantissa e +2 para evitar overflow da operacao de soma
		for(int i = Float.COMECO_MANTISSA; i < Float.MAXIMO_BITS; i++) mantissaA[i - Float.COMECO_MANTISSA + 4] = a.bit(i);
		//mantissaA[2] = a.bit(0); // Bit de sinal		
		mantissaA[3] = 1; // A mantissa sempre eh iniciada por um bit '1' que eh omitido na hora de armazenar, mas eh considerado na hora de fazer calculos

		int[] mantissaB = new int[Float.TAMANHO_MANTISSA + 4]; // +1 para o bit de sinal, +1 para o bit 1 da mantissa e +2 para evitar overflow da operacao de soma
		for(int i = Float.COMECO_MANTISSA; i < Float.MAXIMO_BITS; i++) mantissaB[i - Float.COMECO_MANTISSA + 4] = b.bit(i);
		//mantissaB[2] = b.bit(0); // Bit de sinal		
		mantissaB[3] = 1; // A mantissa sempre eh iniciada por um bit '1' que eh omitido na hora de armazenar, mas eh considerado na hora de fazer calculos

		/* Verifica se os expoentes sao iguais */
		boolean expoenteAMenor = false;
		boolean expoenteBMenor = false;

		for(int i = expoenteA.length - 1; i >= 0; i--) {
			expoenteAMenor = (expoenteA[i] < expoenteB[i]);
			expoenteBMenor = (expoenteB[i] < expoenteA[i]);
			if(expoenteAMenor || expoenteBMenor) break;
		}

		expoenteAMenor = NumeroBinario.menorDoQue(expoenteA, expoenteB);
		expoenteBMenor = NumeroBinario.menorDoQue(expoenteB, expoenteA);

		// Ajusta os expoentes se necessario, ou seja, se os expoentes a e b nao forem iguais
		if(expoenteAMenor || expoenteBMenor) {
			int[] expoenteMaior, expoenteEmAjuste, mantissaEmAjuste;
			
			if(expoenteAMenor) {
				expoenteMaior = expoenteB;
				expoenteEmAjuste = expoenteA;
				mantissaEmAjuste = mantissaA;
			} else {
				expoenteMaior = expoenteA;
				expoenteEmAjuste = expoenteB;
				mantissaEmAjuste = mantissaB;
			}

			/*
			  Incrementa o menor expoente e move a mantissa para a direita, ate que o menor expoente
			  se iguale ao maior expoente ou ate que a mantissa vire zero
			*/
			boolean mantissaDiferenteZero;
			while(NumeroBinario.menorDoQue(expoenteEmAjuste, expoenteMaior)) {
				try {			
					// Soma um ao expoente
					NumeroBinario.somarUm(expoenteEmAjuste, false);
				} catch(OverflowException oe) {
					throw new OverflowException("Overflow no expoente: " + oe.getMessage());
				}

				// Move a mantissa para a direita (pois o expoente foi incrementado)
				mantissaDiferenteZero = NumeroBinario.deslocarUmParaDireita(mantissaEmAjuste);

				// Se a mantissa ficou nula o outro numero eh retornado
				if(!mantissaDiferenteZero) return (expoenteAMenor ? new Float(b) : new Float(a));
			}
		}

		/*
		  Agora expoenteA e expoenteB sao iguais. Para fins de padronizacao a variavel expoenteA sera a escolhida
		  para ser o expoente do numero binario resultante da soma. Eh necessario fazer esta padronizao pelo fato
		  de o expoente ainda poder ser modificado.
		*/

		// Se 'a' for negativo transforma a mantissa em complemento de dois considerando o sinal
		if(a.sinal() == 1) {
			// Pega o complemento de 1 do valor (os bits de indice 0 e 1 que nao fazem parte da mantissa)
			for(int i = 2; i < mantissaA.length; i++) mantissaA[i] = (mantissaA[i] + 1) % 2;
			// Soma um
			NumeroBinario.somarUm(mantissaA, true);
			mantissaA[0] = 0;
			mantissaA[1] = 0;
		}

		// Se 'b' for negativo transforma a mantissa em complemento de dois considerando o sinal
		if(b.sinal() == 1) {
			// Pega o complemento de 1 do valor (os bits de indice 0 e 1 que nao fazem parte da mantissa)
			for(int i = 2; i < mantissaB.length; i++) mantissaB[i] = (mantissaB[i] + 1) % 2;
			// Soma um
			NumeroBinario.somarUm(mantissaB, true);
			mantissaB[0] = 0;
			mantissaB[1] = 0;
		}

		// Soma as mantissas considerando o sinal e o bit '1' que nao fica armazenado
		int[] somaMantissa = Inteiro.somar(new Inteiro(mantissaA, mantissaA.length), new Inteiro(mantissaB, mantissaB.length)).bits();

		// Verifica se os sinais de 'a' e 'b' sao iguais e se o sinal da soma eh diferente, ou seja, verifica se houve overflow
		if(mantissaA[2] == mantissaB[2] && mantissaA[0] != somaMantissa[2]) {
			// Caso tenha ocorrido overflow, move os bits para a direita e incrementa o expoente
			NumeroBinario.deslocarUmParaDireita(somaMantissa);
			try {
				NumeroBinario.somarUm(expoenteA, false); // expoenteA escolhido por padronizacao
			} catch(OverflowException oe) {
				throw new OverflowException("Overflow no expoente: " + oe.getMessage());
			}
		}

		// Salva o sinal pois ele pode ser perdido durante o processo de normalizacao
		int sinal = somaMantissa[2];

		// Se o resultado da soma for negativo eh necessario transforma-lo em positivo para armazena-lo
		if(sinal == 1) {
			// Pega o complemento de 1 do valor (os bits de indice 0 e 1 que nao fazem parte da mantissa)
			for(int i = 2; i < somaMantissa.length; i++) somaMantissa[i] = (somaMantissa[i] + 1) % 2;

			// Soma um
			NumeroBinario.somarUm(somaMantissa, true);
		}

		// Normaliza o resultado (o bit de sinal esta no indice 2, logo a mantissa comeca no indice 3)
		while(somaMantissa[3] != 1) {
			NumeroBinario.deslocarUmParaEsquerda(somaMantissa);
			try {
				NumeroBinario.subtrairUm(expoenteA, false); // expoenteA escolhido por padronizacao
			} catch(OverflowException oe) {
				throw new OverflowException("Underflow no expoente: " + oe.getMessage());
			}
		}
		
		int[] mantissa = new int[Float.TAMANHO_MANTISSA];
		for(int i = 0; i < mantissa.length; i++) mantissa[i] = somaMantissa[i + 4];

		return new Float(sinal, expoenteA, mantissa);
	}

	public static Float subtrair(Float a, Float b) throws OverflowException {
		// Inverte o sinal do segundo numero e efetua uma soma
		return Float.somar(a, new Float((b.sinal()+1 % 2), b.expoente(), b.mantissa()));
	}

	public static Float multiplicar(Float a, Float b) throws OverflowException {
		// Verifica se um dos numeros eh zero e retorna o outro caso seja
		if(a.expoenteZero() && a.mantissaZero()) return new Float(b);
		if(b.expoenteZero() && b.mantissaZero()) return new Float(a);

		Inteiro expoente;

		// Soma os expoentes
		try {
			expoente = Inteiro.somar(new Inteiro(a.expoente(), Float.TAMANHO_EXPOENTE + 1), new Inteiro(b.expoente(), Float.TAMANHO_EXPOENTE));
		} catch(OverflowException oe) {
			throw new OverflowException("Overflow no expoente: " + oe.getMessage());
		}

		// Subtrai a bias do expoente
		Inteiro bias = ConversaoBinarioDecimal.decimalParaInteiroBinario(Float.BIAS);
		try {
			expoente = Inteiro.subtrair(expoente, bias);
		} catch(OverflowException oe) {
			throw new OverflowException("Underflow no expoente: " + oe.getMessage());
		}

		// Multiplica

		// Normaliza

		return a;
	}

	public static Float dividir(Float a, Float b) throws OverflowException {
		// Verifica se um dos numeros eh zero e retorna o outro caso seja
		if(a.expoenteZero() && a.mantissaZero()) return new Float(b);
		if(b.expoenteZero() && b.mantissaZero()) return new Float(a);

		Inteiro expoente;

		// Soma os expoentes
		try {
			expoente = Inteiro.somar(new Inteiro(a.expoente(), Float.TAMANHO_EXPOENTE + 1), new Inteiro(b.expoente(), Float.TAMANHO_EXPOENTE));
		} catch(OverflowException oe) {
			throw new OverflowException("Overflow no expoente: " + oe.getMessage());
		}

		// Subtrai a bias do expoente
		Inteiro bias = ConversaoBinarioDecimal.decimalParaInteiroBinario(Float.BIAS);
		try {
			expoente = Inteiro.subtrair(expoente, bias);
		} catch(OverflowException oe) {
			throw new OverflowException("Underflow no expoente: " + oe.getMessage());
		}

		// Divide

		// Normaliza

		return a;
	}


}
