package com.garbage.code.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import javax.swing.ImageIcon;

public class Tile extends GameObject {
	
	static ArrayList<Integer> scoreArray = new ArrayList<Integer>();
	
	static int score = 0;
	static int maxScore = 0;
	static int alpha = 0; static int alpha1 = 0;
	static int buttons = 0;
	static boolean show = false;
	
	public Tile(int x, int y, ID id) {
		super(x, y, id);
	}

	public static int[][] gameboard = new int[][]{
		{0, 0, 0, 0,},
		{0, 0, 0, 0,},
		{0, 0, 0, 0,},
		{0, 0, 0, 0,}
	};
	
	/*tile corners: (15, 15)	(136, 15)	(257, 15)	(378, 15)
					(15, 136)	(136, 136)	(257, 136)	(378, 136)
					(15, 258)	(136, 258)	(257, 258)	(378, 258)
					(15, 379)	(136, 379)	(257, 379)	(378, 379)
	*/	
	
	//random start location
	int[] locX = {15, 136, 257, 378};
	int[] locY = {116, 237, 359, 480};
	
	public void render(Graphics g) {
		
		DecimalFormat f = new DecimalFormat("#,###");
		Graphics2D g2d = (Graphics2D) g;
		
		g.setColor(new Color(250, 248, 239)); 
		g.fillRect(0, 0, 506, 101); 
		ImageIcon gameLogo = new ImageIcon("board/gameIcon.png");
		g2d.drawImage(gameLogo.getImage(), 20, 25, null);
		ImageIcon scorePanel = new ImageIcon("board/scorePanel.png");
		g2d.drawImage(scorePanel.getImage(), 230, 25, null);

		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		//redraw for every update to the array
		for(int i = 0; i < 4; ++i){
			for(int j = 0; j < 4; ++j){
				if(gameboard[i][j] == 0){
					ImageIcon image = new ImageIcon("blocks/blankTile.png");
					g.drawImage(image.getImage(), locX[j], locY[i], null);
				} else {
					int offset = 0;
					if(gameboard[i][j] > 256) offset = 18;
					ImageIcon image = new ImageIcon("blocks/" + gameboard[i][j] + ".png");
					g.drawImage(image.getImage(), locX[j] - offset, locY[i] - offset, null);
				}			
			}
		}
		try { //score and end screen section
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			Font font = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/2048font.ttf"));
			ge.registerFont(font);
			g2d.setFont(font.deriveFont(24.0f));
			FontMetrics m = g2d.getFontMetrics();
			
			int fontX = (584 - m.stringWidth(f.format(score))) / 2;
			int fontX_1 = (845 - m.stringWidth(f.format(maxScore))) / 2;
			
			g2d.setColor(Color.WHITE);
			g2d.drawString(f.format(score), fontX, 69);
			g2d.drawString(f.format(maxScore), fontX_1, 69);
			
			g2d.setFont(font.deriveFont(64.0f));
			
			if(KeyInput.lose || KeyInput.win){
				System.out.println(show);
				if(KeyInput.win){
					g2d.setColor(new Color(244, 221, 142, alpha));
					g2d.fillRect(0, 101, 500, 500);
					g2d.setColor(new Color(255, 255, 255, alpha1));
					g2d.drawString("You Win!", 120, 290);
					if(show) buttons = 2;			
				} else if(KeyInput.lose){
					g2d.setColor(new Color(255, 255, 255, alpha));
					g2d.fillRect(0, 101, 500, 500);
					g2d.setColor(new Color(119, 110, 101, alpha1));
					g2d.drawString("Game Over", 86, 320);
					if(show) buttons = 1;
				}
				String s = buttons == 2 ? "2buttonPanel.png" : buttons == 1 ? "buttonPanel_tryAgain.png" : "";
				int xButton = buttons == 2 ? 110 : buttons == 1 ? 190 : 0;
				ImageIcon panel = new ImageIcon("board/" + s);
				g.drawImage(panel.getImage(), xButton, 350, null);
			}
		} catch(IOException | FontFormatException e){
			e.printStackTrace();
		}	
	}
	
