package domain;

import game.EndOfGameException;

import java.awt.Rectangle;
import java.math.BigDecimal;

public interface ICollider 
{ 
	boolean collidesWith(ICollider otherObject); 
	void handleCollision(ICollider otherObject) throws EndOfGameException; 
	Rectangle getBounds();
	BigDecimal getX();
	BigDecimal getY();
} 