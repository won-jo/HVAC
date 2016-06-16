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
		String response = parser.parse("set_high 80");
		
		int high = parser.getHighValue();
		
		assertEquals("code - 1001 : description - success", response);
		assertEquals(80, high);
    }
    
    
    @Test
    public void decreaseLowTemperatureValue() {
    	String response = parser.parse("set_low 60");
		
		int low = parser.getLowValue();
		
		assertEquals("code - 1001 : description - success", response);
		assertEquals(60, low);
    }
    
    @Test
    public void wrongCommand() {
    	String response = parser.parse("increase temp to 120");
    	
    	assertEquals("code - 4100 : description - wrong command", response);
    }
    
    @Test
    public void setHighRangeValueLessThanLowRange() {
    	parser.parse("set_low 70");
    	
    	String response = parser.parse("set_high 69");
    	
    	assertEquals("code - 4001 : description - high range must be greater than low range", response);
    	
    }
    
    @Test
    public void setLowRangeValueGreaterThanHighRange() {
    	parser.parse("set_high 70");
    	
    	String response = parser.parse("set_low 75");
    	
    	assertEquals("code - 4002 : description - low range must be less than high range", response);
    }
    
    @Test
    public void setHighRangeGreaterThanMaxTempAllowed() {
    	String response = parser.parse("set_high 150");
    	
    	assertEquals("code - 4003 : description - high range must be less than 130", response);
    	
    }
    
    @Test
    public void setLowRangeLessThanMinTempAllowed() {
    	String response = parser.parse("set_low 20");
    	
    	assertEquals("code - 4004 : description - low range must be greater than 32", response);
    }
    
    @Test
    public void invalidArguments() {
    	String response = parser.parse("set_low 12ab");
    	
    	assertEquals("code - 4101 : description - invalid arguments", response);
    	
    	response = parser.parse("set_high 12ab");
    	
    	assertEquals("code - 4101 : description - invalid arguments", response);
    }
}
