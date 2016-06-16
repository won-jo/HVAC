
public class EnvironmentController {
	
	private HVAC hvac;
	
	private int heaterTurnOffTimer;
	private int coolerTurnOffTimer;
	
	private boolean coolOn;
	private boolean heatOn;
	
	private int lowRange;
	private int highRange;
	
	public EnvironmentController(HVAC hvac) {
		this.lowRange = 65;
		this.highRange = 75;
		this.hvac = hvac;
	}

	public void tick() {
		if(hvac.temp() < lowRange) {
			turnOnHeater();
			
			if(coolOn)
				turnOffCooler();
			
			if(isFanAvailable())
				hvac.fan(true);
		} else if(hvac.temp() > highRange) {
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
	
	public int getLowRange() {
		return lowRange;
	}

	public void setLowRange(int lowRange) {
		this.lowRange = lowRange;
	}

	public int getHighRange() {
		return highRange;
	}

	public int setHighRange(int highRange) {
		if(highRange < this.lowRange)
			return 4001;
		
		this.highRange = highRange;
		return 1001;
	}

	public HVAC getHvac() {
		return this.hvac;
	}
}
