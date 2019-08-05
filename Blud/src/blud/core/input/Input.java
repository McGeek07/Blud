package blud.core.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import blud.core.Engine;
import blud.geom.Vector2f;
import blud.util.Logic;


public class Input implements KeyListener, MouseListener, MouseWheelListener, MouseMotionListener {
	public static final Input
		INSTANCE = new Input();
	private static final int
		NUM_KEYS = 91,
		NUM_BTNS =  4;
	public static final int
		KEY_UP 			= 0, BTN_UP 		= 0,
		KEY_UP_ACTION 	= 1, BTN_UP_ACTION 	= 1,
		KEY_DN			= 2, BTN_DN			= 2,
		KEY_DN_ACTION	= 3, BTN_DN_ACTION 	= 3;
	
	protected final boolean[]
		key_buffer = new boolean[NUM_KEYS],
		btn_buffer = new boolean[NUM_BTNS];
	protected int[]
		keys = new int[NUM_KEYS],
		btns = new int[NUM_BTNS];
	protected int
		wheel_buffer = 0,
		wheel		 = 0;
	protected Vector2f.Mutable
		mouse_buffer = new Vector2f.Mutable(),
		mouse		 = new Vector2f.Mutable();
	
	protected Input() {
		// do nothing
	}
	
	public void poll() {
		pollMouse();
		pollWheel();
		pollKeys();
		pollBtns();
	}
	
	public void pollMouse() {
		if(!this.mouse.equals(this.mouse_buffer)) {
			this.mouse.set(   this.mouse_buffer);			
			Engine.INSTANCE.mouseMoved(
					mouse.x(),
					mouse.y()
					);
		}
	}
	
	public void pollWheel() {
		this.wheel = 0;		
		if(this.wheel_buffer != 0) {
			this.wheel = this.wheel_buffer;	
			Engine.INSTANCE.wheelMoved(wheel);
		}
		this.wheel_buffer = 0;
	}
	
	public void pollKeys() {
		for(int i = 0; i < NUM_KEYS; i ++) {
			if(key_buffer[i])
				switch(keys[i]) {
					case KEY_DN: case KEY_DN_ACTION:
						keys[i] = KEY_DN;
						break;
					case KEY_UP: case KEY_UP_ACTION:
						keys[i] = KEY_DN_ACTION;
						Engine.INSTANCE.keyDnAction(i);
						break;
				}
			else
				switch(keys[i]) {
					case KEY_DN: case KEY_DN_ACTION:
						keys[i] = KEY_UP_ACTION;
						Engine.INSTANCE.keyUpAction(i);
						break;
					case KEY_UP: case KEY_UP_ACTION:
						keys[i] = KEY_UP;
						break;		
				}
		}
	}
	
	public void pollBtns() {
		for(int i = 0; i < NUM_BTNS; i ++) {
			if(btn_buffer[i])
				switch(btns[i]) {
					case BTN_DN: case BTN_DN_ACTION:
						btns[i] = BTN_DN;
						break;
					case BTN_UP: case BTN_UP_ACTION:
						btns[i] = BTN_DN_ACTION;
						Engine.INSTANCE.btnDnAction(
								i,
								mouse.x(),
								mouse.y()
								);
						break;
				}
			else
				switch(btns[i]) {
					case BTN_DN: case BTN_DN_ACTION:
						btns[i] = BTN_UP_ACTION;
						Engine.INSTANCE.btnUpAction(
								i,
								mouse.x(),
								mouse.y()
								);
						break;
					case BTN_UP: case BTN_UP_ACTION:
						btns[i] = BTN_UP;
						break;		
				}
		}
	}
	
	public static Vector2f getMouse() {
		return INSTANCE.mouse;
	}
	
	public static int getKey(int key) {
		return INSTANCE.keys[key];
	}
	
	public static boolean isKeyDn(int key) {
		int k = getKey(key);
		return
				k == KEY_DN ||
				k == KEY_DN_ACTION;
	}
	
	public static boolean isKeyDn(Logic logic, int... keys) {
		int 
			dn = 0;
		for(int i = 0; i < keys.length; i ++)
			if(isKeyDn(keys[i]))
				dn ++;		
		switch(logic) {
			case  AND: return dn == keys.length;
			case   OR: return dn >= 1;
			case  XOR: return dn == 1;
			case NAND: return dn != keys.length;
			case  NOR: return dn  < 1;
			case XNOR: return dn != 1;	
		}
		return false;
	}
	
