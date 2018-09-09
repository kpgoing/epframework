package club.xubowei.epframework.helper;

import club.xubowei.epframework.constant.ConfigConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author kpgoing
 * @date 2018/9/9.
 */
public class DatabaseHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);

    private static final ThreadLocal<Connection> CONNECTION_HOLDER = new ThreadLocal<>();

    private static final String DRIVER;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;

    static {
        DRIVER = ConfigHelper.getConfigByName(ConfigConstant.JDBC_DRIVER);
        URL = ConfigHelper.getConfigByName(ConfigConstant.JDBC_URL);
        USERNAME = ConfigHelper.getConfigByName(ConfigConstant.JDBC_USERNAME);
        PASSWORD = ConfigHelper.getConfigByName(ConfigConstant.JDBC_PASSWORD);

        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            LOGGER.error("can not load jdbc driver", e);
        }
    }

    private static Connection getConnection() {
        Connection connection = CONNECTION_HOLDER.get();

        if (connection == null) {
            try {
                DriverManager.getConnection(URL, USERNAME, PASSWORD);
            } catch (SQLException e) {
                LOGGER.error("get connection failure", e);
            }
        }

        return connection;
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.error("close connection failure", e);
            }
        }
    }

    public static void beginTransaction() {
        Connection connection = getConnection();

        if (connection != null) {
            try {
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                LOGGER.error("begin transaction failure", e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDER.set(connection);
            }
        }
    }

    public static void commitTransaction() {
        Connection connection = getConnection();

        try {
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            LOGGER.error("commit transaction failure", e);
        } finally {
            CONNECTION_HOLDER.remove();
        }
    }

    public static void rollbackTransaction() {
        Connection connection = getConnection();

        if (connection != null) {
            try {
                connection.rollback();
                connection.close();
            } catch (SQLException e) {
                LOGGER.error("rollback transaction failure", e);
            } finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }

}
