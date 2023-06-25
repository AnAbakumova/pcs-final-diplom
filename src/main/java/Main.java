import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        // Сервер, который отвечает на текстовые запросы в JSON-формате

        try (ServerSocket serverSocket = new ServerSocket(8989)) {
            System.out.println("Server started on port 8989");
            BooleanSearchEngine searcher = new BooleanSearchEngine(new File("pdfs"));

            while (true) { // в цикле принимаем подключения
                try (Socket socket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
                ) {
                    String word;
                    while ((word = in.readLine()) != null) {
                        System.out.println("Поиск: " + word);
                        List<PageEntry> result = searcher.search(word);
                        // Формируем JSON-ответ и отправляем его клиенту
                        String json = "{Поиск по слову " + word + ": " + result + "\"}";
                        out.println(json);
                        System.out.println("Отправлено клиенту: " + json);
                    }
                } catch (IOException e) {
                    System.out.println("Could not listen on port 8989");
                    e.printStackTrace();
                }
            }
        }
    }
}