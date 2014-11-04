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

  private Map<Ability, Integer> stats;

  public Character(Context c) {
    context = c.getApplicationContext();

    id = UUID.randomUUID();
    name = c.getString(R.string.new_character_name);
    raceId = R.string.new_character_race;
    alignmentId = R.string.new_character_alignment;
    level = 1;
    xp = 0;
    description = c.getString(R.string.new_character_desc);

    stats = Maps.newConcurrentMap();
    for (Ability a : Ability.ALL) {
      stats.put(a, 1);
    }
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

  public int getAbilityScore(Ability ability) {
    return stats.get(ability);
  }
}
