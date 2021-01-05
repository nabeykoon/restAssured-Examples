package com.filedownloadupload;

import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class FileUploadExample {

    @Test
    public void fileUploadTest(){
        File inputFile = new File (System.getProperty ("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator+ "expectedfiles" + File.separator + "giphy.gif");
        System.out.println (inputFile.length ());
        String apiKey = "c1be0e1da6f2e8bf698dddbfcdfc6360ecdc3973";
        String endpoint = "https://sandbox.zamzar.com/v1/jobs";
        given()
                .auth ()
                .basic (apiKey, "")
                .multiPart ("source_file", inputFile)
                .multiPart ("target_format", "png")
                .when ()
                .post (endpoint)
                .then ()
                .log ()
                .all ();

    }



}
