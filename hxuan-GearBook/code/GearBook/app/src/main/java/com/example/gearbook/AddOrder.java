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
import java.util.*;

// AddOrder Dialog
// A dialog that is used for adding items
// It can be only accessed from the floating button
// Similar structure as EditOrder
public class AddOrder extends DialogFragment {
    EditText dateText;
    EditText makerText;
    EditText descriptionText;
    EditText priceText;
    EditText commentText;
    private OnFragmentInteractionListener listener;
    // Create interface for the Ok button on the Dialog
    public interface OnFragmentInteractionListener{
        void onOkPressed(Order newOrder);
    }
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof OnFragmentInteractionListener){
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    +" must implement OnFragmentInteractionListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState){
        // set up the view and layout for EditOrder dialog(Same as EditOrder dialog)
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
        // Build the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        // Setting up two buttons
        // Cancel button for return to the item list
        // Ok button for committing adding item
        builder.setView(view).setTitle("Add order")
                .setNegativeButton("Cancel",null)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Use Toast to show users if they give a invalid input value
                        // The button action won't be proceeded if there is any invalid input
                        String date = dateText.getText().toString();
                        if(date.equals("")){
                            Utility.showToast(getContext(), "invalid date");
                            return;
                        }
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
                        listener.onOkPressed(new Order(date, maker, description, price, comment));
                    }});
        return builder.create();
    }
    // A date pick function that open a datePicker dialog
    // result will be formatted by yyyy-MM-dd and set onto the EditText
    public void datePick(){
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                AddOrder.this.dateText.setText(year + "-" + (month+1)+ "-" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
}
