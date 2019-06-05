/* 
 * File: 		VerificationActivity.java
 * Created:		2013/05/03
 * 
 * copyright (c) 2013 DigitalPersona Inc.
 */

package com.example.authvertical;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

import com.digitalpersona.uareu.Engine;
import com.digitalpersona.uareu.Fid;
import com.digitalpersona.uareu.Fmd;
import com.digitalpersona.uareu.Reader;
import com.digitalpersona.uareu.UareUGlobal;
import com.digitalpersona.uareu.Reader.Priority;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;
import android.widget.Toast;

public class VerificationActivity extends Activity 
{

	private Button m_back;
	private String m_deviceName = "";

	private String m_enginError;

	private Reader m_reader = null;
	private int m_DPI = 0;
	private Bitmap m_bitmap = null;
	private ImageView m_imgView;
	private TextView m_selectedDevice;
	private TextView m_title;
	private boolean m_reset = false;

	private TextView m_text;
	private TextView m_text_conclusion;
	private String m_textString;
	private String m_text_conclusionString;
	private Engine m_engine = null;
	private Fmd m_fmd = null;
	private int m_score = -1;
	private boolean m_first = true;
	private boolean m_resultAvailableToDisplay = false;
	private Reader.CaptureResult cap_result = null;

	private void initializeActivity()
	{
		m_title = (TextView) findViewById(R.id.title);
		m_title.setText("Verification");

		m_enginError = "";

		m_selectedDevice = (TextView) findViewById(R.id.selected_device);
		m_deviceName = getIntent().getExtras().getString("device_name");

		m_selectedDevice.setText("Device: " + m_deviceName);

		m_bitmap = Globals.GetLastBitmap();
		if (m_bitmap == null) m_bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.black);
		m_imgView = (ImageView) findViewById(R.id.bitmap_image);
		m_imgView.setImageBitmap(m_bitmap);

