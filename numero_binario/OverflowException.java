package numero_binario;

public class OverflowException extends Exception {

        // Binario resultande da soma
        private Inteiro inteiro;
        
        // Bit que sobrou
        private int excesso;
    
	public OverflowException(String mensagem, Inteiro inteiro, int excesso) {
		super(mensagem);
                this.inteiro = inteiro;
                this.excesso = excesso;
	}
        
        public OverflowException(String mensagem) {
            super(mensagem);
            this.inteiro = null;
            this.excesso = -1;
        }

        public Inteiro inteiro() {
            return this.inteiro;
        }
        
        public int excesso() {
            return this.excesso;
        }
}
