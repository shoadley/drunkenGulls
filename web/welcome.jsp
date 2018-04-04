<%--@ page import="com.endicott.edu.ui.UiMessage" %>
<%@ page import="com.endicott.edu.xxxxmodels.CollegeModel" --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<title>Endicott Unhappiness Project</title>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<link rel="stylesheet" href = "resources/style.css">
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">

<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r" crossorigin="anonymous">

<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>

<link href="https://fonts.googleapis.com/css?family=Shadows+Into+Light" rel="stylesheet">
<style>
  body {
    font-family: 'Shadows Into Light', cursive;
  }
</style>
</head>
<body>
<%--
  UiMessage msg = (UiMessage) request.getAttribute("message");
  if (msg == null) {
      msg = new UiMessage();
      msg.setMessage("");
  }
  CollegeModel college = (CollegeModel) request.getAttribute("college");
  if (college == null) {
      college = new CollegeModel();
  }
  String hostOfSim = (String) request.getAttribute("host");
  if (hostOfSim == null) {
    hostOfSim = "http://localhost:8080/enccollegesim/";
  } else {
    hostOfSim = hostOfSim;
  }
--%>
<p></p>
<p></p>
<div class="container">

  <form action="welcome" method="post">
  <div class="jumbotron">
    <h1>Drunken Gulls</h1>
    <p>Stories of Endicott Students at the Bottom of a Glass</p>
    <div class="form-group">
      <input type="text" name="username" class="form-control" id="username" placeholder="User ID or Be Anonymous below. ">
    </div>
    <div class="container">
      <input type="submit" class="btn btn-success" name="button" value="Log In">
      <input type="submit" class="btn btn-primary" name="button" value="Create Account">
      <input type="submit" class="btn btn-info" name="button" value="Be Anonymous">
    </div>
  </div>

<div class="container">
  <!-- Display a message if defined -->
  <%-- if (msg.getMessage().length() > 0) { %>
  <div class="alert alert-danger">
    <strong>Info</strong> <%=msg.getMessage()%>
  </div>
  <% } --%>

</form>
</div>
</body>
</html>
