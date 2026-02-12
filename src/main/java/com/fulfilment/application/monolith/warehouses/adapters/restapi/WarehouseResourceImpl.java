package com.fulfilment.application.monolith.warehouses.adapters.restapi;

import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import com.fulfilment.application.monolith.warehouses.domain.ports.*;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/warehouses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class WarehouseResourceImpl {

  @Inject private CreateWarehouseOperation createWarehouseOperation;
  @Inject private ReplaceWarehouseOperation replaceWarehouseOperation;
  @Inject private ArchiveWarehouseOperation archiveWarehouseOperation;
  @Inject private WarehouseStore warehouseStore;

  @GET
  public List<Warehouse> listAllWarehousesUnits() {
    return warehouseStore.getAll();
  }

  @POST
  public Warehouse createANewWarehouseUnit(@NotNull Warehouse data) {
    createWarehouseOperation.create(data);
    return data;
  }

  @GET
  @Path("/{id}")
  public Warehouse getAWarehouseUnitByID(@PathParam("id") String id) {
    var warehouse = warehouseStore.findByBusinessUnitCode(id);
    if (warehouse == null) {
      throw new WebApplicationException("Warehouse not found", 404);
    }
    return warehouse;
  }

  @PUT
  @Path("/{id}")
  public Warehouse replaceTheCurrentActiveWarehouse(
          @PathParam("id") String businessUnitCode,
          @NotNull Warehouse data) {

    data.businessUnitCode = businessUnitCode;
    replaceWarehouseOperation.replace(data);
    return data;
  }

  @DELETE
  @Path("/{id}")
  public void archiveAWarehouseUnitByID(@PathParam("id") String id) {

    var warehouse = warehouseStore.findByBusinessUnitCode(id);

    if (warehouse == null) {
      throw new WebApplicationException("Warehouse not found", 404);
    }

    archiveWarehouseOperation.archive(warehouse);
  }
}
