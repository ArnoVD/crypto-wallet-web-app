<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE HTML>
<html lang="nl">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Update confirmation</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <link href='http://fonts.googleapis.com/css?family=Crete+Round' rel='stylesheet' type='text/css'>
</head>

<body>

<jsp:include page="header.jsp"/>

<main>
    <article>
        <h2 class="firstHeading beautifulHeading"><span class="blueText">Update</span> crypto currency in your wallet.</h2>

        <c:if test="${not empty errors}">
            <div class="alert">
                <ul id="errorsList">
                    <c:forEach items="${errors}" var="error">
                        <li>${error}</li>
                    </c:forEach>
                </ul>
            </div>
        </c:if>

        <form action="WalletInformatie?command=updateConfirmation" method="POST" class="addToWalletFormDiv" novalidate>

            <input type="hidden" id="previousId" name="previousId" value="${previousId}">

            <label for="currency" class="currencyClass">Current currency:</label>
            <select id="currency" name="currency">
                <option value="${previousWallet.currency}">${previousWallet.currency}</option>
                <option value="">..</option>
                <option value="BTC">BTC</option>
                <option value="ETH">ETH</option>
                <option value="XRP">XRP</option>
            </select>

            <label for="exchange" class="exchangeClass">Current exchange:</label>
            <input type="text" id="exchange" name="exchange" value="${previousWallet.exchange}" required>

            <label for="amount" class="amountClass">Current amount:</label>
            <input type="text" id="amount" name="amount" value="${previousWallet.amount}" required>

            <input type="submit" value="Update" class="submitButton"/>
        </form>

    </article>
</main>

<jsp:include page="footer.jsp">
    <jsp:param name="" value=""/>
</jsp:include>

</body>
</html>

