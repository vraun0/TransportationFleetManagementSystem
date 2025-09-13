package com.assignment1.app.interfaces;

import com.assignment1.app.exceptions.InsufficientFuelException;
import com.assignment1.app.exceptions.InvalidOperationException;
public interface FuelConsumable
{
  public void refuel(double amount) throws InvalidOperationException;
  public double getFuelLevel();
  public double consumeFuel(double distance) throws InsufficientFuelException;
}
