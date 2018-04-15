package numero_binario;

/*
  Esta classe representa um numero inteiro sinalizado em binario, onde o primeiro bit eh o de sinal. Todas as operacoes envolvendo
  instancias dessa classe assumem que o binario armazenado esta em complemento de dois. 
*/

public class Inteiro {

    // Numero maximo de bits que pode ser usado para representar um inteiro
    public static final int MAXIMO_BITS = 32;

    private int[] binario;

    /*
     * Inicializa o vetor de bits a partir de um numero binario no formato de complemento de dois
     */
    private void criarInteiro(int[] binario, int numeroBits) {
        if(binario.length > MAXIMO_BITS || numeroBits > MAXIMO_BITS) throw new IllegalArgumentException("Nao eh possivel usar mais do que " + MAXIMO_BITS + " bits");
        if(binario.length > numeroBits) throw new IllegalArgumentException("O numero de bits indicado eh menor do que o tamanho do vetor de bits");

        this.binario = new int[numeroBits];

        // Diferenca entre a quantidade de bits do vetor passado e a quantidade de bits definida para o Inteiro que esta sendo criado
        int diferenca = this.binario.length - binario.length;

        // Ajusta o bit de sinal
        this.binario[0] = binario[0];

        // Ajusta os bits de valor
        for(int i = binario.length - 1; i > 0; i--) this.binario[i + diferenca] = binario[i];

        // Caso o numero seja negativo ele deve ser completado com bits de valor '1' (pois esta em complemento de dois)
        if(this.binario[0] == 1) for(int i = 1; i <= diferenca; i++) this.binario[i] = 1;	
    }

    /*
     * Cria um Inteiro com um numero binario de tamanho indicado no paramatro
     */
    public Inteiro(int[] binario, int numeroBits) {
        this.criarInteiro(binario, numeroBits);
    }

    /*
     * Cria um Inteiro com um numero binario do tamanho do binario indicado
     */
    public Inteiro(int[] binario) {
        this.criarInteiro(binario, binario.length);
    }

    /*
     * Cria um Inteiro com base em outro inteiro
     */
    public Inteiro(Inteiro numero) {
        int[] binario = new int[numero.tamanho()];

        for(int i = 0; i < binario.length; i++) binario[i] = numero.bit(i);
        this.criarInteiro(binario, binario.length);
    }



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
     * Devolve o valor do bit de sinal
     */	
    public int sinal() {
        return this.binario[0];
    }

    /*
     * Verifica se o numero eh zero
     */
    public boolean zero() {
        for(int i = 0; i < this.binario.length; i++) if(this.binario[i] != 0) return false;
        return true;
    }



    /*
     * Desloca o binario do Inteiro para a direita preenchendo o espaco vazio a esquerda com 0's ou 1's, dependendo do sinal.
     * Retorna true caso o numero resultante seja nao nulo e false caso seja nulo (igual a zero)
     */
    public boolean deslocarUmParaDireita() {
        boolean binarioDiferenteZero = false;
        for(int i = this.binario.length - 1; i > 0; i--) {
                this.binario[i] = this.binario[i - 1];
                if(this.binario[i] == 1) binarioDiferenteZero = true;
        }
        /* 
          Nao altera o primeiro bit pois eh o bit de sinal. Caso o bit de sinal seja 1 os espacos serao preenchidos
          com o bit 1, conforme um numero negativo em complemento de 2 deve ser
        */
        //this.binario[0] = 0;

        return binarioDiferenteZero;
    }

    /*
     * Desloca os bits do inteiro para a direita e coloca o bit indicado na primeira posicao
     */
    public boolean deslocarUmParaDireita(int bit) {
        boolean binarioDiferenteZero = this.deslocarUmParaDireita();
        this.binario[0] = bit;
        return binarioDiferenteZero;
    }
    
    /*
     * Desloca o binario do Inteiro para a esquerda preenchendo o espaco vazio a direita com 0's e mantendo o bit de sinal.
     * Retorna true caso o numero resultado seja nao nulo e false caso seja nulo (igual a zero)
     */
    public boolean deslocarUmParaEsquerda() {
        boolean binarioDiferenteZero = false;
        for(int i = 1; i < this.binario.length - 1; i++) {
                this.binario[i] = this.binario[i + 1];
                if(this.binario[i] == 1) binarioDiferenteZero = true;
        }
        this.binario[this.binario.length - 1] = 0;

        return binarioDiferenteZero;
    }

    /*
     * Transforma o inteiro atual em complemento de dois
     */
    public void complementoDeDois() {
        for(int i = 0; i < this.binario.length; i++) this.binario[i] = (this.binario[i] + 1) % 2; // Inverte os sinais
        try {
            this.somarUm();
        } catch(OverflowException oe) {
            // Ignora esta excessao pois overflow neste caso eh irrelevante
        }
    }
    
    
    
    /*
     * Verifica se um Inteiro eh menor do que o outro de mesmo tamanho
     */
    public boolean menorDoQue(Inteiro inteiro) {
        if(this.tamanho() != inteiro.tamanho()) return false;

        // Verifica se os sinais sao iguais
        if(this.sinal() > inteiro.sinal()) return true; // Este inteiro eh menor do que o outro
        else if(this.sinal() < inteiro.sinal()) return true; // Este inteiro eh maior do que o outro
        
        for(int i = 1; i < this.tamanho(); i++) {
            if(this.bit(i) < inteiro.bit(i)) return true; // Ente inteiro eh menor do que o outro
            else if(this.bit(i) > inteiro.bit(i)) return false; // Este inteiro eh maior do que o outro
        }

        // Sao iguais
        return false;
    }
    
