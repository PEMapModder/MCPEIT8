package pemapmodder.modmaker;

import java.io.File;

import pemapmodder.utils.PropertiesDb;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;

@SuppressWarnings("unused")
public class ModMaker extends PropertiesDb{
	private File dir;
	private PropertiesDb db;
	public static ModMaker create(String name, Context app)throws Exception{
		return new ModMaker(new File(Environment.getExternalStorageDirectory()+"games/pemapmodder/modmaker/"+name+'/'), app);
	}
	public ModMaker(File file, Context app)throws Exception{
		super(app, new File(file, "main.properties"), new Bundle(), new String[0], new int[0]);
		this.dir=file;
		if(this.dir.isFile())throw new Exception("isFile");
		this.init();
	}
	private void init()throws Exception{
		
	}
	public File getDir() {
		return dir;
	}
}
