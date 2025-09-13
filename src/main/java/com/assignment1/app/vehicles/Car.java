package com.assignment1.app.vehicles;

import com.assignment1.app.exceptions.*;
import com.assignment1.app.interfaces.*;
import com.assignment1.app.base.*;

public class Car extends LandVehicle implements FuelConsumable, PassengerCarrier, Maintainable {

  private double fuelLevel;
  private final int passengerCapacity;
  private int currentPassengers;
  private boolean maintenanceNeeded;

  public Car(String id, String model, double maxSpeed, double currentMileage, int numWheels)
      throws InvalidOperationException {
    super(id, model, maxSpeed, currentMileage, numWheels);

    this.fuelLevel = 0.0;
    this.passengerCapacity = 5;
    this.currentPassengers = 0;
    this.maintenanceNeeded = false;
  }

  public void setFuelLevel(double fuelLevel) {
    this.fuelLevel = fuelLevel;
  }

  public void setCurrentPassengers(int currentPassengers) {
    this.currentPassengers = currentPassengers;
  }

  public void setMaintenanceNeeded(boolean maintenanceNeeded) {
    this.maintenanceNeeded = maintenanceNeeded;
  }

  @Override
  public String toCsvString() {
    return String.join(",",
        "Car",
        getId(),
        getModel(),
        String.valueOf(getMaxSpeed()),
        String.valueOf(getCurrentMileage()),
        String.valueOf(getNumWheels()),
        String.valueOf(this.fuelLevel),
        String.valueOf(this.passengerCapacity),
        String.valueOf(this.currentPassengers),
        String.valueOf(this.maintenanceNeeded)

    );
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
    return 15.0;
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
