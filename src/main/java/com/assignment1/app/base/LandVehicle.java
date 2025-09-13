package com.assignment1.app.base;

import com.assignment1.app.exceptions.InvalidOperationException;

public abstract class LandVehicle extends Vehicle {

  private int numWheels;

  public LandVehicle(String id, String model, double maxSpeed, double currentMileage, int numWheels)
      throws InvalidOperationException {
    super(id, model, maxSpeed, currentMileage);
    this.numWheels = numWheels;
  }

  protected int getNumWheels() {
    return numWheels;
  }

  @Override
  public double estimateJourneyTime(double distance) {
    double maxSpeed = getMaxSpeed();
    return (distance / maxSpeed) * 1.1;
  }
}
