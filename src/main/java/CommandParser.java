
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
			environmentController.setHighRange(Integer.parseInt(args[1]));
			return "ok";
		case "set_low":
			environmentController.setLowRange(Integer.parseInt(args[1]));
			return "ok";
		default:
			return "wrong command";
		}
	}
	
	public Integer getHighValue() {
		return environmentController.getHighRange();
	}
	
	public Integer getLowValue() {
		return environmentController.getLowRange();
	}
}
