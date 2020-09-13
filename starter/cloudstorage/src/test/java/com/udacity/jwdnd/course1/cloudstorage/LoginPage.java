package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    @FindBy(xpath = "//h1[@class='display-5' and contains(text(),'Login')]")
    private WebElement pageHeader;

    @FindBy(xpath = "//a[contains(@href,'signup')]")
    private WebElement navigateToSignUpPageButton;

    @FindBy(css="#inputUsername")
    private WebElement usernameField;

    @FindBy(css="#inputPassword")
    private WebElement passwordField;

    @FindBy(css="#submit-button")
    private WebElement submitButton;

    public LoginPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public String getPageHeader() {
        return this.pageHeader != null ? this.pageHeader.getText() : "";
    }


    public void clickOnSignUpPage() throws InterruptedException {
        this.navigateToSignUpPageButton.click();
    }

    public void login(String username, String password) {
        this.usernameField.sendKeys(username);
        this.passwordField.sendKeys(password);
        this.submitButton.click();
    }
}
