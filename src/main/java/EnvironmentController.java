
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
			turnOnHeater();
			
			if(coolStatus)
				turnOffCooler();
			
			if(isFanAvailable())
				hvac.fan(true);
		} else if(hvac.temp() > 72) {
			if(heatStatus)
				turnOffHeater();
			
			turnOnCooler();
			
			if(isFanAvailable())
				hvac.fan(true);
		} else {
			if(heatStatus)
				turnOffHeater();
			
			if(coolStatus) 
				turnOffCooler();
			
			hvac.fan(false);
		}
		
		heaterTurnOffTimer = heaterTurnOffTimer == 0 ? 0 : heaterTurnOffTimer - 1;
		coolerTurnOffTimer = coolerTurnOffTimer == 0 ? 0 : coolerTurnOffTimer - 1;
 	}
	
	private void turnOffHeater() {
		hvac.heat(false);
		heaterTurnOffTimer = 6;
		heatStatus = false;
		hvac.fan(false);
	}
	
	private void turnOffCooler() {
		hvac.cool(false);
		coolerTurnOffTimer = 4;
		coolStatus = false;
		hvac.fan(false);
	}
	
	private void turnOnHeater() {
		hvac.heat(true);
		heatStatus = true;
	}
	
	private void turnOnCooler() {
		hvac.cool(true);
		coolStatus = true;
	}
	
	private boolean isFanAvailable() {
		return coolerTurnOffTimer == 0 && heaterTurnOffTimer == 0;
	}
	
	public HVAC getHvac() {
		return this.hvac;
	}
}
