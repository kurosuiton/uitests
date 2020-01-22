package org.example;

import net.bytebuddy.utility.RandomString;
import org.example.pages.HomePage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class RegistrationTest extends TestNgTestBase {

    private static String CORRECT_PASS = "testtest";

    @BeforeMethod
    void openHomePage() {
        driver.get(baseUrl);
    }

    @DataProvider
    public Object[][] getCorrectMail() {
        return new Object[][]{
                {"tester_MAIL-001" + new RandomString().nextString() + "@test-001.rus.ru"}, // Norm email
                {"testerMAIL001" + new RandomString().nextString() + "@test_001.ru"}, // Email contains _ in domain
                {"testermail001!#$%&‘*+—/=?^_`{|}~" + new RandomString().nextString() + "@test.ru"}, // Email contains spec symbols
                {" testmail004" + new RandomString().nextString() + "@test.ru"}, // Space before mail
                {"testmail004" + new RandomString().nextString() + "@test.ru "} // Space before mail
        };
    }

    /**
     * Регистрация с новым email
     */
    @Test(dataProvider = "getCorrectMail")
    public void registration(String mail) {
        new HomePage(driver)
                .checkOnThisPage()
                .clickRegisterButton()
                .checkOnThisPage()
                .checkDisabledRegisterButton()
                .enterMail(mail)
                .checkEnteredMail(mail.trim())
                .enterPassword(CORRECT_PASS)
                .checkDisabledRegisterButton()
                .clickLicenseCheckbox()
                .checkEnabledRegisterButton()
                .clickRegisterButton()
                .checkOnThisPage()
                .clickNotNowButton()
                .checkOnThisPage()
                .loginCheck();
    }

    /**
     * Попытка повторной регистрации на тот же email
     */
    @Test
    public void ReRegistration() {
        new HomePage(driver)
                .checkOnThisPage()
                .clickRegisterButton()
                .checkOnThisPage()
                .checkDisabledRegisterButton()
                .enterMail("testmail001@test.ru")
                .enterPassword(CORRECT_PASS)
                .clickLicenseCheckbox()
                .checkEnabledRegisterButton()
                .clickRegisterButtonAndCheckError("Такой email уже зарегистрирован.");
    }

    @DataProvider
    public Object[][] getDataForIncorrectRegistration() {
        String over255String = "0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789" +
                "0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789" +
                "01234567890123456789012345678901234567890123456789012345";
        return new Object[][]{
                {"", ""}, // Without email and pass
                {"", CORRECT_PASS}, // Without email
                {"testermail003" + new RandomString().nextString() + "@test.ru", ""}, // Without pass
                {"testermail 001@test.ru", CORRECT_PASS}, // Email with space in local
                {"testermail001@te st.ru", CORRECT_PASS}, // Email with space in domain
                {over255String, CORRECT_PASS}, // Mail over 255
                {"01234567890123456789012345678901234567890123456789012345678901234@test.ru", CORRECT_PASS}, // Mail local over 64
                {"testmail001test.ru", CORRECT_PASS}, // Mail without @
                {"testmail001@testru", CORRECT_PASS}, // Mail without .
                {"testmail001@test.r", CORRECT_PASS}, // Mail with first domain less 2
                {"@test.ru", CORRECT_PASS}, // Mail without local
                {"testmail001", CORRECT_PASS}, // Mail without domain
                {"testermail003" + new RandomString().nextString() + "@test.ru", over255String}, // Pass over 255
                {"testermail003" + new RandomString().nextString() + "@test.ru", "12345"} // Pass less 6
        };
    }

    @Test(dataProvider = "getDataForIncorrectRegistration")
    public void registrationWithIncorrectMailOrPass(String mail, String pass) {
        new HomePage(driver)
                .checkOnThisPage()
                .clickRegisterButton()
                .checkOnThisPage()
                .checkDisabledRegisterButton()
                .enterMail(mail)
                .enterPassword(pass)
                .clickLicenseCheckbox()
                .checkDisabledRegisterButton();
    }
}