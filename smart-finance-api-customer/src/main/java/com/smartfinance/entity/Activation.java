package com.smartfinance.entity;

public interface Activation {
  void setActive(boolean active);

  default Activation activate() {
    setActive(true);
    return this;
  }

  default Activation deactivate() {
    setActive(false);
    return this;
  }
}
