import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.FileHandler;

public class HttpServer {
    private int port;
    private Handler defaultHandler = null;
    // Two level map: first level is HTTP Method (GET, POST, OPTION, etc.), second level is the
    // request paths.
    private Map<String, Map<String, Handler>> handlers = new HashMap<String, Map<String, Handler>>();

    // TODO SSL support
    public HttpServer(int port) {
        this.port = port;
    }

    /**
     * @param path if this is the special string "/*", this is the default handler if
     *             no other handler matches.
     */
    public void addHandler(String method, String path, Handler handler) {
        Map<String, Handler> methodHandlers = handlers.get(method);
        if (methodHandlers == null) {
            methodHandlers = new HashMap<String, Handler>();
            handlers.put(method, methodHandlers);
        }
        methodHandlers.put(path, handler);
    }

    public void start() throws IOException {
        ServerSocket socket = new ServerSocket(port);
        System.out.println("Listening on port " + port);
        Socket client;
        while ((client = socket.accept()) != null) {
            System.out.println("Received connection from " + client.getRemoteSocketAddress().toString());
            SocketHandler handler = new SocketHandler(client, handlers);
            Thread t = new Thread(handler);
            t.start();
        }
    }

    public static void main(String[] args) throws IOException {
        HttpServer server = new HttpServer(8080);
        server.addHandler("GET", "/hello", new Handler() {
            public void handle(Request request, Response response) throws IOException {
                String html = "It works, " + request.getParameter("name") + "";
                response.setResponseCode(200, "OK");
                response.addHeader("Content-Type", "text/html");
                response.addBody(html);
            }
        });
        server.addHandler("GET", "/*", (Handler) new FileHandler());  // Default handler
        server.start();
    }
}
