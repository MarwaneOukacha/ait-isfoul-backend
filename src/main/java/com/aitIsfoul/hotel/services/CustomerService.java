package com.aitIsfoul.hotel.services;

import com.aitIsfoul.hotel.entity.dto.GenericPage;
import com.aitIsfoul.hotel.entity.dto.request.AddCustomerRequest;
import com.aitIsfoul.hotel.entity.dto.request.SearchCustomerRequest;
import com.aitIsfoul.hotel.entity.dto.request.UpdateCustomerRequest;
import com.aitIsfoul.hotel.entity.dto.response.AddCustomerResponse;
import com.aitIsfoul.hotel.entity.dto.response.SearchCustomerResponse;
import com.aitIsfoul.hotel.entity.dto.response.UpdateCustomerResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;

public interface CustomerService {
    GenericPage<SearchCustomerResponse> getAllCustomers(SearchCustomerRequest searchCustomerRequest, Pageable pageable);


    AddCustomerResponse addCustomer(@Valid AddCustomerRequest addCustomerRequest);

    UpdateCustomerResponse updateCustomer(@Valid UpdateCustomerRequest updateCustomerRequest);
}