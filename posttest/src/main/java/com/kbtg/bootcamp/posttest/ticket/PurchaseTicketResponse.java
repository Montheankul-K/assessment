package com.kbtg.bootcamp.posttest.ticket;

import java.util.List;

public record PurchaseTicketResponse(List<String> tickets, Integer count, Double cost) {}
