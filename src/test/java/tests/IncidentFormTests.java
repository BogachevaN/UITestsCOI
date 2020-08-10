package tests;

import helpers.DataProviderIncident;
import org.testng.annotations.*;
import steps.AuthSteps;
import steps.BaseSteps;
import steps.IncidentSteps;
import steps.MainSteps;

import java.io.IOException;
import java.util.List;

import static org.testng.Assert.*;

public class IncidentFormTests {

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
        mainSteps.goToIncidentsList();
    }

    @BeforeMethod()
    public void beforeTestMethod(){
        mainSteps.goToIncidentsList();
        mainSteps.addIncident();
    }

    @AfterMethod
    public void afterTestMethod(){
        incidentSteps.goToIncidentsList();
    }

    @Test (dataProvider = "requiredFieldsInFormIncident", dataProviderClass = DataProviderIncident.class)
    public void checkRequiredFields_MinimumSetFields(String requiredField) throws IOException, NoSuchFieldException, IllegalAccessException {
        List<String[]> data = incidentSteps.readCSVFileWithData("dataRequiredFieldsForIncident.csv");
        incidentSteps.removeRow(data, requiredField);

        if (requiredField.equals("vectorINT")) {
            incidentSteps.removeRow(data, "event");
            incidentSteps.removeRow(data, "eventDescription");
        }
        if (requiredField.equals("federalDistrict")) {
            incidentSteps.removeRow(data, "subjectOfFederation");
        }

        incidentSteps.setDataInRequiredFieldFromFile(data);
        incidentSteps.saveIncident();
        incidentSteps.sendIncident();

        assertTrue(incidentSteps.containsRequiredText());
    }

    @Test (dataProvider = "requiredFieldsInOwcFormIncident", dataProviderClass = DataProviderIncident.class)
    public void checkRequiredFields_OwcFormSetFields(String requiredField) throws IOException, NoSuchFieldException, IllegalAccessException {
        List<String[]> data = incidentSteps.readCSVFileWithData("dataRequiredFieldsForIncident.csv");
        List<String[]> dataForOwc = incidentSteps.readCSVFileWithData("dataRequiredFieldsForOwcForm.csv");

        incidentSteps.setDataInRequiredFieldFromFile(data);
        incidentSteps.removeRow(dataForOwc, requiredField);

        if (requiredField.equals("payerDetailsType")) {
            incidentSteps.removeRow(dataForOwc, "phoneNumberPayer");
        }
        if (requiredField.equals("beneficiaryDetailsType")) {
            incidentSteps.removeRow(dataForOwc, "phoneNumberBeneficiary");
        }
        if (requiredField.equals("entity")) {
            incidentSteps.removeRow(dataForOwc, "inn");
        }

        incidentSteps.addOpetationWithoutСonsent();
        incidentSteps.scrollToField("//*[contains(text(),'Реквизиты плательщика')]");
        incidentSteps.fillPayerDetailsType("По абонентскому номеру телефона");
        incidentSteps.fillBeneficiaryDetailsType("По абонентскому номеру телефона");
        incidentSteps.setDataInRequiredFieldFromFile(dataForOwc);

        incidentSteps.saveIncident();
        incidentSteps.sendIncident();

        assertTrue(incidentSteps.containsRequiredText());
    }

    @Test
    public void checkRequiredFields_LawEnforcementRequestBlock() throws IOException, NoSuchFieldException, IllegalAccessException {
        List<String[]> data = incidentSteps.readCSVFileWithData("dataRequiredFieldsForIncident.csv");
        incidentSteps.setDataInRequiredFieldFromFile(data);
        incidentSteps.fillAntifraudFormCompany();
        incidentSteps.selectLawEnforcementRequestTrue();
        incidentSteps.saveIncident();
        incidentSteps.sendIncident();
        assertFalse(incidentSteps.containsRequiredText());
    }

    @Test
    public void checkRequiredFields_InAttackedServiceBlock() throws NoSuchFieldException, IllegalAccessException, IOException {
        List<String[]> data = incidentSteps.readCSVFileWithData("dataRequiredFieldsForIncident.csv");
        incidentSteps.setDataInRequiredFieldFromFile(data);
        incidentSteps.fillAntifraudFormCompany();
        incidentSteps.addAttackedService();

        //проверка обязательности заполнения типа севиса
        incidentSteps.fillServiceDescription("Описание сервиса");
        incidentSteps.saveIncident();
        incidentSteps.sendIncident();
        assertTrue(incidentSteps.containsRequiredText());

        //проверка обязательности заполнения описания сервиса
        incidentSteps.clearServiceDescription();
        incidentSteps.fillServiceType("Система АБС");
        incidentSteps.saveIncident();
        incidentSteps.sendIncident();
        assertTrue(incidentSteps.containsRequiredText());
    }

    @Test
    public void checkRequiredFields_IfSelectedIndividual()throws NoSuchFieldException, IllegalAccessException, IOException {
        List<String[]> data = incidentSteps.readCSVFileWithData("dataRequiredFieldsForIncident.csv");
        incidentSteps.setDataInRequiredFieldFromFile(data);
        incidentSteps.fillAntifraudFormIndividual();

        //проверка обязательности паспорта
        incidentSteps.clearPassport();
        incidentSteps.saveIncident();
        incidentSteps.sendIncident();
        assertTrue(incidentSteps.containsRequiredText());

        //инн, снилс опционально
        incidentSteps.clearInn();
        incidentSteps.fillPassport("5705123654");
        incidentSteps.saveIncident();
        incidentSteps.sendIncident();
        assertFalse(incidentSteps.containsRequiredText());
    }

    @Test(dataProvider = "incorrectPhoneNumber", dataProviderClass = DataProviderIncident.class)
    public void checkValidation_FieldPhoneNumber_withIncorrectData(String number) throws IOException, NoSuchFieldException, IllegalAccessException {
        List<String[]> data = incidentSteps.readCSVFileWithData("dataRequiredFieldsForIncident.csv");
        incidentSteps.setDataInRequiredFieldFromFile(data);
        incidentSteps.fillAntifraudFormCompany();
        incidentSteps.clearPhoneNumberPayer();
        incidentSteps.fillPhoneNumberPayer(number);
        incidentSteps.saveIncident();
        assertTrue(incidentSteps.containsText("Некорректное значение поля \"Номер телефона\""));
    }

    @Test(dataProvider = "correctPhoneNumber", dataProviderClass = DataProviderIncident.class)
    public void checkValidation_FieldPhoneNumber_withCorrectData(String number) throws IOException, NoSuchFieldException, IllegalAccessException {
        List<String[]> data = incidentSteps.readCSVFileWithData("dataRequiredFieldsForIncident.csv");
        incidentSteps.setDataInRequiredFieldFromFile(data);
        incidentSteps.fillAntifraudFormCompany();
        incidentSteps.clearPhoneNumberPayer();
        incidentSteps.fillPhoneNumberPayer(number);
        incidentSteps.saveIncident();
        assertFalse(incidentSteps.containsText("Некорректное значение поля \"Номер телефона\""));
    }

    @Test
    public void fillPhoneNumberMustDetermineCountry()  {
        incidentSteps.fillAntifraudFormCompany();
        incidentSteps.clearPhoneNumberPayer();

        incidentSteps.fillPhoneNumberPayer("+7 9134723502");
        assertTrue(incidentSteps.containsText("Россия"));

        incidentSteps.clearPhoneNumberPayer();
        incidentSteps.fillPhoneNumberPayer("+33 456353644");
        assertTrue(incidentSteps.containsText("Франция"));

        incidentSteps.clearPhoneNumberPayer();
        incidentSteps.fillPhoneNumberPayer("+357 99123456");
        assertTrue(incidentSteps.containsText("Кипр"));

        incidentSteps.clearPhoneNumberPayer();
        incidentSteps.fillPhoneNumberPayer("+4428 09999999");
        assertTrue(incidentSteps.containsText("Соединенное Королевство"));

        incidentSteps.clearPhoneNumberPayer();
        incidentSteps.fillPhoneNumberPayer("+90392 6756434");
        assertTrue(incidentSteps.containsText("Турция"));

    }

    @Test(dataProvider = "correctCardNumber", dataProviderClass = DataProviderIncident.class)
    public void checkValidation_FieldCardNumber_withСorrectData(String number) throws IOException, NoSuchFieldException, IllegalAccessException {
        List<String[]> data = incidentSteps.readCSVFileWithData("dataRequiredFieldsForIncident.csv");
        incidentSteps.setDataInRequiredFieldFromFile(data);
        incidentSteps.fillAntifraudFormCompany();
        incidentSteps.fillPayerDetailsType("С использованием платежных карт");
        incidentSteps.fillCardNumber(number);
        incidentSteps.saveIncident();
        assertFalse(incidentSteps.containsText("Некорректное значение поля \"Номер карты\""));
    }

    @Test(dataProvider = "incorrectCardNumber", dataProviderClass = DataProviderIncident.class)
    public void checkValidation_FieldCardNumber_withIncorrectData(String number) throws IOException, NoSuchFieldException, IllegalAccessException {
        List<String[]> data = incidentSteps.readCSVFileWithData("dataRequiredFieldsForIncident.csv");
        incidentSteps.setDataInRequiredFieldFromFile(data);
        incidentSteps.fillAntifraudFormCompany();
        incidentSteps.fillPayerDetailsType("С использованием платежных карт");
        incidentSteps.fillCardNumber(number);
        incidentSteps.saveIncident();
        assertTrue(incidentSteps.containsText("Некорректное значение поля \"Номер карты\""));
    }

    @Test
    public void checkRequiredField_FieldCardNumber() throws IOException, NoSuchFieldException, IllegalAccessException {
        List<String[]> data = incidentSteps.readCSVFileWithData("dataRequiredFieldsForIncident.csv");
        incidentSteps.setDataInRequiredFieldFromFile(data);
        incidentSteps.fillAntifraudFormCompany();
        incidentSteps.fillPayerDetailsType("С использованием платежных карт");
        incidentSteps.saveIncident();
        incidentSteps.sendIncident();
        assertTrue(incidentSteps.containsRequiredText());
    }

    @Test
    public void checkRequiredField_OnlineWallet() throws IOException, NoSuchFieldException, IllegalAccessException {
        List<String[]> data = incidentSteps.readCSVFileWithData("dataRequiredFieldsForIncident.csv");
        incidentSteps.setDataInRequiredFieldFromFile(data);
        incidentSteps.fillAntifraudFormCompany();
        incidentSteps.fillPayerDetailsType("По электронному кошельку");
        incidentSteps.fillOnlineWalletNumberPayer("13674пр");
        assertTrue(incidentSteps.containsText("Некорректное значение поля \"Номер электронного кошелька\""));

        incidentSteps.fillOnlineWalletNumberPayer("136746746857g");
        incidentSteps.saveIncident();
        incidentSteps.sendIncident();
        assertTrue(incidentSteps.containsRequiredText());

        incidentSteps.clearOnlineWalletNumberPayer();
        incidentSteps.fillECommercePaymentSystemPayer("Вебмани");
        incidentSteps.saveIncident();
        incidentSteps.sendIncident();
        assertTrue(incidentSteps.containsRequiredText());
    }

    @Test
    public void checkRequiredField_OtherIdentifier() throws NoSuchFieldException, IllegalAccessException, IOException {
        List<String[]> data = incidentSteps.readCSVFileWithData("dataRequiredFieldsForIncident.csv");
        incidentSteps.setDataInRequiredFieldFromFile(data);
        incidentSteps.fillAntifraudFormCompany();
        incidentSteps.fillPayerDetailsType("Иной идентификатор");
        incidentSteps.saveIncident();
        incidentSteps.sendIncident();
        assertTrue(incidentSteps.containsRequiredText());
    }

    @Test(dataProvider = "correctBankAccountNumber", dataProviderClass = DataProviderIncident.class)
    public void checkValidation_FieldBankAccountNumber_withCorrectData(String number) throws IOException, NoSuchFieldException, IllegalAccessException {
        List<String[]> data = incidentSteps.readCSVFileWithData("dataRequiredFieldsForIncident.csv");
        incidentSteps.setDataInRequiredFieldFromFile(data);
        incidentSteps.fillAntifraudFormCompany();
        incidentSteps.fillPayerDetailsType("По банковским счетам");
        incidentSteps.fillBankAccountNumber(number);
        incidentSteps.saveIncident();
        assertFalse(incidentSteps.containsText("Некорректное значение поля \"Номер банковского счёта\""));
    }

    @Test(dataProvider = "incorrectBankAccountNumber", dataProviderClass = DataProviderIncident.class)
    public void checkValidation_FieldBankAccountNumber_withIncorrectData(String number) throws IOException, NoSuchFieldException, IllegalAccessException {
        List<String[]> data = incidentSteps.readCSVFileWithData("dataRequiredFieldsForIncident.csv");
        incidentSteps.setDataInRequiredFieldFromFile(data);
        incidentSteps.fillAntifraudFormCompany();
        incidentSteps.fillPayerDetailsType("По банковским счетам");
        incidentSteps.fillBankAccountNumber(number);
        incidentSteps.saveIncident();
        assertTrue(incidentSteps.containsText("Некорректное значение поля \"Номер банковского счёта\""));
    }

    @Test
    public void checkRequiredField_FieldBankAccountNumber() throws IOException, NoSuchFieldException, IllegalAccessException {
        List<String[]> data = incidentSteps.readCSVFileWithData("dataRequiredFieldsForIncident.csv");
        incidentSteps.setDataInRequiredFieldFromFile(data);
        incidentSteps.fillAntifraudFormCompany();
        incidentSteps.fillPayerDetailsType("По банковским счетам");
        incidentSteps.fillBik("012345667");
        incidentSteps.saveIncident();
        incidentSteps.sendIncident();
        assertTrue(incidentSteps.containsRequiredText());
    }

    @Test(dataProvider = "correctBik", dataProviderClass = DataProviderIncident.class)
    public void checkValidation_FieldBik_withСorrectData(String number) throws IOException, NoSuchFieldException, IllegalAccessException {
        List<String[]> data = incidentSteps.readCSVFileWithData("dataRequiredFieldsForIncident.csv");
        incidentSteps.setDataInRequiredFieldFromFile(data);
        incidentSteps.fillAntifraudFormCompany();
        incidentSteps.fillPayerDetailsType("По банковским счетам");
        incidentSteps.fillBik(number);
        incidentSteps.saveIncident();
        assertFalse(incidentSteps.containsText("Некорректное значение поля \"БИК\""));
    }

    @Test(dataProvider = "incorrectBik", dataProviderClass = DataProviderIncident.class)
    public void checkValidation_FieldBik_withIncorrectData(String number) throws IOException, NoSuchFieldException, IllegalAccessException {
        List<String[]> data = incidentSteps.readCSVFileWithData("dataRequiredFieldsForIncident.csv");
        incidentSteps.setDataInRequiredFieldFromFile(data);
        incidentSteps.fillAntifraudFormCompany();
        incidentSteps.fillPayerDetailsType("По банковским счетам");
        incidentSteps.fillBik(number);
        incidentSteps.saveIncident();
        assertTrue(incidentSteps.containsText("Некорректное значение поля \"БИК\""));
    }
    @Test
    public void checkRequiredField_FieldBik() throws IOException, NoSuchFieldException, IllegalAccessException {
        List<String[]> data = incidentSteps.readCSVFileWithData("dataRequiredFieldsForIncident.csv");
        incidentSteps.setDataInRequiredFieldFromFile(data);
        incidentSteps.fillAntifraudFormCompany();
        incidentSteps.fillPayerDetailsType("По банковским счетам");
        incidentSteps.fillBankAccountNumber("40817810070001234567");
        incidentSteps.saveIncident();
        incidentSteps.sendIncident();
        assertTrue(incidentSteps.containsRequiredText());
    }

    @Test
    public void createIncidentVectorExtWithDetectedByMustIncidentSaveAndSend() throws IOException, NoSuchFieldException, IllegalAccessException {
        List<String[]> data = incidentSteps.readCSVFileWithData("dataRequiredFieldsForIncident.csv");
        incidentSteps.setDataInRequiredFieldFromFile(data);

        incidentSteps.scrollToField("//*[contains(text(),'EXT')]");
        incidentSteps.selectRadioExt();
        incidentSteps.fillTypeOfAttack("Реализация спам рассылки");
        incidentSteps.fillEvent("Клиент обратился к оператору перевода");
        incidentSteps.fillEventDescription("СМС на телефон из договора счета");

        incidentSteps.fillAntifraudFormCompany();

        incidentSteps.scrollToField("//input[@name='antifraud.transfers.0.transferInfo.detectedBy']");
        incidentSteps.selectDetectedByExt("CLNT");

        incidentSteps.saveIncident();
        incidentSteps.sendIncident();
        assertFalse(incidentSteps.containsRequiredText());
    }

    @Test
    public void createIncidentVectorExtWithoutDetectedByMustIncidentSaveAndNotSend() throws IOException, NoSuchFieldException, IllegalAccessException {
        List<String[]> data = incidentSteps.readCSVFileWithData("dataRequiredFieldsForIncident.csv");
        incidentSteps.setDataInRequiredFieldFromFile(data);
        incidentSteps.scrollToField("//*[contains(text(),'EXT')]");
        incidentSteps.selectRadioExt();
        incidentSteps.fillEvent("Клиент обратился к оператору перевода");
        incidentSteps.fillEventDescription("СМС на телефон из договора счета");

        incidentSteps.fillAntifraudFormCompany();

        incidentSteps.saveIncident();
        incidentSteps.sendIncident();
        assertTrue(incidentSteps.containsRequiredText());
    }

    @Test
    public void createIncidentVectorIntMustIncidentSaveAndSend() throws IOException, NoSuchFieldException, IllegalAccessException {
        List<String[]> data = incidentSteps.readCSVFileWithData("dataRequiredFieldsForIncident.csv");
        incidentSteps.setDataInRequiredFieldFromFile(data);

        incidentSteps.fillAntifraudFormCompany();

        incidentSteps.saveIncident();
        incidentSteps.sendIncident();
        assertFalse(incidentSteps.containsRequiredText());
    }

    @Test
    public void createIncidentWithoutAntifraudMustSaveAndSend() throws IOException, NoSuchFieldException, IllegalAccessException {
        List<String[]> data = incidentSteps.readCSVFileWithData("dataRequiredFieldsForIncident.csv");
        incidentSteps.setDataInRequiredFieldFromFile(data);
        incidentSteps.saveIncident();
        incidentSteps.sendIncident();
        assertFalse(incidentSteps.containsRequiredText());
    }

    @Test
    public void createIncidentWithAttachment500KbMustSaveAndSend() throws IOException, NoSuchFieldException, IllegalAccessException {
        List<String[]> data = incidentSteps.readCSVFileWithData("dataRequiredFieldsForIncident.csv");
        incidentSteps.setDataInRequiredFieldFromFile(data);
        incidentSteps.uploadAttachmentToIncident("File500kB.txt");
        incidentSteps.saveIncident();
        incidentSteps.sendIncident();
    }

   @Test
    public void createIncidentWithAttachment1MbMustError() throws IOException, NoSuchFieldException, IllegalAccessException {
        List<String[]> data = incidentSteps.readCSVFileWithData("dataRequiredFieldsForIncident.csv");
        incidentSteps.setDataInRequiredFieldFromFile(data);
        incidentSteps.uploadAttachmentToIncident("File1Mb.txt");
        incidentSteps.saveIncident();
        assertTrue(incidentSteps.containsAttachmentsMore500KbText());
    }
 //    Пока есть ограничение на фронте по размеру вложений некоторые тесты заигнорила
    @Test @Ignore
    public void createIncidentWithAttachment5MbMustSaveAndSend() throws IOException, NoSuchFieldException, IllegalAccessException {
        List<String[]> data = incidentSteps.readCSVFileWithData("dataRequiredFieldsForIncident.csv");
        incidentSteps.setDataInRequiredFieldFromFile(data);
        incidentSteps.uploadAttachmentToIncident("File5Mb.txt");
        incidentSteps.saveIncident();
        incidentSteps.sendIncident();
    }

    @Test @Ignore
    public void createIncidentWith10AttachmentsMustSaveAndSend() throws IOException, NoSuchFieldException, IllegalAccessException {
        List<String[]> data = incidentSteps.readCSVFileWithData("dataRequiredFieldsForIncident.csv");
        incidentSteps.setDataInRequiredFieldFromFile(data);
        incidentSteps.upload10AttachmentToIncident("(0).txt","(1).txt","(2).txt","(3).txt","(4).txt","(5).txt","(6).txt","(7).txt","(8).txt","(9).txt");
        incidentSteps.sendIncident();
    }

    @Test @Ignore
    public void createIncidentWith11AttachmentsMustError() throws IOException, NoSuchFieldException, IllegalAccessException {
        List<String[]> data = incidentSteps.readCSVFileWithData("dataRequiredFieldsForIncident.csv");
        incidentSteps.setDataInRequiredFieldFromFile(data);
        incidentSteps.upload10AttachmentToIncident("(0).txt","(1).txt","(2).txt","(3).txt","(4).txt","(5).txt","(6).txt","(7).txt","(8).txt","(9).txt");
        incidentSteps.uploadAttachmentToIncident("(10).txt");
        incidentSteps.saveIncident();
        assertTrue(incidentSteps.containsMoreThan10AttachmentsText());
    }

    @Test @Ignore
    public void createIncidentWithAttachmentMore5MbMustError() throws IOException, NoSuchFieldException, IllegalAccessException {
        List<String[]> data = incidentSteps.readCSVFileWithData("dataRequiredFieldsForIncident.csv");
        incidentSteps.setDataInRequiredFieldFromFile(data);
        incidentSteps.uploadAttachmentToIncident("FileMore5Mb.txt");
        assertTrue(incidentSteps.containsAttachmentsMore500KbText());
    }

    @Test @Ignore
    public void deleteAttachmentsInIncidentMustDeleted() throws IOException, NoSuchFieldException, IllegalAccessException {
        List<String[]> data = incidentSteps.readCSVFileWithData("dataRequiredFieldsForIncident.csv");
        incidentSteps.setDataInRequiredFieldFromFile(data);
        incidentSteps.uploadAttachmentToIncident("(0).txt");
        incidentSteps.saveIncident();
        incidentSteps.uploadAttachmentToIncident("(1).txt");
        incidentSteps.saveIncident();
        incidentSteps.uploadAttachmentToIncident("(2).txt");

        int attachmentsExpected3 = incidentSteps.getElementsCollectionSize("div[data-testid=\"fileItem\"]");
        assertEquals(attachmentsExpected3,3);

        incidentSteps.deleteAllAttachmentFromIncident();
        incidentSteps.saveIncident();

        int attachmentsExpected0 = incidentSteps.getElementsCollectionSize("div[data-testid=\"fileItem\"]");
        assertEquals(attachmentsExpected0,0);
    }

    @Test
    public void checkRequiredFieldFederalDistinctSubjectOfFederationMustSaveAndNotSend() throws IOException, NoSuchFieldException, IllegalAccessException {
        List<String[]> data = incidentSteps.readCSVFileWithData("dataRequiredFieldsForIncident.csv");
        incidentSteps.removeRow(data, "federalDistrict");
        incidentSteps.removeRow(data, "subjectOfFederation");
        incidentSteps.setDataInRequiredFieldFromFile(data);
        incidentSteps.saveIncident();
        incidentSteps.sendIncident();
        assertTrue(incidentSteps.containsRequiredText());
    }
}
