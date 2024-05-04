package com.example.pizzadeliveryapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzadeliveryapp.R;
import com.example.pizzadeliveryapp.helper.ChangeNumberItemsListener;
import com.example.pizzadeliveryapp.helper.ManagementCart;
import com.example.pizzadeliveryapp.models.HomeVerModel;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>{

    private final ArrayList<HomeVerModel> cartModelList;
    private final Context context;
    private ManagementCart managementCart;
    ChangeNumberItemsListener changeNumberItemsListener;

    public CartAdapter(ArrayList<HomeVerModel> cartModelList, Context context, ChangeNumberItemsListener changeNumberItemsListener) {
        this.cartModelList = cartModelList;
        managementCart = new ManagementCart(context);
        this.context = context;
        this.changeNumberItemsListener = changeNumberItemsListener;
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.mycart_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        holder.fname.setText(String.valueOf(cartModelList.get(position).getFname()));
        holder.rating.setText(String.valueOf(cartModelList.get(position).getRating()));
        holder.price.setText("Rs. "+cartModelList.get(position).getPrice());
        holder.total.setText("Rs. "+cartModelList.get(position).getNumberInCart()*cartModelList.get(position).getPrice());
        holder.quantity.setText(""+cartModelList.get(position).getNumberInCart());


        holder.plus.setOnClickListener(view -> {
            managementCart.plusNumberItem(cartModelList, position, () -> {
                notifyDataSetChanged();
                changeNumberItemsListener.change();
            });
        });

        holder.minus.setOnClickListener(view -> {
            managementCart.minusNumberItem(cartModelList, position, () -> {
                notifyDataSetChanged();
                changeNumberItemsListener.change();
            });
        });

    }

    @Override
    public int getItemCount() {
        return cartModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageButton minus, plus;
        public TextView fname, price, rating, quantity, total;
        public Button btnCheckOut;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fname = itemView.findViewById(R.id.orderID);
            rating = itemView.findViewById(R.id.detailed_rating);
            price = itemView.findViewById(R.id.detailed_price);
            quantity = itemView.findViewById(R.id.quantity);
            total = itemView.findViewById(R.id.totalTxt);
            minus = itemView.findViewById(R.id.minus);
            plus = itemView.findViewById(R.id.plus);
            btnCheckOut = itemView.findViewById(R.id.btn_check_out);
        }
    }
}
