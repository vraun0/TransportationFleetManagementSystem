package com.assignment1.app;

import com.assignment1.app.fleet.FleetManager;
import com.assignment1.app.base.*;
import com.assignment1.app.exceptions.*;
import com.assignment1.app.vehicles.*;
import java.util.*;

public class Main {
  private static FleetManager fleetManager;

  public static void main(String[] args) {
    fleetManager = new FleetManager();
    demo();

    fleetManager = new FleetManager();
    Scanner scanner = new Scanner(System.in);

    while (true) {
      System.out.println("------------------------");
      System.out.println("1. Add Vehicle");
      System.out.println("2. Remove Vehicle");
      System.out.println("3. Start All Journeys");
      System.out.println("4. Refuel Vehicles");
      System.out.println("5. Maintain Vehicles");
      System.out.println("6. Generate Report");
      System.out.println("7. Save Vehicles");
      System.out.println("8. Load Vehicles");
      System.out.println("9. Search by Type");
      System.out.println("10. List Vehicles Needing Maintenance");
      System.out.println("11. Exit");
      System.out.println("------------------------");

      int choice = getIntInput(scanner, "Enter choice (1-11): ", 1, 11);

      switch (choice) {
        case 1:
          addVehicleCLI(scanner);
          break;
        case 2:
          System.out.print("Enter vehicle ID to remove: ");
          String vehicleIdToRemove = getStringInput(scanner);
          try {
            fleetManager.removeVehicle(vehicleIdToRemove);
          } catch (InvalidOperationException e) {
            System.out.println(e.getMessage());
          }
          break;
        case 3:
          double distance = getDoubleInput(scanner, "Enter distance: ", 0, Double.MAX_VALUE);
          fleetManager.startAllJourneys(distance);
          break;
        case 4:
          double distanceToRefuel = getDoubleInput(scanner, "Enter distance: ", 0, Double.MAX_VALUE);
          for (Vehicle v : fleetManager.vehicles) {
            try {
              v.refuel(distanceToRefuel);
            } catch (InvalidOperationException e) {
              System.out.println(e.getMessage());
            }
          }
          break;
        case 5:
          fleetManager.maintainAll();
          break;
        case 6:
          System.out.println(fleetManager.generateReport());
          break;
        case 7:
          System.out.print("Enter filename to save: ");
          String filename = getStringInput(scanner);
          try {
            fleetManager.saveToFile(filename);
          } catch (Exception e) {
            System.out.println(e.getMessage());
          }
          break;
        case 8:
          System.out.print("Enter filename to load: ");
          String filenameToLoad = getStringInput(scanner);
          try {
            fleetManager.loadFromFile(filenameToLoad);
          } catch (Exception e) {
            System.out.println(e.getMessage());
          }
          break;
        case 9:
          System.out.print("Enter full class name (e.g., com.assignment1.app.vehicles.Car): ");
          String type = getStringInput(scanner);
          try {
            ArrayList<Vehicle> vehicles = fleetManager.searchByType(Class.forName(type));
            for (Vehicle v : vehicles) {
              System.out.println(v.getId());
            }
          } catch (Exception e) {
            System.out.println(e.getMessage());
          }
          break;
        case 10:
          List<Vehicle> vehiclesNeedingMaintenance = fleetManager.getVehiclesNeedingMaintenance();
          for (Vehicle v : vehiclesNeedingMaintenance) {
            System.out.println(v.getId());
          }
          break;
        case 11:
          System.exit(0);
          break;
      }
    }
  }

  private static int getIntInput(Scanner scanner, String prompt, int min, int max) {
    while (true) {
      System.out.print(prompt);
      if (scanner.hasNextInt()) {
        int value = scanner.nextInt();
        scanner.nextLine();
        if (value >= min && value <= max) {
          return value;
        }
      } else {
        scanner.nextLine();
      }
      System.out.println("Invalid input. Please enter a number between " + min + " and " + max + ".");
    }
  }

