package com.kbtg.bootcamp.posttest;

import com.kbtg.bootcamp.posttest.ticket.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {
	MockMvc mockMvc;
	@Mock
	TicketRepository ticketRepository;
	@BeforeEach
	void SetUp() {
		TicketService ticketService = new TicketService(ticketRepository);
		mockMvc = MockMvcBuilders.standaloneSetup(ticketService).build();
	}

	@Test
	@DisplayName("get tickets should return list of tickets")
	void getTicketShouldReturnListOfTickets() throws Exception {
		List<String> ticketList = new ArrayList<>();
		ticketList.add("123456");
		ticketList.add("000001");
		ticketList.add("000002");
		ticketList.add("000003");
		ticketList.add("000004");

		when(ticketRepository.findAll()).thenReturn(ticketList.stream().map(ticketId -> {
			Ticket ticket = new Ticket();
			ticket.setTicket(ticketId);
			return ticket;
		}).collect(Collectors.toList()));

		Map<String, List<String>> expectedResponse = new HashMap<>();
		expectedResponse.put("tickets", ticketList);

		Map<String, List<String>> actualResponse = new TicketService(ticketRepository).getTickets();
		assertEquals(expectedResponse, actualResponse);
	}

	@Test
	@DisplayName("add ticket should return added ticket")
	void addTicketShouldReturnAddedTicket() throws Exception {
		TicketRequest ticketRequest = new TicketRequest("00001", 80.00, 1);
		String expectResponse = ticketRequest.ticket();

		Ticket ticket = new Ticket();
		ticket.setTicket("000001");
		when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

		TicketResponse actualResponse = new TicketService(ticketRepository).addTicket(ticketRequest);

		assertEquals(expectResponse, actualResponse.ticket());
	}
}
