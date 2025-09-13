package com.assignment1.app.fleet;

import java.io.BufferedWriter;
import java.util.*;
import java.io.IOException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import com.assignment1.app.base.*;
import com.assignment1.app.exceptions.*;
import com.assignment1.app.vehicles.*;

public class FleetManager {
  public ArrayList<Vehicle> vehicles = new ArrayList<>();

  public void addVehicle(Vehicle vehicle) throws InvalidOperationException {
    for (Vehicle v : vehicles) {
      if (v.getId().equals(vehicle.getId())) {
        throw new InvalidOperationException("Vehicle with ID " + vehicle.getId() + " already exists.");
      }
    }
    vehicles.add(vehicle);
  }

  public void removeVehicle(String vehicleId) throws InvalidOperationException {
    for (Vehicle v : vehicles) {
      if (v.getId().equals(vehicleId)) {
        vehicles.remove(v);
      }
    }
  }

  public void startAllJourneys(double distance) {
    for (Vehicle v : vehicles) {
      try {
        v.move(distance);
      } catch (InvalidOperationException | InsufficientFuelException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  public double getTotalFuelConsumption(double distance) {
    double fuelConsumption = 0;
    for (Vehicle v : vehicles) {
      try {
        fuelConsumption += v.consumeFuel(distance);
      } catch (InsufficientFuelException e) {
        System.out.println(e.getMessage());
      }
    }
    return fuelConsumption;
  }

  public void maintainAll() {
    for (Vehicle v : vehicles) {
      if (v.needsMaintenance()) {
        v.performMaintenance();
      }
    }
  }

  public ArrayList<Vehicle> searchByType(Class<?> type) {
    ArrayList<Vehicle> results = new ArrayList<>();
    for (Vehicle v : vehicles) {
      if (type.isInstance(v)) {
        results.add(v);
      }
    }
    return results;
  }

  public void sortFleetByEfficiency() {
    Collections.sort(vehicles);
  }

  public String generateReport() {
    StringBuilder report = new StringBuilder();

    int totalVehicles = vehicles.size();
    double averageEfficiency = 0;
    double totalMileage = 0;

    report.append("Total Vehicles: " + totalVehicles + "\n");

    for (Vehicle v : vehicles) {
      report.append(v.getId() + "\n");
      report.append("Model: " + v.getModel() + "\n");
      report.append("Maintenance Status: " + v.needsMaintenance() + "\n");
      report.append("\n");

      totalMileage = totalMileage + v.getCurrentMileage();
      averageEfficiency += v.calculateFuelEfficiency();
    }

    averageEfficiency /= totalVehicles;
    report.append("Total Mileage: " + totalMileage + "\n");
    report.append("Average Efficiency: " + averageEfficiency + "\n");
    return report.toString();
  }

  public ArrayList<Vehicle> getVehiclesNeedingMaintenance() {
    ArrayList<Vehicle> results = new ArrayList<>();
    for (Vehicle v : vehicles) {
      if (v.needsMaintenance()) {
        results.add(v);
      }
    }
    return results;
  }

  public void saveToFile(String filename) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
      for (Vehicle v : vehicles) {
        writer.write(v.toCsvString());
        writer.newLine();
      }
    } catch (Exception e) {
      System.out.println("Error saving to file: " + e.getMessage());
    }
  }

  public void loadFromFile(String filename) throws IOException {
    ArrayList<Vehicle> loadedVehicles = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
      String line;
      int lineNumber = 1;
      while ((line = reader.readLine()) != null) {
        if (line.trim().isEmpty()) {
          continue;
        }
        try {
          Vehicle v = createVehicleFromCsvString(line);
          loadedVehicles.add(v);
        } catch (Exception e) {
          System.out.println("Error loading line " + lineNumber + ": " + e.getMessage());
        }
        lineNumber++;
      }
    } catch (Exception e) {
      System.out.println("Error loading from file: " + e.getMessage());
    }
    this.vehicles = loadedVehicles;
  }

