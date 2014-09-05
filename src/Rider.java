
public class Rider implements Runnable {
	private SenateBusProblem senateBusProblemInstance;

	public Rider() {
		senateBusProblemInstance= SenateBusProblem.getInstance();
	}
	@Override
	public void run() {
	 try {
		senateBusProblemInstance.riderAction();
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
		
	}

}
