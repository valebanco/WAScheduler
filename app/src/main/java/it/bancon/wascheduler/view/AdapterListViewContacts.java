package it.bancon.wascheduler.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import it.bancon.wascheduler.R;
import it.bancon.wascheduler.model.ContactModel;
import it.bancon.wascheduler.model.SchedulationDetailsPreview;

class AdapterListViewContacts extends ArrayAdapter<ContactModel> {
   private Context mContext;
   private int mResource;
   private OnContactListEventListener onContactListEventListener;

   public AdapterListViewContacts(@NonNull Context context, int resource, @NonNull List<ContactModel> objects,OnContactListEventListener onContactListEventListener) {
      super(context, resource, objects);
      this.mContext = context;
      this.mResource = resource;
      this.onContactListEventListener = onContactListEventListener;
   }

   @NonNull
   @Override
   public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
      LayoutInflater layoutInflater = LayoutInflater.from(mContext);
      convertView = layoutInflater.inflate(mResource,parent,false);
      ImageButton buttonRemoveContactChoice = convertView.findViewById(R.id.buttonRemoveFromListContacts);
      buttonRemoveContactChoice.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            ContactModel contactToRemove = new ContactModel();
            contactToRemove.setName(getItem(position).getName());
            contactToRemove.setNumber(getItem(position).getNumber());
            onContactListEventListener.onRemoveItemContact(contactToRemove);
         }
      });
      TextView textViewNameContact = convertView.findViewById(R.id.textViewNameContactItem);
      String name = getItem(position).getName();
      textViewNameContact.setText(name);

      return convertView;
   }

   public interface OnContactListEventListener{
      void onRemoveItemContact(ContactModel contactModel);
   }


}
