package com.depot.management;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DepotGUI {
    private JFrame frame;
    private JTextArea parcelsArea;
    private JTextArea customersArea;
    private JTextArea logArea;

    private ParcelMap parcelMap;
    private QueueOfCustomers customerQueue;
    private Worker worker;

    public DepotGUI(ParcelMap parcelMap, QueueOfCustomers customerQueue) {
        this.parcelMap = parcelMap;
        this.customerQueue = customerQueue;
        this.worker = new Worker(parcelMap, customerQueue);

        initializeGUI();
    }

    private void initializeGUI() {
        frame = new JFrame("Depot Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setLayout(new BorderLayout());

        // Create panels
        JPanel mainPanel = new JPanel(new GridLayout(1, 2));
        parcelsArea = new JTextArea(20, 30);
        customersArea = new JTextArea(20, 30);
        logArea = new JTextArea(10, 60);
        logArea.setEditable(false); // Logs are read-only

        JScrollPane parcelsScrollPane = new JScrollPane(parcelsArea);
        JScrollPane customersScrollPane = new JScrollPane(customersArea);
        JScrollPane logScrollPane = new JScrollPane(logArea);

        mainPanel.add(parcelsScrollPane);
        mainPanel.add(customersScrollPane);

        parcelsArea.setBorder(BorderFactory.createTitledBorder("Parcels List"));
        customersArea.setBorder(BorderFactory.createTitledBorder("Customer Queue"));
        logArea.setBorder(BorderFactory.createTitledBorder("Processing Log"));

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.add(logScrollPane, BorderLayout.SOUTH);

        // Control buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 6));
        JButton addCustomerButton = new JButton("Add Customer");
        JButton removeCustomerButton = new JButton("Remove Customer");
        JButton displayQueueButton = new JButton("Display Queue");
        JButton displayParcelsButton = new JButton("Display Parcels");
        JButton processNextButton = new JButton("Process Next Customer");
        JButton exitButton = new JButton("Exit");

        buttonPanel.add(addCustomerButton);
        buttonPanel.add(removeCustomerButton);
        buttonPanel.add(displayQueueButton);
        buttonPanel.add(displayParcelsButton);
        buttonPanel.add(processNextButton);
        buttonPanel.add(exitButton);

        frame.add(buttonPanel, BorderLayout.NORTH);

        // Button actions
        addCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCustomer();
            }
        });

        removeCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeCustomer();
            }
        });

        displayQueueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayQueue();
            }
        });

        displayParcelsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayParcels();
            }
        });

        processNextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processNextCustomer();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Initial view update
        refreshView();

        frame.setVisible(true);
    }

    private void addCustomer() {
        String name = JOptionPane.showInputDialog(frame, "Enter Customer Name:");
        if (name == null || name.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Customer name cannot be empty!");
            return;
        }

        String parcelId = JOptionPane.showInputDialog(frame, "Enter Parcel ID:");
        if (parcelId == null || parcelId.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Parcel ID cannot be empty!");
            return;
        }

        int queueNumber = customerQueue.getCustomers().size() + 1;
        Customer customer = new Customer(queueNumber, name, parcelId);
        customerQueue.enqueue(customer);
        logAction("Added customer: " + customer);
        refreshView();
    }

    private void removeCustomer() {
        Customer removed = customerQueue.dequeue();
        if (removed != null) {
            logAction("Removed customer: " + removed);
        } else {
            logAction("No customers in the queue to remove.");
        }
        refreshView();
    }

    private void displayQueue() {
        StringBuilder customersText = new StringBuilder();
        for (Customer customer : customerQueue.getCustomers()) {
            customersText.append(customer).append("\n");
        }
        customersArea.setText(customersText.toString());
        logAction("Displayed customer queue.");
    }

    private void displayParcels() {
        StringBuilder parcelsText = new StringBuilder();
        for (Parcel parcel : parcelMap.getParcels().values()) {
            parcelsText.append(parcel).append("\n");
        }
        parcelsArea.setText(parcelsText.toString());
        logAction("Displayed parcel list.");
    }

    private void processNextCustomer() {
        Customer nextCustomer = customerQueue.peek();
        if (nextCustomer == null) {
            logAction("No customers in the queue to process.");
            return;
        }

        Parcel parcel = parcelMap.findParcel(nextCustomer.getParcelId());
        if (parcel == null) {
            logAction("No parcel found for the customer: " + nextCustomer.getName());
            return;
        }

        worker.processNextCustomer();
        logAction("Processed customer: " + nextCustomer.getName() + " for Parcel ID: " + parcel.getId());
        refreshView();
    }

    private void refreshView() {
        displayQueue();
        displayParcels();
    }

    private void logAction(String message) {
        logArea.append(message + "\n");
    }
}
