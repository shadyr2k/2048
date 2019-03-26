package com.garbage.code.main;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Board extends GameObject {
	
	public Board(int x, int y, ID id){
		super(x, y, id);
	}

	public void tick() {

	}

	public void render(Graphics g) {
		ImageIcon im = new ImageIcon("board/board.png");
		Image i = resize(im.getImage(), 500, 500);
		g.drawImage(i, x, y, null);
	}
}
