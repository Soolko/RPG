package rpg.world.structure;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.yaml.snakeyaml.Yaml;

public class Structure
{
	public List<Entry> entries = new ArrayList<Entry>();
	
	public static class Entry
	{
		public String resource;
		public Color colour = Color.white;
		
		public int x, y;
		
		public boolean collideable = true;
		public boolean water = false;
	}
	
	// Load & Save
	public static Structure load(File yamlFile) throws FileNotFoundException
	{
		final Yaml yaml = new Yaml();
		return yaml.<Structure>load(new FileInputStream(yamlFile));
	}
}
