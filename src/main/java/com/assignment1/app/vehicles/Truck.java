package com.assignment1.app.vehicles;

import com.assignment1.app.exceptions.*;
import com.assignment1.app.interfaces.*;
import com.assignment1.app.base.*;

public class Truck extends LandVehicle implements FuelConsumable, CargoCarrier, Maintainable {
  private double fuelLevel;
  private double cargoCapacity;
  private double currentCargo;
  private boolean maintenanceNeeded;

  public Truck(String id, String model, double maxSpeed, double currentMileage, int numWheels)
      throws InvalidOperationException {
    super(id, model, maxSpeed, currentMileage, numWheels);
    this.fuelLevel = 0.0;
    this.cargoCapacity = 5000.0;
    this.currentCargo = 0.0;
    this.maintenanceNeeded = false;
  }

  public String toCsvString() {
    return String.join(",",
        "Truck",
        getId(),
        getModel(),
        String.valueOf(getMaxSpeed()),
        String.valueOf(getCurrentMileage()),
        String.valueOf(getNumWheels()),
        String.valueOf(this.fuelLevel),
        String.valueOf(this.cargoCapacity),
        String.valueOf(this.currentCargo),
        String.valueOf(this.maintenanceNeeded));
  }

  public void setFuelLevel(double fuelLevel) {
    this.fuelLevel = fuelLevel;
  }

  public void setCurrentCargo(double currentCargo) {
    this.currentCargo = currentCargo;
  }

  public void setMaintenanceNeeded(boolean maintenanceNeeded) {
    this.maintenanceNeeded = maintenanceNeeded;
  }

  @Override
  public void move(double distance) throws InvalidOperationException, InsufficientFuelException {
    if (distance < 0) {
      throw new InvalidOperationException("Distance cannot be negative.");
    }
    double fuelRequired = distance / calculateFuelEfficiency();
    if (fuelLevel < fuelRequired) {
      throw new InsufficientFuelException("Not enough fuel to complete the journey. Required: "
          + String.format("%.2f", fuelRequired) + "L, Available: " + String.format("%.2f", fuelLevel) + "L.");
    }
    consumeFuel(distance);
    addMileage(distance);
    if (needsMaintenance()) {
      scheduleMaintenance();
    }
  }

  public double calculateFuelEfficiency() {
    if (this.currentCargo > this.cargoCapacity / 2) {
      return 7.2;
    } else {
      return 8.0;
    }
  }

  public void refuel(double amount) throws InvalidOperationException {
    if (amount <= 0) {
      throw new InvalidOperationException("Please specify a positive amount");
    }
    this.fuelLevel += amount;
  }

  public double getFuelLevel() {
    return this.fuelLevel;
  }

  public double consumeFuel(double distance) throws InsufficientFuelException {
    double fuelConsumed = distance / calculateFuelEfficiency();
    if (fuelConsumed > this.fuelLevel) {
      throw new InsufficientFuelException("Attempting to consume more fuel than available.");
    }
    this.fuelLevel -= fuelConsumed;
    return fuelConsumed;
  }

  public void loadCargo(double weight) throws OverloadException {
    if (weight > this.cargoCapacity) {
      throw new OverloadException("Cannot load cargo. Exceeds capacity of " + this.cargoCapacity + ".");
    }
    this.currentCargo += weight;
  }

  public void unloadCargo(double weight) throws InvalidOperationException {
    if (weight > this.currentCargo) {
      throw new InvalidOperationException("Cannot unload cargo. Only " + this.currentCargo + " is on board.");
    }
    this.currentCargo -= weight;
  }

  public double getCargoCapacity() {
    return this.cargoCapacity;
  }

  public double getCurrentCargo() {
    return this.currentCargo;
  }

  public void scheduleMaintenance() {
    this.maintenanceNeeded = true;
  }

  public boolean needsMaintenance() {
    return getCurrentMileage() > 10000;
  }

  public void performMaintenance() {
    this.maintenanceNeeded = false;
  }
}
