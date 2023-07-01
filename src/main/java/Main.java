import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

            GsonBuilder builder = new GsonBuilder();
            Gson json = builder.create();

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
                        out.println(json.toJson(result));
                        System.out.println("Ответ отправлен клиенту");
                    }
                } catch (IOException e) {
                    System.out.println("Could not listen on port 8989");
                    e.printStackTrace();
                }
            }
        }
    }
}