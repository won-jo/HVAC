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
	public void temperatureBetween71And72() {
		hvac.setTemp(71);
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
	public void fanCantRunAfter5MinAfterHeaterOff() {
		hvac.setTemp(60);
		controller.tick();
		
		// turning off the heater
		hvac.setTemp(71);
		controller.tick();
		
		hvac.setTemp(80);
		for(int i = 0; i < 5; i++) {
			controller.tick();
			
			Assert.assertFalse(hvac.isHeatOn());
			Assert.assertTrue(hvac.isCoolOn());
			Assert.assertFalse(hvac.isFanOn());
		}
	}
	
	@Test
	public void fanCanRunAfter6MinsAfterHeaterOff() {
		hvac.setTemp(60);
		controller.tick();
		
		// turning off the heater
		hvac.setTemp(71);
		controller.tick();
		
		hvac.setTemp(80);
		controller.tick();
		controller.tick();
		controller.tick();
		controller.tick();
		controller.tick();
		controller.tick();
		
		Assert.assertFalse(hvac.isHeatOn());
		Assert.assertTrue(hvac.isCoolOn());
		Assert.assertTrue(hvac.isFanOn());
	}
	
	@Test
	public void fanCantRunAfter3MinAfterCoolerOff() {
		hvac.setTemp(80);
		controller.tick();
		
		// turning off the cooler
		hvac.setTemp(72);
		controller.tick();
		
		hvac.setTemp(60);
		
		for(int i = 0; i < 3; i++) {
			controller.tick();
					
			Assert.assertTrue(hvac.isHeatOn());
			Assert.assertFalse(hvac.isCoolOn());
			Assert.assertFalse(hvac.isFanOn());
		}
	}
	
	@Test
	public void fanCanRunAfterr4MinAfterCoolerOff() {
		hvac.setTemp(80);
		controller.tick();
		
		// turning off the cooler
		hvac.setTemp(72);
		controller.tick();
		
		// first minute after heat turn off
		hvac.setTemp(60);
		controller.tick();
		controller.tick();
		controller.tick();
		controller.tick();
		
		Assert.assertTrue(hvac.isHeatOn());
		Assert.assertFalse(hvac.isCoolOn());
		Assert.assertTrue(hvac.isFanOn());
	}
	
	@Test
	public void fromCoolerToHeater() {
		hvac.setTemp(76);
		controller.tick();
		
		hvac.setTemp(60);
		controller.tick();
		
		Assert.assertTrue(hvac.isHeatOn());
		Assert.assertFalse(hvac.isCoolOn());
		Assert.assertFalse(hvac.isFanOn());
	}
	
	@Test
	public void fromHeaterToCooler() {
		hvac.setTemp(60);
		controller.tick();
		
		hvac.setTemp(76);
		controller.tick();
		
		Assert.assertFalse(hvac.isHeatOn());
		Assert.assertTrue(hvac.isCoolOn());
		Assert.assertFalse(hvac.isFanOn());
	}
	
	@Test
	public void constructor() {
		HVAC hvac = new HVACMock();
		EnvironmentController controller = new EnvironmentController(hvac);
		
		Assert.assertEquals(controller.getHvac(), hvac);
	}
 
}
