
/*Fontes: 
https://www.devmedia.com.br/como-criar-um-chat-multithread-com-socket-em-java/33639
https://github.com/youngmonkeys/ezyfox-server/issues
https://github.com/iamrohitsuthar/LiveChatServer
*/
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class EchoChatServer {
    public static void main(String[] args) {

        String ip = "127.0.0.1";
        int port = 8888;
        int backlog = 50;

        for (int i = 0; i < args.length; i += 2) {
            if (args[i].equals("--port")) {
                port = Integer.parseInt(args[i + 1]);
                System.out.println("port: " + port);
            } else if (args[i].equals("--host")) {
                ip = args[i + 1];
                System.out.println("ip: " + ip);
            } else {
                System.out.println("error!!!");
                return;
            }
        }

        Scanner sc = new Scanner(System.in);
        PrintStream out = System.out;

        Socket socket = null;
        ServerSocket serverSocket = null;

        try {

            InetAddress addr = InetAddress.getByName(ip);

            out.println("Chat Iniciado");
            serverSocket = new ServerSocket(port, backlog, addr);
            out.println("inet addr: " + serverSocket.getInetAddress());
            out.println("port: " + serverSocket.getLocalPort());
            out.println("");

            out.println("Esperado chat amigo");
            socket = serverSocket.accept();

            InetAddress clientAddr = socket.getInetAddress();
            int clientPort = socket.getPort();
            out.println("client addr: " + clientAddr);
            out.println("client port: " + clientPort);
            out.println("");

            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            PrintWriter pw = new PrintWriter(bw);

            out.print("Amigo :/ ");
            String message = br.readLine();
            while (true) {
                out.println(message);
                if (message.toLowerCase().equals("sair"))
                    break;

                out.print("Eu :/ ");
                String reply = sc.nextLine();
                pw.println(reply);
                pw.flush();
                if (reply.toLowerCase().equals("sair")) {
                    break;
                }

                out.print("Amigo :/ ");
                message = br.readLine();
            }
            out.println("");

            br.close();
            pw.close();
        } catch (IOException e) {
            System.out.print("Finalizado!");
        }

    }
}
