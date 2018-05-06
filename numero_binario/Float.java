package numero_binario;

/**
  Esta classe representa um numero com ponto flutuante em binario no formato IEEE754. Um atributo inteiro representa o sinal,
  enquanto outros dois atributos recebem dois objetos Inteiro para armazenar o expoente e a mantissa.
*/

public class Float {
    
    // Numero maximo de bits que podem ser usados para representar um binario inteiro
    public static final int MAXIMO_BITS = 32;

    // Numero de bits usados para representar o valor do expoente
    public static final int BITS_EXPOENTE = 8;

    // Numero de bits usados para representar a mantissa sinalizada e com o bit mais significativo
    public static final int BITS_MANTISSA = MAXIMO_BITS - BITS_EXPOENTE;

    // Tamanho do expoente (incluindo o bit de sinal do expoente)
    public static final int TAMANHO_EXPOENTE = BITS_EXPOENTE + 1; // 8 bits de valor, 1 bit de sinal

    // Tamanho da mantissa incluindo os bits de valor, o bit de sinal e o bit mais siginificativo
    public static final int TAMANHO_MANTISSA = BITS_MANTISSA + 1; // 24 bits de valor, 1 bit de sinal

    public static final int BIAS = (int)Math.pow(2, Float.BITS_EXPOENTE - 1) - 1; // BIAS = [(2^k) - 1], onde k = (numeros de bits) - 1

    private int sinal;
    private Inteiro expoente;
    private Inteiro mantissa;

    /*
     * Cria um numero float distribuindo os bits de forma adequada. O expoente e a mantissa ja devem estar em complemento de dois.
     */
    private void criarFloat(int[] expoente, int[] mantissa) {
        if(expoente.length != BITS_EXPOENTE) throw new IllegalArgumentException("O tamanho do expoente eh diferente de " + BITS_EXPOENTE);
        if(mantissa.length != BITS_MANTISSA) throw new IllegalArgumentException("O tamanho da mantissa eh diferente de " + BITS_MANTISSA);

        this.expoente = new Inteiro(expoente);
        this.mantissa = new Inteiro(mantissa);
    }

    /*
     * Cria um Float a partir do sinal, expoente e mantissa passados separadamente
     */
    /*public Float(int sinal, int[] expoente, int[] mantissa) {
        
        this.criarFloat(expoente, mantissa);
    }*/

    /*
     * Cria um numero Float a partir de dois objetos Inteiros
     */
    public Float(int sinal, Inteiro expoente, Inteiro mantissa) {
        if(expoente.tamanho() != TAMANHO_EXPOENTE) throw new IllegalArgumentException("O tamanho do expoente eh diferente de " + TAMANHO_EXPOENTE);
        if(mantissa.tamanho() != TAMANHO_MANTISSA) throw new IllegalArgumentException("O tamanho da mantissa eh diferente de " + TAMANHO_MANTISSA);

        this.sinal = sinal;
        this.expoente = new Inteiro(expoente);
        this.mantissa = new Inteiro(mantissa);
    }

    /*
     * Cria um Float a partir de um outro float
     */
    public Float(Float numero) {
        this.sinal = numero.sinal();
        this.expoente = numero.expoente();
        this.mantissa = numero.mantissa();
    }

    /*
     * Cria um Float a partir de um numero binario float no formato IEEE-754
     */
    public Float(int[] binario) {
        if(binario.length != Float.MAXIMO_BITS) throw new IllegalArgumentException("O tamanho do vetor de bits eh diferente de " + MAXIMO_BITS);

        // Separa o expoente
        int[] expoente = new int[TAMANHO_EXPOENTE];
        for(int i = 1; i < expoente.length; i++) expoente[i] = binario[i];


        /* Pega a mantissa */
        int[] mantissa = new int[Float.TAMANHO_MANTISSA];

        mantissa[1] = 1; // Define o valor do bit mais significativo
        for(int i = 2; i < mantissa.length; i++) mantissa[i] = binario[i + 7];

        this.sinal = binario[0];
        this.expoente = new Inteiro(expoente);
        this.mantissa = new Inteiro(mantissa);
    }



    /*
     * Transforma o objeto Float em um vetor binario de float no formato IEEE-754
     */
    public int[] bits() {
        int[] binario = new int[MAXIMO_BITS];

        binario[0] = this.sinal(); // Bit de sinal
        for(int i = 1; i < TAMANHO_EXPOENTE; i++) binario[i] = this.expoente.bit(i); // Expoente
        for(int i = 2; i < TAMANHO_MANTISSA; i++) binario[i + 7] = this.mantissa.bit(i); // Mantissa
        return binario;
    }