	public static boolean isKeyUp(int key) {
		int k = getKey(key);
		return
				k == KEY_UP ||
				k == KEY_UP_ACTION;
	}
	
	public static boolean isKeyUp(Logic logic, int... keys) {
		int 
			up = 0;
		for(int i = 0; i < keys.length; i ++)
			if(isKeyUp(keys[i]))
				up ++;		
		switch(logic) {
			case  AND: return up == keys.length;
			case   OR: return up >= 1;
			case  XOR: return up == 1;
			case NAND: return up != keys.length;
			case  NOR: return up  < 1;
			case XNOR: return up != 1;
		}
		return false;
	}
	
	public static boolean isKeyDnAction(int key) {
		return getKey(key) == KEY_DN_ACTION;
	}
	
	public static boolean isKeyUpAction(int key) {
		return getKey(key) == KEY_UP_ACTION;
	}
	
	public static int getBtn(int btn) {
		return INSTANCE.btns[btn];
	}
	
	public static boolean isBtnDn(int btn) {
		int b = getBtn(btn);
		return
				b == BTN_DN ||
				b == BTN_DN_ACTION;
	}
	
	public static boolean isBtnUp(int btn) {
		int b = getBtn(btn);
		return
				b == BTN_UP ||
				b == BTN_UP_ACTION;
	}
	
	public static boolean isBtnDnAction(int btn) {
		return getBtn(btn) == BTN_DN_ACTION;
	}
	
	public static boolean isBtnUpAction(int btn) {
		return getBtn(btn) == BTN_UP_ACTION;
	}	
	
	public static int getWheel() {
		return INSTANCE.wheel;
	}
	
	public static boolean isWheelUp() {
		return getWheel() < 0;
	}
	
	public static boolean isWheelDn() {
		return getWheel() > 0;
	}	

	@Override
	public void keyTyped(KeyEvent ke) {
		//do nothing
	}

	@Override
	public void keyPressed(KeyEvent ke) {
		switch(ke.getKeyCode()) {
			case KeyEvent.VK_ESCAPE	: key_buffer[KEY_ESCAPE] 	= true; break;
			case KeyEvent.VK_ENTER 	: key_buffer[KEY_RETURN] 	= true; break;
			case KeyEvent.VK_TAB	: key_buffer[KEY_TAB] 		= true; break;
			case KeyEvent.VK_UP		: key_buffer[KEY_UP_ARROW] 	= true; break;
			case KeyEvent.VK_DOWN	: key_buffer[KEY_DN_ARROW] 	= true; break;
			case KeyEvent.VK_LEFT	: key_buffer[KEY_L_ARROW] 	= true; break;
			case KeyEvent.VK_RIGHT	: key_buffer[KEY_R_ARROW] 	= true; break;
			case KeyEvent.VK_SPACE 	: key_buffer[KEY_SPACE] 	= true; break;
			case KeyEvent.VK_0 : key_buffer[KEY_0] = true; break;
			case KeyEvent.VK_1 : key_buffer[KEY_1] = true; break;
			case KeyEvent.VK_2 : key_buffer[KEY_2] = true; break;
			case KeyEvent.VK_3 : key_buffer[KEY_3] = true; break;
			case KeyEvent.VK_4 : key_buffer[KEY_4] = true; break;
			case KeyEvent.VK_5 : key_buffer[KEY_5] = true; break;
			case KeyEvent.VK_6 : key_buffer[KEY_6] = true; break;
			case KeyEvent.VK_7 : key_buffer[KEY_7] = true; break;
			case KeyEvent.VK_8 : key_buffer[KEY_8] = true; break;
			case KeyEvent.VK_9 : key_buffer[KEY_9] = true; break;				
			case KeyEvent.VK_A : key_buffer[KEY_A] = true; break;
			case KeyEvent.VK_B : key_buffer[KEY_B] = true; break;
			case KeyEvent.VK_C : key_buffer[KEY_C] = true; break;
			case KeyEvent.VK_D : key_buffer[KEY_D] = true; break;
			case KeyEvent.VK_E : key_buffer[KEY_E] = true; break;
			case KeyEvent.VK_F : key_buffer[KEY_F] = true; break;
			case KeyEvent.VK_G : key_buffer[KEY_G] = true; break;
			case KeyEvent.VK_H : key_buffer[KEY_H] = true; break;
			case KeyEvent.VK_I : key_buffer[KEY_I] = true; break;
			case KeyEvent.VK_J : key_buffer[KEY_J] = true; break;
			case KeyEvent.VK_K : key_buffer[KEY_K] = true; break;
			case KeyEvent.VK_L : key_buffer[KEY_L] = true; break;
			case KeyEvent.VK_M : key_buffer[KEY_M] = true; break;
			case KeyEvent.VK_N : key_buffer[KEY_N] = true; break;
			case KeyEvent.VK_O : key_buffer[KEY_O] = true; break;
			case KeyEvent.VK_P : key_buffer[KEY_P] = true; break;
			case KeyEvent.VK_Q : key_buffer[KEY_Q] = true; break;
			case KeyEvent.VK_R : key_buffer[KEY_R] = true; break;
			case KeyEvent.VK_S : key_buffer[KEY_S] = true; break;
			case KeyEvent.VK_T : key_buffer[KEY_T] = true; break;
			case KeyEvent.VK_U : key_buffer[KEY_U] = true; break;
			case KeyEvent.VK_V : key_buffer[KEY_V] = true; break;
			case KeyEvent.VK_W : key_buffer[KEY_W] = true; break;
			case KeyEvent.VK_X : key_buffer[KEY_X] = true; break;
			case KeyEvent.VK_Y : key_buffer[KEY_Y] = true; break;
			case KeyEvent.VK_Z : key_buffer[KEY_Z] = true; break;
		}	
		ke.consume();
	}

