import java.util.Scanner;
import java.util.InputMismatchException;

import numero_binario.*;

public class CalculadoraBinaria {
    
    /*
     * Recebe um numero binario na sinalizado e o retorna como vetor de bits no qual
     * o primeiro bit eh o de sinal e sempre vale 0, isto eh, retorna sempre um
     * numero positivo.
     */
    public static int[] vetorBits(String numero) {
        
        // Adiciona um para o bit de sinal
        int[] vetor = new int[numero.length() + 1]; 
        
        // Ignora o bit de sinal
        for(int i = 0; i < numero.length(); i++) {
            if(numero.charAt(i) != '0' && numero.charAt(i) != '1') return new int[0];
            vetor[i+1] = (numero.charAt(i) - '0');
        }
        
        return vetor;
    }
    
    public static void imprimirResultado(Inteiro numero) {
        System.out.print("Resultado: ");
        for(int i = 0; i < numero.tamanho(); i++) System.out.print(numero.bit(i));
        System.out.println("\n");
        //System.out.println("\tDecimal: (" + ConversaoBinarioDecimal.binarioInteiroParaDecimal(numero) + ")\n");
    }
    
    public static void imprimirResultado(numero_binario.Float numero) {
        Inteiro expoente, mantissa;
        
        System.out.println("# @@@@#### @@@@####@@@@####@@@@###");
        System.out.print(numero.sinal() + " ");
        
        expoente = numero.expoente();
        for(int i = 1; i < expoente.tamanho(); i++) System.out.print(expoente.bit(i)); // Comeca do 1 para ignorar o bit de sinal
        System.out.print(" ");
        
        mantissa = numero.mantissa();
        for(int i = 2; i < mantissa.tamanho(); i++) System.out.print(mantissa.bit(i)); // Comeca do 2 para ignorar o bit de sinal e o bit mais significativo da mantissa
        System.out.println("\tDecimal aproximado: (" + ConversaoBinarioDecimal.binarioFloatParaDecimal(numero) + ")\n");
    }
    
    
    
    private Scanner entradaLinha;
    private Scanner entrada;
    
    boolean modo;
    
    private String modo() {
                if(this.modo) return ("FLOAT\n");
                else return ("INTEGER\n");
    }
    
    private boolean opcaoValida(char opcao) {
        return (opcao == '+' || opcao == '-' || opcao == '*' || opcao == '/') || (opcao == 'm' || opcao == 'S');
    }
    
    public char menuOpcoes() {
        char opcao = ' ';
        do {
            System.out.print( "Modo: " + this.modo()
                            + "Escolha um operador ou operacao:\n"
                            + "   {+ - * /} - Operador\n"
                            + "   m - Alterar modo\n"
                            + "   S - Sair"
                            + "\n-> ");
            
            opcao = entradaLinha.nextLine().charAt(0);
        } while(!this.opcaoValida(opcao));
        
        return opcao;
    }
    
    public void iniciar() {
    
        char opcao;
        
        do {
        
            opcao = this.menuOpcoes();
            
            if(opcao == 'S') break;
 
            if(opcao == 'm') {
                this.modo = !this.modo;
                System.out.print("Alterado para o modo: ");
                System.out.println(this.modo());
                continue;
            }
            
            switch(opcao) {
                case '+':
                    System.out.println("Operacao: SOMA\n");
                    break;

                case '-':
                    System.out.println("Operacao: SUBTRACAO\n");
                    break;

                case '*':
                    System.out.println("Operacao: MULTIPLICACAO\n");
                    break;

                case '/':
                    System.out.println("Operacao: DIVISAO\n");
                    break;
            }
            
            if(modo) {
                // Opera com floats
                this.operarFloat(opcao);
            } else {
                // Opera com inteiros
                this.operarInteiro(opcao);
            }
        } while(true);
        
        // Eh cordial com o usuario
        System.out.println("Tchau! ;)");
        
        // Fecha os Scanners
        this.entradaLinha.close();
        this.entrada.close();
    }
    
