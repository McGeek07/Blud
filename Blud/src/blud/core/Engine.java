package blud.core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import blud.Blud;
import blud.core.Renderable.RenderContext;
import blud.core.Updateable.UpdateContext;
import blud.core.input.Input;
import blud.core.scene.Scene;
import blud.geom.Vector2f;
import blud.util.Util;

public class Engine implements Runnable {
	public static final Engine
		INSTANCE = new Engine();
	protected static final int
		NUM_BUFFERS = 2,
		WINDOW_W = 512,
		WINDOW_H = 512,
		CANVAS_W = 64,
		CANVAS_H = 64,
		FPS = 30,
		TPS = 30;
	
	protected final Canvas
		canvas = new Canvas(this);
	protected final Window
		window = new Window(this);
	
	protected Scene
		scene;
	
	protected boolean
		running;
	protected Thread
		thread;
	protected long
		fps,
		tps;	
	
	protected Engine() {
		//do nothing
	}
	
	public static void init() {
		if(!INSTANCE.running) {
			INSTANCE.thread = new Thread(INSTANCE);
			INSTANCE.running = true;
			INSTANCE.thread.start();
		}
	}
	
	public static void exit() {
		if( INSTANCE.running) {
			INSTANCE.running = false;
		}
	}
	
	public static void setScene(Scene scene) {
		if(INSTANCE.scene != null)
			INSTANCE.scene.onDetach();
		INSTANCE.scene = scene;
		if(INSTANCE.scene != null)
			INSTANCE.scene.onAttach();
	}
	
	public static Vector2f windowToCanvas(Vector2f v) {
		return windowToCanvas(v.X(), v.Y());
	}
	
	public static Vector2f windowToCanvas(float x, float y) {
		x = (x - INSTANCE.canvas.canvas_w / 2) / INSTANCE.canvas.scale + CANVAS_W / 2;
		y = (y - INSTANCE.canvas.canvas_h / 2) / INSTANCE.canvas.scale + CANVAS_H / 2;
		return new Vector2f(x, y);
	}
	
	public static Vector2f canvasToWindow(Vector2f v) {
		return canvasToWindow(v.X(), v.Y());
	}
	
	public static Vector2f canvasToWindow(float x, float y) {
		x = (x - CANVAS_W / 2) * INSTANCE.canvas.scale + INSTANCE.canvas.canvas_w / 2;
		y = (y - CANVAS_H / 2) * INSTANCE.canvas.scale + INSTANCE.canvas.canvas_h / 2;
		return new Vector2f(x, y);
	}
	
	public void mouseMoved(int x, int y) {
		if(this.scene != null)
			this.scene.onMouseMoved(x, y);
	}
	
	public void wheelMoved(int wheel) {
		if(this.scene != null)
			this.scene.onWheelMoved(wheel);
	}
	
	public void keyDnAction(int key) {
		if(this.scene != null)
			this.scene.onKeyDnAction(key);
	}
	
	public void keyUpAction(int key) {
		if(this.scene != null)
			this.scene.onKeyUpAction(key);
	}
	
	public void btnDnAction(int btn, int x, int y) {
		if(this.scene != null)
			this.scene.onBtnDnAction(btn, x, y);
	}
	
	public void btnUpAction(int btn, int x, int y) {
		if(this.scene != null)
			this.scene.onBtnUpAction(btn, x, y);
	}
	
	public void onInit() {
		this.window.onInit();
		this.canvas.onInit();
		if(this.scene != null)
			this.scene.onInit();
	}
	
	public void onExit() {
		if(this.scene != null)
			this.scene.onExit();
		this.canvas.onExit();
		this.window.onExit();
	}
	
	private void render(float dt, float t) {
		if(this.scene != null)
			this.canvas.render(dt, t, this.scene);
	}
	
	private void update(float dt, float t) {
		Input.INSTANCE.poll();
		if(this.scene != null)
			this.canvas.update(dt, t, this.scene);
	}
	
	private static final long
		ONE_SECOND = 1000000000L;

