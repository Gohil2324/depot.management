  package com.depot.management;

import java.util.Scanner;

public class Manager {
    private static final QueueOfCustomers customerQueue = new QueueOfCustomers();
    private static final ParcelMap parcelMap = new ParcelMap();
    private static final Worker worker = new Worker(parcelMap, customerQueue);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Load initial data (replace paths with actual file paths)
        String parcelsFilePath = "C:/Users/Owner/OneDrive - University of Hertfordshire/last year/Software Architecture/ASSINMENTS/Parcels.csv";
        String customersFilePath = "C:/Users/Owner/OneDrive - University of Hertfordshire/last year/Software Architecture/ASSINMENTS/Custs.csv";

        loadParcels(parcelMap, parcelsFilePath);
        loadCustomers(customerQueue, customersFilePath);

        int choice;
        do {
            System.out.println("\nMenu:");
            System.out.println("1. Add Customer");
            System.out.println("2. Remove Customer");
            System.out.println("3. Display Queue");
            System.out.println("4. Display Parcels");
            System.out.println("5. Process Next Customer");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1 -> addCustomer(scanner);
                case 2 -> removeCustomer();
                case 3 -> displayQueue();
                case 4 -> displayParcels();
                case 5 -> processNextCustomer();
                case 6 -> System.out.println("Exiting the program. Goodbye!");
                default -> System.out.println("Invalid option. Please try again.");
            }
        } while (choice != 6);

        scanner.close();
    }

    private static void addCustomer(Scanner scanner) {
        System.out.print("Enter Customer Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Parcel ID: ");
        String parcelId = scanner.nextLine();

        int queueNumber = customerQueue.getCustomers().size() + 1;
        Customer customer = new Customer(queueNumber, name, parcelId);
        customerQueue.enqueue(customer);
        System.out.println("Customer added successfully.");
    }

    private static void removeCustomer() {
        Customer removed = customerQueue.dequeue();
        if (removed != null) {
            System.out.println("Removed customer: " + removed);
        } else {
            System.out.println("No customers in the queue to remove.");
        }
    }

    private static void displayQueue() {
        System.out.println("\nCustomer Queue:");
        System.out.println(customerQueue);
    }

    private static void displayParcels() {
        System.out.println("\nParcels:");
        System.out.println(parcelMap);
    }

    private static void processNextCustomer() {
        worker.processNextCustomer();
    }

    private static void loadParcels(ParcelMap parcelMap, String filePath) {
        try (Scanner fileScanner = new Scanner(new java.io.File(filePath))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] data = line.split(",");
                if (data.length == 6) {
                    parcelMap.addParcel(new Parcel(
                            data[0],
                            Float.parseFloat(data[1]),
                            Float.parseFloat(data[2]),
                            Float.parseFloat(data[3]),
                            Float.parseFloat(data[4]),
                            Integer.parseInt(data[5]),
                            ParcelStatus.WAITING_FOR_COLLECTION
                    ));
                }
            }
            System.out.println("Parcels loaded successfully.");
        } catch (Exception e) {
            System.err.println("Error loading parcels: " + e.getMessage());
        }
    }

    private static void loadCustomers(QueueOfCustomers queue, String filePath) {
        try (Scanner fileScanner = new Scanner(new java.io.File(filePath))) {
            int queueNumber = 1;
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] data = line.split(",");
                if (data.length == 2) {
                    queue.enqueue(new Customer(queueNumber++, data[0], data[1]));
                }
            }
            System.out.println("Customers loaded successfully.");
        } catch (Exception e) {
            System.err.println("Error loading customers: " + e.getMessage());
        }
    }
}
