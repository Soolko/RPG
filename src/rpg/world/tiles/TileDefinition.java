package rpg.world.tiles;

import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.io.Serializable;
import java.util.HashMap;

public class TileDefinition implements Serializable
{
	private static final long serialVersionUID = 9212750754180264807L;
	
	/*
	 * DO NOT USE FINAL ON THESE
	 * -------------------------
	 * 
	 * It messes with serialisation in the YAML file as it tries to set them for some reason.
	 * The "transient" keyword does literally nothing.
	 */
	
	/** The key to use to look up this tile in the program. */
	public String key;
	
	/** Path to the used texture. */
	public File texturePath;
	/** Colour to set the texture to. */
	public Color colour = Color.white;
	public int textureScale = 16;
	
	/*
	 * Animation Frames:
	 * "default" will be played as the default image if animated is set to true.
	 * The rest are component-specific.
	 */
	public boolean animated = false;
	public HashMap<String, Point> animationFrames = new HashMap<String, Point>();
	
	public boolean collideable = true;
}
