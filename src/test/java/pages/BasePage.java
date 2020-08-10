package pages;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class BasePage {
    public void clickBtn(String btnName) {
        $(By.xpath(String.format("//button/span[contains(text(),'%s')]",btnName))).shouldNotBe(Condition.disabled).click();
    }

}
