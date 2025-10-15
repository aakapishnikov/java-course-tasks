package org.example;


public class Main {
    public static void main(String[] args) {
        ObservableStringBuilder sb = new ObservableStringBuilder();

        // Регистрируем двух наблюдателей
        // Можно было реализовать наблюдателей в виде классов имплементирующих StringBuilderObserver/onChange,
        // но в случае функционального интерфейса реализация будет более громоздкой.
        sb.addObserver((src, action) -> System.out.println("Событие: " + action + " | Значение: '" + src.toString() + "'"));
        sb.addObserver((src, action) -> {
            if (src.length() == 0) {
                System.out.println("Внимание: после операции " + action + " строка пустая");
            }
        });

        // Работаем с наблюдаемым объектом
        // присвоение значения как для объекта типа String (str = "test") работать не будет
        // т.к. String неизменяемый и при присвоении объекта "test по-сути меняется ссылка на объект в str
        // В нашем случае ObservableStringBuilder изменяемый, присвоение sb = "test" это попытка присвоить
        // объект "test" типа String в объект sb типа ObservableStringBuilder
        sb.append("Доброе");
        sb.append(' ').append("утро");
        sb.insert(7, "солнечное ");
        sb.replace(0, 6, "Прекрасное");
        sb.deleteCharAt(sb.length() - 1);
        sb.reverse();
        String str = new String();

        // Очистим строку, чтобы проверить работу второго наблюдателя
        sb.setLength(0);
    }
}