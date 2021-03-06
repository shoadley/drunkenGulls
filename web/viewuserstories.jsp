<%@ page import="models.StoryModel" %>
<%@ page import="models.UserModel" %>
<%@ page import="datalayer.LikeDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<title>Drunken Tales</title>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<link rel="stylesheet" href="resources/style.css">
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
      integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">

<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css"
      integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r" crossorigin="anonymous">

<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"
        integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS"
        crossorigin="anonymous"></script>
<link href="https://fonts.googleapis.com/css?family=Shadows+Into+Light" rel="stylesheet">
<style>
    body {
        font-family: 'Shadows Into Light', cursive;
    }
</style>
</head>
<body>
<!-- Let's start by loading information we expect in the request.
     For any info missing, we'll just fake it.
  -->
<%
    UserModel user = (UserModel) request.getAttribute("user");
    if (user == null) {
        user = new UserModel();
        user.setUsername("anonymous");
    }

    StoryModel stories[] = (StoryModel[]) request.getAttribute("stories");
    if (stories == null) {
        stories = new StoryModel[0];
    }
%>
<p></p>
<p></p>
<div class="container">

    <form action="viewStories" method="post">

        <!-- Navigation Bar -->
        <nav class="navbar navbar-inverse">
            <div class="container-fluid">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                </div>
                <div class="collapse navbar-collapse" id="myNavbar">
                    <ul class="nav navbar-nav">
                        <li class="active"><a href="viewStories?username=<%=user.getUsername()%>">Tales</a></li>
                        <li class="active"><a href="viewUserStories"><%=user.getUsername()%></a></li>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="welcome"><span class="glyphicon glyphicon-log-out"></span>Logout</a></li>
                    </ul>
                </div>
            </div>
        </nav>

        <!-- Display a list of stories -->
        <div class="container">
            <div class="row">
                <div class="well well-sm">
                    <div class="pre-scrollable">

                        <ul class="list-group">
                            <%
                                for (int i = stories.length - 1; i >= 0; i--) {
                                    // Skip the story if it's a comment.
                                    if (stories[i].getCommentOnStoryID() != 0)
                                        continue;
                            %>
                            <li class="list-group-item">
                                <% if (stories[i].getUsername().equals(user.getUsername())) { %>
                                <% if (stories[i].getUsername().equals("anonymous")) { %>
                                <span class="glyphicon glyphicon-user"></span>
                                <% } else { %>
                                <span class="glyphicon glyphicon-user" style="color:seagreen" ><%=stories[i].getUsername()%></span>
                                <% } %>
                                <%=stories[i].getStory()%>
                                <button type="submit" class="btn btn-default btn-xs" name="<%=stories[i].getStoryId()%>" value="View">View</button>
                                <% if (stories[i].getUsername().equals(user.getUsername()) && !user.getUsername().equals("anonymous")) { %>
                                <button type="submit" class="btn btn-default btn-xs" name="<%=stories[i].getStoryId()%>" value="Delete">Delete</button>
                                <% } %>
<!-- LIKE-->                    <button type="submit" class="btn btn-default btn-xs" name="<%=stories[i].getStoryId()%>" value="Like">Like</button>
                                Likes: <%=LikeDao.getNumberOfLikes(stories[i].getStoryId())%>
                                <%
                                    }
                                %>
                            </li>
                            <%
                                }
                            %>
                        </ul>
                    </div>
                </div>
            </div>
        </div>

        <!-- This is a screet input to the post!  Acts as if the user
             had an input field with the username.
         -->
        <input type="hidden" name="username" value="<%=user.getUsername()%>">

    </form>
</div>
</body>
</html>
