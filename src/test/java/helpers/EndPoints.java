package helpers;

public class EndPoints {
    public static final String BASE_URI = "http://coi-webapp-develop.front.ftc.ru";
    public static final String AUTH_PAGE = BASE_URI + "/login";
    public static final String INCIDENTS_PAGE = BASE_URI + "/incidents";
    public static final String INCIDENT_PAGE = BASE_URI + "/incidents/";

    private static final String API_GATEWAY = "http://coi-api-gateway-develop.front.ftc.ru";
    public static final String GET_TOKEN = API_GATEWAY + "/auth/realms/{realm}/protocol/openid-connect/token";
    public static final String CREATE_INCIDENT = API_GATEWAY + "/coordinator/incident";
    public static final String GET_INCIDENT = API_GATEWAY + "/coordinator/incident/{id}";
    public static final String ASSIGN_INCIDENT = API_GATEWAY + "/coordinator/incident/{id}/assign";
    public static final String ARCHIVE_INCIDENT = API_GATEWAY + "/coordinator/incident/{id}/archive";
    public static final String UNARCHIVE_INCIDENT = API_GATEWAY + "/coordinator/incident/{id}/unarchive";
}
