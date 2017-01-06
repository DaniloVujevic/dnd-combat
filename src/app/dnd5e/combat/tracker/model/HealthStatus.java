package app.dnd5e.combat.tracker.model;

public enum HealthStatus {

	FULL_HEALTH, THREE_Q_TO_FULL, HALF_TO_THREE_Q, ONE_Q_TO_HALF, UP_TO_ONE_Q, NO_HEALTH;

	public static String getDescription(final HealthStatus hs) {
		switch (hs) {
		case FULL_HEALTH:
			return "Healthy";
		case THREE_Q_TO_FULL:
			return "Hurt";
		case HALF_TO_THREE_Q:
			return "Injured";
		case ONE_Q_TO_HALF:
			return "Wounded";
		case UP_TO_ONE_Q:
			return "Gravely Wounded";
		case NO_HEALTH:
			return "R.I.P";
		default:
			return "";
		}
	}

}
