<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE HTML>
<html lang="nl">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Delete confirmation</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <link href='http://fonts.googleapis.com/css?family=Crete+Round' rel='stylesheet' type='text/css'>
</head>

<body>

<jsp:include page="header.jsp"/>

<main>
    <article>
        <div class="deleteConfirmationDiv">
            <h2 class="redText beautifulHeading">Are you sure you want to delete <span class="darkGrayText">(${wallet.currency}, ${wallet.exchange})</span> from your wallet?</h2>
            <form action="WalletInformatie?command=deleteConfirmation" method="POST" novalidate>
                <input type="hidden" name="id" value="${wallet.id}">
                <input type="submit" value="Yes" name="submit" class="deleteButton" id="deleteBtn">
                <input type="submit" value="Cancel" name="submit" class="deleteButton" id="cancelBtn">
            </form>
        </div>
    </article>
</main>

<jsp:include page="footer.jsp">
    <jsp:param name="" value=""/>
</jsp:include>

</body>
</html>

