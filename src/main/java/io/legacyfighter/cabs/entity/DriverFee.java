package io.legacyfighter.cabs.entity;

import io.legacyfighter.cabs.common.BaseEntity;
import io.legacyfighter.cabs.money.Money;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class DriverFee extends BaseEntity {

    public enum FeeType {
        FLAT, PERCENTAGE
    }

    public DriverFee() {

    }

    public DriverFee(FeeType feeType, Driver driver, Integer amount, Integer min) {
        this.feeType = feeType;
        this.driver = driver;
        this.amount = amount;
        this.min = min;
    }

    @Column(nullable = false)
    private FeeType feeType;

    @OneToOne
    private Driver driver;

    @Column(nullable = false)
    private Integer amount;

    private Integer min;

    public FeeType getFeeType() {
        return feeType;
    }

    public void setFeeType(FeeType feeType) {
        this.feeType = feeType;
    }

    Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public int calculateDriverFee(Money transitPrice) {
        Money finalFee;
        if (feeType.equals(FeeType.FLAT)) {
            finalFee = transitPrice.subtract(new Money(amount));
        } else {
            finalFee = transitPrice.percentage(amount);
        }
        return Math.max(finalFee.toInt(), min == null ? 0 : min);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof DriverFee))
            return false;

        DriverFee other = (DriverFee) o;

        return this.getId() != null &&
                this.getId().equals(other.getId());
    }
}