	public static int[][] addATile(int[][] arr){
		int x = random(0, 4), y = random(0, 4);
		if(arr[x][y] != 0){
			addATile(arr);
		} else {
			arr[x][y] = random(1, 2) * 2;
		}
		return arr;
	}
	
	public static int[] moveColumns(int[] toAlter){
		LinkedList<Integer> l = new LinkedList<Integer>();
		for(int i = 0; i < 4; ++i){
			if(toAlter[i] != 0) l.add(toAlter[i]);
		}
		if(l.size() == 0) return toAlter;
		else {
			if(l.size() < 4){
				int wtfjava = 4 - l.size();
				for(int i = 0; i < wtfjava; ++i){
					l.add(0);
				}
			}
			int[] ret = new int[4];
			for(int j = 0; j < 4; ++j){
				ret[j] = l.removeFirst();
			}
			return ret;
		}
	}
	
	public static int[] join(int[] toAlter){
		LinkedList<Integer> l = new LinkedList<Integer>();
		for(int i = 0; i < 4; ++i){
			if(toAlter[i] == 0) continue;
			int value = toAlter[i];
			if(i < 3 && toAlter[i] == toAlter[i+1]){
				value *= 2;
				score += value; ++i;		
			}
			l.add(value);
		}
		if(l.size() == 0) return toAlter;
		else {
			if(l.size() < 4){
				int wtfjava = 4 - l.size();
				for(int i = 0; i < wtfjava; ++i){
					l.add(0);
				}	
			}
			int[] ret = new int[4];
			for(int j = 0; j < 4; ++j){
				ret[j] = l.removeFirst();
			}
			return ret;
		}
	}
	
	public static int[][] rotate(int deg, int[][] arr){
		int[][] ret = new int[4][4];
		int x = -1; int y = -1; //last row/column number would be array index 3 (technically they both start at 3)
		if(deg < 0) deg += 360; //if(deg > 360) deg -= 360; 
		switch(deg){
			case 90: x = 3; y = 0; break;
			case 180: x = 3; y = 3; break;
			case 270: x = 0; y = 3; break;
		}
		double r = (Math.toRadians(deg));
		for(int i = 0; i < 4; ++i){
			for(int j = 0; j < 4; ++j){
				/* coordinate rotation in trig:
				 * x' = xcos(t) - ysin(t)
				 * y' = xsin(t) + ycos(t)
				 * offset = shape with axis 3 (depending on rotation) 
				 * (3, 3) + 90deg = (3, 0) therefore the y-value above would be set to 0
				 */
				int cost = (int) Math.cos(r);
				int sint = (int) Math.sin(r);
				ret[(i * cost) - (j * sint) + x][(i * sint) + (j * cost) + y] = arr[i][j];  
			}
		}
		return ret;
	}
	
	public static void resetGame() {
		show = false; buttons = 0; KeyInput.lose = false; KeyInput.win = false; alpha = 0; alpha1 = 0;
		for(int i = 0; i < 4; ++i){
			for(int j = 0; j < 4; ++j){
				gameboard[i][j] = 0;
			}
		}
		/* auto-win code
		 * gameboard[0][1] = 1024; gameboard[0][2] = 1024;	
		 */
		boolean addS = false;
		if(score != 0) addS = true;
		if(addS){
			scoreArray.add(score);
			maxScore = Collections.max(scoreArray);
			score = 0;
		}
		addATile(gameboard); addATile(gameboard);
	}

	public void tick() {
		if(!(alpha > 150) && (KeyInput.win || KeyInput.lose)){
			alpha += 1; 
			if(alpha > 180) show = true;
		}		
		if(!(alpha1 > 254) && (KeyInput.win || KeyInput.lose)){
			alpha1 += 3;
			if(alpha1 > 180) show = true;
		}
		if(KeyInput.move){
			
		}
		//todo = find a way to set gameboard to x/y and then move them accordingly
	}
}