    private void operarFloat(char operacao) {

        boolean erro = false;
        
        int sinal = 0;
        String expoenteBinario;
        String mantissaBinario;
        
        // Auxiliar para armazenar em formato de vetor os bits que estao salvos em uma String
        int[] bits;
        
        Inteiro expoente = null;
        Inteiro mantissa = null;
        
        numero_binario.Float floatA;
        numero_binario.Float floatB;
        numero_binario.Float resultado = null;
        
        /* ########## BINARIO A ########## */
        
        // Recebe o bit de sinal
        do {
            erro = false;
            System.out.print("Digite o sinal A: #\n"
                           + "----------------> ");
            try {
                sinal = this.entrada.nextInt(2);
                if(sinal != 0 && sinal != 1) erro = true;
            } catch(InputMismatchException ime) {
                this.entrada.next();
                System.out.println("[!] - Digite um valor valido!\n");
                erro = true;
            }
        } while(erro);
       
        // Recebe o expoente
        do {
            erro = false;
            System.out.print("Digite a expoente A: @@@@####\n"
                           + "-------------------> ");
            try {
                expoenteBinario = this.entradaLinha.nextLine();
                bits = vetorBits(expoenteBinario);
                if(bits.length != 9) erro = true;
                else expoente = new Inteiro(bits);
            } catch(InputMismatchException ime) {
                this.entradaLinha.next();
                System.out.println("[!] - Digite um valor valido!\n");
                erro = true;
            }
        } while(erro);
        
        // Recebe a mantissa
        do {
            erro = false;
            System.out.print("Digite a mantissa A: @@@@####@@@@####@@@@###\n"
                           + "-------------------> ");
            try {
                mantissaBinario = this.entradaLinha.nextLine();
                bits = vetorBits('1' + mantissaBinario); // Eh necessario adicionar o bit mais significativo
                if(bits.length != 25) erro = true; // Bit de sinal + mantissa (incluindo o bit mais significativo)
                else mantissa = new Inteiro(bits);
            } catch(InputMismatchException ime) {
                this.entradaLinha.next();
                System.out.println("[!] - Digite um valor valido!\n");
                erro = true;
            }
        } while(erro);
        
        // Numero float A
        floatA = new numero_binario.Float(sinal, expoente, mantissa);
        
        
        /* ########## BINARIO B ########## */
        
        // Recebe o bit de sinal
        do {
            erro = false;
            System.out.print("Digite o sinal B: #\n"
                           + "----------------> ");
            try {
                sinal = this.entrada.nextInt(2);
                if(sinal != 0 && sinal != 1) erro = true;
            } catch(InputMismatchException ime) {
                this.entradaLinha.next();
                System.out.println("[!] - Digite um valor valido!\n");
                erro = true;
            }
        } while(erro);
        
        // Recebe o expoente
        do {
            erro = false;
            System.out.print("Digite a expoente B: @@@@####\n"
                           + "-------------------> ");
            try {
                expoenteBinario = this.entradaLinha.nextLine();
                bits = vetorBits(expoenteBinario);
                if(bits.length != 9) erro = true;
                else expoente = new Inteiro(bits);
            } catch(InputMismatchException ime) {
                this.entrada.next();
                System.out.println("[!] - Digite um valor valido!\n");
                erro = true;
            }
        } while(erro);
        
        // Recebe a mantissa
        do {
            erro = false;
            System.out.print("Digite a mantissa B: @@@@####@@@@####@@@@###\n"
                           + "-------------------> ");
            try {
                mantissaBinario = this.entradaLinha.nextLine();
                bits = vetorBits('1' + mantissaBinario); // Eh necessario adicionar o bit mais significativo
                if(bits.length != 25) erro = true; // Bit de sinal + mantissa (incluindo o bit mais significativo)
                else mantissa = new Inteiro(bits);
            } catch(InputMismatchException ime) {
                this.entradaLinha.next();
                System.out.println("[!] - Digite um valor valido!\n");
                erro = true;
            }
        } while(erro);
        
        // Monta o numero float B
        floatB = new numero_binario.Float(sinal, expoente, mantissa);
        
                erro = false;
        
        try {
            switch(operacao) {
                case '+':
                    resultado = numero_binario.Float.somar(floatA, floatB);
                    break;

                case '-':
                    resultado = numero_binario.Float.subtrair(floatA, floatB);
                    break;

                case '*':
                    resultado = numero_binario.Float.multiplicar(floatA, floatB);
                    break;

                case '/':
                    resultado = numero_binario.Float.dividir(floatA, floatB);
                    break;

                default:
                    System.out.println("[!] - Erro durante a selecao da operacao.\n");
                    erro = true;
            }
        } catch(OverflowException oe) {
            System.out.println("[!] - Overflow na operacao: " + oe.getMessage() + "\n");
            erro = true;
        }
        
        imprimirResultado(floatA);
        imprimirResultado(floatB);
        if(!erro) imprimirResultado(resultado);
    }

