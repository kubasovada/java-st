package ru.stqa.sandbox;

import java.sql.SQLOutput;

public class MyFirstProgram {

    public static void main(String[] args) {

        Point p1 = new Point(6, 2);
        Point p2 = new Point(9, 6);
        System.out.println("Вариант 3 при помощи метода");
        System.out.println("Расстояние между двумя точками с координатами " + "("+p1.x+";"+ p1.y +")"+ " и " + "("+ p2.x + ";" + p2.y + ")" + " = " + p1.distance(p2));

//        Square s = new Square(5);
//        System.out.println("Площадь квадрата со стороной " + s.l + " = " + s.area());
//
//        Rectangle r = new Rectangle(4,6);
//        System.out.println("Площадь прямоугольника со сторонами " + r.a + " и " + r.b + " = " + r.area());
    }

}