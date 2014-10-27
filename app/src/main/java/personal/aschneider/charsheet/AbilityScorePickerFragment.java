package personal.aschneider.charsheet;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;

public class AbilityScorePickerFragment extends DialogFragment {
	public static final String EXTRA_SCORE_KEY = "personal.aschneider.charsheet.DatePickerFragment.score";
	public static final String EXTRA_NAME_KEY = "personal.aschneider.charsheet.DatePickerFragment.name";
	
	private String abilityName;
	private int abilityScore;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_number, null);
		
		abilityScore = getArguments().getInt(EXTRA_SCORE_KEY);
		
		NumberPicker picker = (NumberPicker) v.findViewById(R.id.dialog_number_numberPicker);
		
		picker.setMaxValue(18);
		picker.setMinValue(1);
		picker.setValue(19 - abilityScore);
		picker.setWrapSelectorWheel(false);
		
		// display numbers in descending order
		String[] displayVals = new String[18];
		for (int i = 0; i < 18; i++) {
			displayVals[i] = Integer.valueOf(18 - i).toString();
		}
		picker.setDisplayedValues(displayVals);
		
		// don't allow soft keyboard number selection
		picker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);  
		
		picker.setOnValueChangedListener(new OnValueChangeListener() {
			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
				abilityScore = 19 - newVal;
			}
		});
		
		return new AlertDialog.Builder(getActivity())
			.setView(v)
			.setTitle(abilityName)
			.setPositiveButton(android.R.string.ok, new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					sendResult(Activity.RESULT_OK);
				}
			})
			.create();
	}
	
	public static AbilityScorePickerFragment newInstance(String abilityName, int abilityScore) {
		Bundle args = new Bundle();
		args.putString(EXTRA_NAME_KEY, abilityName);
		args.putInt(EXTRA_SCORE_KEY, abilityScore);
		
		AbilityScorePickerFragment fragment = new AbilityScorePickerFragment();
		fragment.setArguments(args);
		
		return fragment;
	}
	
	private void sendResult(int resultCode) {
		if (getTargetFragment() == null)
			return;
		
		Intent i = new Intent();
		i.putExtra(EXTRA_SCORE_KEY, abilityScore);
		
		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
	}

}
