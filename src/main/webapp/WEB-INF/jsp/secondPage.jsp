<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Question № ${question.number}</title>
    <script>
        var seconds = ${duration};
        function timer() {
            seconds--;
            document.getElementById("my_timer").innerHTML = secondsToTime(seconds);

            if (seconds % 2 == 0) {
                aClient = new HttpClient();
                aClient.get('/spring-hibernate-mysql/krams/main/timer?duration='+seconds, function(response) {

                });
            }

            if (seconds <= 0) {
                alert("time is off!");
                document.location.replace('/spring-hibernate-mysql/krams/main/submit');
            } else {setTimeout(timer, 1000);}

        }

        function secondsToTime(sec) {
            s = sec % 60;
            m = ((sec - s) / 60) % 60;
            h = (sec - s - (m*60)) / 3600;
            if (h < 10) h = "0" + h;
            if (m < 10) m = "0" + m;
            if (s < 10) s = "0" + s;

            return h+":"+m+":"+s;
        }
        var HttpClient = function() {
            this.get = function(aUrl, aCallback) {
                var anHttpRequest = new XMLHttpRequest();
                anHttpRequest.onreadystatechange = function() {
                    if (anHttpRequest.readyState == 4 && anHttpRequest.status == 200)
                        aCallback(anHttpRequest.responseText);
                }

                anHttpRequest.open( "GET", aUrl, true );
                anHttpRequest.send( null );
            }
        }
    </script>
</head>
<body>

<h1>Question № ${question.number}</h1>


<form:form method="post" action="/spring-hibernate-mysql/krams/main/questions" commandName="ab">
    <form:hidden name="questionId" value="${question.id}" path="id"/>
    <form:checkboxes items="${answerList}" path="answer" itemLabel="text" itemValue="id" delimiter="<br/>"/>
    <p><input type="submit" name="submit"></p>
</form:form>

<form method="get" action="/spring-hibernate-mysql/krams/main/questions">
    <input type="submit" name="submit" value="go to">
    <select size="1" name="question">
        <c:forEach items="${questionNumbesList}" var="numberQuestion">
            <option value="${numberQuestion.number}">${numberQuestion.number}</option>
        </c:forEach>
    </select>
    question
</form>

<form method="get" action="/spring-hibernate-mysql/krams/main/submit">
    <p><input type="submit" name="submit" value="end test"></p>
</form>
<div>
    <span id="my_timer" style="color: #f00; font-size: 150%; font-weight: bold;">01:10:00</span>
    <script>
        timer();
    </script>
</div>
</body>
</html>