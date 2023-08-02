import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class IEC104Client {

    public static void main(String[] args) {
        try {
            // Создание экземпляра CamelContext
            CamelContext context = new DefaultCamelContext();

            // Добавление маршрута для получения данных от сервера
            context.addRoutes(new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    from("iec60870-client:127.0.0.1:2404/00-01-02-03-04")
                            .to("log:iec104-client")
                            .to("direct:processData");
                }
            });

            // Добавление маршрута для обработки полученных данных
            context.addRoutes(new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    from("direct:processData")
                            .log("Processing data from server...")
                            .process(exchange -> {
                                // Получение пакета данных от сервера
                                byte[] data = exchange.getIn().getBody(byte[].class);

                                // Добавьте свою обработку данных здесь
                                // Например, распаковка и анализ пакета данных

                                // Вывод данных в консоль
                                System.out.println("Received data from server: " + new String(data));
                            });
                }
            });

            // Запуск CamelContext
            context.start();

            // Ожидание завершения работы
            Thread.sleep(5000);

            // Остановка CamelContext
            context.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
