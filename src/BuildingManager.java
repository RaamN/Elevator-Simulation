//Nachiappan, Raam 45618246, 
//La, Brenda 27885244

public class BuildingManager
{
	private BuildingFloor[] floors;
	
	//BuildingManager constructor 
	public BuildingManager(){
		floors = new BuildingFloor[5];
		for (int i =0; i < 5; i++){
			floors[i] = new BuildingFloor();
		}
	}

	//Returns the building floors
	public synchronized BuildingFloor[] getFloors()
	{
		return floors;
	}
	
	//Set Building Floor's passenger request array
	public synchronized void setFloorsPR(int numPass, int arrFloor, int destFloor)
	{
		this.floors[arrFloor].setPassengerRequests(numPass, destFloor);
	}
	
	//Set Building Floor's arrived passengers array
	public synchronized void setFloorsAP(int numPass, int arrFloor, int elevatorID){
		this.floors[arrFloor].setArrivedPassengers(numPass, elevatorID);
	}
	
	//Set Building Floor's total destination requests array
	public synchronized void setTotalDestinationRequests(int numPass, int arrFloor, int destFloor){
		this.floors[arrFloor].setTotalDestinationRequests(numPass, destFloor);
	}
	
	//Checks each floor for passenger requests and returns the floor number, else it returns -1
	public synchronized int requestingFloor(int elevatorID){
		for(int i = 0; i < floors.length; i++){
			for (int j = 0; j < floors[i].getPassengerRequests().length; j++){
				if(floors[i].getPassengerRequests()[j] > 0 && floors[i].getApproachingElevator() == -1){
					floors[i].setApproachingElevator(elevatorID);
					return i;
				}
			}
		}
		return -1;
	}
}
