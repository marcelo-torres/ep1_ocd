package numero;

public class Numero {

	public static Inteiro somar(Inteiro a, Inteiro b) throws OverflowException {
		int[] c = new int[(a.tamanho() > b.tamanho() ? a.tamanho() : b.tamanho())];

		// Excesso de uma soma (no caso de 1 + 1 excesso = 1)
		int excesso = 0;

		// Soma os bits
		for(int i = Inteiro.BITS_INTEIRO - 1; i >= 0; i--) {
			c[i] = (a.bit(i) + b.bit(i) + excesso) % 2;
			excesso = (a.bit(i) + b.bit(i) + excesso) / 2;
		}

		// Se o numero 'a' e o numero 'b' possuem o mesmo sinal, entao houve um 'overflow' se, e somente se, 
		// o sinal do numero resultante da soma for diferente
		if(a.bit(0) == b.bit(0) && a.bit(0) != c[0]) throw new OverflowException("Overflow"); 

		return new Inteiro(c);
	}

	public static Inteiro subtrair(Inteiro a, Inteiro b) throws OverflowException {
		return a;
	}

	public static Inteiro multiplicar(Inteiro a, Inteiro b) throws OverflowException {
		// Usar Booth’s algorithm
		return a;
	}

	public static Inteiro dividir(Inteiro a, Inteiro b) throws OverflowException {
		return a;
	}



	public static Float somar(Float a, Float b) throws OverflowException {
		return a;
	}

	public static Float subtrair(Float a, Float b) throws OverflowException {
		return a;
	}

	public static Float multiplicar(Float a, Float b) throws OverflowException {
		return a;
	}

	public static Float dividir(Float a, Float b) throws OverflowException {
		return a;
	}

}
