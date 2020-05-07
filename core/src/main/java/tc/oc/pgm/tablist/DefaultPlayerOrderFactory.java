package tc.oc.pgm.tablist;

import tc.oc.pgm.api.player.MatchPlayer;

public class DefaultPlayerOrderFactory implements PlayerOrderFactory {

	@Override
	public PlayerOrder getOrder(MatchPlayer viewer) {
		return new PlayerOrder(viewer);
	}

}
