package com.example.gearbook;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

// EditOrder Dialog
// A dialog that is used for editing items
// It can be only accessed from View page(dialog)
// Similar structure as AddOrder
public class EditOrder extends DialogFragment {
    // initiate EditText
    EditText dateText;
    EditText makerText;
    EditText descriptionText;
    EditText priceText;
    EditText commentText;
    private OnFragmentInteractionListener1 listener;
    private Order order;
    private int position = 0;
    // Create interface for the Edit button and Delete button on the Dialog
    public interface OnFragmentInteractionListener1{
        void onEditPressed(Order oldOrder, int position, Order newOrder);
        void onDeletePressed(Order oldOrder);
    }
    // Constructor that get the Order object(item) and its position in the list
    public EditOrder(Order o, int p){
        this.order = o;
        this.position = p;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof EditOrder.OnFragmentInteractionListener1){
            listener = (EditOrder.OnFragmentInteractionListener1) context;
        } else {
            throw new RuntimeException(context.toString()
                    +" must implement OnFragmentInteractionListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState){
        // set up the view and layout for EditOrder dialog(Same as AddOrder dialog)
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.add_order_layout,null);
        dateText = view.findViewById(R.id.date_editText);
        makerText = view.findViewById(R.id.maker_editText);
        descriptionText = view.findViewById(R.id.description_editText);
        priceText = view.findViewById(R.id.price_editText);
        commentText = view.findViewById(R.id.comment_editText);
        // set up a date selector for the dateText to ensure proper input
        dateText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    datePick();
                    return true;
                }
                return false;
            }
        });
        // Get the all the existed information from item
        dateText.setText(order.getDate());
        makerText.setText(order.getMaker());
        priceText.setText(order.getPrice());
        descriptionText.setText(order.getDescription());
        commentText.setText(order.getComment());
        // Build the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        // Setting up three buttons
        // Cancel button to return to the order list
        // Edit button to commit the change
        // Delete button to remove the item
        builder.setView(view)
                .setTitle("Edit order")
                .setNegativeButton("Cancel",null)
                .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    // Use Toast to show users if they give a invalid input value
                    // The button action won't be proceeded if there is any invalid input
                    public void onClick(DialogInterface dialog, int which) {
                        String date = dateText.getText().toString();
                        String maker = makerText.getText().toString();
                        if(maker.equals("") || maker.length() > 20){
                            Utility.showToast(getContext(), "invalid maker");
                            return;
                        }
                        String description = descriptionText.getText().toString();
                        if(description.equals("") || description.length() > 40){
                            Utility.showToast(getContext(), "invalid description");
                            return;
                        }
                        if(priceText.getText().toString().equals("")){
                            Utility.showToast(getContext(), "invalid price");
                            return;
                        }
                        double price = Double.parseDouble(priceText.getText().toString());
                        String comment = commentText.getText().toString();
                        if(comment.length() > 20){
                            Utility.showToast(getContext(), "invalid comment");
                            return;
                        }
                        listener.onEditPressed(order, position, new Order(date, maker, description, price, comment));
                    }
                })
                .setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onDeletePressed(order);
                    }
                });
        return builder.create();
    }
    // A date pick function that open a datePicker dialog
    // result will be formatted by yyyy-MM-dd and set onto the EditText
    public void datePick(){
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                EditOrder.this.dateText.setText(year + "-" + (month+1)+ "-" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
}
