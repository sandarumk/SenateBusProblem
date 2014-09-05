
public class Bus implements Runnable {
	private SenateBusProblem senateBusProblemInstance;
	
	

	public Bus() {
		senateBusProblemInstance= SenateBusProblem.getInstance();
	}



	@Override
	public void run() {
		try {
			senateBusProblemInstance.busAction();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}
