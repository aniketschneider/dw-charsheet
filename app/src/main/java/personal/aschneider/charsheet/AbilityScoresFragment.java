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

import java.util.HashMap;
import java.util.Map;

public class AbilityScoresFragment extends Fragment {
	public static final String EXTRA_ABILITY_NAME = "personal.aschneider.charsheet.AbilityScoreFragment.abilityName";
	private static final int REQUEST_SCORE = 1;
	private static final String SCORE_DIALOG_TAG = "score_dialog";
    private Map<Integer, AbilityScore> abilityScores = new HashMap<Integer, AbilityScore>();
    private Map<Integer, AbilityScoreView> abilityScoreViews = new HashMap<Integer, AbilityScoreView>();

	public static AbilityScoresFragment newInstance() {
        //stub
        return new AbilityScoresFragment();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_character_sheet, null);

        abilityScores.put(R.string.ability_strength, new AbilityScore(getString(R.string.ability_strength), 8));
        abilityScores.put(R.string.ability_dexterity, new AbilityScore(getString(R.string.ability_dexterity), 9));
        abilityScores.put(R.string.ability_constitution, new AbilityScore(getString(R.string.ability_constitution), 12));
        abilityScores.put(R.string.ability_intelligence, new AbilityScore(getString(R.string.ability_intelligence), 13));
        abilityScores.put(R.string.ability_wisdom, new AbilityScore(getString(R.string.ability_wisdom), 15));
        abilityScores.put(R.string.ability_charisma, new AbilityScore(getString(R.string.ability_charisma), 16));

        abilityScoreViews.put(R.string.ability_strength, (AbilityScoreView) v.findViewById(R.id.strWidget));
        abilityScoreViews.put(R.string.ability_dexterity, (AbilityScoreView) v.findViewById(R.id.dexWidget));
        abilityScoreViews.put(R.string.ability_constitution, (AbilityScoreView) v.findViewById(R.id.conWidget));
        abilityScoreViews.put(R.string.ability_intelligence, (AbilityScoreView) v.findViewById(R.id.intWidget));
        abilityScoreViews.put(R.string.ability_wisdom, (AbilityScoreView) v.findViewById(R.id.wisWidget));
        abilityScoreViews.put(R.string.ability_charisma, (AbilityScoreView) v.findViewById(R.id.chaWidget));

        for (Map.Entry<Integer, AbilityScoreView> e : abilityScoreViews.entrySet()) {
            e.getValue().setAbilityScore(abilityScores.get(e.getKey()));
            setAbilityWidgetListener(e.getValue(), e.getKey());
        }

		
		return v;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != Activity.RESULT_OK) return;
		if (requestCode == REQUEST_SCORE) {
            int scoreKey = data.getIntExtra(AbilityScorePickerFragment.EXTRA_CALLBACK_KEY, R.string.ability_strength);
            AbilityScore modifiedScore = abilityScores.get(scoreKey);
			int n = data.getIntExtra(AbilityScorePickerFragment.EXTRA_SCORE_KEY, modifiedScore.getValue());
			modifiedScore.setValue(n);
            abilityScoreViews.get(scoreKey).setScore(n);
		}
	}
	
	private void setAbilityWidgetListener(AbilityScoreView widget, final Integer scoreKey) {
        widget.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AbilityScorePickerFragment fragment = AbilityScorePickerFragment.newInstance(
                        abilityScores.get(scoreKey).getLongName(),
                        abilityScores.get(scoreKey).getValue(),
                        scoreKey);
                FragmentManager fm = getActivity().getSupportFragmentManager();

                fragment.setTargetFragment(AbilityScoresFragment.this, REQUEST_SCORE);
                fragment.show(fm, SCORE_DIALOG_TAG);

                return true;
            }
        });
    }
	
}
