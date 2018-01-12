package apiv3tests;

import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class APIv3Tests {
    private SoftAssert softAssert = new SoftAssert();
    private String apiV3Url = "http://api.pubnative.net/api/v3/native";

    @BeforeTest
    public void beforeTest() {
        System.out.println("Hello world: before Test");
    }

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("Hello world: before Method");
    }

    @Test
    public void apiv3SmokeAndroidTest() {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("apptoken", "bed5dfea7feb694967a8755bfa7f67fdf1ceb9c291ddd7b8825983a103c8b266");
        paramsMap.put("os", "android");
        paramsMap.put("devicemodel", "GT-I5930");
        paramsMap.put("osver", "9.1");
        paramsMap.put("dnt", "1");
        paramsMap.put("af", "title,cta,rating,icon,banner");
        paramsMap.put("mf", "points,revenuemodel,campaignid,creativeid,bundleid");
        paramsMap.put("zoneid", "1");
        Response response = given()
                .params(paramsMap)
                .log().all()
                .when()
                .get(apiV3Url);
        response.then().log().all();
        String json = response.getBody().asString();
        softAssert.assertEquals(response.getStatusCode(), 200, "Wrong status code");
        softAssert.assertTrue(getLinkFrom(json, "link").contains("got.pubnative.net"), "Link doen't have go tracker stetment");
        softAssert.assertTrue(getLinkFrom(json, "link").contains("native"), "Link should be native");
        softAssert.assertTrue(getLinkFrom(json, "beacons..url").contains("got.pubnative.net/impression"), "Impression link is wrong");
        softAssert.assertAll();
    }

    @Test
    public void apiv3SmokeIosTest() {

        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("apptoken", "b886f322495c4c1388c6e71dec59aaf8");
        paramsMap.put("os", "ios");
        paramsMap.put("devicemodel", "iphone");
        paramsMap.put("osver", "9.1");
        paramsMap.put("dnt", "1");
        paramsMap.put("af", "title,cta,rating,icon,banner");
        paramsMap.put("mf", "points,revenuemodel,campaignid,creativeid,bundleid");
        paramsMap.put("zoneid", "1");
        Response response = given()
                .params(paramsMap)
                .log().all()
                .when()
                .get(apiV3Url);
        response.then().log().all();
        String json = response.getBody().asString();
        softAssert.assertEquals(response.getStatusCode(), 200, "Wrong status code");
        softAssert.assertTrue(getLinkFrom(json, "link").contains("got.pubnative.net"), "Link doesn't have go tracker statement");
        softAssert.assertTrue(getLinkFrom(json, "link").contains("native"), "Link should be native");
        softAssert.assertTrue(getLinkFrom(json, "beacons..url").contains("got.pubnative.net/impression"), "Impression link is wrong");
        softAssert.assertAll();
    }

    @AfterTest
    public void afterTest() {
        System.out.println("Hello world: after Test");
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("Hello world: after Method");
    }

    private String getLinkFrom(String json, String key) {
        List<String> link = JsonPath.read(json, "$.ads.." + key);
        return link.get(0);
    }

}
