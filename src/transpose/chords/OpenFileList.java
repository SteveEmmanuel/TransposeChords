package transpose.chords;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class OpenFileList extends ListActivity {

	private DatabaseHelper db = null;
	private Cursor constantsCursor = null;
	private static final int DELETE_ID = Menu.FIRST + 3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.e("transpose", "OPENFILELIST:oncreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_open_file_list);

		db = new DatabaseHelper(this);
		constantsCursor = db.getReadableDatabase().rawQuery(
				"SELECT _ID,filename " + "FROM TransposeChordFileList", null);

		ListAdapter adapter = new SimpleCursorAdapter(this, R.layout.row,
				constantsCursor, new String[] { DatabaseHelper.FILENAME },
				new int[] { R.id.fileName }, 0);

		setListAdapter(adapter);
		registerForContextMenu(getListView());
		Log.e("transpose", "OPENFILELIST:oncreate over");
		listView();
	}



	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		// open File

		String fileName = (String) ((Cursor) l.getItemAtPosition(position))
				.getString(1);

		Log.e("transpose", "OPENFILELIST:ListItem:" + fileName);

		Intent returnIntent = new Intent();
		returnIntent.putExtra("filename", fileName);
		setResult(RESULT_OK, returnIntent);
		finish();

	}
	
void listView(){
	db = new DatabaseHelper(this);
	constantsCursor = db.getReadableDatabase().rawQuery(
			"SELECT _ID,filename " + "FROM TransposeChordFileList", null);

	ListAdapter adapter = new SimpleCursorAdapter(this, R.layout.row,
			constantsCursor, new String[] { DatabaseHelper.FILENAME },
			new int[] { R.id.fileName }, 0);

	setListAdapter(adapter);
	registerForContextMenu(getListView());
	Log.e("transpose", "MainActivity:oncreate over");
}

private void delete(final long rowId) {
	if (rowId > 0) {
		new AlertDialog.Builder(this)
				.setTitle("Delete")
				.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								processDelete(rowId);
							}
						})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								// ignore, just dismiss
							}
						}).show();
	}
}

private void processDelete(long rowId) {
	String[] args = { String.valueOf(rowId) };

	db.getWritableDatabase().delete("TransposeChordFileList", "_ID=?", args);
	listView();
}

@Override
public void onCreateContextMenu(ContextMenu menu, View v,
		ContextMenu.ContextMenuInfo menuInfo) {
	menu.add(Menu.NONE, DELETE_ID, Menu.NONE, "Delete")
			.setAlphabeticShortcut('d');
}

@Override
public boolean onContextItemSelected(MenuItem item) {
	switch (item.getItemId()) {
	case DELETE_ID:
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();

		delete(info.id);
		return (true);
	}

	return (super.onOptionsItemSelected(item));
}


	@Override
	protected void onResume() {
		super.onResume();
		listView();
	}
	@Override
	protected void onRestart() {
		super.onRestart();
		listView();
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		constantsCursor.close();
		db.close();
		finish();
	}
}
