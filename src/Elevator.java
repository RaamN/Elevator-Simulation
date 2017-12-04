//Nachiappan, Raam 45618246, 
//La, Brenda 27885244

import java.util.ArrayList;

public class Elevator implements Runnable
{
	private int elevatorID;
	private int currentFloor;
	private int numPassengers;
	private int totalLoadedPassengers;
	private int totalUnloadedPassengers;
	private ArrayList<ElevatorEvent> moveQueue;
	private int [] passengerDestinations;
	private BuildingManager manager;

	//Elevator constructor
	public Elevator(int elevatorID, BuildingManager manager){
		this.elevatorID = elevatorID;
		currentFloor = 0;
		numPassengers = 0;
		totalLoadedPassengers = 0;
		totalUnloadedPassengers = 0;
		moveQueue = new ArrayList<ElevatorEvent>();
		passengerDestinations = new int[5];
		this.manager = manager;
	}
	
	public void run()
	{
		//Running elevator simulation on separate threads before thread stops 
		while(!Thread.interrupted()){
			//Scanning for requests
			if(moveQueue.size() == 0){
				int requestingFloor = manager.requestingFloor(elevatorID);
				if(requestingFloor != -1){
					ElevatorEvent e = new ElevatorEvent();
					int diff = Math.abs(currentFloor - requestingFloor);
					int newTime = SimClock.getTime() + diff * 5 + 10;
					e.setExpectedArrival(newTime);
					e.setDestination(requestingFloor);
					moveQueue.add(e);
					System.out.println("Ts " + SimClock.getTime() + ": Elevator " + elevatorID + " detected passengers on Floor " + 
							requestingFloor + " ETA: " + moveQueue.get(0).getExpectedArrival());
				}
			}
			//Elevators have requests 
			else if (SimClock.getTime() >= moveQueue.get(0).getExpectedArrival()){
				//loading passengers
				if (numPassengers == 0){
					currentFloor = moveQueue.get(0).getDestination();
					int requestedFloorETA = moveQueue.get(0).getExpectedArrival();
					moveQueue.remove(0);
					boolean passengersGoingUp = false;
					//searching for passengers going up 
					for(int j = currentFloor; j < 5; j++){
						if(manager.getFloors()[currentFloor].getPassengerRequests()[j] > 0){
							passengersGoingUp = true;
							numPassengers += manager.getFloors()[currentFloor].getPassengerRequests()[j];
							ElevatorEvent e = new ElevatorEvent();
							int diff = Math.abs(currentFloor - j);
							int newTime = requestedFloorETA + 10 + diff * 5 + 10 * moveQueue.size();
							e.setExpectedArrival(newTime);
							e.setDestination(j);
							moveQueue.add(e);
							passengerDestinations[j] += manager.getFloors()[currentFloor].getPassengerRequests()[j];
							loadPassengers(manager.getFloors()[currentFloor].getPassengerRequests()[j], currentFloor, j, newTime);
						}
					}
					//searching for passengers going down 
					if(!passengersGoingUp){
						for(int j = currentFloor; j >= 0; j--){
							if(manager.getFloors()[currentFloor].getPassengerRequests()[j] > 0){
								numPassengers += manager.getFloors()[currentFloor].getPassengerRequests()[j];
								ElevatorEvent e = new ElevatorEvent();
								int diff = Math.abs(currentFloor - j);
								int newTime = requestedFloorETA + 10 + diff * 5 + 10 * moveQueue.size();
								e.setExpectedArrival(newTime);
								e.setDestination(j);
								moveQueue.add(e);
								passengerDestinations[j] += manager.getFloors()[currentFloor].getPassengerRequests()[j];
								loadPassengers(manager.getFloors()[currentFloor].getPassengerRequests()[j], currentFloor, j, newTime);
							}
						}
					}
					manager.getFloors()[currentFloor].setApproachingElevator(-1);
				}
				else
				{	//unloading passengers 
					currentFloor = moveQueue.get(0).getDestination();
					unloadPassengers(passengerDestinations[moveQueue.get(0).getDestination()], currentFloor, moveQueue.get(0).getDestination());
					moveQueue.remove(0);
				}
			}
		}
	}
	//unload passengers by interacting with building floor's arrived passengers array and prints statistics
	public void unloadPassengers(int numPass, int arrFloor, int destFloor){
		manager.setFloorsAP(passengerDestinations[arrFloor], arrFloor, elevatorID);
		totalUnloadedPassengers += passengerDestinations[destFloor];
		numPassengers -= passengerDestinations[destFloor];
		passengerDestinations[destFloor] = 0;
		System.out.println("Ts " + SimClock.getTime() + ": Elevator " + elevatorID + " unloaded " + numPass + " on Floor " + destFloor);
	}
	//load passengers by interacting with building floor's passenger requests array and prints statistics
	public void loadPassengers(int numPass, int arrFloor, int destFloor, int time){
		manager.setFloorsPR(0, arrFloor, destFloor);
		totalLoadedPassengers += passengerDestinations[destFloor];
		System.out.println("Ts " + SimClock.getTime() + ": Elevator " + elevatorID + " reached Floor " + currentFloor + ", loaded " + numPass + " passengers for "
			+ "passenger request Floor " + destFloor + " ETA: " + time);
	}
	//returns elevator ID that this running on the thread
	public int getElevatorID(){
		return elevatorID;
	}
	//returns current floor elevator is on 
	public int getCurrentFloor(){
		return currentFloor;
	}
	//prints statistics: the amount of passengers that unloaded on a certain elevator 
	public void simStateUnloadedPassengers(){
		System.out.println("Elevator " + elevatorID + " had " + totalUnloadedPassengers + " that exited the elevator throughout the simulation");
	}
	//prints statistics: the amount of passengers that loaded on a certain elevator
	public void simStateLoadedPassengers(){
		System.out.println("Elevator " + elevatorID + " had " + totalLoadedPassengers + " that entered the elevator throughout the simulation");
	}
	//prints statistics: the amount of passengers on a certain elevator heading to a specific floor 
	public int simStateCurrentPassengers(){
		for(int i = 0; i < passengerDestinations.length; i++){
			if(passengerDestinations[i] > 0){
				System.out.println("Elevator " + elevatorID + " had " + passengerDestinations[i] + " passengers currently heading to Floor " + i);
				return 1;
			}
		}
		System.out.println("Elevator " + elevatorID + " had 0 passengers currently");
		return 0;
	}

}
