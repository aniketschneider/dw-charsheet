package personal.aschneider.charsheet;

import android.content.Context;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.UUID;

public class Character {
  private Context context;

  private final UUID id;
  private String name;
  private CharacterClass characterClass;
  private int raceId;
  private int alignmentId;
  private int level;
  private int xp;
  private String description;

  private String damageDie;
  private int armor;

  private int currentLoad;
  private int baseLoad;

  private int currentHp;
  private int baseHp;

  private Map<Ability, Integer> abilities;

  public Character(Context c, CharacterClass characterClass) {
    context = c.getApplicationContext();

    id = UUID.randomUUID();
    name = c.getString(R.string.new_character_name);
    this.characterClass = characterClass;
    raceId = R.string.new_character_race;
    alignmentId = R.string.new_character_alignment;
    level = 1;
    xp = 0;
    description = c.getString(R.string.new_character_desc);

    abilities = Maps.newConcurrentMap();
    for (Ability a : Ability.ALL) {
      abilities.put(a, 1);
    }

    //TODO: unstub these
    damageDie = "d8";
    armor = 0;
    baseHp = 10;
    currentHp = getMaxHp();  // depends on abilities being defined
    baseLoad = 6;
    currentLoad = 0;
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getRaceId() {
    return raceId;
  }

  public String getRace(Context c) {
    return c.getString(getRaceId());
  }

  public void setRaceId(int race) {
    this.raceId = race;
  }

  public int getAlignmentId() {
    return alignmentId;
  }

  public String getAlignment(Context c) {
    return c.getString(getAlignmentId());
  }

  public void setAlignmentId(int alignment) {
    this.alignmentId = alignment;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public int getXp() {
    return xp;
  }

  public void setXp(int xp) {
    this.xp = xp;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getAbilityScore(Ability ability) {
    return abilities.get(ability);
  }

  public void setAbilityScore(Ability ability, int score) {
    abilities.put(ability, score);
  }

  public String getDamageDie() {
    //TODO: should be based on class
    return damageDie;
  }

  public int getCurrentHp() {
    return currentHp;
  }

  public int getMaxHp() {
    //TODO: should be based on class, not fixed baseHp
    return baseHp + getAbilityScore(Ability.CONSTITUTION);
  }

  public int getCurrentLoad() {
    return currentLoad;
  }

  public int getMaxLoad() {
    //TODO: should be based on class, not fixed baseLoad
    return baseLoad + Ability.getMod(getAbilityScore(Ability.STRENGTH));
  }

  public int getArmor() {
    //TODO: should be calculated based on gear
    return armor;
  }

  public void setCharacterClass(CharacterClass newClass) {
    this.characterClass = newClass;
  }

  public CharacterClass getCharacterClass() {
    return characterClass;
  }
}
