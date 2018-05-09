package ui;

import datalayer.UserDao;
import models.UserModel;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.logging.Logger;

public class WelcomeServlet extends javax.servlet.http.HttpServlet {
    private Logger logger = Logger.getLogger(getClass().getName());

    /**
     * The post method is called by the browser when the user presses the button
     *
     * @param request The request has info on filled in fields and button presses.
     * @param response We use this to give the browser a response.
     * @throws javax.servlet.ServletException
     * @throws IOException
     */
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        UserModel user = null;

        // Load data from the request
        String buttonValue = request.getParameter("button");
        String username=request.getParameter("username");
        String password=request.getParameter("password");

        // Create an account
        if (buttonValue != null && buttonValue.equals("Create Account") && username != null && !username.isEmpty() && password != null && !password.isEmpty()){
            user = new UserModel();
            user.setUsername(username);
            user.setPassword(password);
            UserDao.saveUser(user);
            request.getSession().setAttribute("username", user.getUsername());
            request.getSession().setAttribute("password", user.getPassword());
        }

        // Or log in
        else if (buttonValue != null && buttonValue.equals("Log In")){
            user = UserDao.getUser(username, password);
            if (user == null || !user.getPassword().equals(password)) {
                String err = "Incorrect username and/or password.";
                request.setAttribute("errorMessage", err);
                // We don't know who this is.
                // We're going to stay on this page.
                RequestDispatcher dispatcher=request.getRequestDispatcher("/welcome.jsp");
                dispatcher.forward(request, response);
                return;
            }
            System.out.println(user.getPassword());
            System.out.println(user.getUsername());
            request.getSession().setAttribute("username", user.getUsername());
            request.getSession().setAttribute("password", user.getPassword());
        }

        // Or by anonymous
        else if (buttonValue != null && buttonValue.equals("Be Anonymous")){
            user = new UserModel();
            user.setUsername("anonymous");
            user.setPassword("1234");
            UserDao.saveUser(user);
        }

        // Load any data we need on the page into the request.
        request.setAttribute("user", user);

        // Show the stories page
        RequestDispatcher dispatcher=request.getRequestDispatcher("/viewStories");
        dispatcher.forward(request, response);
    }

    /**
     * The get method is invoked when the user goes to the page by browser URI.
     */
    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/welcome.jsp");
        dispatcher.forward(request, response);
    }

}
