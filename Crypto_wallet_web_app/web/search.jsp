<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE HTML>
<html lang="nl">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Search wallet</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <link href='http://fonts.googleapis.com/css?family=Crete+Round' rel='stylesheet' type='text/css'>
</head>

<body>

<jsp:include page="header.jsp">
    <jsp:param name="actual" value="Search"/>
</jsp:include>

<main>
    <article>
        <h2 class="firstHeading beautifulHeading">Search for crypto in your wallet.</h2>

        <c:if test="${not empty errors}">
            <div class="alert">
                <ul id="errorsList">
                    <c:forEach items="${errors}" var="error">
                        <li>${error}</li>
                    </c:forEach>
                </ul>
            </div>
        </c:if>

        <form method="Get" action="WalletInformatie" class="addToWalletFormDiv" novalidate>
            <p class="form-group">
                <label class="control-label ${currencyClass}" for="currency">Currency: </label> <input
                    id="currency" name="currency" type="text" value="${currencyPreviousValue}">
            </p>
            <p class="form-group">
                <label class="control-label ${exchangeClass}" for="exchange">Exchange: </label> <input
                    id="exchange" name="exchange" type="text" value="${exchangePreviousValue}">
            </p>
            <!-- Command wordt via input meegegeven aangezien command overgeschreven
            zou worden als deze in action gezet zou worden. -->
            <input type="hidden" name="command" value="searchCrypto">
            <p>
                <label for="search">&nbsp;</label> <input id="search" type="submit" value="Search Crypto" class="submitButton">
            </p>
        </form>

    </article>
</main>

<jsp:include page="footer.jsp">
    <jsp:param name="" value=""/>
</jsp:include>

</body>
</html>
