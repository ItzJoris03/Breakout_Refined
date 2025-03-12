package com.breakout.physics;

import com.breakout.entities.Sprite;

public interface IMovable {
	public boolean hasCollided(Sprite sprite);
}
