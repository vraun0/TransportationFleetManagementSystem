package com.assignment1.app.vehicles;

import com.assignment1.app.exceptions.*;
import com.assignment1.app.interfaces.*;
import com.assignment1.app.base.*;

public class Airplane extends AirVehicle implements FuelConsumable, PassengerCarrier, CargoCarrier, Maintainable {
  private double fuelLevel;
  private final int passengerCapacity;
  private int currentPassengers;
  private double cargoCapacity;
  private double currentCargo;
  private boolean maintenanceNeeded;

  public Airplane(String id, String model, double maxSpeed, double currentMileage, double maxAltitude)
      throws InvalidOperationException {
    super(id, model, maxSpeed, currentMileage, maxAltitude);
    this.fuelLevel = 0.0;
    this.passengerCapacity = 200;
    this.currentPassengers = 0;
    this.cargoCapacity = 10000.0;
    this.currentCargo = 0.0;
    this.maintenanceNeeded = false;
  }

  public void setFuelLevel(double fuelLevel) {
    this.fuelLevel = fuelLevel;
  }

  public void setCurrentPassengers(int currentPassengers) {
    this.currentPassengers = currentPassengers;
  }

  public void setCurrentCargo(double currentCargo) {
    this.currentCargo = currentCargo;
  }

  public void setMaintenanceNeeded(boolean maintenanceNeeded) {
    this.maintenanceNeeded = maintenanceNeeded;
  }

  public String toCsvString() {
    return String.join(",",
        "Airplane",
        getId(),
        getModel(),
        String.valueOf(getMaxSpeed()),
        String.valueOf(getCurrentMileage()),
        String.valueOf(getMaxAltitude()),
        String.valueOf(this.fuelLevel),
        String.valueOf(this.passengerCapacity),
        String.valueOf(this.currentPassengers),
        String.valueOf(this.cargoCapacity),
        String.valueOf(this.currentCargo),
        String.valueOf(this.maintenanceNeeded));
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

  @Override
  public double calculateFuelEfficiency() {
    return 5.0;
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

  public void boardPassengers(int count) throws OverloadException {
    if (currentPassengers + count > passengerCapacity) {
      throw new OverloadException(
          "Cannot board " + count + " passengers. Exceeds capacity of " + passengerCapacity + ".");
    }
    this.currentPassengers += count;
  }

  public void disembarkPassengers(int count) throws InvalidOperationException {
    if (count > this.currentPassengers) {
      throw new InvalidOperationException(
          "Cannot disembark " + count + " passengers. Only " + currentPassengers + " are on board.");
    }
    this.currentPassengers -= count;
  }

  public int getPassengerCapacity() {
    return this.passengerCapacity;
  }

  public int getCurrentPassengers() {
    return this.currentPassengers;
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
