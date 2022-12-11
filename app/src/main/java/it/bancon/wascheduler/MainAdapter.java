package it.bancon.wascheduler;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import it.bancon.wascheduler.model.ContactModel;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    Activity activity;
    ArrayList<ContactModel> contacts;

    public MainAdapter(Activity activity, ArrayList<ContactModel> contacts) {
        this.activity = activity;
        this.contacts = contacts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ContactModel model = contacts.get(position);
        holder.textViewName.setText(model.getName());
        holder.textViewNumber.setText(model.getNumber());
    }


    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textViewName, textViewNumber;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.TextNameRowItem);
            textViewNumber = itemView.findViewById(R.id.TextNumberRowItem);
        }
    }
}
