import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        if (args[0].equals("client_example")) {
            clientExample();
        }
    }

    public static void clientExample() {
        try (Socket socket = new Socket(InetAddress.getByName("example.com"), 80)) {
            PrintWriter pw = new PrintWriter(socket.getOutputStream());
            pw.println("GET /index.html HTTP/1.1");
            pw.println("Host: example.com");
            pw.println("");
            pw.flush();
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String t;
            StringBuilder sb = new StringBuilder();
            while (!Objects.equals(t = br.readLine(), "")) {
                sb.append(t).append("\n");
            }
            System.out.println(sb);
            br.close();
        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
        }
    }
}
