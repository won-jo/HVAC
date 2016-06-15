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
	
	@Test
	public void tempLessThan65() {
		hvac.setTemp(60);
		controller.tick();
		
		Assert.assertTrue(hvac.isHeatOn());
		Assert.assertFalse(hvac.isCoolOn());
		Assert.assertTrue(hvac.isFanOn());
	}
	
	@Test
	public void tempGreaterThan75() {
		hvac.setTemp(80);
		controller.tick();
		
		Assert.assertTrue(hvac.isCoolOn());
		Assert.assertTrue(hvac.isFanOn());
		Assert.assertFalse(hvac.isHeatOn());
	}
	
	@Test
	public void fanCantRunFor5MinsAfterHeaterOff() {
		hvac.setTemp(60);
		controller.tick();
		
		Assert.assertTrue(hvac.isHeatOn());
		Assert.assertFalse(hvac.isCoolOn());
		Assert.assertTrue(hvac.isFanOn());
		
		// turning off the heater
		hvac.setTemp(70);
		controller.tick();
		
		Assert.assertFalse(hvac.isHeatOn());
		Assert.assertFalse(hvac.isCoolOn());
		Assert.assertFalse(hvac.isFanOn());
		
		// first minute after heat turn off
		hvac.setTemp(80);
		controller.tick();
		
		Assert.assertFalse(hvac.isHeatOn());
		Assert.assertTrue(hvac.isCoolOn());
		Assert.assertFalse(hvac.isFanOn());
		
		controller.tick();
		
		Assert.assertFalse(hvac.isHeatOn());
		Assert.assertTrue(hvac.isCoolOn());
		Assert.assertFalse(hvac.isFanOn());
		
		controller.tick();
		
		Assert.assertFalse(hvac.isHeatOn());
		Assert.assertTrue(hvac.isCoolOn());
		Assert.assertFalse(hvac.isFanOn());
		
		controller.tick();
		
		Assert.assertFalse(hvac.isHeatOn());
		Assert.assertTrue(hvac.isCoolOn());
		Assert.assertFalse(hvac.isFanOn());
		
		controller.tick();
		
		Assert.assertFalse(hvac.isHeatOn());
		Assert.assertTrue(hvac.isCoolOn());
		Assert.assertFalse(hvac.isFanOn());
		
		controller.tick();
		
		Assert.assertFalse(hvac.isHeatOn());
		Assert.assertTrue(hvac.isCoolOn());
		Assert.assertTrue(hvac.isFanOn());
	}
	
	@Test
	public void fanCantRunFor3MinsAfterCoolerOff() {
		hvac.setTemp(80);
		controller.tick();
		
		Assert.assertFalse(hvac.isHeatOn());
		Assert.assertTrue(hvac.isCoolOn());
		Assert.assertTrue(hvac.isFanOn());
		
		// turning off the cooler
		hvac.setTemp(70);
		controller.tick();
		
		Assert.assertFalse(hvac.isHeatOn());
		Assert.assertFalse(hvac.isCoolOn());
		Assert.assertFalse(hvac.isFanOn());
		
		// first minute after heat turn off
		hvac.setTemp(60);
		controller.tick();
				
		Assert.assertTrue(hvac.isHeatOn());
		Assert.assertFalse(hvac.isCoolOn());
		Assert.assertFalse(hvac.isFanOn());
		
		controller.tick();
		
		Assert.assertTrue(hvac.isHeatOn());
		Assert.assertFalse(hvac.isCoolOn());
		Assert.assertFalse(hvac.isFanOn());
		
		controller.tick();
		
		Assert.assertTrue(hvac.isHeatOn());
		Assert.assertFalse(hvac.isCoolOn());
		Assert.assertFalse(hvac.isFanOn());
		
		controller.tick();
		
		Assert.assertTrue(hvac.isHeatOn());
		Assert.assertFalse(hvac.isCoolOn());
		Assert.assertTrue(hvac.isFanOn());
	}
 
}
