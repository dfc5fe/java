import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class dbhandler {
    private static final String PROP_FILE = "db.properties";

    public static Connection getConnection() throws SQLException {
        Properties props = new Properties();
        try (InputStream input = dbhandler.class.getClassLoader().getResourceAsStream(PROP_FILE)) {
            if (input == null) {
                throw new RuntimeException("Sorry, unable to find " + PROP_FILE);
            }
            props.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return DriverManager.getConnection(props.getProperty("db.url"));
    }
}