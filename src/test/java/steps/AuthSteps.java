package steps;

import com.codeborne.selenide.Selenide;
import helpers.EndPoints;
import pages.AuthPage;

public class AuthSteps extends BaseSteps{
    private AuthPage authPage = null;

    public AuthSteps(){
        authPage = Selenide.page(AuthPage.class);
    }

    public  void authorization(String userName, String password){
        authPage.setUserName(userName);
        authPage.setPassword(password);
        authPage.clickBtn("ВОЙТИ");
    }

}
