package ru.stqa.sandbox;

public class Point {
  public double x;
  public double y;

  // конструктор
  public Point (double x, double y) {
    this.x = x;
    this.y = y;
  }

  // метод
  public double distance (double x, double y) {
    return Math.sqrt(Math.pow((this.x-x), 2) + Math.pow((this.y-y), 2));
  }
}




