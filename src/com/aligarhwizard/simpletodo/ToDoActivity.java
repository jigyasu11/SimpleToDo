package com.aligarhwizard.simpletodo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class ToDoActivity extends ActionBarActivity {
	
	List<String> items;
	ArrayAdapter<String> itemsAdapter;
	ListView lvItems;
	public final static String TAG = "SIMPLE_TO_DO";
	public final static String EXTRA_MESSAGE = "com.aligarhwizard.simpletodo.item";
	static final int UPDATE_ITEM_REQUEST = 1;  // The request code
	private int listPos;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo);
		lvItems = (ListView) findViewById(R.id.lvItems);
		items = new ArrayList<String>();
		itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
		lvItems.setAdapter(itemsAdapter);
		items.add("first item");
		items.add("second item");
		setupListViewListener();
		readItems();
	}
	
	private void setupListViewListener() {
		lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View aView,
					int pos, long id) {
				Intent intent = new Intent(getBaseContext(), EditItemActivity.class);
				intent.putExtra(EXTRA_MESSAGE, items.get(pos));
				startActivityForResult(intent, UPDATE_ITEM_REQUEST);
				listPos = (int) id;
				return true;
			}

		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == UPDATE_ITEM_REQUEST && resultCode == RESULT_OK) {
			Log.i(TAG, "Updating data at pos " + listPos);
			items.set(listPos, data.getStringExtra(EXTRA_MESSAGE));
			itemsAdapter.notifyDataSetInvalidated();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.simple_to_do, menu);
		return true;
	}
	
	public void addToDoItem(View v) {
		EditText item = (EditText) findViewById(R.id.etNewItem);
		itemsAdapter.add(item.getText().toString());
		item.setText("");
	}
	
	private void readItems() {
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		try {
	    items = new ArrayList<String>(FileUtils.readLines(todoFile));
		} catch (Exception e) {
			Log.e(TAG, "Failed while reading file " + todoFile, e);
		}
	}
	
	private void saveItems() {
		File filesDir = getFilesDir();
		Log.i(TAG, "File dir is " + filesDir);
		File todoFile = new File(filesDir, "todo.txt");
		try {
	    FileUtils.writeLines(todoFile, items);
		}  catch (Exception e) {
			Log.e(TAG, "Failed while writing file " + todoFile, e);
		}
	}
}
