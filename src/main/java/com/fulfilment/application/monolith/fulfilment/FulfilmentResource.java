package com.fulfilment.application.monolith.fulfilment;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("fulfilment")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FulfilmentResource {

    @Inject AssignWarehouseToProductUseCase useCase;

    @POST
    public void assign(FulfilmentRequest request) {
        useCase.assign(
                request.storeId,
                request.productId,
                request.warehouseBusinessUnitCode);
    }
}
