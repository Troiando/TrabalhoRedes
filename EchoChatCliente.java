import java.io.*;
import java.net.*;
import java.util.Scanner;

public class EchoChatCliente {
    public static void main(String[] args) {

        String destIP = "127.0.0.1";
        int destPort = 8888;

        for (int i = 0; i < args.length; i += 2) {
            if (args[i].equals("--port")) {
                destPort = Integer.parseInt(args[i + 1]);
                System.out.println("port: " + destPort);
            } else if (args[i].equals("--server")) {
                destIP = args[i + 1];
                System.out.println("ip: " + destIP);
            } else {
                System.out.println("error!!");
                return;
            }
        }

        Scanner sc = new Scanner(System.in);
        PrintStream out = System.out;

        Socket socket = null;
        
        try {
            InetAddress destAddr = InetAddress.getByName(destIP);
            String localHost = "127.0.0.1";
            int localPort = 0;
            InetAddress localAddr = InetAddress.getByName(localHost);

            socket = new Socket(destAddr, destPort);

            out.println("dest addr: " + socket.getInetAddress());
            out.println("dest port: " + socket.getPort());
            out.println("local addr: " + socket.getLocalAddress());
            out.println("local port: " + socket.getLocalPort());
            out.println("");

            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            PrintWriter pw = new PrintWriter(bw);

            out.print("Eu :/ ");
            String reply = sc.nextLine();
            pw.println(reply);
            pw.flush();
            while (!reply.equals("sair") && !reply.equals("sair")) {
                out.print("Amigo :/ ");
                String message = br.readLine();
                out.println(message);
                if (message.toLowerCase().equals("sair")) {
                    break;
                }
                //
                out.print("Eu :/ ");
                reply = sc.nextLine();
                pw.println(reply);
                pw.flush();
            }
            out.println("");
            br.close();
            pw.close();
        } catch (IOException e) {
            System.out.print("Chat fechado");
        }
    }
}
