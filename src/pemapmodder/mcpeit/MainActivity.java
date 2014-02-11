package pemapmodder.mcpeit;

import pemapmodder.utils.Utils;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends Activity implements android.view.View.OnClickListener{
	public final static int ID_MODMAKER=0x8af3f000;
	@Override protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(layout());
		checkIO();
	}
	private LinearLayout layout() {
		LinearLayout ret=new LinearLayout(this);
		Button modMaker=new Button(this);
		modMaker.setText(R.string.MAIN_modMaker);
		modMaker.setId(ID_MODMAKER);
		modMaker.setOnClickListener(this);
		ret.addView(modMaker, Utils.flatParams);
		return ret;
	}
	@Override public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case ID_MODMAKER:
			
		}
	}
	public void checkIO(){
		String state=Environment.getExternalStorageState();
		if(state!=Environment.MEDIA_MOUNTED){
			Toast.makeText(this, R.string.MAIN_noMedia, Toast.LENGTH_LONG).show();
			finish();
		}
	}
}
