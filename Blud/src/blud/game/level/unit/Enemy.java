package blud.game.level.unit;

import java.util.LinkedList;

import blud.core.Engine;
import blud.game.level.node.Node;
import blud.game.level.node.Node.Check;
import blud.game.level.unit.units.Player;
import blud.game.sprite.Sprite;
import blud.game.sprite.sprites.Sprites;
import blud.geom.Vector;

public abstract class Enemy extends Unit {
	public final Sprite
		alert = Sprites.get("Alert");
	public boolean
		isAlert,//the alert state of the enemy
		isDetect,
		detectThroughUnits,//can detect the player through other units
		detectThroughWalls,//can detect the player through other walls
		aoeDetection;//is detection aoe? if not detect only in the direction this unit is facing
	public int
		frame,//the current frame
		alertFrames,//the number of frames a player must be in     vision before becoming alerted
		relaxFrames,//the number of frames a player must be out of vision before becoming relaxed
		detectionRange;//the range of detection (0 = no detection, 1 = only my spot, >1 = adjacent nodes)
	public final LinkedList<Node>
		detection = new LinkedList<>();//all nodes that can be "seen" (detected) by this unit
	public Player
		target;//the player target (will be null if player is out of detection)	
	
	public Check 
		//does this node block my vision?
		check1 = (node) -> {
			if(node.unit != null) {
				if(!detectThroughWalls &&  (node.unit instanceof Wall  ))
					return true;
				if(!detectThroughUnits && !(node.unit instanceof Player))
					return true;
			}
			return false;
		},
		//is this node visible?
		check2 = (node) -> {
			return
					node.entityVision && 
					node.lightLevel > 0;
		};
	
	public Enemy() {
		this(0, 0);
	}
	
	public Enemy(Vector local) {
		this(local.X(), local.Y());
	}
	
	public Enemy(float i, float j) {
		super(i, j);
		drawFacing = true;
	}	
	
	//method to alert this unit
	public void alert() {
		if(!isAlert) {
			isAlert = true;
			onAlert();
		}
	}
	
	//method to relax this unit
	public void relax() {
		if( isAlert) {
			isAlert = false;
			onRelax();
		}
	}
	
	public void detect() {
		if(!isDetect) {
			isDetect = true;
			onDetect();
		}
	}
	
	public void forget() {
		if( isDetect) {
			isDetect = false;
			onForget();
		}
	}
	
	//called when alert is triggered
	public void onAlert() { }	
	//called when relax is triggered
	public void onRelax() { }	
	//called once per update while player is detected before becoming alerted
	public void onDetect() { }
	public void onForget() { }
	//called once per update while alerted
	public void whileAlerted() { }	
	//called once per update while relaxed
	public void whileRelaxed() { }
	
	@Override
	public void onRender2(RenderContext context) {
		if(alert.mode > 0)
			alert.render(context);
	}
	
	@Override
	public void onUpdate1(UpdateContext context) {
		detection.clear();
		node.walk(
				detection,
				check1,
				check2,
				detectionRange,
				aoeDetection ? -1 : facing
				);
		Player target = null;
		for(Node node: detection)
			if(node.unit instanceof Player)
				target = (Player)node.unit;
		if(isAlert) {
			if(target != null) {
				frame = 0;
			} else {
				frame ++;				  
				if(frame >= relaxFrames) {
					this.target = target;
					relax();
					forget();
					return;
				}
			}
			whileAlerted();
		} else {
			if(target != null) {
				frame ++;
				detect();
				alert.play(Engine.TPS / alertFrames * 5, pixel.x(), pixel.y() - 8);
				if(frame >= alertFrames) {					
					this.target = target;
					alert.stop();
					alert();
					return;
				}
			} else {
				alert.stop();
				frame = 0;
				forget();
			}
			whileRelaxed();
		}
		alert.update(context);
	}
}
