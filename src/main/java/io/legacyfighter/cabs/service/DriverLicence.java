package io.legacyfighter.cabs.service;

import javax.persistence.Embeddable;

@Embeddable
public class DriverLicence {

    public DriverLicence() {
    }

    private static final String DRIVER_LICENSE_REGEX = "^[A-Z9]{5}\\d{6}[A-Z9]{2}\\d[A-Z]{2}$";

    private String driverLicence;

    private DriverLicence(String driverLicence) {
        this.driverLicence = driverLicence;
    }

    public static DriverLicence withLicence(String driverLicence) {
        if (driverLicence == null || driverLicence.isEmpty() || !driverLicence.matches(DRIVER_LICENSE_REGEX)) {
            throw new IllegalArgumentException("Illegal license no = " + driverLicence);
        }
        return new DriverLicence(driverLicence);
    }

    public static DriverLicence withoutValdation(String driverLicence) {
        return new DriverLicence(driverLicence);
    }

    public String asString() {
        return driverLicence;
    }
}