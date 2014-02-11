/*
 * @Copyright (C) 2013-2014 PEMapModder
 * 
 * You may share redistributions of this software for non-commercial use as long as you indicate the original creator PEMapModder and the source https://github.com/pemapmodder/MCPEIT-new.git
 */

package pemapmodder.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

public class Utils {
	/**
	 * totally wrap content
	 */
	public final static LayoutParams wrapParams=new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
	/**
	 * full width, wrap height
	 */
	public final static LayoutParams flatParams=new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
	/**
	 * file path of the config file
	 */
	public final static String FILE_PATH=Environment.getExternalStorageDirectory().getAbsolutePath()+"/games/pemapmodder.mcpeit/DONTDELETEME.txt";
	/**
	 * The app's directory on the external storage
	 * @return directory of the app
	 */
	public final static File getAppDir(){
		File r=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/games/pemapmodder.mcpeit/");
		r.mkdirs();
		return r;
	}
	/**
	 * Find the first occurrence of an object in an object array
	 * 
	 * @param array array to search in
	 * @param item the object to search
	 * @return the key of the object, or -1 if not found
	 */
	public static int isInArray(Object[] array, Object item){
		for(int i=0;i<array.length;i++){
			if(array[i].equals(item)){
				return i;
			}
		}
		return -1;
	}
	/**
	 * Gets the contents in a file as a string
	 * @param file the file to read
	 * @return the contents in the file
	 * @throws Throwable in case the file is not readable
	 */
	public static String readFile(File f)throws java.io.IOException{
		return readFile(new FileInputStream(f));
	}
	/**
	 * Gets the contents readable in an input stream as a string
	 * @param is input stream of the file
	 * @return the contents readable in the stream
	 * @throws Throwable in case the stream is not readable
	 * @see readFile(File)
	 */
	public static String readFile(InputStream is)throws java.io.IOException{
		String ret="";
		BufferedReader br=new BufferedReader(new InputStreamReader(is));
		try{
			for(String line=br.readLine();line!=null;line=br.readLine())
				ret+=(line+"\n");
		}finally{
			br.close();
			is.close();
		}
		return ret;
	}
	public final static String[][][] getNames(Activity a){
		String[][][] ret={};
		try{
			String c=readFile(a.getAssets().open("item_names.txt"));
			String[] itemsRaw=c.split("\n");
			String[] items={};
			for(int i=0;i<itemsRaw.length;i++){
				if(itemsRaw[i].charAt(0)=='#')continue;
				items[items.length]=itemsRaw[i];
			}
			String[][] itemDamages={};
			for(int i=0;i<items.length;i++){
				
				itemDamages[i]=items[i].split(";");
			}
			
			for(int id=0;id<itemDamages.length;id++){
				for(int damage=0;damage<itemDamages[id].length;damage++){
					ret[id][damage]=itemDamages[id][damage].split(",");
				}
			}
		}catch(Throwable e){
			if(e.getClass()==IndexOutOfBoundsException.class){
				Toast.makeText(a, "While trying to get name of an item, an offset outofbouds error occured", Toast.LENGTH_LONG).show();
				Log.e("pemapmodder.utils.Utils.getNames()", "IndexOutOfBoundsException");
			}
			Log.e("pemapmodder.utils.Utils.getNames()", e.toString());
			return null;
		}
		return ret;
	}
	/**
	 * Prints an error
	 * @param ctx
	 * @param e
	 */
	public static void err(Context ctx, Throwable e) {
		Log.e(ctx.getClass().getPackage().getName()+'.'+ctx.getClass().getName(), e.toString());
		Toast.makeText(ctx, (e.getMessage()==null?e.toString():e.getMessage()), Toast.LENGTH_LONG).show();
	}
	public static boolean compare(String a, String b) {
		return a.contains(b)||b.contains(a)||a==null||b==null||a==""||b=="";
	}
	public static String[] evalAssets(String filename,Context ctx) throws Throwable{
		InputStream is=ctx.getAssets().open(filename);
		String[] lines=readFile(is).split("\n");
		String[] out={};
		for(int i=0;i<lines.length;i++){
			if(lines[i].charAt(0)!='#'&&lines[i]!="")//if not (begin with # or empty)
				out[out.length]=lines[i];
		}
		return out;
	}
	
}
