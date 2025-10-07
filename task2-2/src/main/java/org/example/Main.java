package org.example;

public class Main {
    public static void main(String[] args) {
        // Создаём два вектора
        Vector3D v1 = new Vector3D(1, 2, 3);
        Vector3D v2 = new Vector3D(4, -5, 6);

        System.out.println("v1 = " + v1);
        System.out.println("v2 = " + v2);

        // Длина векторов
        System.out.println("Длина v1 = " + v1.length());
        System.out.println("Длина v2 = " + v2.length());

        // Скалярное произведение
        System.out.println("Скалярное произведение v1·v2 = " + v1.scalarProduct(v2));

        // Векторное произведение
        System.out.println("Векторное произведение v1×v2 = " + v1.cross(v2));

        // Косинус угла
        System.out.println("cos(угла) между v1 и v2 = " + v1.angleCos(v2));

        // Сумма и разность
        System.out.println("v1 + v2 = " + v1.add(v2));
        System.out.println("v1 - v2 = " + v1.subtract(v2));

        // Массив случайных векторов
        Vector3D[] randomVectors = Vector3D.randomArray(3);
        System.out.println("Случайные векторы:");
        for (Vector3D v : randomVectors) {
            System.out.println("  " + v);
        }
    }
}