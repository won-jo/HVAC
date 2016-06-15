import static junit.framework.TestCase.assertEquals;

import java.io.IOException;
import java.net.UnknownHostException;

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
    public void decreaseLowTemperatureValue() throws UnknownHostException, IOException {
    	parser.parse("set_low 60");
		
		int low = parser.getLowValue();
		
		assertEquals(60, low);
    }
}
