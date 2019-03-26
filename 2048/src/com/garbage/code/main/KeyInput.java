package com.garbage.code.main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;

public class KeyInput extends KeyAdapter {
	
	private Handler handler;
	static boolean win = false;
	static boolean lose = false;
	static boolean move = false;
	
	public KeyInput(Handler handler){
		this.handler = handler;
	}
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		for(int k = 0; k < handler.object.size(); ++k){
			GameObject temp = handler.object.get(k);
			if(temp.getID() == ID.Tile){
				if(win || lose) return;
				if(key == KeyEvent.VK_UP){
					int[][] tempArr = Tile.gameboard;
					tempArr = Tile.rotate(90, tempArr);
					tempArr = Tile.rotate(-90, goLeft(tempArr));
					move = true;
					Tile.gameboard = tempArr;
				}
				if(key == KeyEvent.VK_DOWN){
					int[][] tempArr = Tile.gameboard;
					tempArr = Tile.rotate(-90, tempArr);
					tempArr = Tile.rotate(90, goLeft(tempArr));
					Tile.gameboard = tempArr;
				}
				if(key == KeyEvent.VK_LEFT){
					Tile.gameboard = goLeft(Tile.gameboard);
				}
				if(key == KeyEvent.VK_RIGHT){
					int[][] tempArr = Tile.gameboard;
					tempArr = Tile.rotate(180, tempArr);
					tempArr = Tile.rotate(180, goLeft(tempArr));
					Tile.gameboard = tempArr;
				}
				if(key == KeyEvent.VK_ESCAPE){
					Tile.resetGame();
				}
				win = checkWin(Tile.gameboard, win);
				lose = checkLose(Tile.gameboard, lose);
			}
		}
	}
	
	public int[] splitRows(int[][] toSplit){
		int[] ret = new int[16];
		for(int i = 0; i < 4; ++i){
			for(int j = 0; j < 4; ++j){
				ret[i*4 + j] = toSplit[i][j];
			}
		}
		return ret;
	}
	
	public int[] getRow(int[] toGet, int index){
		int[] ret = new int[4];
		for(int i = 0; i < 4; ++i){
			ret[i] = toGet[index*4 + i];
		}
		return ret;
	}
	
	public int[][] goLeft(int[][] arr){
		int[][] toInt = new int[4][4];
		int[] newInt = splitRows(arr);
		for(int i = 0; i < 4; ++i){
			int[] thisLine = getRow(newInt, i);
			int[] out = Tile.join(Tile.moveColumns(thisLine));
			for(int j = 0; j < 4; ++j){
				toInt[i][j] = out[j];
			}
		}
		boolean add = true;
		if(Arrays.deepEquals(arr, toInt)) add = false;
		arr = toInt;
		if(add) Tile.addATile(arr);
		return arr;
	}
	
	public boolean checkLose(int[][] arr, boolean b){
		for(int i = 0; i < 4; ++i){
			for(int j = 0; j < 4; ++j){
				if(arr[i][j] == 0) return false; 
				if(i < 3 && arr[i][j] == arr[i+1][j] || j < 3 && arr[i][j] == arr[i][j+1]) return false;
			}
		}
		return true;
	}
	
	public boolean checkWin(int[][] arr, boolean b){
		for(int i = 0; i < 4; ++i){
			for(int j = 0; j < 4; ++j){
				if(arr[i][j] == 2048) return true;
			}
		}
		return false;
	}
}
