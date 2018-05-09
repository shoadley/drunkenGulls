package ui;

import datalayer.LikeDao;
import datalayer.StoryDao;
import datalayer.UniqueIdDao;
import datalayer.UserDao;
import models.StoryModel;
import models.UserModel;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.logging.Logger;

public class ViewUserStoriesServlet extends javax.servlet.http.HttpServlet {
    private Logger logger = Logger.getLogger(getClass().getName());

    /**
     * The post method is called by the browser when the user presses the button
     *
     * @param request The request has info on filled in fields and button presses.
     * @param response We use this to give the browser a response.
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logRequestParameters(request);  // Just to help with debugging.

        // Let's grab some data from the request.  It may or may not be
        // there depending on what the user click/entered.

        UserModel user = loadUserFromRequest(request);
        String storyText=request.getParameter("storyText");
        String submitButtonValue = request.getParameter("submitButton");
        String deleteButtonName = getButtonNameGivenValue(request, "Delete");  // button name is storyId
        String likeButtonName = getButtonNameGivenValue(request, "Like");  // button name is storyId


        // Maybe the user hit the view button.  If so we're going to another page.
        String viewButtonName = getButtonNameGivenValue(request, "View");
        if (viewButtonName != null) {
            handleViewButton(request, response, user, viewButtonName);
            return;
        }

        // Maybe the user hit the delete button.  The button name is the story ID.
        if (deleteButtonName != null) {
            int storyId = Integer.parseInt(deleteButtonName);
            StoryDao.deleteStory(storyId);
        }

        // Maybe the user hit the LIKE button.  The button name is the story ID.
        else if (likeButtonName != null) {
            int storyId = Integer.parseInt(likeButtonName);
            likeStory(user, storyId);
        }

        // Maybe the user submitted a story
        else if (submitButtonValue != null && submitButtonValue.equals("Submit")){
            addStory(user, storyText);
        }

        // Load any data we need on the page into the request.
        // We're going to stay on this page.
        request.setAttribute("user", user);
        loadStoriesIntoRequest(request);

        // Show the page
        RequestDispatcher dispatcher=request.getRequestDispatcher("/viewstories.jsp");
        dispatcher.forward(request, response);
    }

    // LIKE the story
    private void likeStory(UserModel user, int storyId) {
        LikeDao.saveLike(storyId, user.getUsername());
    }

    private void handleViewButton(HttpServletRequest request, HttpServletResponse response, UserModel user, String viewButtonName) throws ServletException, IOException {
        int storyId = Integer.parseInt(viewButtonName);
        StoryModel story = StoryDao.getStory(storyId);

        // Store in the session the id of the story that we're going to view.
        // Data stored in the session will be there across many browser-server requests.
        request.getSession().setAttribute("storyid", storyId);

        // Load any data we need to use in the JSP page into the request.
        request.setAttribute("user", user);
        request.setAttribute("story", story);
        loadCommentsOnStoryIntoRequest(request, storyId);

        RequestDispatcher dispatcher=request.getRequestDispatcher("/viewstory.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Grab the username from the request and create a user model.
     */
    private UserModel loadUserFromRequest(HttpServletRequest request) {
        //String username=request.getParameter("username");
        String username = (String) request.getSession().getAttribute("username");
        String password = (String) request.getSession().getAttribute("password");
        UserModel user = UserDao.getUser(username, password);

        // If there is no user for some weird reason, just use anonymous.
        if (user == null) {
            user = new UserModel();
            user.setUsername("anonymous");
        }

        return user;
    }

    /**
     * The get method is called if the user directly invokes the URI.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Before we go the page to display the stories, we need to get the stories.
        // And then shove the stories in to the request.
        UserModel user = loadUserFromRequest(request);

        // Load and data that the JSP page is expecting.
        request.setAttribute("user", user);
        loadStoriesIntoRequest(request);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/viewuserstories.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Retrieve all the stories and put them in the request.
     * We can then use then in the JSP file.
     *
     * @param request
     */
    private void loadStoriesIntoRequest(HttpServletRequest request) {
        ArrayList<StoryModel> storiesList = StoryDao.getStories();

        // We're going to convert the array list to an array because it works better in the JSP.
        StoryModel[] stories = storiesList.toArray(new StoryModel[storiesList.size()]);
        request.setAttribute("stories", stories);
    }

    private void loadCommentsOnStoryIntoRequest(HttpServletRequest request, int storyId) {
        ArrayList<StoryModel> storiesList = StoryDao.getStoriesThatAreComments(storyId);

        // We're going to convert the array list to an array because it works better in the JSP.
        StoryModel[] stories = storiesList.toArray(new StoryModel[storiesList.size()]);
        request.setAttribute("storycomments", stories);
    }

    /**
     * Save a story.
     */
    private void addStory(UserModel user, String storyText) {
        if (storyText != null && storyText.length() > 0 && user != null) {
            StoryDao.saveStory(UniqueIdDao.getID(), storyText, user.getUsername(), 0);
        }
    }

    /**
     * This method is useful in debugging what you got back in the
     * response from the user.
     *
     * @param request
     */
    private void logRequestParameters(HttpServletRequest request) {
        Enumeration<String> params = request.getParameterNames();
        while(params.hasMoreElements()){
            String paramName = params.nextElement();
            logger.info("Parameter Name - "+paramName+", Value - "+request.getParameter(paramName));
        }
    }

    private String getButtonNameGivenValue(HttpServletRequest request, String buttonValue) {
        // Might be selling a dorm.
        Enumeration<String> params = request.getParameterNames();

        while(params.hasMoreElements()) {
            String paramName = params.nextElement();
            String paramValue = request.getParameter(paramName);
            if (request.getParameter(paramName).equals(buttonValue)) {
                return paramName;
            }
        }

        return null;
    }

    private void dispatchToViewStoriesJsp(HttpServletRequest request, HttpServletResponse response, String user) throws ServletException, IOException {
        // Load any data we need on the page into the request.
        request.setAttribute("user", user);
        loadStoriesIntoRequest(request);

        // Show the page
        RequestDispatcher dispatcher=request.getRequestDispatcher("/viewstories.jsp");
        dispatcher.forward(request, response);
    }

}
