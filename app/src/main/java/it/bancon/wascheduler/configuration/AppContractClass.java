package it.bancon.wascheduler.configuration;

public class AppContractClass {
   public static final String FILE_NAME = "schedules.txt";
   public static final String REGEX_ITEM_SCHEDULATION_DETAILS = ";";
   public static final String REGEX_ITEM_CONTACTS = "--";

   public static final String MESSAGE_RATIONALE_READ_CONTACTS = "Read Contact permission is necessary. If you do not enable this permission you will unable to use the app! Please,type yes or enable permission in settings section.";
   public static final int REQUEST_CODE_READ_CONTACTS = 100;

   public static final String MESSAGE_TOAST_NEED_READ_CONTACT_PERMISSION = "Enable the Read Contact permission!!";

   public static final String KEY_PARCELABLE_SCHEDULATION_DETAILS = "schedulation_details_selected";

}
