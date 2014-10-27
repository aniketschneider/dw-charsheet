package personal.aschneider.charsheet;

import java.io.Serializable;

public class AbilityScore implements Serializable {
	private static final long serialVersionUID = 6537222532616210327L;
	
	private String longName;
	private int value;
	
	public AbilityScore(String longName, int initialValue) {
		this.longName = longName;
		this.setValue(initialValue);
	}
	
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		if (value >= 18)
			this.value = 18;
		else if (value <= 1)
			this.value = 1;
		else
			this.value = value;
	}
	public int getModifier() {
		if (value <= 3)	return -3;
		else if (value <= 5) return -2;
		else if (value <= 8) return -1;
		else if (value <= 12) return 0;
		else if (value <= 15) return 1;
		else if (value <= 17) return 2;
		else return 3;
	}
	public String getLongName() {
		return longName;
	}
	public String getShortName() {
		return longName.substring(0,3).toUpperCase();
	}
}
