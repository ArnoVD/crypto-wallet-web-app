package ui.controller;

import domain.DomainException;
import domain.db.WalletDb;
import domain.model.Log;
import domain.model.Wallet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@WebServlet("WalletInformatie/")
public class WalletInformatie extends HttpServlet {

    WalletDb walletDb = new WalletDb();
    double value = 0.0;

    public WalletInformatie() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String destination;
        String command = request.getParameter("command");
        if(command == null)
            command = "";

        switch (command){
            case "addCrypto":
                destination = addCrypto(request, response);
                break;
            case "showAddCrypto":
                destination = showAddCrypto(request, response);
                break;
            case "searchCrypto":
                destination = searchCrypto(request);
                break;
            case "emptySearch":
                destination = showSearch(request);
                break;
            case "overview":
                destination = overview(request);
                break;
            case "deleteCrypto":
                destination = deleteCrypto(request);
                break;
            case "deleteConfirmation":
                destination = deleteConfirmation(request);
                break;
            case "updateCrypto":
                destination = updateCrypto(request);
                break;
            case "updateConfirmation":
                destination = updateConfirmation(request);
                break;
            case "log":
                destination = logsOverview();
                break;
            default:
                request.setAttribute("total", walletDb.getTotal());
                addLogToSession(request, "Visited home.");
                destination = "index.jsp";
        }

