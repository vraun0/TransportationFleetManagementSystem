package com.assignment1.app.base;

import com.assignment1.app.exceptions.InvalidOperationException;

public abstract class AirVehicle extends Vehicle {

  private double maxAltitude;

  public AirVehicle(String id, String model, double maxSpeed, double currentMileage, double maxAltitude)
      throws InvalidOperationException {
    super(id, model, maxSpeed, currentMileage);
    this.maxAltitude = maxAltitude;
  }

  protected double getMaxAltitude() {
    return maxAltitude;
  }

  @Override
  public double estimateJourneyTime(double distance) {
    double maxSpeed = getMaxSpeed();
    return (distance / maxSpeed) * 0.95;
  }
}
