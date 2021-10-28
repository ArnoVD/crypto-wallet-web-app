package ui.view;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WalletDbTest {

    //================================================================================
    // Setup
    //================================================================================

    private WebDriver driver;
    private final String url = "http://localhost:8080/r0712776_project/";

    @Before
    public void setUp()  {
        System.setProperty("webdriver.chrome.driver", "/Applications/chromedriver");
        driver = new ChromeDriver();
        driver.get("http://localhost:8080/r0712776_project/");
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    //================================================================================
    // Tests for the form / add functionalities
    //================================================================================

    @Test
    public void Test_Add_Crypto_To_Wallet_URL(){
        Assert.assertEquals(driver.getTitle(), "Wallet");
    }

    @Test
    public void Test_Empty_Form_For_Add_Crypto_To_Wallet_Form(){
        driver.get(url + "add.jsp");

        WebElement button = driver.findElement(By.className("submitButton"));
        button.click();

        Assert.assertEquals(driver.getTitle(), "Add to wallet");
        Assert.assertEquals("Wallet.", driver.findElement(By.tagName("h1")).getText());

        List<WebElement> list = driver.findElements(By.tagName("li"));

        assertEquals(loopThroughListAndFindError(list, "No valid currency."), "No valid currency.");
        assertEquals(loopThroughListAndFindError(list, "No valid exchange."), "No valid exchange.");
        assertEquals(loopThroughListAndFindError(list, "No valid amount."), "No valid amount.");
    }

    @Test
    public void Test_Filled_Form_For_Add_Crypto_To_Wallet_Form(){
        driver.get(url + "add.jsp");

        Select dropdown = new Select(driver.findElement(By.id("currency")));
        dropdown.selectByVisibleText("BTC");

        WebElement exchange = driver.findElement(By.id("exchangeName"));
        exchange.clear();
        exchange.sendKeys("Bitmex");

        WebElement amount = driver.findElement(By.id("exchangeAmount"));
        amount.clear();
        amount.sendKeys("0.345");

        WebElement favExchangeCheckBox = driver.findElement(By.id("favExchange"));
        favExchangeCheckBox.click();

        WebElement button = driver.findElement(By.className("submitButton"));
        button.click();

        Assert.assertEquals(driver.getTitle(), "My wallet");
        Assert.assertEquals("MY WALLET.", driver.findElement(By.tagName("h2")).getText());

        assertTrue(checkPageForTd(driver.findElements(By.tagName("td")), "BTC"));
        assertTrue(checkPageForTd(driver.findElements(By.tagName("td")), "Bitmex"));
        assertTrue(checkPageForTd(driver.findElements(By.tagName("td")), "0.345"));
    }

    @Test
    public void Test_Search_Wallet_Not_Found() {
        driver.get(url + "search.jsp");
        int rand =new Random().nextInt(999999);
        searchWallet("x"+rand, "y"+rand);
        assertEquals("Wallet not found", driver.getTitle());
        assertEquals("CRYPTO NOT FOUND.\n" +
                        "PLEASE CHECK THAT EVERYTHING IS FILLED IN CORRECTLY.",
                driver.findElement(By.tagName("h2")).getText());
    }

    @Test
    public void Test_Search_Wallet_From_Constructor_Found() {
        driver.get(url + "search.jsp");
        searchWallet("BTC", "Bitmex");
        assertEquals("Wallet found", driver.getTitle());
        assertEquals("CRYPTO BTC FROM EXCHANGE BITMEX FOUND!",
                driver.findElement(By.tagName("h2")).getText());
    }

    @Test
    public void Test_Search_Added_Wallet_Found() {
        driver.get(url + "add.jsp");
        addWallet();
        driver.get(url + "search.jsp");
        searchWallet("BTC", "Binance");
        assertEquals("Wallet found", driver.getTitle());
        assertEquals("CRYPTO BTC FROM EXCHANGE BINANCE FOUND!",
                driver.findElement(By.tagName("h2")).getText());
    }

    @Test
    public void Test_Currency_Alerts_On_Add_Crypto_To_Wallet_Page() {
        driver.get(url + "add.jsp");

        WebElement exchange = driver.findElement(By.id("exchangeName"));
        exchange.clear();
        exchange.sendKeys("Bitmex");

        WebElement amount = driver.findElement(By.id("exchangeAmount"));
        amount.clear();
        amount.sendKeys("0.345");

        WebElement button = driver.findElement(By.className("submitButton"));
        button.click();

        Assert.assertEquals(driver.getTitle(), "Add to wallet");
        Assert.assertEquals("Wallet.", driver.findElement(By.tagName("h1")).getText());

        List<WebElement> list = driver.findElements(By.tagName("li"));

        assertEquals(loopThroughListAndFindError(list, "No valid currency."), "No valid currency.");
    }

    @Test
    public void Test_Exchange_Alerts_On_Add_Crypto_To_Wallet_Page() {
        driver.get(url + "add.jsp");

        Select dropdown = new Select(driver.findElement(By.id("currency")));
        dropdown.selectByVisibleText("BTC");

        WebElement amount = driver.findElement(By.id("exchangeAmount"));
        amount.clear();
        amount.sendKeys("0.345");

        WebElement button = driver.findElement(By.className("submitButton"));
        button.click();

        Assert.assertEquals(driver.getTitle(), "Add to wallet");
        Assert.assertEquals("Wallet.", driver.findElement(By.tagName("h1")).getText());

        List<WebElement> list = driver.findElements(By.tagName("li"));

        assertEquals(loopThroughListAndFindError(list, "No valid exchange."), "No valid exchange.");
    }

    @Test
    public void Test_Amount_Alerts_On_Add_Crypto_To_Wallet_Page() {
        driver.get(url + "add.jsp");

        Select dropdown = new Select(driver.findElement(By.id("currency")));
        dropdown.selectByVisibleText("BTC");

        WebElement exchange = driver.findElement(By.id("exchangeName"));
        exchange.clear();
        exchange.sendKeys("Bitmex");

        WebElement button = driver.findElement(By.className("submitButton"));
        button.click();

        Assert.assertEquals(driver.getTitle(), "Add to wallet");
        Assert.assertEquals("Wallet.", driver.findElement(By.tagName("h1")).getText());

        List<WebElement> list = driver.findElements(By.tagName("li"));

        assertEquals(loopThroughListAndFindError(list, "No valid amount."), "No valid amount.");
    }

    //================================================================================
    // Tests for the update functionalities
    //================================================================================

    @Test
    public void Test_Empty_Update_Form_From_Update_Confirmation_Page() {
        driver.get(url + "overview.jsp");

        addTestWallet();

        WebElement link = driver.findElement(By.className("updateLink"));
        link.click();

        Assert.assertEquals(driver.getTitle(), "Update confirmation");

        Select dropdown = new Select(driver.findElement(By.id("currency")));
        dropdown.selectByVisibleText("ETH");

        WebElement exchange = driver.findElement(By.id("exchange"));
        exchange.clear();
        exchange.sendKeys("Binance");

        WebElement amount = driver.findElement(By.id("amount"));
        amount.clear();
        amount.sendKeys("1");

        WebElement button = driver.findElement(By.className("submitButton"));
        button.click();

        Assert.assertEquals(driver.getTitle(), "My wallet");
        Assert.assertEquals("MY WALLET.", driver.findElement(By.tagName("h2")).getText());

        assertTrue(checkPageForTd(driver.findElements(By.tagName("td")), "ETH"));
        assertTrue(checkPageForTd(driver.findElements(By.tagName("td")), "Binance"));
        assertTrue(checkPageForTd(driver.findElements(By.tagName("td")), "1.0"));
        assertTrue(checkPageForTd(driver.findElements(By.tagName("td")), "375.71$"));
    }

    @Test
    public void Test_Filled_Update_Form_From_Update_Confirmation_Page() {
        driver.get(url + "overview.jsp");

        addTestWallet();

        WebElement link = driver.findElement(By.className("updateLink"));
        link.click();

        Assert.assertEquals(driver.getTitle(), "Update confirmation");

        Select dropdown = new Select(driver.findElement(By.id("currency")));
        dropdown.selectByVisibleText("..");

        WebElement exchange = driver.findElement(By.id("exchange"));
        exchange.clear();
        exchange.sendKeys("");

        WebElement amount = driver.findElement(By.id("amount"));
        amount.clear();
        amount.sendKeys("");

        WebElement button = driver.findElement(By.className("submitButton"));
        button.click();

        Assert.assertEquals(driver.getTitle(), "Update confirmation");

        List<WebElement> list = driver.findElements(By.tagName("li"));

        assertEquals(loopThroughListAndFindError(list, "No valid currency."), "No valid currency.");
        assertEquals(loopThroughListAndFindError(list, "No valid exchange."), "No valid exchange.");
        assertEquals(loopThroughListAndFindError(list, "No valid amount."), "No valid amount.");
    }

    //================================================================================
    // Tests for the delete functionalities
    //================================================================================

    @Test
    public void Test_Delete_Link_From_Overview_Page(){
        driver.get(url + "WalletInformatie?command=overview");

        addTestWallet();

        WebElement link = driver.findElement(By.className("deleteLink"));
        link.click();

        Assert.assertEquals(driver.getTitle(), "Delete confirmation");
    }

    @Test
    public void Test_Delete_Functionality_From_Delete_A_Crypto_When_Clicked_Yes(){
        driver.get(url + "WalletInformatie?command=overview");

        addTestWallet();

        WebElement link = driver.findElement(By.className("deleteLink"));
        link.click();

        Assert.assertEquals(driver.getTitle(), "Delete confirmation");

        WebElement deleteButton = driver.findElement(By.id("deleteBtn"));
        deleteButton.click();

        Assert.assertEquals(driver.getTitle(), "My wallet");
        Assert.assertEquals("MY WALLET.", driver.findElement(By.tagName("h2")).getText());
    }

    @Test
    public void Test_Delete_Functionality_From_Delete_A_Crypto_When_Clicked_Cancel(){
        driver.get(url + "WalletInformatie?command=overview");

        addTestWallet();

        WebElement link = driver.findElement(By.className("deleteLink"));
        link.click();

        Assert.assertEquals(driver.getTitle(), "Delete confirmation");

        WebElement deleteButton = driver.findElement(By.id("cancelBtn"));
        deleteButton.click();

        Assert.assertEquals(driver.getTitle(), "My wallet");
        Assert.assertEquals("MY WALLET.", driver.findElement(By.tagName("h2")).getText());
    }

    //================================================================================
    // Tests for the search form / functionalities
    //================================================================================

    @Test
    public void Test_Filled_In_Search_And_Found_Page_From_Search_A_Crypto() {
        driver.get(url + "search.jsp");

        WebElement currency = driver.findElement(By.id("currency"));
        currency.clear();
        currency.sendKeys("BTC");

        WebElement exchange = driver.findElement(By.id("exchange"));
        exchange.clear();
        exchange.sendKeys("Bitmex");

        WebElement button = driver.findElement(By.className("submitButton"));
        button.click();

        Assert.assertEquals(driver.getTitle(), "Wallet found");
        Assert.assertEquals("CRYPTO BTC FROM EXCHANGE BITMEX FOUND!", driver.findElement(By.tagName("h2")).getText());
    }

    @Test
    public void Test_Filled_In_Search_And_Not_Found_Page_From_Search_A_Crypto() {
        driver.get(url + "search.jsp");

        WebElement currency = driver.findElement(By.id("currency"));
        currency.clear();
        currency.sendKeys("--Unknown currency--");

        WebElement exchange = driver.findElement(By.id("exchange"));
        exchange.clear();
        exchange.sendKeys("--Unknow exchange--");

        WebElement button = driver.findElement(By.className("submitButton"));
        button.click();

        Assert.assertEquals(driver.getTitle(), "Wallet not found");
        Assert.assertEquals("CRYPTO NOT FOUND.\n" +
                "PLEASE CHECK THAT EVERYTHING IS FILLED IN CORRECTLY.", driver.findElement(By.tagName("h2")).getText());
    }

    @Test
    public void Test_Empty_Search_And_Alerts_On_Search_Page(){
        driver.get(url + "search.jsp");

        WebElement button = driver.findElement(By.className("submitButton"));
        button.click();

        Assert.assertEquals(driver.getTitle(), "Search wallet");
        Assert.assertEquals("SEARCH FOR CRYPTO IN YOUR WALLET.", driver.findElement(By.tagName("h2")).getText());

        List<WebElement> list = driver.findElements(By.tagName("li"));

        assertEquals(loopThroughListAndFindError(list, "No valid currency."), "No valid currency.");

        assertEquals(loopThroughListAndFindError(list, "No valid exchange."), "No valid exchange.");
    }

    @Test
    public void Test_Currency_Alert_On_Search_Page() {
        driver.get(url + "search.jsp");

        WebElement exchange = driver.findElement(By.id("exchange"));
        exchange.clear();
        exchange.sendKeys("Bitmex");

        WebElement button = driver.findElement(By.className("submitButton"));
        button.click();

        Assert.assertEquals(driver.getTitle(), "Search wallet");
        Assert.assertEquals("SEARCH FOR CRYPTO IN YOUR WALLET.", driver.findElement(By.tagName("h2")).getText());

        List<WebElement> list = driver.findElements(By.tagName("li"));
        assertEquals(loopThroughListAndFindError(list, "No valid currency."), "No valid currency.");
    }

    @Test
    public void Test_Exchange_Alert_On_Search_Page() {
        driver.get(url + "search.jsp");

        WebElement currency = driver.findElement(By.id("currency"));
        currency.clear();
        currency.sendKeys("BTC");

        WebElement button = driver.findElement(By.className("submitButton"));
        button.click();

        Assert.assertEquals(driver.getTitle(), "Search wallet");
        Assert.assertEquals("SEARCH FOR CRYPTO IN YOUR WALLET.", driver.findElement(By.tagName("h2")).getText());

        List<WebElement> list = driver.findElements(By.tagName("li"));
        assertEquals(loopThroughListAndFindError(list, "No valid exchange."), "No valid exchange.");
    }

    //================================================================================
    // Methods / functions to use in tests
    //================================================================================

    private void addTestWallet() {
        driver.get(url + "add.jsp");

        Select dropdown = new Select(driver.findElement(By.id("currency")));
        dropdown.selectByVisibleText("BTC");

        WebElement exchange = driver.findElement(By.id("exchangeName"));
        exchange.clear();
        exchange.sendKeys("Bitmex");

        WebElement amount = driver.findElement(By.id("exchangeAmount"));
        amount.clear();
        amount.sendKeys("0.345");

        WebElement favExchangeCheckBox = driver.findElement(By.id("favExchange"));
        favExchangeCheckBox.click();

        WebElement button = driver.findElement(By.className("submitButton"));
        button.click();
    }

    private String loopThroughListAndFindError(List<WebElement> list, String message) {
        String errorMessage = "";
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i).getText().equals(message)){
                errorMessage = list.get(i).getText();
                return errorMessage;
            }
        }
        return errorMessage;
    }

    private void addWallet() {
        Select dropdown = new Select(driver.findElement(By.id("currency")));
        dropdown.selectByVisibleText("BTC");
        driver.findElement(By.id("exchangeName")).sendKeys("Binance");
        driver.findElement(By.id("exchangeAmount")).sendKeys(0.68 + "");
        driver.findElement(By.className("submitButton")).click();
    }

    private void searchWallet(String currency, String exchange) {
        driver.findElement(By.id("currency")).sendKeys(currency);
        driver.findElement(By.id("exchange")).sendKeys(exchange);
        driver.findElement(By.id("search")).click();
    }

    private boolean checkPageForTd(List<WebElement> tds, String text) {
        for (WebElement td : tds) {
            if (td.getText().equals(text)) {
                return true;
            }
        }
        return false;
    }
}
