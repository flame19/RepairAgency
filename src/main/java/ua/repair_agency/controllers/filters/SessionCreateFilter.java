package ua.repair_agency.controllers.filters;

import ua.repair_agency.constants.Attributes;
import ua.repair_agency.constants.CommonConstants;
import ua.repair_agency.constants.Role;
import ua.repair_agency.models.user.User;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "SessionCreator")
public class SessionCreateFilter extends AbstractFilter {
    @Override
    public void doCustomFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        if (req.getSession(false) == null) {
            setUnknownUserSession(req);
        }
        filterChain.doFilter(req, resp);
    }

    private void setUnknownUserSession(HttpServletRequest req) {
        HttpSession session = req.getSession(true);
        User user = new User.UserBuilder().
                setLanguage(defineLanguageForUser(req)).
                setRole(Role.UNKNOWN).
                build();
        session.setAttribute(Attributes.USER, user);
    }

    private String defineLanguageForUser(HttpServletRequest req) {
        String language = tryGetLanguageFromCookie(req);
        if (language != null) {
            return language;
        } else {
            return CommonConstants.EN;
        }
    }

    private String tryGetLanguageFromCookie(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(Attributes.LANGUAGE)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}