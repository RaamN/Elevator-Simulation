//Nachiappan, Raam 45618246, 
//La, Brenda 27885244

public class ElevatorEvent
{
	private int destination;
	private int expectedArrival;
	
	//Returns the destination floor for an elevator event 
	public int getDestination()
	{
		return destination;
	}
	
	//Sets the destination floor for an elevator event 
	public void setDestination(int destination)
	{
		this.destination = destination;
	}
	
	//Returns the expected arrival time for an elevator event 
	public int getExpectedArrival()
	{
		return expectedArrival;
	}
	
	//Sets the expected arrival time for an elevator event 
	public void setExpectedArrival(int expectedArrival)
	{
		this.expectedArrival = expectedArrival;
	}
}
