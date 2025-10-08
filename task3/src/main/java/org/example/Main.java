/**
 * Необходимо создать иерархию классов для описания нескольких видов транспорта. В иерархию
 * должны быть включены как минимум 4 конечных вида транспорта. (например, автомобиль, самолет,
 * корабль, велосипед и т. д.). Пользователь должен иметь возможность создавать объекты разных видов
 * транспорта, вызывать методы для каждого объекта и выводить информацию о транспорте на консоль.
 * В иерархию также могут входить такие сущности, как двигатель, топливо и т. д.
 * Требования:
 * Реализовать пользовательский интерфейс на свое усмотрение
 * Обязательно применение запечатанных (Sealed) классов и перечислений (Enum)
 * Обязательно корректное применение абстрактных классов или интерфейсов для реализации
 * иерархии
 * Создать UML-диаграмму на основе полученной иерархии, на которой будут указаны отношения
 * между классами (наследование, реализация, ассоциация)
 */

package org.example;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean run = true;
        System.out.println("=== Транспорт: демо иерархии (ООП) ===");

        while (run) {
            System.out.println("\nВыберите транспорт для создания:");
            System.out.println("1) Автомобиль\n2) Мотоцикл\n3) Самолет\n4) Катер\n5) Велосипед\n0) Выход");
            System.out.print("Ваш выбор: ");
            String choice = sc.nextLine().trim();
            Transport t = null;
            switch (choice) {
                case "1" -> t = new Car("CarModel", 4);
                case "2" -> t = new Motorcycle("MotoModel", 2);
                case "3" -> t = new Airplane("AirModel", 60);
                case "4" -> t = new Boat("BoatModel", 6);
                case "5" -> t = new Bicycle("BikeModel");
                case "0" -> run = false;
                default -> System.out.println("Неизвестная команда.");
            }
            if (t != null) System.out.println(t.info());
        }
        System.out.println("До встречи!");
    }
}
