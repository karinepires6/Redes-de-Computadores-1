import java.util.Scanner;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author karine
 */
public class ForcaPrincipal {
    public static void main(String[] args) {
        int n;
        Scanner ler = new Scanner(System.in);
        ServidorUDP coordenador;
        boolean jahExisteCoord = false;
        ClienteUDP jogador;
        
        do {
            System.out.println("Escolha entre as opcoes abaixo:");
            if(!jahExisteCoord)
                System.out.println("1 - Coordenador");
            System.out.println("2 - Jogador");
            System.out.println("3 - Sair");
            System.out.println("Escolha:");
            n = ler.nextInt();
            if(n == 1){
                new ServidorUDP("Karine", "Laranja");
                jahExisteCoord = true;
            }
            else if(n == 2) {
                new ClienteUDP();
            }
        } while (n != 3);
    }
}

public class ClienteUDP {
    Jogador jogador = new Jogador();

    public ClienteUDP(){
        this.iniciar();
    }
    
    public void iniciar() throws Exception {
        byte[] sendData = new byte[1024];
        DatagramSocket clientSocket = new DatagramSocket();
        int porta = 9876;
        String servidor = "192.168.0.1";
        
        System.out.println("Digite seu nome: ");
        String nome = inFromUser.readLine();
        jogador.setNome(nome);
        jogador.setEnderecoIP("localhost");
        jogador.setPorta(porta);
        jogador.setContChutesLetra(0);
        jogador.setContChutesPalavra(0);
        sendData = jogador.getBytes();
        InetAddress IPAddress = InetAddress.getByName(servidor);
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, porta);
        clientSocket.send(sendPacket);
    }
    
    byte[] receiveData;
    Map<String, String> hash;
    while(true){
        receiveData = new byte[1024];
        
        
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		serverSocket.receive(receivePacket);
        
        hash = new HashMap<>(receivePacket.getData());
        
        String sentence = new String(receivePacket.getData());
        
        if(hash.get("dica")){
            
        } else if(hash.get("suaVezLP")) {
        
        } else if(hash.get("suaVezL")){
        
        } else if(){
        
        }
            
    }
    
}

class ServidorUDP {
    public static void main(String args[]) throws Exception {
        List<Jogador> jogadores = new ArrayList<>();
        Coordenador coordenador = new Cordenador();
        String palavraMontada;
        String letrasDigitadas;
        

        public ServidorUDP(String nome, String palavra){
            coordenador.setNome(this.nome);
            coordenador.setPalavra(this.palavra);
            for(int i=0; i < this.palavra.length() ; i++){
                palavraMontada.setCharAt(i, '_');
            }
            coordenador.setPorta(9876);
            coordenador.setEnderecoIP("localhost");
        }

        DatagramSocket serverSocket = new DatagramSocket(coordenador.getPorta());

        byte[] receiveData = new byte[1024];
        byte[] sendData = new byte[1024];

        while (true) {
            receiveData = new byte[1024];

            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            System.out.println("Esperando por datagrama UDP na porta " + porta);
            serverSocket.receive(receivePacket);

            String sentence = new String(receivePacket.getData());
            InetAddress IPAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();
            String nomeJogador = sentence.toUpperCase();
            
            Jogador jogador = new Jogador();
            jogador.nome = nomeJogador;
            jogador.enderecoIP = receivePacket.getAddress();
            jogador.porta = receivePacket.getPort();

            jogadores.add(jogador);
            
            if(jogadores.size() >= 2){
                iniciarJogo();
                break;
            }
            
        }
        
    }
    
    public void iniciarJogo(){
        System.out.println("Informe a dica: ");
        String sentence = inFromUser.readLine();
        coordenador.setDica(sentence);
        
        Map<String, String> hash = new HashMap<>();
        hash.put("dica", sentence);
        
        for(Jogador jogador : jogadores){
            //Pega a dica informada pelo coordenador e envia para todos os jogadores online
            sendData = hash.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, jogador.getEnderecoIP(), jogador.getPorta());
            clientSocket.send(sendPacket);
        }
        
