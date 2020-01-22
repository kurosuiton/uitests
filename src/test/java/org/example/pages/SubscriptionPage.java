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

public class SubscriptionPage extends Page {

    @FindBy(how = How.CSS, using = "div.btn.success__button-return")
    @CacheLookup
    public WebElement notNowButton;

    public SubscriptionPage(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(driver, this);
    }

    @Step
    public SubscriptionPage checkOnThisPage() {
        new WebDriverWait(driver, 5).until(ExpectedConditions
                .titleIs("Оформление подписки"));
        return this;
    }

    @Step
    public HomePage clickNotNowButton() {
        notNowButton.click();
        return new HomePage(driver);
    }
}
