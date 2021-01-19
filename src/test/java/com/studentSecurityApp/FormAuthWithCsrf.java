package com.studentSecurityApp;

import io.restassured.RestAssured;
import io.restassured.authentication.FormAuthConfig;
import io.restassured.filter.session.SessionFilter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class FormAuthWithCsrf {

    // This is based on custom app "sstudentSpringSecurity_CSRF" - local location - Sync\Dev-tools\studentSpringSecurity_CSRF

    public static SessionFilter filter;

    @BeforeClass
    public static void init() {
        RestAssured.baseURI = "http://localhost:8080/";
        filter = new SessionFilter ();
        given ()
                .auth ()
                .form ("user", "user", new FormAuthConfig ("/login", "uname", "pwd").withAutoDetectionOfCsrf ())
                //or you can use - .withCsrfFieldName ("_csrf")
                .filter (filter)
                .get ();

        System.err.println (filter.getSessionId ());
    }

    @Test
    public void getAllStudent() {
        given ()
                .sessionId (filter.getSessionId ())
                .get ("/student/list")
                .then ()
                .log ()
                .all ();
    }
}
