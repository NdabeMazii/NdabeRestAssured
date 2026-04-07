package Tests;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import Utilities.PayloadStore;
import Utilities.Requests;

public class ImprovedAPITests {

    Response response;
    @Test
    public void test(){
        String baseURL = "https://www.ndosiautomation.co.za";
        String apiPath = "/APIDEV/login";
        String payload = PayloadStore.loginUserPayload("spare@admin.com","@12345678");

        response = Requests.post(baseURL + apiPath, payload);

        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200");
    }
}
