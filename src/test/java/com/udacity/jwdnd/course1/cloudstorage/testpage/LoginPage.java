package com.udacity.jwdnd.course1.cloudstorage.testpage;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    @FindBy(id = "inputUsername")
    private WebElement nUserName;
    @FindBy(id = "inputPassword")
    private WebElement nPassword;
    @FindBy(id = "submit-button")
    private WebElement nSubmitButton;
    @FindBy(id = "signup-link")
    private WebElement nSignupLink;
    @FindBy(id = "login-form-container")
    private WebElement formContainer;

    public LoginPage(final WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 1000);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("submit-button")));
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        PageFactory.initElements(driver, this);
    }

    public void login(final String username, final String password) {
        wait.until(ExpectedConditions.elementToBeClickable(nSubmitButton));
        nUserName.clear();
        nUserName.sendKeys(username);
        nPassword.clear();
        nPassword.sendKeys(password);
        nSubmitButton.click();
    }

    public String getLogoutMsg() throws NoSuchElementException {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("logout-message")));
        return driver.findElement(By.id("logout-message")).getText();
    }

    public String getSignupMsg() throws NoSuchElementException {

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("signup-msg")));
        return driver.findElement(By.id("signup-msg")).getText();
    }

    public void setUserName(String username){
        nUserName.clear();
        nUserName.sendKeys(username);
    }

    public void setPassword(String password){
        nPassword.clear();
        nPassword.sendKeys(password);
    }

    public void submit(){
        nSubmitButton.click();
    }

    public Boolean success(){
        return !hasErrorMessage();
    }

    private boolean hasErrorMessage(){
        boolean hasError = false;
        try{
            hasError =formContainer.findElements(By.id("invalid-credentials-message")).size() !=0;
        }
        catch(Exception ignored){

        }
        return hasError;
    }

}
