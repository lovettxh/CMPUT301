package com.example.gearbook;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity implements AddOrder.OnFragmentInteractionListener, EditOrder.OnFragmentInteractionListener1, ViewOrder.OnFragmentInteractionListener2{
    // initiate the ListView, ArrayAdapter and ArrayList to store the order data
    ListView orderList;
    ArrayAdapter<Order> orderAdapter;
    ArrayList<Order> orderData;
    public static Context context;
    // initiate a TextView to display total price
    private TextView text_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // setup views
        orderList = findViewById(R.id.order_list);
        text_view = findViewById(R.id.total_price);
        context = MainActivity.this;
        text_view.setText("0.00 $");
        // Set a click listener for the ListView
        orderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            // Clicking an item in the ListView will view the full detail of it
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               new ViewOrder(orderData.get(position), position).show(getSupportFragmentManager(), "VIEW_ORDER");
            }
        });

        orderData = new ArrayList<>();
        // Use the custom ArrayAdapter for the Order class data
        orderAdapter = new CustomList(this, orderData);
        orderList.setAdapter(orderAdapter);
        // setup view for the floating button
        // Clicking it will pop the dialog for adding items
        final FloatingActionButton addOrderButton = findViewById(R.id.add_order_button);
        addOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AddOrder().show(getSupportFragmentManager(),"ADD_ORDER");
            }
        });

    }
    // Ok button in Dialog AddOrder
    // Press Ok will finish adding item
    @Override
    public void onOkPressed(Order newOrder){
        // add the Order item and then calculate and display the total price
        orderAdapter.add(newOrder);
        DecimalFormat f = new DecimalFormat("##0.00");
        text_view.setText(f.format(getTotalPrice()) + " $");

    }
    // Edit button in Dialog EditOrder
    // Press Edit will finish editing item
    @Override
    public void onEditPressed(Order oldOrder, int p, Order newOrder){
        // remove the old item and insert the edited item at the original position
        // calculate new total price
        orderAdapter.remove(oldOrder);
        orderAdapter.insert(newOrder, p);
        DecimalFormat f = new DecimalFormat("##0.00");
        text_view.setText(f.format(getTotalPrice()) + " $");
    }
    // Delete button in Dialog EditOrder
    // Press Delete will delete the item
    @Override
    public void onDeletePressed(Order oldOrder){
        // remove the item from the list
        // calculate new total price
        orderAdapter.remove(oldOrder);
        DecimalFormat f = new DecimalFormat("##0.00");
        text_view.setText(f.format(getTotalPrice()) + " $");
    }
    // Edit button in Dialog ViewOrder
    // Press Edit will start editing the item
    @Override
    public void onViewEditPressed(Order order, int position){
        // open the EditOrder dialog
        new EditOrder(orderData.get(position), position).show(getSupportFragmentManager(),"EDIT_ORDER");
    }

    // A function that can calculate the total price of the listed items
    public double getTotalPrice(){
        double t = 0;
        for(int i = 0; i < orderData.size(); i++){
            t += Double.parseDouble(orderData.get(i).getPrice());
        }
        return t;
    }


}