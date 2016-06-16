
public class CommandParser {
	
	private EnvironmentController environmentController;
	
	public CommandParser() {
		HVAC hvac = new MyHVACImpl();
		environmentController = new EnvironmentController(hvac);
	}
	
	public CommandParser(HVAC hvac) {
		environmentController = new EnvironmentController(hvac);
	}

	public String parse(String command) {
		String[] args = command.split(" ");
		
		switch (args[0]) {
		case "set_high":
			int response = environmentController.setHighRange(Integer.parseInt(args[1]));
			if(response == 1001)
				return "ok";
			else if(response == 4001)
				return "code - 4001 : description - high range must be greater than low range";
		case "set_low":
			environmentController.setLowRange(Integer.parseInt(args[1]));
			return "ok";
		default:
			return "code - 4100 : description - wrong command";
		}
	}
	
	public Integer getHighValue() {
		return environmentController.getHighRange();
	}
	
	public Integer getLowValue() {
		return environmentController.getLowRange();
	}
}
