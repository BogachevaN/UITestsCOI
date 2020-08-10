package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;

public class IncidentPage extends BasePage {
    @FindBy(xpath = "//input[@name='theme']") public SelenideElement theme;
    @FindBy(xpath = "//input[@name='fixationAt']") public SelenideElement fixationAt;
    @FindBy(xpath = "//textarea[@name='description']") public SelenideElement description;
    @FindBy(xpath = "//*[contains(text(),'Требуется')]") public SelenideElement fincertHelpTrue;
    @FindBy(xpath = "//*[contains(text(),'Не требуется')]") public SelenideElement fincertHelpFalse;
    @FindBy(xpath = "//*[contains(text(),'Тип инцидента')]") public SelenideElement typeOfIncident;
    @FindBy(xpath = "//*[contains(text(),'Федеральный округ')]") public SelenideElement federalDistrict;
    @FindBy(xpath = "//*[contains(text(),'Субъект федерации')]") public SelenideElement subjectOfFederation;
    @FindBy(xpath = "//*[contains(text(),'Населенный пункт')]") public SelenideElement locality;
    @FindBy(xpath = "//*[contains(text(),'EXT')]") public SelenideElement vectorEXT;
    @FindBy(xpath = "//*[contains(text(),'INT')]") public SelenideElement vectorINT;
    @FindBy(xpath = "//input[@name='antifraud.transfers.0.transferInfo.detectedBy']") public SelenideElement detectedByExt;
    @FindBy(xpath = "//input[@name='antifraud.transfers.0.transferInfo.detectedBy']") public SelenideElement detectedByInt;
    @FindBy(xpath = "//*[contains(text(),'Тип атаки')]") public SelenideElement typeOfAttack;
    @FindBy(xpath = "//input[@name='registration.department']") public SelenideElement registrationDepartment;
    @FindBy(xpath = "//input[@name='registration.technicalDevice']") public SelenideElement registrationTechnicalDevice;
    @FindBy(xpath = "//*[contains(text(),'Есть запрос')]") public SelenideElement lawEnforcementRequestTrue;
    @FindBy(xpath = "//*[contains(text(),'Нет запроса')]") public SelenideElement lawEnforcementRequestFalse;
    @FindBy(xpath = "//label[contains(text(),'Событие')]") public SelenideElement event;
    @FindBy(xpath = "//label[contains(text(),'Тип и способ описания')]") public SelenideElement eventDescription;
    @FindBy(css = "input[type=\"file\"]") public SelenideElement uploadAttachment;
    @FindBy(css = "div[data-testid=\"delete\"]") public SelenideElement deleteAttachment;
    @FindBy(xpath = "//*[contains(text(),'Юридическое лицо')]") public SelenideElement company;
    @FindBy(xpath = "//*[contains(text(),'Физическое лицо')]") public SelenideElement individual;
    @FindBy(xpath = "//input[@name='antifraud.transfers.0.inn']") public SelenideElement inn;
    @FindBy(xpath = "//input[@name='antifraud.transfers.0.hashes.0.hash']") public SelenideElement passport;
    @FindBy(xpath = "//input[@name='antifraud.transfers.0.sum']") public SelenideElement sum;
    @FindBy(xpath = "//*[@for='currency']") public SelenideElement currency;
    @FindBy(xpath = "//*[contains(text(),'RUB – Российский рубль')]") public SelenideElement currencyRUB;
    @FindBy(xpath = "//input[@name='antifraud.transfers.0.datetimeAt']") public SelenideElement datetimeAt;
    @FindBy(xpath = "//input[@name='antifraud.transfers.0.transferInfo.payerTransferId.type']") public SelenideElement payerDetailsType;
    @FindBy(xpath = "//input[@name='antifraud.transfers.0.transferInfo.payerTransferId.number']") public SelenideElement cardNumberPayer;
    @FindBy(xpath = "//input[@name='antifraud.transfers.0.transferInfo.payerTransferId.number']") public SelenideElement phoneNumberPayer;
    @FindBy(xpath = "//input[@name='antifraud.transfers.0.transferInfo.payerTransferId.number']") public SelenideElement onlineWalletNumberPayer;
    @FindBy(xpath = "//input[@name='antifraud.transfers.0.transferInfo.payerTransferId.number']") public SelenideElement bankAccountNumberPayer;
    @FindBy(xpath = "//input[@name='antifraud.transfers.0.transferInfo.payerTransferId.bik']") public SelenideElement BikPayer;
    @FindBy(xpath = "//input[@name='antifraud.transfers.0.transferInfo.payerTransferId.eCommercePaymentSystem']") public SelenideElement eCommercePaymentSystemPayer;
    @FindBy(xpath = "//input[@name='antifraud.transfers.0.transferInfo.payeeTransferId.type']") public SelenideElement beneficiaryDetailsType;
    @FindBy(xpath = "//input[@name='antifraud.transfers.0.transferInfo.payeeTransferId.number']") public SelenideElement phoneNumberBeneficiary;
    @FindBy(xpath = "//*[contains(text(),'Внутри страны')]") public SelenideElement insideCountry;
    @FindBy(xpath = "//label[contains(text(),'Тип сервиса')]") public SelenideElement serviceType;
    @FindBy(xpath = "//textarea[@name='affectedServices[0].description']") public SelenideElement serviceDescription;
    @FindBy(xpath = "//a[contains(text(),'Инциденты')]") public SelenideElement incidents;
    @FindBy(xpath = "//span[contains(text(),'Назначить меня')]") public SelenideElement makeMeAssignee;
    @FindBy(xpath = "//span[contains(text(),'Назначить себя')]") public SelenideElement makeMyselfAssignee;
    @FindBy(xpath = "//span[contains(text(),'Назначить себя и сохранить')]") public SelenideElement makeMyselfAssigneeAndSave;
    @FindBy(xpath = "//span[contains(text(),'Оставить текущего')]") public SelenideElement currentUserAssignee;
    @FindBy(xpath = "//span[contains(text(),'Отменить')]") public SelenideElement cancelAssignee;
    @FindBy(xpath = "//span[contains(text(),'Закрыть с потерей изменений')]") public SelenideElement closeWithoutSave;
    @FindBy(xpath = "//span[contains(text(),'Назначить себя и перевести в архив')]") public SelenideElement makeMyselfAssigneeAndArchveIncident;
    @FindBy(xpath = "//span[contains(text(),'Закрыть')]") public SelenideElement closeAndDoNotArchive;

