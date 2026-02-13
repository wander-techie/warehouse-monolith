package com.fulfilment.application.monolith.fulfilment;

import static org.junit.jupiter.api.Assertions.*;
import static io.restassured.RestAssured.given;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import java.util.List;

@QuarkusTest
public class FulfilmentRepositoryTest {

    @Inject
    FulfilmentRepository repository;

    @Test
    @Transactional
    public void testPersistAndFindByStore() {

        FulfilmentAssignment assignment = new FulfilmentAssignment();
        assignment.storeId = 1L;
        assignment.productId = 1L;
        assignment.warehouseBusinessUnitCode = "MWH.001";

        repository.persist(assignment);

        List<FulfilmentAssignment> result =
                repository.findByStore(1L);

        assertFalse(result.isEmpty());
        assertEquals(1L, result.get(0).storeId);
    }

    @Test
    public void testFulfilmentAssignmentEndpoint() {

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
    public void testFulfilmentFailsWhenWarehouseNotFound() {

        String body = """
      {
        "storeId": 1,
        "productId": 1,
        "warehouseBusinessUnitCode": "INVALID"
      }
      """;

        given()
                .contentType("application/json")
                .body(body)
                .when()
                .post("/fulfilment")
                .then()
                .statusCode(404);
    }

    @Test
    public void testFulfilmentAssignmentFields() {

        FulfilmentAssignment assignment = new FulfilmentAssignment();
        assignment.storeId = 1L;
        assignment.productId = 2L;
        assignment.warehouseBusinessUnitCode = "MWH.001";

        assertEquals(1L, assignment.storeId);
        assertEquals(2L, assignment.productId);
        assertEquals("MWH.001", assignment.warehouseBusinessUnitCode);
    }


}
