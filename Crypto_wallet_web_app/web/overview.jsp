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
    <jsp:param name="actual" value="Overview"/>
</jsp:include>

<main>
    <article class="search-table-outter wrapper">
        <h2 class="firstHeading beautifulHeading">My wallet.</h2>

        <table id="overviewTable" class="styled-table">
            <thead>
            <tr>
                <th>#</th>
                <th>Currency</th>
                <th>Exchange</th>
                <th>Amount</th>
                <th>Value</th>
                <th>Update | Delete</th>
            </tr>
            </thead>
            <tbody>

            <c:choose>
                <c:when test="${not empty wallet}">
                    <c:forEach var="w" items="${wallet}">
                        <tr>
                            <td>${w.id}</td>
                            <td>${w.currency}</td>
                            <td>${w.exchange}</td>
                            <td>${w.amount}</td>
                            <td>${w.value}$</td>
                            <td>
                                <a class="updateLink" href="WalletInformatie?command=updateCrypto&id=${w.id}">Update</a>
                                 |
                                <a class="deleteLink" href="WalletInformatie?command=deleteCrypto&id=${w.id}">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <h2 class="redText beautifulHeading">There is no crypto added to your wallet.</h2>
                </c:otherwise>
            </c:choose>

            </tbody>
            <tfoot class="tableBottomBorder">
            <tr class="lastTableRow">
                <th id="total" colspan="5">Total :</th>
                <td>${total}$</td>
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

