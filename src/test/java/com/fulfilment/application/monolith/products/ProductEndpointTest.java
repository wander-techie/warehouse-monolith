package com.fulfilment.application.monolith.products;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.IsNot.not;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class ProductEndpointTest {

  @Test
  public void testCreateProduct() {
    final String path = "product";

    String newProduct = """
      {
        "name": "LACK",
        "price": 49.99,
        "stock": 10
      }
      """;

    given()
            .contentType("application/json")
            .body(newProduct)
            .when()
            .post(path)
            .then()
            .statusCode(201);

    // Verify it exists
    given()
            .when()
            .get(path)
            .then()
            .statusCode(200)
            .body(containsString("LACK"));
  }

  @Test
  public void testGetSingleProduct() {
    final String path = "product";

    given()
            .when()
            .get(path + "/2")
            .then()
            .statusCode(200)
            .body(containsString("KALLAX"));
  }

  @Test
  public void testGetNonExistingProduct() {
    final String path = "product";

    given()
            .when()
            .get(path + "/999")
            .then()
            .statusCode(404);
  }

}
