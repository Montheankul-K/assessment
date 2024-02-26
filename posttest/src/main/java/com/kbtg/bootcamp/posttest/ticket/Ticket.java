package com.kbtg.bootcamp.posttest.ticket;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "lottery")
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "lottery_id")
	@JsonProperty("id")
	private Integer ticketId;
	@NotNull(message = "ticket should not be null")
	@Size(min = 6, max = 6, message = "ticket should be 6 digits")
	@Column(name = "lottery_ticket")
	private String ticket;
	@NotNull(message = "ticket price should not be null")
	@JsonProperty("price")
	@Column(name = "lottery_price")
	private Double ticketPrice;
	@NotNull(message = "ticket amount should not be null")
	@JsonProperty("amount")
	@Column(name = "lottery_amount")
	private Integer ticketAmount;

	public Ticket(){}

	public Integer getTicketId() {
		return ticketId;
	}

	public void setTicketId(Integer ticketId) {
		this.ticketId = ticketId;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public Double getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(Double ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

	public Integer getTicketAmount() {
		return ticketAmount;
	}

	public void setTicketAmount(Integer ticketAmount) {
		this.ticketAmount = ticketAmount;
	}
}
