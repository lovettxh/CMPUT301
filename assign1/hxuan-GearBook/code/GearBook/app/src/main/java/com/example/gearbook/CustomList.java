package com.example.gearbook;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;

// A custom ArrayAdapter that is designed for the Order object
public class CustomList extends ArrayAdapter<Order> {
    private ArrayList<Order> orders;
    private Context context;
    public CustomList(Context context, ArrayList<Order> orders){
        super(context,0, orders);
        this.orders = orders;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.content, parent,false);
        }
        Order order = orders.get(position);
        TextView date = view.findViewById(R.id.date_text);
        TextView price = view.findViewById(R.id.price_text);
        TextView description = view.findViewById(R.id.description_text);
        date.setText(order.getDate());
        price.setText(order.getPrice());
        description.setText(order.getDescription());
        return view;
    }

}
