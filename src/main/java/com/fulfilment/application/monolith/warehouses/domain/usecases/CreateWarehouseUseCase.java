package com.fulfilment.application.monolith.warehouses.domain.usecases;

import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import com.fulfilment.application.monolith.warehouses.domain.ports.CreateWarehouseOperation;
import com.fulfilment.application.monolith.warehouses.domain.ports.WarehouseStore;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CreateWarehouseUseCase implements CreateWarehouseOperation {

  private final WarehouseStore warehouseStore;

  public CreateWarehouseUseCase(WarehouseStore warehouseStore) {
    this.warehouseStore = warehouseStore;
  }

  @Override
  public void create(Warehouse warehouse) {
    if (warehouse.businessUnitCode == null) {
      throw new RuntimeException("BusinessUnitCode is required");
    }

    var existing =
            warehouseStore.findByBusinessUnitCode(warehouse.businessUnitCode);

    if (existing != null && existing.archivedAt == null) {
      throw new RuntimeException("Warehouse already exists");
    }

    warehouse.createdAt = java.time.LocalDateTime.now();
    warehouse.archivedAt = null;

    warehouseStore.create(warehouse);
  }
}
