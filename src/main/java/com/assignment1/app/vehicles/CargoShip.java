package com.assignment1.app.vehicles;

import com.assignment1.app.exceptions.*;
import com.assignment1.app.interfaces.*;
import com.assignment1.app.base.*;

public class CargoShip extends WaterVehicle implements CargoCarrier, Maintainable, FuelConsumable {

  private final double cargoCapacity;
  private double currentCargo;
  private boolean maintenanceNeeded;
  private double fuelLevel;

  public CargoShip(String id, String model, double maxSpeed, double currentMileage, boolean hasSail)
      throws InvalidOperationException {
    super(id, model, maxSpeed, currentMileage, hasSail);
    this.cargoCapacity = 50000;
    this.currentCargo = 0;
    this.maintenanceNeeded = false;
    this.fuelLevel = 0;

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

  public String toCsvString() {
    return String.join(",",
        "CargoShip",
        getId(),
        getModel(),
        String.valueOf(getMaxSpeed()),
        String.valueOf(getCurrentMileage()),
        String.valueOf(getHasSail()),
        String.valueOf(this.fuelLevel),
        String.valueOf(this.cargoCapacity),
        String.valueOf(this.currentCargo),
        String.valueOf(this.maintenanceNeeded));
  }

  @Override
  public void move(double distance) throws InvalidOperationException, InsufficientFuelException {
    if (distance < 0) {
      throw new InvalidOperationException("Distance cannot be negative.");
    }
    if (!getHasSail()) {
      double fuelRequired = distance / calculateFuelEfficiency();
      if (fuelLevel < fuelRequired) {
        throw new InsufficientFuelException("Not enough fuel to complete the journey. Required: "
            + String.format("%.2f", fuelRequired) + "L, Available: " + String.format("%.2f", fuelLevel) + "L.");
      }
      consumeFuel(distance);
    }
    addMileage(distance);
  }

  @Override
  public double calculateFuelEfficiency() {
    if (!getHasSail()) {
      return 4.0;
    }
    return 0;
  }

  public void refuel(double amount) throws InvalidOperationException {
    if (getHasSail()) {
      throw new InvalidOperationException("Cannot refuel a cargo ship that has a sail.");
    }
    if (amount < 0) {
      throw new InvalidOperationException("Cannot refuel a negative amount.");
    }
    this.fuelLevel += amount;
  }

  public double getFuelLevel() {
    if (getHasSail()) {
      return 0;
    }
    return this.fuelLevel;
  }

  public double consumeFuel(double distance) throws InsufficientFuelException {
    if (getHasSail()) {
      return 0;
    }

    double fuelConsumed = distance / calculateFuelEfficiency();
    if (fuelConsumed > this.fuelLevel) {
      throw new InsufficientFuelException("Not enough fuel for the cargo ship's journey.");
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
