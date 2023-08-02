import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class IEC104Client {

    public static void main(String[] args) {
        try {
            // Создание серверного сокета для прослушивания входящих соединений
            ServerSocket serverSocket = new ServerSocket(8080);

            while (true) {
                // Ожидание клиентского подключения
                Socket clientSocket = serverSocket.accept();

                // Чтение данных из сокета
                DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());

                // Обработка пакетов
                while (true) {
                    byte[] packetData = new byte[1024];
                    int bytesRead = inputStream.read(packetData);

                    if (bytesRead == -1) {
                        // Клиент разорвал соединение
                        break;
                    }

                    // Вывод информации о пакете
                    System.out.println("Received packet: " + new String(packetData, 0, bytesRead));
                }

                // Закрытие клиентского сокета
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
