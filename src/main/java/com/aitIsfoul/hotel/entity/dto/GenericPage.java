package com.aitIsfoul.hotel.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@AllArgsConstructor
@Getter
@Setter
public class GenericPage<T> {
    Page<T> page;
    Object totalCount;
}