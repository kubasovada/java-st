package ru.stqa.sandbox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Collections {
  public static void main(String[] args) {
    // Массив
    String[] langs = {"Java", "C#", "Python", "PHP"};

    // Коллекция Список. Слева интерфейс, справа - конкретный класс
    List<String> languages = new ArrayList<String>();
    languages.add("Java");
    languages.add("C#");
    languages.add("Python");

    List<String> lans = Arrays.asList("Java", "C#", "Python", "PHP");

    for (String l : languages) {
      System.out.println("Я хочу выучить " + l);
    }
    for (int i = 0; i < lans.size(); i++) {
      System.out.println("Я хочу выучить " + languages.get(i));
    }
  }
}
