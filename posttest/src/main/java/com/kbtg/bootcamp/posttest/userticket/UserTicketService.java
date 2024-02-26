package com.kbtg.bootcamp.posttest.userticket;

import com.kbtg.bootcamp.posttest.exception.InternalServiceException;
import com.kbtg.bootcamp.posttest.exception.NotFoundException;
import com.kbtg.bootcamp.posttest.ticket.PurchaseTicketResponse;
import com.kbtg.bootcamp.posttest.ticket.Ticket;
import com.kbtg.bootcamp.posttest.ticket.TicketRepository;
import com.kbtg.bootcamp.posttest.ticket.TicketResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserTicketService {
    private final UserTicketRepository userTicketRepository;
    private final TicketRepository ticketRepository;

    public UserTicketService(UserTicketRepository userTicketRepository, TicketRepository ticketRepository) {
        this.userTicketRepository = userTicketRepository;
        this.ticketRepository = ticketRepository;
    }

    @Transactional
    public UserTicketResponse purchaseTicket(String userId, Integer ticketId) throws Exception {
        Ticket ticket = ticketRepository.findById(Long.valueOf(ticketId)).orElseThrow(() -> new NotFoundException("ticket not found"));

        Integer currentTicketAmount = ticket.getTicketAmount();
        if (currentTicketAmount == null || currentTicketAmount == 0) {
            throw new NotFoundException("ticket is sold out");
        }

        Integer newTicketAmount = currentTicketAmount - 1;
        ticket.setTicketAmount(newTicketAmount);
        try {
            ticketRepository.save(ticket);
        } catch (Exception error) {
            throw new InternalServiceException("can't update ticket amount", error);
        }

        UserTicket userTicket = new UserTicket();
        userTicket.setUserId(userId);
        userTicket.setTicketId(ticketId);

        try {
            UserTicket saveUserTicket = userTicketRepository.save(userTicket);
            Integer purchaseId = saveUserTicket.getPurchaseId();

            return new UserTicketResponse(String.valueOf(purchaseId));
        } catch (Exception error) {
            throw new InternalServiceException("can't insert purchase ticket data", error);
        }
    }

    public PurchaseTicketResponse getPurchaseTickets(String userId) throws Exception {
        try {
            List<UserTicket> userTicketList = (List<UserTicket>) userTicketRepository.findAllPurchaseTicketsByUserId(userId);

            Integer count = 0;
            Double cost = 0.0;
            List<String> tickets = new ArrayList<>();

            for (UserTicket userTicket : userTicketList) {
                Integer ticketId = userTicket.getTicketId();
                Ticket ticket = ticketRepository.findById(Long.valueOf(ticketId)).orElseThrow(() -> new NotFoundException("failed to fetch ticket"));
                tickets.add(ticket.getTicket());
                cost += ticket.getTicketPrice();
                count += 1;
            }

            return new PurchaseTicketResponse(tickets, count, cost);
        } catch (Exception error) {
            throw new InternalServiceException("failed to get purchase tickets", error);
        }
    }

    @Transactional
    public TicketResponse sellBackTicket(String userId, Integer ticketId) throws Exception {
        try {
            userTicketRepository.deleteTicketsByUserIdAndTicketId(userId, ticketId);
            Ticket ticket = ticketRepository.findById(Long.valueOf(ticketId)).orElseThrow(() -> new NotFoundException("failed to fetch ticket"));

            return new TicketResponse(ticket.getTicket());
        } catch (Exception error) {
            throw new InternalServiceException("can't sell back ticket", error);
        }
    }
}


