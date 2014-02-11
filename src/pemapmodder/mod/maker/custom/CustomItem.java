package pemapmodder.mod.maker.custom;

import java.io.File;

import pemapmodder.mod.maker.ModMaker;
import pemapmodder.utils.PropertiesDb;
import android.content.Context;
import android.os.Bundle;

public class CustomItem extends PropertiesDb{
	public final static String KEY_TEXTURE_X="textureX",
			KEY_TEXTURE_Y="textureY",
			KEY_NAME="name",
			KEY_ID="id";
	private int id, x, y;
	private String name;
	public CustomItem(int id, int x, int y, String name, Context app, ModMaker maker) throws Exception{
		super(app, new File(maker.getDir()+"custom_items/"+Integer.toString(id)+".db"),
				bundlize(id, x, y, name),
				new String[]{KEY_ID, KEY_TEXTURE_X, KEY_TEXTURE_Y, KEY_NAME},
				new int[]{TYPE_INT, TYPE_INT, TYPE_INT, TYPE_STRING});
		this.id=id;
		this.x=x;
		this.y=y;
		this.name=name;
	}
	private static Bundle bundlize(int id, int x, int y, String name){
		Bundle b=new Bundle();
		b.putInt(KEY_ID, id);
		b.putInt(KEY_TEXTURE_X, x);
		b.putInt(KEY_TEXTURE_Y, y);
		b.putString(KEY_NAME, name);
		return b;
	}
	public int getId(){
		return id;
	}
	public int[] getTexture(){
		return new int[]{x, y};
	}
	public String getName(){
		return new String(name);
	}
}
