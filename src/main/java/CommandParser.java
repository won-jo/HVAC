
public class CommandParser {
	
	private EnvironmentController ec;
	
	public CommandParser() {
		HVAC hvac = new MyHVACImpl();
		ec = new EnvironmentController(hvac);
	}
	
	public CommandParser(HVAC hvac) {
		ec = new EnvironmentController(hvac);
	}

	public String parse(String command) {
		String[] args = command.split(" ");
		int response;
		try {
			switch (args[0]) {
				case "set_high":
					response = ec.setHighRange(Integer.parseInt(args[1]));
					break;
				case "set_low":
					response = ec.setLowRange(Integer.parseInt(args[1]));
					break;
				default:
					response = 4100;
			}
		} catch(NumberFormatException e) {
			response = 4101;
		}
		
		switch (response) {
			case 1001:
				return "code - 1001 : description - success";
			case 4001:
				return "code - 4001 : description - high range must be greater than low range";
			case 4003:
				return "code - 4003 : description - high range must be less than " + ec.getMAX_TEMP();
			case 4002:
				return "code - 4002 : description - low range must be less than high range";
			case 4004:
				return "code - 4004 : description - low range must be greater than " + ec.getMIN_TEMP();
			case 4005:
				return "code - 4005 : description - high and low range can not be equal";
			case 4100:
				return "code - 4100 : description - wrong command";
			case 4101:
				return "code - 4101 : description - invalid arguments";
			default:  
				return "code - 5001 : description - server error";
		}
	}
	
	public Integer getHighValue() {
		return ec.getHighRange();
	}
	
	public Integer getLowValue() {
		return ec.getLowRange();
	}
}
