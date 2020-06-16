package controller;

import model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.ICustomerService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerController {
    @Autowired
    ICustomerService customerService;

    @GetMapping("customers")
    public ResponseEntity<List<Customer>> findAll(){
        List<Customer> customerList = customerService.findAll();
        if (customerList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customerList,HttpStatus.OK);
    }

    @GetMapping("customers/{id}")
    public ResponseEntity<Customer> getOneCustomer(@PathVariable Long id){
        Customer customer = customerService.findById(id);
        if (customer==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customer,HttpStatus.OK);
    }
//    POST request "/api/customers/" với một JSON object tạo một khách hàng mới
//    PUT request "/api/customers/3" với một JSON object cập nhật khách hàng có ID 3
//    DELETE request "/api/customers/3" xóa khách hàng có ID 3

    @PostMapping("customers")
    public ResponseEntity<Void> createNewCustomer(@RequestBody Customer customer){
        customerService.save(customer);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @DeleteMapping("customers/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id){
        Customer customer = customerService.findById(id);
        if (customer == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        customerService.remove(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("customers/{id}")
    public ResponseEntity<Void> updateCustomer(@PathVariable Long id,@RequestBody Customer customer){
        Customer findOutCustomer = customerService.findById(id);
        if (customer == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        findOutCustomer.setLastName(customer.getLastName());
        findOutCustomer.setFirstName(customer.getFirstName());
        customerService.save(findOutCustomer);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
