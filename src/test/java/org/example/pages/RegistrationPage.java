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

public class RegistrationPage extends Page {

    @FindBy(how = How.CSS, using = "div.registration__content-title")
    @CacheLookup
    private WebElement title;

    @FindBy(how = How.CSS, using = "input[type='email']")
    @CacheLookup
    private WebElement mailInputField;

    @FindBy(how = How.CSS, using = "input[type='password']")
    @CacheLookup
    private WebElement passwordInputField;

    @FindBy(how = How.CSS, using = "label[for='confirm_license']")
    @CacheLookup
    private WebElement licenseCheckbox;

    @FindBy(how = How.CSS, using = "div.registration__form-button")
    @CacheLookup
    private WebElement registerButton;

    @FindBy(how = How.CSS, using = "div.registration__form-error")
    @CacheLookup
    private WebElement registerError;

    public RegistrationPage(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(driver, this);
    }

    @Step
    public RegistrationPage checkOnThisPage() {
        new WebDriverWait(driver, 5).until(ExpectedConditions.textToBePresentInElement(title, "Регистрация"));
        return this;
    }

    @Step
    public SubscriptionPage clickRegisterButton() {
        registerButton.click();
        return new SubscriptionPage(driver);
    }

    @Step
    public RegistrationPage clickRegisterButtonAndCheckError(String errorText) {
        registerButton.click();
        if (!errorText.equals(registerError.getText())) {
            throw new IllegalStateException("Error text is not '" + errorText + "' on registration page");
        }
        return this;
    }

    @Step
    public RegistrationPage enterPassword(String pass) {
        passwordInputField.sendKeys(pass);
        return this;
    }

    @Step
    public RegistrationPage enterMail(String mail) {
        mailInputField.sendKeys(mail);
        return this;
    }

    @Step
    public RegistrationPage checkEnabledRegisterButton() {
        if (!registerButton.getAttribute("class").contains("active")) {
            throw new IllegalStateException("Register button is disabled on registration page");
        }
        return this;
    }

    @Step
    public RegistrationPage checkDisabledRegisterButton() {
        if (registerButton.getAttribute("class").contains("active")) {
            throw new IllegalStateException("Register button is enabled on registration page");
        }
        return this;
    }

    @Step
    public RegistrationPage clickLicenseCheckbox() {
        licenseCheckbox.click();
        return this;
    }

    @Step
    public RegistrationPage checkEnteredMail(String mail) {
        if (!mail.equals(mailInputField.getAttribute("value"))) {
            throw new IllegalStateException("Mail does not equals in mail field on registration field");
        }
        return this;
    }
}
