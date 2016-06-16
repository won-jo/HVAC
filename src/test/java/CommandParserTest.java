import static junit.framework.TestCase.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class CommandParserTest {
	
	private CommandParser parser;
	private HVAC hvac;
	
	
	@Before
	public void setup() {
		hvac = new HVACMock();
		parser = new CommandParser(hvac);
	}
	
	@Test
    public void increaseHighTemperatureValue() {
		parser.parse("set_high 80");
		
		int high = parser.getHighValue();
		
		assertEquals(80, high);
    }
    
    
    @Test
    public void decreaseLowTemperatureValue() {
    	parser.parse("set_low 60");
		
		int low = parser.getLowValue();
		
		assertEquals(60, low);
    }
    
    @Test
    public void wrongCommand() {
    	String response = parser.parse("increase temp to 120");
    	
    	assertEquals("wrong command", response);
    }
}
