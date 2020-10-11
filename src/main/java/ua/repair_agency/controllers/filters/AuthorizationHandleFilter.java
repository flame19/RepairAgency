package ua.repair_agency.controllers.filters;

import ua.repair_agency.constants.Attributes;
import ua.repair_agency.constants.CRAPaths;
import ua.repair_agency.constants.CRA_JSPFiles;
import ua.repair_agency.exceptions.AuthorizationException;
import ua.repair_agency.models.user.User;
import ua.repair_agency.services.autherization.AuthorizationService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "AuthorizationHandler")
public class AuthorizationHandleFilter extends AbstractFilter {
    @Override
    public void doCustomFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        String servletPath = req.getServletPath();
        User user = getUserFromSession(req);
        if (AuthorizationService.authorize(servletPath, user)) {
            filterChain.doFilter(req, resp);
        } else if (req.getServletPath().equals(CRAPaths.CUSTOMER_HOME) || req.getServletPath().equals(CRAPaths.MANAGER_HOME) ||
                req.getServletPath().equals(CRAPaths.MASTER_HOME) || req.getServletPath().equals(CRAPaths.CREATE_ORDER)) {
            if(req.getServletPath().equals(CRAPaths.CREATE_ORDER)){
                req.getSession().setAttribute(Attributes.TO_CREATE_ORDER, req.getServletPath());
            }
            setMainBlock(req, CRA_JSPFiles.LOGIN_MAIN_BLOCK);
            resp.sendRedirect(req.getContextPath() + CRAPaths.LOGIN);
        } else {
            throw new AuthorizationException(req.getServletPath() + " page not found");
        }
    }
}
