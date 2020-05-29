package tc.oc.util.bukkit.bossbar;

import org.bukkit.entity.Player;
import tc.oc.util.bukkit.component.Component;

/** A retained UI component that renders text and a health amount to the boss bar */
public interface BossBar {

  /**
   * Is this bar currently visible? This is called before every render, and if it returns false, the
   * bar will be completely ignored.
   */
  boolean isVisible(Player viewer);

  /** Called at render time to get the text to display in the bar. */
  Component getText(Player viewer);

  /**
   * Called at render time to get the amount of health to display in the bar. Valid range is 0 (no
   * health) to 1 (full health).
   */
  float getMeter(Player viewer);

  /** Subscribe to invalidation events for this bar */
  void addObserver(BossBarObserver observer);

  /** Unsubscribe from invalidation events for this bar */
  void removeObserver(BossBarObserver observer);
}
