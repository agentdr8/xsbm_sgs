package com.dr8.sbicons.sgs.mod;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.dr8.sbicons.sgs.R;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

@SuppressLint("DefaultLocale")
public class ZipStuff {

	private static String TAG = "XSBM";
	protected static final String APP_LIST = "apps.txt";

	public static boolean unpackZip(String outpath, String zipname) {       
		InputStream is;
		ZipInputStream zis;
		try {
			String filename;
			is = new FileInputStream(zipname);
			zis = new ZipInputStream(new BufferedInputStream(is));          
			ZipEntry ze;
			byte[] buffer = new byte[1024];
			int count;
			while ((ze = zis.getNextEntry()) != null) {
				filename = ze.getName();
				if (ze.isDirectory()) {
					File fmd = new File(outpath + filename);
					fmd.mkdirs();
					fmd.setWritable(true, true);
					fmd.setExecutable(true, false);
					fmd.setReadable(true, false);
					continue;
				}
				FileOutputStream fout = new FileOutputStream(outpath + filename);
				while ((count = zis.read(buffer)) != -1) {
					fout.write(buffer, 0, count);             
				}
				fout.close();               
				zis.closeEntry();
			}
			zis.close();
			File nomedia = new File(outpath + ".nomedia");
			nomedia.createNewFile();
		} catch(IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static void doResize(Resources res, Integer sbh, Integer scale, File fileOrDir) {
		if (fileOrDir.isDirectory()) {
			for (File child : fileOrDir.listFiles()) {
				doResize(res, sbh, scale, child);
			}
		} else if (fileOrDir.getName().endsWith(".png") && (!fileOrDir.getName().contains("anchor")) && (!fileOrDir.getName().contains("select")) && (!fileOrDir.getAbsolutePath().contains("apps")) && (!fileOrDir.getAbsolutePath().contains("framework")) && (!fileOrDir.getAbsolutePath().contains("settings"))) {
			try
			{
				int inWidth = 0;
				int inHeight = 0;

				InputStream in = new FileInputStream(fileOrDir.getAbsolutePath());

				// decode image size (decode metadata only, not the whole image)
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inJustDecodeBounds = true;
				BitmapFactory.decodeStream(in, null, options);
				in.close();
				in = null;

				// save width and height
				inWidth = options.outWidth;
				inHeight = options.outHeight;
//				Log.e("XSBM-Image", fileOrDir.getName() + " is W:" + inWidth + " by H:" + inHeight );

				// scale image 
				if (inWidth < inHeight) {
					if (inHeight != scale) {
						double hfactor = ((double)scale / (double)inHeight);
//						Log.e("XSBM-Image", "hfactor is: " + hfactor);
						double dstWidthF = ((double)inWidth * hfactor);
						int dstWidth = (int) Math.round(dstWidthF);
//						Log.e("XSBM-Image", "dstWidth is: " + dstWidth);
						int dstHeight = scale;
						// decode full image pre-resized
						in = new FileInputStream(fileOrDir.getCanonicalPath());
						// decode full image
						Bitmap roughBitmap = BitmapFactory.decodeStream(in);	    

						// resize bitmap
//						Log.e("XSBM-Image", fileOrDir.getName() + " should now be W:" + dstWidth + " by H:" + dstHeight );
						Bitmap resizedBitmap = Bitmap.createScaledBitmap(roughBitmap, dstWidth, dstHeight, true);

						// create our canvas w/ transparent background
						Bitmap background = BitmapFactory.decodeResource(res, R.drawable.iconbkg).copy(Bitmap.Config.ARGB_8888, true);
						
						// resize our bkg to our statusbar height
						Bitmap resizedBkg = Bitmap.createScaledBitmap(background, dstWidth, sbh, true);
						
						// will allow us to center the overlay onto the background
						int offset = (sbh - scale) / 2;
						
						Canvas canvas = new Canvas(resizedBkg);
						canvas.drawBitmap(resizedBitmap, 0, offset, null);
						
						// save image
						try
						{
							FileOutputStream out = new FileOutputStream(fileOrDir.getAbsolutePath());
							resizedBkg.compress(Bitmap.CompressFormat.PNG, 100, out);
						}
						catch (Exception e)
						{
							Log.e("Image", e.getMessage(), e);
						}
						in.close();
					}
				} else if (inWidth > inHeight) {
					if (inWidth != scale) {
						double wfactor = ((double)scale / (double)inWidth);
//						Log.e("XSBM-Image", "wfactor is: " + wfactor);
						double dstHeightF = ((double)inHeight * wfactor);
						int dstHeight = (int) Math.round(dstHeightF);
//						Log.e("XSBM-Image", "dstHeight is: " + dstHeight);
						int dstWidth = scale;
						// decode full image pre-resized
						in = new FileInputStream(fileOrDir.getAbsolutePath());
						// decode full image
						Bitmap roughBitmap = BitmapFactory.decodeStream(in);	    

						// resize bitmap
//						Log.e("XSBM-Image", fileOrDir.getName() + " should now be W:" + dstWidth + " by H:" + dstHeight );
						Bitmap resizedBitmap = Bitmap.createScaledBitmap(roughBitmap, dstWidth, dstHeight, true);

						// create our canvas w/ transparent background
						Bitmap background = BitmapFactory.decodeResource(res, R.drawable.iconbkg).copy(Bitmap.Config.ARGB_8888, true);
						// resize our bkg to our statusbar height
						Bitmap resizedBkg = Bitmap.createScaledBitmap(background, dstWidth, sbh, true);
						int offset = (sbh - dstHeight) / 2;
						Canvas canvas = new Canvas(resizedBkg);
						canvas.drawBitmap(resizedBitmap, 0, offset, null);
						
						// save image
						try
						{
							FileOutputStream out = new FileOutputStream(fileOrDir.getAbsolutePath());
							resizedBkg.compress(Bitmap.CompressFormat.PNG, 100, out);
						}
						catch (Exception e)
						{
							Log.e("Image", e.getMessage(), e);
						}
						in.close();
					}
				} else if (inWidth == inHeight) {
					if (inWidth != scale || inHeight != scale) {
						double wfactor = ((double)scale / (double)inWidth);
//						Log.e("XSBM-Image", "wfactor is: " + wfactor);
						double dstHeightF = ((double)inWidth * wfactor);
						int dstHeight = (int) Math.round(dstHeightF);
//						Log.e("XSBM-Image", "dstHeight is: " + dstHeight);
						int dstWidth = scale;
						// decode full image pre-resized
						in = new FileInputStream(fileOrDir.getAbsolutePath());
						// decode full image
						Bitmap roughBitmap = BitmapFactory.decodeStream(in);	    

						// resize bitmap
//						Log.e("XSBM-Image", fileOrDir.getName() + " should now be W:" + dstWidth + " by H:" + dstHeight );
						Bitmap resizedBitmap = Bitmap.createScaledBitmap(roughBitmap, dstWidth, dstHeight, true);

						// create our canvas w/ transparent background
						Bitmap background = BitmapFactory.decodeResource(res, R.drawable.iconbkg).copy(Bitmap.Config.ARGB_8888, true);
						// resize our bkg to our statusbar height
						Bitmap resizedBkg = Bitmap.createScaledBitmap(background, sbh, sbh, true);
						int offset = (sbh - scale) / 2;
						Canvas canvas = new Canvas(resizedBkg);
						canvas.drawBitmap(resizedBitmap, offset, offset, null);
						
						// save image
						try
						{
							FileOutputStream out = new FileOutputStream(fileOrDir.getAbsolutePath());
							resizedBkg.compress(Bitmap.CompressFormat.PNG, 100, out);
						}
						catch (Exception e)
						{
							Log.e("Image", e.getMessage(), e);
						}
						in.close();
					}
				}

			}
			catch (IOException e)
			{
				Log.e("Image", e.getMessage(), e);
			}
		}
}



public static Bitmap getBitmap(final String FilePath, final String imageFile, final Integer density) {
	//	    Log.i(TAG, "Getting image '" + imageFileInZip + "' from '" + zipFilePath +"'");
	Bitmap result = null;
	try {
		FileInputStream fis = new FileInputStream(FilePath + imageFile);
		result = BitmapFactory.decodeStream(fis);
		result.setDensity(Bitmap.DENSITY_NONE);
		//        	result.setDensity(320);
	} catch (FileNotFoundException e) {
		Log.d(TAG, ": Error opening image file - FileNotFoundException: " + e);
	} 
	return result;
}

// expect zip filename, extsd path and .xsbmpack
public static Integer getPackInfo(final String zipFile, final String path, final String infoFile) {
	//		Log.i(TAG, "Getting pack id '" + infoFile + "' from '" + path + zipFile + "'");
	Integer result = 0;
	try {
		FileInputStream fis = new FileInputStream(path + "/" + zipFile);
		ZipInputStream zis = new ZipInputStream(fis);
		ZipEntry ze = null;
		while ((ze = zis.getNextEntry()) != null) {
			if (ze.getName().equals(infoFile)) {
				result = 1;
			}
		}
		zis.close();
		fis.close();
	} catch (FileNotFoundException e) {
		Log.d(TAG, ": Extracting file: Error opening zip file - FileNotFoundException: " + e);
	} catch (IOException e) {
		Log.d(TAG, ": Extracting file: Error opening zip file - IOException: " + e);
	}
	return result;
}

public static HashMap<String, String> getPackDetail(final String zipFile, final String path, final String infoFile) {
	//		Log.i(TAG, "Getting pack id '" + infoFile + "' from '" + path + zipFile + "'");
	HashMap<String, String> result = new HashMap<String, String>();
	try {
		FileInputStream fis = new FileInputStream(path + zipFile);
		ZipInputStream zis = new ZipInputStream(fis);
		ZipEntry ze = null;
		while ((ze = zis.getNextEntry()) != null) {
			if (ze.getName().equals(infoFile)) {
				if (ze.getSize() == 0) {
					result.put("author", "N/A");
					result.put("note", "N/A");
					break;
				} else { 
					BufferedReader br = new BufferedReader(new InputStreamReader(zis));
					String line;
					while ((line = br.readLine()) != null) {
						if (line.startsWith("author")) {
							result.put("author", line.substring(7));
						} else if (line.startsWith("note")) {
							result.put("note", line.substring(5));
						} 
					}
					br.close();
				}
			}
		}
		zis.close();
		fis.close();
	} catch (FileNotFoundException e) {
		Log.d(TAG, ": Extracting file: Error opening zip file - FileNotFoundException: " + e);
	} catch (IOException e) {
		Log.d(TAG, ": Extracting file: Error opening zip file - IOException: " + e);
	}
	return result;
}

public static Multimap<String, String> getAppsList(final String File) {
	//		Log.i(TAG, "Getting app names from '" + path + zipFile + "'");
	Multimap<String, String> result = HashMultimap.create();
	try {
		FileInputStream fis = new FileInputStream(File);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		String line;
		while ((line = br.readLine()) != null) {
			//						Log.d(TAG, "our line is: " + line);
			String[] split = line.split("-");
			//						Log.d(TAG, "our split0: " + split[0] + " and split1: " + split[1]);
			result.put(split[0], split[1]);
		}
		fis.close();
	} catch (IOException e) {
		Log.d(TAG, "Error getting applist: " + e);
	}
	return result;
}

}
