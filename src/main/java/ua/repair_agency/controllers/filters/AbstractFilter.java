package ua.repair_agency.controllers.filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.repair_agency.constants.Attributes;
import ua.repair_agency.models.user.User;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public abstract class AbstractFilter implements Filter {

    protected static final Logger LOGGER = LogManager.getLogger(ExceptionHandleFilter.class);

    public abstract void doCustomFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        request.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        doCustomFilter(request, response, filterChain);
    }

    protected User getUserFromSession(HttpServletRequest req){
        HttpSession session = req.getSession();
        return (User) session.getAttribute(Attributes.USER);
    }

    protected void setMainBlock(HttpServletRequest req, String value){
        req.setAttribute(Attributes.MAIN_BLOCK, value);
    }
}
