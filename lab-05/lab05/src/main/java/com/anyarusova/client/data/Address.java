package com.anyarusova.client.data;

import java.util.Objects;

public class Address {
    private final String street; //length < 121, not null
    private final String zipCode; //not null

    public Address(String street, String zipCode) {
        this.street = street;
        this.zipCode = zipCode;
    }

    @Override
    public String toString() {
        return "Address{"
                + "street='" + street + '\''
                + ", zipCode='" + zipCode + '\''
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Address address = (Address) o;
        return Objects.equals(street, address.street) && Objects.equals(zipCode, address.zipCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, zipCode);
    }
}
