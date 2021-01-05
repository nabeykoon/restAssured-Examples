package com.filedownloadupload;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class FileDownloadExamples {
    //Get download url using dev tool network tab. It shows url after downloading file
    //https://github.com/mozilla/geckodriver/releases/download/v0.27.0/geckodriver-v0.27.0-linux32.tar.gz
    @Test
    public void verifyDownloadFile(){
        //Read the input file
        File inputFile = new File(System.getProperty ("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator+ "expectedfiles" + File.separator + "geckodriver-v0.27.0-linux32.tar.gz");
        // length of the input file
        int expectedSize = (int) inputFile.length ();
        System.out.println ("The size of the expected file is: " + expectedSize);

        //Read the downloaded file
        byte[] actualValue = given ()
                .when ()
                .get("https://github.com/mozilla/geckodriver/releases/download/v0.27.0/geckodriver-v0.27.0-linux32.tar.gz")
                .then ()
                .extract ()
                .asByteArray ();

        System.out.println ("The size of the actual file is: " + actualValue.length);
        //Using hamcrest matchers
        assertThat(expectedSize, equalTo(actualValue.length));
        //TestNG assertion
        Assert.assertEquals (actualValue.length, expectedSize);

    }
}
