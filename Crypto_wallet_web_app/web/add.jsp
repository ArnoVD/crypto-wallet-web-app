<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE HTML>
<html lang="nl">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Add to wallet</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <link href='http://fonts.googleapis.com/css?family=Crete+Round' rel='stylesheet' type='text/css'>
</head>

<body>

<jsp:include page="header.jsp">
    <jsp:param name="actual" value="Add"/>
</jsp:include>

<main>
    <article>
            <h2 class="firstHeading beautifulHeading"><span class="blueText">Add</span> crypto currency to your wallet.</h2>

        <c:if test="${not empty errors}">
            <div class="alert">
                <ul id="errorsList">
                    <c:forEach items="${errors}" var="error">
                        <li>${error}</li>
                    </c:forEach>
                </ul>
            </div>
        </c:if>

            <div class="addToWalletFormDiv">
                <form action="WalletInformatie?command=addCrypto" method="post" novalidate>

                    <label for="currency" class="${currencyClass}">Select a currency:</label>
                    <select id="currency" name="currency">
                        <option value="${currencyPreviousValue}">${currencyPreviousValue}</option>
                        <option value="BTC">BTC</option>
                        <option value="ETH">ETH</option>
                        <option value="XRP">XRP</option>
                    </select>

                    <c:choose>
                        <c:when test="${not empty exchangePreviousValue}">
                            <label for="exchangeName" class="${exchangeClass}">Choose the exchange:</label>
                            <input type="text" id="exchangeName" name="exchangeName" value="${exchangePreviousValue}" required>
                        </c:when>
                        <c:otherwise>
                            <label for="exchangeName">Choose the exchange:</label>
                            <input type="text" id="exchangeName" name="exchangeName" value="${cookie["favExchange"].value}" required>
                        </c:otherwise>
                    </c:choose>

                    <input type="checkbox" id="favExchange" name="favExchange" value="yes">
                    <label for="favExchange">Mark exchange as <span class="blueText">favorite</span>?</label><br><br>

                    <label for="exchangeAmount" class="${amountClass}">Choose the amount:</label>
                    <input type="text" id="exchangeAmount" name="exchangeAmount" value="${amountPreviousValue}" required>

                    <input type="submit" value="Add" class="submitButton"/>
                </form>
            </div>
    </article>
</main>

<jsp:include page="footer.jsp">
    <jsp:param name="" value=""/>
</jsp:include>

</body>
</html>