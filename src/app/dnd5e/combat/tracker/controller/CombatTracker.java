package app.dnd5e.combat.tracker.controller;

import java.util.ArrayList;
import java.util.Collections;

import app.dnd5e.combat.tracker.model.Creature;
import app.dnd5e.combat.tracker.view.CreatureView;

public class CombatTracker {

	private boolean combatStarted = false;
	private int timer = 0;
	private int roundCounter = 0;
	private int turnCounter = -1;
	private int turnsPerRound = 0;
	private ArrayList<Creature> combatants = new ArrayList<Creature>();

	public void addCombatant(CreatureView creatureView) {
		Creature creatureData = new Creature();
		// TODO proper mapping
		creatureData.setClazz(creatureView.getClazz());
		creatureData.setCondition(creatureView.getCondition());
		creatureData.setHitPointsCur(Integer.parseInt(creatureView.getHpCur()));
		creatureData.setHitPointsMax(Integer.parseInt(creatureView.getHpMax()));
		creatureData.setInitiative(Integer.parseInt(creatureView.getInitiative()));
		creatureData.setName(creatureView.getName());
		combatants.add(creatureData);
	}

	public boolean getCombatStarted() {
		return this.combatStarted;
	}

	public void setCombatStarted(final boolean combatStarted) {
		this.combatStarted = combatStarted;
	}

	public int getTimer() {
		return (this.timer < 0) ? 0 : this.timer;
	}

	public void setTimer(final int timer) {
		this.timer = timer;
	}

	public int getRoundCounter() {
		return this.roundCounter;
	}

	public void setRoundCounter(final int roundCounter) {
		this.roundCounter = roundCounter;
	}

	public int getTurnCounter() {
		return this.turnCounter;
	}

	public void setTurnCounter(final int turnCounter) {
		this.turnCounter = turnCounter;
	}

	public int getTurnsPerRound() {
		return this.turnsPerRound;
	}

	public void setTurnsPerRound(final int turnsPerRound) {
		this.turnsPerRound = turnsPerRound;
	}

	public ArrayList<Creature> getCombatants() {
		return this.combatants;
	}

	public void setCombatants(final ArrayList<Creature> combatants) {
		this.combatants = combatants;
	}

	public void startCombat() {
		this.combatStarted = true;
		this.turnsPerRound = this.combatants.size();
	}

	public void nextTurn() {
		if (++this.turnCounter == this.turnsPerRound) {
			this.roundCounter++;
			this.timer += 6;
			this.turnCounter = 0;
		}
	}

	public void stopCombat() {
		this.combatStarted = false;
		this.turnsPerRound = 0;
	}

	public void creatureHealthChange(Creature creatureToUpdate,
			final int newHealth) {
		combatants.remove(creatureToUpdate);
		creatureToUpdate.setHitPointsCur(newHealth);
		combatants.add(creatureToUpdate);
	}

	public void creatureInitChange(Creature creatureToUpdate, final int newInit) {
		combatants.remove(creatureToUpdate);
		creatureToUpdate.setInitiative(newInit);
		combatants.add(creatureToUpdate);
	}

	public void sortCreaturesByInitiative() {
		Collections.sort(this.combatants);
	}

}
