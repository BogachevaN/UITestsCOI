package pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;

public class AuthPage extends BasePage{
    @FindBy(xpath = "//*[@name='username']") public SelenideElement userName;
    @FindBy(xpath = "//*[@name='password']") public SelenideElement password;


    public void setUserName(String userName) {
        this.userName.setValue(userName);
    }

    public void setPassword(String password) {
        this.password.setValue(password);
    }
}