        byte[] sendDataJogador;
        byte[] sendDataPalavraMontada;
        byte[] sendDataLetraDigitada;
        int contPalavra = 0;
        int i = 0;
        while(contPalavra < coordenador.getPalavra().length()){
            sendDataJogador = new byte[1024];
            sendDataPalavraMontada = new byte[1024];
            sendDataLetraDigitada = new byte[1024];
            
            hash = new HashMap<>();
            
            if(jogadores.get(i).getContChutesPalavra() != 2){
                hash.put("suaVezLP", "Eh a sua vez de jogar, informe uma LETRA ou PALAVRA!");
            } else {
                 hash.put("suaVezL", "Eh a sua vez de jogar, informe uma LETRA!");
            }
            
            sendDataJogador = msgJogador.getBytes();
            DatagramPacket sendPacketJogador = new DatagramPacket(sendDataJogador, sendDataJogador.length, jogadores.get(i).getEnderecoIP(), jogadores.get(i).getPorta());
            clientSocket.send(sendPacketJogador);
            
            //espero a resposta do jogador
            receiveDataJogador = new byte[1024];

            DatagramPacket receivePacketJogador = new DatagramPacket(receiveDataJogador, receiveDataJogador.length);
            serverSocket.receive(receiveDataJogador);
            
            //atribuicao na variavel sentence o que o jogador em especifico enviou de volta para o coordenador
            String sentence = new String(receivePacketJogador.getData());
            
            /*
            * REGRA: "Chutar" a palavra. Cada competidor terá 2 chances;
            */
            //Se ele já chutou a palavra 2 vezes e ele fez gracinha digitando uma palavra, passa a vez para o próximo jogador  
            if(jogadores.get(i).getContChutesPalavra() == 2 && sentence.length() > 1){
                i++;
                
                if(i == jogadores.length())
                    i = 0;
                
                continue;
            }
            
            //verifica se a letra digitada pelo jogador esta na palavra digitada pelo coordenador
            if(coordenador.getPalavra().matches("(.*)" + sentence + "(.*)")){ // Se o jogador acertou a letra/palavra
                //se a palavra que o jogador digitou é a palavra que o coordenador escolheu, ELE ACERTOU E ACABA O JOGO
                if(sentence.length() > 1 && coordenador.getPalavra().equals(sentence)){
                    contPalavra = coordenador.getPalavra().length();
                    //AVISAR QUE O JOGADOR GANHOU
                } else if(sentence.length() == 1) {
                    /*
                    * REGRA: Ao acertar a letra, preencher ela na posição em que a letra está presente e dar nova chance ao jogador que acertou a tentativa;
                    */
                    int ant = 0, atual;
                    do{
                        atual = coordenador.getPalavra().indexOf(sentence, ant);
                        if(atual != -1){
                            palavraMontada.setCharAt(atual, sentence);
                            ant += atual+1;
                            contPalavra++;
                        }
                    }while(atual != -1);
                    
                    sendDataPalavraMontada = new byte[1024];
                    
                    hash = new HashMap<>();
                    hash.put("palavraMontada", palavraMontada);
                    
                    for(Jogador jogador : jogadores){
                        //Envia para todos os jogados a palavra MONTADA
                        sendDataPalavraMontada = hash.getBytes();
                        DatagramPacket sendPacketPalavraMontada = new DatagramPacket(sendDataPalavraMontada, sendDataPalavraMontada.length, jogador.getEnderecoIP(), jogador.getPorta());
                        clientSocket.send(sendPacketPalavraMontada);
                    }
                    
                    continue;
                }
                
            } else {
                //CONTA OS CHUTES QUE O JOGADOR DEU
                if(sentence.length() == 1)
                    jogadores.get(i).setContChutesLetra(jogadores.get(i).getContChutesLetra() + 1);
                else
                    jogadores.get(i).setContChutesPalavra(jogadores.get(i).getContChutesPalavra() + 1);

            }
            
            /*
             * REGRA: Mostrar quais letras já foram digitadas;
            */       
            if(sentence.length() == 1){
                letraDigitada += (sentence + " ");
                sendDataLetraDigitada = letraDigitada.getBytes();
                DatagramPacket sendPacketLetraDigitada = new DatagramPacket(sendDataLetraDigitada, sendDataLetraDigitada.length, jogador.getEnderecoIP(), jogador.getPorta());
                clientSocket.send(sendPacketLetraDigitada);
            }
            
            if(contPalavra == coordenador.getPalavra().length())
                break;
            
            i++;
            
            if(i == jogadores.length())
                i = 0;
        }
        
    }

}

/*
public class Jogador {
    private String nome;
    private String enderecoIP;
    private String porta;
    private int contChutesLetra;
    private int contChutesPalavra;
}

public class Coordenador {
    private String nome;
    private String enderecoIP;
    private String porta;
    private String palavra;
    private String dica;
}

*/
