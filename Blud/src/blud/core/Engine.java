package blud.core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import blud.Blud;
import blud.util.Util;

public class Engine implements Renderable, Updateable, Runnable {
	private static final Engine
		ENGINE = new Engine();
	private static final int
		NUM_BUFFERS = 2,
		WINDOW_W = 512,
		WINDOW_H = 512,
		CANVAS_W = 64,
		CANVAS_H = 64,
		FPS = 24,
		TPS = 24;
	
	protected final Canvas
		canvas = new Canvas(this);
	protected final Window
		window = new Window(this);
	
	protected boolean
		running;
	protected Thread
		thread;
	protected float
		fps,
		tps;	
	
	protected Engine() {
		//do nothing
	}
	
	public static void init() {
		if(!ENGINE.running) {
			ENGINE.thread = new Thread(ENGINE);
			ENGINE.running = true;
			ENGINE.thread.start();
		}
	}
	
	public static void exit() {
		if( ENGINE.running) {
			ENGINE.running = false;
		}
	}
	
	public void onInit() {
		this.window.onInit();
		this.canvas.onInit();
	}
	
	public void onExit() {
		this.canvas.onExit();
		this.window.onExit();
	}
	
	private void render(float dt) {
		canvas.render(dt, this);
	}
	
	private void update(float dt) {
		canvas.update(dt, this);
	}


	@Override
	public void render(RenderContext context) {
		context.g2D.setColor(Color.WHITE);
		context.g2D.fillRect(
				0,
				0,
				context.canvas_w,
				context.canvas_h
				);
	}

	@Override
	public void update(UpdateContext context) {
		
	}
	
	private static final long
		ONE_SECOND = 1000000000L;

	@Override
	public void run() {
		try {
			this.onInit();
			long
				f_time = FPS > 0 ? ONE_SECOND / FPS : 0,
				t_time = TPS > 0 ? ONE_SECOND / TPS : 0,
				f_elapsed = 0,
				t_elapsed = 0,
				elapsed = 0,							
				f_ct = 0,
				t_ct = 0,
				t = System.nanoTime();			
			this.update(0f);
			this.render(0f);
			while(running) {
				long dt = - t + (t = System.nanoTime());
				f_elapsed += dt;
				t_elapsed += dt;
				elapsed += dt;
				if(t_elapsed >= t_time) {
					this.update((float)t_elapsed / ONE_SECOND);
					t_elapsed = 0;
					t_ct ++;
				}
				if(f_elapsed >= f_time) {
					this.render((float)f_elapsed / ONE_SECOND);
					f_elapsed = 0;
					f_ct ++;
				}				
				if(elapsed >= ONE_SECOND) {
					System.out.println(this.fps = f_ct);
					System.out.println(this.tps = t_ct);
					elapsed = 0;
					f_ct = 0;
					t_ct = 0;
				}
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
		
		public Canvas(Engine engine) {
			this.component = new java.awt.Canvas();
			this.render_context = new RenderContext();
			this.update_context = new UpdateContext();
			
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
		public void render(float dt, Renderable renderable) {
			this.render_context.dt = dt;
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
			
			int 				
				canvas_w = this.component.getWidth() ,
				canvas_h = this.component.getHeight();
			float scale = Util.min(
					(float)canvas_w / CANVAS_W,
					(float)canvas_h / CANVAS_H
					);
			

//			this.buffer_gfx.setColor(Color.WHITE);
//			this.buffer_gfx.drawLine(canvas_w / 2, 0, canvas_w / 2, canvas_h);
//			this.buffer_gfx.drawLine(0, canvas_h / 2, canvas_w, canvas_h / 2);
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
		
		public void update(float dt, Updateable updateable) {
			this.update_context.dt = dt;
			this.update_context.canvas_w = CANVAS_W;
			this.update_context.canvas_h = CANVAS_H;
			updateable.update(this.update_context);
		}
	}
}