    private void operarInteiro(char operacao) {
        boolean erro = false;
        
        // Numero de bits usados para representar um inteiro
        int representacao = 2;
        
        Inteiro inteiroA = new Inteiro(new int[1], representacao);
        Inteiro inteiroB = new Inteiro(new int[1], representacao);
        Inteiro resultado = new Inteiro(new int[1], representacao);
        
        // Recebe a quantidade bits que serao usados para representar um inteiro
        do {
            erro = false;
            System.out.print("Escolha a quantidade de bits para o inteiro (entre 2 e 32)\n-> ");
            try {
                representacao = this.entrada.nextInt();
                System.out.println();
            } catch(InputMismatchException ime) {
                this.entrada.next();
                System.out.println("[!] - Digite um valor valido!\n");
                erro = true;
            }
        } while(representacao < 2 || representacao > 32 || erro);

        // Recebe o numero A
        do {
            erro = false;
            System.out.print("A - Digite um numero inteiro decimal:\n-> ");
            try {
                inteiroA = ConversaoBinarioDecimal.decimalInteiroParaInteiroBinario(this.entrada.nextInt(), representacao);
            } catch(InputMismatchException ime) {
                this.entrada.next();
                System.out.println("[!] - Digite um valor valido!\n");
                erro = true;
            } catch(OverflowException oe) {
                System.out.println("[!] Overflow, numero muito grande: " + oe.getMessage() + "\n");
                erro = true;
            }
        } while(erro);
     
        // Recebe o numero B
        do {
            erro = false;
            System.out.print("B - Digite um numero inteiro decimal:\n-> ");
            try {
                inteiroB = ConversaoBinarioDecimal.decimalInteiroParaInteiroBinario(this.entrada.nextInt(), representacao);
            } catch(InputMismatchException ime) {
                this.entrada.next();
                System.out.println("[!] - Digite um valor valido!\n");
                erro = true;
            } catch(OverflowException oe) {
                System.out.println("[!] Overflow, numero muito grande: " + oe.getMessage() + "\n");
                erro = true;
            }
        } while(erro);

        erro = false;
        
        try {
            switch(operacao) {
                case '+':
                    resultado = Inteiro.somar(inteiroA, inteiroB);
                    break;

                case '-':
                    resultado = Inteiro.subtrair(inteiroA, inteiroB);
                    break;

                case '*':
                    resultado = Inteiro.multiplicar(inteiroA, inteiroB);
                    break;

                case '/':
                    resultado = Inteiro.dividir(inteiroA, inteiroB);
                    break;

                default:
                    System.out.println("[!] - Erro durante a selecao da operacao.\n");
                    erro = true;
            }
        } catch(OverflowException oe) {
            System.out.println("[!] - Overflow na operacao: " + oe.getMessage() + "\n");
            erro = true;
        }
        
        if(!erro) imprimirResultado(resultado);
    }
    
    CalculadoraBinaria() {
        this.entradaLinha = new Scanner(System.in);
        this.entrada = new Scanner(System.in);
    }
    
    public static void main(String args[]) {
        new CalculadoraBinaria().iniciar();
    }
    
}
