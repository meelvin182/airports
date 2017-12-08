import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.entities.FlightEntity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import service.FlightService;
import util.HibernateUtil;

import java.io.FileWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "controller")
public class Main {
    private static FlightService flightService = new FlightService();

    public static void main(final String[] args) {
        SpringApplication.run(Main.class, args);
        /*FlightRequest request = new FlightRequest("aaa", "bbb", "1999-12-12 00:00:00", 0.5);

        Gson gson = new Gson();
        String json = gson.toJson(request);
        Writer writer = new FileWriter("flights.json");
        writer.write(json);
        writer.close();*/
        /*Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader("flights.json"));
        FlightRequest requests = gson.fromJson(reader, FlightRequest.class);
        reader.close();
        System.out.println(requests);*/
        /*
        try {
            HibernateUtil.getCurrentSession().beginTransaction();
            List<FlightEntity> filter = flightService.getWithFilter(
                    "Москва",
                    "Анапа",
                    Timestamp.valueOf("3018-10-10 00:00:00"),
                    new BigDecimal("1331.13")
            );
            HibernateUtil.getCurrentSession().getTransaction().commit();
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            String json = gson.toJson(filter);
            Writer writer = new FileWriter("flights_arr.json");
            writer.write(json);
            writer.close();
        }
        catch (Exception exc) {
            System.out.println(exc.toString());
        }*/
    }
}