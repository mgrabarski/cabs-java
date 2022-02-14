package io.legacyfighter.cabs.entity;

import io.legacyfighter.cabs.money.Money;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class CalculateTransitPriceTest {

    @Test
    void canNotCalculateTransitWhenItsNotCompleted() {
        Transit transit = new Transit();
        transit.setStatus(Transit.Status.IN_TRANSIT);

        assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(transit::calculateFinalCosts);
    }

    @Test
    void canNotEstimateCostWhenTransitIsCompleted() {
        Transit transit = new Transit();
        transit.setStatus(Transit.Status.COMPLETED);

        assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(transit::estimateCost);
    }

    @Test
    void calculatesCostForRegularDay() {
        Transit transit = new Transit();
        transit.setStatus(Transit.Status.COMPLETED);
        transit.setDateTime(LocalDate.of(2022, 2, 14).atStartOfDay().toInstant(ZoneOffset.UTC));

        Money result = transit.calculateFinalCosts();

        assertThat(result).isEqualTo(new Money(900));
    }

    @Test
    void calculatesCostForWeekendDay() {
        Transit transit = new Transit();
        transit.setStatus(Transit.Status.COMPLETED);
        transit.setDateTime(LocalDate.of(2022, 2, 13).atStartOfDay().toInstant(ZoneOffset.UTC));

        Money result = transit.calculateFinalCosts();

        assertThat(result).isEqualTo(new Money(1000));
    }

    @Test
    void calculateEstimateCostForRegularDay() {
        Transit transit = new Transit();
        transit.setStatus(Transit.Status.DRAFT);
        transit.setDateTime(LocalDate.of(2022, 2, 14).atStartOfDay().toInstant(ZoneOffset.UTC));

        Integer result = transit.estimateCost();

        assertThat(result).isEqualTo(900);
    }

    @Test
    void calculateEstimateCostForWeekendDay() {
        Transit transit = new Transit();
        transit.setStatus(Transit.Status.DRAFT);
        transit.setDateTime(LocalDate.of(2022, 2, 13).atStartOfDay().toInstant(ZoneOffset.UTC));

        Integer result = transit.estimateCost();

        assertThat(result).isEqualTo(1000);
    }
}