	@SuppressWarnings("unused")
	@Override
	public void run() {
		try {
			this.onInit();
			long
				f_time = FPS > 0 ? ONE_SECOND / FPS : 0,
				t_time = TPS > 0 ? ONE_SECOND / TPS : 0,
				f_elapsed = 0,
				t_elapsed = 0,
				elapsed1 = 0,	
				elapsed2 = 0,
				f_ct = 0,
				t_ct = 0,
				t = System.nanoTime();			
			this.update(0f, 0f);
			this.render(0f, 0f);
			while(running) {
				long dt = - t + (t = System.nanoTime());
				f_elapsed += dt;
				t_elapsed += dt;
				elapsed1 += dt;
				elapsed2 += dt;
				if(t_elapsed >= t_time) {
					this.update((float)t_elapsed / ONE_SECOND, (float)elapsed2 / ONE_SECOND);
					t_elapsed = 0;
					t_ct ++;
				}
				if(f_elapsed >= f_time) {
					this.render((float)f_elapsed / ONE_SECOND, (float)elapsed2 / ONE_SECOND);
					f_elapsed = 0;
					f_ct ++;
				}				
				if(elapsed1 > ONE_SECOND) {
					System.out.println("FPS: " + (this.fps = f_ct));
					System.out.println("TPS: " + (this.tps = t_ct));
					elapsed1 = 0;
					f_ct = 0;
					t_ct = 0;
				}
				Thread.sleep(1);
			}
		} catch(Exception ex) {
			ex.printStackTrace();
			this.running = false;
		} finally {
			this.onExit();
		}		
	}
	
	public static class Window {
		protected java.awt.Frame
			component;		
		protected Engine
			engine;
		
		public Window(Engine engine) {
			this.engine = engine;
			this.component = new java.awt.Frame();
			this.component.add(engine.canvas.component);
			this.component.setTitle(Blud.VERSION.toString());
			this.component.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent we) {
					Engine.exit();
				}
			});			
			this.component.pack();
		}
		
		public void onInit() {	
			this.component.setLocationRelativeTo(null);
			this.component.setVisible(true);
		}
		
		public void onExit() {
			this.component.dispose();
		}
	}
	
	public static class Canvas {
		protected java.awt.Canvas
			component;
		protected RenderContext
			render_context;
		protected UpdateContext
			update_context;
		protected BufferedImage
			canvas;
		protected Graphics2D
			buffer_gfx,
			canvas_gfx;	
		protected Engine
			engine;
		
		protected int
			canvas_w,
			canvas_h;
		protected float
			scale;
		
		public Canvas(Engine engine) {
			this.component = new java.awt.Canvas();
			this.render_context = new RenderContext();
			this.update_context = new UpdateContext();
			
			this.component.setFocusable(true);
			this.component.setFocusTraversalKeysEnabled(false);
			
			this.component.addKeyListener(Input.INSTANCE);
			this.component.addMouseListener(Input.INSTANCE);
			this.component.addMouseWheelListener(Input.INSTANCE);
			this.component.addMouseMotionListener(Input.INSTANCE);
			
			Dimension size = new Dimension(
						WINDOW_W,
						WINDOW_H
						);
			this.component.setMinimumSize(size);
			this.component.setMaximumSize(size);
			this.component.setPreferredSize(size);
			
			this.canvas = new BufferedImage(
					CANVAS_W,
					CANVAS_H,
					BufferedImage.TYPE_INT_ARGB
					);	
			
			this.component.setBackground(Color.BLACK);
			this.component.setForeground(Color.WHITE);
		}
		
		public void onInit() {
			//do nothing
		}
		
		public void onExit() {
			//do nothing
		}

		protected BufferStrategy
			buffer;
		public void render(float dt, float t, Renderable renderable) {
			this.render_context.dt = dt;
			this.render_context.t  =  t;
			this.render_context.canvas_w = CANVAS_W;
			this.render_context.canvas_h = CANVAS_H;
			
			if(this.buffer == null || this.buffer.contentsLost()) {
				this.component.createBufferStrategy(NUM_BUFFERS);
				this.buffer = this.component.getBufferStrategy();
			}
			
			this.buffer_gfx = (Graphics2D)this.buffer.getDrawGraphics();
			this.canvas_gfx = (Graphics2D)this.canvas.createGraphics() ;
			
			this.render_context.g2D = this.canvas_gfx;			
			renderable.render(this.render_context);			
			this.canvas_gfx.dispose();
			
			canvas_w = this.component.getWidth() ;
			canvas_h = this.component.getHeight();
			scale = Util.min(
					(float)canvas_w / CANVAS_W,
					(float)canvas_h / CANVAS_H
					);
			
			this.buffer_gfx.translate(
					canvas_w / 2,
					canvas_h / 2
					);
			this.buffer_gfx.scale(
					scale,
					scale
					);
			this.buffer_gfx.drawImage(
					this.canvas,
					null,
					- CANVAS_W / 2,
					- CANVAS_H / 2
					);
			this.buffer_gfx.dispose();
			this.buffer.show();
		}
		
		public void update(float dt, float t, Updateable updateable) {
			this.update_context.dt = dt;
			this.update_context.t  =  t;
			this.update_context.canvas_w = CANVAS_W;
			this.update_context.canvas_h = CANVAS_H;
			updateable.update(this.update_context);
		}
	}
}
