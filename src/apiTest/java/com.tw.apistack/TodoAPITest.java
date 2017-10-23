package com.tw.apistack;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.tw.apistack.config.Constants;
import com.tw.apistack.core.todo.model.Todo;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static com.jayway.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.is;

/**
 * Created by jxzhong on 2017/7/3.
 */
@RunWith(SpringRunner.class)
@ActiveProfiles(Constants.SPRING_PROFILE_TEST)
@SpringBootTest(classes = ApiStackApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TodoAPITest {

    private static final String API_PATH = "/api/todos";


    @Value("${local.server.port}")
    private int port;

    @Before //todo init some data with post and delete them in the end of all tests
    public void setup() {
        RestAssured.port = this.port;
        RestAssured.baseURI = "http://localhost";

    }


    @Test
    public void should_get_status_200_when_call_todos() throws Exception {
        RestAssured.
                given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .when()
                .get(API_PATH)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.JSON)
                .body("size()", is(2));
    }


    @Test
    public void should_get_status_201_when_call_post_todo() throws Exception {
        Todo todo = new Todo("first todo item", false);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(todo);
        System.out.println(json);

        RestAssured.
                given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(todo)
                .when()
                .post(API_PATH)
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .contentType(ContentType.JSON)
                .body("title", is(todo.getTitle()));
    }

    @Test
    public void should_get_status_200_when_call_todos_with_path_1() throws Exception {

        RestAssured
                .given()
                .accept(ContentType.JSON)
                .when()
                .get(API_PATH + "/1")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.JSON)
                .body(matchesJsonSchemaInClasspath("todo/todo-schema.json"));
    }

    @Test
    public void should_get_status_404_when_call_todos_with_path_4() throws Exception {

        RestAssured
                .given()
                .accept(ContentType.JSON)
                .when()
                .get(API_PATH + "/4")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void should_get_status_200_when_call_todos_patch_with_path_2() throws Exception {
        Todo todo = new Todo();
        todo.setCompleted(true);
        RestAssured
                .given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(todo)
                .when()
                .patch(API_PATH + "/2")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.JSON)
                .body("completed", is(true));
    }

    @Test
    public void should_get_status_404_when_call_todos_patch_with_path_4() throws Exception {
        Todo todo = new Todo();
        todo.setCompleted(true);
        RestAssured
                .given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(todo)
                .when()
                .patch(API_PATH + "/4")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void should_get_status_400_when_call_todos_patch_with_path_1_and_empty_body() throws Exception {
        RestAssured
                .given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body("")
                .when()
                .patch(API_PATH + "/1")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void should_get_status_200_when_call_todos_delete_with_path_1() throws Exception {
        RestAssured
                .given()
                .accept(ContentType.JSON)
                .when()
                .delete(API_PATH + "/1")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void should_get_status_404_when_call_todos_delete_with_path_3() throws Exception {
        RestAssured
                .given()
                .accept(ContentType.JSON)
                .when()
                .delete(API_PATH + "/100")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }
}
