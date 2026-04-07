package Tests;

import RequestBuilder.APIRequestBuilder;
import Utilities.DatabaseConnection;
import com.github.javafaker.Faker;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.equalTo;

public class UserRegistrationTest {

    static String registeredEmail;
    @BeforeClass
    public void setup() throws SQLException {
        DatabaseConnection.dbConnection();
    }

    @Test
    public void adminLoginTest(){
        // use credentials loaded from the DB utility
        String adminEmail = DatabaseConnection.getEmail;
        String adminPassword = DatabaseConnection.getPassword;
        System.out.println("Using admin credentials -> Email: " + adminEmail + ", Password: " + adminPassword);

        APIRequestBuilder.loginUserResponse(adminEmail, adminPassword)
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test(dependsOnMethods = "adminLoginTest")
    public void userRegistration(){

        registeredEmail = Faker.instance().internet().emailAddress();
        APIRequestBuilder.registerUserResponse("Register","Jsonapi",registeredEmail,"@87654321", "5328c91e-fc40-11f0-8e00-5000e6331276")
                .then()
                .log().all()
                .assertThat()
                .statusCode(201)
                .body("success", equalTo(true));
    }

    @Test(dependsOnMethods = "userRegistration")
    public void approveUserRegistration() {

        APIRequestBuilder.approveUserRegistrationResponse()
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test(dependsOnMethods = "approveUserRegistration")
    public void userLoginTest(){

        APIRequestBuilder.loginUserResponse(registeredEmail, "@87654321")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("success", equalTo(true));
    }
}
