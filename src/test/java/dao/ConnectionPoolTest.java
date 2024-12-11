package dao;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.mockito.*;
import java.sql.Connection;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;

public class ConnectionPoolTest {

    private HikariDataSource h2DataSource;
    private HikariDataSource postgresDataSource;
    private HikariDataSource mysqlDataSource;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        h2DataSource = new HikariDataSource();
        h2DataSource.setJdbcUrl("jdbc:h2:mem:hashtest");
        h2DataSource.setUsername("sa");
        h2DataSource.setPassword("password");
        h2DataSource.setMaximumPoolSize(10);

        postgresDataSource = new HikariDataSource();
        postgresDataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/hashtest");
        postgresDataSource.setUsername("postgres");
        postgresDataSource.setPassword("postgres");
        postgresDataSource.setMaximumPoolSize(10);

        mysqlDataSource = new HikariDataSource();
        mysqlDataSource.setJdbcUrl("jdbc:mysql://localhost:3306/hashtest");
        mysqlDataSource.setUsername("root");
        mysqlDataSource.setPassword("1234");
        mysqlDataSource.setMaximumPoolSize(10);
    }

    @AfterEach
    public void tearDown() {
        closeDataSource(h2DataSource);
        closeDataSource(postgresDataSource);
        closeDataSource(mysqlDataSource);
    }

    private void closeDataSource(HikariDataSource dataSource) {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }

    @Test
    public void testH2Connection() {
        testRealDatabaseConnection(h2DataSource, "H2");
    }

    @Test
    public void testPostgresConnection() {
        testRealDatabaseConnection(postgresDataSource, "PostgreSQL");
    }

    @Test
    public void testMySQLConnection() {
        testRealDatabaseConnection(mysqlDataSource, "MySQL");
    }

    private void testRealDatabaseConnection(HikariDataSource dataSource, String dbName) {
        try (Connection connection = dataSource.getConnection()) {
            assertNotNull(connection, dbName + " connection should not be null");
            assertFalse(connection.isClosed(), dbName + " connection should not be closed");
        } catch (SQLException e) {
            fail("Failed to acquire " + dbName + " connection: " + e.getMessage());
        }
    }


}
