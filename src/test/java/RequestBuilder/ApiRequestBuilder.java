package RequestBuilder;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static Base.BaseURIs.baseURL;
import static Payload.PayloadBuilder.*;

public class ApiRequestBuilder {
    static String authToken;
    static String adminToken;
    static String userToken;
    static String registeredUserId;
    static String adminEmail;

    public static Response loginUserResponse(String email, String password){

        String apiPath = "/APIDEV/login";
        Response response = RestAssured.given()
                .baseUri(baseURL)
                .basePath(apiPath)
                .header("Content-Type", "application/json")
                .body(loginUserPayload(email, password))
                .log().all()
                .post()
                .then().extract().response();

        String token = response.jsonPath().getString("data.token");
        authToken = token;

        // Distinguish between admin and user logins
        if (adminEmail != null && email.equals(adminEmail)) {
            adminToken = token;
        } else {
            userToken = token;
        }

        return response;
    }

    public static Response registerUserResponse(String firstName, String lastName, String email, String password, String groupId){

        String apiPath = "/APIDEV/register";
        Response response = RestAssured.given()
                .baseUri(baseURL)
                .basePath(apiPath)
                .header("Content-Type", "application/json")
                .body(registerUserPayload(firstName, lastName, email, password, groupId))
                .log().all()
                .post()
                .then().extract().response();
        registeredUserId = response.jsonPath().getString("data.id");
        return response;
    }

    public static Response approveUserRegistrationResponse(){

        String apiPath = "/APIDEV/admin/users/"+registeredUserId+"/approve";
        return RestAssured.given()
                .baseUri(baseURL)
                .basePath(apiPath)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + adminToken)
                .log().all()
                .put()
                .then().extract().response();
    }

    public static Response makeUserAdminResponse(){
        String apiPath = "/APIDEV/admin/users/"+registeredUserId+"/role";
        return RestAssured.given()
                .baseUri(baseURL)
                .basePath(apiPath)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + adminToken)
                .body(makeUserAdminPayload())
                .log().all()
                .put()
                .then().extract().response();
    }

    public static Response verifyUserAdminResponse(){
        String apiPath = "/APIDEV/admin/users/"+registeredUserId;
        return RestAssured.given()
                .baseUri(baseURL)
                .basePath(apiPath)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + userToken)
                .log().all()
                .get()
                .then().extract().response();
    }

    public static Response deleteUserResponse(){
        String apiPath = "/APIDEV/admin/users/"+registeredUserId;
        return RestAssured.given()
                .baseUri(baseURL)
                .basePath(apiPath)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + adminToken)
                .body(deleteUserPayload())
                .log().all()
                .delete()
                .then().extract().response();
    }

    // Helper method to set admin email for token classification
    public static void setAdminEmail(String email) {
        adminEmail = email;
    }

    // Helper method to re-login as admin (useful before admin operations)
    public static Response adminLoginResponse(String email, String password) {
        setAdminEmail(email);
        return loginUserResponse(email, password);
    }
}
