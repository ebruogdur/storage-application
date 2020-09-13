package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class NotePage {

    @FindBy(xpath = "//a[contains(@id,'nav-notes-tab')]")
    private WebElement noteTab;

    @FindBy(xpath = "//button[contains(text(),'Add a New Note')]")
    private WebElement addNewNoteButton;

    @FindBy(css = "#note-title")
    private WebElement noteTitle;

    @FindBy(css = "#note-description")
    private WebElement noteDescription;


    @FindBy(css = "#noteSubmitBtn")
    private WebElement saveChangesButton;

    @FindBy(css = "#edit-note")
    private WebElement editNoteBtn;


    @FindBy(css = "#delete-note")
    private WebElement deleteNoteBtn;


    @FindBy(xpath = "//td[contains(text(),'Hiç Note Yok!')]")
    private WebElement noNoteText;

    public NotePage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void openNoteTab() throws InterruptedException {
        Thread.sleep(1000);
        this.noteTab.click();
    }

    public Note getNote() throws InterruptedException {
        Thread.sleep(1000);
        Note note = new Note();
        note.setNoteTitle(editNoteBtn.getAttribute("data-note-title"));
        note.setNoteDescription(editNoteBtn.getAttribute("data-note-desc"));
        return note;
    }

    public void addNewNote(Note note) throws InterruptedException {
        Thread.sleep(1000);
        this.addNewNoteButton.click();
        Thread.sleep(1000);
        this.noteTitle.sendKeys(note.getNoteTitle());
        this.noteDescription.sendKeys(note.getNoteDescription());
        this.saveChangesButton.click();
    }


    public void editNote(Note note) throws InterruptedException {
        Thread.sleep(1000);
        this.editNoteBtn.click();
        Thread.sleep(1000);
        this.noteTitle.clear();
        this.noteTitle.sendKeys(note.getNoteTitle());
        this.noteDescription.clear();
        this.noteDescription.sendKeys(note.getNoteDescription());
        this.saveChangesButton.click();
    }

    public void deleteNote() throws InterruptedException {
        Thread.sleep(1000);
        this.deleteNoteBtn.click();
    }

    public String getNoNoteText() throws InterruptedException {
        Thread.sleep(1000);
        return this.noNoteText.getText();
    }







}
