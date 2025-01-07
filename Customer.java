package com.depot.management;

public class Customer {
      private int queueNumber;
    private String name;
    private String parcelId;

    public Customer(int queueNumber, String name, String parcelId) {
        this.queueNumber = queueNumber;
        this.name = name;
        this.parcelId = parcelId;
    }

    public int getQueueNumber() {
        return queueNumber;
    }

    public String getName() {
        return name;
    }

    public String getParcelId() {
        return parcelId;
    }

    @Override
    public String toString() {
        return "Customer [QueueNumber=" + queueNumber + ", Name=" + name + ", ParcelID=" + parcelId + "]";
    }
}
