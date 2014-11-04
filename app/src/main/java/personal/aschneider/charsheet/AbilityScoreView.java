package personal.aschneider.charsheet;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.common.base.Optional;

/**
 * A widget for displaying an ability score.  Requires at least an attribute to define which ability
 * it refers to.
 */
public class AbilityScoreView extends LinearLayout {
  private final Ability ability;
  private int score;

  private TextView abilityNameView;
  private TextView abilityModNameView;
  private TextView abilityScoreView;
  private TextView abilityModView;

  public int getScore() {
    return score;
  }

  public Ability getAbility() {
    return this.ability;
  }

  public void setScore(int value) {
    this.score = value;
    updateViews();
  }

  public AbilityScoreView(Context context) {
    super(context);
    inflateViews(context);

    // TODO: what should happen here?
    throw new RuntimeException("AbilityScoreView created without attributes");
  }

  public AbilityScoreView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    inflateViews(context);

    ability = readAttributes(context, attrs);
    updateViews();
  }

  public AbilityScoreView(Context context, AttributeSet attrs) {
    super(context, attrs);
    inflateViews(context);

    ability = readAttributes(context, attrs);
    updateViews();
  }

  private Ability readAttributes(Context context, AttributeSet attrs) {
    TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AbilityScoreView);
    String longName = a.getString(R.styleable.AbilityScoreView_statName);
    int score = a.getInt(R.styleable.AbilityScoreView_score, 1);
    a.recycle();

    this.score = score;

    Optional<Ability> attributeAbility = Ability.get(context, longName);
    if (attributeAbility.isPresent()) {
      return attributeAbility.get();
    } else {
      throw new IllegalArgumentException(longName + " is not a valid ability");
    }
  }

  private void inflateViews(Context c) {
    LayoutInflater inflater = LayoutInflater.from(c);
    inflater.inflate(R.layout.widget_ability_score, this);

    abilityNameView = (TextView) this.findViewById(R.id.abilityNameView);
    abilityScoreView = (TextView) this.findViewById(R.id.abilityScoreView);
    abilityModNameView = (TextView) this.findViewById(R.id.abilityModNameView);
    abilityModView = (TextView) this.findViewById(R.id.abilityModView);
  }

  private void updateViews() {
    abilityNameView.setText(ability.getName(getContext()));
    abilityScoreView.setText(Integer.valueOf(score).toString());
    abilityModNameView.setText(ability.getShortName(getContext()));
    Integer mod = Integer.valueOf(Ability.getMod(score));
    String modString;
    if (mod >= 0) {
      modString = "+" + mod.toString();
    } else {
      modString = mod.toString();
    }
    abilityModView.setText(modString);
  }
}
