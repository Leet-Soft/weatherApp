<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.ArrayList"%>
<%@page import="uni.fmi.masters.entity.StatusEntity"%>

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
	ArrayList<StatusEntity> statuses = 
		(ArrayList<StatusEntity>)request.getAttribute("statuses");
%>

<div class="home-page">

	<c:set scope="session" var="navLocation" value="home"></c:set>

    <%@ include file="header.jsp" %>

        <div class="container">


            <div class="row" style="margin-top:30px;">

                <div class="col-sm-4">

                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title">Отбележи място и напиши коментар</h3>
                        </div>
                        <div class="panel-body">
                            <div class="form-group">
                                <div class="row">
                                    <div class="col-sm-4">
                                        <img class="thumbnail img-responsive" src="assets/img/icons/clear_sky.png">
                                    </div>
                                    <div class="col-sm-8">
                                        <div>
                                            <span class="label label-success"><span class="current-temp">27</span> ℃</span>
                                        </div>
                                        <label for="select-city">Избери град:</label>
                                        <select id="select-city" class="form-control">
                                            <option value="0" selected>Всички</option>
                                            <option value="Sofia">София</option>
                                            <option value="Plovdiv">Пловдив</option>
                                            <option value="Varna">Варна</option>
                                            <option value="Burgas">Бургас</option>
                                            <option value="StaraZagora">Стара Загора</option>
                                            <option value="Ruse">Русе</option>
                                            <option value="Pleven">Плевен</option>
                                            <option value="Dobrich">Добрич</option>
                                            <option value="Sliven">Сливен</option>
                                            <option value="Shumen">Шумен</option>
                                            <option value="Pernik">Перник</option>
                                            <option value="Pazardjik">Пазарджик</option>
                                            <option value="Qmbol">Ямбол</option>
                                            <option value="Haskovo">Хасково</option>
                                            <option value="Blagoevgrad">Благоевград</option>
                                            <option value="VelikoTarnovo">Велико Търново</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="row">
                                   <div class="col-sm-12">
                                       <label for="comment">Коментар:</label>
                                       <textarea class="form-control" rows="3" id="comment"></textarea>
                                   </div>
                                </div>

                            </div>
                        </div>
                        <div class="panel-footer" style="height:55px;">
                            <button type="button" onClick="addComment();" class="btn btn-primary pull-right publish"><span class="glyphicon glyphicon-globe"></span> Публикувай</button>
                        </div>
                    </div>
                </div>
                <div class="col-sm-8">

                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title">Твоите отбелязвания</h3>
                        </div>

                        <ul class="list-group" style="min-height:241px;">

						<%
							for(StatusEntity sta : statuses) {
						%>
                            <li class="list-group-item">
                                <div class="row">
                                    <div class="col-sm-2 col-xs-3">
                                        <img src="assets/img/icons/clear_sky.png" class="thumbnail img-responsive">
                                    </div>
                                    <div class="col-sm-7 col-xs-6">
                                        <span class="label label-success"><span class="current-temp"><%= sta.getTemp() %></span> ℃</span>
                                        <h4><%= sta.getCity() %></h4>
                                        <p><%= sta.getDescription() %></p>
                                    </div>
                                    <div class="col-sm-3 col-xs-3">
                                        <button type="button" class="btn btn-danger pull-right remove-post"><span class="glyphicon glyphicon-remove"></span><span class="hidden-xs"> Изтрий</span></button>
                                    </div>
                                </div>
                            </li>     
                         <% } %>                     
                            
                        </ul>
                    </div>

                </div>
            </div>


        </div>

</div>


<script> 
    function addComment(){
    	
    	var city = $('#select-city').val();
    	var comment = $('#comment').val();
    	
    	var url = "http://api.openweathermap.org/data/2.5/weather?q="
    				+ city 
    				+"&appid=4ddc620cf659bfe28db6d4012c9fc208"
    				+ "&units=metric";
    	$.ajax({
    		method: "GET",
    		url: url,
    		dataType: "json"
    	}).done(function(response){
    		
    		var temp = response.main.temp;
    		
    		var newUrl = "HelloServlet?action=addComment" +
    				"&city=" + city +
    				"&temp=" + temp +
    				"&comment=" + comment;   		
    				
    		
    		$.ajax({
    			method: "GET",
    			url: newUrl    			
    		}).done(function(response){
    			if(response === "completed"){
    				 location.reload();
    			}
    		});
    		
    		
    		
    	}).fail(function(){
    		console.log("something broke... :(");
    	});
    	
    }
</script>
</body>
</html>
