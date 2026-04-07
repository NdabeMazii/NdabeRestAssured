package Utilities;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Requests {

    public static RequestSpecification getRequestSpec() {
        return RestAssured.given().contentType("application/json").urlEncodingEnabled(false);
    }

    public static Response get(String url){
        Response response = getRequestSpec()
                .when()
                .get(url)
                .then()
                .extract()
                .response();
        response.getBody().prettyPrint();
        return response;
    }

    public static Response post(String url, String body){
        Response response = getRequestSpec()
                .body(body)
                .when().log().all()
                .post(url)
                .then()
                .extract()
                .response();
        response.getBody().prettyPrint();
        return response;
    }

    public static Response put(String url, Object body){
        Response response = getRequestSpec()
                .body(body)
                .when()
                .put(url)
                .then()
                .extract()
                .response();
        response.getBody().prettyPrint();
        return response;
    }

    public static Response delete(String url){
        Response response = getRequestSpec()
                .when()
                .delete(url)
                .then()
                .extract()
                .response();
        response.getBody().prettyPrint();
        return response;
    }
}
