package tests;

import helpers.RestApiCoordinator;
import lombok.val;
import org.testng.annotations.*;

import steps.AuthSteps;
import steps.BaseSteps;
import steps.IncidentSteps;
import steps.MainSteps;

import java.io.IOException;
import java.util.UUID;


import static com.codeborne.selenide.Selenide.*;
import static org.testng.Assert.assertTrue;

public class AssigneeUserTests extends TestBase {

    private AuthSteps authStep;
    private BaseSteps baseSteps;
    private MainSteps mainSteps;
    private IncidentSteps incidentSteps;

    @BeforeClass
    public void beforeRun() {
        baseSteps.beforeTest("chrome");
        baseSteps.openAuthPage();
        baseSteps.windowMaximize();
        authStep = new AuthSteps();
        mainSteps = new MainSteps();
        incidentSteps = new IncidentSteps();
        authStep.authorization("gavrilov@coi","gavrilov");
    }

    public String token;
    public String currentAssignee;
    public int incidentId;

    @BeforeMethod()
    public void createIncidentForTest()throws IOException{
        token = COI_AUTH.getToken();
        val requestBody = getJsonResource("createIncidentWithoutAssignee.json")
                .set("$.attachment.header.sourceId", UUID.randomUUID()).jsonString();
        val responseBody = RestApiCoordinator.createIncident(token, requestBody);

        incidentId = responseBody.jsonPath().getInt("id");
        currentAssignee = responseBody.jsonPath().getString("assignee");

        baseSteps.openIncidentPage(incidentId);
    }

    @AfterMethod
    public void afterTestMethod(){
        incidentSteps.goToIncidentsList();
    }

    @Test
    public void setAssigneeCurrentUserMustAssigneCurrentUser()  {
         incidentSteps.makeMeAssignee();
         assertTrue(incidentSteps.containsText("Отправить в ФинЦЕРТ"));
    }
    @Test
    public void setAssigneeCurrentUserAndCancelMustAssigneeNull()  {
        incidentSteps.makeMeAssignee();
        assertTrue(incidentSteps.containsText("Отправить в ФинЦЕРТ"));
        incidentSteps.cancelAssignee();
        assertTrue(incidentSteps.containsText("Назначить меня ответственным"));
    }

    @Test
    public void setAssigneeFromUserToCurrentUserMustAssigneCurrentUser()  {
        RestApiCoordinator.assignIncident(token,incidentId,null,"selivjorstova").statusCode();
        refresh();// Чтобы увидеть изменения на странице
        incidentSteps.makeMeAssignee();
        assertTrue(incidentSteps.containsText("Отправить в ФинЦЕРТ"));
    }

    @Test
    public void setAssigneeCurrentUserAndCancelMustConflictSetCurrentUser()  {
        incidentSteps.makeMeAssignee();

        String currentAssignee = RestApiCoordinator.getIncident(token,incidentId).jsonPath().getString("assignee");
        RestApiCoordinator.assignIncident(token,incidentId,currentAssignee,"selivjorstova");
        incidentSteps.cancelAssignee();
        assertTrue(incidentSteps.containsText("Смена ответственного"));
        incidentSteps.makeMyselfAssignee();
        assertTrue(incidentSteps.containsText("Отправить в ФинЦЕРТ"));
    }

    @Test
    public void setAssigneeCurrentUserAndCancelMustConflictSetOtherUser()  {
        incidentSteps.makeMeAssignee();

        String currentAssignee = RestApiCoordinator.getIncident(token,incidentId).jsonPath().getString("assignee");
        RestApiCoordinator.assignIncident(token,incidentId,currentAssignee,"selivjorstova");
        incidentSteps.cancelAssignee();
        assertTrue(incidentSteps.containsText("Смена ответственного"));
        incidentSteps.makeCurrentUserAssignee();
        assertTrue(incidentSteps.containsText("Назначить меня"));
    }

    @Test
    public void setAssigneeCurrentUserAndSaveMustConflictSetCurrentUser() {
        incidentSteps.makeMeAssignee();

        String currentAssignee = RestApiCoordinator.getIncident(token,incidentId).jsonPath().getString("assignee");
        RestApiCoordinator.assignIncident(token,incidentId,currentAssignee,"selivjorstova");

        incidentSteps.scrollToField("//*[contains(text(),'EXT')]");
        incidentSteps.selectRadioExt();
        incidentSteps.saveIncident();
        sleep(1000);
        assertTrue(incidentSteps.containsText("Изменился ответственный"));
        incidentSteps.makeMyselfAssigneeAndSave();
        assertTrue(incidentSteps.containsText("Отправить в ФинЦЕРТ"));
    }

    @Test
    public void setAssigneeCurrentUserAndSaveMustConflictCancelChanges() {
        incidentSteps.makeMeAssignee();

        String currentAssignee = RestApiCoordinator.getIncident(token,incidentId).jsonPath().getString("assignee");
        RestApiCoordinator.assignIncident(token,incidentId,currentAssignee,"selivjorstova");

        incidentSteps.scrollToField("//*[contains(text(),'EXT')]");
        incidentSteps.selectRadioExt();

        incidentSteps.saveIncident();
        sleep(1000);
        assertTrue(incidentSteps.containsText("Изменился ответственный"));
        incidentSteps.closeWithoutSave();
        assertTrue(incidentSteps.containsText("Добавить инцидент"));
    }
}
