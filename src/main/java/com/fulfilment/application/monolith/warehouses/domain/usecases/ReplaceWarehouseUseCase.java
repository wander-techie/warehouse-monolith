package com.fulfilment.application.monolith.warehouses.domain.usecases;

import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import com.fulfilment.application.monolith.warehouses.domain.ports.ReplaceWarehouseOperation;
import com.fulfilment.application.monolith.warehouses.domain.ports.WarehouseStore;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ReplaceWarehouseUseCase implements ReplaceWarehouseOperation {

  private final WarehouseStore warehouseStore;

  public ReplaceWarehouseUseCase(WarehouseStore warehouseStore) {
    this.warehouseStore = warehouseStore;
  }

  @Override
  public void replace(Warehouse newWarehouse) {
    var existing =
            warehouseStore.findByBusinessUnitCode(
                    newWarehouse.businessUnitCode);

    if (existing == null) {
      throw new RuntimeException("Warehouse not found");
    }

    if (existing.archivedAt != null) {
      throw new RuntimeException("Cannot replace archived warehouse");
    }

    newWarehouse.createdAt = existing.createdAt;
    newWarehouse.archivedAt = null;

    warehouseStore.update(newWarehouse);
  }
}
