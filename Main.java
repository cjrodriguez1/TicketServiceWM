import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String selectionStr = "";
		int selection = 0;
		
		TicketServiceImp ticketService = new TicketServiceImp();
		
		ticketService.populateSeats(9,32);

		System.out.println("Please make your selection:\n");
		
		do {
			menu();
			selectionStr = scan.nextLine();
			selection = Integer.parseInt(selectionStr);
			if(selection == 1) {
				ticketService.plot(9,32);
			}
			else if(selection == 2) {
				ticketService.holdSeats();
			}
			else if(selection ==3) {
				System.out.println("Enter reservation ID:");
				String seatHoldIdStr = scan.nextLine();
				int seatHoldId = Integer.parseInt(seatHoldIdStr);
				System.out.println("Enter customer email:");
				String customerEmail = scan.nextLine();
				String confirmation = ticketService.reserveSeats(seatHoldId, customerEmail);
				System.out.println(confirmation);
			}
		} while(selection != 0);
		
		System.out.println("Thank you!\nCome Again!");
	}
	
	
	
	private static void menu() {
		System.out.println("1) Show available seats");
		System.out.println("2) Hold seats");
		System.out.println("3) Commit held seats");
		System.out.println("0) EXIT");
	}

}
