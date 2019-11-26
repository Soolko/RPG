package rpg;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class Config
{
	protected final File file;
	public Map<String, Object> data;
	
	public Config(File file, Config.Callbacks callbacks) throws IOException
	{
		this.file = file;
		data = file.createNewFile() ? load(file) : callbacks.defaultState();
	}
	
	public static Map<String, Object> load(File file) throws IOException
	{
		final Yaml yaml = new Yaml();
		if(!file.createNewFile()) return yaml.load(new FileInputStream(file));
		else return new HashMap<String, Object>();
	}
	
	public void save() throws IOException
	{
		final Yaml yaml = new Yaml();
		String output = yaml.dump(data);
		
		// Clear
		file.delete();
		file.createNewFile();
		
		// Write
		try(FileWriter fw = new FileWriter(file)) { fw.write(output); }
	}
	
	public interface Callbacks
	{
		public Map<String, Object> defaultState();
	}
}
