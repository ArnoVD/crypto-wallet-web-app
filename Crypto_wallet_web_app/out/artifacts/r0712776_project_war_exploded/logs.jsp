<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE HTML>
<html lang="nl">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>My wallet</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <link href='http://fonts.googleapis.com/css?family=Crete+Round' rel='stylesheet' type='text/css'>
</head>

<body>

<jsp:include page="header.jsp">
    <jsp:param name="actual" value="Logs"/>
</jsp:include>

<main>
    <article class="search-table-outter wrapper">
        <h2 class="firstHeading beautifulHeading">Logs.</h2>

        <table id="overviewTable" class="styled-table">
            <thead>
            <tr>
                <th>#</th>
                <th>Date & Time</th>
                <th>Activity</th>
            </tr>
            </thead>
            <tbody>

            <c:choose>
                <c:when test="${not empty logs}">
                    <c:forEach var="log" items="${logs}">
                        <tr>
                            <td>${log.id}</td>
                            <td>${log.logTime}</td>
                            <td>${log.activity}</td>
                        </tr>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <h2 class="redText beautifulHeading">There are currently no logs.</h2>
                </c:otherwise>
            </c:choose>

            </tbody>
            <tfoot class="tableBottomBorder">
            <tr class="lastTableRow">
                <th id="total" colspan="2">Total logs:</th>
                <td>${logs.size()}</td>
            </tr>
            </tfoot>
        </table>
    </article>
</main>

<jsp:include page="footer.jsp">
    <jsp:param name="" value=""/>
</jsp:include>

</body>
</html>
