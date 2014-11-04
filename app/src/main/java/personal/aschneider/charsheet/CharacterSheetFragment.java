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

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.UUID;

public class CharacterSheetFragment extends Fragment {
  public static final String EXTRA_CHARACTER_ID = "personal.aschneider.charsheet.CharacterSheetFragment.characterId";
  private static final int REQUEST_SCORE = 1;
  private static final String SCORE_DIALOG_TAG = "score_dialog";

  private UUID characterId;
  private Map<Ability, AbilityScoreView> abilityScoreViews = Maps.newHashMap();

  public static CharacterSheetFragment newInstance(UUID characterId) {
    Bundle args = new Bundle();
    args.putSerializable(EXTRA_CHARACTER_ID, characterId);

    CharacterSheetFragment f = new CharacterSheetFragment();
    f.setArguments(args);

    return f;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Bundle args = getArguments();
    UUID characterId = (UUID) args.getSerializable(EXTRA_CHARACTER_ID);
    if (characterId == null) {
      throw new IllegalStateException("No characterId in fragment args");
    } else {
      this.characterId = characterId;
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_character_sheet, null);

    Character character = Playpen.getInstance().getCharacter(characterId);

    abilityScoreViews.put(Ability.STRENGTH, (AbilityScoreView) v.findViewById(R.id.strWidget));
    abilityScoreViews.put(Ability.DEXTERITY, (AbilityScoreView) v.findViewById(R.id.dexWidget));
    abilityScoreViews.put(Ability.CONSTITUTION, (AbilityScoreView) v.findViewById(R.id.conWidget));
    abilityScoreViews.put(Ability.INTELLIGENCE, (AbilityScoreView) v.findViewById(R.id.intWidget));
    abilityScoreViews.put(Ability.WISDOM, (AbilityScoreView) v.findViewById(R.id.wisWidget));
    abilityScoreViews.put(Ability.CHARISMA, (AbilityScoreView) v.findViewById(R.id.chaWidget));

    for (Map.Entry<Ability, AbilityScoreView> e : abilityScoreViews.entrySet()) {
      e.getValue().setScore(character.getAbilityScore(e.getKey()));
      setAbilityWidgetListener(e.getValue());
    }


    return v;
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (resultCode != Activity.RESULT_OK) return;
    if (requestCode == REQUEST_SCORE) {
      Ability ability = (Ability) data.getSerializableExtra(AbilityScorePickerFragment.EXTRA_ABILITY_KEY);
      int newScore = data.getIntExtra(AbilityScorePickerFragment.EXTRA_SCORE_KEY, abilityScoreViews.get(ability).getScore());
      abilityScoreViews.get(ability).setScore(newScore);
    }
  }

  private void setAbilityWidgetListener(final AbilityScoreView widget) {
    widget.setOnLongClickListener(new OnLongClickListener() {
      @Override
      public boolean onLongClick(View v) {
        AbilityScorePickerFragment fragment = AbilityScorePickerFragment.newInstance(
            widget.getAbility(),
            widget.getScore());
        FragmentManager fm = getActivity().getSupportFragmentManager();

        fragment.setTargetFragment(CharacterSheetFragment.this, REQUEST_SCORE);
        fragment.show(fm, SCORE_DIALOG_TAG);

        return true;
      }
    });
  }

}
