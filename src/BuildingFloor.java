//Nachiappan, Raam 45618246, 
//La, Brenda 27885244

public class BuildingFloor
{
	private int[] totalDestinationRequests;
	private int[] arrivedPassengers;
	private int[] passengerRequests;
	private int approachingElevator;
	
	//BuildingFloor Constructor
	public BuildingFloor(){
		totalDestinationRequests = new int[5];
		arrivedPassengers = new int[5];
		passengerRequests = new int[5];
		approachingElevator = -1;
	}
	
	//Returns the array that has access to total destination requests from passengers on each floor
	public int[] getTotalDestinationRequests()
	{
		return totalDestinationRequests;
	}

	//Set the total amount of destination requests on a specific floor
	public void setTotalDestinationRequests(int numPass, int destFloor)
	{
		this.totalDestinationRequests[destFloor] += numPass;
	}
	
	//Return the array that has access to the amount of arrived passengers on each floor
	public int[] getArrivedPassengers()
	{
		return arrivedPassengers;
	}
	
	//Sets the amount of arrived passengers on a specific floor
	public void setArrivedPassengers(int numPass, int elevatorID)
	{
		this.arrivedPassengers[elevatorID] += numPass;
	}
	
	//Return the array that has access to the amount of passenger requests on each floor
	public int[] getPassengerRequests()
	{
		return passengerRequests;
	}
	
	//Sets the amount of passenger requests on a specific floor
	public void setPassengerRequests(int numPass, int destFloor)
	{
		this.passengerRequests[destFloor] = numPass;
	}
	
	//Returns the elevator ID approaching to a floor
	public int getApproachingElevator()
	{
		return approachingElevator;
	}
	
	//Sets the elevator ID approaching to a floor
	public void setApproachingElevator(int approachingElevator)
	{
		this.approachingElevator = approachingElevator;
	}
	
}
