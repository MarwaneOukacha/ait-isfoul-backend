package com.aitIsfoul.hotel.mapper;

import com.aitIsfoul.hotel.entity.Customer;
import com.aitIsfoul.hotel.entity.dto.request.AddCustomerRequest;
import com.aitIsfoul.hotel.entity.dto.response.AddCustomerResponse;
import com.aitIsfoul.hotel.entity.dto.response.SearchCustomerResponse;
import com.aitIsfoul.hotel.entity.dto.response.UpdateCustomerResponse;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;


@Mapper(componentModel = "spring")
public interface CustomerMapper {
    SearchCustomerResponse userToSearchUserResponse(Customer customer);
    Customer addCustomerReqToCustomer(AddCustomerRequest addCustomerRequest);

    default Page<SearchCustomerResponse> userPageToSearchUserResponsePage(Page<Customer> customers) {
        return customers.map(this::userToSearchUserResponse);
    }

    AddCustomerResponse customerToAddCustomerResponse(Customer customer);

    UpdateCustomerResponse customerToUpdateCustomerResponse(Customer user);
}