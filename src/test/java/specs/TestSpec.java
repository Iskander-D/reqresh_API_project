package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomApiListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.filter.log.LogDetail.STATUS;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.notNullValue;

public class TestSpec {
    public static RequestSpecification requestSpec = with()
            .filter(withCustomTemplates())
            .log().uri()
            .log().headers()
            .log().body()
            .contentType(JSON);
    public static ResponseSpecification responseUser = new ResponseSpecBuilder()
            .expectStatusCode(201)
            .log(LogDetail.BODY)
            .build();

    public static ResponseSpecification response = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(LogDetail.BODY)
            .build();

    public static ResponseSpecification responseNotFound = new ResponseSpecBuilder()
            .expectStatusCode(404)
            .log(ALL)
            .build();
    public static ResponseSpecification responseDelete = new ResponseSpecBuilder()
            .expectStatusCode(201)
            .log(STATUS)
            .build();

    public static ResponseSpecification loginResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(LogDetail.BODY)
            .log(LogDetail.STATUS)
            .expectBody("token",notNullValue())
            .build();
}
