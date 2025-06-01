package com.aitIsfoul.hotel.services.impl;
import com.aitIsfoul.hotel.dao.CustomerDao;
import com.aitIsfoul.hotel.entity.Customer;
import com.aitIsfoul.hotel.entity.OtpToken;
import com.aitIsfoul.hotel.entity.dto.GenericPage;
import com.aitIsfoul.hotel.entity.dto.request.AddCustomerRequest;
import com.aitIsfoul.hotel.entity.dto.request.SearchCustomerRequest;
import com.aitIsfoul.hotel.entity.dto.request.UpdateCustomerRequest;
import com.aitIsfoul.hotel.entity.dto.response.AddCustomerResponse;
import com.aitIsfoul.hotel.entity.dto.response.SearchCustomerResponse;
import com.aitIsfoul.hotel.entity.dto.response.UpdateCustomerResponse;
import com.aitIsfoul.hotel.mapper.CustomerMapper;
import com.aitIsfoul.hotel.repository.OtpTokenRepository;
import com.aitIsfoul.hotel.services.CustomerService;
import com.aitIsfoul.hotel.services.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;


@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private EmailService emailService;
    @Autowired
    private OtpTokenRepository otpTokenRepository;
    @Override
    public GenericPage<SearchCustomerResponse> getAllCustomers(SearchCustomerRequest searchCustomerRequest, Pageable pageable) {
        log.info("CustomerServiceImpl :: searchCustomer {}", searchCustomerRequest);
        Page<Customer> customers = customerDao.searchCustomer(searchCustomerRequest, pageable);
        Page<SearchCustomerResponse> searchCustomerResponses = customerMapper.userPageToSearchUserResponsePage(customers);
        log.info("CustomerDao search response inside CustomerServiceImpl: {}", customers.getContent());
        return new GenericPage<>(searchCustomerResponses);
    }

    @Override
    public AddCustomerResponse addCustomer(AddCustomerRequest addCustomerRequest) {
        log.info("CustomerServiceImpl :: addCustomer {}", addCustomerRequest);
        Customer customer = customerDao.addCustomer(addCustomerRequest);
        log.info("customerDao add user response inside CustomerServiceImpl: {}", customer);
        // Generate OTP
        String otp = generateOtp(); // e.g., 6-digit code
        OtpToken otpToken = new OtpToken();
        otpToken.setOtpCode(otp);
        otpToken.setExpiresAt(LocalDateTime.now().plusMinutes(5));
        otpToken.setCustomer(customer);
        otpTokenRepository.save(otpToken);
        emailService.sendOtp(customer.getEmail(),otp);
        return customerMapper.customerToAddCustomerResponse(customer);
    }

    @Override
    public UpdateCustomerResponse updateCustomer(UpdateCustomerRequest updateCustomerRequest) {
        log.info("CustomerServiceImpl :: updateUser {}", updateCustomerRequest);
        Customer customer = customerDao.updateCustomer(updateCustomerRequest);
        log.info("customerDao update Customer response inside CustomerServiceImpl: {}", customer);
        return customerMapper.customerToUpdateCustomerResponse(customer);
    }

    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }
}