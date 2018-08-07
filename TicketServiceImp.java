import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class TicketServiceImp implements TicketService{

	static List<Seat> seats = new ArrayList<Seat>();
	Map<Integer, List<Seat>> groupedSeats;
	List<SeatHold> reservations = new ArrayList<SeatHold>();
	Map<Integer, List<SeatHold>> groupedReservations;
	
	@Override
	public int numSeatsAvailable() {
		// TODO Auto-generated method stub
		int totalAvailableSeats = 0;
		for(Seat seat : seats) {
			if(!seat.isTaken) {
				totalAvailableSeats++;
			}
		}
		return totalAvailableSeats;
	}

	@Override
	public SeatHold findAndHoldSeats(int numSeats, String customerEmail) {
		SeatHold seatHold = new SeatHold(numSeats, customerEmail);
		for(Map.Entry<Integer, List<Seat>> entry : groupedSeats.entrySet()) {
			for(int i = 0; i< numSeats; i++) {
					findNextAvailableSeat(1, i, customerEmail, seatHold);
					
				}
				break;
		}
		
		return seatHold;
	}

	@Override
	public String reserveSeats(int seatHoldId, String customerEmail) {
		// TODO Auto-generated method stub
		return "\n" + confirmReservation(seatHoldId) + "\n";
	}
	
	/*
	 * This method will find the next available set of seats based on the number of seats requested
	 * */
	private void findNextAvailableSeat(int row, int seatNum, String customerEmail, SeatHold seatHold) {
		
		if(seatNum > 31) {
			seatNum = 0;
			findNextAvailableSeat(row+1, seatNum,customerEmail, seatHold);
		}
		else if(row > 9) {
			System.out.println("Not enough Seats");
			return;
		}
		else if(groupedSeats.get(row).get(seatNum).isTaken) {
			findNextAvailableSeat(row, seatNum+1, customerEmail, seatHold);
		}
		else {
			groupedSeats.get(row).get(seatNum).setCustomerEmail(customerEmail);
			seatHold.addReservedSeat(groupedSeats.get(row).get(seatNum));
		}
	}
	
	/**
	 * This method sets the stage to hold a group of seats. 
	 * */
	public void holdSeats() {
		Scanner scan = new Scanner(System.in);
		int seatsToHold = 0;
		String seatsStr = "";
		String userEmail = "";
		System.out.println("Please enter the number of seats you want to hold:");
		seatsStr = scan.nextLine();
		seatsToHold = Integer.parseInt(seatsStr);
		
		while(seatsToHold > numSeatsAvailable()) {
			System.out.println("There are not enough available seats to meet your request");
			System.out.println("Please Try Again:");
			seatsToHold = scan.nextInt();
		}
		
		System.out.println("Please enter your email to proceed:");
		userEmail = scan.nextLine();
		
		SeatHold seatHold = findAndHoldSeats(seatsToHold, userEmail);
		System.out.println("\nConfirmation #: " + seatHold.getSeatHoldID());
		System.out.println(seatHold.getNumberOfSeats() +" seats reserved for " + seatHold.getCustomerEmail()+ "\n");
		
		Date creationDate = new Date();
		seatHold.setCreationDate(creationDate);
		storeReservation(seatHold);
		cleanUpOldReservations();
	}
	
	/**
	 * This method is called to populate the Seat objects when the program first executes
	 * */
	public void populateSeats(int rows, int seatsPerRow) {
		
		for(int i=0; i<rows; i++) {
			for(int j=0; j<seatsPerRow; j++) {
				Seat seat = new Seat(i,j,null);
				seats.add(seat);
			}
		}
		groupedSeats = seats
				.stream()
				.collect(Collectors.groupingBy((p)->p.row+1)); //Lambda expression to group seats by row.*/
	}
	
	/**
	 * This program shows a top view of the theater, displaying which seats are available and which are not.
	 * */
	public void plot(int rows, int seatsPerRow) {
		System.out.println("-------------------------------------------[[  STAGE  ]]--------------------------------------------");
		System.out.println("----------------------------------------------------------------------------------------------------");
		cleanUpOldReservations();
		
		for(Map.Entry<Integer, List<Seat>> entry : groupedSeats.entrySet()) {
			System.out.println(entry.getKey() + " - " + entry.getValue());
		}
		
		System.out.println("\nThere are " + numSeatsAvailable() + " seats still available" );
		
		System.out.println("\no = Open Seat");
		System.out.println("x = Taken/Reserved Seat\n");	
	}
	
	private String confirmReservation(int reservationID) {
		
		groupedReservations = reservations
				.stream()
				.collect(Collectors.groupingBy((p)->p.seatHoldID));
		
		return groupedReservations.get(reservationID).toString();
	}
	
	/**
	 * This mehot removes oid reservations (in this case, older than 60 seconds)
	 * */
	private void cleanUpOldReservations() {
		Date now = new Date();
		
		groupedReservations = reservations
				.stream()
				.collect(Collectors.groupingBy((p)->p.seatHoldID));
		
		for(Map.Entry<Integer, List<SeatHold>> entry : groupedReservations.entrySet()) {
			if(entry.getValue().get(0).creationDate.getTime()- now.getTime() > 60000) {
				for(Seat seat : entry.getValue().get(0).reservedSeats) {
					seat.setCustomerEmail(null);
				}
				groupedReservations.remove(entry.getValue().get(0).seatHoldID);
			}
		}
	}
	
	/*
	 * Once this reservation is complete, this method stores it into a list for further reference*/
	private void storeReservation(SeatHold reservation) {
		this.reservations.add(reservation);
	}

}
