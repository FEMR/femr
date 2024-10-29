package integration;
import static org.junit.Assert.*;

import com.google.common.collect.ImmutableMap;
import com.mysql.jdbc.Driver;
import org.junit.*;
import play.Mode;
import play.db.Database;
import play.db.Databases;
import play.api.Environment;
import play.api.db.evolutions.EnvironmentEvolutionsReader;
import play.db.evolutions.*;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;


public class TestIntegrations {

    Database database;

    @Before
    public void createDatabase() {
        database = Databases.createFrom("com.mysql.jdbc.Driver", "jdbc:mysql://127.0.0.1:3306/femr_db?characterEncoding=UTF-8&useSSL=false&allowPublicKeyRetrieval=true",
                ImmutableMap.of("username", "femr", "password", "password"));


        // Relative path (based on your project's root folder)
        Evolutions.applyEvolutions(
                database,
                new EnvironmentEvolutionsReader(Environment.simple(new File("."), Mode.DEV.asScala())));
    }

    @After
    public void shutdownDatabase() {
        Evolutions.cleanupEvolutions(database);
        database.shutdown();
    }

    @Test
    public void newTest() throws SQLException {
        Connection connection = database.getConnection();

        assertTrue(
                connection.prepareStatement("select * from users where first_name = 'SuperUser'").executeQuery().next());
    }

}
