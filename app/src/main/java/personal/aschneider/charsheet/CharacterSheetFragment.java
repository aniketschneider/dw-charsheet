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

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.UUID;

public class CharacterSheetFragment extends Fragment {
  public static final String EXTRA_CHARACTER_ID = "personal.aschneider.charsheet.CharacterSheetFragment.characterId";
  private static final int REQUEST_SCORE = 1;
  private static final String SCORE_DIALOG_TAG = "score_dialog";

  private UUID characterId;
  private TextView characterClass;
  private TextView race;
  private TextView alignment;
  private TextView level;
  private TextView experience;
  private TextView description;
  private Map<Ability, AbilityScoreView> abilityScoreViews = Maps.newHashMap();
  private TextView damageDie;
  private TextView armor;
  private TextView hitPoints;
  private TextView load;

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

    setRetainInstance(true);
  }

  @Override
  public View onCreateView(LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_character_sheet, container, false);

    characterClass = (TextView) v.findViewById(R.id.class_view);
    race = (TextView) v.findViewById(R.id.race_view);
    alignment = (TextView) v.findViewById(R.id.alignment_view);
    level = (TextView) v.findViewById(R.id.level_view);
    experience = (TextView) v.findViewById(R.id.experience_view);
    description = (TextView) v.findViewById(R.id.description_view);
    damageDie = (TextView) v.findViewById(R.id.damage_die_view);
    armor = (TextView) v.findViewById(R.id.armor_view);
    hitPoints = (TextView) v.findViewById(R.id.hit_points_view);
    load = (TextView) v.findViewById(R.id.load_view);

    abilityScoreViews.put(Ability.STRENGTH, (AbilityScoreView) v.findViewById(R.id.strWidget));
    abilityScoreViews.put(Ability.DEXTERITY, (AbilityScoreView) v.findViewById(R.id.dexWidget));
    abilityScoreViews.put(Ability.CONSTITUTION, (AbilityScoreView) v.findViewById(R.id.conWidget));
    abilityScoreViews.put(Ability.INTELLIGENCE, (AbilityScoreView) v.findViewById(R.id.intWidget));
    abilityScoreViews.put(Ability.WISDOM, (AbilityScoreView) v.findViewById(R.id.wisWidget));
    abilityScoreViews.put(Ability.CHARISMA, (AbilityScoreView) v.findViewById(R.id.chaWidget));

    for (Map.Entry<Ability, AbilityScoreView> e : abilityScoreViews.entrySet()) {
      setAbilityWidgetListener(e.getValue());
    }


    updateCharacterData();

    return v;
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (resultCode != Activity.RESULT_OK) return;
    if (requestCode == REQUEST_SCORE) {
      Ability ability = (Ability) data.getSerializableExtra(AbilityScorePickerFragment.EXTRA_ABILITY_KEY);
      int newScore = data.getIntExtra(AbilityScorePickerFragment.EXTRA_SCORE_KEY, abilityScoreViews.get(ability).getScore());
      Playpen.getInstance().getCharacter(characterId).setAbilityScore(ability, newScore);
      updateCharacterData();
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

  private void updateCharacterData() {
    Character character = Playpen.getInstance().getCharacter(characterId);

    getActivity().setTitle(character.getName());
    characterClass.setText(character.getCharacterClass().getName(getActivity()));
    race.setText(character.getRace(getActivity()));
    alignment.setText(character.getAlignment(getActivity()));
    level.setText(Integer.valueOf(character.getLevel()).toString());
    experience.setText(Integer.valueOf(character.getXp()).toString());
    description.setText(character.getDescription());
    damageDie.setText(character.getDamageDie());
    armor.setText(Integer.valueOf(character.getArmor()).toString());
    hitPoints.setText(character.getCurrentHp() + "/" + character.getMaxHp());
    load.setText(character.getCurrentLoad() + "/" + character.getMaxLoad());

    for (Map.Entry<Ability, AbilityScoreView> e : abilityScoreViews.entrySet()) {
      e.getValue().setScore(character.getAbilityScore(e.getKey()));
    }
  }
}
