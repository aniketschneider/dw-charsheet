package personal.aschneider.charsheet;

import android.content.Context;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.UUID;

/**
 * Singleton facility to manage a set of characters.
 */
public class Playpen {
  private static Playpen INSTANCE = null;

  private Map<UUID, Character> characters;

  private Playpen() {
    characters = Maps.newConcurrentMap();
  }

  public static Playpen getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new Playpen();
    }
    return INSTANCE;
  }

  /**
   * Get the character associated with an id.
   *
   * @param characterId character object's id
   * @return the character object
   */
  public Character getCharacter(UUID characterId) {
    return characters.get(characterId);
  }

  public void addCharacter(Character character) {
    this.characters.put(character.getId(), character);
  }

  public Character newCharacter(Context c) {
    Character newCharacter = new Character(c);
    this.addCharacter(newCharacter);
    return newCharacter;
  }
}
