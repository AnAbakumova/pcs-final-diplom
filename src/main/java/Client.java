import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public static void main(String[] args) {
        try (
                Socket socket = new Socket("127.0.01", 8989); // Создание сокета для подключения к серверу
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true); // Поток вывода для отправки сообщений на сервер
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Поток ввода для чтения ответов от сервера
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in)) // Поток ввода с консоли для ввода запросов
        ) {
            String word;
            while ((word = stdIn.readLine()) != null) { // Читаем запросы из консоли
                out.println(word); // Отправляем запрос на сервер
                System.out.println("Ответ от сервера: " + in.readLine()); // Читаем ответ от сервера и выводим его в консоль
            }
        } catch (UnknownHostException e) {
            System.out.println("Error: Unknown host 127.0.01");
        } catch (IOException e) {
            System.out.println("Error: Could not connect to 127.0.01");
        }
    }
}