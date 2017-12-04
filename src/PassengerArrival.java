//Nachiappan, Raam 45618246, 
//La, Brenda 27885244

public class PassengerArrival
{
	private int numPassengers;
	private int destinationFloor;
	private int timePeriod;
	private int expectedTimeOfArrival;
	
	//Returns the number of passengers that arrived
	public int getNumPassengers()
	{
		return numPassengers;
	}
	
	//Sets the number of passengers that arrived 
	public void setNumPassengers(int numPassengers)
	{
		this.numPassengers = numPassengers;
	}
	
	//Returns the destination floor requested by passengers that arrived
	public int getDestinationFloor()
	{
		return destinationFloor;
	}
	
	//Sets the destination floor requested by passengers that arrived
	public void setDestinationFloor(int destinationFloor)
	{
		this.destinationFloor = destinationFloor;
	}
	
	//Returns the time period the passengers will request elevator access 
	public int getTimePeriod()
	{
		return timePeriod;
	}
	
	//Sets the time period the passengers will request elevator access 
	public void setTimePeriod(int timePeriod)
	{
		this.timePeriod = timePeriod;
	}
	
	//Returns the simulated time where the next group of passengers will enter the simulation 
	public int getExpectedTimeOfArrival()
	{
		return expectedTimeOfArrival;
	}
	
	//Sets the simulated time where the next group of passengers will enter the simulation 
	public void setExpectedTimeOfArrival(int expectedTimeOfArrival)
	{
		this.expectedTimeOfArrival = expectedTimeOfArrival;
	}
}
