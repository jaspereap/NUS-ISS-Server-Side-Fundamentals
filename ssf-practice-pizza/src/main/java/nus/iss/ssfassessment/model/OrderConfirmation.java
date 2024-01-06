package nus.iss.ssfassessment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderConfirmation {
    private String orderId;
    private String name;
    private String address;
    private String phone;
    private boolean rush;
    private String comments;
    private String pizza;
    private String size;
    private String quantity;
    private Float total;
}
