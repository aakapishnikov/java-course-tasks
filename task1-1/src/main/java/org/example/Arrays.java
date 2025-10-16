package org.example;
/*
Вариант 1 - Работа с массивом
Заполните массив случайным числами и выведите максимальное, минимальное и среднее значение.
Для генерации случайного числа используйте метод Math.random(). Также реализовать сортировку
массива по возрастанию / убыванию любым алгоритмом.
Требования:
Необходимо использовать отдельные функции для подсчета каждого значения и сортировки
Написать перегрузки функций для массива целочисленных и дробных значений
Размер должен вводиться с консоли (смотри класс Scanner и его методы считывания с консоли).
Если он 0 или меньше должно появляться сообщение об ошибке
Пользователь может ввести границы генерации случайных чисел
*/

import java.util.Scanner;

public class Arrays {
    public static void main(String[] args) {
        //для консольного ввода Scanner не очень удобен из-за характерных проблем с буфером.
        //Рассмотрел вариант использования hasNextInt()+nextInt() с последующим nextLine()
        //и вариант с чтением всей строки с самостоятельным разбором на токены.
        //Второй вариант показался более удобным т.к. самоятельно определяется пользовательский контракт.
        //Исходя из прочитанного в ходе выполнения, предпочтительным выглядит вариант использования
        // BufferedReader/InputStreamReader
        try (Scanner scanner = new Scanner(System.in)) {
            int arraySize = getArraySize(scanner);
            boolean isInteger = getValueType(scanner);
            double[] bounds = getValueBounds(scanner, isInteger);

            if (isInteger) {
                int[] intArray = generateIntArray(arraySize, (int) bounds[0], (int) bounds[1]);
                System.out.println("Массив:" + java.util.Arrays.toString(intArray));
                System.out.println("Максимальное значение: " + getMax(intArray));
                System.out.println("Минимальное значение: " + getMin(intArray));
                System.out.println("Среднее значение: " + getAverage(intArray));
                System.out.println("Массив отсортированный по возрастанию:" + java.util.Arrays.toString(sortAscending(intArray)));
                System.out.println("Массив отсортированный по убыванию:" + java.util.Arrays.toString(sortDescending(intArray)));
            } else {
                double[] doubleArray = generateDoubleArray(arraySize, bounds[0], bounds[1]);
                System.out.println("Массив:" + java.util.Arrays.toString(doubleArray));
                System.out.println("Максимальное значение: " + getMax(doubleArray));
                System.out.println("Минимальное значение: " + getMin(doubleArray));
                System.out.println("Среднее значение: " + getAverage(doubleArray));
                System.out.println("Массив отсортированный по возрастанию:" + java.util.Arrays.toString(sortAscending(doubleArray)));
                System.out.println("Массив отсортированный по убыванию:" + java.util.Arrays.toString(sortDescending(doubleArray)));
            }
        } catch (RuntimeException e) {
            System.err.println("Произошла ошибка: " + e.getMessage());
        }
    }

    private static int getArraySize(Scanner scanner) {
        System.out.println("Введите размер массива (положительное целочисленное значение): ");
        while (true) {
            if (!scanner.hasNextInt()) {
                System.out.println("Ошибка: введите положительное целое число в диапазоне от 1 до " + Integer.MAX_VALUE);
                scanner.nextLine();
                continue;
            }
            int size = scanner.nextInt();
            scanner.nextLine();
            if (size <= 0) {
                System.out.println("Ошибка: введите положительное целое число в диапазоне от 1 до " + Integer.MAX_VALUE);
                continue;
            }
            return size;
        }
    }

    private static boolean getValueType(Scanner scanner) {
        System.out.println("Выберите тип значений массива (1 - целые, 2 - вещественные): ");
        while (true) {
            String input = scanner.nextLine();
            if (!input.equals("1") && !input.equals("2")) {
                System.out.println("Ошибка: введите значение 1 или 2");
                continue;
            }
            return input.equals("1");
        }
    }

    private static double[] getValueBounds(Scanner scanner, boolean isInteger) {
        double defaultLower, defaultUpper, lower, upper;

        System.out.println("Введите через пробел нижнюю и верхнюю границы генерации случайных чисел");
        if (isInteger) {
            defaultLower = Integer.MIN_VALUE;
            defaultUpper = Integer.MAX_VALUE;
            System.out.println("Например, -145 170");
        } else {
            defaultLower = Double.MIN_VALUE;
            defaultUpper = Double.MAX_VALUE;
            System.out.println("Например, -145.78 135.56");
        }

        while (true) {
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                return new double[]{defaultLower, defaultUpper};
            }

            String[] parts = input.split("\\s+"); //Учтем всевозможные пробелы

            if (parts.length != 2) {
                System.out.println("Ошибка: требуется ровно два значения");
                continue;
            }

            try {
                if (isInteger) {
                    lower = Integer.parseInt(parts[0]);
                    upper = Integer.parseInt(parts[1]);
                } else {
                    lower = Double.parseDouble(parts[0]);
                    upper = Double.parseDouble(parts[1]);
                }

                if (lower > upper) {
                    System.out.println("Ошибка: первое число должно быть меньше либо равно второму");
                    continue;
                }

                return new double[]{lower, upper};

            } catch (NumberFormatException _) {
                System.out.println("Ошибка: некорректный формат чисел");
            }
        }
    }

    private static int[] generateIntArray(int size, double lower, double upper) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = (int) (Math.random() * (upper - lower + 1.0)) + (int) lower;
        }
        return array;
    }

    private static double[] generateDoubleArray(int size, double lower, double upper) {
        double[] array = new double[size];
        for (int i = 0; i < size; i++) {
            array[i] = Math.random() * (upper - lower) + lower;
        }
        return array;
    }

    private static int getMax(int[] array) {
        // IRL для методов getMax, getMin, getAverage надо обрабатывать
        // ситуацию с пустым array[] т.к. возврат нуля вводит в заблуждение
        // в данном случае решил не утяжелять реализацию обработкой
        // невозможной ситуации.
        return java.util.Arrays.stream(array).max().orElse(0);
    }

    private static double getMax(double[] array) {
        return java.util.Arrays.stream(array).max().orElse(0.0);
    }

    private static int getMin(int[] array) {
        return java.util.Arrays.stream(array).min().orElse(0);
    }

    private static double getMin(double[] array) {
        return java.util.Arrays.stream(array).min().orElse(0.0);
    }

    private static double getAverage(int[] array) {
        return java.util.Arrays.stream(array).average().orElse(0.0);
    }

    private static double getAverage(double[] array) {
        return java.util.Arrays.stream(array).average().orElse(0.0);
    }

    private static int[] sortAscending(int[] array) {
        // здесь и далее можно было использовать void, но хотим сохранить
        // единообразие вызова методов
        java.util.Arrays.sort(array);
        return array;
    }

    private static double[] sortAscending(double[] array) {
        java.util.Arrays.sort(array);
        return array;
    }

    private static int[] sortDescending(int[] array) {
        // Используем встроенную в Arrays сортировку
        // Изменяем порядок на обратный меняя по по два элемента за проход
        java.util.Arrays.sort(array);
        for (int i = 0; i < array.length / 2; i++) {
            int temp = array[i];
            array[i] = array[array.length - 1 - i];
            array[array.length - 1 - i] = temp;
        }
        return array;
    }

    private static double[] sortDescending(double[] array) {
        java.util.Arrays.sort(array);
        for (int i = 0; i < array.length / 2; i++) {
            double temp = array[i];
            array[i] = array[array.length - 1 - i];
            array[array.length - 1 - i] = temp;
        }
        return array;
    }
}