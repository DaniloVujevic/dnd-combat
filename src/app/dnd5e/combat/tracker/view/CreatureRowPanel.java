package app.dnd5e.combat.tracker.view;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import app.dnd5e.combat.tracker.controller.CombatTracker;
import app.dnd5e.combat.tracker.model.Action;
import app.dnd5e.combat.tracker.model.BonusAction;
import app.dnd5e.combat.tracker.model.Condition;
import app.dnd5e.combat.tracker.model.Creature;
import app.dnd5e.combat.tracker.model.HealthStatus;
import app.dnd5e.combat.tracker.model.Reaction;

class CreatureRowPanel extends JPanel {

	private static final long serialVersionUID = -3591668633395961520L;

	private Creature creature;
	private JLabel nameLabel;
	private JTextField initTextField;
	private JComboBox<Action> actionBox;
	private JComboBox<BonusAction> bonusActionBox;
	private JComboBox<Reaction> reactionBox;
	private JComboBox<Condition> conditionBox;
	private JTextField hpTextField;
	private JLabel healthDescLabel;

	CreatureRowPanel(final Creature creature, final CombatTracker tracker) {

		super(new CombatDataTableRowLayout());
		this.creature = creature;
		nameLabel = new JLabel(this.creature.getName(), JLabel.CENTER);
		initTextField = new JTextField(Integer.toString(this.creature
				.getInitiative()));
		initTextField.getDocument().addDocumentListener(
				new InitiativeTextFieldListener(tracker));
		actionBox = new JComboBox<Action>(Action.values());
		actionBox.insertItemAt(null, 0);
		actionBox.setSelectedIndex(0);
		bonusActionBox = new JComboBox<BonusAction>(BonusAction.values());
		bonusActionBox.insertItemAt(null, 0);
		bonusActionBox.setSelectedIndex(0);
		reactionBox = new JComboBox<Reaction>(Reaction.values());
		reactionBox.insertItemAt(null, 0);
		reactionBox.setSelectedIndex(0);
		HealthStatus creatureHealth = this.creature.getHitPointsDesc();
		healthDescLabel = new JLabel(
				HealthStatus.getDescription(creatureHealth), JLabel.CENTER);
		conditionBox = new JComboBox<Condition>(Condition.values());
		conditionBox.setSelectedItem(this.creature.getCondition());
		String hpText = this.creature.getHitPointsCur() + "/"
				+ this.creature.getHitPointsMax();
		hpTextField = new JTextField(hpText);
		hpTextField.getDocument().addDocumentListener(
				new HpTextFieldListener(tracker));

		setHealthLabel();

		this.add(nameLabel);
		this.add(initTextField);
		this.add(actionBox);
		this.add(bonusActionBox);
		this.add(reactionBox);
		this.add(conditionBox);
		this.add(hpTextField);
		this.add(healthDescLabel);
	}

	void setHealthLabel() {
		HealthStatus creatureHealth = this.creature.getHitPointsDesc();
		healthDescLabel.setText(HealthStatus.getDescription(creatureHealth));
		switch (creatureHealth) {
		case FULL_HEALTH:
		case THREE_Q_TO_FULL:
			healthDescLabel.setForeground(Color.GREEN);
			break;
		case HALF_TO_THREE_Q:
			healthDescLabel.setForeground(Color.YELLOW);
			break;
		case ONE_Q_TO_HALF:
			healthDescLabel.setForeground(Color.ORANGE);
			break;
		case UP_TO_ONE_Q:
			healthDescLabel.setForeground(Color.RED);
			break;
		case NO_HEALTH:
			healthDescLabel.setForeground(Color.DARK_GRAY);
			break;
		}
		healthDescLabel.revalidate();
		healthDescLabel.repaint();
	}

	void resetActvities() {
		actionBox.setSelectedIndex(0);
		bonusActionBox.setSelectedIndex(0);
		reactionBox.setSelectedIndex(0);
	}

	@Override
	public void setEnabled(final boolean enabled) {
		// TODO don't disable HP field
		for (Component c : this.getComponents()) {
			c.setEnabled(enabled);
		}
	}

	private class HpTextFieldListener implements DocumentListener {

		private CombatTracker ct;
		private Creature c;

		public HpTextFieldListener(CombatTracker tracker) {
			this.ct = tracker;
			this.c = creature;
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			updateHealth();
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			updateHealth();
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			updateHealth();
		}

		private void updateHealth() {
			String fullHp = hpTextField.getText();
			String updatedHp = fullHp.substring(0, fullHp.indexOf("/"));
			if (updatedHp.isEmpty())
				return;
			ct.creatureHealthChange(c, Integer.parseInt(updatedHp));
			setHealthLabel();
		}
	}

	private class InitiativeTextFieldListener implements DocumentListener {

		private CombatTracker ct;
		private Creature c;

		public InitiativeTextFieldListener(CombatTracker tracker) {
			this.ct = tracker;
			this.c = creature;
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			updateInitiative();
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			updateInitiative();
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			updateInitiative();
		}

		private void updateInitiative() {
			String newInit = initTextField.getText();
			if (newInit.isEmpty())
				return;
			ct.creatureInitChange(c, Integer.parseInt(newInit));
		}
	}

}
