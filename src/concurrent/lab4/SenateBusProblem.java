package concurrent.lab4;
import java.util.concurrent.Semaphore;

/* Assumptions : Maximum riders the waiting area has is 50. But the bus halt can have more than 50 riders. 
 Only the riders in the waiting area get the chance to board the bus
 */

public class SenateBusProblem {

	private static SenateBusProblem senateBusProblemInstance;

	public static synchronized SenateBusProblem getInstance() {
		if (senateBusProblemInstance == null) {
			senateBusProblemInstance = new SenateBusProblem();
		}
		return senateBusProblemInstance;
	}

	private int waitingRiders = 0; // initialize the riders in the bus stop as 0
	
	// set an lock to the waiting area so no more riders can enter it when a bus is there
	private Semaphore mutexWaitingArea = new Semaphore(1); 
	
	// set an lock to the waiting area so it can have only 50 riders at that time.
	private Semaphore multiplexRider = new Semaphore(50); 
	
	// set an lock on the bus waiting to be board.
	private Semaphore busWaiting = new Semaphore(0); 
	
	// set the lock on the bus signaling all the riders in the waiting area has boarded in to the bus
	private Semaphore allAboard = new Semaphore(0); 
	
	// the bus which is currently in the bus stop
	private Thread currentBus; 

	public void riderAction() throws InterruptedException {

		// limit the number of waiting riders to 50 in the waiting area.
		// if this cannot be acquired, then you can't enter to the waiting area
		// although you are in the bus stop.
		multiplexRider.acquire();

		// acquire the waiting area. This signals that there is no bus waiting
		// if there is already a bus in the bus stop you can't go to waiting
		// area.
		mutexWaitingArea.acquire();

		System.out.println(Thread.currentThread().getName()
				+ "arrived in waiting area");

		waitingRiders++;

		// release the lock on the waiting area so that other rider can enter
		mutexWaitingArea.release();

		// rider wait in the waiting area until a bus arrives.
		// when a bus comes the rider acquire the bus so that any other can't
		// get in
		busWaiting.acquire();

		// rider board the bus
		boardBus();

		// release the lock in waiting area so others can enter
		multiplexRider.release();

		if (waitingRiders == 0) {
			// when the riders in the waiting area is 0 signal the bus that
			// there are no more riders so that the bus can go
			allAboard.release();

		} else {
			// if there are more riders release the bus so that others can get
			// in
			busWaiting.release();
		}

	}

	public void busAction() throws InterruptedException {

		// acquire the lock so that any riders can't enter the waiting area
		mutexWaitingArea.acquire();

		currentBus = Thread.currentThread();

		if (waitingRiders > 0) {
			// if there are riders waiting signal the bus to wait
			busWaiting.release();
			// wait till all the riders get in to bus
			// lock can be acquired only if a rider release it signaling all the
			// riders has got in
			allAboard.acquire();
		}
		depart();

		// release the lock on waiting area so others can enter
		mutexWaitingArea.release();

	}

	private void depart() {
		// bus depart
		System.out.println(Thread.currentThread().getName() + " depart");

	}

	private void boardBus() {
		System.out.println(Thread.currentThread().getName() + " board in to "
				+ currentBus.getName());
		waitingRiders--;
	}

}
