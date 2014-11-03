package personal.aschneider.charsheet;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by aschneider on 10/27/14.
 */
public class AbilityScoreView extends LinearLayout {
  //TODO: awkward to store mutable abilityScore object here
  private AbilityScore abilityScore;

  private TextView abilityNameView;
  private TextView abilityModNameView;

  private TextView abilityScoreView;
  private TextView abilityModView;

  public AbilityScoreView(Context context) {
    super(context);
    inflateViews(context);
    // TODO: what should happen here?
  }

  public AbilityScoreView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    inflateViews(context);

    readAttributes(context, attrs);
  }

  public AbilityScoreView(Context context, AttributeSet attrs) {
    super(context, attrs);
    inflateViews(context);

    readAttributes(context, attrs);
  }

  private void readAttributes(Context context, AttributeSet attrs) {
    TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AbilityScoreView);
    String longName = a.getString(R.styleable.AbilityScoreView_statName);
    if (longName == null) longName = "REPLACE";
    Integer score = a.getInteger(R.styleable.AbilityScoreView_score, 1);
    a.recycle();

    setAbilityScore(new AbilityScore(longName, score));
  }

  public void setAbilityScore(AbilityScore abilityScore) {
    this.abilityScore = abilityScore;
    updateViews();
  }

  public void setScore(int value) {
    abilityScore.setValue(value);
    updateViews();
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
    abilityNameView.setText(abilityScore.getLongName());
    abilityScoreView.setText(Integer.valueOf(abilityScore.getValue()).toString());
    abilityModNameView.setText(abilityScore.getShortName());
    Integer mod = Integer.valueOf(abilityScore.getModifier());
    String modString;
    if (mod >= 0) {
      modString = "+" + mod.toString();
    } else {
      modString = mod.toString();
    }
    abilityModView.setText(modString);
  }
}
