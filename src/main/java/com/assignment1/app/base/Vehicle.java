package com.assignment1.app.base;

import com.assignment1.app.exceptions.*;

public abstract class Vehicle implements Comparable<Vehicle> {
  private String id;
  private String model;
  private double maxSpeed;
  private double currentMileage;

  public Vehicle(String id, String model, double maxSpeed, double currentMileage) throws InvalidOperationException {
    if (id.isEmpty()) {
      throw new InvalidOperationException("Vehicle ID cannot be empty.");
    }
    this.id = id;
    this.model = model;
    this.maxSpeed = maxSpeed;
    this.currentMileage = currentMileage;
  }

  public abstract void move(double distance) throws InvalidOperationException, InsufficientFuelException;

  public abstract double calculateFuelEfficiency();

  public abstract double estimateJourneyTime(double distance);

  public abstract String toCsvString();

  public abstract boolean needsMaintenance();

  public abstract double consumeFuel(double distance) throws InsufficientFuelException;

  public abstract void performMaintenance();

  public abstract void refuel(double amount) throws InvalidOperationException;

  public int compareTo(Vehicle other) {
    return Double.compare(this.calculateFuelEfficiency(), other.calculateFuelEfficiency());
  }

  public void displayInfo() {
    System.out.println("Vehicle ID: " + id);
    System.out.println("Model: " + model);
    System.out.println("Max Speed: " + maxSpeed);
    System.out.println("Current Mileage: " + currentMileage);
  }

  public double getCurrentMileage() {
    return currentMileage;
  }

  public String getId() {
    return id;
  }

  public String getModel() {
    return model;
  }

  protected void addMileage(double distance) {
    this.currentMileage += distance;
  }

  protected double getMaxSpeed() {
    return maxSpeed;
  }

}
