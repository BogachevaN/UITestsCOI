package steps;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Locatable;
import pages.MainPage;

import static com.codeborne.selenide.Selenide.$;

public class MainSteps extends BaseSteps{
    public MainPage mainPage =null;

    public MainSteps(){
        mainPage = Selenide.page(MainPage.class);
    }

    public void addIncident(){
        mainPage.clickBtn("Добавить инцидент");
    }

    public void goToIncidentsList() {
        mainPage.getIncidents().click();
    }

    public void pageUp() {
        SelenideElement btnAdd = $(By.xpath("//button/span[contains(text(),'Добавить инцидент')]"));
        btnAdd.scrollIntoView(true);
    }

    public void fillTheme(String value) {
        mainPage.getTheme().setValue(value);
    }

    public void clickApply() {
        mainPage.clickBtn("ПРИМЕНИТЬ");
    }

    public void clickReset() {
        mainPage.clickBtn("СБРОСИТЬ");
    }

    public void fillAuthor(String value) {
        selectElementInList(mainPage.getAllAuthors(),value);
    }

    public void fillResponsible(String value) {
        selectElementInList(mainPage.getAllResponsible(),value);
    }

    public void fillCorrespondenceStatus(String value) {
        selectElementInList(mainPage.getAllCorrespondenceStatuses(),value);
    }

    public void fillIncidentStatus(String value) {
        selectElementInList(mainPage.getAllIncidentStatuses(),value);
    }

    public void fillBeginDate(String s) {
        mainPage.getBeginDate().setValue(s);
    }

    public void fillEndDate(String s) {
        mainPage.getEndDate().setValue(s);
    }
}
