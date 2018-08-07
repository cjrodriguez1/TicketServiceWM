
public interface TicketService {
	
	/**
	 * The number of seats in the venue that are neither held nor reserrved
	 * 
	 * @return the number of tickets available in the venue
	 * */
	int numSeatsAvailable();
	
	/**
	 * Find and hold the best available seats for a customer
	 * 
	 * @param numseats the number of seats to find and hold
	 * @param customerEmial unique identifier for the customer
	 * @return a SeatHold object identifying the specific seats and
	 * related information
	 * **/
	SeatHold findAndHoldSeats(int numSeats, String customerEmail);
	
	/**
	 * Commit seats held for a specific customer
	 * 
	 * @param seatHold the seat hold identifier
	 * @param customerEmail the email address of the customer to which the seat hold is assigned
	 * @return a reservation confirmation code
	 * */
	String reserveSeats(int seatHoldId, String customerEmail);

}
