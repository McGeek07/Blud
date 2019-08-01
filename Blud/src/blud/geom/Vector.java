package blud.geom;

import java.io.Serializable;

import blud.util.Copyable;

public abstract class Vector implements Copyable<Vector>, Serializable {
	private static final long 
		serialVersionUID = 1L;
	public static final int
		X_AXIS = 0,
		Y_AXIS = 1,
		Z_AXIS = 2;
	
	public float X() { return 0f; }
	public float Y() { return 0f; }
	public float Z() { return 0f; }	
	public int x() { return (int)X(); }
	public int y() { return (int)Y(); }
	public int z() { return (int)Z(); }
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Vector) {
			Vector v = (Vector)o;
			return 
					this.X() == v.X() &&
					this.Y() == v.Y() &&
					this.Z() == v.Z();
		}
		return false;
	}	
}
