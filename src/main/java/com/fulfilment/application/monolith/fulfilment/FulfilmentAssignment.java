package com.fulfilment.application.monolith.fulfilment;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class FulfilmentAssignment {

    @Id
    @GeneratedValue
    public Long id;

    public Long storeId;

    public Long productId;

    public String warehouseBusinessUnitCode;
}