    public SelenideElement getMakeMyselfAssignee() { return makeMyselfAssignee; }

    public SelenideElement getMakeMyselfAssigneeAndSave() { return makeMyselfAssigneeAndSave; }

    public SelenideElement getCurrentUserAssignee() { return currentUserAssignee; }

    public SelenideElement getCloseWithoutSave() { return closeWithoutSave; }

    public SelenideElement getCancelAssignee() { return cancelAssignee; }

    public SelenideElement getMakeMeAssignee() { return makeMeAssignee; }

    public SelenideElement getFederalDistrict() {
        return federalDistrict;
    }

    public SelenideElement getSubjectOfFederation() {
        return subjectOfFederation;
    }

    public SelenideElement getLocality() {
        return locality;
    }

    public SelenideElement getOnlineWalletNumberPayer() {
        return onlineWalletNumberPayer;
    }

    public SelenideElement getECommercePaymentSystemPayer() {
        return eCommercePaymentSystemPayer;
    }

    public SelenideElement getBeneficiaryDetailsType() {
        return beneficiaryDetailsType;
    }

    public SelenideElement getPhoneNumberBeneficiary() {
        return phoneNumberBeneficiary;
    }

    public SelenideElement getInsideCountry() {
        return insideCountry;
    }

    public SelenideElement getIncidents() {
        return incidents;
    }

    public SelenideElement getCardNumberPayer() { return cardNumberPayer; }

    public SelenideElement getBankAccountNumberPayer() { return bankAccountNumberPayer;}

    public SelenideElement getBikPayer() { return BikPayer;}

    public SelenideElement getPayerDetailsType() {
        return payerDetailsType;
    }

    public SelenideElement getPhoneNumberPayer() {
        return phoneNumberPayer;
    }

    public SelenideElement getEvent() { return event; }

    public SelenideElement getEventDescription() { return eventDescription; }

    public SelenideElement getDeleteAttachmentButton() { return deleteAttachment; }

    public SelenideElement getUploadAttachmentButton() { return uploadAttachment; }

    public SelenideElement getInn() { return inn; }

    public SelenideElement getCompany() {
        return company;
    }

    public SelenideElement getIndividual() {
        return individual;
    }

    public SelenideElement getTypeOfAttack() {
        return typeOfAttack;
    }

    public SelenideElement getServiceType() {
        return serviceType;
    }

    public SelenideElement getServiceDescription() {
        return serviceDescription;
    }

    public SelenideElement getLawEnforcementRequestTrue() {
        return lawEnforcementRequestTrue;
    }

    public SelenideElement getRegistrationTechnicalDevice() {
        return registrationTechnicalDevice;
    }

    public SelenideElement getPassport() {
        return passport;
    }

    public SelenideElement getSum() { return sum; }

    public SelenideElement getCurrencyRUB() { return currencyRUB; }

    public SelenideElement getDatetimeAt() { return datetimeAt; }

    public SelenideElement getVectorExt() { return vectorEXT; }

    public SelenideElement getVectorInt() { return vectorINT; }

    public SelenideElement getDetectedByExt(String variantValue) { return detectedByExt.shouldHave(Condition.attribute("value",variantValue));}

    public SelenideElement getDetectedByInt() {
        return detectedByInt;
    }

    public SelenideElement getMakeMyselfAssigneeAndArchveIncident() {
        return makeMyselfAssigneeAndArchveIncident;
    }

    public SelenideElement getCloseAndDoNotArchive() {
        return closeAndDoNotArchive;
    }
}
