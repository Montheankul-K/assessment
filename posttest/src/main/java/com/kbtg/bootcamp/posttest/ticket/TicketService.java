package com.kbtg.bootcamp.posttest.ticket;

import com.kbtg.bootcamp.posttest.exception.InternalServiceException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Transactional
    public TicketResponse addTicket(TicketRequest request) throws Exception {
        try {
            Ticket ticket = new Ticket();
            ticket.setTicket(request.ticket());
            ticket.setTicketPrice(request.price());
            ticket.setTicketAmount(request.amount());
            ticketRepository.save(ticket);

            return new TicketResponse(ticket.getTicket());
        } catch (Exception error) {
            throw new InternalServiceException("failed to add ticket", error);
        }
    }

    public Map<String, List<String>> getTickets() throws Exception {
        try {
            List<String> tickets = ticketRepository.findAll().stream().map(Ticket::getTicket).collect(Collectors.toList());

            Map<String, List<String>> ticketsResponse = new HashMap<>();
            ticketsResponse.put("tickets", tickets);

            return ticketsResponse;
        } catch (Exception error) {
            throw new InternalServiceException("failed to fetch tickets", error);
        }
    }
}
