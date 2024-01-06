package nus.iss.ssfassessment.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Order {
    @NotNull(message = "Must select a pizza")
    private String pizza;
    @NotNull(message = "Must select a size")
    private String size;
    @NotNull(message = "Must select a quantity")
    @Min(value = 1)
    @Max(value = 10)
    private String quantity;
}
