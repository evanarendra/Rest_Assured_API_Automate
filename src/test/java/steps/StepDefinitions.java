package steps;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.configuration.ConfigurationException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class StepDefinitions {

    Properties prop = new Properties();

    FileInputStream file;

    {
        try {
            file = new FileInputStream("./src/test/resources/config.properties");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    RequestSpecification request;
    Response response;

    @Given("User login API is provided")
    public void userLoginAPIIsProvided() throws Exception {
        prop.load(file);
        RestAssured.baseURI = prop.getProperty("baseUrl1");
        request = RestAssured.given();
    }

    @And("Login App API is provided")
    public void loginAppAPIIsProvided() throws Exception {
        prop.load(file);
        RestAssured.baseURI = prop.getProperty("baseUrl");
        request = RestAssured.given();
    }

    //Release Stock
    @When("User call Release Stock")
    public void userCallReleaseStock() {
        request.header("Content-Type", "application/json");
        request.header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOjY3NSwiaWF0I" +
                "joxNzAxODUxMTMyLCJleHAiOjE3MDE5Mzc1MzJ9.kepwhx8Gw8kWXkTvg7ClDvNSA9xzcMeFisVj4q9ef-M");
        response = request.get("/api/v3/gateway/v2/stock/inventory/release/6598");
    }

    @And("User call update Release Stock")
    public void userCallUpdateReleaseStock() {
        JSONObject postedProduct = new JSONObject();
        postedProduct.put("group_type", "grosir");
        postedProduct.put("quantity", "1");
        postedProduct.put("warehouse_id", 4);
        postedProduct.put("product_attribute_id", 9632);
        postedProduct.put("page_id", "9a210149-b37f-46da-877e-7723ed572c5d");
        postedProduct.put("token", "mKPc6F5uqeYeQ57QAFEp");
    }

    //Request Stock App
    @And("User call Request Stock API")
    public void userCallRequestStockAPI() {
        request.header("Content-Type", "application/json");
        request.header("Authorization", "Bearer " +
                "");
        response = request.get("/product/request-stock/list?city=kabupaten-wajo&is_opened=false");
    }

    @Then("Request Stock list will be shown")
    public void requestStockListWillBeShown() {
        System.out.println("Response Body is =>  " + response.asString());
        String product_attribute_id = response.jsonPath().getString("product_attribute_id");
        Assert.assertEquals(product_attribute_id, "9632");
    }

}
