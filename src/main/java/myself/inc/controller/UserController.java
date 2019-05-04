/*
 * Developed by Sumin Pavel on 5/2/19 11:23 PM
 */

package myself.inc.controller;

import myself.inc.dao.UserDao;
import myself.inc.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class UserController extends HttpServlet {

    private final String INSERT_OR_EDIT = "/user.jsp";
    private final String LIST_USER = "/listUser.jsp";
    private final UserDao userDao;

    public UserController() {
        this.userDao = new UserDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String forwardPath;
        final String action = req.getParameter("action");
        if (action != null) {
            switch (action) {
                case "listUser":
                    forwardPath = LIST_USER;
                    req.setAttribute("users", userDao.getAllUsers());
                    break;

                case "insert":
                    forwardPath = INSERT_OR_EDIT;
                    break;

                case "edit":
                    forwardPath = INSERT_OR_EDIT;
                    int userId = Integer.parseInt(req.getParameter("userId"));
                    User userToEdit = userDao.getUserById(userId);
                    req.setAttribute("user", userToEdit);
                    break;

                case "delete":
                    forwardPath = LIST_USER;
                    int id = Integer.parseInt(req.getParameter("userId"));
                    User user = userDao.getUserById(id);
                    if (user != null) userDao.deleteUser(user);
                    req.setAttribute("users", userDao.getAllUsers());
                    break;

                 default:
                     forwardPath = LIST_USER;
                     break;
            }
        } else {
            forwardPath = INSERT_OR_EDIT;
        }
        req.getRequestDispatcher(forwardPath).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String userIdString = req.getParameter("userId");
        final String firstName = req.getParameter("firstName");
        final String lastName = req.getParameter("lastName");
        final String dateOfBirthString = req.getParameter("dateOfBirth");
        final String email = req.getParameter("email");
        Date dateOfBirth = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            dateOfBirth = formatter.parse(dateOfBirthString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int userId = 0;
        if (userIdString != null && !userIdString.isEmpty()) userId = Integer.parseInt(userIdString);

        final User user = new User(
                userId,
                firstName,
                lastName,
                dateOfBirth,
                email
        );

        if (userId == 0) {
            userDao.addUser(user);
        } else {
            userDao.updateUser(user);
        }

        req.setAttribute("users", userDao.getAllUsers());
        req.getRequestDispatcher(LIST_USER).forward(req, resp);
    }
}
