package com.assignment1.app.interfaces;

import com.assignment1.app.exceptions.InvalidOperationException;
import com.assignment1.app.exceptions.OverloadException;;
public interface PassengerCarrier
{
  public void boardPassengers(int count) throws OverloadException;
  public void disembarkPassengers(int count) throws InvalidOperationException;
  public int getPassengerCapacity();
  public int getCurrentPassengers();
}
