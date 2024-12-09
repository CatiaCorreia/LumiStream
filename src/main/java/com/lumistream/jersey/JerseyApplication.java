package com.lumistream.jersey;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lumistream.jersey.user.UserSupervisor;

import static org.eclipse.jetty.servlet.ServletContextHandler.NO_SESSIONS;

public class JerseyApplication {

    private static final Logger logger = LoggerFactory.getLogger(JerseyApplication.class);
    public static UserSupervisor sup = UserSupervisor.getInstance();

    public static void main(String[] args) {

	// Set the system property to bind Jetty to all interfaces (IPv4 and IPv6)
	System.setProperty("jetty.host", "0.0.0.0");

        // Create the Jetty server
        //Server server = new Server(8080);
        Server server = new Server();

	// Create a ServerConnector (for HTTP requests)
	ServerConnector connector = new ServerConnector(server);
	connector.setHost("0.0.0.0"); // Bind to all network interfaces (IPv4 and IPv6)
        connector.setPort(8080); // Set the port to 8080
	server.addConnector(connector);  // add connector to the server

	// create the servletContexthandler
        ServletContextHandler servletContextHandler = new ServletContextHandler(NO_SESSIONS);

        servletContextHandler.setContextPath("/");
        server.setHandler(servletContextHandler);

        ServletHolder servletHolder = servletContextHandler.addServlet(ServletContainer.class, "/api/*");
        servletHolder.setInitOrder(0);
        servletHolder.setInitParameter(
                "jersey.config.server.provider.packages",
                "com.lumistream.jersey.resources"
        );

        try {
            server.start();
            server.join();
        } catch (Exception ex) {
            logger.error("Error occurred while starting Jetty", ex);
            System.exit(1);
        }

        finally {
            server.destroy();
        }
    }
}
