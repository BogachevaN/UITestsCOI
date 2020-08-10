package helpers;

import com.jayway.restassured.response.Response;
import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.http.ContentType.JSON;


public class RestApiCoordinator {

    public static Response createIncident(String accessToken, String body) {
        return given()
                .log().all()
                .headers("Authorization", "Bearer " + accessToken)
                .contentType(JSON)
                .body(body)
                .when()
                .post(EndPoints.CREATE_INCIDENT);
    }

    public static Response getIncident(String accessToken, int incidentId) {
        return given()
                .log().all()
                .headers("Authorization", "Bearer " + accessToken)
                .contentType(JSON)
                .when()
                .get(EndPoints.GET_INCIDENT, incidentId);
    }

    public static Response assignIncident(String accessToken, int incidentId, String fromAssignee, String toAssignee) {
        if (fromAssignee == null) {
            return given()
                    .log().all()
                    .headers("Authorization", "Bearer " + accessToken)
                    .contentType(JSON)
                    .body("{\"from\":" + fromAssignee + "," + "\"to\":\"" + toAssignee + "\"}")
                    .when()
                    .post(EndPoints.ASSIGN_INCIDENT, incidentId);
        } else return given()
                .log().all()
                .headers("Authorization", "Bearer " + accessToken)
                .contentType(JSON)
                .body("{\"from\":\"" + fromAssignee + "\"," + "\"to\":\"" + toAssignee + "\"}")
                .when()
                .post(EndPoints.ASSIGN_INCIDENT, incidentId);
    }

    public static Response archiveIncident(String accessToken, int incidentId) {
        return given()
                .log().all()
                .headers("Authorization", "Bearer " + accessToken)
                .contentType(JSON)
                .when()
                .post(EndPoints.ARCHIVE_INCIDENT, incidentId);
    }

    public static Response unarchiveIncident(String accessToken, int incidentId) {
        return given()
                .log().all()
                .headers("Authorization", "Bearer " + accessToken)
                .contentType(JSON)
                .when()
                .post(EndPoints.UNARCHIVE_INCIDENT, incidentId);
    }
}
