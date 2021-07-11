package ru.stqa.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

import static ru.stqa.sandbox.MyFirstProgram.distance;

public class PointTests {

  @Test
  public void testDistance() {
    Point p1 = new Point(6, 2);
    Point p2 = new Point(9, 6);
    Assert.assertEquals(distance(p1, p2), 5);
  }

@Test
  public void testDistanceWrong() {
    Point p1 = new Point(6, 2);
    Point p2 = new Point(9, 6);
    Assert.assertEquals(distance(p1, p2), 25);
  }
@Test
  public void testDistance3() {
    Point a = new Point(6,2);
    Point b = new Point(8,6);
    Assert.assertEquals(distance(a,b), 4.47213595499958);
}

}
