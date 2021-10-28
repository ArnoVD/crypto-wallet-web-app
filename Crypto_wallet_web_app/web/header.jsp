<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<header>
    <div class="wrapper">
        <h1>Wallet<span class="color">.</span></h1>
        <nav class="navbar">
            <ul>
                <li ${param.actual eq 'Home'?"id = actual":""}>
                    <a href="WalletInformatie">Home</a>
                </li>
                <li ${param.actual eq 'Overview'?"id = actual":""}>
                    <a href="WalletInformatie?command=overview">My wallet</a>
                </li>
                <li ${param.actual eq 'Add'?"id = actual":""}>
                    <a href="WalletInformatie?command=showAddCrypto">Add to wallet</a>
                </li>
                <li ${param.actual eq 'Search'?"id = actual":""}>
                    <a href="WalletInformatie?command=emptySearch">Search for crypto</a>
                </li>
                <li ${param.actual eq 'Logs'?"id = actual":""}>
                    <a href="WalletInformatie?command=log">Logs</a>
                </li>
            </ul>
        </nav>
    </div>
</header>