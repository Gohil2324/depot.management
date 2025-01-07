   package com.depot.management;

public class Parcel {
    private String id;
    private float length;
    private float width;
    private float height;
    private float weight;
    private int daysInDepot;
    private ParcelStatus status;

    public Parcel(String id, float length, float width, float height, float weight, int daysInDepot, ParcelStatus status) {
        this.id = id;
        this.length = length;
        this.width = width;
        this.height = height;
        this.weight = weight;
        this.daysInDepot = daysInDepot;
        this.status = status;
    }

    public float calculateFee() {
        return (length * width * height * 0.01f) + (weight * 0.1f) + (daysInDepot * 0.5f);
    }

    public void updateStatus(ParcelStatus newStatus) {
        this.status = newStatus;
    }

    public String getId() {
        return id;
    }

    public ParcelStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Parcel [ID=" + id + ", Fee=" + calculateFee() + ", Status=" + status + "]";
    }
}

