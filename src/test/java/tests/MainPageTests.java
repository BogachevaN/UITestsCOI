package tests;

import com.codeborne.selenide.ElementsCollection;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import steps.AuthSteps;
import steps.BaseSteps;
import steps.MainSteps;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Arrays;

import static org.testng.Assert.assertTrue;

public class MainPageTests {

    private AuthSteps authStep;
    private BaseSteps baseSteps;
    private MainSteps mainSteps;

    @BeforeClass
    public void beforeRun() {
        baseSteps.beforeTest("chrome");
        baseSteps.openAuthPage();
        baseSteps.windowMaximize();
        authStep = new AuthSteps();
        mainSteps = new MainSteps();
        authStep.authorization("gavrilov@coi","gavrilov");
        mainSteps.goToIncidentsList();
    }

    @AfterMethod
    public void resetFilter(){
        mainSteps.clickReset();
        mainSteps.clickApply();
    }

    @Test
    public void filterByThemeMustChangeIncidentTable(){
        ElementsCollection beforeFilter = mainSteps.mainPage.getTableOfIncidents();
        mainSteps.fillTheme("Test");
        mainSteps.clickApply();
        ElementsCollection afterFilter = mainSteps.mainPage.getTableOfIncidents();
        assertTrue(compareArrays(beforeFilter.toArray(), afterFilter.toArray()));
    }

    @Test
    public void filterByAuthorMustChangeIncidentTable(){
        ElementsCollection beforeFilter = mainSteps.mainPage.getTableOfIncidents();
        mainSteps.fillAuthor("Михаил Гаврилов");
        mainSteps.clickApply();
        ElementsCollection afterFilter = mainSteps.mainPage.getTableOfIncidents();
        assertTrue(compareArrays(beforeFilter.toArray(), afterFilter.toArray()));
    }

    @Test
    public void filterByResponsibleMustChangeIncidentTable(){
        ElementsCollection beforeFilter = mainSteps.mainPage.getTableOfIncidents();
        mainSteps.fillResponsible("Михаил Гаврилов");
        mainSteps.clickApply();
        ElementsCollection afterFilter = mainSteps.mainPage.getTableOfIncidents();
        assertTrue(compareArrays(beforeFilter.toArray(), afterFilter.toArray()));
    }

    @Test
    public void filterByCorrespondenceStatusMustChangeIncidentTable(){
        ElementsCollection beforeFilter = mainSteps.mainPage.getTableOfIncidents();
        mainSteps.fillCorrespondenceStatus("Отправлен запрос");
        mainSteps.clickApply();
        ElementsCollection afterFilter = mainSteps.mainPage.getTableOfIncidents();
        assertTrue(compareArrays(beforeFilter.toArray(), afterFilter.toArray()));
    }

    @Test
    public void filterByIncidentStatusMustChangeIncidentTable(){
        ElementsCollection beforeFilter = mainSteps.mainPage.getTableOfIncidents();
        mainSteps.fillIncidentStatus("Новый");
        mainSteps.clickApply();
        ElementsCollection afterFilter = mainSteps.mainPage.getTableOfIncidents();
        assertTrue(compareArrays(beforeFilter.toArray(), afterFilter.toArray()));
    }

    @Test
    public void filterByDateMustChangeIncidentTable(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String endDate = dateFormat.format(date);

        ElementsCollection beforeFilter = mainSteps.mainPage.getTableOfIncidents();
        mainSteps.fillBeginDate("01.05.2020");
        mainSteps.fillEndDate(endDate);
        mainSteps.clickApply();
        ElementsCollection afterFilter = mainSteps.mainPage.getTableOfIncidents();
        assertTrue(compareArrays(beforeFilter.toArray(), afterFilter.toArray()));
    }

    private boolean compareArrays(Object[] beforeFilter, Object[] afterFilter) {
        return (Arrays.equals(beforeFilter, afterFilter)) ? true : false;
    }

}