     public void imprimir (){
        
        for (int i = 0; i<this.tamanho(); i++)
            System.out.print(this.bit(i));
        
        System.out.print("\t");
        
    }
    
    
    /*
     * ###########################################################
     * ################# OPERADORES ARITMETICOS ##################
     * ###########################################################
     */



    /*
     * Soma um unidade ao Inteiro. O numero somado eh o "um" positivo em complemento de dois,
     * logo para verificar se houve overflow basta verificar se o numero Inteiro era positivo
     * e virou negativo apos a soma.
     */
    public void somarUm() throws OverflowException {
        int sinalAnterior = this.sinal();
        int valorAnterior;
        int excesso = 1;
        for(int i = this.binario.length - 1; i >= 0 && excesso == 1; i--) {
                valorAnterior = this.binario[i]; // Salva o valor do bit antes da modificacao
                this.binario[i] = (this.binario[i] + excesso) % 2;
                excesso = (valorAnterior + excesso) / 2; // Calcula o excesso usando o valor do bit antes da modificacao
        }

        // Se houver mudanca de sinal apos somar numeros de mesmo sinal, entao houve overflow (soma um numero
        if(sinalAnterior == 0 && this.sinal() != sinalAnterior) throw new OverflowException("Nao eh possivei incrementar uma unidade");
    }

    /*
     * Subtrai uma unidade do Inteiro. O numero "um" negativo em complemento de dois eh somado ao Inteiro,
     * logo para verificar se houve overflow basta verificar se o numero Inteiro era negativo
     * e virou positivo apos a operacao.
     */
    public void subtrairUm() throws OverflowException {
        int sinalAnterior = this.sinal();
        int valorAnterior;
        int excesso = 0;
        for(int i = this.binario.length - 1; i >= 0; i--) {
                valorAnterior = this.binario[i];
                this.binario[i] = (this.binario[i] + 1 + excesso) % 2;
                excesso = (valorAnterior + 1 + excesso) / 2;
        }

        // Se houver mudanca de sinal ao subtrari numeros de mesmo sinal, entao houve overflow (soma um numero
        if(sinalAnterior == 1 && this.sinal() != sinalAnterior) throw new OverflowException("Nao eh possivel decrementar uma unidade");
    }

    
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
        if(a.sinal() == b.sinal() && a.sinal() != c[0]) throw new OverflowException("A soma ultrapassa o limite do inteiro", new Inteiro(c, c.length), excesso); 

        return new Inteiro(c, c.length);
    }

    
    /*
     * Método de soma que ignora o overflow para auxiliar a multiplicacao
    */
    public static Inteiro somaAux (Inteiro a, Inteiro b){
        
        int [] soma = new int [a.tamanho()+1];
        
        // Excesso de uma soma (no caso de 1 + 1 excesso = 1)
        int excesso = 0;

        // Soma os bits
        for(int i = a.tamanho() - 1; i > 0; i--) {
            
            soma[i+1] = (a.bit(i) + b.bit(i) + excesso) % 2;
            excesso = (a.bit(i) + b.bit(i) + excesso) / 2;
        }
        
        int [] aux = new int [a.tamanho()];
        for (int i = 0; i < aux.length; i++){
            
            aux[i] = soma[i+1];
            
        }
 
        
        return new Inteiro(aux);
    }
    
    
    
    
    
    public static Inteiro subtrair(Inteiro a, Inteiro b) throws OverflowException {
        
        //Pegando complemento de dois de b
	Inteiro b_comp2 = new Inteiro (b);
        b_comp2.complementoDeDois();  

	//Somando a e b
	return new Inteiro(somar(a,b_comp2));
    }
       

   
    
    /*
     * Realiza a multiplicação de dois binarios seguindo o algoritmo de Booth
     */
    public static Inteiro multiplicar(Inteiro M, Inteiro Q) throws OverflowException {
              
        // A variável 'A' deve ser inicializada com 0s
        int [] aux = new int [M.tamanho()];
        Inteiro A = new Inteiro (aux);
        
        int Q_1 = 0;
        
        
        int contador = Q.tamanho();

        while (contador>0) {
                        
            // Ultimo bit de Q é comparado com Q_1
            if (Q.bit(Q.tamanho()-1) != Q_1){

                if (Q_1 == 0)
                    A = subtrair(A,M);
                else 
                    A = somaAux(A,M);

            }
            
            // Deslocamento a direita de A, Q e Q_1
            Q_1 = Q.bit(Q.tamanho()-1);
            Q.deslocarUmParaDireita(A.bit(A.tamanho()-1));
            A.deslocarUmParaDireita();
            

            contador--;
        }    
            
        //O resultado será o conjunto de bits de A e Q
        int [] produto = new int [Q.tamanho()+A.tamanho()];
        for (int i = 0; i<A.tamanho(); i++)
            produto[i] = A.bit(i);
        for (int i = 0; i<Q.tamanho(); i++)
            produto[i+Q.tamanho()] = Q.bit(i); 
        
        Inteiro prod = new Inteiro (produto);
        
        return prod;
    }

    public static Inteiro dividir(Inteiro a, Inteiro b) throws OverflowException {
        return a;
    }

}
