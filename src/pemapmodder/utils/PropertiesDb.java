package pemapmodder.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.OutputStreamWriter;

import android.content.Context;
import android.os.Bundle;

public class PropertiesDb{
	public final static int TYPE_DOUBLE=0;
	public final static int TYPE_DOUBLE_ARRAY=1;
	public final static int TYPE_INT=2;
	public final static int TYPE_INT_ARRAY=3;
	public final static int TYPE_STRING=4;
	public final static int TYPE_STRING_ARRAY=5;
	public final static int TYPE_PROPERTIES_DB=6;
	public final static String ARRAY_SPLITER="0~1`2~3`4~5`6~7`8~9";
	public final static String[] getTypeNames(){
		String[] out={};
		out[TYPE_DOUBLE]="oneDbl";
		out[TYPE_DOUBLE_ARRAY]="dblAry";
		out[TYPE_INT]="oneInt";
		out[TYPE_INT_ARRAY]="intAry";
		out[TYPE_STRING]="oneStr";
		out[TYPE_STRING]="strAry";
		out[TYPE_PROPERTIES_DB]="propdb";
		return out;
	}
	public static final int getType(String name){
		for(int i=0; i<getTypeNames().length; i++){
			if(getTypeNames()[i]==name)
				return i;
		}
		return -1;
	}
	public Context app;
	public File file;
	public Bundle data;
	public String[] keys;
	public int[] types;
	public PropertiesDb(Context app, File file, Bundle data, String[] keys, int[] types)throws Exception{
		if(file.isDirectory())throw new Exception();
		this.app=app;
		this.file=file;
		this.data=data;
		this.keys=keys;
		this.types=types;
		file.createNewFile();
		if(!file.exists())
			save();
		else reload();
	}
	public Bundle getData() {
		return data;
	}
	public void reload()throws Exception{
		BufferedReader br=null;
		this.data=new Bundle();
		try{
			br=new BufferedReader(new java.io.InputStreamReader(new java.io.FileInputStream(file)));
			String[] lines={};
			String line;
			while(null!=(line=br.readLine()))
				lines[lines.length]=line;
			br.close();
			for(int i=0; i<lines.length; i++){
				line=lines[i];
				if(line.charAt(6)!='>')
					throw new Exception("corrupted");
				int type=getType(line.substring(0, 6));
				if(type==-1)
					throw new Exception("corrupted");
				String key=line.split("=")[0];
				String datum=line.split("=", 2)[1];
				switch(type){
				case TYPE_DOUBLE:
					try{
						Double.parseDouble(datum);
					}catch(NumberFormatException e){
						throw new Exception("corrupted");
					}
					this.data.putDouble(key, Double.parseDouble(datum));
					this.keys[i]=key;
					this.types[i]=type;
					break;
				case TYPE_INT:
					try{
						Integer.parseInt(datum);
					}catch(NumberFormatException e){
						throw new Exception("corrupted");
					}
					this.data.putInt(key, Integer.parseInt(datum));
					this.keys[i]=key;
					this.types[i]=type;
					break;
				case TYPE_STRING:
					this.data.putString(key, datum);
					this.keys[i]=key;
					this.types[i]=type;
					break;
				case TYPE_DOUBLE_ARRAY:
					String[] doubles=datum.split(ARRAY_SPLITER);
					double[] dbls={};
					for(int j=0; j<doubles.length; j++)
						dbls[dbls.length]=Double.parseDouble(doubles[j]);
					this.data.putDoubleArray(key, dbls);
					this.keys[i]=key;
					this.types[i]=type;
					break;
				case TYPE_INT_ARRAY:
					String[] integers=datum.split(ARRAY_SPLITER);
					int[] ints={};
					for(int j=0; j<ints.length; j++)
						ints[ints.length]=Integer.parseInt(integers[j]);
					this.data.putIntArray(key, ints);
					this.keys[i]=key;
					this.types[i]=type;
					break;
				case TYPE_STRING_ARRAY:
					this.data.putStringArray(key, datum.split(ARRAY_SPLITER));
					this.keys[i]=key;
					this.types[i]=type;
					break;
				case TYPE_PROPERTIES_DB:
					this.data.putString(key, datum);
					this.keys[i]=key;
					this.types[i]=type;
					break;
				}
			}
		}catch(Exception e){
			if(br!=null)
				br.close();
			throw e;
		}
	}
	public File getFile(){
		return file;
	}
	public void save()throws Exception{
		OutputStreamWriter out = null;
		try{
			out=new OutputStreamWriter(new java.io.FileOutputStream(file));
			for(int i=0; i<keys.length; i++){
				switch(types[i]){
				case TYPE_INT:
					int oneInt=data.getInt(keys[i]);
					out.append(getTypeNames()[TYPE_INT]);
					out.append('>');
					out.write(keys[i]);
					out.append('=');
					out.write(Integer.toString(oneInt));
					break;
				case TYPE_DOUBLE:
					double oneDouble=data.getDouble(keys[i]);
					out.append(getTypeNames()[TYPE_DOUBLE]);
					out.append('>');
					out.write(keys[i]);
					out.append('=');
					out.write(Double.toString(oneDouble));
					break;
				case TYPE_STRING:
					out.append(getTypeNames()[TYPE_STRING]);
					out.append('>');
					out.write(keys[i]);
					out.append('=');
					out.write(data.getString(keys[i]));
					break;
				case TYPE_INT_ARRAY:
					int[] ints=data.getIntArray(keys[i]);
					out.write(getTypeNames()[TYPE_INT_ARRAY]);
					out.append('>');
					out.write(keys[i]);
					out.append('=');
					if(ints.length==0)
						break;
					for(int j=0; j<ints.length-1; j++){
						out.write(Integer.toString(ints[j]));
						out.write(ARRAY_SPLITER);
					}
					out.write(Integer.toString(ints[ints.length-1]));
					break;
				case TYPE_DOUBLE_ARRAY:
					double[] dbls=data.getDoubleArray(keys[i]);
					out.write(getTypeNames()[TYPE_DOUBLE_ARRAY]);
					out.append('>');
					out.write(keys[i]);
					out.append('=');
					if(dbls.length==0)
						break;
					for(int j=0; j<dbls.length-1; j++){
						out.write(Double.toString(dbls[j]));
						out.write(ARRAY_SPLITER);
					}
					out.write(Double.toString(dbls[dbls.length-1]));
					break;
				case TYPE_STRING_ARRAY:
					String[] strs=data.getStringArray(keys[i]);
					out.write(getTypeNames()[TYPE_STRING_ARRAY]);
					out.append('>');
					out.write(keys[i]);
					out.write('=');
					if(strs.length==0)
						break;
					for(int j=0; j<strs.length-1; j++){
						out.write(strs[j]);
						out.write(ARRAY_SPLITER);
					}
					out.write(strs[strs.length-1]);
					break;
				case TYPE_PROPERTIES_DB:
					String path=data.getString(keys[i]);
					out.write(getTypeNames()[TYPE_PROPERTIES_DB]);
					out.append('>');
					out.write(keys[i]);
					out.write('=');
					out.write(path);
					break;
				}
				out.append('\n');
			}
			out.close();
		}catch(Exception e){
			if(out!=null)
				out.close();
			throw e;
		}
	}
	public void setInt(int data, String key){
		for(int i=0; i<keys.length; i++){
			if(keys[i]==key){
				types[i]=TYPE_INT;
				this.data.putInt(key, data);
				return;
			}
		}
		keys[keys.length]=key;
		types[types.length]=TYPE_INT;
		this.data.putInt(key, data);
	}
	public void setDouble(double data, String key){
		for(int i=0; i<keys.length; i++){
			if(keys[i]==key){
				types[i]=TYPE_DOUBLE;
				this.data.putDouble(key, data);
				return;
			}
		}
		keys[keys.length]=key;
		types[types.length]=TYPE_DOUBLE;
		this.data.putDouble(key, data);
	}
	public void setString(String data, String key){
		for(int i=0; i<keys.length; i++){
			if(keys[i]==key){
				types[i]=TYPE_STRING;
				this.data.putString(key, data);
				return;
			}
		}
		keys[keys.length]=key;
		types[types.length]=TYPE_STRING;
		this.data.putString(key, data);
	}
	public void setIntArray(int[] data, String key){
		for(int i=0; i<keys.length; i++){
			if(keys[i]==key){
				types[i]=TYPE_INT_ARRAY;
				this.data.putIntArray(key, data);
				return;
			}
		}
		keys[keys.length]=key;
		types[types.length]=TYPE_INT_ARRAY;
		this.data.putIntArray(key, data);
	}
	public void setDoubleArray(double[] data, String key){
		for(int i=0; i<keys.length; i++){
			if(keys[i]==key){
				types[i]=TYPE_DOUBLE_ARRAY;
				this.data.putDoubleArray(key, data);
				return;
			}
		}
		keys[keys.length]=key;
		types[types.length]=TYPE_DOUBLE_ARRAY;
		this.data.putDoubleArray(key, data);
	}
	public void setStringArray(String[] data, String key){
		for(int i=0; i<keys.length; i++){
			if(keys[i]==key){
				types[i]=TYPE_STRING_ARRAY;
				this.data.putStringArray(key, data);
				return;
			}
		}
		keys[keys.length]=key;
		types[types.length]=TYPE_STRING_ARRAY;
		this.data.putStringArray(key, data);
	}
	public void setDb(PropertiesDb db, String key){
		String data=db.getFile().getAbsolutePath();
		for(int i=0; i<keys.length; i++){
			if(keys[i]==key){
				types[i]=TYPE_PROPERTIES_DB;
				this.data.putString(key, data);
				return;
			}
		}
		keys[keys.length]=key;
		types[types.length]=TYPE_PROPERTIES_DB;
		this.data.putString(key, data);
	}
	public String[] getStringArray(String key){
		return data.getStringArray(key);
	}
	public String getString(String key){
		return data.getString(key);
	}
	public int getInt(String key){
		return data.getInt(key);
	}
	public int[] getIntArray(String key){
		return data.getIntArray(key);
	}
	public double getDouble(String key){
		return data.getDouble(key);
	}
	public double[] getDoubleArray(String key){
		return data.getDoubleArray(key);
	}
	public PropertiesDb getDb(String key) throws Exception{
		return new PropertiesDb(app, new File(data.getString(key)), new Bundle(), new String[0], new int[0]);
	}
}
