package Basic;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class UserRegistration {
    static String baseURL = "https://ndosiautomation.co.za";
    static String registeredEmail;
    static String authToken;
    static String userId;

    @Test
    public void adminLoginTest() {
        // Implement admin login test and set authToken
        String apiPath = "/APIDEV/login";
        String payload = "{\n" +
                "  \"email\": \"spare@admin.com\",\n" +
                "  \"password\": \"@12345678\"\n" +
                "}";

        Response response = RestAssured.given()
                .baseUri(baseURL)
                .basePath(apiPath)
                .header("Content-Type", "application/json")
                .body(payload)
                .log().all()
                .post().prettyPeek();

        int actualStatusCode = response.getStatusCode();
        assert actualStatusCode == 200 : "Status code should be 200";
        authToken = response.jsonPath().getString("data.token");
    }

    @Test(priority = 2)
    public void registerUser() {
        // Implement user registration test using authToken
        String apiPath = "/APIDEV/register";
        registeredEmail = Faker.instance().internet().emailAddress();
        String payload = String.format("{\n" +
                "  \"firstName\": \"Ntando\",\n" +
                "  \"lastName\": \"Mazii\",\n" +
                "  \"email\": \"%s\",\n" +
                "  \"password\": \"Ntando@053103\",\n" +
                "  \"confirmPassword\": \"Ntando@053103\",\n" +
                "  \"phone\": \"\",\n" +
                "  \"groupId\": \"1deae17a-c67a-4bb0-bdeb-df0fc9e2e526\"\n" +
                "}", registeredEmail);

        Response response = RestAssured.given()
                .baseUri(baseURL)
                .basePath(apiPath)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + authToken)
                .body(payload)
                .log().all()
                .post().prettyPeek();

        int actualStatusCode = response.getStatusCode();
        assert actualStatusCode == 201 : "Status code should be 201";
        userId = response.jsonPath().getString("data.id");
    }
}