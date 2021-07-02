package com.udacity.jwdnd.course1.cloudstorage.testpage;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class NotePageTab {

    private final WebDriverWait wait;
    @FindBy(id = "nav-notes-tab")
    private WebElement nNotesTabLink;
    @FindBy(id = "add-note-button")
    private WebElement nAddNoteButton;
    @FindBy(id = "note-title")
    private WebElement nModalNoteTitle;
    @FindBy(id = "note-description")
    private WebElement nModalNoteDesc;
    @FindBy(id = "note-save-button")
    private WebElement nModalNoteSaveButton;
    @FindBy(id = "note-edit-list")
    private List<WebElement> nNoteEditList;
    @FindBy(id = "note-delete-list")
    private List<WebElement> nNoteDeleteList;
    @FindBy(id = "note-title-list")
    private List<WebElement> nNoteTitleList;
    @FindBy(id = "note-description-list")
    private List<WebElement> nNoteDescriptionList;

    public NotePageTab(final WebDriver driver) {
        this.wait = new WebDriverWait(driver, 1000);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("note-save-button")));
        PageFactory.initElements(driver, this);

    }

    public void showNotes() {
        wait.until(ExpectedConditions.elementToBeClickable(nNotesTabLink));
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        nNotesTabLink.click();
    }

    public void newNote(String title, String description) {
        wait.until(ExpectedConditions.elementToBeClickable(nAddNoteButton));
        nAddNoteButton.click();
        fillNote(title, description);
    }

    private void fillNote(String title, String description) {
        wait.until(ExpectedConditions.elementToBeClickable(nModalNoteSaveButton));
        nModalNoteTitle.clear();
        nModalNoteTitle.sendKeys(title);
        nModalNoteDesc.clear();
        nModalNoteDesc.sendKeys(description);
        nModalNoteSaveButton.click();
    }

    public boolean editNote(int index, String newTitle, String newDescription) {

        if (nNoteEditList.size() <= index) {
            return false;
        }
        wait.until(ExpectedConditions.elementToBeClickable(nNoteEditList.get(index)));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        nNoteEditList.get(index).click();
        fillNote(newTitle, newDescription);
        return true;
    }

    public boolean deleteNote(int index) {
        if (nNoteDeleteList.size() <= index) {
            return false;
        }
        wait.until(ExpectedConditions.elementToBeClickable(nNoteDeleteList.get(index)));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        nNoteDeleteList.get(index).click();
        return true;
    }

    public List<Note> getNotes() {
        final List<Note> notes = new ArrayList<>();
        for (int i = 0; i < nNoteTitleList.size(); i++) {
            notes.add(new Note(null, nNoteTitleList.get(i).getText(), nNoteDescriptionList.get(i).getText(), null));
        }
        return notes;
    }
}
