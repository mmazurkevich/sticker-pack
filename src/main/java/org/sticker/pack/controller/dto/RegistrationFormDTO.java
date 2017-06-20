package org.sticker.pack.controller.dto;

import java.util.Objects;

public class RegistrationFormDTO {

    private String email;
    private String password;
    private String repassword;
    private String firstName;
    private String lastName;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepassword() {
        return repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegistrationFormDTO that = (RegistrationFormDTO) o;
        return Objects.equals(email, that.email) &&
                Objects.equals(password, that.password) &&
                Objects.equals(repassword, that.repassword) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password, repassword, firstName, lastName);
    }

    @Override
    public String toString() {
        return "RegistrationFormDTO{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", repassword='" + repassword + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}

