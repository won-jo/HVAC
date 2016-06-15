
public class EnvironmentController {
	
	private HVAC hvac;
	
	private int heaterTurnOffTimer;
	private int coolerTurnOffTimer;
	
	private boolean coolOn;
	private boolean heatOn;
	
	public EnvironmentController(HVAC hvac) {
		this.hvac = hvac;
	}

	public void tick() {
		if(hvac.temp() < 71) {
			turnOnHeater();
			
			if(coolOn)
				turnOffCooler();
			
			if(isFanAvailable())
				hvac.fan(true);
		} else if(hvac.temp() > 72) {
			if(heatOn)
				turnOffHeater();
			
			turnOnCooler();
			
			if(isFanAvailable())
				hvac.fan(true);
		} else {
			if(heatOn)
				turnOffHeater();
			
			if(coolOn) 
				turnOffCooler();
			
			hvac.fan(false);
		}
		
		heaterTurnOffTimer = heaterTurnOffTimer == 0 ? 0 : heaterTurnOffTimer - 1;
		coolerTurnOffTimer = coolerTurnOffTimer == 0 ? 0 : coolerTurnOffTimer - 1;
 	}
	
	private void turnOffHeater() {
		hvac.heat(false);
		heaterTurnOffTimer = 6;
		heatOn = false;
		hvac.fan(false);
	}
	
	private void turnOffCooler() {
		hvac.cool(false);
		coolerTurnOffTimer = 4;
		coolOn = false;
		hvac.fan(false);
	}
	
	private void turnOnHeater() {
		hvac.heat(true);
		heatOn = true;
	}
	
	private void turnOnCooler() {
		hvac.cool(true);
		coolOn = true;
	}
	
	private boolean isFanAvailable() {
		return coolerTurnOffTimer == 0 && heaterTurnOffTimer == 0;
	}
	
	public HVAC getHvac() {
		return this.hvac;
	}
}
