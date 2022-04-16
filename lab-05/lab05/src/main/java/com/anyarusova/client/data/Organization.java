package com.anyarusova.client.data;


import com.anyarusova.client.utility.CollectionManager;

import java.util.Objects;


public class Organization implements Comparable<Organization> {

    private int id; // >0, unique, automatic generation
    private final String name; //not null, not empty
    private final Coordinates coordinates; //not null
    private final java.time.ZonedDateTime creationDate; //not null, automatic generation
    private final long annualTurnover; //> 0
    private final Long employeesCount; //not null, > 0
    private final OrganizationType type; //not null
    private final Address postalAddress; //not null

    public Organization(String name,
                        Coordinates coordinates,
                        long annualTurnover,
                        Long employeesCount,
                        OrganizationType type,
                        Address postalAddress,
                        CollectionManager collectionManager) {
        this.name = name;
        this.coordinates = coordinates;
        this.annualTurnover = annualTurnover;
        this.employeesCount = employeesCount;
        this.type = type;
        this.postalAddress = postalAddress;
        this.creationDate = java.time.ZonedDateTime.now();
        this.id = collectionManager.getMaxId() + 1;
    }

    @Override
    public String toString() {
        return "Organization{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", coordinates=" + coordinates
                + ", creationDate=" + creationDate
                + ", annualTurnover=" + annualTurnover
                + ", employeesCount=" + employeesCount
                + ", type=" + type
                + ", postalAddress=" + postalAddress
                + '}';
    }

    public Long getEmployeesCount() {
        return employeesCount;
    }

    public OrganizationType getType() {
        return type;
    }

    public long getAnnualTurnover() {
        return annualTurnover;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Organization o) {
        long oValue = o.getAnnualTurnover();
        long thisValue = this.getAnnualTurnover();

        if (oValue - thisValue != 0) {
            return -Long.compare(thisValue, oValue);
        } else {
            return this.getId() - o.getId();
        }
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Organization that = (Organization) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, annualTurnover, employeesCount, type, postalAddress);
    }
}
