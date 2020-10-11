package ua.repair_agency.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.repair_agency.services.database_services.connection.DBConnectionsPool;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;

@WebListener
public class RepairAgencyApplicationListener implements ServletContextListener {

    private final Logger LOGGER = LogManager.getLogger(RepairAgencyApplicationListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOGGER.info("Webapp 'Repair Service' was started.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            DBConnectionsPool.getConnectionsPool().close();
        } catch (SQLException exc) {
            LOGGER.error("DBConnectionPool closing error: " + exc.getMessage(), exc);
        }
        LOGGER.info("Webapp 'Repair Service' was closed");
    }
}