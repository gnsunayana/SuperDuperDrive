package com.udacity.jwdnd.course1.cloudstorage.testpage;

import com.udacity.jwdnd.course1.cloudstorage.model.UserCredentials;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.ArrayList;
import java.util.List;

public class CredentialsPageTab {

    @FindBy(id="nav-credentials-tab")
    private WebElement nCredentialsTabLink;

    @FindBy(id="add-credential-button")
    private WebElement nAddCredentialButton;

    @FindBy(id="credential-url")
    private WebElement nModalCredentialUrl;

    @FindBy(id="credential-username")
    private WebElement nModalCredentialUsername;

    @FindBy(id="credential-password")
    private WebElement nModalCredentialPassword;

    @FindBy(id="credential-save-button")
    private WebElement nModalCredentialSaveButton;

    @FindBy(id="credential-edit-list")
    private List<WebElement> nCredentialEditList;

    @FindBy(id="credential-delete-list")
    private List<WebElement> nCredentialDeleteList;

    @FindBy(id="credential-url-list")
    private List<WebElement> nCredentialUrlList;

    @FindBy(id="credential-username-list")
    private List<WebElement> nCredentialUsernameList;

    @FindBy(id="credential-password-list")
    private List<WebElement> nCredentialPasswordList;

    private final WebDriverWait wait;

    public CredentialsPageTab(final WebDriver driver){
        this.wait= new WebDriverWait(driver,1000);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("credential-save-button")));
        PageFactory.initElements(driver,this);
    }

    public void showCredentials(){
        wait.until(ExpectedConditions.elementToBeClickable(nCredentialsTabLink));
        try{
            Thread.sleep(500);
        }
        catch(InterruptedException e){
            System.out.println(e);
        }

        nCredentialsTabLink.click();
    }

    public void newCredential(String url,String username,String password){
        wait.until(ExpectedConditions.elementToBeClickable(nAddCredentialButton));
        nAddCredentialButton.click();
        fillCredential(url,username,password);
    }

    private void fillCredential(String url,String username,String password){
        wait.until(ExpectedConditions.elementToBeClickable(nModalCredentialSaveButton));
        nModalCredentialUrl.clear();
        nModalCredentialUrl.sendKeys(url);
        nModalCredentialUsername.clear();
        nModalCredentialUsername.sendKeys(username);
        nModalCredentialPassword.clear();
        nModalCredentialPassword.sendKeys(password);
        nModalCredentialSaveButton.click();
    }
    public boolean editCredential(int index, String newUrl,String newUsername,String newPassword){
        if(nCredentialEditList.size() <= index){
            return false;
        }
        wait.until(ExpectedConditions.elementToBeClickable(nCredentialEditList.get(index)));
        try{
            Thread.sleep(500);
        }
        catch(InterruptedException e){
            System.out.println(e);
        }
        nCredentialEditList.get(index).click();
        fillCredential(newUrl,newUsername,newPassword);
        return true;
    }

    public boolean deleteCredential(int index){
        if(nCredentialDeleteList.size() <= index){
            return false;
        }
        wait.until(ExpectedConditions.elementToBeClickable(nCredentialDeleteList.get(index)));
        try{
            Thread.sleep(500);
        }
        catch(InterruptedException e){
            System.out.println(e);
        }
        nCredentialDeleteList.get(index).click();
        return true;
    }

    public List<UserCredentials> getCredential(){
        final List<UserCredentials> credentials= new ArrayList<>();
        for(int i=0;i <nCredentialUrlList.size();i++){
            credentials.add(new UserCredentials(null,nCredentialUrlList.get(i).getText(),nCredentialUsernameList.get(i).getText(),null,nCredentialPasswordList.get(i).getText(),null));

        }
        return credentials;
    }

}
