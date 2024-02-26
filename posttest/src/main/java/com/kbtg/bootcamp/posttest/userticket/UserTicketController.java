package com.kbtg.bootcamp.posttest.userticket;

import com.kbtg.bootcamp.posttest.ticket.PurchaseTicketResponse;
import com.kbtg.bootcamp.posttest.ticket.TicketResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserTicketController {
    private final UserTicketService userTicketService;

    public UserTicketController(UserTicketService userTicketService) {
        this.userTicketService = userTicketService;
    }

    @PostMapping("/{userId}/lotteries/{ticketId}")
    @ResponseStatus(HttpStatus.CREATED)
    public UserTicketResponse purchaseTicket(@PathVariable String userId, @PathVariable Integer ticketId) throws Exception {
        return userTicketService.purchaseTicket(userId, ticketId);
    }

    @GetMapping("/{userId}/lotteries")
    @ResponseStatus(HttpStatus.OK)
    public PurchaseTicketResponse getPurchaseTickets(@PathVariable String userId) throws Exception {
        return userTicketService.getPurchaseTickets(userId);
    }

    @DeleteMapping("/{userId}/lotteries/{ticketId}")
    @ResponseStatus(HttpStatus.OK)
    public TicketResponse sellBackTicket(@PathVariable String userId, @PathVariable Integer ticketId) throws Exception {
        return userTicketService.sellBackTicket(userId, ticketId);
    }
}
