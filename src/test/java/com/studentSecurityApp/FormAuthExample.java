package com.studentSecurityApp;

import io.restassured.RestAssured;
import io.restassured.authentication.FormAuthConfig;
import io.restassured.filter.session.SessionFilter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class FormAuthExample {

    // This is based on custom app "studentSpringSecurity" - local location - Sync\Dev-tools\studentSpringSecurity - Rest assured form auth sample

    public static SessionFilter filter;

    @BeforeClass
    public static void init() {
        RestAssured.baseURI = "http://localhost:8080/";
        filter = new SessionFilter ();
        given ()
                .auth ()
                .form ("user", "user", new FormAuthConfig ("/login", "uname", "pwd"))
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
