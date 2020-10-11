package ua.repair_agency.controllers.servlet.commands;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface RequestHandler {
    void handleRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
}
