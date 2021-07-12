package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.testpage.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.testpage.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.testpage.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserAndAccessTests {

    @LocalServerPort
    private int port;
    private String baseURL;
    private WebDriver driver;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
        baseURL = "http://localhost:" + port;
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    public void unauthorizedUserAccess() {

        driver.get(baseURL + "/signup");
        assertEquals(baseURL + "/signup", driver.getCurrentUrl());
        assertEquals("Sign Up", driver.getTitle());

        driver.get(baseURL + "/login");
        assertEquals(baseURL + "/login", driver.getCurrentUrl());
        assertEquals("Login", driver.getTitle());

        driver.get(baseURL + "/home");
        assertEquals(baseURL + "/login", driver.getCurrentUrl());
        assertEquals("Login", driver.getTitle());

        driver.get(baseURL);
        assertEquals(baseURL + "/login", driver.getCurrentUrl());
        assertEquals("Login", driver.getTitle());

        driver.get(baseURL + "/hello");
        assertEquals(baseURL + "/login", driver.getCurrentUrl());
        assertEquals("Login", driver.getTitle());

        driver.get(baseURL + "/file-upload");
        assertEquals(baseURL + "/login", driver.getCurrentUrl());
        assertEquals("Login", driver.getTitle());
    }

    @Test
    public void newUserAccess() throws InterruptedException {

        String username = "sunayana";
        String password = "hello123";

        String expectedSignupSuccessMsg = "You successfully signed up.Please login.";

        driver.get(baseURL + "/signup");

        SignupPage signupPage = new SignupPage(driver);
        signupPage.signup("jane", "suzz", username, password);
        signupPage = null;


        LoginPage loginPage = new LoginPage(driver);
        assertEquals(expectedSignupSuccessMsg, loginPage.getSignupMsg());
        loginPage.login(username, password);
        loginPage = null;


        HomePage homePage = new HomePage(driver);
        assertEquals(baseURL + "/home", driver.getCurrentUrl());
        assertEquals("Home", driver.getTitle());

        homePage.logout();
        homePage = null;


        loginPage = new LoginPage(driver);
        assertEquals(baseURL + "/login?logout", driver.getCurrentUrl());
        assertEquals("Login", driver.getTitle());
        assertEquals("You have been logged out", loginPage.getLogoutMsg());

        driver.get(baseURL + "/home");
        assertEquals(baseURL + "/login", driver.getCurrentUrl());
        assertEquals("Login", driver.getTitle());


    }





}
