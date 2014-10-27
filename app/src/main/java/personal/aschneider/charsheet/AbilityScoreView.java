package personal.aschneider.charsheet;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by aschneider on 10/27/14.
 */
public class AbilityScoreView extends LinearLayout {
    private AbilityScore abilityScore;

    private TextView abilityNameView;
    private TextView abilityModNameView;

    private TextView abilityScoreView;
    private TextView abilityModView;

    public AbilityScoreView(Context context, AttributeSet attrs, AbilityScore abilityScore) {
        super(context, attrs);
        inflateViews(context);

        this.abilityScore = abilityScore;
        updateViews();
    }

    public AbilityScoreView(Context context) {
        super(context);
        inflateViews(context);
    }

    public AbilityScoreView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflateViews(context);
    }

    public AbilityScoreView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inflateViews(context);
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
        abilityModView.setText(Integer.valueOf(abilityScore.getModifier()).toString());
    }
}
