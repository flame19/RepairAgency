package ua.repair_agency.controllers.filters;

import ua.repair_agency.constants.CRA_JSPFiles;
import ua.repair_agency.constants.Parameters;
import ua.repair_agency.exceptions.AuthenticationException;
import ua.repair_agency.exceptions.AuthorizationException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter(filterName = "ExceptionHandler")
public class ExceptionHandleFilter extends AbstractFilter {

    @Override
    public void doCustomFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        try {
            filterChain.doFilter(req, resp);
        } catch (Throwable exc) {
            if (exc instanceof AuthenticationException) {
                switch (((AuthenticationException) exc).getType()) {
                    case EMAIL:
                        LOGGER.warn("Attempt to log in using non-existing email: " + req.getParameter(Parameters.EMAIL) + "\t User-Agent: " + req.getHeader(Parameters.USER_AGENT));
                        break;
                    case PASS:
                        LOGGER.warn("Wrong password log in attempt. User: " + req.getParameter(Parameters.EMAIL) + "\t User-Agent: " + req.getHeader(Parameters.USER_AGENT));
                        break;
                    default:
                        break;
                }
            } else if (exc instanceof AuthorizationException){
                LOGGER.info("Unauthorized request (" + req.getRequestURI() + ") User-Agent : " + req.getHeader(Parameters.USER_AGENT));
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                setMainBlock(req, CRA_JSPFiles.PAGE404);
                req.getRequestDispatcher(CRA_JSPFiles.CORE_PAGE).forward(req, resp);
            } else {
                LOGGER.error("Failed request (" + req.getRequestURI() + "). Internal server error: " + exc.getMessage(), exc);
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                setMainBlock(req, CRA_JSPFiles.PAGE500);
                req.getRequestDispatcher(CRA_JSPFiles.CORE_PAGE).forward(req, resp);
            }
        }
    }
}
