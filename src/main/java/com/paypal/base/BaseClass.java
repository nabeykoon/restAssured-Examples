package com.paypal.base;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class BaseClass {

    public static String accessToken;
    public static final String clientId = "AfH3K0Oi7rVKNxPc5byisnMmD9iejtYS0H0DvccAe57p_izevwWNtSu5P5_ZPHVByJf7HLYNN4DX06F2";
    public static final String clientSecret = "ENl9nbySAF7PsdyROQrOp1Y_SUk9VS_1trSwcOmohdiNB4lY1130OHIcRQb-YAJ9wXFjJfpWeiBVO1rA";



    @BeforeClass
    public void init(){

        RestAssured.baseURI = "https://api-m.sandbox.paypal.com";
        RestAssured.basePath = "/v1";
        accessToken = given()
                .params ("grant_type", "client_credentials")
                .auth ()
                .preemptive ()
                .basic (clientId, clientSecret)
                .when ()
                .post ("/oauth2/token")
                .then ()
                .log ()
                .all ()
                .extract ()
                .path ("access_token");

        System.out.println (accessToken);

    }
}
