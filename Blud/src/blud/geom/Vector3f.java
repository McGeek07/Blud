package blud.geom;

import blud.util.Util;

public class Vector3f extends Vector {
	private static final long 
		serialVersionUID = 1L;
	protected float
		X,
		Y,
		Z;
	
	public Vector3f() {
		//do nothing
	}
	
	public Vector3f(Vector v) {
		this.X = v.X();
		this.Y = v.Y();
		this.Z = v.Z();
	}
	
	public Vector3f(float x, float y, float z) {
		this.X = x;
		this.Y = y;
		this.Z = z;
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
	public float Z() {
		return this.Z;
	}

	@Override
	public Vector3f copy() {
		return new Vector3f(this);
	}
	
	@Override
	public String toString() {
		return Vector3f.toString(this, "%s");
	}
	
	public static String toString(Vector3f v3, String format) {
		return 
				"<" + String.format(format, v3.X) + ", " 
					+ String.format(format, v3.Y) + ", "
					+ String.format(format, v3.Z) + ">";
	}
	
	protected static final <V extends Vector3f> V parseVector3f(V v3, String str) {
		if(v3 == null)
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
            case 3:
            	v3.Z = arr[Z_AXIS];
            case 2:
                v3.Y = arr[Y_AXIS];
            case 1:
                v3.X = arr[X_AXIS];
            case 0:
        }
        return v3;
	}
	
	public static Vector3f parseVector3f(String str) {
		return Vector3f.parseVector3f(new Vector3f(), str);
	}
	
	public static class Mutable extends Vector3f {
		private static final long 
			serialVersionUID = 1L;
		
		public Mutable() {
			super();
		}
		
		public Mutable(Vector v) {
			super(v);
		}
		
		public Mutable(float x, float y, float z) {
			super(x, y, z);
		}
		
		public Vector3f.Mutable set(Vector v) {
			this.X = v.X();
			this.Y = v.Y();
			this.Z = v.Z();
			return this;
		}
		
		public Vector3f.Mutable set(float x, float y, float z) {
			this.X = x;
			this.Y = y;
			this.Z = z;
			return this;
		}
		
		public Vector3f.Mutable setX(float x) {
			this.X = x;
			return this;
		}
		
		public Vector3f.Mutable setY(float y) {
			this.Y = y;
			return this;
		}
		
		public Vector3f.Mutable setZ(float z) {
			this.Z = z;
			return this;
		}
		
		@Override
		public Vector3f.Mutable copy() {
			return new Vector3f.Mutable(this);
		}
		
		public static Vector3f.Mutable parseVector3f(String str) {
			return Vector3f.parseVector3f(new Vector3f.Mutable(), str);
		}
	}
}
