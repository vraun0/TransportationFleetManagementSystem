package com.assignment1.app.base;

import com.assignment1.app.exceptions.InvalidOperationException;

public abstract class WaterVehicle extends Vehicle {
  private boolean hasSail;

  public WaterVehicle(String id, String model, double maxSpeed, double currentMileage, boolean hasSail)
      throws InvalidOperationException {
    super(id, model, maxSpeed, currentMileage);
    this.hasSail = hasSail;
  }

  protected boolean getHasSail() {
    return hasSail;
  }

  @Override
  public double estimateJourneyTime(double distance) {
    double maxSpeed = getMaxSpeed();
    return (distance / maxSpeed) * 1.15;
  }
}
