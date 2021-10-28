package domain.model;

import domain.DomainException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Wallet {
    private int id;
    private static int tId=0;
    private String currency, exchange;
    private double amount, value;

    public Wallet (String currency, String exchange, double amount, double value) {
        setId();
        setCurrency(currency);
        setExchange(exchange);
        setAmount(amount);
        setValue(value);
    }

    public Wallet() {
    }

    public int getId() {
        return id;
    }

    public void setId() {
        tId++;
        this.id = tId;
    }

    public void decrementId(int id){
        this.id = id - 1;
    }

    public void resetIdCounter(int newIdCounter){
        tId = newIdCounter;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        if (isValidString(currency))
            this.currency = currency;
        else throw new DomainException("No valid currency.");
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        if (isValidString(exchange))
            this.exchange = exchange;
        else throw new DomainException("No valid exchange.");
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        if (isValidDouble(amount))
            this.amount = amount;
        else throw new DomainException("No valid amount.");
    }

    public double getValue() {
        /* Round up to 2 decimals behind separator */
        BigDecimal bd = new BigDecimal(value).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public void setValue(double value) {
        if (isValidDouble(value))
            this.value = value;
    }

    public static boolean isValidString(String input) {
        return (input != null) && !(input.trim().isEmpty());
    }

    public static boolean isValidDouble(double x) {
        return x > 0;
    }
    
}
