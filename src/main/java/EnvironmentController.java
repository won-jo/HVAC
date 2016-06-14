
public class EnvironmentController {
	
	private HVAC hvac;
	
	public EnvironmentController(HVAC hvac) {
		this.hvac = hvac;
	}

	public void tick() {
		
		if(hvac.temp() < 65) {
			hvac.heat(true);
			hvac.fan(true);
			hvac.cool(false);
		} else if(hvac.temp() > 75) {
			hvac.heat(false);
			hvac.fan(true);
			hvac.cool(true);
		}
		
	}

}