	@Override
	public void keyReleased(KeyEvent ke) {
		switch(ke.getKeyCode()) {
			case KeyEvent.VK_ESCAPE	: key_buffer[KEY_ESCAPE] 	= false; break;
			case KeyEvent.VK_ENTER 	: key_buffer[KEY_RETURN] 	= false; break;
			case KeyEvent.VK_TAB	: key_buffer[KEY_TAB] 		= false; break;
			case KeyEvent.VK_UP		: key_buffer[KEY_UP_ARROW] 	= false; break;
			case KeyEvent.VK_DOWN	: key_buffer[KEY_DN_ARROW] 	= false; break;
			case KeyEvent.VK_LEFT	: key_buffer[KEY_L_ARROW] 	= false; break;
			case KeyEvent.VK_RIGHT	: key_buffer[KEY_R_ARROW] 	= false; break;
			case KeyEvent.VK_SPACE 	: key_buffer[KEY_SPACE] 	= false; break;
			case KeyEvent.VK_0 : key_buffer[KEY_0] = false; break;
			case KeyEvent.VK_1 : key_buffer[KEY_1] = false; break;
			case KeyEvent.VK_2 : key_buffer[KEY_2] = false; break;
			case KeyEvent.VK_3 : key_buffer[KEY_3] = false; break;
			case KeyEvent.VK_4 : key_buffer[KEY_4] = false; break;
			case KeyEvent.VK_5 : key_buffer[KEY_5] = false; break;
			case KeyEvent.VK_6 : key_buffer[KEY_6] = false; break;
			case KeyEvent.VK_7 : key_buffer[KEY_7] = false; break;
			case KeyEvent.VK_8 : key_buffer[KEY_8] = false; break;
			case KeyEvent.VK_9 : key_buffer[KEY_9] = false; break;				
			case KeyEvent.VK_A : key_buffer[KEY_A] = false; break;
			case KeyEvent.VK_B : key_buffer[KEY_B] = false; break;
			case KeyEvent.VK_C : key_buffer[KEY_C] = false; break;
			case KeyEvent.VK_D : key_buffer[KEY_D] = false; break;
			case KeyEvent.VK_E : key_buffer[KEY_E] = false; break;
			case KeyEvent.VK_F : key_buffer[KEY_F] = false; break;
			case KeyEvent.VK_G : key_buffer[KEY_G] = false; break;
			case KeyEvent.VK_H : key_buffer[KEY_H] = false; break;
			case KeyEvent.VK_I : key_buffer[KEY_I] = false; break;
			case KeyEvent.VK_J : key_buffer[KEY_J] = false; break;
			case KeyEvent.VK_K : key_buffer[KEY_K] = false; break;
			case KeyEvent.VK_L : key_buffer[KEY_L] = false; break;
			case KeyEvent.VK_M : key_buffer[KEY_M] = false; break;
			case KeyEvent.VK_N : key_buffer[KEY_N] = false; break;
			case KeyEvent.VK_O : key_buffer[KEY_O] = false; break;
			case KeyEvent.VK_P : key_buffer[KEY_P] = false; break;
			case KeyEvent.VK_Q : key_buffer[KEY_Q] = false; break;
			case KeyEvent.VK_R : key_buffer[KEY_R] = false; break;
			case KeyEvent.VK_S : key_buffer[KEY_S] = false; break;
			case KeyEvent.VK_T : key_buffer[KEY_T] = false; break;
			case KeyEvent.VK_U : key_buffer[KEY_U] = false; break;
			case KeyEvent.VK_V : key_buffer[KEY_V] = false; break;
			case KeyEvent.VK_W : key_buffer[KEY_W] = false; break;
			case KeyEvent.VK_X : key_buffer[KEY_X] = false; break;
			case KeyEvent.VK_Y : key_buffer[KEY_Y] = false; break;
			case KeyEvent.VK_Z : key_buffer[KEY_Z] = false; break;
		}	
		ke.consume();
	}

