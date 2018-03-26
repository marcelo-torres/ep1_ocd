package numero;

public class Numero {

	public static Inteiro somar(Inteiro a, Inteiro b) {
		int[] c = new int[(a.tamanho() > b.tamanho() ? a.tamanho() : b.tamanho())];

		int excesso = 0;

		for(int i = Inteiro.BITS_INTEIRO - 1; i >= 0; i--) {
			c[i] = (a.bit(i) + b.bit(i) + excesso) % 2;
			excesso = (a.bit(i) + b.bit(i) + excesso) / 2;
		}

		return new Inteiro(c);
	}

	public static Inteiro subtrair(Inteiro a, Inteiro b) {
		return a;
	}

	public static Inteiro multiplicar(Inteiro a, Inteiro b) {
		return a;
	}

	public static Inteiro dividir(Inteiro a, Inteiro b) {
		return a;
	}



	public static Float somar(Float a, Float b) {
		return a;
	}

	public static Float subtrair(Float a, Float b) {
		return a;
	}

	public static Float multiplicar(Float a, Float b) {
		return a;
	}

	public static Float dividir(Float a, Float b) {
		return a;
	}

}