    /*
     * Devolve o valor do bit de sinal, que fica armazenado na mantissa
     */	
    public int sinal() {
        //return this.mantissa.sinal();
        return this.sinal;
    }

    /*
     * Devolve um objeto Inteiro representando o expoente 
     */
    public Inteiro expoente() {
        return new Inteiro(this.expoente);
    }

    /*
     * Devolve um objeto Inteiro representando a mantissa
     */
    public Inteiro mantissa() {
        return new Inteiro(this.mantissa);
    }

    /*
     * Retorna true se o expoente for 0, false caso contrario
     */
    public boolean expoenteZero() {
        return this.expoente.zero();
    }

    /*
     * Retorna true se a mantissa for 0, false caso contrario
     */
    public boolean mantissaZero() {
        for(int i = 2; i < this.mantissa.tamanho(); i++) if(this.mantissa.bit(i) == 1) return false;
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
        Inteiro expoenteA = a.expoente();
        Inteiro expoenteB = b.expoente();

        /* Separa a mantissa de cada numero */
        Inteiro mantissaA = a.mantissa();
        Inteiro mantissaB = b.mantissa();


        /* Verifica se os expoentes sao iguais */
        boolean expoenteAMenor;
        boolean expoenteBMenor;

        // Eh necessario chamar o metodo para comparar duas vezes pois os expoentes podem ser iguais e por isso as duas variaveis podem ser false
        expoenteAMenor = expoenteA.menorDoQue(expoenteB);
        expoenteBMenor = expoenteB.menorDoQue(expoenteA);

        // Ajusta os expoentes se necessario, ou seja, se os expoentes a e b nao forem iguais
        if(expoenteAMenor || expoenteBMenor) {
            
            Inteiro expoenteMaior, expoenteEmAjuste, mantissaEmAjuste;

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
            while(expoenteEmAjuste.menorDoQue(expoenteMaior)) {
                try {			
                    // Soma um ao expoente
                    expoenteEmAjuste.somarUm();
                } catch(OverflowException oe) {
                    throw new OverflowException("Overflow no expoente: " + oe.getMessage());
                }

                // Move a mantissa para a direita (pois o expoente foi incrementado)
                mantissaDiferenteZero = mantissaEmAjuste.deslocarUmParaDireita();

                // Se a mantissa ficou nula o outro numero eh retornado
                if(!mantissaDiferenteZero) return (expoenteAMenor ? new Float(b) : new Float(a));
            }
        }

        /*
          Agora expoenteA e expoenteB sao iguais. Para fins de padronizacao a variavel expoenteA sera a escolhida
          para ser o expoente do numero binario resultante da soma. Eh necessario fazer esta padronizao pelo fato
          de o expoente ainda poder ser modificado.
        */

        /*
          Se um dos numeros for negativo, entao o inverso da mantissa em complemento de dois eh usado para fazer
          a soma das mantissas.
        */
        if(a.sinal() == 1) mantissaA.complementoDeDois();
        if(b.sinal() == 1) mantissaB.complementoDeDois();

        Inteiro somaMantissa;
        
        try {
            // Soma as mantissas
            somaMantissa = Inteiro.somar(mantissaA, mantissaB);
        } catch (OverflowException oe) {
            // Caso tenha ocorrido overflow, move os bits para a direita e incrementa o expoente
            somaMantissa = oe.inteiro();
            somaMantissa.deslocarUmParaDireita(oe.excesso()); // Desloca para a esquerda e insere no inicio o bit que sobrou

            try {
                    expoenteA.somarUm(); // expoenteA escolhido por padronizacao
            } catch(OverflowException oeExpoente) {
                    throw new OverflowException("Overflow no expoente: " + oeExpoente.getMessage());
            }

        }

        // Salva o sinal da mantissa resultante da soma
        int sinalSoma = somaMantissa.bit(0);
        
        /*
          Se o resultado da soma for negativo, entao eh necessario tomar o complemento de dois do numero para 
          relizar o processo de normalizacao.
        */
        if(sinalSoma == 1) somaMantissa.complementoDeDois();

        // Normaliza o resultado
        while(somaMantissa.bit(1) != 1) {
            somaMantissa.deslocarUmParaEsquerda();
            try {
                expoenteA.subtrairUm(); // expoenteA escolhido por padronizacao
            } catch(OverflowException oe) {
                throw new OverflowException("Underflow no expoente: " + oe.getMessage());
            }
        }

        return new Float(sinalSoma, expoenteA, somaMantissa);
    }