		m_back = (Button) findViewById(R.id.back);
		m_back.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				onBackPressed();
			}
		});

		m_text = (TextView) findViewById(R.id.text);
		m_text_conclusion = (TextView) findViewById(R.id.text_conclusion);
		UpdateGUI();
	
		Globals.DefaultImageProcessing = Reader.ImageProcessing.IMG_PROC_DEFAULT;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_engine);
		m_textString = "Place any finger on the reader";

		initializeActivity();

		// initiliaze dp sdk
		try 
		{
			Context applContext = getApplicationContext();
			m_reader = Globals.getInstance().getReader(m_deviceName, applContext);
			m_reader.Open(Priority.EXCLUSIVE);
			m_DPI = Globals.GetFirstDPI(m_reader);
			m_engine = UareUGlobal.GetEngine();
		} 
		catch (Exception e)
		{
			Log.w("UareUSampleJava", "error during init of reader");
			m_deviceName = "";
			onBackPressed();
			return;
		}

		// loop capture on a separate thread to avoid freezing the UI
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				m_reset = false;
				while (!m_reset)
				{
					try 
					{
						cap_result = m_reader.Capture(Fid.Format.ANSI_381_2004, Globals.DefaultImageProcessing, m_DPI, -1);
					}
					catch (Exception e) 
					{
						if(!m_reset)
						{
							Log.w("UareUSampleJava", "error during capture: " + e.toString());
							m_deviceName = "";
							onBackPressed();
						}
					}

					m_resultAvailableToDisplay = false;

					// an error occurred
					if (cap_result == null || cap_result.image == null) continue;

					try 
					{
						m_enginError="";

						// save bitmap image locally
						m_bitmap = Globals.GetBitmapFromRaw(cap_result.image.getViews()[0].getImageData(), cap_result.image.getViews()[0].getWidth(), cap_result.image.getViews()[0].getHeight());

						if (m_fmd == null)
						{
							m_fmd = m_engine.CreateFmd(cap_result.image, Fmd.Format.ANSI_378_2004);
						}
						else
						{
							m_score = m_engine.Compare(m_fmd, 0, m_engine.CreateFmd(cap_result.image, Fmd.Format.ANSI_378_2004), 0);
							m_fmd = null;
							m_resultAvailableToDisplay = true;
						}
					}
					catch (Exception e)
					{
						m_enginError = e.toString();
						Log.w("UareUSampleJava", "Engine error: " + e.toString());
					}
					
//					m_text_conclusionString = Globals.QualityToString(cap_result);
//					if(!m_enginError.isEmpty())
//					{
//						m_text_conclusionString = "Engine: " + m_enginError;
//					}
//					else if (m_fmd == null)
//					{
//						if ((!m_first) && (m_resultAvailableToDisplay))
//						{
//							DecimalFormat formatting = new DecimalFormat("##.######");
//							m_text_conclusionString = "Dissimilarity Score: " + String.valueOf(m_score)+ ", False match rate: " + Double.valueOf(formatting.format((double)m_score/0x7FFFFFFF)) + " (" + (m_score < (0x7FFFFFFF/100000) ? "match" : "no match") + ")";
//						}
//
//						m_textString = "Place any finger on the reader";
//					}
//					else
//					{
//						m_first = false;
//						m_textString = "Place the same or a different finger on the reader";
//					}

					runOnUiThread(new Runnable()
					{
					    @Override public void run() 
					    {
					    	UpdateGUI();
//                            String converted = converttoBase64(m_bitmap);

//                          Base64 codec = new Base64();

//                            String converted1 = Base64.encodeToString(cap_result.image.getViews()[0].getImageData() , Base64.NO_WRAP);
//                            String converted2 = Base64.encodeToString(cap_result.image.getViews()[0].getData() , Base64.NO_WRAP);
                            String converted3 = Base64.encodeToString(cap_result.image.getData() , Base64.NO_WRAP);

//                            Toast.makeText(VerificationActivity.this, "[] byte = " + cap_result.image.getData().length + " base64 = " + converted.length(), Toast.LENGTH_LONG).show();
//                            converttoBitmap(converted1);
//                            writeToFile(converted1, "11");
//                            writeToFile(converted2, "22");
                            writeToFile(converted3, "33");
					    }
					});
				}
			}
		}).start();
	}

	public String converttoBase64(Bitmap bitmap) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
		return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
	}

    public Bitmap converttoBitmap(String base64Str) throws IllegalArgumentException {
        byte[] decodedBytes = Base64.decode(
                base64Str.substring(base64Str.indexOf(",") + 1),
                Base64.DEFAULT        );
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

	public void UpdateGUI()
	{
		m_imgView.setImageBitmap(m_bitmap);
		m_imgView.invalidate();
		m_text_conclusion.setText(m_text_conclusionString);
		m_text.setText(m_textString);
	}

	
	@Override
	public void onBackPressed()
	{
		try
		{
			m_reset = true;
			try { m_reader.CancelCapture(); } catch (Exception e) {}
			m_reader.Close();

		} 
		catch (Exception e)
		{
			Log.w("UareUSampleJava", "error during reader shutdown");
		} 

		Intent i = new Intent();
		i.putExtra("device_name", m_deviceName);
		setResult(Activity.RESULT_OK, i);
		finish();
	}

	// called when orientation has changed to manually destroy and recreate activity
	@Override
	public void onConfigurationChanged(Configuration newConfig) 
	{
		super.onConfigurationChanged(newConfig);
		setContentView(R.layout.activity_engine);
		initializeActivity();
	}

    public void onAPIResult(String apiResult) {
        Toast.makeText(VerificationActivity.this, apiResult, Toast.LENGTH_LONG).show();
    }

    public void writeToFile(String data, String file_name)
    {
        // Get the directory for the user's public pictures directory.
        final File path =
                Environment.getExternalStoragePublicDirectory
                        (
                                //Environment.DIRECTORY_PICTURES
                                Environment.DIRECTORY_DCIM + "/FPReader/"
                        );

        // Make sure the path directory exists.
        if(!path.exists())
        {
            // Make it, if it doesn't exit
            path.mkdirs();
        }


        Date currentTime = Calendar.getInstance().getTime();
        final File file = new File(path, file_name + "fp" + currentTime+".txt");

        // Save your stream, don't forget to flush() it before closing it.

        try
        {
            file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(file);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.write(data);
            Toast.makeText(VerificationActivity.this, "Data wrote to file", Toast.LENGTH_LONG).show();
            myOutWriter.close();

            fOut.flush();
            fOut.close();
        }
        catch (IOException e)
        {
            Toast.makeText(VerificationActivity.this, "File write failed", Toast.LENGTH_LONG).show();
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}

