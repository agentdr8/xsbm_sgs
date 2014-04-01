package com.dr8.sbicons.sgs.mod;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.dr8.sbicons.sgs.mod.IconPackActivity;
import com.dr8.sbicons.sgs.mod.ZipStuff;
import com.dr8.sbicons.sgs.R;

@SuppressLint("WorldReadableFiles")
public class IconPackActivity extends ListActivity implements OnItemLongClickListener
{
	private String intpath = null;
	private String extpath = null;
	private ProgressDialog pd;

	private ArrayAdapter<String> adapter;
	SharedPreferences prefs = null;

	@SuppressLint("DefaultLocale")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		intpath = getApplicationContext().getFilesDir().getParent() + "/xsbm/";
		extpath = Environment.getExternalStorageDirectory().toString() + "/xsbm/";

		Resources mCtx = this.getResources();

		prefs = getSharedPreferences("com.dr8.sbicons.sgs_preferences", MODE_WORLD_READABLE);

		Uri uri = getIntent().getData();
		final File intentfile = (uri != null) ? new File(uri.getPath()) : null;
		if (intentfile != null) {
			String item = intentfile.getName();
			String path = intentfile.getParent();
			//			Log.i("XSBM", ": filename is " + item + " and path is " + path);
			if (ZipStuff.getPackInfo(item, path, ".xsbmpack") == 1) {
				Toast.makeText(this, item + " selected", Toast.LENGTH_SHORT).show();
				File df = new File(intpath);
				DeleteRecursive(df);
				df.mkdir();
				ZipStuff.unpackZip(intpath, path + "/" + item);
				int sbh = prefs.getInt("sbh", 0);
				int scale = prefs.getInt("icon_size", 50);
				ZipStuff.doResize(mCtx, sbh, scale, df);
				ChmodRecursive(df);
				IconPackActivity.this.finish();
			} else {
				Toast.makeText(this, item + " is not a valid iconpack", Toast.LENGTH_SHORT).show();
				IconPackActivity.this.finish();
			}
		}

		ArrayList<String> filearray = new ArrayList<String>();

		File sd = new File(Environment.getExternalStorageDirectory().toString());
		if (sd.canWrite()) {
			File f = new File(extpath);
			if (f.isDirectory()) {
				if (prefs.getBoolean("firstrun", true)) {
					prefs.edit().putBoolean("firstrun", false).commit();
				}
				try {
					InputStream in = getResources().openRawResource(R.raw.default_iconpack_sgs);
					FileOutputStream out;
					out = new FileOutputStream(extpath + "default_iconpack_sgs.xsbm.zip");
					byte[] buff = new byte[1024];
					int read = 0;
					while ((read = in.read(buff)) > 0) {
						out.write(buff, 0, read);
					}
					in.close();
					out.close();
				} catch (IOException e) {
				}
				final File file[] = f.listFiles();
				for (int i=0; i < file.length; i++) {
					if (file[i].getName().toLowerCase().endsWith(".zip")) {
						filearray.add(file[i].getName());
					}
				}
			} else {
				f.mkdirs();
				InputStream in = getResources().openRawResource(R.raw.default_iconpack_sgs);
				FileOutputStream out;
				File f2 = new File(intpath);
				if (!f2.isDirectory()) {
					f2.mkdirs();
					f2.setExecutable(true, false);
					f2.setReadable(true, false);
					f2.setWritable(true, true);
				}
				try {
					out = new FileOutputStream(extpath + "default_iconpack_sgs.xsbm.zip");
					byte[] buff = new byte[1024];
					int read = 0;
					while ((read = in.read(buff)) > 0) {
						out.write(buff, 0, read);
					}
					in.close();
					out.close();
				} catch (IOException e) {
				} 
				if (prefs.getBoolean("firstrun", true)) {
					String item = "default_iconpack_sgs.xsbm.zip";
					String path = extpath;
					File df = new File(intpath);
					df.mkdir();
					ZipStuff.unpackZip(intpath, path + "/" + item);
					int sbh = prefs.getInt("sbh", 0);
					int scale = prefs.getInt("icon_size", 50);
					ZipStuff.doResize(mCtx, sbh, scale, df);
					ChmodRecursive(df);
					prefs.edit().putBoolean("firstrun", false).commit();
					IconPackActivity.this.finish();
				} else {
					final File file[] = f.listFiles();
					for (int i=0; i < file.length; i++) {
						if (file[i].getName().toLowerCase().endsWith(".zip")) {
							filearray.add(file[i].getName());
						}
					}
				}
			}
		} else {
			Toast.makeText(this, "Your /sdcard is not writeable. Please check XPrivacy or other modules for blocking behavior", Toast.LENGTH_LONG).show();
			IconPackActivity.this.finish();
		}


