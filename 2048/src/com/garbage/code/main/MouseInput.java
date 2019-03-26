package com.garbage.code.main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInput implements MouseListener {
	
	private Handler handler;
	public MouseInput(Handler handler){
		this.handler = handler;
	}
	
	public void mouseClicked(MouseEvent e) {
		
		int xPos = e.getX();
		int yPos = e.getY();
		for(int k = 0; k < handler.object.size(); ++k){
			GameObject temp = handler.object.get(k);
			if(temp.getID() == ID.Tile){
				if(e.getButton() == MouseEvent.BUTTON1){
					if(Tile.show){
						if(Tile.buttons == 2){
							if(xPos >= 271 && xPos <= 386 && yPos >= 350 && yPos <= 389){			
								Tile.resetGame();
							}
							if(xPos >= 110 && xPos <= 256 && yPos >= 350 && yPos <= 389){
								//keep going
							}
						} else if(Tile.buttons == 1){
							if(xPos >= 190 && xPos <= 305 && yPos >= 350 && yPos <= 389){			
								Tile.resetGame();
							}
						}
					}
				}
			}
		}
	}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e){}
	
}
