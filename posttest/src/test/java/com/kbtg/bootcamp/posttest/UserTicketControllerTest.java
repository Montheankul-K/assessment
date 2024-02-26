package com.kbtg.bootcamp.posttest;

import com.kbtg.bootcamp.posttest.ticket.PurchaseTicketResponse;
import com.kbtg.bootcamp.posttest.ticket.TicketResponse;
import com.kbtg.bootcamp.posttest.userticket.UserTicketController;
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
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@ExtendWith(MockitoExtension.class)
public class UserTicketControllerTest {
    MockMvc mockMvc;
    @Mock
    UserTicketService userTicketService;

    @BeforeEach
    void SetUp() {
        UserTicketController userTicketController = new UserTicketController(userTicketService);
        mockMvc = MockMvcBuilders.standaloneSetup(userTicketController).build();
    }

    @Test
    @DisplayName("when GET: /users/:userId/lotteries should return status 200 and body contain user purchase history")
    void getPurchaseTicketsShouldReturnStatus200AndUserPurchaseHistory() throws Exception {
        String userId = "user000001";
        List<String> ticketList = new ArrayList<>();
        ticketList.add("000001");
        ticketList.add("000002");
        Integer count = 2;
        Double cost = 160.00;

        PurchaseTicketResponse purchaseTicketResponse = new PurchaseTicketResponse(ticketList, count, cost);

        when(userTicketService.getPurchaseTickets(userId)).thenReturn(purchaseTicketResponse);

        mockMvc.perform(get("/users/{userId}/lotteries", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tickets", containsInAnyOrder("000001", "000002")))
                .andExpect(jsonPath("count").value(2))
                .andExpect(jsonPath("$.cost").value(160.00));
    }

    @Test
    @DisplayName("when DELETE: /users/:userId/lotteries/:ticketId should return status 200 and body contain deleted ticket")
    void sellBackTicketShouldReturnStatus200andDeletedTicket() throws Exception {
        String userId = "user000001";
        Integer ticketId = 1;
        TicketResponse ticketResponse = new TicketResponse("000001");

        when(userTicketService.sellBackTicket(userId,ticketId)).thenReturn(ticketResponse);

        mockMvc.perform(delete("/users/{userId}/lotteries/{ticketId}", userId, ticketId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ticket").value("000001"));
    }

    @Test
    @DisplayName("when POST: /users/{userId}/lotteries/{ticketId} should return status 201 and body contain purchase id")
    void purchaseTicketShouldReturnStatus200andPurchaseId() throws Exception {
        String userId = "user000001";
        Integer ticketId = 1;
        UserTicketResponse userTicketResponse = new UserTicketResponse("1");

        when(userTicketService.purchaseTicket(userId, ticketId)).thenReturn(userTicketResponse);

        mockMvc.perform(post("/users/{userId}/lotteries/{ticketId}", userId, ticketId))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"));
    }
}
