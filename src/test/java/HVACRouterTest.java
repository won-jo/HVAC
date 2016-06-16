import static junit.framework.TestCase.assertEquals;

import java.io.IOException;
import java.net.UnknownHostException;

import org.junit.Before;
import org.junit.Test;

public class HVACRouterTest {
	
	private HVACRouter router;
	
	@Before
	public void setup() {
		router = new HVACRouter();
	}
	
	@Test
    public void increaseHighTemperatureValue() {
		router.getHvacRouter().apply("set_high 80");
		
		int high = router.getHighValue();
		
		assertEquals(80, high);
    }
    
    
    @Test
    public void decreaseLowTemperatureValue() throws UnknownHostException, IOException {
    	router.getHvacRouter().apply("set_low 60");
		
		int low = router.getLowValue();
		
		assertEquals(60, low);
    }
    
    @Test
    public void wrongCommand() {
    	String response = router.getHvacRouter().apply("increase temp to 100");
    	
    	assertEquals("code - 4100 : description - wrong command", response);
    	
    }
}
