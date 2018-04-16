package numero_binario;

public class ConversaoBinarioDecimal {

    public static final int TAMANHO_MAXIMO_INTEIRO = 32;



    /*
     * Converte um vetor binario em um numero decimal inteiro.
     * Esse metodo suporta conversor de no maximo 32 bits devido a variavel usada para converter o valor ser
     * um inteiro que possui 32 bits.
     */
    /*public static int binarioParaDecimal(int[] binario) {
        if(binario.length > TAMANHO_MAXIMO_INTEIRO) throw new IllegalArgumentException("O numero nao pode ter mais do que " + ConversaoBinarioDecimal.TAMANHO_MAXIMO_INTEIRO + " bits");

        int potencia = 1;
        int soma = 0;
        for(int i = binario.length - 1; i > 0; i--) { // Se o numero for sinalizado o bit de sinal nao eh contado
            soma += (potencia * binario[i]);
            potencia *= 2;
        }

        if(binario[0] == 1) soma *= -1;

        return soma;
    }*/

    /*
     * Metodo para converter um objeto Inteiro de um numero binario em um numero decimal inteiro.
     * Esse metodo suporta conversor de no maximo 32 bits devido a variavel usada para converter o valor ser
     * um inteiro que possui 32 bits.
     */
    public static int binarioInteiroParaDecimal(Inteiro numero) {
        if(numero.tamanho() > TAMANHO_MAXIMO_INTEIRO) throw new IllegalArgumentException("O numero nao pode ter mais do que " + ConversaoBinarioDecimal.TAMANHO_MAXIMO_INTEIRO + " bits");

        int potencia = 1;
        int soma = 0;
        for(int i = numero.tamanho() - 1; i > 0; i--) {
            soma += potencia * numero.bit(i);
            potencia *= 2;
        }

        soma -= numero.bit(0) * potencia;
        return soma;
    }	

    /*
     * Tranforma um numero inteiro no sistema decimal em um numero binario inteiro
     */
    public static Inteiro decimalInteiroParaInteiroBinario(int numero, int numeroBits) throws OverflowException {
        boolean negativo = (numero < 0);

        // Transforma o numero em positivo
        if(negativo) numero *= -1;

        int expoente = 0;
        int potencia = 1;
        int soma = 1;
        while(soma < numero) {
            expoente++;
            potencia *= 2;
            soma += potencia;
        }

        if(expoente + 2 > numeroBits) throw new OverflowException("Eh necessario mais bits do que o indicado para criar o numero");
        if(expoente + 2 > Inteiro.MAXIMO_BITS) throw new OverflowException("Nao eh possivel converter o numero " + numero + " para binario, eh necessario mais do que " + Inteiro.MAXIMO_BITS + "bits");

        // Incrementa 1 para ajustar o vetor de acordo com o expoente e mais 1 para o bit de sinal
        int[] binario = new int[numeroBits];

        // Ajuda o bit de sinal
        binario[0] = (negativo ? 1 : 0);

        // Se for negativo armazena o binario em complemento de 2
        if(negativo) {
            // Insere os bits em complemento de 1
            for(int j = binario.length - 1; j >= 0; j--) {
                //binario[j] = ((numero % 2) == 1 ? 0 : 1); // Inverte o valor dos bits
                binario[j] = ((numero % 2) + 1) % 2; // Inverte o valor dos bits
                numero /= 2;
            }

            // Soma um no binario para virar complemento de dois
            //NumeroBinario.somarUm(binario, true);
            int anterior = 0;
            int excesso = 1;
            for(int i = binario.length - 1; i >= 0 && excesso == 1; i--) {
                anterior = binario[i]; // Salva o valor do bit antes da modificacao
                binario[i] = (binario[i] + excesso) % 2;
                excesso = (anterior + excesso) / 2; // Calcula o excesso usando o valor do bit antes da modificacao
            }
        } else {
            // Insere os bits
            for(int j = binario.length - 1; j > 0; j--) {
                binario[j] = (numero % 2);
                numero /= 2;
            }
        }

        return new Inteiro(binario);
    }

    /*
     * Transforma um objeto Float binario em um numero float decimal 
     * Baseado no passo-a-passo: http://class.ece.iastate.edu/arun/CprE281_F05/ieee754/ie5.html
     */
    public static float binarioFloatParaDecimal(Float numero) {
        int expoente = binarioInteiroParaDecimal(numero.expoente()) - Float.BIAS;

        int[] mantissaBinario = numero.mantissa().bits();

        // Converte a 
        double potencia = 2;
        float mantissa = 0;
        for(int i = 2; i < mantissaBinario.length; i++) {
            mantissa += (mantissaBinario[i] / potencia);
            potencia *= 2;
        }

        // Se a mantissa for zero e o expoente for 0, por convencao o valor do binario eh 0
        if(mantissa == 0 && expoente == -Float.BIAS) return 0;

        return (float)((1 + mantissa) * Math.pow(2, expoente) * (numero.sinal() == 1 ? -1 : 1));
    }

    /*
     * Converte um numero float em um objeto Float.
     * Metodo baseado em: https://stackoverflow.com/questions/5157664/java-how-to-convert-a-string-of-binary-values-to-a-float-and-vice-versa
     */
    public static Float decimalFloatParaFloatBinario(float numero) {
        // Se o numero for zero entao eh criado um objeto Float que vale 0 de acordo com a convencao de 0 do formato IEEE 754
        if(numero == 0) return new Float(0, new Inteiro(new int[numero_binario.Float.TAMANHO_EXPOENTE]), new Inteiro(new int[numero_binario.Float.TAMANHO_MANTISSA]));
        
        int sinal;
        
        /*
          Pega o sinal do numero e o transforma em positivo caso seja negativo, pois o metodo que esta sendo usado
          para realizar a conversao para binario em formato IEEE 754 omite o bit sinal quando o numero eh positivo,
          entao para simplificar o codigo o numero eh padronizado como positivo.
        */
        if(numero < 0) {
            sinal = 1;
            numero *= -1;
        } else sinal = 0;

        String numeroBinario =  Integer.toBinaryString(java.lang.Float.floatToIntBits(numero));
        //System.out.print(numeroBinario.length() + " " + (32 - numeroBinario.length()) + " ");
        for(int i = 0; i < 33 - numeroBinario.length(); i++) numeroBinario = "0" + numeroBinario;
        System.out.print("(" + numeroBinario + ")\t");
        
        int[] expoente = new int[numero_binario.Float.TAMANHO_EXPOENTE];
        for(int i = 1; i < expoente.length; i++) expoente[i] = (numeroBinario.charAt(i) - '0');

        int[] mantissa = new int[numero_binario.Float.TAMANHO_MANTISSA];
        for(int i = 2; i < mantissa.length; i++) mantissa[i] = (numeroBinario.charAt(i + 7) - '0');
        mantissa[1] = 1; // Ajusta o valor do bit mais significativo
 
        /*System.out.print("[" + sinal);
        for(int i = 1; i < expoente.length; i++) System.out.print(expoente[i]);
        for(int i = 2; i < mantissa.length; i++) System.out.print(mantissa[i]);
        System.out.print("]\t");*/
        
        return new Float(sinal, new Inteiro(expoente), new Inteiro(mantissa));
    }

}
