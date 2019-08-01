package blud.geom;

import blud.util.Util;

public class Vector2f extends Vector {
	private static final long 
		serialVersionUID = 1L;
	protected float
		X,
		Y;
	
	public Vector2f() {
		//do nothing
	}
	
	public Vector2f(Vector v) {
		this.X = v.X();
		this.Y = v.Y();
	}
	
	public Vector2f(float x, float y) {
		this.X = x;
		this.Y = y;
	}
	
	@Override
	public float X() { 
		return this.X; 
	}
	
	@Override
	public float Y() {
		return this.Y;
	}

	@Override
	public Vector2f copy() {
		return new Vector2f(this);
	}
	
	@Override
	public String toString() {
		return Vector2f.toString(this, "%s");
	}
	
	public static String toString(Vector2f v2, String format) {
		return 
				"<" + String.format(format, v2.X) + ", " 
					+ String.format(format, v2.Y) + ">";
	}
	
	protected static final <V extends Vector2f> V parseVector2f(V v2, String str) {
		if(v2 == null)
            throw new IllegalArgumentException("Null Vector");
        if (str == null)
            throw new IllegalArgumentException("Null String");
        if ((str = str.trim()).isEmpty())
            throw new IllegalArgumentException("Empty String");
        int
            a = str.indexOf("<"),
            b = str.indexOf(">");
        if (a >= 0 || b >= 0) {
            if (b > a) {
                str = str.substring(++a, b);
            } else {
                str = str.substring(++a);
            }
        }
        String[] temp = str.split("\\,");
        float[] arr = new float[temp.length];
        for (int i = 0; i < temp.length; i++) {
            arr[i] = Util.stringToFloat(temp[i]);
        }
        switch (arr.length) {
            default:
            case 2:
                v2.Y = arr[Y_AXIS];
            case 1:
                v2.X = arr[X_AXIS];
            case 0:
        }
        return v2;
	}
	
	public static Vector2f parseVector2f(String str) {
		return Vector2f.parseVector2f(new Vector2f(), str);
	}
	
	public static class Mutable extends Vector2f {
		private static final long 
			serialVersionUID = 1L;
		
		public Mutable() {
			super();
		}
		
		public Mutable(Vector v) {
			super(v);
		}
		
		public Mutable(float x, float y) {
			super(x, y);
		}
		
		public Vector2f.Mutable set(Vector v) {
			this.X = v.X();
			this.Y = v.Y();
			return this;
		}
		
		public Vector2f.Mutable set(float x, float y) {
			this.X = x;
			this.Y = y;
			return this;
		}
		
		public Vector2f.Mutable setX(float x) {
			this.X = x;
			return this;
		}
		
		public Vector2f.Mutable setY(float y) {
			this.Y = y;
			return this;
		}
		
		@Override
		public Vector2f.Mutable copy() {
			return new Vector2f.Mutable(this);
		}
		
		public static Vector2f.Mutable parseVector2f(String str) {
			return Vector2f.parseVector2f(new Vector2f.Mutable(), str);
		}
	}
}
