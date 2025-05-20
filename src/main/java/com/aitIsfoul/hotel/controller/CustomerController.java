package com.aitIsfoul.hotel.controller;
import com.aitIsfoul.hotel.entity.dto.GenericPage;
import com.aitIsfoul.hotel.entity.dto.request.AddCustomerRequest;
import com.aitIsfoul.hotel.entity.dto.request.SearchCustomerRequest;
import com.aitIsfoul.hotel.entity.dto.request.UpdateCustomerRequest;
import com.aitIsfoul.hotel.entity.dto.response.AddCustomerResponse;
import com.aitIsfoul.hotel.entity.dto.response.SearchCustomerResponse;
import com.aitIsfoul.hotel.entity.dto.response.UpdateCustomerResponse;
import com.aitIsfoul.hotel.enums.UserType;
import com.aitIsfoul.hotel.services.CustomerService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/search")
    public GenericPage<SearchCustomerResponse> searchUsers(@RequestParam(required = false) String keyword,
                                                           @RequestParam(required = false) UserType type, Pageable pageable
    ) {
        SearchCustomerRequest searchCustomerRequest=new SearchCustomerRequest(keyword);
        log.info("Search customers {}", searchCustomerRequest);
        return customerService.getAllCustomers(searchCustomerRequest,pageable);
    }

    @PostMapping("/add")
    public AddCustomerResponse addCustomer(@RequestBody @Valid AddCustomerRequest addCustomerRequest) {
        log.info("Add customer request: {}", addCustomerRequest);
        return customerService.addCustomer(addCustomerRequest);
    }
    @PatchMapping("/update")
    public UpdateCustomerResponse updateCustomer(@RequestBody @Valid UpdateCustomerRequest updateCustomerRequest) {
        log.info("Update customer request: {}", updateCustomerRequest);
        return customerService.updateCustomer(updateCustomerRequest);
    }


}