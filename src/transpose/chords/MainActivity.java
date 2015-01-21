package transpose.chords;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private DatabaseHelper db = null;
	private static final String DATABASE_NAME="TransposeChordFileList";
	static final String FILENAME="filename";
	String openFile;
	EditText chordText;
	Boolean edit=false;
	String fileN = "";
	Button plusBtn, minusBtn,openBtn,saveBtn,newBtn;
	String[] chordTransposeInput = { "C", "C#", "D", "D#", "E", "F", "F#", "G",
			"G#", "A", "A#", "B" };
	String[] chordTransposeMatchPlus = { "C#", "D", "D#", "E", "F", "F#", "G",
			"G#", "A", "A#", "B", "C" };
	String[] chordTransposeMatchMinus = { "C", "C#", "D", "D#", "E", "F", "F#",
			"G", "G#", "A", "A#", "B" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.e("transpose", "onCreate called");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		File folder = new File(Environment.getExternalStorageDirectory() + "/Transpose Chords");
		boolean success = true;
		if (!folder.exists()) {
		    success = folder.mkdir();
		}
		if(!success){
		System.out.println("ERROR!!Folder not created");
		Log.e("transpose", "ERROR!!Folder not created");
		}
		else{
			System.out.println("SUCCESS!!Folder created");
			Log.e("transpose", "SUCCESS!!Folder created");
		}
		
		
		
		chordText = (EditText) findViewById(R.id.chordText);
		plusBtn = (Button) findViewById(R.id.plusBtn);
		minusBtn = (Button) findViewById(R.id.minusBtn);
		openBtn = (Button) findViewById(R.id.openBtn);
		saveBtn = (Button) findViewById(R.id.saveBtn);
		newBtn = (Button) findViewById(R.id.newBtn);

		plusBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				transpose(0);
				// for plus
			}
		});
		
		minusBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				transpose(1);
				// for minus
			}
		});
		
		openBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//open listview
				Intent intent = new Intent(MainActivity.this, OpenFileList.class);
				Log.e("transpose", "MAINACTIVITY:openBTn:start");
			    startActivityForResult(intent, 1);
			    
			}
		});
	
	
	saveBtn.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
			
			// open dialogueBox
			saveDialog();
		}
	});
	
	newBtn.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
			edit = false;
			chordText.setText("");
		}
	});
	
	}

	public void transpose(int mode) {
		String token = new String();

		token = chordText.getText().toString();
		String[] intermediate = token.split("(?<=\\S)(?=\\s)|(?<=\\s)(?=\\S)");
		
		chordText.setText("");

		int l = intermediate.length;

		for (int i = 0; i < l; i++) {
			for (int j = 0; j < 12; j++) {
				if (intermediate[i].equals(chordTransposeInput[j])
						|| intermediate[i].equals(chordTransposeInput[j] + "m")
						|| intermediate[i].equals(chordTransposeInput[j]
								+ "sus2")
						|| intermediate[i].equals(chordTransposeInput[j]
								+ "sus4")
						|| intermediate[i].equals(chordTransposeInput[j]
								+ "dd2")
						|| intermediate[i].equals(chordTransposeInput[j]
								+ "dd9")
						|| intermediate[i].equals(chordTransposeInput[j]
								+ "dd4")
						|| intermediate[i].equals(chordTransposeInput[j]
								+ "mdd2")
						|| intermediate[i].equals(chordTransposeInput[j]
								+ "mdd9")
						|| intermediate[i].equals(chordTransposeInput[j]
								+ "mdd4")
						|| intermediate[i].equals(chordTransposeInput[j]
								+ "dd2dd4")
						|| intermediate[i].equals(chordTransposeInput[j]
								+ "mdd2dd4")
						|| intermediate[i]
								.equals(chordTransposeInput[j] + "ug")
						|| intermediate[i].equals(chordTransposeInput[j]
								+ "dim")
						|| intermediate[i].equals(chordTransposeInput[j]
								+ "dim7")
						|| intermediate[i].equals(chordTransposeInput[j] + "5")
						|| intermediate[i].equals(chordTransposeInput[j] + "6")
						|| intermediate[i]
								.equals(chordTransposeInput[j] + "m6")
						|| intermediate[i].equals(chordTransposeInput[j]
								+ "6/9")
						|| intermediate[i].equals(chordTransposeInput[j]
								+ "m6/9")
						|| intermediate[i].equals(chordTransposeInput[j]
								+ "6/7")
						|| intermediate[i].equals(chordTransposeInput[j]
								+ "m6/7")
						|| intermediate[i].equals(chordTransposeInput[j]
								+ "mj6/7")
						|| intermediate[i].equals(chordTransposeInput[j] + "7")
						|| intermediate[i]
								.equals(chordTransposeInput[j] + "m7")
						|| intermediate[i].equals(chordTransposeInput[j]
								+ "mj7")
						|| intermediate[i].equals(chordTransposeInput[j]
								+ "7sus4")
						|| intermediate[i].equals(chordTransposeInput[j]
								+ "7sus2")
						|| intermediate[i].equals(chordTransposeInput[j]
								+ "7dd4")
						|| intermediate[i].equals(chordTransposeInput[j]
								+ "m7dd4")
						|| intermediate[i].equals(chordTransposeInput[j] + "9")
						|| intermediate[i]
								.equals(chordTransposeInput[j] + "m9")
						|| intermediate[i].equals(chordTransposeInput[j]
								+ "mj9")
						|| intermediate[i].equals(chordTransposeInput[j]
								+ "9sus4")
						|| intermediate[i]
								.equals(chordTransposeInput[j] + "11")
						|| intermediate[i].equals(chordTransposeInput[j]
								+ "m11")
						|| intermediate[i].equals(chordTransposeInput[j]
								+ "mj11")
						|| intermediate[i]
								.equals(chordTransposeInput[j] + "13")
						|| intermediate[i].equals(chordTransposeInput[j]
								+ "m13")
						|| intermediate[i].equals(chordTransposeInput[j]
								+ "mj13")
						|| intermediate[i].equals(chordTransposeInput[j]
								+ "13sus4")
						|| intermediate[i].equals(chordTransposeInput[j]
								+ "mmj7")
						|| intermediate[i].equals(chordTransposeInput[j]
								+ "mmj9")
						|| intermediate[i].equals(chordTransposeInput[j]
								+ "7#9")
						|| intermediate[i].equals(chordTransposeInput[j]
								+ "7b9")
						|| intermediate[i].equals(chordTransposeInput[j]
								+ "7#5")
						|| intermediate[i].equals(chordTransposeInput[j]
								+ "7b5")
						|| intermediate[i].equals(chordTransposeInput[j]
								+ "m7#5")
						|| intermediate[i].equals(chordTransposeInput[j]
								+ "m7b5")
						|| intermediate[i].equals(chordTransposeInput[j]
								+ "mj7#5")
						|| intermediate[i].equals(chordTransposeInput[j]
								+ "mj7b5")
						|| intermediate[i].equals(chordTransposeInput[j]
								+ "9#5")
						|| intermediate[i].equals(chordTransposeInput[j]
								+ "9b5")
						|| intermediate[i]
								.equals(chordTransposeInput[j] + "/#")
						|| intermediate[i]
								.equals(chordTransposeInput[j] + "/B")
						|| intermediate[i]
								.equals(chordTransposeInput[j] + "/C")
						|| intermediate[i].equals(chordTransposeInput[j]
								+ "/C#")
						|| intermediate[i]
								.equals(chordTransposeInput[j] + "/D")
						|| intermediate[i].equals(chordTransposeInput[j]
								+ "/D#")
						|| intermediate[i]
								.equals(chordTransposeInput[j] + "/E")
						|| intermediate[i]
								.equals(chordTransposeInput[j] + "/F")
						|| intermediate[i].equals(chordTransposeInput[j]
								+ "/F#")
						|| intermediate[i]
								.equals(chordTransposeInput[j] + "/G")
						|| intermediate[i].equals(chordTransposeInput[j]
								+ "/G#")
						|| intermediate[i].equals(chordTransposeInput[j]
								+ "m/#")
						|| intermediate[i].equals(chordTransposeInput[j]
								+ "m/B")
						|| intermediate[i].equals(chordTransposeInput[j]
								+ "m/C")
						|| intermediate[i].equals(chordTransposeInput[j]
								+ "m/C#")
						|| intermediate[i].equals(chordTransposeInput[j]
								+ "m/D")
						|| intermediate[i].equals(chordTransposeInput[j]
								+ "m/D#")
						|| intermediate[i].equals(chordTransposeInput[j]
								+ "m/E")
						|| intermediate[i].equals(chordTransposeInput[j]
								+ "m/F")
						|| intermediate[i].equals(chordTransposeInput[j]
								+ "m/F#")
						|| intermediate[i].equals(chordTransposeInput[j]
								+ "m/G")
						|| intermediate[i].equals(chordTransposeInput[j]
								+ "m/G#")) {
					System.out.println("0");
					if (mode == 0) {
						System.out.println("4");
						if (j == 12) {
							if (intermediate[i].length() > 1) {
								if (!intermediate[i].substring(1, 2)
										.equals("#")) {
									System.out.println("1");
									intermediate[i] = chordTransposeMatchPlus[0]
											+ intermediate[i].substring(1);
								} else {

									intermediate[i] = chordTransposeMatchPlus[0]
											+ intermediate[i].substring(2);
								}
							} else {
								System.out.println("6");
								intermediate[i] = chordTransposeMatchPlus[0];
							}
							break;
						} else {
							if (intermediate[i].length() > 1) {
								if (!intermediate[i].substring(1, 2)
										.equals("#")) {
									System.out.println("2");
									intermediate[i] = chordTransposeMatchPlus[j]
											+ intermediate[i].substring(1);
								} else {
									System.out.println("5");
									intermediate[i] = chordTransposeMatchPlus[j]
											+ intermediate[i].substring(2);
								}
							} else {
								System.out.println("6");
								intermediate[i] = chordTransposeMatchPlus[j];
							}
							break;
						}

					} else if (mode == 1) {
						if (j == 0) {
							if (intermediate[i].length() > 1) {
								if (!intermediate[i].substring(1, 2)
										.equals("#")) {
									System.out.println("-1");
									intermediate[i] = chordTransposeMatchMinus[11]
											+ intermediate[i].substring(1);
								} else {

									intermediate[i] = chordTransposeMatchMinus[11]
											+ intermediate[i].substring(2);
								}
							} else {
								System.out.println("-6");
								intermediate[i] = chordTransposeMatchMinus[11];
							}
							break;
						} else {
							if (intermediate[i].length() > 1) {
								if (!intermediate[i].substring(1, 2)
										.equals("#")) {
									System.out.println("-2");
									intermediate[i] = chordTransposeMatchMinus[j - 1]
											+ intermediate[i].substring(1);
								} else {
									System.out.println("-5");
									intermediate[i] = chordTransposeMatchMinus[j - 1]
											+ intermediate[i].substring(2);
								}
							} else {
								System.out.println("-6");
								intermediate[i] = chordTransposeMatchMinus[j - 1];
							}
							break;
						}
					}
					break;
				}

			}
		}

		for (int i = 0; i < l; i++) {
			chordText.append(intermediate[i]);
			// System.out.println("int"+intermediate[i]);
		}

	}
	
	
	
	void saveDialog(){
		if(!edit){
		final EditText fileName= new EditText(this);
		fileName.setHint("Enter Filename:");
		
		new AlertDialog.Builder(this)
		.setTitle("Save")
		.setView(fileName)
		.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int whichButton) {
						fileN=fileName.getText().toString();
						String fName = "/Transpose Chords/"+fileN+".txt";
						Log.e("transpose", "OPENFILELIST:FileName"+fName.toString());
						File file = new File(Environment.getExternalStorageDirectory() + fName.toString());
						
						
							 try {
								 FileWriter fos = new FileWriter(file,false);
								fos.write(chordText.getText().toString());
								fos.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								Log.e("transpose", "OPENFILELIST:FileOutputStream:"+e.toString());
							}
							 
							 
							 db = new DatabaseHelper(MainActivity.this);
								
						    	SQLiteDatabase dB = db.getWritableDatabase();

							 // Create a new map of values, where column names are the keys
							 ContentValues cv = new ContentValues();
							 cv.put(FILENAME, fileN);
							 try {
							 dB.insert(DATABASE_NAME, FILENAME, cv);
					} catch (Exception e) {
						Log.e("Mainactivity", "DAtabase ERROR:"+e.toString());
						Toast.makeText(getApplicationContext(), "Filename  "+fileN+"exists.", Toast.LENGTH_SHORT).show();
					}
							 
					}
				})
		.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int whichButton) {
						// ignore, just dismiss
					}
				}).show();
		
		Toast.makeText(this, "File saved to "+fileN, Toast.LENGTH_SHORT).show();
		}
		else{
			String fName = Environment.getExternalStorageDirectory()+"/Transpose Chords/"+openFile+".txt";
			
		    File file = new File(fName);
				 try {
					 FileWriter fos = new FileWriter(file,false);
					fos.write(chordText.getText().toString());
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					Log.e("transpose", "OPENFILELIST||abc:FileOutputStream:"+e.toString());
				}
				 
				 Toast.makeText(this, "File saved to "+openFile, Toast.LENGTH_SHORT).show();
		}
		edit = false;
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		 if (requestCode == 1) {
		        if(resultCode == RESULT_OK){
		        	edit=true;
		        	openFile=data.getStringExtra("filename");
		            Log.e("transpose", "MAINACTIVITY:openBTn:switch1"+openFile);
				    String fName = Environment.getExternalStorageDirectory()+"/Transpose Chords/"+openFile+".txt";
				    File file = new File(fName);
				    
					Log.e("transpose", "MAINACTIVITY:new");
				    BufferedReader reader = null;
					try {
						reader = new BufferedReader(new FileReader(file));
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				  
				    
				    StringBuilder textBuilder = new StringBuilder();
		            String line;
		            try {
						while((line = reader.readLine()) != null) {
						    textBuilder.append(line);
						    textBuilder.append("\n");

						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		            
					
					chordText.setText(textBuilder);
		        }
		        if (resultCode == RESULT_CANCELED) {
		        	Log.e("transpose", "Intent ERrOR"); 
		        	Toast.makeText(MainActivity.this, "No File Selected", Toast.LENGTH_SHORT).show();//Write your code if there's no result
		        	
		        }
		    }
		 
		 
		 
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	

}
