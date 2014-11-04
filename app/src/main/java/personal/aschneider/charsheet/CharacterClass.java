package personal.aschneider.charsheet;

import android.content.Context;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

import java.io.Serializable;
import java.util.List;

/**
 * Created by aschneider on 11/4/14.
 */
public class CharacterClass implements Serializable {
  public static final CharacterClass BARD = new CharacterClass(R.string.class_bard);
  public static final CharacterClass CLERIC = new CharacterClass(R.string.class_cleric);
  public static final CharacterClass DRUID = new CharacterClass(R.string.class_druid);
  public static final CharacterClass FIGHTER = new CharacterClass(R.string.class_fighter);
  public static final CharacterClass PALADIN = new CharacterClass(R.string.class_paladin);
  public static final CharacterClass RANGER = new CharacterClass(R.string.class_ranger);
  public static final CharacterClass THIEF = new CharacterClass(R.string.class_thief);
  public static final CharacterClass WIZARD = new CharacterClass(R.string.class_wizard);

  // Important to have canonical ordering, for save/load serialization purposes
  public static final List<CharacterClass> ALL = ImmutableList.<CharacterClass>builder()
      .add(BARD)
      .add(CLERIC)
      .add(DRUID)
      .add(FIGHTER)
      .add(PALADIN)
      .add(RANGER)
      .add(THIEF)
      .add(WIZARD)
      .build();

  /**
   * Retrieve the CharacterClass associated with a name resource.
   *
   * @param nameId a resource id for the name of a class
   * @return the CharacterClass associated with the name
   */
  public static Optional<CharacterClass> get(int nameId) {
    for (CharacterClass a : ALL) {
      if (a.getNameId() == nameId) {
        return Optional.of(a);
      }
    }
    return Optional.absent();
  }

  /**
   * Retrieve the CharacterClass associated with a name.
   *
   * @param c application context, for associating ids with resources
   * @param name the name of a class
   * @return the CharacterClass associated with the name
   */
  public static Optional<CharacterClass> get(Context c, String name) {
    for (CharacterClass a : ALL) {
      if (c.getString(a.getNameId()).equals(name)) {
        return Optional.of(a);
      }
    }
    return Optional.absent();
  }

  private int nameId;

  private CharacterClass(int nameId) {
    this.nameId = nameId;
  }

  /**
   * @return resource id for the name of the class
   */
  public int getNameId() {
    return nameId;
  }

  /**
   * Get the full, localized name of the class.
   *
   * @param c Application context, to access resources from ids.
   * @return the full name of the ability
   */
  public String getName(Context c) {
    return c.getString(nameId);
  }
}
