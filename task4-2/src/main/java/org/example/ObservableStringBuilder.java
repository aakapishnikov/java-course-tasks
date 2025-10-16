package org.example;
/**
 * Занятие 4 Вариант 2
 * Напишите свой класс StringBuilder, с возможностью оповещения других объектов об изменении своего
 * состояния. Для этого делегируйте все методы стандартному StringBuilder, а в собственном классе
 * реализуйте шаблон проектирования «Наблюдатель».
 * Требования:
 * Данный класс предоставляет возможность поставить обработчик на событие изменения объекта,
 * и таким образом, уведомлять всех наблюдателей о изменении своего состояния
 * Добавить простейшую демонстрацию работы с данным классом
 * Простейшая демонстрация работы ObservableStringBuilder.
 */

import java.util.ArrayList;
import java.util.List;

/**
 * Обертка над стандартным StringBuilder с поддержкой паттерна «Наблюдатель».
 * Делегируем операции StringBuilder и уведомляем подписчиков о мутациях.
 */
public class ObservableStringBuilder implements CharSequence, Appendable {
    // Делегируемый экземпляр
    private final StringBuilder delegate;

    // Подписчики
    private final List<StringBuilderObserver> observers = new ArrayList<>();

    /**
     * Реализуем все варианты создания экземпляра StringBuilder для ObservableStringBuilder:
     * <p>
     * 1. Без входного параметра
     */
    public ObservableStringBuilder() {
        this.delegate = new StringBuilder();
    }

    /**
     * 2. С заданной емкостью
     */
    public ObservableStringBuilder(int capacity) {
        this.delegate = new StringBuilder(capacity);
    }

    /**
     * 3. С заданным начальным содержимым типа String
     * Изначально делал в Java 17, там String покрывался CharSequence
     * При переходе на Java 24 в StringBuilder/AbstractStringBuilder появилась
     * реализация для String. Судя по реализации в AbstractStringBuilder, с целью
     * оптимизации работы.
     */
    public ObservableStringBuilder(String str) {
        this.delegate = new StringBuilder(str);
    }

    /**
     * 4. С заданным начальным содержимым типа CharSequence
     */
    public ObservableStringBuilder(CharSequence cs) {
        this.delegate = new StringBuilder(cs);
    }

