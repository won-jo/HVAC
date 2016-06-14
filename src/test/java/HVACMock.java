
public class HVACMock implements HVAC {
	
	private boolean heatStatus;
	private boolean coolStatus;
	private boolean fanStatus;
	private int temp;
	
	@Override
	public void heat(boolean on) {
		heatStatus = on;
	}

	@Override
	public void cool(boolean on) {
		coolStatus = on;
	}

	@Override
	public void fan(boolean on) {
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
	
}
