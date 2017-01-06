package app.dnd5e.combat.tracker.view;

import app.dnd5e.combat.tracker.model.Clazz;
import app.dnd5e.combat.tracker.model.Condition;

public class CreatureView {

	private String name;
	private Clazz clazz;
	private String initiative;
	private String hpMax;
	private String hpCur;
	private Condition condition;

	public CreatureView(final String name, final Clazz clazz,
			final String initiative, final String hpMax, final String hpCur,
			final Condition condition) {

		this.name = name;
		this.clazz = clazz;
		this.initiative = initiative;
		this.hpMax = hpMax;
		this.hpCur = hpCur;
		this.condition = condition;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Clazz getClazz() {
		return clazz;
	}

	public void setClazz(final Clazz clazz) {
		this.clazz = clazz;
	}

	public String getInitiative() {
		return initiative;
	}

	public void setInitiative(final String initiative) {
		this.initiative = initiative;
	}

	public String getHpMax() {
		return hpMax;
	}

	public void setHpMax(final String hpMax) {
		this.hpMax = hpMax;
	}

	public String getHpCur() {
		return hpCur;
	}

	public void setHpCur(final String hpCur) {
		this.hpCur = hpCur;
	}

	public Condition getCondition() {
		return condition;
	}

	public void setCondition(final Condition condition) {
		this.condition = condition;
	}

}
