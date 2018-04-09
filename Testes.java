import numero_binario.*;

/**
 *
 * 
 */

public class Testes {
   
    /*
     * ###########################################################
     * ################## METODOS AUXILIARES #####################
     * ###########################################################
     */
    
    public static int[] obterBits(String stringBinaria) {
            int[] binario = new int[stringBinaria.length()];

            for(int i = 0; i < binario.length; i++) binario[i] =  (stringBinaria.charAt(i) - '0');

            return binario;
    }

    public static boolean binariosIguais(int[] a, int[] b) {
            //if(a.length != b.length) return false;

            for(int i = 0; i < a.length; i++) if(a[i] != b[i]) return false;

            return true;
    }

    public static void imprimirInteiro(Inteiro inteiro) {
        for(int i = 0; i < inteiro.tamanho(); i++) System.out.print(inteiro.bit(i));
    }

    public static void imprimirFloat(numero_binario.Float numero) {
            Inteiro expoente = numero.expoente();
            Inteiro mantissa = numero.mantissa();

            System.out.print(mantissa.sinal());
            for(int i = 1; i < expoente.tamanho(); i++) System.out.print(expoente.bit(i));
            for(int i = 2; i < mantissa.tamanho(); i++) System.out.print(mantissa.bit(i));
    }
    
    /*
     * ###########################################################
     * ################### DADOS PARA TESTE ######################
     * ###########################################################
     */
    
    static String[][] somaFloat = {
                                  // NUMERO A | NUMERO B | RESULTADO | {0 = OVERFLOW N ESPERADO, 1 = OVERFLOW ESPERADO
                                  // sEEEEEEEEVVVVVVVVVVVVVVVVVVVVVVV    sEEEEEEEEVVVVVVVVVVVVVVVVVVVVVVV    sEEEEEEEEVVVVVVVVVVVVVVVVVVVVVVV
                                  //{"01000011100000000000000000000000", "01000011100000000000000000000000", "00000000000000000000000000000000", "0"},
                                    {"00000000000000000000000000000000", "00000000000000000000000000000000", "00000000000000000000000000000000", "0"}, // 0 + 0 = 0
                                    {"01000110111111011100000000000000", "00000000000000000000000000000000", "01000110111111011100000000000000", "0"}, // 32480 + 0 = 32480 
                                    {"00000000000000000000000000000000", "01000110111111011100000000000000", "01000110111111011100000000000000", "0"}, // 0 + 32480 = 32480
                                    {"01000010010010000000000000000000", "01000001110010000000000000000000", "01000010100101100000000000000000", "0"}, // 50 + 50 = 75    
                                    {"01000001111000000000000000000000", "01000100000011011100000000000000", "01000100000101001100000000000000", "0"}, // 28 + 567 = 595  
                                    {"01000010010010000000000000000000", "11000010110010000000000000000000", "11000010010010000000000000000000", "0"}, // 50 - 100 = -50 
                                    {"00111111110000000000000000000000", "00111111000000000000000000000000", "01000000000000000000000000000000", "0"}, // 1.5 + 0.5 = 2   
                                    {"00111110101011111101001000100000", "10111110101011111101001000100000", "00000000000000000000000000000000", "0"}, // 0.3434 - 0.3434 = 0
                                    {"00111111001010100111111011111010", "10111111001100001010001111010111", "10111100110001001001101110100110", "0"}, // 0.666 - 0.69 = -0.024
                                    {"00111101001110100010111010001100", "01000101100100011000000000000000", "01000101100100011000000001011101", "0"}, // 0.045454547 + = 4656.0454
                                    {"01001000101001111011000101111010", "01000101101010100111011011001101", "01001000101010100101101101010101", "0"}, // 343435.8 + 5454.85 = 348890.66
                                    {"00000000000000000000000000000000", "00000000000000000000000000000000", "00000000000000000000000000000000", "0"}, //
                                    {"00000000000000000000000000000000", "00000000000000000000000000000000", "00000000000000000000000000000000", "0"}, //
                                    {"00000000000000000000000000000000", "00000000000000000000000000000000", "00000000000000000000000000000000", "0"}, //
                                    {"00000000000000000000000000000000", "00000000000000000000000000000000", "00000000000000000000000000000000", "0"}, //
                                    {"00000000000000000000000000000000", "00000000000000000000000000000000", "00000000000000000000000000000000", "0"}, //
                                    {"00000000000000000000000000000000", "00000000000000000000000000000000", "00000000000000000000000000000000", "0"}, //
                                    {"00000000000000000000000000000000", "00000000000000000000000000000000", "00000000000000000000000000000000", "0"}, //
                                    {"00000000000000000000000000000000", "00000000000000000000000000000000", "00000000000000000000000000000000", "0"}, //
                                    {"00000000000000000000000000000000", "00000000000000000000000000000000", "00000000000000000000000000000000", "0"}, //
                                    {"00000000000000000000000000000000", "00000000000000000000000000000000", "00000000000000000000000000000000", "0"}, //
                                    {"00000000000000000000000000000000", "00000000000000000000000000000000", "00000000000000000000000000000000", "0"}, //

                                  };
    
