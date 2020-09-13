package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {

    @FindBy(xpath = "//h1[@class='display-5' and contains(text(),'Sign Up')]")
    private WebElement pageHeader;

    @FindBy(css = "#inputFirstName")
    private WebElement firstNameField;

    @FindBy(css = "#inputLastName")
    private WebElement lastNameField;

    @FindBy(css = "#inputUsername")
    private WebElement usernameField;

    @FindBy(css = "#inputPassword")
    private WebElement passwordField;

    @FindBy(css = "#submit-button")
    private WebElement submitButton;

    @FindBy(css = "#error-msg")
    private WebElement errorMessage;

    @FindBy(css = "#success-msg")
    private WebElement successMessage;

    public SignupPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public String getPageHeader() {
        return pageHeader != null ? pageHeader.getText() : "";
    }
    public String getErrorMessage() {
        return errorMessage != null ? errorMessage.getText() : "";
    }

    public String getSuccessMessage() {
        return successMessage != null ? successMessage.getText() : "";
    }

    public void signup(String firstName, String lastName, String username, String password) throws InterruptedException {
        Thread.sleep(1000);
        this.firstNameField.sendKeys(firstName);
        this.lastNameField.sendKeys(lastName);
        this.usernameField.sendKeys(username);
        this.passwordField.sendKeys(password);
        this.submitButton.click();
        Thread.sleep(1000);
    }

}