	@Override
	public void mouseClicked(MouseEvent me) {
		//do nothing		
	}

	@Override
	public void mousePressed(MouseEvent me) {
		int btn = me.getButton();
		if(btn > 0 && btn < NUM_BTNS)
			btn_buffer[btn] = true;
		me.consume();
	}

	@Override
	public void mouseReleased(MouseEvent me) {
		int btn = me.getButton();
		if(btn > 0 && btn < NUM_BTNS)
			btn_buffer[btn] = false;
		me.consume();
	}

	@Override
	public void mouseEntered(MouseEvent me) {
		//do nothing
	}

	@Override
	public void mouseExited(MouseEvent me) {
		//do nothing		
	}	

	@Override
	public void mouseDragged(MouseEvent me) {
		mouse_buffer.set(Engine.windowToCanvas(
				me.getX(),
				me.getY()
				));
		me.consume();
	}

	@Override
	public void mouseMoved(MouseEvent me) {
		mouse_buffer.set(Engine.windowToCanvas(
				me.getX(),
				me.getY()
				));
		me.consume();
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent mwe) {
		wheel_buffer = mwe.getWheelRotation();
		mwe.consume();
	}
	
	public static final int
		KEY_ESCAPE 	= 0,
		KEY_RETURN 	= 10,			
		KEY_TAB 	= 20,
		
		KEY_UP_ARROW = 1,
		KEY_DN_ARROW = 4,
		KEY_L_ARROW = 2,
		KEY_R_ARROW = 8,
		
		KEY_SPACE = ' ',
		
		KEY_0 = '0',
		KEY_1 = '1',
		KEY_2 = '2',
		KEY_3 = '3',
		KEY_4 = '4',
		KEY_5 = '5',
		KEY_6 = '6',
		KEY_7 = '7',
		KEY_8 = '8',
		KEY_9 = '9',
		
		KEY_A = 'A',
		KEY_B = 'B',
		KEY_C = 'C',
		KEY_D = 'D',
		KEY_E = 'E',
		KEY_F = 'F',
		KEY_G = 'G',
		KEY_H = 'H',
		KEY_I = 'I',
		KEY_J = 'J',
		KEY_K = 'K',
		KEY_L = 'L',
		KEY_M = 'M',
		KEY_N = 'N',
		KEY_O = 'O',
		KEY_P = 'P',
		KEY_Q = 'Q',
		KEY_R = 'R',
		KEY_S = 'S',
		KEY_T = 'T',
		KEY_U = 'U',
		KEY_V = 'V',
		KEY_W = 'W',
		KEY_X = 'X',
		KEY_Y = 'Y',
		KEY_Z = 'Z';
	
	public static final int
		BTN_1				= 1,
		BTN_2 				= 2,
		BTN_3 				= 3;
}
