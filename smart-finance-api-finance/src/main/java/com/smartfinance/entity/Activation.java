package com.smartfinance.entity;

public interface Activation {
  void setActive(boolean active);

  default void activate() {
    setActive(true);
  }

  default void deactivate() {
    setActive(false);
  }
}
