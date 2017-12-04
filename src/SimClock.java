//Nachiappan, Raam 45618246, 
//La, Brenda 27885244

public class SimClock
{
	private volatile static int time;
	
	//SimClock constructor
	public SimClock(){
		time = 0;
	}
	
	//Increments the time 
	public static void tick(){
		time++;
	}
	
	//Returns the current time 
	public static int getTime(){
		return time;
	}
}
