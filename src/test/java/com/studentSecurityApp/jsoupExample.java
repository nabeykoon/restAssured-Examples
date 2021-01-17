package com.studentSecurityApp;

import io.restassured.RestAssured;
import io.restassured.authentication.FormAuthConfig;
import io.restassured.filter.session.SessionFilter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class jsoupExample {

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
    public void extractTitle() {
        String response = given ()
                .when ()
                .get ("http://localhost:8080/")
                .asString ();

        Document document = Jsoup.parse (response);
        System.out.println ("The title of the HTML page is: " + document.title ().toUpperCase ());
    }

    @Test
    public void extractElementByTag() {
        String response = given ()
                .when ()
                .get ("http://localhost:8080/")
                .asString ();

        Document document = Jsoup.parse (response);
        Elements element = document.getElementsByTag ("form");
        System.out.println ("Number of form elements: " + element.size ());
        for(Element e: element){
            System.out.println (e);
        }
    }

    @Test
    public void extractElementById() {
        String response = given ()
                .when ()
                .get ("http://localhost:8080/")
                .asString ();

        Document document = Jsoup.parse (response);
        Element element = document.getElementById ("command");
        System.out.println ("Form element: " + element);
        System.out.println ("The element contents are: " + element.text ());
    }

    @Test
    public void extractingLinks() {
        String response = given ()
                .when ()
                .get ("http://localhost:8080/")
                .asString ();

        Document document = Jsoup.parse (response);
        Elements links = document.select ("a[href]");
        for(Element e: links){
            System.out.println (e.text ());
        }
    }

    @Test
    public void extractingEmail() {
        String response = given ()
                .sessionId (filter.getSessionId ())
                .when ()
                .get ("/student/list")
                .asString ();

        Document document = Jsoup.parse (response);
        Elements emailIds = document.select ("table tbody tr td:nth-child(4)");
        System.out.println (emailIds.size ());
        ArrayList<String> actualEmailIds = new ArrayList<String> ();
        for(Element email: emailIds){
            actualEmailIds.add (email.text ());
        }
        assertThat(actualEmailIds, hasItem ("nascetur@conguea.com"));
        Assert.assertTrue (actualEmailIds.contains ("nascetur@conguea.com"));
    }
}
