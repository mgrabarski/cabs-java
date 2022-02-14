package io.legacyfighter.cabs.money;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTest {

    @Test
    void canCreateMoneyFromInteger() {
        assertEquals("100.00", new Money(10000).toString());
        assertEquals("0.00", new Money(0).toString());
        assertEquals("10.12", new Money(1012).toString());
    }

    @Test
    void shouldProjectMoneyToInteger() {
        assertEquals(10000, new Money(10000).toInt());
        assertEquals(0, new Money(0).toInt());
        assertEquals(1012, new Money(1012).toInt());
    }

    @Test
    void canAddMoney() {
        assertEquals(100, new Money(50).add(new Money(50)).toInt());
        assertEquals(0, new Money(0).add(new Money(0)).toInt());
        assertEquals(-2, new Money(2).add(new Money(-4)).toInt());
    }

    @Test
    void canSubtractMoney() {
        assertEquals(0, new Money(50).subtract(new Money(50)).toInt());
        assertEquals(0, new Money(0).subtract(new Money(0)).toInt());
        assertEquals(6, new Money(2).subtract(new Money(-4)).toInt());
    }

    @Test
    void canCalculatePercentage() {
        assertEquals("30.00", new Money(10000).percentage(30).toString());
        assertEquals("26.40", new Money(8800).percentage(30).toString());
        assertEquals("88.00", new Money(8800).percentage(100).toString());
        assertEquals("0.00", new Money(8800).percentage(0).toString());
        assertEquals("13.20", new Money(4400).percentage(30).toString());
        assertEquals("0.30", new Money(100).percentage(30).toString());
        assertEquals("0.00", new Money(1).percentage(40).toString());
    }
}