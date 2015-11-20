package com.example.lucas.to_dolist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // declarations
    private ListView todoList;
    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // assign ListView, items for  ListView and adapter
        todoList = (ListView) findViewById(R.id.initialList);
        items = new ArrayList<>();
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        todoList.setAdapter(itemsAdapter);
    }

    // onClick event
    public void addItem(View view) {

        // gets input and converts
        EditText text = (EditText) findViewById(R.id.itemToAdd);
        String content = text.getText().toString();

        // persists on input
        if (content.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter an item", Toast.LENGTH_SHORT).show();
        }
        else {
            // adds input to list and empties EditText
            itemsAdapter.add(content);
            text.setText("");
        }

        // calls removal function
        removeItemFromList();
    }


    // removal function
    private void removeItemFromList() {

        // set and declare event for long click
        todoList.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {

                    @Override
                    // gets info on item to delete
                    public boolean onItemLongClick(AdapterView<?> adapter,
                                                   View item, int position, long id) {

                        // prompts deletion at position
                        promptDeletion(position);
                        return true;
                    }

                });
    }

    // creates pop-up for deletion confirmation
    private void promptDeletion(final int position){
        // build pop-up
        AlertDialog.Builder newPopUp = new AlertDialog.Builder(this);

        // set title and message
        newPopUp.setTitle("Delete");
        newPopUp.setMessage("Do you really want to delete this task?");

        // create positive click events
        newPopUp.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                // removes item at position and notifies of changed data
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();

            }
        });

        // create negative click events
        newPopUp.setNegativeButton("Keep", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // do nothing
            }
        });

        // creates pop-up and shows after calling function
        AlertDialog helpDialog = newPopUp.create();
        helpDialog.show();
    }
}