    public static Float subtrair(Float a, Float b) throws OverflowException {
        // Inverte o sinal do segundo numero e efetua uma soma
        return Float.somar(a, new Float((b.sinal()+1 % 2), b.expoente(), b.mantissa()));
    }

    public static Float multiplicar(Float a, Float b) throws OverflowException {
        
        
        // Verifica se um dos numeros eh zero e retorna o outro caso seja
        if(a.expoenteZero() && a.mantissaZero()) return new Float(b);
        if(b.expoenteZero() && b.mantissaZero()) return new Float(a);

        int sinal = 1;
        if (a.sinal() == b.sinal()) sinal = 0;
        
        
        Inteiro somaExpoentes;
        Inteiro expoente_a = new Inteiro (a.expoente.bits(), TAMANHO_EXPOENTE + 1); //passar Inteiro para vetor e criar novo inteiro com 1 bit a mais
        Inteiro expoente_b = new Inteiro (b.expoente.bits(), TAMANHO_EXPOENTE + 1);

        //Soma os expoentes
        try {
        somaExpoentes = Inteiro.somar(expoente_a, expoente_b);
        } catch(OverflowException oe) {
            throw new OverflowException("Overflow no expoente: " + oe.getMessage());
        }
        
        //Subtrai a bias do expoente
        Inteiro bias = ConversaoBinarioDecimal.decimalInteiroParaInteiroBinario(Float.BIAS, somaExpoentes.tamanho());
        try {
            
            somaExpoentes = Inteiro.subtrair(somaExpoentes, bias);
        } catch(OverflowException oe) {
            throw new OverflowException("Underflow no expoente: " + oe.getMessage());
        }
        
        
        // Multiplica as mantissas
       
       Inteiro mantissa; 
        
       
            a.mantissa.imprimir();
            b.mantissa.imprimir();
            Inteiro mantissa_a = new Inteiro (a.mantissa());
            Inteiro mantissa_b = new Inteiro (b.mantissa());
            mantissa = Inteiro.multiplicar(mantissa_a, mantissa_b);
       
        

        
        // Normaliza
        while(mantissa.bit(1) != 1) {
                mantissa.deslocarUmParaEsquerda();
                try {
                    somaExpoentes.subtrairUm(); // expoenteA escolhido por padronizacao
                } catch(OverflowException oe) {
                        throw new OverflowException("Underflow no expoente: " + oe.getMessage());
                }
        }

        return new Float (sinal, somaExpoentes, mantissa);
    }

    public static Float dividir(Float a, Float b) throws OverflowException {
        
        // Verifica se um dos numeros eh zero e retorna o outro caso seja
        if(a.expoenteZero() && a.mantissaZero()) return new Float(b);
        if(b.expoenteZero() && b.mantissaZero()) return new Float(a);

        Inteiro expoente;

        //Subtrai os expoentes
        try {
            expoente = Inteiro.subtrair(new Inteiro(a.expoente.bits(), TAMANHO_EXPOENTE), new Inteiro(b.expoente.bits(), TAMANHO_EXPOENTE));
            
        } catch(OverflowException oe) {
                throw new OverflowException("Overflow no expoente: " + oe.getMessage());
        }

        // Soma a bias do expoente
        Inteiro bias = ConversaoBinarioDecimal.decimalInteiroParaInteiroBinario(Float.BIAS, expoente.tamanho());
        try {
                expoente = Inteiro.somar(expoente, bias);
                
        } catch(OverflowException oe) {
                throw new OverflowException("Underflow no expoente: " + oe.getMessage());
        }

        // Divide
        Inteiro divisao;
        try {
            System.out.println(ConversaoBinarioDecimal.binarioInteiroParaDecimal(a.mantissa()));
            System.out.println(ConversaoBinarioDecimal.binarioInteiroParaDecimal(b.mantissa()));
            divisao = Inteiro.dividir(a.mantissa(), b.mantissa());
            System.out.println(ConversaoBinarioDecimal.binarioInteiroParaDecimal(divisao));
            
        } catch (OverflowException oe) {
            throw new OverflowException("Underflow na divisao: " + oe.getMessage());
        }

        // Normaliza
        while(divisao.bit(1) != 1) {
            divisao.deslocarUmParaEsquerda();
            try {
                expoente.subtrairUm(); 
            } catch(OverflowException oe) {
                    throw new OverflowException("Underflow no expoente: " + oe.getMessage());
            }
        }
        return new Float (0, expoente, divisao);
    }


}