  private static double getDoubleInput(Scanner scanner, String prompt, double min, double max) {
    while (true) {
      System.out.print(prompt);
      if (scanner.hasNextDouble()) {
        double value = scanner.nextDouble();
        scanner.nextLine();
        if (value >= min && value <= max) {
          return value;
        }
      } else {
        scanner.nextLine();
      }
      System.out.println("Invalid input. Please enter a number between " + min + " and " + max + ".");
    }
  }

  private static String getStringInput(Scanner scanner) {
    while (true) {
      String input = scanner.nextLine().trim();
      if (!input.isEmpty())
        return input;
      System.out.println("Input cannot be empty. Try again:");
    }
  }

  private static void addVehicleCLI(Scanner scanner) {
    System.out.println("------------------------");
    System.out.println("1. Car");
    System.out.println("2. Truck");
    System.out.println("3. Bus");
    System.out.println("4. Airplane");
    System.out.println("5. CargoShip");
    System.out.println("------------------------");

    int vehicleType = getIntInput(scanner, "Select vehicle type (1-5): ", 1, 5);

    System.out.print("Enter vehicle ID: ");
    String vehicleId = getStringInput(scanner);
    System.out.print("Enter vehicle model: ");
    String vehicleModel = getStringInput(scanner);
    double vehicleMaxSpeed = getDoubleInput(scanner, "Enter vehicle max speed: ", 0, Double.MAX_VALUE);

    Vehicle newVehicle = null;
    switch (vehicleType) {
      case 1:
        int carWheels = getIntInput(scanner, "Enter number of wheels: ", 1, 20);
        try {
          newVehicle = new Car(vehicleId, vehicleModel, vehicleMaxSpeed, 0, carWheels);
        } catch (InvalidOperationException e) {
          System.out.println(e.getMessage());
        }
        break;
      case 2:
        int truckWheels = getIntInput(scanner, "Enter number of wheels: ", 1, 20);
        try {
          newVehicle = new Truck(vehicleId, vehicleModel, vehicleMaxSpeed, 0, truckWheels);
        } catch (InvalidOperationException e) {
          System.out.println(e.getMessage());
        }
        break;
      case 3:
        int busWheels = getIntInput(scanner, "Enter number of wheels: ", 1, 20);
        try {
          newVehicle = new Bus(vehicleId, vehicleModel, vehicleMaxSpeed, 0, busWheels);
        } catch (InvalidOperationException e) {
          System.out.println(e.getMessage());
        }
        break;
      case 4:
        double maxAltitude = getDoubleInput(scanner, "Enter max altitude: ", 0, Double.MAX_VALUE);
        try {
          newVehicle = new Airplane(vehicleId, vehicleModel, vehicleMaxSpeed, 0, maxAltitude);
        } catch (InvalidOperationException e) {
          System.out.println(e.getMessage());
        }
        break;
      case 5:
        System.out.print("Does the ship have a sail? (true/false): ");
        boolean hasSail = scanner.nextBoolean();
        scanner.nextLine();
        try {
          newVehicle = new CargoShip(vehicleId, vehicleModel, vehicleMaxSpeed, 0, hasSail);
        } catch (InvalidOperationException e) {
          System.out.println(e.getMessage());
        }
        break;
    }

    try {
      fleetManager.addVehicle(newVehicle);
    } catch (InvalidOperationException e) {
      System.out.println(e.getMessage());
    }
  }

  public static void demo() {
    try {
      Car car = new Car("Car1", "Car", 100, 0, 4);
      Truck truck = new Truck("Truck1", "Truck", 100, 0, 4);
      Bus bus = new Bus("Bus1", "Bus", 100, 0, 4);
      Airplane airplane = new Airplane("Airplane1", "Airplane", 100, 0, 10000);
      CargoShip cargoShip = new CargoShip("CargoShip1", "CargoShip", 100, 0, true);
      fleetManager.addVehicle(car);
      fleetManager.addVehicle(truck);
      fleetManager.addVehicle(bus);
      fleetManager.addVehicle(airplane);
      fleetManager.addVehicle(cargoShip);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    fleetManager.startAllJourneys(100);
    System.out.println(fleetManager.getTotalFuelConsumption(100));
    System.out.println(fleetManager.generateReport());
    try {
      fleetManager.saveToFile("Vehicles.csv");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}