        request.getRequestDispatcher(destination).forward(request, response);
    }

    private String showAddCrypto(HttpServletRequest request, HttpServletResponse response) {
        addLogToSession(request, "Visited add to crypto page.");
        Cookie cookie = getCookieWithKey(request);

        if(cookie != null){
            response.addCookie(cookie);
        }

        return "add.jsp";
    }

    private String showSearch(HttpServletRequest request) {
        addLogToSession(request, "Visited search.");
        return "search.jsp";
    }

    private String deleteConfirmation(HttpServletRequest request) {
        addLogToSession(request, "Deleted a crypto.");

        if (request.getParameter("submit").equals("Yes")) {
            String id = request.getParameter("id");
            Wallet deletedCrypto = walletDb.findWalletById(id);
            walletDb.deleteCrypto(deletedCrypto);
        }
        return overview(request);
    }

    private String deleteCrypto(HttpServletRequest request) {
        addLogToSession(request, "Crypto chosen to be removed.");

        String id = request.getParameter("id");

        Wallet wallet = walletDb.findWalletById(id);

        request.setAttribute("wallet", wallet);
        return "deleteConfirmation.jsp";
    }

    private String updateCrypto(HttpServletRequest request) {
        addLogToSession(request, "Crypto chosen to be updated.");

        String id = request.getParameter("id");

        Wallet w = walletDb.findWalletById(id);

        /* Remember previous id to be updated */
        request.setAttribute("previousId", id);
        request.setAttribute("previousWallet", w);

        return "updateConfirmation.jsp";
    }

    private String updateConfirmation(HttpServletRequest request) {
        addLogToSession(request, "Updated a crypto.");

        String previousId = request.getParameter("previousId");
        request.setAttribute("previousId", previousId);

        Wallet previousWallet = walletDb.findWalletById(previousId);

        ArrayList<String> errors = new ArrayList<>();
        Wallet newWallet = new Wallet();
        setCurrency(newWallet, request, errors);
        setExchange(newWallet, request, errors, "exchange");
        setAmount(newWallet, request, errors, "amount");

        if (errors.size() == 0) {
            try {
                /* Calculate/Set value only if there are no errors */
                setValue(newWallet, request, "amount");
                walletDb.updateWallet(previousWallet, newWallet.getCurrency(), newWallet.getExchange(), newWallet.getAmount());
                return overview(request);
            } catch (DomainException exc){
                request.setAttribute("error", exc.getMessage());
                return "updateConfirmation.jsp";
            }
        } else {
            request.setAttribute("errors", errors);
            return "updateConfirmation.jsp";
        }
    }

    private String overview(HttpServletRequest request) {
        addLogToSession(request, "Viewed the overview.");

        request.setAttribute("wallet", walletDb.getDb());
        request.setAttribute("total", walletDb.getTotal());

        return "overview.jsp";
    }

    private String searchCrypto(HttpServletRequest request) {
        addLogToSession(request, "Tried to search for a crypto.");

        ArrayList<String> errors = new ArrayList<>();
        String currency, exchange, destination;

        Wallet wallet;
        currency = getCurrencyAndCheckOnNullOrEmpty(request, errors);
        exchange = getExchangeAndCheckOnNullOrEmpty(request, errors);

        if (errors.size() == 0) {
            try {
                wallet = walletDb.findCrypto(currency, exchange);
                if (wallet != null) {
                    request.setAttribute("wallet", wallet);
                    destination = "found.jsp";
                } else {
                    destination = "notFound.jsp";
                }
            } catch (DomainException exc){
                request.setAttribute("error", exc.getMessage());
                destination = "notFound.jsp";
            }
        } else {
            request.setAttribute("errors", errors);
            return "search.jsp";
        }
        return destination;
    }

    private String addCrypto(HttpServletRequest request, HttpServletResponse response) {
        addLogToSession(request, "Added a crypto.");

        ArrayList<String> errors = new ArrayList<>();

        Wallet wallet = new Wallet();
        setCurrency(wallet, request, errors);
        setExchange(wallet, request, errors, "exchangeName");
        setAmount(wallet, request, errors, "exchangeAmount");

        String favExchange = request.getParameter("favExchange");
        String exchange = request.getParameter("exchangeName");

        Cookie c;

        /* If the favorite exchange selectbox is selected */
        if (favExchange != null) {
            c = new Cookie("favExchange", exchange);
        } else {
            c = getCookieWithKey(request);
        }

        if (errors.size() == 0) {
            try {
                /* Set id and calculate value only if there are no errors */
                wallet.setId();
                setValue(wallet, request, "exchangeAmount");

                walletDb.addToWallet(wallet);
                response.addCookie(c);

                return overview(request);
            } catch (DomainException exc){
                request.setAttribute("error", exc.getMessage());
                return "add.jsp";
            }
        } else {
            request.setAttribute("errors", errors);
            return "add.jsp";
        }
    }

    private String logsOverview(){ return "logs.jsp"; }

    private String getCurrencyAndCheckOnNullOrEmpty(HttpServletRequest request, ArrayList<String> errors) {
        String currency = request.getParameter("currency");

        if (currency != null && !currency.trim().isEmpty()) {
            request.setAttribute("currencyClass", "has-success");
            request.setAttribute("currencyPreviousValue", currency);
            return currency;
        } else {
            errors.add("No valid currency.");
            request.setAttribute("currencyClass", "has-error");
        }
        return currency;
    }

    private String getExchangeAndCheckOnNullOrEmpty(HttpServletRequest request, ArrayList<String> errors) {
        String exchange = request.getParameter("exchange");

        if (exchange != null && !exchange.trim().isEmpty()) {
            request.setAttribute("exchangeClass", "has-success");
            request.setAttribute("exchangePreviousValue", exchange);
            return exchange;
        } else {
            errors.add("No valid exchange.");
            request.setAttribute("exchangeClass", "has-error");
        }
        return exchange;
    }

    private void setCurrency(Wallet wallet, HttpServletRequest request, ArrayList<String> errors) {
        String currency = request.getParameter("currency");
        try {
            wallet.setCurrency(currency);
            request.setAttribute("currencyClass", "has-success");
            request.setAttribute("currencyPreviousValue", currency);
        } catch (DomainException exc){
            errors.add(exc.getMessage());
            request.setAttribute("currencyClass", "has-error");
        }
    }

    private void setExchange(Wallet wallet, HttpServletRequest request, ArrayList<String> errors, String parameterName) {
        String exchange = request.getParameter(parameterName);
        try {
            wallet.setExchange(exchange);
            request.setAttribute("exchangeClass", "has-success");
            request.setAttribute("exchangePreviousValue", exchange);
        } catch (DomainException exc){
            errors.add(exc.getMessage());
            request.setAttribute("exchangeClass", "has-error");
        }
    }

    private void setAmount(Wallet wallet, HttpServletRequest request, ArrayList<String> errors, String parameterName) {
        String amount = request.getParameter(parameterName);
        try {
            wallet.setAmount(Double.parseDouble(amount));
            request.setAttribute("amountClass", "has-success");
            request.setAttribute("amountPreviousValue", amount);
            /* Catch NumberFormatException for the double parse on setAmount() */
        } catch (DomainException | NumberFormatException exc){
            /* Error message wordt hier toegevoegd aangezien er twee exceptions gecatched kunnen worden. */
            errors.add("No valid amount.");
            request.setAttribute("amountClass", "has-error");
        }
    }

    /* setValue() moet geen catch voor een DomainException of NumberFormatException hebben aangezien dit
        een berekend veld is en pas uitgevoerd wordt wanneer de errors lijst gelijk is aan 0. --> Dit wil zeggen
        dat de waarde waarmee de value berekend wordt (amount) goedgekeurd is en geen errors heeft opgegooid.
     */
    private void setValue(Wallet wallet, HttpServletRequest request, String parameterName) {
        String currency = request.getParameter("currency");
        String amount = request.getParameter(parameterName);

        double amountNumber = Double.parseDouble(amount);
        value =  walletDb.calcValue(currency, amountNumber);

        wallet.setValue(value);
        request.setAttribute("valueClass", "has-success");
        request.setAttribute("valuePreviousValue", value);
    }

    private Cookie getCookieWithKey(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies == null){
            return null;
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("favExchange")){
                return cookie;
            }
        }

        return null;
    }

    public void addLogToSession(HttpServletRequest request, String activity){
        HttpSession session = request.getSession();
        /* Ignore warning "logs" is always a ArrayList of Log. */
        ArrayList<Log> logs = (ArrayList<Log>) session.getAttribute("logs");

        if(logs == null){
            logs = new ArrayList<>();
            session.setAttribute("logs", logs);
        }

        Date date = new Date();
        Log log = new Log(date.toString(), activity);
        logs.add(log);
    }
}