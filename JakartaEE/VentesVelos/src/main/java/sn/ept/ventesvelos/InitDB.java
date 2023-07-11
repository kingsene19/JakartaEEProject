package sn.ept.ventesvelos;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;

@Singleton
@Startup
public class InitDB {

    @Resource(lookup = "jdbc/velo")
    private DataSource dataSource;

    @PostConstruct
    public void init() {
        System.out.println("#### Initialisation de la base de données ####");
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("sql/venteVelos.sql");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                statement.executeUpdate(line);
            }

            System.out.println("#### Initialisation terminée ####");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}