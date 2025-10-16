package org.example;

import java.util.Scanner;

/**
 * Переработанный калькулятор с поддержкой операций [+, -, *, /, //, ^, %] и поддержкой отрицательных чисел.
 * Программа запрашивает у пользователя ввод выражений в формате "число операция число".
 * Работает в цикле до ввода "exit".
 */
public class CalculatorOOP {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Calculator calculator = new Calculator();
        ExpressionValidator validator = new ExpressionValidator();

        // Выводим информацию о поддерживаемых операциях
        System.out.println("Простой калькулятор. Поддерживаемые операции:");
        System.out.println("+  -  *  /  //  ^  %");
        System.out.println("Введите выражение (например: -15 + -29) или 'exit' для выхода.");

        while (true) {
            System.out.print("> ");
            String line = in.nextLine().trim();

            if (line.equalsIgnoreCase("exit")) {
                System.out.println("Выход...");
                break;
            }

            try {
                // Валидируем и строим Operation
                Operation op = validator.parse(line);
                // Вычисляем через объект калькулятора
                double result = calculator.calculate(op);
                // Вывод (отсекаем .0 при целочисленном результате)
                System.out.println("Результат: " + formatNumber(result));
            } catch (ValidationException ve) {
                System.out.println("Неверное выражение. Введите еще раз:");
            } catch (ArithmeticException ae) {
                System.out.println(ae.getMessage()); // Ошибка деления на 0 или другие арифметические ошибки
            } catch (Exception e) {
                System.out.println("Непредвиденная ошибка: " + e.getMessage());
            }
        }
    }

    private static String formatNumber(double x) {
        // Если x целое (например, 5.0) — отсекаем дробную часть и
        // возвращаем (для нашего примера вернем 5)
        if (Math.abs(x - Math.rint(x)) < 1e-12) {
            return String.valueOf((long) Math.rint(x));
        }
        return String.valueOf(x);
    }
}

/**
 * Класс калькулятора, который выполняет операции.
 */
class Calculator {
    public double calculate(Operation op) {
        return op.compute();
    }
}

/**
 * Абстрактный класс для всех операций.
 */
abstract class Operation {
    protected final double left;
    protected final double right;
    protected final String symbol;

    protected Operation(double left, double right, String symbol) {
        this.left = left;
        this.right = right;
        this.symbol = symbol;
    }

    /**
     * Непосредственное вычисление результата.
     */
    public abstract double compute();
}

/**
 * Операция сложения.
 */
class Addition extends Operation {
    public Addition(double l, double r) {
        super(l, r, "+");
    }

    public double compute() {
        return left + right;
    }
}

/**
 * Операция вычитания.
 */
class Subtraction extends Operation {
    public Subtraction(double l, double r) {
        super(l, r, "-");
    }

    public double compute() {
        return left - right;
    }
}

/**
 * Операция умножения.
 */
class Multiplication extends Operation {
    public Multiplication(double l, double r) {
        super(l, r, "*");
    }

    public double compute() {
        return left * right;
    }
}

/**
 * Операция деления.
 */
class Division extends Operation {
    public Division(double l, double r) {
        super(l, r, "/");
    }

    public double compute() {
        if (Math.abs(right) < 1e-15) {
            throw new ArithmeticException("Ошибка - деление на 0.");
        }
        return left / right;
    }
}

/**
 * Целочисленное деление (//).
 */
class IntDivision extends Operation {
    public IntDivision(double l, double r) {
        super(l, r, "//");
    }

    public double compute() {
        long a = toLongExact(left, "Для // требуется целое число слева.");
        long b = toLongExact(right, "Для // требуется целое число справа.");
        if (b == 0) throw new ArithmeticException("Ошибка - целочисленное деление на 0.");
        return a / b;
    }

    private static long toLongExact(double x, String err) {
        if (Math.abs(x - Math.rint(x)) < 1e-12) return (long) Math.rint(x);
        throw new ArithmeticException(err + " Получено: " + x);
    }
}

/**
 * Операция возведения в степень.
 */
class Power extends Operation {
    public Power(double l, double r) {
        super(l, r, "^");
    }

    public double compute() {
        return Math.pow(left, right);
    }
}

/**
 * Операция остатка от деления.
 */
class Modulus extends Operation {
    public Modulus(double l, double r) {
        super(l, r, "%");
    }

    public double compute() {
        long a = toLongExact(left, "Для % требуется целое число слева.");
        long b = toLongExact(right, "Для % требуется целое число справа.");
        if (b == 0) throw new ArithmeticException("Ошибка - остаток от деления на 0.");
        return a % b;
    }

    private static long toLongExact(double x, String err) {
        if (Math.abs(x - Math.rint(x)) < 1e-12) return (long) Math.rint(x);
        throw new ArithmeticException(err + " Получено: " + x);
    }
}

/**
 * Исключение для ошибок валидации выражения.
 */
class ValidationException extends RuntimeException {
    public ValidationException(String msg) {
        super(msg);
    }
}

/**
 * Класс для валидации ввода и разбора выражений.
 */
class ExpressionValidator {
    public Operation parse(String input) {
        if (input == null || input.isEmpty()) {
            throw new ValidationException("Пустая строка.");
        }

        String[] parts = splitIntoThree(input.trim());
        if (parts.length != 3) {
            throw new ValidationException("Неверный формат. Ожидается: <число> <оператор> <число>");
        }

        String leftStr = parts[0];
        String opStr = parts[1];
        String rightStr = parts[2];

        double left = toNumber(leftStr);
        double right = toNumber(rightStr);

        return switch (opStr) {
            case "+" -> new Addition(left, right);
            case "-" -> new Subtraction(left, right);
            case "*" -> new Multiplication(left, right);
            case "/" -> new Division(left, right);
            case "//" -> new IntDivision(left, right);
            case "^" -> new Power(left, right);
            case "%" -> new Modulus(left, right);
            default -> throw new ValidationException("Неподдерживаемый оператор: " + opStr);
        };
    }

    private double toNumber(String s) {
        try {
            return Double.parseDouble(s.replace(',', '.'));
        } catch (NumberFormatException e) {
            throw new ValidationException("Не число: " + s);
        }
    }

    /**
     * Разделяем выражение на 3 части: левое число, оператор, правое число.
     */
    private String[] splitIntoThree(String s) {
        String[] parts = s.split("\\s+"); // делим выражение по пробелам
        if (parts.length == 3) {                // если сразу удалось получить 3 значения, возвращаем без валидации
            return parts;
        }

        // Разделяем выражения на операнды и операцию через поиск в выражении операции.
        // Добавлена поддержка отрицательных чисел.
        // Операцию "//" ищем первой, чтобы не перепутать с "/"
        // Не учитываем операцию если нашли ее в первом символе выражения (отрицательное число)

        String[] ops = {"//", "+", "-", "*", "/", "^", "%"};
        for (String op : ops) {
            int idx = s.indexOf(op);
            if (idx > 0) {
                String left = s.substring(0, idx).trim();
                String right = s.substring(idx + op.length()).trim();

                try {
                    if (!left.isEmpty() && !right.isEmpty()) {
                        toNumber(left);
                        toNumber(left);
                        return new String[]{left, op, right};
                    }
                } catch (ValidationException ve) {
                   // Обработка ситуации типа 10/-2.
                   // Без попытки toNumber() получим
                   // left = "10/" right = "2" op = "-"
                }
            }
        }
        return parts; // Вернется массив длины != 3, вызвав ошибку формата
    }
}
