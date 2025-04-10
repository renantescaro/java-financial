package com.tescaro.financial.enums;

public enum KindEnum {
  INPUT("Entrada"),
  OUTPUT("Saída");

  private final String description;

  KindEnum(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}
