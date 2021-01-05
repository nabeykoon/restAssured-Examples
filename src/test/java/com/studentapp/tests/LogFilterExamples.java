package com.studentapp.tests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.commons.io.output.WriterOutputStream;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.PrintStream;
import java.io.StringWriter;
import java.nio.charset.Charset;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class LogFilterExamples {

    public static StringWriter requestWriter;
    public static PrintStream requestCapture;

    public static StringWriter responseWriter;
    public static PrintStream responseCapture;

    public static StringWriter errorWriter;
    public static PrintStream errorCapture;

    @BeforeClass
    public static void init() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.basePath = "/student";
    }

    @BeforeMethod
    public void beforeEach() {
        requestWriter = new StringWriter ();
        requestCapture = new PrintStream (new WriterOutputStream (requestWriter, Charset.defaultCharset ()), true);

        responseWriter = new StringWriter ();
        responseCapture = new PrintStream (new WriterOutputStream (responseWriter, Charset.defaultCharset ()), true);

        errorWriter = new StringWriter ();
        errorCapture = new PrintStream (new WriterOutputStream (errorWriter, Charset.defaultCharset ()), true);
    }

    @Test
    public void getSingleCSStudent() {

        String response = given ()
                .when ()
                .get ("/list")
                .asString ();

        //System.out.println (response);

        given ()
                .filter (new RequestLoggingFilter (requestCapture))
                .filter (new ResponseLoggingFilter (responseCapture))
                .when ()
                .get ("/list");

        System.out.println (requestWriter.toString ());
        System.out.println (responseWriter.toString ());

        given ()
                .filter (new ErrorLoggingFilter (errorCapture))
                .when ()
                .get ("/list123");

        System.err.println (errorWriter.toString ().toUpperCase ());
    }

}
