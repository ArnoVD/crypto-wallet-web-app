package domain.db;

import domain.model.Wallet;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class WalletDb {
    private final ArrayList<Wallet> wallet;

    public WalletDb() {
        wallet = new ArrayList<>();
        /* Dummy object added to the arraylist */
        Wallet wallet1 = new Wallet("BTC", "Bitmex", 0.345, 1092.35);
        wallet.add(wallet1);
    }

    public ArrayList<Wallet> getDb(){ return wallet; }

    public Double getTotal(){
        /* Reset total amount */
        double total = 0.00;
        for (Wallet w : wallet){
           total += w.getValue();
        }
        /* Round up to 2 decimals behind separator */
        BigDecimal bd = new BigDecimal(total).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    /* Calculate value of a certain crypto in dollars */
    public Double calcValue(String currency, double amount ){
        double value;
        switch(currency) {
            case "BTC":
                value = (amount * 11390);
                break;
            case "ETH":
                value = (amount * 375.71);
                break;
            case "XRP":
                value = (amount * 0.246121);
                break;
            default:
                return null;
        }
        return value;
    }

    public void addToWallet(Wallet walletItem){
        wallet.add(walletItem);
    }

    public Wallet findCrypto(String currency, String exchange) {
        if(currency != null || !currency.trim().isEmpty() &&
                exchange != null || !exchange.trim().isEmpty()){
            for (Wallet w : wallet){
                if(w.getCurrency().trim().equalsIgnoreCase(currency.trim()) &&
                        w.getExchange().trim().equalsIgnoreCase(exchange.trim())){
                    return w;
                }
            }
        }
        return null;
    }

    public Wallet findWalletById(String id) {
        for (Wallet w : wallet){
            if(w.getId() == Integer.parseInt(id)){
                return w;
            }
        }
        return null;
    }

    public void deleteCrypto(Wallet deletedCrypto) {
        int deletedId = deletedCrypto.getId();
        // Decrement all the ids higher than the id that is about to be deleted
        autoDecrementIds(deletedId);
        wallet.remove(deletedCrypto);
        // Reset the counter for the autoincrement
        deletedCrypto.resetIdCounter(wallet.size());
    }

    /* Decrement all the id's higher than the given id (auto increment) */
    public void autoDecrementIds(int deletedId){
        for (Wallet w : wallet){
            if(w.getId() > deletedId){
                w.decrementId(w.getId());
            }
        }
    }

    public void updateWallet(Wallet previousWallet, String newCurrency, String newExchange, double newAmount)  {
        previousWallet.setCurrency(newCurrency);
        previousWallet.setExchange(newExchange);
        previousWallet.setAmount(newAmount);
        previousWallet.setValue(calcValue(newCurrency, newAmount));
    }
}
