/*
Задание:
Реализовать простой консольный калькулятор, который может выполнять следующие операции:
Сложение ( + )
Вычитание ( - )
Умножение ( * )
Деление ( / )
Дополнительно можно реализовать операции целочисленного деления ( // ), возведения в
степень ( ^ ) и остатка от деления ( % )
Требования:
Программа должна запрашивать у пользователя выражение с двумя операндами и типом
операции (+, -, * , / и т. д.)
Программа должна проверять корректность выражения (проверка в отдельной функции), иначе
попросить повторить ввод
Программа должна проверять корректность ввода данных: если при делении второй операнд
равен нулю, программа должна вывести подробное сообщение об ошибке
Расчет результатов операций должен производиться в отдельных функциях, которые будут
возвращать результат операции (например sum(a, b), divide(a, b) и т.д.)
Перед началом ввода программа должна вывести информацию о поддерживаемых операциях
Поясните работу программы в коде с помощью комментариев
Программа должна работать в цикле, запрашивая операции и числа, пока пользователь не введет
команду "exit" для выхода.
Пример:
Введите выражение:
> 15 + 29
Результат: 44
> 12 *
2 / 2
Неверное выражение. Введите еще раз:
> 12 / 0
Ошибка - деление на 0.
> 12 - 0
Результат: 12
> exit
Выход...
 */
package org.example;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Calculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Вывод краткой инструкции пользователя
        printGreetings();

        // Главный цикл программы. Выход через ввод 'exit'.
        while (true) {
            System.out.print("Введите выражение: ");
            //чтение выражения с удалением всех пробелов
            String input = scanner.nextLine().replaceAll("\\s+", "");

            // Выход из программы при вводе exit (case-insensitive)
            if ("exit".equalsIgnoreCase(input)) {
                System.out.println("Выход...");
                break;
            }

            // Проверка введенного выражения
            if (!isValidExpression(input)) {
                System.out.println("Неверное выражение. Введите еще раз:");
                continue;
            }

            // Парсинг и выполнение операции
            try {
                double result = calculateExpression(input);
                System.out.println("Результат: " + result);
            } catch (ArithmeticException e) {
                System.out.println(e.getMessage());
            }
        }
        scanner.close();
    }

    // Выводит приветственное сообщение с информацией о поддерживаемых операциях
    private static void printGreetings() {
        System.out.println("Консольный калькулятор");
        System.out.println("Поддерживаются операции с двумя операндами.");
        System.out.println("Допустимые операции:");
        System.out.println("+  - сложение");
        System.out.println("-  - вычитание");
        System.out.println("*  - умножение");
        System.out.println("/  - деление");
        System.out.println("// - целочисленное деление");
        System.out.println("^  - возведение в степень");
        System.out.println("%  - остаток от деления");
        System.out.println("Пример ввода, '34*7'");
        System.out.println("Для выхода введите 'exit'");
        System.out.println();
    }

    // Проверка корректности выражения
    private static boolean isValidExpression(String expression) {
        // Поиск оператора в выражении
        String operator = findOperator(expression);
        if (operator == null) {
            return false;
        }

        // Разделение выражения на операнды
        // Символ оператора экранируем через Pattern.quote()
        // Поддержка отрицательных значений операндов не реализована
        String[] parts = expression.split(Pattern.quote(operator), 2);

        // Проверка операндов
        try {
            Double.parseDouble(parts[0]);
            Double.parseDouble(parts[1]);
            return true;
        } catch (NumberFormatException _) {
            return false;
        }
    }

    // Находим операцию в выражении.
    private static String findOperator(String expression) {
        // Приоритет целочисленного деления задан на уровне справочника.
        // В противном случае операция // может быть воспринята как /.
        // Поддержка отрицательных операндов не реализована
        for (Operation op : Operation.values()) {
            if (expression.contains(op.getOperator())) {
                return op.getOperator();
            }
        }
        return null;
    }

    // Вычисление выражения
    private static double calculateExpression(String expression) {
        String operator = findOperator(expression);

        // Ситуация operator == null обработана ранее в isValidExpression
        // Поддержка отрицательных значений операндов не реализована
        assert operator != null;
        String[] parts = expression.split(Pattern.quote(operator), 2);

        double a = Double.parseDouble(parts[0]);
        double b = Double.parseDouble(parts[1]);

        // Запуск вычисления соответствующего оператору
        return switch (operator) {
            case "+" -> add(a, b);
            case "-" -> subtract(a, b);
            case "*" -> multiply(a, b);
            case "/" -> divide(a, b);
            case "//" -> integerDivide(a, b);
            case "^" -> power(a, b);
            case "%" -> modulus(a, b);
            default -> throw new IllegalArgumentException("Неподдерживаемая операция: " + operator);
        };
    }

    // Реализации математических операций
    private static double add(double a, double b) {
        return a + b;
    }

    private static double subtract(double a, double b) {
        return a - b;
    }

    private static double multiply(double a, double b) {
        return a * b;
    }

    private static double divide(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Ошибка - деление на 0.");
        }
        return a / b;
    }

    private static double integerDivide(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Ошибка - деление на 0.");
        }
        return Math.floor(a / b);
    }

    private static double power(double a, double b) {
        return Math.pow(a, b);
    }

    private static double modulus(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Ошибка - деление на 0.");
        }
        return a % b;
    }

    // справочник допустимых операций в выражениях
    // важно проверять // перед /
    private enum Operation {
        ADDITION("+"),
        SUBTRACTION("-"),
        MULTIPLICATION("*"),
        INTEGER_DIVISION("//"),
        DIVISION("/"),
        EXPONENTIATION("^"),
        MODULUS("%");

        private final String operator;

        Operation(String operator) {
            this.operator = operator;
        }

        public String getOperator() {
            return operator;
        }
    }
}