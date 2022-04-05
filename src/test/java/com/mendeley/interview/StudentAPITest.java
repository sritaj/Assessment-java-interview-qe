package com.mendeley.interview;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pojo.StudentPojo;

import java.util.Random;

import static io.restassured.RestAssured.given;

public class StudentAPITest extends BaseTest {

    int studentId;
    String name;
    int age;
    String country;
    StudentPojo stud;
    Faker fs;
    Random random;

    {
        stud = new StudentPojo();
        fs = new Faker();
        random = new Random();
    }

    @BeforeTest
    public void init() {
        RestAssured.baseURI = "https://api.url.org";
    }

    @Test(testName = "Validate creation of user")
    public void validateAddUser() {

        name = fs.name().fullName();
        age = 10 + random.nextInt(90);
        country = fs.country().name();

        String payload = "{\n" +
                "    \"name\" : \"" + name + "\",\n" +
                "    \"age\" : \"" + age + "\",\n" +
                "    \"country\" : \"" + country + "\"\n" +
                "}";

        Response response = given()
                .header("Content-Type", "application/json")
                .body(stud)
                .when().post("/user")
                .then().extract().response();

        response.prettyPrint();
        response.then().assertThat().statusCode(201);
        String resp = response.asString();
        JsonPath jsonPath = new JsonPath(resp);
        studentId = Integer.parseInt(jsonPath.getString("id"));
    }

    @Test(testName = "Validate creation of user - using Pojo Payload")
    public void validateAddUserUsingPojoPayload() {

        stud.setName(fs.name().fullName());
        stud.setAge(10 + random.nextInt(90));
        stud.setCountry(fs.country().name());

        Response response = given()
                .header("Content-Type", "application/json")
                .body(stud)
                .when().post("/user")
                .then().extract().response();

        response.prettyPrint();
        response.then().assertThat().statusCode(201);
        String resp = response.asString();
        JsonPath jsonPath = new JsonPath(resp);
        studentId = Integer.parseInt(jsonPath.getString("id"));
    }

    @Test(testName = "Validate creation of user when Name is Not Provided")
    public void validateAddUserWhenNameIsNotProvided() {

        age = 10 + random.nextInt(90);
        country = fs.country().name();

        String payload = "{\n" +
                "    \"age\" : \"" + age + "\",\n" +
                "    \"country\" : \"" + country + "\"\n" +
                "}";

        Response response = given()
                .header("Content-Type", "application/json")
                .body(payload)
                .when().post("/user")
                .then().extract().response();

        response.prettyPrint();
        response.then().assertThat().statusCode(500);
        String resp = response.asString();
        JsonPath jsonPath = new JsonPath(resp);
        String errorMessage = (jsonPath.getString("error"));
        Assert.assertEquals(errorMessage, "required");
    }

    @Test(testName = "Validate creation of user when Age is Not Provided")
    public void validateAddUserWhenAgeIsNotProvided() {

        name = fs.name().fullName();
        age = 10 + random.nextInt(90);
        country = fs.country().name();

        String payload = "{\n" +
                "    \"age\" : \"" + name + "\",\n" +
                "    \"country\" : \"" + country + "\"\n" +
                "}";

        Response response = given()
                .header("Content-Type", "application/json")
                .body(payload)
                .when().post("/user")
                .then().extract().response();

        response.prettyPrint();
        response.then().assertThat().statusCode(500);
        String resp = response.asString();
        JsonPath jsonPath = new JsonPath(resp);
        String errorMessage = (jsonPath.getString("error"));
        Assert.assertEquals(errorMessage, "required");
    }

    @Test(testName = "Validate fetching of Student based on the id", dependsOnMethods = {"validateAddUser"})
    public void getSpecifiedStudent() {

        Response response = given()
                .pathParam("id", studentId)
                .when().get("/user/{id}")
                .then().extract().response();

        response.prettyPrint();
        response.then().assertThat().statusCode(200);
    }

    @Test(testName = "Validate updation of Studnet", dependsOnMethods = {"validateAddUser"})
    public void updateSpecifiedStudent() {

        name = fs.name().fullName();
        age = 10 + random.nextInt(90);
        country = fs.country().name();

        String updateStudentPayload = "{\n" +
                "    \"name\" : \"" + name + "\",\n" +
                "    \"age\" : \"" + age + "\",\n" +
                "    \"country\" : \"" + country + "\"\n" +
                "}";

        Response response = given()
                .header("Content-Type", "application/json")
                .pathParam("id", 104)
                .body(updateStudentPayload)
                .when().put("student/{id}");

        response.prettyPrint();
        response.then().assertThat().statusCode(200);
    }
}
