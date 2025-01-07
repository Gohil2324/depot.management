  package com.depot.management;

public class Main {
    public static void main(String[] args) {
        // Initialize the model components
        ParcelMap parcelMap = new ParcelMap();
        QueueOfCustomers customerQueue = new QueueOfCustomers();

        // Define file paths (replace with actual file paths on your system)
        String parcelsFilePath = "C:/Users/Owner/OneDrive - University of Hertfordshire/last year/Software Architecture/ASSINMENTS/Parcels.csv";
        String customersFilePath = "C:/Users/Owner/OneDrive - University of Hertfordshire/last year/Software Architecture/ASSINMENTS/Custs.csv";

        // Load data from CSV files
        loadParcels(parcelMap, parcelsFilePath);
        loadCustomers(customerQueue, customersFilePath);

        // Launch the GUI
        new DepotGUI(parcelMap, customerQueue);
    }

    public static void loadParcels(ParcelMap parcelMap, String filePath) {
        try (java.util.Scanner scanner = new java.util.Scanner(new java.io.File(filePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");
                if (data.length == 6) {
                    parcelMap.addParcel(new Parcel(
                            data[0], // Parcel ID
                            Float.parseFloat(data[1]), // Length
                            Float.parseFloat(data[2]), // Width
                            Float.parseFloat(data[3]), // Height
                            Float.parseFloat(data[4]), // Weight
                            Integer.parseInt(data[5]), // Days in Depot
                            ParcelStatus.WAITING_FOR_COLLECTION // Default status
                    ));
                }
            }
            System.out.println("Parcels loaded successfully.");
        } catch (Exception e) {
            System.err.println("Error loading parcels: " + e.getMessage());
        }
    }

    public static void loadCustomers(QueueOfCustomers queue, String filePath) {
        try (java.util.Scanner scanner = new java.util.Scanner(new java.io.File(filePath))) {
            int queueNumber = 1;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
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
