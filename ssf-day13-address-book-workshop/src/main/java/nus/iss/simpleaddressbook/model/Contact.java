package nus.iss.simpleaddressbook.model;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

public class Contact {
    @NotBlank(message="Must input a name")
    @Size(min=2, max=64, message="Length not satisfied")
    private String name;
    @NotBlank(message="Must input an email")
    @Email
    private String email;
    @NotBlank(message="Must input a number")
    @Size(min=7, message="At least 7 digits")
    private String phone;
    @Past
    @NotNull(message="Must input a date")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dob;
    
    private String hexString;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getHexString() {
        return hexString;
    }
    public void setHexString(String hexString) {
        this.hexString = hexString;
    }
    public LocalDate getDob() {
        return dob;
    }
    public void setDob(LocalDate dob) {
        this.dob = dob;
    }
    
}
