package org.example;

import java.util.EventListener;

/**
 * Наблюдатель за изменениями ObservableStringBuilder.
 */

// Т.к. у нас реализован только один метод onChange помечаем интерфейс
// как функциональный. Это позволит нам в Main использовать лямбды:
//      sb.addObserver((src, action) -> System.out.println("Событие: " + action + " | Текущее значение: '" + src.toString() + "'"));
//      sb.addObserver((src, action) -> {...}

@FunctionalInterface
public interface StringBuilderObserver extends EventListener {
    /**
     * Вызывается при изменении состояния StringBuilder.
     * @param src   источник события
     * @param action событие (append, delete и т.п.)
     */
    void onChange(ObservableStringBuilder src, String action);
}
