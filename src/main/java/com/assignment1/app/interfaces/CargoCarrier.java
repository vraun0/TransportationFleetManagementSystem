package com.assignment1.app.interfaces;

import com.assignment1.app.exceptions.InvalidOperationException;
import com.assignment1.app.exceptions.OverloadException;

public interface CargoCarrier {

  public void loadCargo(double weight) throws OverloadException;

  public void unloadCargo(double weight) throws InvalidOperationException;

  public double getCargoCapacity();

  public double getCurrentCargo();
}
