package io.legacyfighter.cabs.service.integration;

import io.legacyfighter.cabs.entity.Driver;
import io.legacyfighter.cabs.entity.DriverFee;
import io.legacyfighter.cabs.entity.DriverFee.FeeType;
import io.legacyfighter.cabs.entity.Transit;
import io.legacyfighter.cabs.money.Money;
import io.legacyfighter.cabs.repository.DriverFeeRepository;
import io.legacyfighter.cabs.repository.TransitRepository;
import io.legacyfighter.cabs.service.DriverFeeService;
import io.legacyfighter.cabs.service.DriverService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CalculateDriverFeeServiceTest {

    @Autowired
    DriverFeeService driverFeeService;

    @Autowired
    DriverFeeRepository feeRepository;

    @Autowired
    TransitRepository transitRepository;

    @Autowired
    DriverService driverService;

    @Test
    void shouldCalculateDriverFlatFee() {
        Driver driver = aDriver();
        Transit transit = aTransit(driver, 60);
        driverHasFee(driver, FeeType.FLAT, 10);

        Integer result = driverFeeService.calculateDriverFee(transit.getId());

        assertEquals(50, result);
    }

    @Test
    void shouldCalculateDriverPercentageFee() {
        Driver driver = aDriver();
        Transit transit = aTransit(driver, 60);
        driverHasFee(driver, FeeType.PERCENTAGE, 10);

        Integer result = driverFeeService.calculateDriverFee(transit.getId());

        assertEquals(6, result);
    }

    @Test
    void shouldUseMinFee() {
        Driver driver = aDriver();
        Transit transit = aTransit(driver, 60);
        driverHasFee(driver, FeeType.PERCENTAGE, 7, 5);

        Integer result = driverFeeService.calculateDriverFee(transit.getId());

        assertEquals(5, result);
    }

    DriverFee driverHasFee(Driver driver, FeeType feeType, int amount, Integer min) {
        DriverFee driverFee = new DriverFee();
        driverFee.setDriver(driver);
        driverFee.setAmount(amount);
        driverFee.setFeeType(feeType);
        driverFee.setMin(min);
        return feeRepository.save(driverFee);
    }

    DriverFee driverHasFee(Driver driver, FeeType feeType, int amount) {
        return driverHasFee(driver, feeType, amount, 0);
    }

    Driver aDriver() {
        return driverService.createDriver("FARME100165AB5EW", "Kowalsi", "Janusz", Driver.Type.REGULAR, Driver.Status.ACTIVE, "");
    }

    Transit aTransit(Driver driver, Integer price) {
        Transit transit = new Transit();
        transit.setPrice(new Money(price));
        transit.setDriver(driver);
        transit.setDateTime(LocalDate.of(2020, 10, 20).atStartOfDay().toInstant(ZoneOffset.UTC));
        return transitRepository.save(transit);
    }
}