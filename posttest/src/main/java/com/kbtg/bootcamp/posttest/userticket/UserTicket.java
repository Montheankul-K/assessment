package com.kbtg.bootcamp.posttest.userticket;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "user_ticket")
public class UserTicket {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("id")
	@Column(name = "purchase_id")
	private Integer purchaseId;
	@NotNull(message = "user id is required")
	@Size(min = 10, max = 10, message = "user id should be 10 characters")
	@JsonProperty("user_id")
	@Column(name = "user_id")
	private String userId;
	@NotNull(message = "ticket id is required")
	@JsonProperty("ticket_id")
	@Column(name = "lottery_id")
	private Integer ticketId;

	public Integer getPurchaseId() {
		return purchaseId;
	}

	public void setPurchaseId(Integer purchaseId) {
		this.purchaseId = purchaseId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getTicketId() {
		return ticketId;
	}

	public void setTicketId(Integer ticketId) {
		this.ticketId = ticketId;
	}
}
