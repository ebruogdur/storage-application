package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class CredentialPage {
    @FindBy(xpath = "//a[contains(@id,'nav-credentials-tab')]")
    private WebElement credentialTab;


    @FindBy(xpath = "//button[contains(text(),'Add a New Credential')]")
    private WebElement addNewCredentialButton;


    @FindBy(css = "#credential-url")
    private WebElement urlField;

    @FindBy(css = "#credential-username")
    private WebElement userNameField;

    @FindBy(css = "#credential-password")
    private WebElement passwordField;

    @FindBy(css = "#credentialSubmitBtn")
    private WebElement saveChangesButton;


    @FindBy(css = "#edit-credential")
    private WebElement editButton;

    @FindBy(css = "#delete-credential")
    private WebElement deleteButton;

    @FindBy(xpath = "//td[contains(text(),'Hiç Credential Yok!')]")
    private WebElement noCredentialText;

    @FindBy(css = "#credentialTable")
    private WebElement credentialTable;


    public CredentialPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }


    public void navigateToCredentialTab() throws InterruptedException {
        Thread.sleep(1000);
        this.credentialTab.click();
    }


    public Credential getCredential() throws InterruptedException {
        Thread.sleep(1000);
        Credential credential = new Credential();
        credential.setUrl(editButton.getAttribute("data-credential-url"));
        credential.setPassword(editButton.getAttribute("data-credential-password"));
        credential.setUsername(editButton.getAttribute("data-credential-username"));
        return credential;
    }

    public void addNewCredential(Credential credential) throws InterruptedException {
        Thread.sleep(1000);
        this.addNewCredentialButton.click();
        Thread.sleep(1000);
        this.urlField.sendKeys(credential.getUrl());
        this.userNameField.sendKeys(credential.getUsername());
        this.passwordField.sendKeys(credential.getPassword());
        this.saveChangesButton.click();



    }

    public void editCredential(Credential credential) throws InterruptedException {
        Thread.sleep(1000);
        this.editButton.click();
        Thread.sleep(1000);
        this.urlField.clear();
        this.urlField.sendKeys(credential.getUrl());
        this.userNameField.clear();
        this.userNameField.sendKeys(credential.getUsername());
        this.passwordField.clear();
        this.passwordField.sendKeys(credential.getPassword());
        this.saveChangesButton.click();



    }

    public void deleteCredential() throws InterruptedException {
        Thread.sleep(1000);
        this.deleteButton.click();
    }

    public String getNoCredentialText() throws InterruptedException {
        Thread.sleep(1000);
        return this.noCredentialText.getText();
    }

    public boolean credentialExistsInTable(Credential credential){

        List<WebElement> tableElements = credentialTable.findElements(By.tagName("tbody"));

        List<String> tableTds = new ArrayList();


        for (int i=0; i <tableElements.size();i++){
            WebElement tableElement  = tableElements.get(i);
            List<WebElement> rowTds = tableElement.findElements(By.tagName("td"));
            for (WebElement rowTd: rowTds) {
                tableTds.add(rowTd.getText());
            }
        }

        if(tableTds.contains(credential.getUsername()) && tableTds.contains(credential.getPassword()) && tableTds.contains(credential.getUrl()))
            return true;
        else
            return false;

    }


}
