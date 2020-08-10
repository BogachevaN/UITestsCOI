package tests;

import helpers.RestApiCoordinator;
import lombok.val;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import steps.AuthSteps;
import steps.BaseSteps;
import steps.IncidentSteps;
import steps.MainSteps;

import java.io.IOException;
import java.util.UUID;

import static com.codeborne.selenide.Selenide.*;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class ArchiveIncidentTests extends TestBase {

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
        authStep.authorization("gavrilov@coi", "gavrilov");
    }

    public String token;
    public String currentAssignee;
    public int incidentId;

    @BeforeMethod()
    public void createIncidentForTest() throws IOException {
        token = COI_AUTH.getToken();
        val requestBody = getJsonResource("createIncidentWithoutAssignee.json")
                .set("$.attachment.header.sourceId", UUID.randomUUID())
                .set("theme", "Тест архивация UI")
                .set("assignee", "gavrilov").jsonString();
        val responseBody = RestApiCoordinator.createIncident(token, requestBody);

        incidentId = responseBody.jsonPath().getInt("id");
        currentAssignee = responseBody.jsonPath().getString("assignee");

        baseSteps.openIncidentPage(incidentId);
    }

    @AfterMethod
    public void afterTestMethod() {
        incidentSteps.goToIncidentsList();
    }

    @Test
    public void archiveIncidentMustArchivedStatus() {
        incidentSteps.archiveIncident();
        sleep(1000);
        assertTrue(incidentSteps.containsText("Вернуть в работу"));
    }

    @Test
    public void unarchiveIncidentMustInProgressStatus() {
        RestApiCoordinator.archiveIncident(token, incidentId);
        refresh();
        incidentSteps.returnToWorkIncident();
        sleep(1000);
        assertTrue(incidentSteps.containsText("Отправить в ФинЦЕРТ"));
        assertTrue(incidentSteps.containsText("Архивировать"));
    }

    @Test
    public void archiveIncidentAssigneeNullMustArchivedStatus() {
        RestApiCoordinator.assignIncident(token, incidentId, currentAssignee, null);
        refresh();
        assertFalse(incidentSteps.containsText("Архивировать"));
        incidentSteps.makeMeAssignee();
        assertTrue(incidentSteps.containsText("Отправить в ФинЦЕРТ"));
        assertTrue(incidentSteps.containsText("Архивировать"));
    }

    @Test
    public void archiveIncidentUserNotAssigneeMustArchivedStatus() {
        RestApiCoordinator.assignIncident(token, incidentId, currentAssignee, "sidorov");
        refresh();
        assertFalse(incidentSteps.containsText("Архивировать"));
        incidentSteps.makeMeAssignee();
        assertTrue(incidentSteps.containsText("Отправить в ФинЦЕРТ"));
        assertTrue(incidentSteps.containsText("Архивировать"));
    }

    @Test
    public void archiveIncidentUserMustConflictMakeUserAssignee() {
        RestApiCoordinator.assignIncident(token, incidentId, currentAssignee, "sidorov");
        incidentSteps.archiveIncident();
        sleep(1000);
        assertTrue(incidentSteps.containsText("Изменился ответственный"));
        incidentSteps.makeMyselfAssigneeAndArchveIncident();
        assertTrue(incidentSteps.containsText("Вернуть в работу"));
    }

    @Test
    public void archiveIncidentUserMustConflictCloseWithoutSave() {
        RestApiCoordinator.assignIncident(token, incidentId, currentAssignee, "sidorov");
        incidentSteps.archiveIncident();
        sleep(1000);
        assertTrue(incidentSteps.containsText("Изменился ответственный"));
        incidentSteps.closeAndDoNotArchive();
        assertFalse(incidentSteps.containsText("Архивировать"));
    }

}
