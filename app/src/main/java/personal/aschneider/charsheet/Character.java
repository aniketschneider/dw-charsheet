package personal.aschneider.charsheet;

import android.content.Context;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.UUID;

public class Character {
  private Context context;

  private final UUID id;
  private String name;
  private int raceId;
  private int alignmentId;
  private int level;
  private int xp;
  private String description;

  private Map<Integer, Integer> stats;

  public Character(Context c) {
    context = c;

    id = UUID.randomUUID();
    name = c.getString(R.string.new_character_name);
    raceId = R.string.new_character_race;
    alignmentId = R.string.new_character_alignment;
    level = 1;
    xp = 0;
    description = c.getString(R.string.new_character_desc);

    stats = Maps.newConcurrentMap();
    stats.put(R.string.ability_strength, 1);
    stats.put(R.string.ability_dexterity, 1);
    stats.put(R.string.ability_constitution, 1);
    stats.put(R.string.ability_intelligence, 1);
    stats.put(R.string.ability_wisdom, 1);
    stats.put(R.string.ability_charisma, 1);
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

  public void setRaceId(int race) {
    this.raceId = race;
  }

  public int getAlignmentId() {
    return alignmentId;
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

  public int getStatValue(int abilityId) {
    return stats.get(abilityId);
  }
}
