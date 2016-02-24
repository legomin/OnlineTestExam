<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>end page</title>
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

<h1>end page</h1>

<c:choose>
    <c:when test="${not empty questionNumbesList}">
        <p>You don't answer all questions </p>
        <c:forEach items="${questionNumbesList}" var="numberQuestion">
            <a href="/spring-hibernate-mysql/krams/main/questions?question=${numberQuestion}">${numberQuestion}</a> &nbsp;
        </c:forEach>
        <form method="POST" action="/spring-hibernate-mysql/krams/main/submit">
            <p><input type="submit" value="submit anywere"></p>
        </form>
        <div>
            <span id="my_timer" style="color: #f00; font-size: 150%; font-weight: bold;">01:10:00</span>
            <script>
                timer();
            </script>
        </div>
    </c:when>
    <c:otherwise>
        <h2>test finished!!!</h2>
        <p>Your score: ${score} </p>
    </c:otherwise>
</c:choose>



</body>
</html>