package pemapmodder.modmaker;

import java.io.File;

import android.content.Context;
import android.os.Environment;

@SuppressWarnings("unused")
public class ModMaker {
	private File dir;
	private Context app;
	public static ModMaker create(String name, Context app)throws Exception{
		return new ModMaker(new File(Environment.getExternalStorageDirectory()+"games/pemapmodder/modmaker/"+name+'/'), app);
	}
	public ModMaker(File file, Context app)throws Exception{
		this.dir=file;
		this.setApp(app);
		this.init();
	}
	private void init()throws Exception{
		if(this.dir.isFile())throw new Exception("isFile");
		if(this.dir.isDirectory()){
			
		}else{
			this.dir.mkdirs();
			
		}
	}
	public File getDir() {
		return dir;
	}
	public void setApp(Context app) {
		this.app=app;
	}
}
