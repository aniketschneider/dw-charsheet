package personal.aschneider.charsheet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class AbilityScoreFragment extends Fragment {
	public static final String EXTRA_ABILITY_NAME = "personal.aschneider.charsheet.AbilityScoreFragment.abilityName";
	private static final int REQUEST_SCORE = 1;
	private static final String SCORE_DIALOG_TAG = "score_dialog";
	private AbilityScore abilityScore;
	private TextView abilityNameView;
	private TextView abilityScoreView;
	private TextView abilityModNameView;
	private TextView abilityModView;
	
	public static AbilityScoreFragment newInstance(String abilityName) {
		Bundle args = new Bundle();
		args.putString(EXTRA_ABILITY_NAME, abilityName);
		
		AbilityScoreFragment fragment = new AbilityScoreFragment();
		fragment.setArguments(args);
		
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Bundle args = getArguments();
		abilityScore = new AbilityScore(args.getString(EXTRA_ABILITY_NAME), 0); // TODO: initialize score sensibly
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_ability_score, null);
		
		
		abilityNameView = (TextView) v.findViewById(R.id.abilityNameView);
		abilityScoreView = (TextView) v.findViewById(R.id.abilityScoreView);
		abilityModNameView = (TextView) v.findViewById(R.id.abilityModNameView);
		abilityModView = (TextView) v.findViewById(R.id.abilityModView);
		
		updateViews();
		
		v.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				AbilityScorePickerFragment fragment = AbilityScorePickerFragment.newInstance(abilityScore.getLongName(), abilityScore.getValue());
				FragmentManager fm = getActivity().getSupportFragmentManager();
				
				fragment.setTargetFragment(AbilityScoreFragment.this, REQUEST_SCORE);
				fragment.show(fm, SCORE_DIALOG_TAG);
				
				return true;
			}
		});
		
		return v;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != Activity.RESULT_OK) return;
		if (requestCode == REQUEST_SCORE) {
			int n = data.getIntExtra(AbilityScorePickerFragment.EXTRA_SCORE_KEY, abilityScore.getValue());
			abilityScore.setValue(n);
			updateViews();
		}
	}
	
	private void updateViews() {
		abilityNameView.setText(abilityScore.getLongName());
		abilityScoreView.setText(Integer.valueOf(abilityScore.getValue()).toString());
		abilityModNameView.setText(abilityScore.getShortName());
		abilityModView.setText(Integer.valueOf(abilityScore.getModifier()).toString());
	}
	
	
	
}