		ListView lv = getListView();
		LayoutInflater inflater = getLayoutInflater();
		ViewGroup header = (ViewGroup) inflater.inflate(R.layout.list_header, lv, false);
		lv.addHeaderView(header, null, false);

		String[] array = filearray.toArray(new String[filearray.size()]);
		ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(array));
		Collections.sort(arrayList);

		adapter = new ArrayAdapter<String>(this, R.layout.iconpacks, R.id.zipitem, arrayList);

		setListAdapter(adapter);
		this.getListView().setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				String item = (String) getListView().getItemAtPosition(position);
				HashMap<String, String> hash = new HashMap<String, String>();
				hash = ZipStuff.getPackDetail(item, extpath, ".xsbmpack");
				if (hash != null) {
					AlertDialog.Builder builder = new AlertDialog.Builder(IconPackActivity.this);
					builder.setTitle("Pack details")
					.setIcon(R.drawable.about)
					.setMessage("Author: " + hash.get("author") + "\n" + "Notes: " + hash.get("note"))
					.setCancelable(true)
					.setNegativeButton("Close",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
						}
					});

					AlertDialog alert = builder.create();
					alert.show();
				}
				return true;
			}
		});
	}

	public void launchImport(View view, final Resources mCtx, final Integer sbh, final Integer scale, final File df, final String item) {
		AsyncTask<Void, Void, Void> importTask = new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ZipStuff.unpackZip(intpath, extpath + item);
				ZipStuff.doResize(mCtx, sbh, scale, df);
				ChmodRecursive(df);
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				pd.dismiss();
				Toast.makeText(IconPackActivity.this, item + " selected", Toast.LENGTH_SHORT).show();
				IconPackActivity.this.finish();
			}
		};

		pd = ProgressDialog.show(this, "Please Wait", "Importing and scaling icons...", true, false);
		importTask.execute((Void[])null);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Resources mCtx = this.getResources();
		String item = (String) getListView().getItemAtPosition(position);
		if (ZipStuff.getPackInfo(item, extpath, ".xsbmpack") == 1) {
			File df = new File(intpath);
			DeleteRecursive(df);
			df.mkdir();
			int sbh = prefs.getInt("sbh", 0);
			int scale = prefs.getInt("icon_size", 50);
			launchImport(v, mCtx, sbh, scale, df, item);
		} else {
			Toast.makeText(this, item + " is not a valid iconpack", Toast.LENGTH_SHORT).show();
		}
	}

	void DeleteRecursive(File fileOrDirectory) {
		if (fileOrDirectory.isDirectory()) {
			for (File child : fileOrDirectory.listFiles()) {
				DeleteRecursive(child);
			}
		}
		fileOrDirectory.delete();
	}

	void ChmodRecursive(File fileOrDirectory) {
		if (fileOrDirectory.isDirectory()) {
			for (File child : fileOrDirectory.listFiles()) {
				ChmodRecursive(child);
			}
		}
		fileOrDirectory.setExecutable(true, false);
		fileOrDirectory.setReadable(true, false);
		fileOrDirectory.setWritable(true, true);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		return true;
	}
}
