
public class HVACMock implements HVAC {
	
	private boolean heatStatus;
	private boolean coolStatus;
	private boolean fanStatus;
	private int temp;
	
	private int heaterTurnOffTimer;
	private int coolerTurnOffTimer;
	

	@Override
	public void heat(boolean on) {
		if(heatStatus && !on)
			heaterTurnOffTimer = 5;
		heatStatus = on;
	}

	@Override
	public void cool(boolean on) {
		if(coolStatus && !on)
			coolerTurnOffTimer = 3;
		coolStatus = on;
	}

	@Override
	public void fan(boolean on) {
		if(coolerTurnOffTimer == 0 && heaterTurnOffTimer == 0)
			fanStatus = on;
	}

	@Override
	public int temp() {
		return temp;
	}
	
	public void setTemp(int temp) {
		this.temp = temp;
	}
	
	public boolean isHeatOn() {
		return heatStatus;
	}
	
	public boolean isCoolOn() {
		return coolStatus;
	}
	
	public boolean isFanOn() {
		return fanStatus;
	}
	
	public void tick() {
		coolerTurnOffTimer = coolerTurnOffTimer == 0 ? 0 : coolerTurnOffTimer - 1;
		heaterTurnOffTimer = heaterTurnOffTimer == 0 ? 0 : heaterTurnOffTimer - 1;
	}
}
