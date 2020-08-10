package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;

public class MainPage extends BasePage {
    @FindBy(xpath = "//div[contains(text(),'Инциденты')]") private SelenideElement incidents;
    @FindBy(xpath = "//input[@name='theme']") private SelenideElement theme;
    @FindBy(xpath = "//*[contains(text(),'Все авторы')]") private SelenideElement allAuthors;
    @FindBy(xpath = "//*[contains(text(),'Все ответственные')]") private SelenideElement allResponsible;
    @FindBy(xpath = "//div[1]/div/div/div[contains(text(),'Все статусы')]") private SelenideElement allCorrespondenceStatuses;
    @FindBy(xpath = "//div[2]/div/div/div[contains(text(),'Все статусы')]") private SelenideElement allIncidentStatuses;
    @FindBy(xpath = "//input[@name='beginDate']") private SelenideElement beginDate;
    @FindBy(xpath = "//input[@name='endDate']") private SelenideElement endDate;
    @FindBy(xpath = "//table/tbody/tr") private ElementsCollection tableOfIncidents;

    public SelenideElement getIncidents() { return incidents; }
    public SelenideElement getTheme() { return theme;}
    public SelenideElement getAllAuthors() { return allAuthors; }
    public SelenideElement getAllResponsible() { return  allResponsible; }
    public SelenideElement getAllCorrespondenceStatuses() { return allCorrespondenceStatuses; }
    public SelenideElement getAllIncidentStatuses() { return allIncidentStatuses; }
    public SelenideElement getBeginDate() { return beginDate; }
    public SelenideElement getEndDate() { return endDate; }
    public ElementsCollection getTableOfIncidents() {return tableOfIncidents;}
}
