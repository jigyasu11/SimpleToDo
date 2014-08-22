package com.aligarhwizard.simpletodo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class ToDoActivity extends ActionBarActivity implements OnItemClickListener, OnItemLongClickListener, OnTouchListener {
	List<String> items;
	ArrayAdapter<String> itemsAdapter;	
	ListView lvItems;
	public final static String TAG = "SIMPLE_TO_DO";
	public final static String EXTRA_MESSAGE = "com.aligarhwizard.simpletodo.item";
	static final int UPDATE_ITEM_REQUEST = 1;  // The request code
	private int listPos;	
    private GestureDetector gd;
	
    class MyGestureDetector extends SimpleOnGestureListener {
    	@Override
    	public boolean onDoubleTap(MotionEvent e) {
    		Intent intent = new Intent(getBaseContext(), EditItemActivity.class);
    		intent.putExtra(EXTRA_MESSAGE, itemsAdapter.getItem(listPos));
    		startActivityForResult(intent, UPDATE_ITEM_REQUEST);
    		return false;
    	}
    	
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
    }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo);
		items = new ArrayList<String>();
		lvItems = (ListView) findViewById(R.id.lvItems);
		items = readItems();
		itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
		lvItems.setAdapter(itemsAdapter);
		gd = new GestureDetector(this, new MyGestureDetector());
		lvItems.setOnItemClickListener(this);
		lvItems.setOnItemLongClickListener(this);
		lvItems.setOnTouchListener(this);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == UPDATE_ITEM_REQUEST && resultCode == RESULT_OK) {
			items.set(listPos, data.getStringExtra(EXTRA_MESSAGE));
			itemsAdapter.notifyDataSetChanged();
		}
	}
	
	/*
	 * This method is called when Add button is pressed
	 */
	public void addToDoItem(View v) {
		EditText item = (EditText) findViewById(R.id.etNewItem);
		items.add(item.getText().toString());
		itemsAdapter.notifyDataSetChanged();
		item.setText("");
		saveItems();
	}
	
	private List<String> readItems() {
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		List<String> storedTasks = new ArrayList<String>();
		try {
	    storedTasks = FileUtils.readLines(todoFile);
	    return storedTasks;
		} catch (Exception e) {
			Log.e(TAG, "Failed while reading file " + todoFile, e);
		}
		return storedTasks;
	}
	
	private void saveItems() {
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		try {
	    FileUtils.writeLines(todoFile, items);
		}  catch (Exception e) {
			Log.e(TAG, "Failed while writing file " + todoFile, e);
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long rowId) {
		this.listPos = position;
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position,
			long rowId) {
		// Delete item from the list
		items.remove(position);
		itemsAdapter.notifyDataSetChanged();
		saveItems();
		return true;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
	    gd.onTouchEvent(event);
	    return false;
	}
}