    /**
     * Добавить наблюдателя
     */
    public void addObserver(StringBuilderObserver observer) {
        /* Проверяем что не пытаемся повторно добавить одного и того же
           наблюдателя */
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    /**
     * Удалить наблюдателя
     */
    public void removeObserver(StringBuilderObserver observer) {
        observers.remove(observer);
    }

    /**
     * Уведомить наблюдателей о событии
     */
    private void notifyObservers(String action) {
        // защищаемся от изменения списка наблюдателей в момент работы for
        for (var o : List.copyOf(observers)) {
            try {
                o.onChange(this, action);
            } catch (RuntimeException _) {
                // игнорируем ошибки подписчиков, чтобы не ломать работу остальных
            }
        }
    }

    // Делегирование иммутабельных методов:

    /**
     * Длина
     */
    public int length() {
        return delegate.length();
    }

    /**
     * Емкость буфера
     */
    public int capacity() {
        return delegate.capacity();
    }

    /**
     * Символ по индексу
     */
    @Override // помечаем метод как перегруженный т.к. это метод интерфейса CharSequence
    public char charAt(int index) {
        return delegate.charAt(index);
    }

    /**
     * Подпоследовательность.
     */
    @Override // помечаем метод как перегруженный т.к. это метод интерфейса CharSequence
    public CharSequence subSequence(int start, int end) {
        return delegate.subSequence(start, end);
    }

    /**
     * Представление в строку.
     */
    @Override // помечаем метод как перегруженный т.к. это метод интерфейса CharSequence
    public String toString() {
        return delegate.toString();
    }

    // Мутации с уведомлениями

    /**
     * Выделить емкость не меньше указанной
     */
    public void ensureCapacity(int minimumCapacity) {
        delegate.ensureCapacity(minimumCapacity);
        notifyObservers("ensureCapacity");
    }

    /**
     * Уменьшить емкость до фактической длины
     */
    public void trimToSize() {
        delegate.trimToSize();
        notifyObservers("trimToSize");
    }

    /**
     * Установить длину
     */
    public void setLength(int newLength) {
        delegate.setLength(newLength);
        notifyObservers("setLength");
    }

    // append перегрузки
    @Override // помечаем метод как перегруженный т.к. это метод интерфейса Appendable
    public ObservableStringBuilder append(CharSequence csq) {
        delegate.append(csq);
        notifyObservers("append(CharSequence)");
        return this;
    }

    @Override // помечаем метод как перегруженный т.к. это метод интерфейса Appendable
    public ObservableStringBuilder append(CharSequence csq, int start, int end) {
        delegate.append(csq, start, end);
        notifyObservers("append(CharSequence,int,int)");
        return this;
    }

    @Override // помечаем метод как перегруженный т.к. это метод интерфейса Appendable
    public ObservableStringBuilder append(char c) {
        delegate.append(c);
        notifyObservers("append(char)");
        return this;
    }

    // Методы объявленные в StringBuilder:
    public ObservableStringBuilder append(Object obj) {
        delegate.append(obj);
        notifyObservers("append(Object)");
        return this;
    }

    public ObservableStringBuilder append(String str) {
        delegate.append(str);
        notifyObservers("append(String)");
        return this;
    }

    public ObservableStringBuilder append(boolean b) {
        delegate.append(b);
        notifyObservers("append(boolean)");
        return this;
    }

    public ObservableStringBuilder append(int i) {
        delegate.append(i);
        notifyObservers("append(int)");
        return this;
    }

    public ObservableStringBuilder append(long l) {
        delegate.append(l);
        notifyObservers("append(long)");
        return this;
    }

    public ObservableStringBuilder append(float f) {
        delegate.append(f);
        notifyObservers("append(float)");
        return this;
    }

    public ObservableStringBuilder append(double d) {
        delegate.append(d);
        notifyObservers("append(double)");
        return this;
    }

    public ObservableStringBuilder append(StringBuffer sb) {
        delegate.append(sb);
        notifyObservers("append(StringBuffer)");
        return this;
    }

    public ObservableStringBuilder append(char[] str) {
        delegate.append(str);
        notifyObservers("append(char[])");
        return this;
    }

    public ObservableStringBuilder append(char[] str, int offset, int len) {
        delegate.append(str, offset, len);
        notifyObservers("append(char[],int,int)");
        return this;
    }

    public ObservableStringBuilder appendCodePoint(int codePoint) {
        delegate.appendCodePoint(codePoint);
        notifyObservers("appendCodePoint");
        return this;
    }

    public ObservableStringBuilder insert(int dstOffset, Object obj) {
        delegate.insert(dstOffset, obj);
        notifyObservers("insert(Object)");
        return this;
    }

    public ObservableStringBuilder insert(int dstOffset, String str) {
        delegate.insert(dstOffset, str);
        notifyObservers("insert(String)");
        return this;
    }

    public ObservableStringBuilder insert(int dstOffset, char[] str) {
        delegate.insert(dstOffset, str);
        notifyObservers("insert(char[])");
        return this;
    }

    public ObservableStringBuilder insert(int index, char[] str, int offset, int len) {
        delegate.insert(index, str, offset, len);
        notifyObservers("insert(char[],int,int)");
        return this;
    }

    public ObservableStringBuilder insert(int dstOffset, CharSequence s) {
        delegate.insert(dstOffset, s);
        notifyObservers("insert(CharSequence)");
        return this;
    }

    public ObservableStringBuilder insert(int dstOffset, CharSequence s, int start, int end) {
        delegate.insert(dstOffset, s, start, end);
        notifyObservers("insert(CharSequence,int,int)");
        return this;
    }

    public ObservableStringBuilder insert(int dstOffset, boolean b) {
        delegate.insert(dstOffset, b);
        notifyObservers("insert(boolean)");
        return this;
    }

    public ObservableStringBuilder insert(int dstOffset, char c) {
        delegate.insert(dstOffset, c);
        notifyObservers("insert(char)");
        return this;
    }

    public ObservableStringBuilder insert(int dstOffset, int i) {
        delegate.insert(dstOffset, i);
        notifyObservers("insert(int)");
        return this;
    }

    public ObservableStringBuilder insert(int dstOffset, long l) {
        delegate.insert(dstOffset, l);
        notifyObservers("insert(long)");
        return this;
    }

    public ObservableStringBuilder insert(int dstOffset, float f) {
        delegate.insert(dstOffset, f);
        notifyObservers("insert(float)");
        return this;
    }

    public ObservableStringBuilder insert(int dstOffset, double d) {
        delegate.insert(dstOffset, d);
        notifyObservers("insert(double)");
        return this;
    }

    public ObservableStringBuilder replace(int start, int end, String str) {
        delegate.replace(start, end, str);
        notifyObservers("replace");
        return this;
    }

    public ObservableStringBuilder delete(int start, int end) {
        delegate.delete(start, end);
        notifyObservers("delete");
        return this;
    }

    public ObservableStringBuilder deleteCharAt(int index) {
        delegate.deleteCharAt(index);
        notifyObservers("deleteCharAt");
        return this;
    }

    public ObservableStringBuilder reverse() {
        delegate.reverse();
        notifyObservers("reverse");
        return this;
    }

    public void setCharAt(int index, char ch) {
        delegate.setCharAt(index, ch);
        notifyObservers("setCharAt");
    }

    // // методы не меняют состояние — уведомление не требуется
    public int indexOf(String str) {
        return delegate.indexOf(str);
    }

    public int indexOf(String str, int fromIndex) {
        return delegate.indexOf(str, fromIndex);
    }

    public int lastIndexOf(String str) {
        return delegate.lastIndexOf(str);
    }

    public int lastIndexOf(String str, int fromIndex) {
        return delegate.lastIndexOf(str, fromIndex);
    }

    public String substring(int start) {
        return delegate.substring(start);
    }

    public String substring(int start, int end) {
        return delegate.substring(start, end);
    }

    public void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin) {
        delegate.getChars(srcBegin, srcEnd, dst, dstBegin);
    }
}