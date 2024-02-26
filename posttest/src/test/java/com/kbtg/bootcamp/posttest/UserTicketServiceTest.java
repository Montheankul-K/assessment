package com.kbtg.bootcamp.posttest;

import com.kbtg.bootcamp.posttest.ticket.PurchaseTicketResponse;
import com.kbtg.bootcamp.posttest.ticket.Ticket;
import com.kbtg.bootcamp.posttest.ticket.TicketRepository;
import com.kbtg.bootcamp.posttest.ticket.TicketResponse;
import com.kbtg.bootcamp.posttest.userticket.UserTicket;
import com.kbtg.bootcamp.posttest.userticket.UserTicketRepository;
import com.kbtg.bootcamp.posttest.userticket.UserTicketResponse;
import com.kbtg.bootcamp.posttest.userticket.UserTicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserTicketServiceTest {
    MockMvc mockMvc;
    @Mock
    UserTicketRepository userTicketRepository;
    @Mock
    TicketRepository ticketRepository;
    @BeforeEach
    void SetUp() {
        UserTicketService userTicketService = new UserTicketService(userTicketRepository, ticketRepository);
        mockMvc = MockMvcBuilders.standaloneSetup(userTicketService).build();
    }

    @Test
    @DisplayName("purchase ticket should return purchase id")
    void purchaseTicketShouldReturnPurchaseId() throws Exception {
        Integer ticketId = 1;
        String userId = "user000001";

        Ticket ticket = new Ticket();
        ticket.setTicketId(1);
        ticket.setTicketAmount(1);

        UserTicket userTicket = new UserTicket();
        userTicket.setPurchaseId(1);
        userTicket.setUserId(userId);
        userTicket.setTicketId(ticketId);

        when(ticketRepository.findById(Long.valueOf(ticketId))).thenReturn(Optional.of(ticket));
        when(userTicketRepository.save(any(UserTicket.class))).thenReturn(userTicket);

        UserTicketService userTicketService = new UserTicketService(userTicketRepository, ticketRepository);
        UserTicketResponse actualUserTicketResponse = userTicketService.purchaseTicket(userId, ticketId);

        UserTicketResponse expectUserTicketResponse = new UserTicketResponse("1");

        assertEquals(expectUserTicketResponse.id(), actualUserTicketResponse.id());
    }

    @Test
    @DisplayName("get purchase tickets should return purchase history")
    void getPurchaseTicketsShouldReturnPurchaseHistory() throws Exception {
        String userId = "user000001";

        UserTicket userTicket1 = new UserTicket();
        userTicket1.setPurchaseId(1);
        userTicket1.setUserId(userId);
        userTicket1.setTicketId(1);

        UserTicket userTicket2 = new UserTicket();
        userTicket2.setPurchaseId(2);
        userTicket2.setUserId(userId);
        userTicket2.setTicketId(2);

        List<UserTicket> userTicketList = new ArrayList<>();
        userTicketList.add(userTicket1);
        userTicketList.add(userTicket2);

        when(userTicketRepository.findAllPurchaseTicketsByUserId(userId)).thenReturn(userTicketList);

        Ticket ticket1 = new Ticket();
        ticket1.setTicketId(1);
        ticket1.setTicket("000001");
        ticket1.setTicketPrice(80.00);

        Ticket ticket2 = new Ticket();
        ticket2.setTicketId(2);
        ticket2.setTicket("000002");
        ticket2.setTicketPrice(80.00);

        when(ticketRepository.findById(Long.valueOf(ticket1.getTicketId()))).thenReturn(Optional.of(ticket1));
        when(ticketRepository.findById(Long.valueOf(ticket2.getTicketId()))).thenReturn(Optional.of(ticket2));

        List<String> expectTickets = Arrays.asList(ticket1.getTicket(), ticket2.getTicket());
        Integer expectCount = 2;
        Double expectCost = ticket1.getTicketPrice() + ticket2.getTicketPrice();

        PurchaseTicketResponse expectPurchaseTicketResponse = new PurchaseTicketResponse(expectTickets, expectCount, expectCost);

        UserTicketService userTicketService = new UserTicketService(userTicketRepository, ticketRepository);
        PurchaseTicketResponse actualPurchaseTicketResponse = userTicketService.getPurchaseTickets(userId);

        assertEquals(expectPurchaseTicketResponse, actualPurchaseTicketResponse);
    }

    @Test
    @DisplayName("sell back ticket should return ticket")
    void sellBackTicketShouldReturnTicket() throws Exception {
        String userId = "user000001";
        Integer ticketId = 1;

        doNothing().when(userTicketRepository).deleteTicketsByUserIdAndTicketId(userId, ticketId);

        Ticket ticket = new Ticket();
        ticket.setTicketId(1);
        ticket.setTicket("000001");

        when(ticketRepository.findById(Long.valueOf(ticketId))).thenReturn(Optional.of(ticket));

        TicketResponse expectTicketResponse = new TicketResponse(ticket.getTicket());

        UserTicketService userTicketService = new UserTicketService(userTicketRepository, ticketRepository);
        TicketResponse actualTicketResponse = userTicketService.sellBackTicket(userId, ticketId);

        assertEquals(expectTicketResponse, actualTicketResponse);
    }
}
