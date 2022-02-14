package io.legacyfighter.cabs.money;

import java.util.Locale;
import java.util.Objects;

public class Money {

    private Integer value;

    public Money(Integer value) {
        this.value = value;
    }

    public Money add(Money money) {
        return new Money(value + money.value);
    }

    public Money subtract(Money money) {
        return new Money(value - money.value);
    }

    public Money percentage(int percentage) {
        return new Money((int) Math.round(percentage * value/100.0));
    }

    public Integer toInt() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Objects.equals(value, money.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        double value = Double.valueOf(this.value) / 100;
        return String.format(Locale.US, "%.2f", value);
    }
}
