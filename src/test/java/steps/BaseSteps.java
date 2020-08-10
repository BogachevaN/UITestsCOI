package steps;

import com.codeborne.selenide.*;
import com.opencsv.CSVReader;
import helpers.EndPoints;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

import java.io.FileReader;
import java.io.IOException;


import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.apache.commons.io.FilenameUtils.getPath;

public class BaseSteps {

    static ElementsCollection contentList = $$(new By.ByXPath("//ul/li"));

    public static void beforeTest(String driverName) {
        System.setProperty("webdriver.chrome.driver", Paths.get("src", "test", "resources")
            .resolve("chromedriver.exe")
            .toAbsolutePath()
            .toString());

        Configuration.browser = driverName;
        Configuration.timeout = 6000;
    }

    public static void openAuthPage() {
        Selenide.open(EndPoints.AUTH_PAGE);
    }

    public static void openIncidentPage(int incidentId) {
        Selenide.open(EndPoints.INCIDENT_PAGE+incidentId);
    }

    public static void windowMaximize(){
        WebDriverRunner.getWebDriver().manage().window().maximize();
    }

    public void fillFieldIfEmpty(SelenideElement element, String text) {
        if (element.getTagName().equals("textarea")){
            element.setValue(text);
        }
        if (element.getTagName().equals("label")){
            selectElementInList(element,text);
        }
        if(element.getTagName().equals("span")) {
            element.click();
        }
        if (element.getTagName().equals("input")){
            if (element.getValue().equals("") || element.getValue().equals("+7(___) ___-____")) {
                element.setValue(text);
            }
        }
        if (element.getTagName().equals("mat-select")){
            selectElementInList(element,text);
        }
        if (element.getTagName().equals("mat-option")){
            element.click();
        }
    }

    public void selectElementInList(SelenideElement element, String text) {
        element.parent().click();
        getContentList().findBy(text(text)).click();
    }

    public static ElementsCollection getContentList() {
        return contentList;
    }

    public List<String[]> readCSVFileWithData(String fileName) throws IOException {
        String path = Paths.get("src", "test", "resources","data","csv")
            .resolve(fileName)
            .toAbsolutePath()
            .toString();
        CSVReader reader = new CSVReader(new FileReader(path));
        List<String[]> allRows = reader.readAll();
        return allRows;
    }

    public void removeRow(List<String[]> data, String requiredField) {
        Iterator iter = data.iterator();
        while (iter.hasNext()) {
            String[] s = (String[]) iter.next();
            if (s[0].equals(requiredField)) {
                iter.remove();
            }
        }
    }

    public boolean containsRequiredText() {
        return containsText("Поле обязательно для отправки в ФинЦЕРТ");
    }

    public boolean containsMoreThan10AttachmentsText() {
        return containsText("Нельзя загрузить более 10 вложений");
    }

    public boolean containsAttachmentsMore500KbText() {
        return containsText("Ошибка загрузки. Размер превышает 500 кБ, уменьшите размер файла");
    }

    public boolean containsText(String text) {
        Boolean res = false;
        if ($(byText(text)).exists()){res = true;}
        return res;
    }

    protected void clearInputOrTextareaField(SelenideElement element) {
        String description = element.getValue();
        int n = description.length();
        for (int i = 0; i < n; i++) {
            element.sendKeys("\b");
        }
    }
}
