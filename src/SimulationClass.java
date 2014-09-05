import java.util.Random;

/* This class simulate the senate bus problem creating a random number of riders and random number of buses */

public class SimulationClass {

	private SenateBusProblem problemInstance;
	private Thread[] buses;
	private Thread[] riders;
	private Bus busAction;
	private Rider riderAction;
	private int NumberOfRiders;
	private int NumberOfBuses;

	public static void main(String[] args) {
		SimulationClass simulator = new SimulationClass();
		simulator.setUp();
		try {
			simulator.simulate();
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}

	}

	private void simulate() throws InterruptedException {
		
		// start riders
		for (int i = 0; i < riders.length; i++) {
			riders[i] = new Thread(riderAction, "rider" + i);
			riders[i].start();
			System.out.println("Rider "+i+" started");
		}

		// start buses
		for (int i = 0; i < buses.length; i++) {
			// leave a time out between 2 buses
			buses[i].sleep(500);
			buses[i] = new Thread(busAction, "BUS-" + i);
			buses[i].start();
			System.out.println("Bus "+i+" started");
		}

	}

	private void setUp() {
		
		System.out.println("Initialization started");

		// initialize the objects
		problemInstance = new SenateBusProblem();
		busAction = new Bus();
		riderAction = new Rider();

		// initialize the random object
		Random random = new Random();

		// get random number of passengers and buses

		NumberOfBuses = random.nextInt(10) + 1;
		System.out.println(NumberOfBuses + " number of buses created");

		// to make sure that every passenger get a bus
		NumberOfRiders = random.nextInt(NumberOfBuses * 50) + 1;
		System.out.println(NumberOfRiders + " number of riders created");

		// create thread arrays for each rider and bus
		buses = new Thread[NumberOfBuses];
		riders = new Thread[NumberOfRiders];

		System.out.println("Initialization finished");
	}

}
