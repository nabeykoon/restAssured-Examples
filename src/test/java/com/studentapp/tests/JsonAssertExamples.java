package com.studentapp.tests;

import io.restassured.RestAssured;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonAssertExamples extends BaseTest {

    @Test
    public void verifyStudentList() throws IOException, JSONException {
        String expectedValue = new String (Files.readAllBytes (Paths.get (System.getProperty ("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "studentListJson.txt" )));
        System.out.println (expectedValue);
        String actualValue = RestAssured.given ().when ().get ("/list").asString ();
        System.out.println (actualValue);
        //Assert.assertEquals (actualValue, expectedValue);
        //If we use TestNG Assert class it will through assertion error but error message thrown considering entire Json string. Cannot troubleshoot easily
        JSONAssert.assertEquals (expectedValue, actualValue, JSONCompareMode.LENIENT);
    }

    @Test
    public void verifyStudentListStrictMode() throws IOException, JSONException {
        String expectedValue = new String (Files.readAllBytes (Paths.get (System.getProperty ("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "studentListDifferOrderJson.txt" )));
        System.out.println (expectedValue);
        String actualValue = RestAssured.given ().when ().get ("/list").asString ();
        System.out.println (actualValue);
        JSONAssert.assertEquals (expectedValue, actualValue, JSONCompareMode.STRICT);
        //JSONCompareMode.STRICT - STRICT mode will validate even the order of list inside JSON
    }
}
