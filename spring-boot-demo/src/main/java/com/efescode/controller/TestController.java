package com.efescode.controller;

import com.efescode.Customer;
import com.efescode.Main;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/customers")

public class TestController {
    private final Main main;

    public TestController(Main main) {
        this.main = main;
    }

    @GetMapping
    public List<Customer> getCustomers(){
        return main.getCustomerRepository().findAll();
    }

    @PostMapping("/create")
    public void addCustomer(@RequestBody CustomerCreationDTO request){
        Customer customer = new Customer();
        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setAge(request.getAge());
        main.getCustomerRepository().save(customer);

    }
    @DeleteMapping("remove/{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Integer id)  {
        main.getCustomerRepository().deleteById(id); //revoke repository

    }
    @PutMapping("update/{customerId}")
    public void updateCustomer(@PathVariable("customerId") Integer id,
                               @RequestBody CustomerCreationDTO request
    )  {
        Optional<Customer> optionalCustomer = main.getCustomerRepository().findById(id);
        if  (optionalCustomer.isPresent())    {
            Customer existingCustomer = optionalCustomer.get(); // if ID exists get the customer
            //update the properties with new values from request
            existingCustomer.setAge(request.getAge());
            existingCustomer.setEmail(request.getEmail());
            existingCustomer.setName(request.getName());
            //save updated customer
            main.getCustomerRepository().save(existingCustomer);
        }   else {
            System.out.println("NOT FOUND!!!");
        }

    }

}