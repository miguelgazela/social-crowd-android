package pt.up.fe.socialcrowd.helpers;

import pt.up.fe.socialcrowd.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

public class RateDialogFragment extends DialogFragment {
	
	public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }
	
	private NoticeDialogListener mListener;
    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
	
    	AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    	LayoutInflater inflater = getActivity().getLayoutInflater();
      
        builder.setView(inflater.inflate(R.layout.rating_dialog_layout, null));
        builder.setTitle(R.string.rate_event);
               
        builder.setPositiveButton(R.string.ok_button_text, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   mListener.onDialogPositiveClick(RateDialogFragment.this);  
                   }
                
               });
               
        builder.setNegativeButton(R.string.cancel_button_text, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   mListener.onDialogNegativeClick(RateDialogFragment.this);
                   }
               });
        
        return builder.create();
    }
    
    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }
}