  private static Vehicle createVehicleFromCsvString(String csvLine) throws InvalidOperationException {
    String[] data = csvLine.split(",");
    String type = data[0];

    switch (type) {
      case "Car":
        String CarId = data[1];
        String CarModel = data[2];
        double CarMaxSpeed = Double.parseDouble(data[3]);
        double CarCurrentMileage = Double.parseDouble(data[4]);
        int CarNumWheels = Integer.parseInt(data[5]);
        double CarFuelLevel = Double.parseDouble(data[6]);
        int CarPassengerCapacity = Integer.parseInt(data[7]);
        int CarCurrentPassengers = Integer.parseInt(data[8]);
        boolean CarMaintenanceNeeded = Boolean.parseBoolean(data[9]);
        Car car = new Car(CarId, CarModel, CarMaxSpeed, CarCurrentMileage, CarNumWheels);
        car.setFuelLevel(CarFuelLevel);
        car.setCurrentPassengers(CarCurrentPassengers);
        car.setMaintenanceNeeded(CarMaintenanceNeeded);
        return car;

      case "Truck":
        String TruckId = data[1];
        String TruckModel = data[2];
        double TruckMaxSpeed = Double.parseDouble(data[3]);
        double TruckCurrentMileage = Double.parseDouble(data[4]);
        int TruckNumWheels = Integer.parseInt(data[5]);
        double TruckFuelLevel = Double.parseDouble(data[6]);
        double TruckCargoCapacity = Double.parseDouble(data[7]);
        double TruckCurrentCargo = Double.parseDouble(data[8]);
        boolean TruckMaintenanceNeeded = Boolean.parseBoolean(data[9]);
        Truck truck = new Truck(TruckId, TruckModel, TruckMaxSpeed, TruckCurrentMileage, TruckNumWheels);
        truck.setFuelLevel(TruckFuelLevel);
        truck.setCurrentCargo(TruckCurrentCargo);
        truck.setMaintenanceNeeded(TruckMaintenanceNeeded);
        return truck;

      case "Bus":
        String BusId = data[1];
        String BusModel = data[2];
        double BusMaxSpeed = Double.parseDouble(data[3]);
        double BusCurrentMileage = Double.parseDouble(data[4]);
        int BusNumWheels = Integer.parseInt(data[5]);
        double BusFuelLevel = Double.parseDouble(data[6]);
        int BusPassengerCapacity = Integer.parseInt(data[7]);
        int BusCurrentPassengers = Integer.parseInt(data[8]);
        double BusCargoCapacity = Double.parseDouble(data[9]);
        double BusCurrentCargo = Double.parseDouble(data[10]);
        boolean BusMaintenanceNeeded = Boolean.parseBoolean(data[11]);
        Bus bus = new Bus(BusId, BusModel, BusMaxSpeed, BusCurrentMileage, BusNumWheels);
        bus.setFuelLevel(BusFuelLevel);
        bus.setCurrentPassengers(BusCurrentPassengers);
        bus.setCurrentCargo(BusCurrentCargo);
        bus.setMaintenanceNeeded(BusMaintenanceNeeded);
        return bus;

      case "Airplane":
        String AirplaneId = data[1];
        String AirplaneModel = data[2];
        double AirplaneMaxSpeed = Double.parseDouble(data[3]);
        double AirplaneCurrentMileage = Double.parseDouble(data[4]);
        double AirplaneMaxAltitude = Double.parseDouble(data[5]);
        double AirplaneFuelLevel = Double.parseDouble(data[6]);
        int AirplanePassengerCapacity = Integer.parseInt(data[7]);
        int AirplaneCurrentPassengers = Integer.parseInt(data[8]);
        double AirplaneCargoCapacity = Double.parseDouble(data[9]);
        double AirplaneCurrentCargo = Double.parseDouble(data[10]);
        boolean AirplaneMaintenanceNeeded = Boolean.parseBoolean(data[11]);
        Airplane airplane = new Airplane(AirplaneId, AirplaneModel, AirplaneMaxSpeed, AirplaneCurrentMileage,
            AirplaneMaxAltitude);
        airplane.setFuelLevel(AirplaneFuelLevel);
        airplane.setCurrentPassengers(AirplaneCurrentPassengers);
        airplane.setCurrentCargo(AirplaneCurrentCargo);
        airplane.setMaintenanceNeeded(AirplaneMaintenanceNeeded);
        return airplane;

      case "CargoShip":
        String CargoShipId = data[1];
        String CargoShipModel = data[2];
        double CargoShipMaxSpeed = Double.parseDouble(data[3]);
        double CargoShipCurrentMileage = Double.parseDouble(data[4]);
        boolean CargoShipHasSail = Boolean.parseBoolean(data[5]);
        double CargoShipFuelLevel = Double.parseDouble(data[6]);
        double CargoShipCargoCapacity = Double.parseDouble(data[7]);
        double CargoShipCurrentCargo = Double.parseDouble(data[8]);
        boolean CargoShipMaintenanceNeeded = Boolean.parseBoolean(data[9]);
        CargoShip cargoShip = new CargoShip(CargoShipId, CargoShipModel, CargoShipMaxSpeed, CargoShipCurrentMileage,
            CargoShipHasSail);
        cargoShip.setFuelLevel(CargoShipFuelLevel);
        cargoShip.setCurrentCargo(CargoShipCurrentCargo);
        cargoShip.setMaintenanceNeeded(CargoShipMaintenanceNeeded);
        return cargoShip;

      default:
        throw new InvalidOperationException("Invalid vehicle type: " + type);
    }

  }
}
