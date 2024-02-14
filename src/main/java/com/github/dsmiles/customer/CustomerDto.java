package com.github.dsmiles.customer;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Getter
public class CustomerDto {

    @JsonProperty("customerName")
    private final String name;

    @JsonProperty("member_since")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd HH:mm")
    private final ZonedDateTime joinedAt;

    public CustomerDto(Customer customer) {
        this.name = customer.getName();
        this.joinedAt = customer.getJoinedAt();
    }
}
