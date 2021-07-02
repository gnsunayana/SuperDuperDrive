package com.udacity.jwdnd.course1.cloudstorage.testpage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    private final WebDriverWait wait;
    @FindBy(id = "logout-button")
    private WebElement nLogoutButton;

    public HomePage(final WebDriver driver) {
        this.wait = new WebDriverWait(driver, 1000);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("logout-button")));
        PageFactory.initElements(driver, this);
    }

    public void logout() throws InterruptedException {

        wait.until(ExpectedConditions.elementToBeClickable(nLogoutButton));
        Thread.sleep(1000);
        nLogoutButton.click();
    }
}
