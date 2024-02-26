package com.kbtg.bootcamp.posttest;

import com.kbtg.bootcamp.posttest.ticket.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class TicketControllerTest {
	MockMvc mockMvc;
	@Mock
	TicketService ticketService;
	@BeforeEach
	void SetUp() {
		TicketController ticketController = new TicketController(ticketService);
		mockMvc = MockMvcBuilders.standaloneSetup(ticketController).build();
	}

	@Test
	@DisplayName("when perform on POST: /admin/lotteries should return status 201 and body contain ticket number")
	void addTicket() throws Exception {
		TicketRequest ticketRequest = new TicketRequest("000001",80.00,1);
		TicketResponse ticketResponse = new TicketResponse("000001");

		ObjectMapper objectMapper = new ObjectMapper();
		String jsonRequest = objectMapper.writeValueAsString(ticketRequest);

		when(ticketService.addTicket(any())).thenReturn(ticketResponse);

		mockMvc.perform(post("/admin/lotteries")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON)
						.content(jsonRequest))
						.andExpect(status().isCreated())
						.andExpect(jsonPath("$.ticket").value("000001"));
	}

	@Test
	@DisplayName("when perform on GET: /lotteries should return status 200 and body contain list of tickets")
	void addTicketShouldReturnListOfTicketsAndStatus200() throws Exception {
		Map<String, List<String>> ticketsResponse = new HashMap<>();
		List<String> ticketList = new ArrayList<>();
		ticketList.add("123456");
		ticketList.add("000001");
		ticketList.add("000002");
		ticketList.add("000003");
		ticketList.add("000004");
		ticketsResponse.put("tickets", ticketList);

		when(ticketService.getTickets()).thenReturn(ticketsResponse);

		mockMvc.perform(get("/lotteries"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.tickets", hasSize(5)))
				.andExpect(jsonPath("$.tickets", containsInAnyOrder("123456", "000001", "000002", "000003", "000004")));
	}
}
