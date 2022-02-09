package io.legacyfighter.cabs.service;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DriverLicenceTest {

    @Test
    void canCreateDriveLicenceWithValidLicence() {
        DriverLicence driverLicence = DriverLicence.withLicence("FARME100165AB5EW");

        assertEquals("FARME100165AB5EW", driverLicence.asString());
    }

    @Test
    void canNotCreateDriverLicenceWithInvalidLicence() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> DriverLicence.withLicence("invalidLicence"));
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> DriverLicence.withLicence(""));
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> DriverLicence.withLicence(null));
    }

    @Test
    void canCreateDriverLicenceWithoutValidation() {
        DriverLicence driverLicence = DriverLicence.withoutValdation("invalidLicence");

        assertEquals("invalidLicence", driverLicence.asString());
    }
}