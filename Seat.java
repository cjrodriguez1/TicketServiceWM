public class Seat {
	
	public int row;
	int seat;
	boolean isTaken = false;
	String customerEmail = null;
	
	public Seat(int row, int seat, String customerEmail) {
		this.row = row;
		this.seat = seat;
		this.customerEmail = customerEmail;
		
		if(customerEmail == null) {
			isTaken = false;
		}
		else if(customerEmail != null) {
			isTaken = true;
		}
	}
	
	/**
	 * This mutator is also used to detect if a seat is available or not
	 * */
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
		if(this.customerEmail.length() > 0) {
			isTaken = true;
		}
		else {
			isTaken = false;
		}
		
	}
	
	@Override
	public String toString() {
		if(isTaken) {
			return "X";
		}
		else {
			return "o";
		}
	}
	
	
}
