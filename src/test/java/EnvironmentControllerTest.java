import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EnvironmentControllerTest {
	
	private EnvironmentController controller;
	private HVACMock hvac;
	
	@Before
	public void setup() {
		hvac = new HVACMock();
		controller = new EnvironmentController(hvac);
	}
	
	@Test
	public void temperatureBetween65And75() {
		hvac.setTemp(67);
		controller.tick();
		
		Assert.assertFalse(hvac.isFanOn());
		Assert.assertFalse(hvac.isCoolOn());
		Assert.assertFalse(hvac.isHeatOn());
	}

}
