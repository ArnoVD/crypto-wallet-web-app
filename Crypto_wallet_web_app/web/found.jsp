<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE HTML>
<html lang="nl">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Wallet found</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <link href='http://fonts.googleapis.com/css?family=Crete+Round' rel='stylesheet' type='text/css'>
</head>

<body>

<jsp:include page="header.jsp"/>


<main>
    <article>
        <h2 class="firstHeading beautifulHeading">Crypto <span class="blueText">${wallet.currency}</span>
            from exchange <span class="blueText">${wallet.exchange}</span> found!</h2>
    </article>
</main>

<jsp:include page="footer.jsp">
    <jsp:param name="" value=""/>
</jsp:include>

</body>
</html>
