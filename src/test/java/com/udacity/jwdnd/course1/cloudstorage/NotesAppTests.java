package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.testpage.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.testpage.NotePageTab;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:beforeEachTest.sql")
public class NotesAppTests {

    @LocalServerPort
    private int port;

    private String baseURL;

    private WebDriver driver;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();

    }

    @BeforeEach
    public void beforeEach() throws InterruptedException {
        driver = new ChromeDriver();
        baseURL = "http://localhost:" + port;

        driver.get(baseURL + "/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("d", "1");
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    public void createNote() {
        String title = "Testing note creation";
        String description = "//Write a test that creates a note,\n and verifies it is displayed";
        NotePageTab notePageTab = new NotePageTab(driver);
        notePageTab.showNotes();
        notePageTab.newNote(title, description);

        List<Note> notes = notePageTab.getNotes();
        assertEquals(1, notes.size());
        assertEquals(title, notes.get(0).getNoteTitle());
        assertEquals(description.replace("\n", ""), notes.get(0).getNoteDescription());

    }


    public void editNote() {
        String title = "Test Note Creation";
        String description = "//Write a test that creates a note,\n and verifies it is displayed";
        String newTitle = "Test Note Editing";
        String newDescription = "// Write a test that edits an existing note\n and verifies that the changes are displayed";
        NotePageTab notePageTab = new NotePageTab(driver);
        notePageTab.showNotes();
        notePageTab.newNote(title, description);
        assertTrue(notePageTab.editNote(0, newTitle, newDescription));

        List<Note> notes = notePageTab.getNotes();
        assertEquals(1, notes.size());
        assertEquals(newTitle, notes.get(0).getNoteTitle());
        assertEquals(newDescription.replace("\n", ""), notes.get(0).getNoteDescription());
    }

    public void deleteNote() {
        String title = "Test Note Deletion";
        String description = "// Write a test that deletes a note\n and verifies that the note is no longer displayed";
        NotePageTab notePageTab = new NotePageTab(driver);
        notePageTab.showNotes();
        notePageTab.newNote(title, description);
        assertTrue(notePageTab.deleteNote(0));
        List<Note> notes = notePageTab.getNotes();
        assertEquals(0, notes.size());
    }


}
