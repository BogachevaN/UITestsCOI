package steps;


import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import pages.IncidentPage;
import pages.MainPage;

import java.nio.file.Paths;
import java.util.List;
import java.io.File;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class IncidentSteps extends BaseSteps {
    public IncidentPage incidentPage = null;
    public MainPage mainPage = null;

    public IncidentSteps(){
        incidentPage = Selenide.page(IncidentPage.class);
        mainPage = Selenide.page(MainPage.class);
    }
    public void setDataInRequiredFieldFromFile(List<String[]> data) throws NoSuchFieldException, IllegalAccessException {
        for (String[] row: data) {
            SelenideElement element = getSelenideElementByName(row[0]);
            String text = row[1];
            fillFieldIfEmpty(element, text);
        }
    }

    public SelenideElement getSelenideElementByName(String fieldName) throws IllegalAccessException, NoSuchFieldException {
        try {
            SelenideElement element = (SelenideElement) incidentPage.getClass().getField(fieldName).get(incidentPage);
            return element;
        } catch (NoSuchFieldException e) {
            SelenideElement element = (SelenideElement) incidentPage.getClass().getDeclaredField(fieldName).get(incidentPage);
            return element;
        }
    }

    public void sendIncident(){
        incidentPage.clickBtn("Отправить в ФинЦЕРТ");
    }

    public void selectLawEnforcementRequestTrue() {
        incidentPage.getRegistrationTechnicalDevice().scrollIntoView(true);
        incidentPage.getLawEnforcementRequestTrue().click();
    }

    public void saveIncident() {
        if ($(byText("Переписка с ФинЦЕРТ")).exists()) {
            incidentPage.clickBtn("Сохранить");
        } else {
            incidentPage.clickBtn("Создать");
        }
    }

    public void addAttackedService() {
        incidentPage.getTypeOfAttack().scrollIntoView(true);
        incidentPage.clickBtn("Добавить сервис");
    }

    public void uploadAttachmentToIncident(String fileName){
        scrollToField("//*[contains(text(),'Вложения')]");
        String path = Paths.get("src", "test", "resources","attachments")
                .toAbsolutePath()
                .toString();

        incidentPage.getUploadAttachmentButton().uploadFile(new File(path+"\\",fileName));
    }
    public void upload10AttachmentToIncident(String fileName0,String fileName1,String fileName2,String fileName3,String fileName4,String fileName5,String fileName6,String fileName7,String fileName8,String fileName9){
        scrollToField("//*[contains(text(),'Вложения')]");
        String path = Paths.get("src", "test", "resources","attachments")
                .toAbsolutePath()
                .toString();

        incidentPage.getUploadAttachmentButton().uploadFile(new File(path+"\\",fileName0));
        saveIncident();
        incidentPage.getUploadAttachmentButton().uploadFile(new File(path+"\\",fileName1));
        saveIncident();
        incidentPage.getUploadAttachmentButton().uploadFile(new File(path+"\\",fileName2));
        saveIncident();
        incidentPage.getUploadAttachmentButton().uploadFile(new File(path+"\\",fileName3));
        saveIncident();
        incidentPage.getUploadAttachmentButton().uploadFile(new File(path+"\\",fileName4));
        saveIncident();
        incidentPage.getUploadAttachmentButton().uploadFile(new File(path+"\\",fileName5));
        saveIncident();
        incidentPage.getUploadAttachmentButton().uploadFile(new File(path+"\\",fileName6));
        saveIncident();
        incidentPage.getUploadAttachmentButton().uploadFile(new File(path+"\\",fileName7));
        saveIncident();
        incidentPage.getUploadAttachmentButton().uploadFile(new File(path+"\\",fileName8));
        saveIncident();
        incidentPage.getUploadAttachmentButton().uploadFile(new File(path+"\\",fileName9));
        saveIncident();

    }

    public void deleteAttachmentFromIncident(){
        scrollToField("//*[contains(text(),'Вложения')]");
        incidentPage.getDeleteAttachmentButton().click();
    }

    public void deleteAllAttachmentFromIncident(){
        scrollToField("//*[contains(text(),'Вложения')]");
        while(incidentPage.getDeleteAttachmentButton().exists() != false)
        {incidentPage.getDeleteAttachmentButton().click();} }

    public int getElementsCollectionSize(String cssSelector){
        return $$(new By.ByCssSelector(cssSelector)).size();
    }

    public void fillServiceDescription(String description) {
        incidentPage.getServiceDescription().setValue(description);
    }

    public void clearServiceDescription() {
        clearInputOrTextareaField(incidentPage.getServiceDescription());
    }

    public void fillServiceType(String type) {
        selectElementInList(incidentPage.getServiceType(),type);
    }

    public void selectIndividual() {
        incidentPage.getEventDescription().scrollIntoView(true);
        incidentPage.getIndividual().click();
    }

    public void selectCompany() {
        incidentPage.getEventDescription().scrollIntoView(true);
        incidentPage.getCompany().click();
    }

    public void fillInn(String s) {
        incidentPage.getInn().setValue(s);
    }


    public void clearInn() {
        clearInputOrTextareaField(incidentPage.getInn());
    }

    public void fillPassport(String s) {
        incidentPage.getPassport().setValue(s);
    }

    public void clearPassport() { clearInputOrTextareaField(incidentPage.getPassport()); }

    public void fillSum(String value) { incidentPage.getSum().setValue(value); }

    public void fillCurrencyRUB() { incidentPage.getCurrencyRUB().click();}

    public void fillDatetimeAt(String value) { incidentPage.getDatetimeAt().setValue(value); }

    public void fillPhoneNumberPayer(String number) { incidentPage.getPhoneNumberPayer().setValue(number); }

    public void clearPhoneNumberPayer () {clearInputOrTextareaField(incidentPage.getPhoneNumberPayer());}

    public void clearPhoneNumberBeneficiary () {clearInputOrTextareaField(incidentPage.getPhoneNumberBeneficiary());}

    public void fillPayerDetailsType(String s) {
        selectElementInList(incidentPage.getPayerDetailsType(),s);
    }

    public void fillCardNumber(String number) {
        incidentPage.getCardNumberPayer().setValue(number);
    }

    public void fillBankAccountNumber(String number) {
        incidentPage.getBankAccountNumberPayer().setValue(number);
    }

    public void fillBik(String number) {
        incidentPage.getBikPayer().setValue(number);
    }

    public void goToIncidentsList() {
        Selenide.refresh();
        if(incidentPage.getIncidents().exists()) {
            incidentPage.getIncidents().click();
        } else {
            mainPage.getIncidents().click();
        }
    }

    public void fillOnlineWalletNumberPayer(String s) {
        clearInputOrTextareaField(incidentPage.getOnlineWalletNumberPayer());
        incidentPage.getOnlineWalletNumberPayer().setValue(s);
    }

    public void clearOnlineWalletNumberPayer() {
        clearInputOrTextareaField(incidentPage.getOnlineWalletNumberPayer());
    }

    public void fillECommercePaymentSystemPayer(String name) {
        incidentPage.getECommercePaymentSystemPayer().setValue(name);
    }

    public void fillBeneficiaryDetailsType(String value) { selectElementInList(incidentPage.getBeneficiaryDetailsType(),value); }

    public void fillPhoneNumberBeneficiary(String value) { incidentPage.getPhoneNumberBeneficiary().setValue(value); }

    public void fillInsideCountry() { incidentPage.getInsideCountry().click(); }

    public void selectRadioExt () { incidentPage.getVectorExt().click(); }

    public void selectRadioInt () { incidentPage.getVectorInt().click(); }

    public void selectDetectedByExt (String variantValue) { incidentPage.getDetectedByExt(variantValue).click(); }

//    public void selectDetectedByInt () { incidentPage.getDetectedByInt().click(); }

    public void fillTypeOfAttack(String type) {
        selectElementInList(incidentPage.getTypeOfAttack(),type);
    }

    public void fillEvent(String event) {
        selectElementInList(incidentPage.getEvent(),event);
    }

    public void fillEventDescription(String eventDescription) { selectElementInList(incidentPage.getEventDescription(),eventDescription); }

    public void scrollToField (String xpathToField ) { $(By.xpath(xpathToField)).scrollIntoView("{block: \"center\"}"); }


    public void fillAntifraudFormIndividual (){
        addOpetationWithoutСonsent();
        selectIndividual();
        fillInn("500100732259");
        fillPassport("5013121314");
        fillSum("5000");
        fillCurrencyRUB();
        fillDatetimeAt("06.02.2020 14:30");
        fillPayerDetailsType("По абонентскому номеру телефона");
        fillPhoneNumberPayer("89134723501");
        fillBeneficiaryDetailsType("По абонентскому номеру телефона");
        fillPhoneNumberBeneficiary("380484855443");
        fillInsideCountry();
    }

    public void fillAntifraudFormCompany (){
        addOpetationWithoutСonsent();
        selectCompany();
        fillInn("5421110463");
        fillSum("5000");
        fillCurrencyRUB();
        fillDatetimeAt("06.02.2020 14:30");
        fillPayerDetailsType("По абонентскому номеру телефона");
        fillPhoneNumberPayer("89134723501");
        fillBeneficiaryDetailsType("По абонентскому номеру телефона");
        fillPhoneNumberBeneficiary("380484855443");
        fillInsideCountry();
    }
    public void makeMeAssignee (){
        incidentPage.getMakeMeAssignee().click();
        sleep(1000); }

    public void makeMyselfAssigneeAndSave (){
        incidentPage.getMakeMyselfAssigneeAndSave().click();
        sleep(1000);}

    public void makeMyselfAssignee (){
        incidentPage.getMakeMyselfAssignee().click();
        sleep(1000);}

    public void cancelAssignee (){
        incidentPage.getCancelAssignee().click();
        sleep(1000);}

    public void makeCurrentUserAssignee (){
        incidentPage.getCurrentUserAssignee().click();
        sleep(1000);}

    public void closeWithoutSave (){
        incidentPage.getCloseWithoutSave().click();
        sleep(1000);}


    public void selectFederalDistrict (String federalDistinct) {
        scrollToField("//*[contains(text(),'Географическое положение')]");
        selectElementInList(incidentPage.getFederalDistrict(),federalDistinct); }

    public void selectSubjectOfFederation (String subjectOfFederation) {
        scrollToField("//*[contains(text(),'Географическое положение')]");
        selectElementInList(incidentPage.getSubjectOfFederation(),subjectOfFederation); }

    public void fillLocality (String locality) {
        scrollToField("//*[contains(text(),'Географическое положение')]");
        incidentPage.getLocality().setValue(locality);}

    public void fillGeographicalPosition (){
        scrollToField("//*[contains(text(),'Географическое положение')]");
        selectFederalDistrict ("Сибирский федеральный округ");
        selectSubjectOfFederation("Новосибирская область");
        fillLocality("Новосибирск");
    }
    public void addOpetationWithoutСonsent(){
        scrollToField("//button[@name=\"add-button\"]");
        incidentPage.clickBtn("+ Добавить операцию");
    }

    public void archiveIncident(){
        incidentPage.clickBtn("Архивировать");
    }

    public void returnToWorkIncident(){
        incidentPage.clickBtn("Вернуть в работу");
    }

    public void makeMyselfAssigneeAndArchveIncident (){
        incidentPage.getMakeMyselfAssigneeAndArchveIncident().click();
        sleep(1000);}

    public void closeAndDoNotArchive (){
        incidentPage.getCloseAndDoNotArchive().click();
        sleep(1000);}
}
