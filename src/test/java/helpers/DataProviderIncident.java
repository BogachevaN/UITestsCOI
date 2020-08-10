package helpers;

import org.testng.annotations.DataProvider;

public class DataProviderIncident {
    @DataProvider(name = "requiredFieldsInFormIncident")
    public Object[][] requiredFieldsInFormIncident() {
        return new Object[][]{
            {"fixationAt"},
            {"description"},
            {"fincertHelpFalse"},
            {"typeOfIncident"},
            {"vectorINT"},
            {"typeOfAttack"},
            {"registrationDepartment"},
            {"registrationTechnicalDevice"},
            {"lawEnforcementRequestFalse"},
            {"event"},
            {"eventDescription"},
            {"subjectOfFederation"},
            {"registrationDepartment"}
        };
    }

    @DataProvider(name = "requiredFieldsInOwcFormIncident")
    public Object[][] requiredFieldsInOwcFormIncident() {
        return new Object[][]{
            {"entity"},
            {"inn"},
            {"sum"},
            {"currencyRUB"},
            {"datetimeAt"},
            {"insideCountry"}
        };
    }

    @DataProvider(name = "incorrectPhoneNumber")
    public Object[][] incorrectPhoneNumber() {
        return new Object[][]{
            {" "},
            {"8913453567"},
            {"1234567891o"},
            {"0000000000"},
            {"1234567890123456"}
        };
    }

    @DataProvider(name = "correctPhoneNumber")
    public Object[][] correctPhoneNumber() {
        return new Object[][]{
            {"+7(913)4354657"},
            {"+35799123456"},
            {"8-913-472-67-23"},
            {"8(913)476-76-89"},
            {"39 333 333 3333"}
        };
    }

    @DataProvider(name = "incorrectCardNumber")
    public Object[][] incorrectCardNumber() {
        return new Object[][]{
            {"37144963539843178965"},
            {"4012888h88881881"},
            {"5105105*05105100"},
            {" "},
            {"67628038888"}
        };
    }

    @DataProvider(name = "correctCardNumber")
    public Object[][] correctCardNumber() {
        return new Object[][]{
            {"371449635398431"},
            {"4012888888881881"},
            {"5105105105105100"},
            {"2200770212727079"},
            {"676280388885503265"}
        };
    }

    @DataProvider(name = "correctBankAccountNumber")
    public Object[][] correctBankAccountNumber() {
        return new Object[][]{
            {"40817810070001234567"},
            {"10817810070001234567"},
            {"20817810070001234567"},
            {"30817810070001234567"},
            {"50817810070001234567"}
        };
    }

    @DataProvider(name = "incorrectBankAccountNumber")
    public Object[][] incorrectBankAccountNumber() {
        return new Object[][]{
            {"90817810070001234567"},
            {"70817810070001234567"},
            {"60817810070001234567"},
            {"00817810070001234567"},
            {"408178100700012345671"},
            {"4081781007000123456"},
            {" "}
        };
    }

    @DataProvider(name = "correctBik")
    public Object[][] correctBik() {
        return new Object[][]{
                {"012345667"},
                {"101288888"},
                {"210510510"},
                {"020077021"},
                {"176280388"}
        };
    }
    @DataProvider(name = "incorrectBik")
    public Object[][] incorrectBik() {
        return new Object[][]{
                {"01234566"},
                {"1012888889"},
                {"310510510"},
                {"02007702l"},
                {" "}
        };
    }
}
