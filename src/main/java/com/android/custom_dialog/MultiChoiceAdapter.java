package com.android.custom_dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class MultiChoiceAdapter extends RecyclerView.Adapter<MultiChoiceAdapter.OrderHolder> {

    Context context;
    private List<MultiChoiceModel> multiChoiceModelList;
    OnSelectItemInterface onSelectItemInterface;

    public MultiChoiceAdapter(Context context, List<MultiChoiceModel> multiChoiceModelList, OnSelectItemInterface onSelectItemInterface) {
        this.context = context;
        this.multiChoiceModelList = multiChoiceModelList;
        this.onSelectItemInterface = onSelectItemInterface;
    }

    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_multi_choice, parent, false);
        return new OrderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderHolder holder, final int position) {
        MultiChoiceModel multiChoiceModel = multiChoiceModelList.get(holder.getAdapterPosition());
        holder.txvItem.setText(multiChoiceModel.getText());
        holder.txvItem.setTextColor(ContextCompat.getColor(context,multiChoiceModel.getColor()));

        holder.txvItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Selected Item: "+holder.getAdapterPosition());
                onSelectItemInterface.onSelect(holder.getAdapterPosition());
            }
        });
    }


    @Override
    public int getItemCount() {
        return multiChoiceModelList.size();
    }


    public class OrderHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private Button txvItem;


        OrderHolder(View itemView) {
            super(itemView);
            txvItem = itemView.findViewById(R.id.txv_item);


        }

        @Override
        public void onClick(View view) {

        }
    }

}
