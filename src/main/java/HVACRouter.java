import java.util.function.Function;

public class HVACRouter {
	
	private CommandParser parser;
	
	Function<String, String> hvacRouter = new Function<String, String>() {
	    public String apply(String command) {
	    	String result = parser.parse(command);
	        return result;
	    }
	};

	public HVACRouter() {
		if(this.parser == null)
			this.parser = new CommandParser();
	}

	public Function<String, String> getHvacRouter() {
		return hvacRouter;
	}
	
	public Integer getHighValue() {
		return parser.getHighValue();
	}
	
	public Integer getLowValue() {
		return parser.getLowValue();
	}
}
