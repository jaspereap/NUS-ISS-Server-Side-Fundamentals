package nus.iss.ssfassessment.controller;

import javax.naming.Binding;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.json.JsonObject;
import jakarta.validation.Valid;
import nus.iss.ssfassessment.model.Delivery;
import nus.iss.ssfassessment.model.Order;
import nus.iss.ssfassessment.model.OrderConfirmation;
import nus.iss.ssfassessment.service.PizzaService;

@Controller
@RequestMapping(path = "/")
public class PizzaController {
    
    @Autowired
    private PizzaService pizzaSvc;

    @GetMapping
    public String landingPage(Model model) {
        model.addAttribute("order", new Order());
        return "index";
    }

    @PostMapping(path = "/pizza")
    public ModelAndView postPizza(@Valid @ModelAttribute Order order, BindingResult result) {
        ModelAndView mav = new ModelAndView();

        if (result.hasErrors()) {
            mav.setViewName("index");
            mav.setStatus(HttpStatus.BAD_REQUEST);
            return mav;
        }
        boolean validSize = pizzaSvc.validSize(order.getSize());
        boolean validPizza = pizzaSvc.validPizza(order.getPizza());
        if (!validSize) {
            FieldError err = new FieldError("order", "size", "Size is invalid");
            result.addError(err);
            mav.setViewName("index");
            mav.setStatus(HttpStatus.BAD_REQUEST);
            return mav;
        }
        if (!validPizza) {
            FieldError err = new FieldError("order", "pizza", "Pizza is invalid");
            result.addError(err);
            mav.setViewName("index");
            mav.setStatus(HttpStatus.BAD_REQUEST);
            return mav;
        }

        // Validations complete
        mav.addObject("order", order);
        mav.addObject("delivery", new Delivery());
        mav.setViewName("details");
        return mav;
    }

    @PostMapping(path = "/pizza/order", consumes = "application/x-www-form-urlencoded")
    public ModelAndView postOrder(@Valid @ModelAttribute Delivery delivery, BindingResult result, @ModelAttribute Order order) {
        ModelAndView mav = new ModelAndView();

        System.out.println(delivery);
        System.out.println(order);

        if (result.hasErrors()) {
            mav.setStatus(HttpStatus.BAD_REQUEST);
            mav.addObject("order", order);
            mav.addObject("delivery", delivery);
            mav.setViewName("details");
            return mav;
        }

        // Call service to process order
        OrderConfirmation orderConfirm = pizzaSvc.processOrder(delivery, order);

        mav.addObject("orderConfirm", orderConfirm);
        mav.setStatus(HttpStatus.ACCEPTED);
        mav.setViewName("thankyou");
        return mav;
    }

    @GetMapping(path = "/order/{orderId}", produces = "text/html")
    public ModelAndView getOrder(@PathVariable String orderId) {
        ModelAndView mav = new ModelAndView();
        System.out.println(orderId);

        OrderConfirmation orderConfirm = pizzaSvc.getOrder(orderId);

        mav.setViewName("thankyou");
        mav.addObject("orderConfirm", orderConfirm);

        return mav;
    }

    @GetMapping(path = "/order/{orderId}", produces = "application/json")
    public ResponseEntity<String> getJsonOrder(@PathVariable String orderId) {
        JsonObject order = pizzaSvc.getOrderObject(orderId);
        ResponseEntity<String> resp = ResponseEntity.ok(order.toString());
        return resp;
    }
}
