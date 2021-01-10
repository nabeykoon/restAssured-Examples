package com.studentapp.tests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.ProxySpecification;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ProxyExample extends BaseTest {

    static RequestSpecBuilder builder;
    static RequestSpecification requestSpec;

    @BeforeMethod
    public void init(){
        super.init ();
        builder = new RequestSpecBuilder ();
        builder.setProxy (5555);
        requestSpec = builder.build ();
    }

    @Test
    public void getStudentList(){
        given()
                .proxy ("localhost",5555)
                .when ()
                .get ("/list")
                .then ()
                .log ()
                .all ();
    }

    @Test
    public void getStudentListUsingProxySpec(){

        ProxySpecification proxySpec = new ProxySpecification ("localhost", 5555, "http");
        given()
                .proxy (proxySpec)
                .when ()
                .get ("/list")
                .then ()
                .log ()
                .all ();
    }

    @Test
    public void getStudentListUsingRequestSpec(){
        given()
                .spec (requestSpec)
                .when ()
                .get ("/list")
                .then ()
                .log ()
                .all ();
    }
}
