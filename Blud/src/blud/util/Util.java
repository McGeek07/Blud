package blud.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.List;

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
	
	public static File validate(File file) {
		if(!file.exists())
			try {
				if(file.getParentFile() != null)
					file.getParentFile().mkdirs();
				file.createNewFile();
			} catch(IOException ioe) {
				System.err.println("Unable to validate file \"" + file + "\"");
				ioe.printStackTrace();
			}
		return file;
	}
	
	public static ObjectOutputStream createObjectOutputStream(String path, boolean append) {
		return createObjectOutputStream(new File(path), append);
	}
	
	public static ObjectOutputStream createObjectOutputStream(File file  , boolean append) {
		try {
			return new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(validate(file), append)));
		} catch (IOException ioe) {
			System.err.println("Unable to open file \"" + file + "\"");
		}
		return null;
	}
	
	public static ObjectInputStream createObjectInputStream(String path) {
		return createObjectInputStream(new File(path));
	}
	
	public static ObjectInputStream createObjectInputStream(File file  ) {
		try {
			return new ObjectInputStream(new BufferedInputStream(new FileInputStream(validate(file))));
		} catch(IOException ioe) {
			System.err.println("Unable to open file \"" + file + "\"");
		}
		return null;
	}
	
	public static BufferedWriter createBufferedWriter(String path, boolean append) {
		return createBufferedWriter(new File(path), append);
	}
	
	public static BufferedWriter createBufferedWriter(File file  , boolean append) {
		try {
			return new BufferedWriter(new FileWriter(validate(file), append));
		} catch(IOException ioe) {
			System.err.println("Unable to open file \"" + file + "\"");
		}
		return null;
	}
	
	public static PrintWriter createPrintWriter(String path, boolean append) {
		return createPrintWriter(new File(path), append);
	}
	
	public static PrintWriter createPrintWriter(File file  , boolean append) {
		try {
			return new PrintWriter(new BufferedWriter(new FileWriter(validate(file), append)));
		} catch(IOException ioe) {
			System.err.println("Unable to open file \"" + file + "\"");
		}
		return null;
	}
	
	public static BufferedReader createBufferedReader(String path) {
		return createBufferedReader(new File(path));
	}
	
	public static BufferedReader createBufferedReader(File file  ) {
		try {
			return new BufferedReader(new FileReader(validate(file)));
		} catch(IOException ioe) {
			System.err.println("Unable to open file \"" + file + "\"");
		}
		return null;
	}
	
	@SafeVarargs
	public static <T> void writeToFile(String path, boolean append, T... list) {
		writeToFile(new File(path), append, list);
	}
	
	@SafeVarargs
	public static <T> void writeToFile(File file  , boolean append, T... list) {
		try(ObjectOutputStream out = createObjectOutputStream(file, append)) {
			out.writeObject(list);
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public static <T> void writeToFile(String path, boolean append, Iterable<T> list) {
		writeToFile(new File(path), append, list);
	}
	
	public static <T> void writeToFile(File file  , boolean append, Iterable<T> list) {
		try(ObjectOutputStream out = createObjectOutputStream(file, append)) {
			out.writeObject(list);
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public static <T> List<T> readFromFile(String path, List<T> list) {
		return readFromFile(new File(path), list);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> List<T> readFromFile(File file  , List<T> list) {
		try(ObjectInputStream in = createObjectInputStream(file)) {
			Object o = in.readObject();
			if(o instanceof Iterable)
				for(T t: (Iterable<T>)o)
					list.add(t);
			else
				for(T t: (T[])o)
					list.add(t);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}
	
	@SafeVarargs
	public static <T> void printToFile(String path, boolean append, T... list) {
		printToFile(path, append, list);
	}
	
	@SafeVarargs
	public static <T> void printToFile(File file  , boolean append, T... list) {
		try(BufferedWriter out = createBufferedWriter(file, append)) {
			for(T t: list)
				out.write(String.format(t + "%n"));
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public static <T> void printToFile(String path, boolean append, Iterable<T> list) {
		printToFile(new File(path), append, list);
	}
	
	public static <T> void printToFile(File file  , boolean append, Iterable<T> list) {
		try(BufferedWriter out = createBufferedWriter(file, append)) {
			for(T t: list)
				out.write(String.format(t + "%n"));
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public static List<String> parseFromFile(String path, List<String> list) {
		return parseFromFile(new File(path), list);
	}
	
	public static List<String> parseFromFile(File file  , List<String> list) {
		try(BufferedReader in = createBufferedReader(file)) {
			while(in.ready()) list.add(in.readLine());
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
		return list;
	}
	
	public static List<String> parseFromResource(Class<?> clazz, String name, List<String> list) {
		try(BufferedReader in = new BufferedReader(new InputStreamReader(clazz.getResourceAsStream(name)))) {
			while(in.ready()) list.add(in.readLine());
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
		return list;
	}
	
	public static void clearFile(String path) {
		clearFile(new File(path));
	}
	
	public static void clearFile(File file  ) {
		try(BufferedWriter out = createBufferedWriter(file, false)) {
			out.write("");
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
