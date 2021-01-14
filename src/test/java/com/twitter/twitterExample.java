package com.twitter;

        import io.restassured.RestAssured;
        import org.testng.annotations.BeforeClass;
        import org.testng.annotations.Test;

        import static io.restassured.RestAssured.given;

public class twitterExample {

    String consumerKey = "TdzxAT2jkhvNukD281K63dHVh";
    String consumerSecret = "Bc5TKdptklBozFhhtwHzzwhNJYqJENuSe6aFL8XWHhI8B7r2TG";
    String accessToken = "3064231026-iUhfuYND5OSU1gbGxKjFhcD3MX3I4nLqc0QCsoo";
    String tokenSecret = "6QjdZQh4Y1pJbsPbE0jd97hVnIkaRV27a01bzuDHdAHvE";

    @BeforeClass
    public void init() {
        RestAssured.baseURI = "https://api.twitter.com";
        RestAssured.basePath = "/1.1/statuses";
    }

    @Test
    public void createTweet() {
        given ()
                .auth ()
                .oauth (consumerKey,consumerSecret, accessToken, tokenSecret)
                .queryParam ("status", "Tweet update from Rest-assured-#2")
                .when ()
                .post ("/update.json")
                .then ()
                .log ()
                .all ();
    }

    @Test
    public void getTweets() {
        given ()
                .auth ()
                .oauth (consumerKey,consumerSecret, accessToken, tokenSecret)
                .queryParam ("screen_name", "nabeykoon1")
                .when ()
                .get ("/user_timeline.json")
                .then ()
                .log ()
                .all ();
    }
}