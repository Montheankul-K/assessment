package com.kbtg.bootcamp.posttest.ticket;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("")
public class TicketController {
	private final TicketService ticketService;

	public TicketController(TicketService ticketService) {
		this.ticketService = ticketService;
	}

	@PostMapping("/admin/lotteries")
	@ResponseStatus(HttpStatus.CREATED)
	public TicketResponse addTicket(@Validated @RequestBody TicketRequest request) throws Exception{
		return ticketService.addTicket(request);
	}

	@GetMapping("/lotteries")
	@ResponseStatus(HttpStatus.OK)
	public Map<String, List<String>> getTickets() throws Exception{
		return ticketService.getTickets();
	}
}
