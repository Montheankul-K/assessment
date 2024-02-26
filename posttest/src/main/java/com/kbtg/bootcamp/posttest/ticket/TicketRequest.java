package com.kbtg.bootcamp.posttest.ticket;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TicketRequest(
        @JsonProperty("ticket")
        @NotNull @Size(min = 6, max = 6)
        String ticket,
        @JsonProperty("price")
        @NotNull
        Double price,
        @JsonProperty("amount")
        @NotNull
        Integer amount
){

}
