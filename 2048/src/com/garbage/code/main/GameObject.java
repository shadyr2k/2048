package com.garbage.code.main;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Random;

public abstract class GameObject {
	protected int x, y;
	protected ID id;
	
	public GameObject(int x, int y, ID id){
		this.x = x;
		this.y = y;
		this.id = id;
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
	 
	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public void setID(ID id){
		this.id = id;
	}
	
	public ID getID(){
		return id;
	}

	public Image resize(Image i, int w, int h){
	    BufferedImage r = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2d = r.createGraphics();
	    g2d.drawImage(i, 0, 0, w, h, null);
	    g2d.dispose();
	    return r;
	}
	
	public static int random(int min, int max){
		Random r = new Random();
		return r.nextInt(max) + min;
	}
}
