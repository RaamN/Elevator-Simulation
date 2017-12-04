//Nachiappan, Raam 45618246, 
//La, Brenda 27885244

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ElevatorSimulation
{
	//ElevatorSimulation Private Variables
	private int simLength;
	private int simSecond;
	private ArrayList<String> command;
	private ArrayList<ArrayList<PassengerArrival>> passengerArrivalList;
	private BuildingManager bm;
	private Elevator[] e;
	private Thread[] t;

	//ElevatorSimulation Default Constructor
	public ElevatorSimulation(){
		simLength = 0;
		simSecond = 0;
		command = new ArrayList<String>();
		passengerArrivalList = new ArrayList<ArrayList<PassengerArrival>>();
		bm = new BuildingManager();
		e = new Elevator[5];
		t = new Thread[5];
	
	}
	
	//Start Method calls readFile, and starts programs main while loop. 
	public void start(){
		readFile();
		//Creates 5 elevators, threads for each elevator, and starts them
		for(int i = 0; i < 5; i++){
			e[i] = new Elevator(i, bm);
			t[i] = new Thread(e[i]);
			t[i].start();
		}
		/*
		 * Runs program up until read simLength
		 * Iterates through passengerArrivalList to see when passengers spawn, get their destination floor, and get their rate of occurrence
		 * Prints out time stamps of when they arrive, their current floor, and the floor they request 
		*/
		while(SimClock.getTime() <= simLength){
			for (int i = 0; i < passengerArrivalList.size(); i++){
				for (int j=0; j < passengerArrivalList.get(i).size(); j++){
					int destination = passengerArrivalList.get(i).get(j).getDestinationFloor();
					if (SimClock.getTime() % passengerArrivalList.get(i).get(j).getTimePeriod() == 0 && SimClock.getTime() > 0){
						System.out.println("Ts " + SimClock.getTime() + ": " + passengerArrivalList.get(i).get(j).getNumPassengers() + 
							" passengers arrived on Floor " + i + " requesting Floor " + destination);
						bm.setFloorsPR(passengerArrivalList.get(i).get(j).getNumPassengers() + bm.getFloors()[i].getPassengerRequests()[destination],
								i, passengerArrivalList.get(i).get(j).getDestinationFloor());
						int newTime = updateESTArrivalTime(SimClock.getTime(), i, destination);
						passengerArrivalList.get(i).get(j).setExpectedTimeOfArrival(newTime);
						bm.setTotalDestinationRequests(passengerArrivalList.get(i).get(j).getNumPassengers(), i, destination);
					}
				}
			}
			
			//Makes program tick as long as a simulated second and then increments the tick
			try
			{
				Thread.sleep(simSecond);
			}
			catch (InterruptedException e1)
			{
				e1.printStackTrace();
			}
			SimClock.tick();
		}
		
		//Iterates through totalDestinationRequests to print the total number of passengers requesting access to each floor.
		System.out.println("\nTotal Number of Passengers Requesting Access to Each Floor: ");
		for(int i = 0; i < 5; i++){
			System.out.println("From Floor " + i + ": " + Arrays.toString(bm.getFloors()[i].getTotalDestinationRequests()));
		}
		
		//Iterates through arrivedPassengers to print the total number of passengers that exited an elevator on each floor.
		System.out.println("\nTotal Number of Passengers that Exited an Elevator on a Floor: ");
		for(int i = 0; i < 5; i++){
			System.out.println("To Floor " + i + ": " + Arrays.toString(bm.getFloors()[i].getArrivedPassengers()));
		}
		
		//Iterates through passengerRequests to print the current number of passengers that are waiting for an Elevator to go to each floor.
		System.out.println("\nCurrent Number of Passengers that are Waiting for an Elevator to go to a Floor: ");
		for(int i = 0; i < 5; i++){
			System.out.println("From Floor " + i + ": " + Arrays.toString(bm.getFloors()[i].getPassengerRequests()));
		}
		
		//Iterates through each floor to print the current elevator approaching it.
		System.out.println("\nCurrent Elevator Approaching each Floor for Passenger PickUp: ");
		for(int i = 0; i < bm.getFloors().length; i++){
			if(bm.getFloors()[i].getApproachingElevator() != -1)
				System.out.println("Floor " + i + " has elevator " + bm.getFloors()[i].getApproachingElevator() + " Approaching it for Passenger Pickup");
			else
				System.out.println("Floor " + i + " has no Elevator Approaching it for Passenger Pickup");
		}
		
		//Iterates through each elevator and prints statistics about it.
		for(int i = 0; i < 5; i++){
			System.out.println("\nELEVATOR ID: " + i);
			e[i].simStateLoadedPassengers();
			e[i].simStateUnloadedPassengers();
			e[i].simStateCurrentPassengers();
			t[i].interrupt();		
		}
	}
	
	/*
	 * Creates a scanner and reads ElevatorConfig.txt
	 * Parses the contents into integers and stores them in variables
	 * At the end it closes the scanner
	*/
	public void readFile(){
			Scanner inFile = null;
			try {
				inFile = new Scanner(new File("ElevatorConfig.txt"));
				simLength = Integer.parseInt(inFile.nextLine());
				simSecond = Integer.parseInt(inFile.nextLine());
				while(inFile.hasNextLine()){
					command.add(inFile.nextLine());
				}
				setPassengerArrivalList();
			} 
			catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			finally{
				if(inFile != null){
					inFile.close();
				}
			}
	}
	
	/*
	 * Iterates through the parsed file to create passenger arrival objects and adds them to an ArrayList of ArrayLists of PassengerArrival objects.
	 */
	public void setPassengerArrivalList(){
		for (int i = 0; i < command.size(); i++){
			String[] stringSplitted= command.get(i).split(";");
			ArrayList<PassengerArrival> listPA = new ArrayList<PassengerArrival>();
			for (int j =0; j < stringSplitted.length; j++){
				String[] jSplit = stringSplitted[j].split(" ");
				PassengerArrival passenger = new PassengerArrival();
				passenger.setNumPassengers(Integer.parseInt(jSplit[0]));
				passenger.setDestinationFloor(Integer.parseInt(jSplit[1]));
				passenger.setTimePeriod(Integer.parseInt(jSplit[2]));
				passenger.setExpectedTimeOfArrival(SimClock.getTime() + Integer.parseInt(jSplit[2]));
				listPA.add(passenger);
			}
			
			passengerArrivalList.add(listPA);
		}
	}
	
	/*
	 * Updates estimated arrival time for elevators when answering passenger requests.
	 */
	public int updateESTArrivalTime(int time, int floor, int destFloor){
		int diff = Math.abs(destFloor - floor);
		time += diff * 5 + 10;
		return time;
	}
		
	
}


