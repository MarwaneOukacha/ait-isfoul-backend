package com.aitIsfoul.hotel.dao;


import com.aitIsfoul.hotel.entity.Customer;
import com.aitIsfoul.hotel.entity.dto.request.AddCustomerRequest;
import com.aitIsfoul.hotel.entity.dto.request.SearchCustomerRequest;
import com.aitIsfoul.hotel.entity.dto.request.UpdateCustomerRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CustomerDao {
    Page<Customer> searchCustomer(SearchCustomerRequest searchCustomerRequest, Pageable pageable);

    Customer addCustomer(AddCustomerRequest addCustomerRequest);

    Customer updateCustomer(UpdateCustomerRequest updateCustomerRequest);

    Customer findById(String clientId);

}