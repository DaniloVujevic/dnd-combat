package app.dnd5e.combat.tracker.model;

public class Creature implements Comparable<Creature> {

	private String name;
	private Clazz clazz;
	private int initiative;
	private int hitPointsMax;
	private int hitPointsCur;
	private HealthStatus hitPointsDesc;
	private Condition condition;

	public Creature() {
	}

	public Creature(final String name, final Clazz clazz, final int initiative,
			final int hitPointsMax, final int hitPointsCur,
			final Condition condition) {

		// TODO proper type checks
		this.name = name;
		this.clazz = clazz;
		this.initiative = initiative;

		if (hitPointsCur < 0 || hitPointsCur > hitPointsMax) {
			// Error - default
			this.hitPointsMax = 20;
			this.hitPointsCur = 20;
		} else {
			this.hitPointsMax = hitPointsMax;
			this.hitPointsCur = hitPointsCur;
		}

		this.hitPointsDesc = describeHealth();
		this.condition = condition;
	}

	private HealthStatus describeHealth() {

		int threeQuartHealth = (int) (this.hitPointsMax * (75 / 100.0f));
		int halfHealth = (int) (this.hitPointsMax * (50 / 100.0f));
		int quarterHealth = (int) (this.hitPointsMax * (25 / 100.0f));

		if (this.hitPointsCur == this.hitPointsMax) {
			return HealthStatus.FULL_HEALTH;
		} else if (this.hitPointsCur > threeQuartHealth) {
			return HealthStatus.THREE_Q_TO_FULL;
		} else if (this.hitPointsCur > halfHealth) {
			return HealthStatus.HALF_TO_THREE_Q;
		} else if (this.hitPointsCur > quarterHealth) {
			return HealthStatus.ONE_Q_TO_HALF;
		} else if (this.hitPointsCur > 0) {
			return HealthStatus.UP_TO_ONE_Q;
		} else {
			this.condition = Condition.UNCONSCIOUS;
			return HealthStatus.NO_HEALTH;
		}
	}

	@Override
	public int compareTo(Creature c) {
		return this.initiative < c.initiative ? 1
				: (this.initiative > c.initiative ? -1 : 0);
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Clazz getClazz() {
		return this.clazz;
	}

	public void setClazz(final Clazz clazz) {
		this.clazz = clazz;
	}

	public int getInitiative() {
		return this.initiative;
	}

	public void setInitiative(final int initiative) {
		this.initiative = initiative;
	}

	public int getHitPointsMax() {
		return this.hitPointsMax;
	}

	public void setHitPointsMax(final int hitPointsMax) {
		this.hitPointsMax = hitPointsMax;
		this.hitPointsDesc = describeHealth();
	}

	public int getHitPointsCur() {
		return this.hitPointsCur;
	}

	public void setHitPointsCur(final int hitPointsCur) {
		this.hitPointsCur = hitPointsCur;
		this.hitPointsDesc = describeHealth();
	}

	public HealthStatus getHitPointsDesc() {
		return this.hitPointsDesc;
	}

	public void setHitPointsDesc(final HealthStatus hitPointsDesc) {
		this.hitPointsDesc = hitPointsDesc;
	}

	public Condition getCondition() {
		return this.condition;
	}

	public void setCondition(final Condition condition) {
		this.condition = condition;
	}

}
