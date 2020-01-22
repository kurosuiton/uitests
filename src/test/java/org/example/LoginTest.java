package org.example;

import org.example.pages.HomePage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginTest extends TestNgTestBase {

    private static String CORRECT_MAIL = "testmail001@test.ru";
    private static String CORRECT_PASS = "testtest";

    @BeforeMethod
    void openHomePage() {
        driver.get(baseUrl);
    }

    @DataProvider
    public Object[][] getCorrectMail() {
        return new Object[][]{
                {CORRECT_MAIL}, // mail is lowercase
                {"TESTMAIL001@TEST.RU"}, // mail is uppercase
                {"tester_MAIL-001@test-001.rus.ru"}, // mail with - and _
                {"testmail001@te_st.ru"}, // mail with _ in domain
                {"testermail001!#$%&‘*+—/=?^_`{|}~@test.ru"}, // mail with spec symbols
                {" testmail001@test.ru"}, // Space before mail
                {"testmail001@test.ru "} // Space before mail
        };
    }

    @Test(dataProvider = "getCorrectMail")
    public void correctMail(String mail) {
        new HomePage(driver)
                .checkOnThisPage()
                .clickLoginButton()
                .checkOnThisPage()
                .checkDisabledLoginButton()
                .enterMail(mail)
                .checkEnteredMail(mail.trim())
                .enterPassword(CORRECT_PASS)
                .checkEnabledLoginButton()
                .clickLoginButton()
                .checkOnThisPage()
                .loginCheck();
    }

    @DataProvider
    public Object[][] getDataForIncorrectLogin() {
        String over255String = "0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789" +
                "0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789" +
                "01234567890123456789012345678901234567890123456789012345";
        return new Object[][]{
                {"", ""}, // Without email and pass
                {"", CORRECT_PASS}, // Without email
                {CORRECT_MAIL, ""}, // Without pass
                {over255String, CORRECT_PASS}, // Mail over 255
                {"01234567890123456789012345678901234567890123456789012345678901234@test.ru", CORRECT_PASS}, // Mail local over 64
                {"testmail001test.ru", CORRECT_PASS}, // Mail without @
                {"testmail001@testru", CORRECT_PASS}, // Mail without .
                {CORRECT_MAIL, over255String}, // Pass over 255
                {CORRECT_MAIL, "12345"}, // Pass less 6
                {"testmail 001@test.ru", CORRECT_PASS}, // Email with space in local
                {"testmail001@te st.ru", CORRECT_PASS}, // Email with space in domain
                {"testmail001@test.r", CORRECT_PASS}, // Mail with first domain less 2
                {"@test.ru", CORRECT_PASS}, // Mail without local
                {"testmail001", CORRECT_PASS} // Mail without domain
        };
    }

    @Test(dataProvider = "getDataForIncorrectLogin")
    public void loginWithIncorrectMailOrPass(String mail, String pass) {
        new HomePage(driver)
                .checkOnThisPage()
                .clickLoginButton()
                .checkOnThisPage()
                .checkDisabledLoginButton()
                .enterMail(mail)
                .enterPassword(pass)
                .checkDisabledLoginButton();
    }

    @DataProvider
    public Object[][] getBodyWithEmailDoesNotRegisteredOrNotAcceptPass() {
        return new Object[][]{
                {"testmail002@test.ru", "123456", "Введенный email не найден."}, // Email does not registered
                {"testmail001@test.ru", "123456", "Введенный пароль не верен для данного аккаунта."} // Pass not accept
        };
    }

    @Test(dataProvider = "getBodyWithEmailDoesNotRegisteredOrNotAcceptPass")
    public void mailDoesNotExistOrPassNotAccept(String mail, String pass, String textError) {
        new HomePage(driver)
                .checkOnThisPage()
                .clickLoginButton()
                .checkOnThisPage()
                .checkDisabledLoginButton()
                .enterMail(mail)
                .enterPassword(pass)
                .checkEnabledLoginButton()
                .clickLoginButtonAndCheckError(textError);
    }
}