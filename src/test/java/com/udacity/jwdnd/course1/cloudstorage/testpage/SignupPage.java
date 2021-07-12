package com.udacity.jwdnd.course1.cloudstorage.testpage;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignupPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    @FindBy(id = "inputFirstName")
    private WebElement nFirstName;
    @FindBy(id = "inputLastName")
    private WebElement nLastName;
    @FindBy(id = "inputUsername")
    private WebElement nUsername;
    @FindBy(id = "inputPassword")
    private WebElement nPassword;
    @FindBy(id = "submit-button")
    private WebElement nSubmitButton;

    public SignupPage(final WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("submit-button")));
        PageFactory.initElements(driver, this);
    }

    public void signup(final String firstName, final String lastName, final String username, final String password) {

        //wait.until(ExpectedConditions.elementToBeClickable(nSubmitButton));
        nFirstName.clear();
        nFirstName.sendKeys(firstName);
        nLastName.clear();
        nLastName.sendKeys(lastName);
        nUsername.clear();
        nUsername.sendKeys(username);
        nPassword.clear();
        nPassword.sendKeys(password);
        nSubmitButton.click();
    }


    public boolean goLogin() {
        try {
            WebElement nLogin = wait.until(ExpectedConditions.elementToBeClickable(By.id("login-link")));
            driver.get(nLogin.getAttribute("href"));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }


}
