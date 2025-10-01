package org.example;

import java.util.Objects;

public class JournalEntry {
    private final String lastName;
    private final String firstName;
    private final String birthDate;
    private final String phoneNumber;
    private final String address;

    public JournalEntry(String lastName, String firstName, String birthDate, String phoneNumber, String address) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Студент: " + lastName + " " + firstName +
                ", Дата народження: " + birthDate +
                ", Телефон: " + phoneNumber +
                ", Адреса: " + address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JournalEntry that)) return false;
        return Objects.equals(lastName, that.lastName) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(birthDate, that.birthDate) &&
                Objects.equals(phoneNumber, that.phoneNumber) &&
                Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lastName, firstName, birthDate, phoneNumber, address);
    }
}
