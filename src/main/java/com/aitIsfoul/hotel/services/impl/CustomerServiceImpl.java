package com.aitIsfoul.hotel.services.impl;
import com.aitIsfoul.hotel.dao.CustomerDao;
import com.aitIsfoul.hotel.entity.Customer;
import com.aitIsfoul.hotel.entity.dto.GenericPage;
import com.aitIsfoul.hotel.entity.dto.request.AddCustomerRequest;
import com.aitIsfoul.hotel.entity.dto.request.SearchCustomerRequest;
import com.aitIsfoul.hotel.entity.dto.request.UpdateCustomerRequest;
import com.aitIsfoul.hotel.entity.dto.response.AddCustomerResponse;
import com.aitIsfoul.hotel.entity.dto.response.SearchCustomerResponse;
import com.aitIsfoul.hotel.entity.dto.response.UpdateCustomerResponse;
import com.aitIsfoul.hotel.mapper.CustomerMapper;
import com.aitIsfoul.hotel.services.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private CustomerMapper customerMapper;
    @Override
    public GenericPage<SearchCustomerResponse> getAllCustomers(SearchCustomerRequest searchCustomerRequest, Pageable pageable) {
        log.info("CustomerServiceImpl :: searchCustomer {}", searchCustomerRequest);
        Page<Customer> customers = customerDao.searchCustomer(searchCustomerRequest, pageable);
        Page<SearchCustomerResponse> searchCustomerResponses = customerMapper.userPageToSearchUserResponsePage(customers);
        log.info("CustomerDao search response inside CustomerServiceImpl: {}", customers.getContent());
        return new GenericPage<>(searchCustomerResponses,searchCustomerResponses.getTotalElements());
    }

    @Override
    public AddCustomerResponse addCustomer(AddCustomerRequest addCustomerRequest) {
        log.info("CustomerServiceImpl :: addCustomer {}", addCustomerRequest);
        Customer customer = customerDao.addCustomer(addCustomerRequest);
        log.info("customerDao add user response inside CustomerServiceImpl: {}", customer);
        return customerMapper.customerToAddCustomerResponse(customer);
    }

    @Override
    public UpdateCustomerResponse updateCustomer(UpdateCustomerRequest updateCustomerRequest) {
        log.info("CustomerServiceImpl :: updateUser {}", updateCustomerRequest);
        Customer customer = customerDao.updateCustomer(updateCustomerRequest);
        log.info("customerDao update Customer response inside CustomerServiceImpl: {}", customer);
        return customerMapper.customerToUpdateCustomerResponse(customer);
    }
}