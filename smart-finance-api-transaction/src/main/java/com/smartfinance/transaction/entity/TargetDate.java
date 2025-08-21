package com.smartfinance.transaction.entity;

public interface TargetDate {
  int getYear();
  int getMonth();

  default int getTarget() {
    String base = getYear() + "" +
        (getMonth() < 10 ? "0" + getMonth() : getMonth());
    return Integer.parseInt(base);
  }
}
