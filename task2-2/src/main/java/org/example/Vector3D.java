package org.example;
/*
Вариант 2 - Вектор
Создайте класс, который описывает вектор (в трёхмерном пространстве).
У него должны быть:
конструктор с параметрами в виде списка координат x, y, z
метод, вычисляющий длину вектора. Корень можно посчитать с помощью Math.sqrt()
метод, вычисляющий скалярное произведение
метод, вычисляющий векторное произведение с другим вектором
метод, вычисляющий угол между векторами (или косинус угла)
методы для суммы и разности
статический метод, который принимает целое число N, и возвращает массив случайных векторов
размером N.
Если метод возвращает вектор, то он должен возвращать новый объект, а не менять базовый. То есть,
вектор должен быть неизменяемым (immutable) объектом как String
 */

import java.util.Random;

/*
 * Абстрактный вектор в трехмерном пространстве - общая логика и контракт.
 * Наследники должны предоставить координаты.
 */
abstract class BaseVector3D {
    // Доступ к координатам через геттеры
    protected abstract double x();

    protected abstract double y();

    protected abstract double z();

    // Длина вектора
    public double length() {
        return Math.sqrt(x() * x() + y() * y() + z() * z());
    }

    // Скалярное произведение
    public double scalarProduct(BaseVector3D other) {
        return x() * other.x() + y() * other.y() + z() * other.z();
    }

    // Векторное произведение - результат новый вектор
    public Vector3D cross(BaseVector3D other) {
        double cx = y() * other.z() - z() * other.y();
        double cy = z() * other.x() - x() * other.z();
        double cz = x() * other.y() - y() * other.x();
        return new Vector3D(cx, cy, cz);
    }

    // Косинус угла между векторами
    public double angleCos(BaseVector3D other) {
        double length1 = this.length();
        double length2 = other.length();
        if (length1 == 0.0 || length2 == 0.0) {
            throw new IllegalArgumentException("Длины векторов должны быть отличны от нуля");
        }
        return scalarProduct(other) / (length1 * length2);
    }

    // Сумма - результат новый вектор
    public Vector3D add(BaseVector3D other) {
        return new Vector3D(x() + other.x(), y() + other.y(), z() + other.z());
    }

    // Разность - результат новый вектор
    public Vector3D subtract(BaseVector3D other) {
        return new Vector3D(x() - other.x(), y() - other.y(), z() - other.z());
    }

    // переопределение toString для удобного вывода значений объекта.
    // Не требуется реализовывать отдельный метод getValue() или склеивать значения отдельных координат.
    // В данном задании не требуется, но IRL имеет смысл переопределить также equals и hashCode.
    @Override
    public String toString() {
        return "(" + x() + ", " + y() + ", " + z() + ")";
    }
}

/*
 * Вектор в трехмерном пространстве.
 * Инкапсуляция координат, предоставление доступа к операциям,
 * реализация специфичного метода randomArray().
 */
public final class Vector3D extends BaseVector3D {
    private final double x;
    private final double y;
    private final double z;
    private static final Random rnd = new Random();

    // Конструктор
    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    // Геттеры для чтения координат
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    // Реализация абстрактных методов базового класса
    @Override
    protected double x() {
        return x;
    }

    @Override
    protected double y() {
        return y;
    }

    @Override
    protected double z() {
        return z;
    }

    // Статический метод, массив из N случайных векторов
    public static Vector3D[] randomArray(int n) {
        if (n < 0) throw new IllegalArgumentException("N должно быть >= 0");
        Vector3D[] vectors = new Vector3D[n];
        for (int i = 0; i < n; i++) {
            // Для красоты используем диапазон [-100; 100] и целочисленные значения
            double rx = rnd.nextInt(-100,100);
            double ry = rnd.nextInt(-100,100);
            double rz = rnd.nextInt(-100,100);
            vectors[i] = new Vector3D(rx, ry, rz);
        }
        return vectors;
    }
}
