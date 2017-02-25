package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.AuthorizedUser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;


/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class UserServlet extends HttpServlet {
    private static final Logger LOG = getLogger(UserServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("forward to users");
        request.getRequestDispatcher("/users.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("switching user");
        String action = req.getParameter("action");
        if (action.equals("switchUser")) {
            String userType = req.getParameter("userSelect");
            String redirectUri = ServletExtractorUtil.extractOrDefault(req, "redirectUri", "/");
            switch (userType) {
                case "admin":
                    AuthorizedUser.setAdmin();
                    req.getSession().setAttribute("userType", "admin");
                    resp.sendRedirect(redirectUri);
                    break;
                case "user":
                    AuthorizedUser.setUser();
                    req.getSession().setAttribute("userType", "user");
                    resp.sendRedirect(redirectUri);
                    break;
                default:
                    resp.sendError(400, "Illegal user type");
                    break;
            }
        } else {
            resp.sendError(400, "Action not supported");
        }
    }
}
