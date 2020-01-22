package org.example.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends Page {

    @FindBy(how = How.CSS, using = "input[type='email']")
    @CacheLookup
    private WebElement mailInputField;

    @FindBy(how = How.CSS, using = "input[type='password']")
    @CacheLookup
    private WebElement passwordInputField;

    @FindBy(how = How.CSS, using = "button.registration__form-button")
    @CacheLookup
    private WebElement loginButton;

    @FindBy(how = How.CSS, using = "div.registration__form-error")
    @CacheLookup
    private WebElement loginError;

    public LoginPage(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(driver, this);
    }

    @Step
    public LoginPage checkOnThisPage() {
        new WebDriverWait(driver, 5).until(ExpectedConditions
                .titleIs("Войти"));
        return this;
    }

    @Step
    public HomePage clickLoginButton() {
        loginButton.click();
        return new HomePage(driver);
    }

    @Step
    public LoginPage clickLoginButtonAndCheckError(String errorText) {
        loginButton.click();
        if (!errorText.equals(loginError.getText())) {
            throw new IllegalStateException("Error text is not '" + errorText + "' on login page");
        }
        return this;
    }

    @Step
    public LoginPage enterPassword(String pass) {
        passwordInputField.sendKeys(pass);
        return this;
    }

    @Step
    public LoginPage enterMail(String mail) {
        mailInputField.sendKeys(mail);
        return this;
    }

    @Step
    public LoginPage checkEnabledLoginButton() {
        if (!loginButton.getAttribute("class").contains("active")) {
            throw new IllegalStateException("Login button is disabled on login page");
        }
        return this;
    }

    @Step
    public LoginPage checkDisabledLoginButton() {
        if (loginButton.getAttribute("class").contains("active")) {
            throw new IllegalStateException("Login button is enabled on login page");
        }
        return this;
    }

    @Step
    public LoginPage checkEnteredMail(String mail) {
        if (!mail.equals(mailInputField.getAttribute("value"))) {
            throw new IllegalStateException("Mail does not equals in mail field on registration field");
        }
        return this;
    }
}
