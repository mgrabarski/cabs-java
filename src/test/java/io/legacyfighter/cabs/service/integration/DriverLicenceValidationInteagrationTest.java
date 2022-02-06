package io.legacyfighter.cabs.service.integration;

import io.legacyfighter.cabs.dto.DriverDTO;
import io.legacyfighter.cabs.entity.Driver;
import io.legacyfighter.cabs.service.DriverService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DriverLicenceValidationInteagrationTest {

    @Autowired
    private DriverService driverService;

    @Test
    void canNotCreateDriverWithInvalidDriverLicence() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> createActiveDriverWithLicence("invalidLicence"));
    }

    @Test
    void createsDriverWithValidDriverLicence() {
        Driver driver = createActiveDriverWithLicence("FARME100165AB5EW");

        DriverDTO driverDTO = driverService.loadDriver(driver.getId());
        assertEquals("FARME100165AB5EW", driverDTO.getDriverLicense());
        assertEquals(Driver.Status.ACTIVE, driverDTO.getStatus());
    }

    @Test
    void createsInactiveDriverWithInvalidLicense() {
        Driver driver = createInactiveDriverWithLicence("invalidLicence");

        DriverDTO driverDTO = driverService.loadDriver(driver.getId());
        assertEquals("invalidLicence", driverDTO.getDriverLicense());
        assertEquals(Driver.Status.INACTIVE, driverDTO.getStatus());
    }

    @Test
    void changesDriverLicenceToValidLicence() {
        Driver driver = createActiveDriverWithLicence("FARME100165AB5EW");

        driverService.changeLicenseNumber("99999740614992TL", driver.getId());

        DriverDTO driverDTO = driverService.loadDriver(driver.getId());
        assertEquals("99999740614992TL", driverDTO.getDriverLicense());
    }

    @Test
    void canNotChangeDriverLicenceForInvalid() {
        Driver driver = createActiveDriverWithLicence("FARME100165AB5EW");

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> driverService.changeLicenseNumber("invalidLicence", driver.getId()));
    }

    @Test
    void activatesDriverWithValidLicence() {
        Driver driver = createInactiveDriverWithLicence("FARME100165AB5EW");

        driverService.changeDriverStatus(driver.getId(), Driver.Status.ACTIVE);

        DriverDTO driverDTO = driverService.loadDriver(driver.getId());
        assertEquals(Driver.Status.ACTIVE, driverDTO.getStatus());
    }

    @Test
    void canNotActivateDriverWithInvalidLicence() {
        Driver driver = createInactiveDriverWithLicence("invalidDriver");

        assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> driverService.changeDriverStatus(driver.getId(), Driver.Status.ACTIVE));
    }

    private Driver createActiveDriverWithLicence(String licence) {
        return driverService.createDriver(licence, "last name", "first name", Driver.Type.REGULAR, Driver.Status.ACTIVE, "photo");
    }

    private Driver createInactiveDriverWithLicence(String licence) {
        return driverService.createDriver(licence, "last name", "first name", Driver.Type.REGULAR, Driver.Status.INACTIVE, "photo");
    }
}