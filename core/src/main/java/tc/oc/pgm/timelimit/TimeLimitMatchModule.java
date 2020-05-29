package tc.oc.pgm.timelimit;

import javax.annotation.Nullable;
import org.joda.time.Duration;
import tc.oc.pgm.api.match.Match;
import tc.oc.pgm.api.match.MatchModule;
import tc.oc.pgm.api.party.VictoryCondition;

public class TimeLimitMatchModule implements MatchModule {
  private final Match match;
  private final TimeLimit defaultTimeLimit;
  private @Nullable TimeLimit timeLimit;
  private @Nullable TimeLimitCountdown countdown;

  public TimeLimitMatchModule(Match match, @Nullable TimeLimit timeLimit) {
    this.match = match;
    this.defaultTimeLimit = timeLimit;
  }

  @Override
  public void load() {
    setTimeLimit(defaultTimeLimit);
  }

  @Override
  public void enable() {
    this.start();
  }

  public @Nullable TimeLimit getTimeLimit() {
    return this.timeLimit;
  }

  public void setTimeLimit(@Nullable TimeLimit timeLimit) {
    if (timeLimit != this.timeLimit) {
      match.getLogger().fine("Changing time limit to " + timeLimit);

      this.timeLimit = timeLimit;
      for (VictoryCondition condition : match.getVictoryConditions()) {
        if (condition instanceof TimeLimit) {
          match.removeVictoryCondition(condition);
        }
      }
      if (this.timeLimit != null) {
        match.addVictoryCondition(this.timeLimit);
      }
    }
  }

  public @Nullable TimeLimitCountdown getCountdown() {
    return countdown;
  }

  public @Nullable Duration getFinalRemaining() {
    return this.countdown == null ? null : this.countdown.getRemaining();
  }

  public void start() {
    // Match.finish() will cancel this, so we don't have to
    if (this.timeLimit != null && match.isRunning()) {
      this.countdown = new TimeLimitCountdown(match, this.timeLimit);
      this.countdown.start();
    }
  }

  public void cancel() {
    if (this.countdown != null) {
      this.countdown.cancel();
      this.countdown = null;
    }
  }
}
