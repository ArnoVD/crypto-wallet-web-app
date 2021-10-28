<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE HTML>
<html lang="nl">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <title>Wallet</title>
  <link rel="stylesheet" type="text/css" href="css/style.css">
  <link href='http://fonts.googleapis.com/css?family=Crete+Round' rel='stylesheet' type='text/css'>
</head>

<body>

<jsp:include page="header.jsp">
  <jsp:param name="actual" value="Home"/>
</jsp:include>

<main>
  <article>
    <div id="hero-image">
      <div class="wrapper">
        <h2 id="indexHeading"><strong>A Wallet, </strong><br/>
          for all your crypto currencies.</h2>
      </div>
    </div>
  </article>

  <article>
    <div class="cta">
      <div class="wrapper">
        <h2 class="beautifulHeading">Why choose our wallet?</h2>
        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec euismod leo a nibh dignissim tincidunt. Nam ultricies odio ac neque suscipit volutpat. Ut dictum adipiscing felis sed malesuada. Integer porta sem nec nibh hendrerit imperdiet. </p>
        <a href="WalletInformatie?command=showAddCrypto" class="button-2">Get Started</a>
      </div>
    </div>
  </article>

  <article>
    <div class="cta">
      <div class="wrapper">
        <h2 class="beautifulHeading">Your current balance:</h2>
        <p class="totalAmount">${total}$</p>
        <a href="WalletInformatie?command=overview" class="button-2">Go to full overview</a>
      </div>
    </div>
  </article>
</main>

<jsp:include page="footer.jsp">
  <jsp:param name="" value=""/>
</jsp:include>

</body>
</html>