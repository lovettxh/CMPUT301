package com.example.gearbook;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

// ViewOrder Dialog
// A dialog that will pop up when user click an item in the ListView
// It contains full detailed information of an order item
public class ViewOrder extends DialogFragment {
    // initiate TextView
    TextView dateView;
    TextView makerView;
    TextView descriptionView;
    TextView priceView;
    TextView commentView;
    private OnFragmentInteractionListener2 listener;
    private Order order;
    private int position;
    // Create interface for the Edit button on the Dialog
    public interface OnFragmentInteractionListener2{
        void onViewEditPressed(Order order, int position);
    }
    // Constructor that get the Order object(item) and its position in the list
    public ViewOrder (Order o, int p){
        this.position = p;
        this.order = o;
    }
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof OnFragmentInteractionListener2){
            listener = (OnFragmentInteractionListener2) context;
        } else {
            throw new RuntimeException(context.toString()
                    +" must implement OnFragmentInteractionListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState){
        // set up the view and layout for ViewOrder dialog
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.view_order,null);
        dateView = view.findViewById(R.id.date_view);
        makerView = view.findViewById(R.id.maker_view);
        descriptionView = view.findViewById(R.id.description_view);
        priceView = view.findViewById(R.id.price_view);
        commentView = view.findViewById(R.id.comment_view);
        // Get the all the information from the order object
        dateView.setText(order.getDate());
        makerView.setText(order.getMaker());
        descriptionView.setText(order.getDescription());
        priceView.setText(order.getPrice() + " $");
        commentView.setText(order.getComment());
        // Build the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        // Set up two button
        // Cancel button to close the dialog and return to MainActivity
        // Edit button to open up another dialog EditOrder
        builder.setView(view)
                .setTitle("View Order")
                .setNegativeButton("Cancel",null)
                .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onViewEditPressed(order, position);
                    }
                });
        return builder.create();
    }

}
