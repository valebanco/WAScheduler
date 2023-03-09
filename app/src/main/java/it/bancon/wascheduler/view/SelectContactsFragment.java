package it.bancon.wascheduler.view;

import android.accounts.OnAccountsUpdateListener;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import it.bancon.wascheduler.R;
import it.bancon.wascheduler.activity.MainAppActivity;
import it.bancon.wascheduler.activity.NewScheduleActivity;
import it.bancon.wascheduler.configuration.AppContractClass;
import it.bancon.wascheduler.model.ContactLoader;
import it.bancon.wascheduler.model.ContactModel;

public class SelectContactsFragment extends DialogFragment implements AdapterListViewContacts.OnContactListEventListener{
    private Button buttonSave;
    private Context context;
    private Activity activity;
    private AutoCompleteTextView editTextPhone;
    private ArrayList<ContactModel> contacts;
    private ListView listViewContacts;
    private AdapterListViewContacts adapterListViewContacts;
    private AdapterListViewContacts.OnContactListEventListener listener = this;
    private onUpdateCountSelectedContactsListener onUpdateCountSelectedContactsListener;

    public SelectContactsFragment(Context context, Activity activity, ArrayList<ContactModel> contacts,onUpdateCountSelectedContactsListener onUpdateCountSelectedContactsListener ){
        this.context = context;
        this.activity = activity;
        this.contacts = contacts;
        this.onUpdateCountSelectedContactsListener = onUpdateCountSelectedContactsListener;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_select_contacts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        populateAutocomplete();
        populateListContacts();
        getDialog().setCanceledOnTouchOutside(false);
        buttonSave = getDialog().findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    private void populateListContacts() {
        listViewContacts = getDialog().findViewById(R.id.listViewContacts);
        adapterListViewContacts = new AdapterListViewContacts(context,R.layout.contact_preview_item,contacts,this);
        listViewContacts.setAdapter(adapterListViewContacts);
    }

    public void populateAutocomplete(){
        ContactLoader contactLoader = new ContactLoader(activity,context);
        contactLoader.loadContactList();
        String [] namesToAdapter = contactLoader.getContactNames();

        editTextPhone = getDialog().findViewById(R.id.editTextPhone);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,namesToAdapter);
        editTextPhone.setAdapter(adapter);
        editTextPhone.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                editTextPhone.setText(getResources().getString(R.string.empty_string));
                editTextPhone.setHint(getResources().getString(R.string.search_for_other_contacts_hint));

                if(!isAlreadyAddedContanctToListView(contactLoader,adapterView,i)){
                    addContactToListView(contactLoader,adapterView,i);
                } else {
                    Toast toast= Toast. makeText(context,activity.getResources().getString(R.string.contact_is_added),Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }
    private boolean isAlreadyAddedContanctToListView(ContactLoader contactLoader, AdapterView<?> adapterView, int i){
        return contacts.contains(contactLoader.getContactFromName((String) adapterView.getItemAtPosition(i)));
    }

    private void addContactToListView(ContactLoader contactLoader, AdapterView<?> adapterView,int i) {
        contacts.add(contactLoader.getContactFromName((String) adapterView.getItemAtPosition(i)));
        adapterListViewContacts.notifyDataSetChanged();
    }

    @Override
    public void onRemoveItemContact(ContactModel contactToRemove) {
        System.out.println(contactToRemove.getName());
        this.contacts.remove(contactToRemove);
        populateListContacts();

    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        onUpdateCountSelectedContactsListener.onUpdateCountContactList();
        super.onDismiss(dialog);
    }

    public interface onUpdateCountSelectedContactsListener{
        void onUpdateCountContactList();
    }
}