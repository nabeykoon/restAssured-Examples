package com.bestbuytrainingapp;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;

public class JsonPathJsonSlurperExamples {

    static ValidatableResponse validatableResponse;

    private static void print(String val) {
        System.out.println ("----------------------------------------------------------");
        System.out.println (val);
        System.out.println ("----------------------------------------------------------");
    }

    @BeforeClass
    public static void init() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 3030;

        validatableResponse = given ().when ().get ("/stores").then ();
    }

    @BeforeMethod
    void startTestMethod() {
        System.out.println ("-----------------------Starting the test method-------------");
        System.out.println ("     ");
    }

    @AfterMethod
    void EndTestMethod() {
        System.out.println ("-----------------------Ending the test method-------------");
        System.out.println ("     ");
    }

    @Test
    public void getTotal() {
        int total = validatableResponse.extract ().path ("total");
        print (String.valueOf (total));
        //Assert.assertEquals (total, 1561, "Total value not matched with Expected total value");
        //using Hamcrest Matchers
        validatableResponse.body ("total", equalTo (1561));

    }

    @Test
    public void getFirstStoreName() {
        String storeName = validatableResponse.extract ().path ("data[0].name");
        print (storeName);
        validatableResponse.body ("data[0].name", equalToIgnoringCase ("MINNetonka"));
    }

    @Test
    public void getFirstServiceNameFromFirstDataElement() {
        String serviceName = validatableResponse.extract ().path ("data[0].services[0].name");
        print (serviceName);
    }

    @Test
    public void getStoreDetailsWithZip() {
        HashMap<String, ?> storeDetails = validatableResponse.extract ().path ("data.find{it.zip=='55901'}");
        print (storeDetails.toString ());
        //find is a groovy method. {it.zip=='55901'} part called closure (block of code). Rest assured jsonpath uses Groovy Gpath to extract object from the document"
    }

    @Test
    public void getStoreAddressWithZip() {
        String storeAddress = validatableResponse.extract ().path ("data.find{it.zip=='55901'}.address");
        print (storeAddress);
        //find using first part of the string -> {it.name==~/first.*/}
        //find using last part of the string -> {it.name==~/.*last/}
    }

    @Test
    public void getInfoOfStoreWithMaxId() {
        HashMap<String, ?> maxId = validatableResponse.extract ().path ("data.max{it.id}");
        print (maxId.toString ());

        HashMap<String, ?> minId = validatableResponse.extract ().path ("data.min{it.id}");
        print (minId.toString ());
    }

    @Test
    public void getAllStoresWithIdsLessThan10() {
        List<String> storeDetails = validatableResponse.extract ().path ("data.findAll{it.id<10}");
        print (storeDetails.toString ());
    }

    @Test
    public void getAllServiceNames() {
        List<List<String>> storeNames = validatableResponse.extract ().path ("data.services.findAll{it.name}.name");
        print (storeNames.toString ());
    }

    @Test
    public void verifyGivenStoreNameIsWithinAllStoreNamesWithIdsLessThan10() {
        List<String> storeDetails = validatableResponse.extract ().path ("data.findAll{it.id<10}.name");
        print (storeDetails.toString ());
        validatableResponse.body ("data.findAll{it.id<10}.name", hasItem ("Inver Grove Heights"));
        validatableResponse.body ("data.findAll{it.id<10}.name", hasItems ("Roseville", "Burnsville"));
    }

    @Test
    public void verifyGivenKeyIsWithinFirstServiceNameFromFirstDataElement() {
        HashMap<String, Object> services = validatableResponse.extract ().path ("data[0].services[0]");
        print (services.toString ());
        validatableResponse.body ("data[0].services[0]", hasKey ("createdAt"));

    }

    @Test
    public void verifyGivenKeyValuePairIsWithinAllStoresWithIdsLessThan10() {
        List<HashMap<String, Object>> storeDetails = validatableResponse.extract ().path ("data.findAll{it.id<10}");
        print (storeDetails.toString ());
        //multiple assertions
        validatableResponse
                .body ("data.findAll{it.id<10}", hasItem (hasEntry ("city", "Roseville")))
                .body ("data[0].services[0]", hasKey ("createdAt"))
                .body ("data.findAll{it.id<10}", hasItem (hasEntry ("name", "Burnsville")))
                .statusCode (200);
    }

    @Test
    public void verifyNumberOfDataWithinDefinedLimit() {
        int total = validatableResponse.extract ().path ("data.size()");
        print (String.valueOf (total));
        //Assert.assertEquals (total, 1561, "Total value not matched with Expected total value");
        //using Hamcrest Matchers
        validatableResponse
                .body ("data.size()", equalTo (10))
                .body ("data.size()", greaterThan (9))
                .body ("data.size()", lessThan (11))
                .body ("data.size()", greaterThanOrEqualTo (10))
                .body ("data.size()", lessThanOrEqualTo (10));

    }


}
