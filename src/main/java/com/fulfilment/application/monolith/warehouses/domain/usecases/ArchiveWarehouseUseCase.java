package com.fulfilment.application.monolith.warehouses.domain.usecases;

import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import com.fulfilment.application.monolith.warehouses.domain.ports.ArchiveWarehouseOperation;
import com.fulfilment.application.monolith.warehouses.domain.ports.WarehouseStore;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ArchiveWarehouseUseCase implements ArchiveWarehouseOperation {

  private final WarehouseStore warehouseStore;

  public ArchiveWarehouseUseCase(WarehouseStore warehouseStore) {
    this.warehouseStore = warehouseStore;
  }

  @Override
  public void archive(Warehouse warehouse) {
    var existing =
            warehouseStore.findByBusinessUnitCode(
                    warehouse.businessUnitCode);

    if (existing == null) {
      throw new RuntimeException("Warehouse not found");
    }

    if (existing.archivedAt != null) {
      throw new RuntimeException("Warehouse already archived");
    }

    existing.archivedAt = java.time.LocalDateTime.now();

    warehouseStore.update(existing);
  }
}
