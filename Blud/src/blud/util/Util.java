package blud.util;

public class Util {	
	public static final float sin(float x) {
		return (float)java.lang.Math.sin(x);
	}
	
	public static final float cos(float x) {
		return (float)java.lang.Math.cos(x);
	}
	
	public static final float tan(float x) {
		return (float)java.lang.Math.tan(x);
	}
	
	public static final float toDegrees(float Θ) {
		return (float)java.lang.Math.toDegrees(Θ);
	}
	
	public static final float toRadians(float Θ) {
		return (float)java.lang.Math.toRadians(Θ);
	}
	
	public static final float min(float a, float b) {
		return a <= b ? a : b;
	}
	
	public static final float max(float a, float b) {
		return a >= b ? a : b;
	}
	
	public static final float box(float x, float a, float b) {
		if(x < a)
			x = a;
		if(x > b)
			x = b;
		return x;
	}
	
	public static final int round(float x) {
		return (int)(x + .5f);
	}
	
	public static final int stringToInt(String str) {
		return stringToInt(str, 0);
	}
	
	public static final int stringToInt(String str, int alt) {
		try {
			return Integer.parseInt(str);
		} catch(Exception ex) {
			return alt;
		}
	}
	
	public static final long stringToLong(String str) {
		return stringToLong(str, 0L);
	}
	
	public static final long stringToLong(String str, long alt) {
		try {
			return Long.parseLong(str);
		} catch(Exception ex) {
			return alt;
		}
	}
	
	public static final float stringToFloat(String str) {
		return stringToFloat(str, 0f);
	}
	
	public static final float stringToFloat(String str, float alt) {
		try {
			return Float.parseFloat(str);
		} catch(Exception ex) {
			return alt;
		}
	}
	
	public static final double stringToDouble(String str) {
		return stringToDouble(str, 0.);
	}
	
	public static final double stringToDouble(String str, double alt) {
		try {
			return Double.parseDouble(str);
		} catch(Exception ex) {
			return alt;
		}
	}
	
	public static final boolean stringToBoolean(String str) {
		return stringToBoolean(str, false);
	}
	
	public static final boolean stringToBoolean(String str, boolean alt) {
		try {
			return Boolean.parseBoolean(str);
		} catch(Exception ex) {
			return alt;
		}
	}
}
