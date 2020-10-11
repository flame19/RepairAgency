package ua.repair_agency.services.database_services.connection;

import org.apache.commons.dbcp2.BasicDataSource;
import ua.repair_agency.services.resources.ApplicationResourceBundle;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public final class DBConnectionsPool {

    private static final BasicDataSource connectionsPool = new BasicDataSource();
    static {
        ResourceBundle resourceBundle = ApplicationResourceBundle.resourceBundle;
        connectionsPool.setUrl(resourceBundle.getString("sr.db.url"));
        connectionsPool.setUsername(resourceBundle.getString("sr.db.user"));
        connectionsPool.setPassword(resourceBundle.getString("sr.db.password"));
        connectionsPool.setInitialSize(Integer.parseInt(resourceBundle.getString("sr.db.initialSize")));
        connectionsPool.setMaxTotal(Integer.parseInt(resourceBundle.getString("sr.db.totalSize")));
    }

    private DBConnectionsPool() {
    }

    public static Connection getConnection() throws SQLException {
        return connectionsPool.getConnection();
    }

    public static BasicDataSource getConnectionsPool() {
        return connectionsPool;
    }
}
