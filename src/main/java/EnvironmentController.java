
public class EnvironmentController {
	
	private HVAC hvac;
	
	private int heaterTurnOffTimer;
	private int coolerTurnOffTimer;
	
	private boolean coolStatus;
	private boolean heatStatus;
	
	public EnvironmentController(HVAC hvac) {
		this.hvac = hvac;
	}

	public void tick() {
		
		if(hvac.temp() < 65) {
			hvac.heat(true);
			heatStatus = false;
			hvac.fan(true);
			if(coolStatus) {
				hvac.cool(false);
				coolerTurnOffTimer = 3;
				coolStatus = false;
			}
		} else if(hvac.temp() > 75) {
			if(heatStatus) {
				hvac.heat(false);
				heaterTurnOffTimer = 5;
				heatStatus = false;
			}
			hvac.fan(true);
			hvac.cool(true);
			coolStatus = true;
		}
	}

}
