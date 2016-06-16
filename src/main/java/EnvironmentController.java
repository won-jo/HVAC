
public class EnvironmentController {
	
	private HVAC hvac;
	
	private int heaterTurnOffTimer;
	private int coolerTurnOffTimer;
	
	private boolean coolOn;
	private boolean heatOn;
	
	private int lowRange;
	private int highRange;
	
	private final int MAX_TEMP = 130;
	private final int MIN_TEMP = 32;
	
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

	public int setLowRange(int lowRange) {
		if(lowRange > this.highRange)
			return 4002;
		if(lowRange < MIN_TEMP)
			return 4004;
		if(lowRange == this.highRange)
			return 4005;
		this.lowRange = lowRange;
		return 1001;
	}

	public int getHighRange() {
		return highRange;
	}
	
	public int getMAX_TEMP() {
		return MAX_TEMP;
	}

	public int getMIN_TEMP() {
		return MIN_TEMP;
	}

	public int setHighRange(int highRange) {
		if(highRange < this.lowRange)
			return 4001;
		if(highRange > MAX_TEMP)
			return 4003;
		if(highRange == this.lowRange)
			return 4005;
		this.highRange = highRange;
		return 1001;
	}

	public HVAC getHvac() {
		return this.hvac;
	}
}
