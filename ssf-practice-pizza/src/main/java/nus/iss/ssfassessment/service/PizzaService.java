package nus.iss.ssfassessment.service;

import java.io.StringReader;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;
import nus.iss.ssfassessment.PizzaUtils;
import nus.iss.ssfassessment.model.Delivery;
import nus.iss.ssfassessment.model.Order;
import nus.iss.ssfassessment.model.OrderConfirmation;
import nus.iss.ssfassessment.repository.PizzaRepository;

@Service
public class PizzaService {

    @Autowired
    private PizzaRepository pizzaRepo;

    public boolean validPizza(String pizza) {
        for (String p : PizzaUtils.PIZZA) {
            if (p.equals(pizza)) {
                return true;
            }
        }
        return false;
    }
    public boolean validSize(String size) {
        for (String s : PizzaUtils.SIZE) {
            if (s.equals(size)) {
                return true;
            }
        }
        return false;
    }

    public String generateOrderId() {
        return UUID.randomUUID().toString();
    }

    public Float calculateOrder(Delivery delivery, Order order) {
        Float totalCost;
        Float pizzaCost = PizzaUtils.PIZZA_PRICE.get(order.getPizza());
        Integer quantity = Integer.parseInt(order.getQuantity());
        Float sizeMultiplier = PizzaUtils.SIZE_MULTIPLIER.get(order.getSize());
        totalCost = pizzaCost*quantity*sizeMultiplier;
        if (delivery.isRush()) {
            totalCost += PizzaUtils.OTHER_PRICE.get("rush");
        }
        return totalCost;
    }

    public JsonObject orderToJsonObject(OrderConfirmation orderConfirm) {
        String orderId = orderConfirm.getOrderId();
        String name = orderConfirm.getName();
        String address = orderConfirm.getAddress();
        String phone = orderConfirm.getPhone();
        String rush = Boolean.toString(orderConfirm.isRush());
        String comments = orderConfirm.getComments();
        String pizza = orderConfirm.getPizza();
        String size = orderConfirm.getSize();
        String quantity = orderConfirm.getQuantity();
        String total = orderConfirm.getTotal().toString();

        JsonObjectBuilder job = Json.createObjectBuilder()
                                    .add("orderId", orderId)
                                    .add("name", name)
                                    .add("address", address)
                                    .add("phone", phone)
                                    .add("rush", rush)
                                    .add("comments", comments)
                                    .add("pizza", pizza)
                                    .add("size", size)
                                    .add("quantity", quantity)
                                    .add("total", total);
        return job.build();
    }

    public OrderConfirmation jsonStringToOrder(String jsonString) {
        JsonReader reader = Json.createReader(new StringReader(jsonString));
        JsonObject obj = reader.readObject();

        String orderId = obj.getString("orderId");
        String name = obj.getString("name");
        String address = obj.getString("address");
        String phone = obj.getString("phone");
        String rush = obj.getString("rush");
        String comments = obj.getString("comments");
        String pizza = obj.getString("pizza");
        String size = obj.getString("size");
        String quantity = obj.getString("quantity");
        String total = obj.getString("total");

        return new OrderConfirmation(orderId, name, address, phone, Boolean.parseBoolean(rush), comments, pizza, size, quantity, Float.parseFloat(total));
    }

    public JsonObject jsonStringToObject(String jsonString) {
        JsonReader reader = Json.createReader(new StringReader(jsonString));
        return reader.readObject();
    }

    public void saveOrder(String orderId, JsonObject order) {
        pizzaRepo.saveOrder(orderId, order.toString());
    }

    public OrderConfirmation getOrder(String orderId) {
        String jsonString = pizzaRepo.getOrder(orderId).get();
        OrderConfirmation orderConfirm = jsonStringToOrder(jsonString);
        
        return orderConfirm;
    }

    public JsonObject getOrderObject(String orderId) {
        String jsonString = pizzaRepo.getOrder(orderId).get();
        return jsonStringToObject(jsonString);
    }

    public OrderConfirmation processOrder(Delivery delivery, Order order) {
        OrderConfirmation orderConfirm = new OrderConfirmation();

        orderConfirm.setOrderId(generateOrderId());
        orderConfirm.setName(delivery.getName());
        orderConfirm.setAddress(delivery.getAddress());
        orderConfirm.setPhone(delivery.getPhone());
        orderConfirm.setRush(delivery.isRush());
        orderConfirm.setComments(delivery.getComments());
        orderConfirm.setPizza(order.getPizza());
        orderConfirm.setSize(order.getSize());
        orderConfirm.setQuantity(order.getQuantity());
        orderConfirm.setTotal(calculateOrder(delivery, order));

        JsonObject orderObject = orderToJsonObject(orderConfirm);
        saveOrder(orderConfirm.getOrderId(), orderObject);

        return orderConfirm;
    }

}
