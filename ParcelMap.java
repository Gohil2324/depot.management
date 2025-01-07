   package com.depot.management;

import java.util.HashMap;
import java.util.Map;

public class ParcelMap {
    private Map<String, Parcel> parcels = new HashMap<>();

    public void addParcel(Parcel parcel) {
        parcels.put(parcel.getId(), parcel);
        Log.getInstance().logEvent("Parcel added: " + parcel);
    }

    public Parcel findParcel(String id) {
        return parcels.get(id);
    }

    public void removeParcel(String id) {
        Parcel removed = parcels.remove(id);
        if (removed != null) {
            Log.getInstance().logEvent("Parcel removed: " + removed);
        }
    }

    public Map<String, Parcel> getParcels() {
        return parcels;
    }

    @Override
    public String toString() {
        return "Parcels: " + parcels.values().toString();
    }
}
