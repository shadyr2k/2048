package com.garbage.code.main;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {
	
	public static final int WIDTH = 516, HEIGHT = 640; //506x529
	
	private static final long serialVersionUID = 7676616993152955582L;
	private Thread thread;
	private boolean running = false;
	
	private Handler handler;
	
	public synchronized void start(){
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop(){
		try {
			thread.join();
			running = false;
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run(){
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000/amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1){
				tick();
				delta--;
			}
			if(running){
				render();
				frames++;
			}
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop();
	}
	
	private void tick(){
		handler.tick();
	}
	
	private void render(){
		BufferStrategy b = this.getBufferStrategy();
		if(b == null){
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = b.getDrawGraphics();
		handler.render(g);
		g.dispose();
		b.show();
	}
	
	public Game() {
		new Window(WIDTH, HEIGHT, "2048", this);
		handler = new Handler();
		this.addKeyListener(new KeyInput(handler));
		this.addMouseListener(new MouseInput(handler));
		handler.addObject(new Board(0, 101, ID.Board));
		handler.addObject(new Tile(0, 101, ID.Tile));
		Tile.resetGame();
	}
	
	public static void main(String[] args){
		new Game();
	} 
}
