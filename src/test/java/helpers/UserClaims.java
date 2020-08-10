package helpers;

import java.util.Collection;

public class UserClaims {

    private final String realm;
    private final String username;
    private final Collection<String> roles;

    UserClaims(String realm, String username, Collection<String> roles) {
        this.realm = realm;
        this.username = username;
        this.roles = roles;
    }
}
