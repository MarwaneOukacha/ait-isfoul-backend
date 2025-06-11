package com.aitIsfoul.hotel.dao.impl;

import com.aitIsfoul.hotel.dao.CustomerDao;
import com.aitIsfoul.hotel.entity.Customer;
import com.aitIsfoul.hotel.entity.dto.request.AddCustomerRequest;
import com.aitIsfoul.hotel.entity.dto.request.SearchCustomerRequest;
import com.aitIsfoul.hotel.entity.dto.request.UpdateCustomerRequest;
import com.aitIsfoul.hotel.enums.UserStatus;
import com.aitIsfoul.hotel.enums.UserType;
import com.aitIsfoul.hotel.mapper.CustomerMapper;
import com.aitIsfoul.hotel.repository.CustomerRepository;
import com.aitIsfoul.hotel.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Slf4j
@Service
public class CustomerDaoImp implements CustomerDao {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;

    @Override
    public Page<Customer> searchCustomer(SearchCustomerRequest searchCustomerRequest, Pageable pageable) {
        log.info("CustomerDao :: searchCustomer {}", searchCustomerRequest);
        return customerRepository.findAllWithCriteria(searchCustomerRequest, pageable);
    }

    @Override
    public Customer addCustomer(AddCustomerRequest addCustomerRequest) {
        log.info("userDao :: addCustomer {}", addCustomerRequest);
        Customer customer = customerMapper.addCustomerReqToCustomer(addCustomerRequest);
        customer.setFirstName(addCustomerRequest.getFirstName());
        customer.setLastName(addCustomerRequest.getLastName());
        customer.setPhoneNumber(addCustomerRequest.getPhoneNumber());
        customer.setEmail(addCustomerRequest.getEmail());
        customer.setIden(addCustomerRequest.getIden());
        customer.setIsActive("N");
        customer.setPassword(encoder.encode(addCustomerRequest.getPassword()));
        customer.setType(UserType.CUSTOMER);
        customer.setStatus(UserStatus.ACTIVE);
        return customerRepository.save(customer);

    }

    @Override
    public Customer updateCustomer(UpdateCustomerRequest updateCustomerRequest) {
        log.info("CustomerDao :: updateCustomer {}", updateCustomerRequest);
        Customer customer = customerRepository.findById(UUID.fromString(updateCustomerRequest.getId()))
                .orElseThrow(
                        () -> new EntityNotFoundException("Customer not found with ID: " + updateCustomerRequest.getIden()));
        if(updateCustomerRequest.getFirstName() != null && !updateCustomerRequest.getFirstName().isEmpty()) {
            customer.setFirstName(updateCustomerRequest.getFirstName());
            log.info("CustomerDao :: updateCustomer firstName {}", customer);
        }
        if (updateCustomerRequest.getLastName() != null && !updateCustomerRequest.getLastName().isEmpty()) {
            customer.setLastName(updateCustomerRequest.getLastName());
            log.info("CustomerDao :: updateCustomer lastName {}", customer);
        }
        if (updateCustomerRequest.getPhoneNumber() != null && !updateCustomerRequest.getPhoneNumber().isEmpty()) {
            customer.setPhoneNumber(updateCustomerRequest.getPhoneNumber());
            log.info("CustomerDao :: updateCustomer phoneNumber {}", customer);
        }
        if (updateCustomerRequest.getStatus() != null) {
            customer.setStatus(updateCustomerRequest.getStatus());
            log.info("CustomerDao :: updateCustomer status {}", customer);
        }
        if (updateCustomerRequest.getIsActive() != null && !updateCustomerRequest.getIsActive().isEmpty()) {
            customer.setIsActive(updateCustomerRequest.getIsActive());
            log.info("CustomerDao :: updateCustomer isActive {}", customer);
        }


        return customerRepository.save(customer);
    }

    @Override
    public Customer findById(String clientId) {
        return customerRepository.findById(UUID.fromString(clientId))
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
    }

    @Override
    public Customer findByEmail(String email) {
        return customerRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Customer not found"));
    }

    @Override
    public void save(Customer customer) {
        customerRepository.save(customer);
    }
}