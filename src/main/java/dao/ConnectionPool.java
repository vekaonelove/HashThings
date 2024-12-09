package dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class ConnectionPool {

    private static HikariDataSource dataSource;

    static {
        try {
            Properties properties = new Properties();
            try (InputStream input = ConnectionPool.class.getClassLoader().getResourceAsStream("db-config.properties")) {
                if (input == null) {
                    System.out.println("Sorry, unable to find db-config.properties");
                    properties.setProperty("db.type", "mysql");  // Default to mysql
                } else {
                    properties.load(input);
                }
            }

            Scanner scanner = new Scanner(System.in);
            System.out.println("Select a database type (mysql, postgres, h2):");
            String dbType = scanner.nextLine().toLowerCase();

            if (!(dbType.equals("mysql") || dbType.equals("postgres") || dbType.equals("h2"))) {
                System.out.println("Invalid database type selected, using default (mysql).");
                dbType = "mysql";
            }

            properties.setProperty("db.type", dbType);

            String dbUrl = "";
            String dbDriver = "";

            switch (dbType) {
                case "mysql":
                    dbUrl = properties.getProperty("mysql.db.url");
                    dbDriver = properties.getProperty("mysql.db.driver");
                    break;
                case "postgres":
                    dbUrl = properties.getProperty("postgres.db.url");
                    dbDriver = properties.getProperty("postgres.db.driver");
                    break;
                case "h2":
                    dbUrl = properties.getProperty("h2.db.url");
                    dbDriver = properties.getProperty("h2.db.driver");
                    break;
                default:
                    System.out.println("Unsupported database type, using mysql by default.");
                    dbUrl = properties.getProperty("mysql.db.url");
                    dbDriver = properties.getProperty("mysql.db.driver");
            }

            String dbUsername = properties.getProperty("db.username");
            String dbPassword = properties.getProperty("db.password");

            // Configuring the Hikari connection pool
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(dbUrl);
            config.setUsername(dbUsername);
            config.setPassword(dbPassword);
            config.setDriverClassName(dbDriver);
            config.setMaximumPoolSize(Integer.parseInt(properties.getProperty("db.pool.max", "10")));  // Default pool size to 10 if not specified

            dataSource = new HikariDataSource(config);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        if (dataSource != null) {
            return dataSource.getConnection();
        } else {
            throw new SQLException("Data source is not initialized.");
        }
    }

    public static void close() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}
