package com.fulfilment.application.monolith.stores;

public class StoreChangedEvent {

    public Store store;
    public ChangeType changeType;

    public enum ChangeType {
        CREATED,
        UPDATED
    }

    public StoreChangedEvent(Store store, ChangeType changeType) {
        this.store = store;
        this.changeType = changeType;
    }
}
