package personal.aschneider.charsheet;

import android.content.Context;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

import java.io.Serializable;
import java.util.List;

/**
 * Abstraction of an ability. Also provides static facilities for general operations on abilities.
 */
public class Ability implements Serializable {
  public static final Ability STRENGTH = new Ability(R.string.ability_strength);
  public static final Ability DEXTERITY = new Ability(R.string.ability_dexterity);
  public static final Ability CONSTITUTION = new Ability(R.string.ability_constitution);
  public static final Ability INTELLIGENCE = new Ability(R.string.ability_intelligence);
  public static final Ability WISDOM = new Ability(R.string.ability_wisdom);
  public static final Ability CHARISMA = new Ability(R.string.ability_charisma);

  public static final List<Ability> ALL = ImmutableList.<Ability>builder()
      .add(STRENGTH)
      .add(DEXTERITY)
      .add(CONSTITUTION)
      .add(INTELLIGENCE)
      .add(WISDOM)
      .add(CHARISMA)
      .build();

  /**
   * Get the numerical modifier for an ability score.
   *
   * @param score the total ability score
   * @return the roll modifier for rolls against that ability score
   */
  public static int getMod(int score) {
    if (score <= 3)	return -3;
    else if (score <= 5) return -2;
    else if (score <= 8) return -1;
    else if (score <= 12) return 0;
    else if (score <= 15) return 1;
    else if (score <= 17) return 2;
    else return 3;
  }

  /**
   * Get the string associated with an ability modifier, including a +/- prefix.
   *
   * @param score the total ability score
   * @return the ability modifier, with a +/- prefix as appropriate
   */
  public static String getModString(int score) {
    Integer mod = getMod(score);

    String modString;
    if (mod >= 0) {
      modString = "+" + mod.toString();
    } else {
      modString = mod.toString();
    }

    return modString;
  }

  /**
   * Retrieve the Ability associated with a name resource.
   *
   * @param nameId a resource id for the name of an ability
   * @return the Ability associated with the name
   */
  public static Optional<Ability> get(int nameId) {
    for (Ability a : ALL) {
      if (a.getNameId() == nameId) {
        return Optional.of(a);
      }
    }
    return Optional.absent();
  }

  /**
   * Retrieve the Ability associated with a name.
   *
   * @param c application context, for associating ids with resources
   * @param name the name of an ability
   * @return the Ability associated with the name
   */
  public static Optional<Ability> get(Context c, String name) {
    for (Ability a : ALL) {
      if (c.getString(a.getNameId()).equals(name)) {
        return Optional.of(a);
      }
    }
    return Optional.absent();
  }

  private int name;

  private Ability(int name) {
    this.name = name;
  }

  /**
   * @return resource id for the name of the ability
   */
  public int getNameId() {
    return name;
  }

  /**
   * Get the full, localized name of the ability.
   *
   * @param c Application context, to access resources from ids.
   * @return the full name of the ability
   */
  public String getName(Context c) {
    return c.getString(name);
  }

  /**
   * Get the short name of the ability, the name of the ability modifier.
   *
   * @param c Application context, to access resources from ids.
   * @return the short name of the ability
   */
  public String getShortName(Context c) {
    return getName(c).substring(0,3).toUpperCase();
  }
}
