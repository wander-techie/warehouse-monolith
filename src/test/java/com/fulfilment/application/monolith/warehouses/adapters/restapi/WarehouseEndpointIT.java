package com.fulfilment.application.monolith.warehouses.adapters.restapi;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

import io.quarkus.test.junit.QuarkusIntegrationTest;
import org.junit.jupiter.api.Test;

@QuarkusIntegrationTest
public class WarehouseEndpointIT {

  @Test
  public void testAssignWarehouseSuccessfully() {

    String body = """
      {
        "storeId": 1,
        "productId": 1,
        "warehouseBusinessUnitCode": "MWH.001"
      }
      """;

    given()
            .contentType("application/json")
            .body(body)
            .when()
            .post("/fulfilment")
            .then()
            .statusCode(204);
  }

  @Test
  public void testMax3WarehousesPerStore() {

    assign(1L, 1L, "MWH.001");
    assign(1L, 2L, "MWH.012");
    assign(1L, 3L, "MWH.023");

    String body = """
      {
        "storeId": 1,
        "productId": 4,
        "warehouseBusinessUnitCode": "MWH.001"
      }
      """;

    given()
            .contentType("application/json")
            .body(body)
            .when()
            .post("/fulfilment")
            .then()
            .statusCode(422);
  }

  @Test
  public void testMax5ProductsPerWarehouse() {

    assign(1L, 1L, "MWH.001");
    assign(1L, 2L, "MWH.001");
    assign(1L, 3L, "MWH.001");
    assign(1L, 4L, "MWH.001");
    assign(1L, 5L, "MWH.001");

    String body = """
      {
        "storeId": 1,
        "productId": 6,
        "warehouseBusinessUnitCode": "MWH.001"
      }
      """;

    given()
            .contentType("application/json")
            .body(body)
            .when()
            .post("/fulfilment")
            .then()
            .statusCode(422);
  }

  private void assign(Long storeId, Long productId, String warehouseCode) {

    String body = """
      {
        "storeId": %d,
        "productId": %d,
        "warehouseBusinessUnitCode": "%s"
      }
      """.formatted(storeId, productId, warehouseCode);

    given()
            .contentType("application/json")
            .body(body)
            .when()
            .post("/fulfilment")
            .then()
            .statusCode(204);
  }

}
