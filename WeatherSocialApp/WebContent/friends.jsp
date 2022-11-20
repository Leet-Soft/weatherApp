<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="uni.fmi.masters.entity.RequestEntity"%>
<%@page import="uni.fmi.masters.entity.UserEntity"%>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Weather App</title>

    <meta name="apple-mobile-web-app-capable" content="yes">

    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">

    <link href="assets/css/bootstrap.min.css" rel="stylesheet">
    <link href="assets/css/font-awesome.min.css" rel="stylesheet">
    <link href="assets/css/main.css" rel="stylesheet">

    <script src="js/vendor/jquery/jquery-1.12.1.min.js"></script>
    <script src="js/vendor/bootstrap/bootstrap.min.js"></script>
    <script src="js/main.js"></script>


</head>
<body>
<%
	ArrayList<RequestEntity> friends = 
		(ArrayList<RequestEntity>)request.getAttribute("userFriends");

	ArrayList<UserEntity> searchResult = 
			(ArrayList<UserEntity>)request.getAttribute("searchResult");

	if(searchResult == null)
		searchResult = new ArrayList<>();

%>

<div class="friends-page">
	<c:set scope="session" var="navLocation" value="friends"></c:set>

    <%@ include file="header.jsp" %>


    <div class="container">


        <div class="row" style="margin-top:30px;">

            <div class="col-sm-4">

                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">Добави приятел</h3>
                    </div>
                    <div class="panel-body">
					<form action="HelloServlet?action=search" method="post">
                        <div class="input-group">
                            <input type="text" class="form-control" name="search-name" id="search-friend" placeholder="Търси по име">
                            <span class="input-group-addon"><i class="fa fa-search"></i></span>
                        </div>
                        <div id="search-result"></div>
					</form>
                    </div>
                </div>
            </div>
            <div class="col-sm-8">

				<div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">Резултат</h3>
                    </div>

                    <table class="table table-striped table-bordered friends-table">
                        <thead>
                        <th>Снимка</th>
                        <th>Име</th>
                        <th class="hidden-xs">Email</th>
                        <th class="remove-col"><span class="hidden-xs">Покани</span></th>
                        </thead>
                        <tbody>     
                        
                        <% for(UserEntity user : searchResult) { %>                  
                       
	                        <tr>
	                            <td class="friend-img">
	                                <img class="img-thumbnail" src="assets/img/user.jpg">
	                            </td>
	                            <td>
	                                <span class="friend-name">
	                                   <%= user.getUsername() %>
	                                </span>
	                            </td>
	                            <td class="hidden-xs">
	                                <span class="friend-email">
	                            		<%= user.getEmail() %>
	                                </span>
	                            </td>
	                            <td>
	                                <button type="button" class="btn btn-info pull-right remove-friend">
	                                    <span class="hidden-xs" 
	                                    onclick="sendRequest(<%=user.getId()%>)"> Покани </span>
	                                </button>
	                            </td>
	                        </tr>
                     	<% } %>
                     
                        </tbody>
                    </table>
                </div>

                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">Списък с приятели</h3>
                    </div>

                    <table class="table table-striped table-bordered friends-table">
                        <thead>
                        <th>Снимка</th>
                        <th>Име</th>
                        <th class="hidden-xs">Email</th>
                        <th class="remove-col"><span class="hidden-xs">Премахни</span></th>
                        </thead>
                        <tbody>
                       
                       <c:forEach items="${friends}" var="fr">
	                        <tr>
	                            <td class="friend-img">
	                                <img class="img-thumbnail" src="assets/img/user.jpg">
	                            </td>
	                            <td>
	                                <span class="friend-name">
	                                    ${fr.fromUser.username}
	                                </span>
	                            </td>
	                            <td class="hidden-xs">
	                                <span class="friend-email">
	                                	${fr.fromUser.email} 
	                                </span>
	                            </td>
	                            <td>
	                                <button type="button" class="btn btn-danger pull-right remove-friend">
	                                    <span class="glyphicon glyphicon-remove"></span>
	                                    <span class="hidden-xs"> Премахни</span>
	                                </button>
	                            </td>
	                        </tr>
                     	</c:forEach>
                     
                        </tbody>
                    </table>
                </div>

            </div>
        </div>

    </div>

</div>


<script>
	function sendRequest(userId){
		
		$.ajax({
			method: "POST",
			url: "HelloServlet?action=sendRequest&userId=" + userId,
			success: function(data){
				window.location = data.url;
			}
			
		});
		
	}
 
</script>
</body>
</html>
