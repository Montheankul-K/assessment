package com.kbtg.bootcamp.posttest.userticket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTicketRepository extends JpaRepository<UserTicket, Long> {
	@Query("SELECT ut FROM UserTicket ut WHERE ut.userId = :userId")
	List<UserTicket> findAllPurchaseTicketsByUserId(@Param("userId") String userId);

	@Modifying
	@Query("DELETE FROM UserTicket ut WHERE ut.userId = :userId AND ut.ticketId = :ticketId")
	void deleteTicketsByUserIdAndTicketId(@Param("userId") String userId, @Param("ticketId") Integer ticketId);
}