    /*
     * ###########################################################
     * ###################### TESTE FLOAT ########################
     * ###########################################################
     */
    
    public static void testarFloat(int operacao, String[][] dados) {

        boolean correto = false;
        int[] resultadoEsperado;
        numero_binario.Float binarioA, binarioB, resultado;
        
        int teste = 0;
        int erros = 0;
        
        for(int i = 0; i < dados.length; i++) {
            teste++;
            
            try {
                System.out.println("Teste " + teste);
                
                binarioA = new numero_binario.Float(obterBits(dados[i][0]));
		binarioB = new numero_binario.Float(obterBits(dados[i][1]));
                resultadoEsperado = obterBits(dados[i][2]);
                
                switch(operacao) {
                    case 0:
                        resultado = numero_binario.Float.somar(binarioA, binarioB);
                        break;

                    case 1:
                        resultado = numero_binario.Float.subtrair(binarioA, binarioB);
                        break;

                    case 2:
                        resultado = numero_binario.Float.multiplicar(binarioA, binarioB);
                        break;

                    case 3:
                        resultado = numero_binario.Float.dividir(binarioA, binarioB);
                        break;

                    default:
                        throw new IllegalArgumentException("Escolha uma operacao: 0, 1, 2 ou 3; nao " + operacao + "!!!!!!!");
                }
                
                imprimirFloat(binarioA);
                System.out.print(" + ");
                imprimirFloat(binarioB);
                System.out.print(" = ");
                imprimirFloat(resultado);
                System.out.println();
                
                System.out.println(ConversaoBinarioDecimal.binarioFloatParaDecimal(binarioA) + " + " +
                                   ConversaoBinarioDecimal.binarioFloatParaDecimal(binarioB) + " = " +
                                   ConversaoBinarioDecimal.binarioFloatParaDecimal(resultado)
                                  );
                
                // Verifica se o resultado obtido bate com o resultado esperado
                correto = binariosIguais(resultado.bits(), resultadoEsperado);
                
                
            } catch(OverflowException oe) {
                //resultado = new Float(new int[Inteiro.MAXIMO_BITS]);
                if(dados[i][3].equals("1")) {
                    // Overflow esperado, n houve erro
                    correto = true;
                    System.out.print("[!] Overflow esperado\t");
                } else {
                    correto = false;
                    System.out.print("[!] Overflow NÃO esperado (" + oe.getMessage() + ")\t");
                }
            } catch(Exception e) {
                System.out.println("[!] Exception nao esperada: " );
                e.printStackTrace();
                correto = false;
            } finally {
                if(!correto) erros++;
                System.out.println((correto ? "CORRETO" : "ERRADO") + "\t\t" + "Erros: " + erros + "\n\n");
            }
            
        }
        
        System.out.println("\nFim de teste\nErros: " + erros + "\tTestes: " + teste);
    }
    
    public static void main(String[] args) {
        testarFloat(0, somaFloat);
    }
}
