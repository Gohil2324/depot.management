package com.depot.management;

public class Worker {
    private ParcelMap parcelMap;
    private QueueOfCustomers customerQueue;

    public Worker(ParcelMap parcelMap, QueueOfCustomers customerQueue) {
        this.parcelMap = parcelMap;
        this.customerQueue = customerQueue;
    }

    public void processNextCustomer() {
        Customer customer = customerQueue.dequeue();
        if (customer == null) {
            System.out.println("No customers in queue.");
            return;
        }

        Parcel parcel = parcelMap.findParcel(customer.getParcelId());
        if (parcel == null) {
            System.out.println("Parcel not found for customer: " + customer);
            return;
        }

        float fee = parcel.calculateFee();
        parcel.updateStatus(ParcelStatus.COLLECTED);
        parcelMap.removeParcel(parcel.getId());

        String event = "Customer " + customer.getName() + " collected Parcel ID: " + parcel.getId() + " (Fee: $" + fee + ")";
        Log.getInstance().logEvent(event);

        System.out.println(event);
    }
}
