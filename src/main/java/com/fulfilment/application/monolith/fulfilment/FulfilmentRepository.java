package com.fulfilment.application.monolith.fulfilment;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class FulfilmentRepository implements PanacheRepository<FulfilmentAssignment> {

    public List<FulfilmentAssignment> findByStore(Long storeId) {
        return list("storeId", storeId);
    }

    public List<FulfilmentAssignment> findByStoreAndProduct(Long storeId, Long productId) {
        return list("storeId = ?1 and productId = ?2", storeId, productId);
    }

    public List<FulfilmentAssignment> findByWarehouse(String warehouseCode) {
        return list("warehouseBusinessUnitCode", warehouseCode);
    }
}
