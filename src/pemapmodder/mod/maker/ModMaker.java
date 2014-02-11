package pemapmodder.mod.maker;

import java.io.File;

import pemapmodder.mod.maker.custom.CustomBlock;
import pemapmodder.mod.maker.custom.CustomItem;
import pemapmodder.mod.maker.custom.CustomMob;
import pemapmodder.utils.PropertiesDb;
import pemapmodder.utils.Utils;
import android.content.Context;
import android.os.Bundle;

public class ModMaker extends PropertiesDb{
	public static ModMaker create(String name, Context app, Bundle args)throws Exception{
		return new ModMaker(new File(android.os.Environment.getExternalStorageDirectory()+"games/pemapmodder/modmaker/"+name+'/'), app, args);
	}
	private File dir;
	private Bundle args;
	private CustomItem[] customItems={};
	private CustomBlock[] customBlocks={};
	private CustomMob[] customMobs={};
	public ModMaker(File file, Context app, Bundle args)throws Exception{
		super(app, new File(file, "main.db"), new Bundle(), new String[0], new int[0]);
		this.dir=file;
		if(this.dir.isFile())throw new Exception("isFile");
		this.args=args;
		this.init();
	}
	private void init()throws Exception{
		String contents=Utils.readFile(app.getAssets().open("default_prefix_comment.js"));
		contents=contents.replace("****MCPEIT_REM_COM_AUTHOR_STRING", args.getString("author"))
				.replace("****MCPEIT_REM_COM_NAME_STRING****", args.getString("name"))
				.replace("****MCPEIT_REM_COM_VERSION_STRING****", args.getString("version"))
				;
	}
	public CustomItem defineItem(int id, String name, int textureX, int textureY) throws Exception{
		if(textureX>15||textureY>15||textureX<0||textureY<0)
			return null;
		for(int i=0; i<customItems.length; i++){
			if(customItems[i].getId()==id)
				return null;
		}
		int offset=customItems.length;
		this.customItems[offset]=new CustomItem(id, textureX, textureY, name, app, this);
		return customItems[offset];
	}
	public File getDir() {
		return dir;
	}
	public void export(java.io.OutputStreamWriter target) throws java.io.IOException{
		
	}
	public Context getApp(){
		return app;
	}
}
