package nus.iss.ssfassessment.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Delivery {
    @NotNull(message = "Name is mandatory")
    @NotBlank(message = "Name is mandatory")
    // @Size(min=3, message = "Min. 3 characters")
    private String name;

    @NotNull(message = "Address is mandatory")
    @NotBlank(message = "Address is mandatory")
    private String address;

    @NotNull(message = "Phone number is mandatory")
    @NotBlank(message = "Phone number is mandatory")
    @Size(min = 8, max = 8)
    private String phone;

    private boolean rush;

    private String comments;

    private String pizza;
    private String size;
    private String quantity;
}
