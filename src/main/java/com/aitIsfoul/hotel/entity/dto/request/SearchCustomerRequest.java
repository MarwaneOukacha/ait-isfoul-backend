package com.aitIsfoul.hotel.entity.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class SearchCustomerRequest  {
    private String keyword;

}
