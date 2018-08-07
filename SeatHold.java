import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.Random;

public class SeatHold {
	
	int numberOfSeats;
	String customerEmail;
	int seatHoldID;
	List<Seat> reservedSeats = new ArrayList<Seat>();
	Date creationDate;
	
	public SeatHold(int numberOfSeats, String customerEmail) {
		this.numberOfSeats = numberOfSeats;
		this.customerEmail = customerEmail;
		seatHoldID = generateSeatHoldId();
	}
	
	/**
	 * This method returns the total available seats for a given row
	 * */
	public static int availableSeatsPerRow(List<Seat> rowOfSeats) {
		int availableSeats = 0;
		for(Seat seat : rowOfSeats) {
			if(!seat.isTaken) {
				availableSeats++;
			}
		}
		return availableSeats;
	}
	
	public void addReservedSeat(Seat seat) {
		reservedSeats.add(seat);
	}
	
	/*ACCESSORS*/
	public int getSeatHoldID() {
		return this.seatHoldID;
	}
	
	public int getNumberOfSeats() {
		return this.numberOfSeats;
	}
	
	public String getCustomerEmail() {
		return this.customerEmail;
	}
	
	public Date getCreationDate() {
		return this.creationDate;
	}
	
	public List<Seat> getReservedSeats() {
		return this.reservedSeats;
	}
	
	/*MUTATORS*/
	public void setCreationDate(Date date) {
		this.creationDate = date;
	}
	
	/*PRIVATE METHODS*/
	private int generateSeatHoldId() {
		Random rand = new Random();
		int holdId = ((rand.nextInt(50) + 1) * 12345) + (rand.nextInt(50) + 1);
		return holdId;
	}
	
	@Override
	public String toString() {
		return "Confirming Reservation: " + numberOfSeats + " seats for " + customerEmail;
	}

}
