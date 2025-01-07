  package com.depot.management;

import java.util.LinkedList;
import java.util.Queue;

public class QueueOfCustomers {
    private Queue<Customer> customers = new LinkedList<>();

    public void enqueue(Customer customer) {
        customers.add(customer);
        Log.getInstance().logEvent("Customer added to queue: " + customer);
    }

    public Customer dequeue() {
        Customer customer = customers.poll();
        if (customer != null) {
            Log.getInstance().logEvent("Customer removed from queue: " + customer);
        }
        return customer;
    }

    public Customer peek() {
        return customers.peek();
    }

    public Queue<Customer> getCustomers() {
        return customers;
    }

    @Override
    public String toString() {
        return "Customer Queue: " + customers.toString();
    }
}
