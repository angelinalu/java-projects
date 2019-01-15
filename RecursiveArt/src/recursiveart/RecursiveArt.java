package recursiveart;

import java.awt.*;

import processing.core.PApplet;
import processing.core.PVector;
/*
 * Recursive Art
 * 
 * This program draws some pretty pictures
 * using recursion
 */

public class RecursiveArt extends PApplet {

	/*
	 * The setup sets the initial size, 
	 * adds smoothing (for pixelated lines)
	 * and noStroke, which takes out outlines of shapes.
	 * This method is called once - when the program starts.
	 */
	public void setup() {
		size(400,400);
		smooth();
		noStroke();
	}

	/*
	 * The draw program is called many times a second.
	 * It draws shapes to the screen.
	 * 
	 * The art should still be correct when the window is resized.
	 */
	public void draw() {
		// Draw background first.
		background(255,255,255);
		
		// Draw the target
		pushMatrix();
		scale(0.5f, 0.5f);
		PVector targetPosition = new PVector(width/2f, height/2f);
		PVector radii = new PVector(width/2f, height/2f);
		drawTarget(targetPosition, radii, 10);
		popMatrix();

		// Draw the "dream catcher"
		pushMatrix();
		translate(width * 0.5f, 0);
		scale(0.5f, 0.5f);
		PVector a = new PVector(0,0);
		PVector b = new PVector(width, 0);
		PVector c = new PVector(width, height);
		PVector d = new PVector(0, height);
		drawDreamcatcher(a, b, c, d, 0.5f, 20);
		popMatrix();

		// Draw the triangles
		rectMode(CORNER);
		pushMatrix();
		translate(0, height * 0.5f);
		scale(0.5f, 0.5f);
		fill(0, 0, 255);
		rect(0, 0, width, height);
		drawSierpinski(10);
		popMatrix();

		// Draw whatever you want as long as its recursive.
		pushMatrix();
		translate(width * 0.5f, height * 0.5f);
		scale(0.5f, 0.5f);
		a = new PVector(0,0);
		b = new PVector(width, 0);
		c = new PVector(width, height);
		d = new PVector(0, height);
		drawSpiral(a, b, c, d, .01f, 1000);
		
		popMatrix();
	}

	public void drawTarget(PVector position, PVector radii, int level){
		if(level > 0) {
			if(level % 2 == 1) {
				fill(0, 0, 255);
			}
			else {
				fill(255,0,0);
			}
			float dx = radii.x/level;
			float dy = radii.y/level;
			ellipse(position.x, position.y, radii.x * 2, radii.y * 2);
			PVector newrad = new PVector(radii.x - dx, radii.y - dy);
			drawTarget(position, newrad, level-1);
		}
	}
	public void drawDreamcatcher(PVector a, PVector b, PVector c, PVector d, float twist, int level){
		if(level > 0) {
			if(level % 2 == 0) {
				fill(128, 128, 255);
			}
			else {
				fill(255,255,255);
			}
			quad(a.x, a.y, b.x, b.y, c.x, c.y, d.x, d.y);
			float twistInv = 1f - twist;
			PVector a1 = PVector.lerp(a, b, twistInv);
			b.lerp(c, twistInv);
			c.lerp(d, twistInv);
			d.lerp(a, twistInv);
			drawDreamcatcher(a1, b, c, d, twist, level - 1);
		}
	}

	/*public void drawSierpinski(PVector up, PVector right, int level){
		if(level > 0){
			fill(255,255,0);
			triangle(up.x, up.y, right.x, right.y, up.x, right.y);
			float levelWidth = (right.x - up.x) / 2;
			float levelHeight = (right.y - up.y) / 2;
			PVector up1 = new PVector(up.x - levelWidth, up.y);
			PVector up2 = new PVector(up.x + levelWidth, up.y);
			PVector up3 = new PVector(up.x + levelWidth, right.y);
			PVector right1 = new PVector(up.x, up.y + levelHeight);
			PVector right2 = new PVector(right.x, right.y - levelHeight);
			PVector right3 = new PVector(right.x, right.y + levelHeight);
			drawSierpinski(up1, right1, level - 1);
			drawSierpinski(up2, right2, level - 1);
			drawSierpinski(up3, right3, level - 1);
		}
	}*/

	public void drawSierpinski(int level) {
		if(level > 0) {
			if(level % 2 == 0) {
				fill(128, 128, 255);
			}
			else {
				fill(255,255,255);
			}
			fill(255,255,0);
			triangle(0, 0, 0, height, width, height);
			
			pushMatrix();
			translate(width/2, 0);
			scale(0.5f, 0.5f);
			drawSierpinski(level-1);
			popMatrix();
			
			pushMatrix();
			translate(-width/2, 0);
			scale(0.5f, 0.5f);
			drawSierpinski(level-1);
			popMatrix();
			
			pushMatrix();
			translate(width/2, height);
			scale(0.5f, 0.5f);
			drawSierpinski(level-1);
			popMatrix();
		}
	}
	
	public void drawSpiral(PVector a, PVector b, PVector c, PVector d, float twist, int level){
		if(level > 0) {
			float distance = a.dist(b);
			float color = (distance/width) * 255;
			fill(128, 255 - color, color);
			quad(a.x, a.y, b.x, b.y, c.x, c.y, d.x, d.y);
			float twistInv = 1f - twist;
			PVector a1 = PVector.lerp(a, b, twistInv);
			b.lerp(c, twistInv);
			c.lerp(d, twistInv);
			d.lerp(a, twistInv);
			drawSpiral(a1, b, c, d, twist, level - 1);
		}
	}
}
