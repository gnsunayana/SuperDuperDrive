package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.UserCredentials;
import com.udacity.jwdnd.course1.cloudstorage.testpage.CredentialsPageTab;
import com.udacity.jwdnd.course1.cloudstorage.testpage.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,scripts = "classpath:beforeEachTest.sql")
public class CredentialAppTests {

    @LocalServerPort
    private int port;

    private String baseURL;

    private WebDriver driver;

    @BeforeAll
    static void beforeAll(){
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach()throws InterruptedException{
        driver= new ChromeDriver();
        baseURL= "http://localhost:"+port;

        driver.get(baseURL+"/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("d","1");
    }

    @AfterEach
    public void afterEach(){
        if(this.driver != null){
            driver.quit();
        }
    }

    @Test
    public void createCredential(){

        String url="http://www.google.com";
        String username="rani";
        String password="hello";
        CredentialsPageTab credentialsPageTab= new CredentialsPageTab(driver);
        credentialsPageTab.showCredentials();
        credentialsPageTab.newCredential(url,username,password);

        List<UserCredentials> credentials= credentialsPageTab.getCredential();
        assertEquals(1,credentials.size());
        assertEquals(url,credentials.get(0).getUrl());
        assertEquals(username,credentials.get(0).getUserName());
        assertEquals(24,credentials.get(0).getPassword().length());
    }

    @Test
    public void editCredential(){
        String url="http://www.google.com";
        String username="rani";
        String password="hello";
        String newUrl="http://www.microsoft.com";
        String newUsername="jani";
        String newPassword="hello123";

        CredentialsPageTab credentialsPageTab = new CredentialsPageTab(driver);
        credentialsPageTab.showCredentials();
        credentialsPageTab.newCredential(url,username,password);

        assertTrue(credentialsPageTab.editCredential(0,newUrl,newUsername,newPassword));

        List<UserCredentials> credentials= credentialsPageTab.getCredential();
        assertEquals(1,credentials.size());
        assertEquals(newUrl,credentials.get(0).getUrl());
        assertEquals(newUsername,credentials.get(0).getUserName());
        assertEquals(24,credentials.get(0).getPassword().length());
    }

}
