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

public class HomePage extends Page {

    @FindBy(how = How.CSS, using = "a.menu__sign.button")
    @CacheLookup
    private WebElement loginButton;

    @FindBy(how = How.CSS, using = "a.menu__register.button-primary")
    @CacheLookup
    private WebElement registerButton;

    @FindBy(how = How.CSS, using = "a[class='menu__item menu__logo']")
    @CacheLookup
    private WebElement logoImageLink;

    @FindBy(how = How.CSS, using = "div.avatar")
    @CacheLookup
    private WebElement avatarProfile;

    public HomePage(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(driver, this);
    }

    @Step
    public HomePage checkOnThisPage() {
        new WebDriverWait(driver, 5).until(ExpectedConditions
                .titleIs("Смотреть сериалы онлайн | Амедиатека | Новинки зарубежных сериалов и фильмов в HD-качестве"));
        return this;
    }

    @Step
    public HomePage clickLogoImageLink() {
        logoImageLink.click();
        return this;
    }

    @Step
    public HomePage loginCheck() {
        if (!avatarProfile.isDisplayed()) {
            throw new IllegalStateException("Avatar does not exit on home page");
        }
        return this;
    }

    @Step
    public LoginPage clickLoginButton() {
        loginButton.click();
        return new LoginPage(driver);
    }

    @Step
    public RegistrationPage clickRegisterButton() {
        registerButton.click();
        return new RegistrationPage(driver);
    }
}
