package com.aligarhwizard.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
		
		Intent intent = getIntent();
		String text = intent.getStringExtra(ToDoActivity.EXTRA_MESSAGE);
		EditText editText = (EditText) findViewById(R.id.editItem);	
		editText.setText(text);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_item, menu);
		return true;
	}
	
	public void updateItem(View v) {
		// set the intent
		Intent returnIntent = new Intent();
		EditText item = (EditText) findViewById(R.id.editItem);
		returnIntent.putExtra(ToDoActivity.EXTRA_MESSAGE, item.getText().toString());
		setResult(RESULT_OK, returnIntent);
		Log.i(ToDoActivity.TAG, "Updting item here " + item.getText().toString());
		finish();
	}
}
