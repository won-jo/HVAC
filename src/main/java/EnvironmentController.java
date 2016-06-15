
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
		if(hvac.temp() < 71) {
			hvac.heat(true);
			heatStatus = true;
			
			if(coolStatus) {
				hvac.cool(false);
				coolerTurnOffTimer = 4;
				coolStatus = false;
				hvac.fan(false);
			}
			
			if(coolerTurnOffTimer == 0 && heaterTurnOffTimer == 0)
				hvac.fan(true);
		} else if(hvac.temp() > 72) {
			if(heatStatus) {
				hvac.heat(false);
				heaterTurnOffTimer = 6;
				heatStatus = false;
				hvac.fan(false);
			}
			
			hvac.cool(true);
			coolStatus = true;
			
			if(coolerTurnOffTimer == 0 && heaterTurnOffTimer == 0)
				hvac.fan(true);
		} else if(hvac.temp() >= 71 && hvac.temp() <= 72) {
			if(heatStatus) {
				hvac.heat(false);
				heaterTurnOffTimer = 6;
				heatStatus = false;
			}
			if(coolStatus) {
				hvac.cool(false);
				coolerTurnOffTimer = 4;
				coolStatus = false;
			}
			hvac.fan(false);
		}
		
		heaterTurnOffTimer = heaterTurnOffTimer == 0 ? 0 : heaterTurnOffTimer - 1;
		coolerTurnOffTimer = coolerTurnOffTimer == 0 ? 0 : coolerTurnOffTimer - 1;
 	}
}
