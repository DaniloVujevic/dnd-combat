package app.dnd5e.combat.tracker.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import app.dnd5e.combat.tracker.controller.CombatTracker;
import app.dnd5e.combat.tracker.model.Clazz;
import app.dnd5e.combat.tracker.model.Condition;
import app.dnd5e.combat.tracker.model.Creature;

public class TrackerScreen {

	private final CombatTracker combatTracker = new CombatTracker();
	private final JFrame combatFrame = new JFrame("Danilo's D&D 5e Combat Tracker");
	private final JLabel roundCounterLbl = new JLabel("Rounds: " + combatTracker.getRoundCounter());
	private final JLabel timerLbl = new JLabel("Time Elapsed: " + combatTracker.getTimer());
	private final JButton nextTurnBtn = new JButton("Next Turn >>");
	private final JPanel combatantRows = new JPanel(new GridLayout(1, 8, 0, 0));
	private final JFrame addCreatureFrame = new JFrame("Add Creatures");

	private final JTextField nameEntryField = new JTextField();
	private final JComboBox<Clazz> clazzEntryOption = new JComboBox<Clazz>(Clazz.values());
	private final JTextField initiativeEntryField = new JTextField();
	private final JTextField hpMaxEntryField = new JTextField();
	private final JTextField hpCurEntryField = new JTextField();
	private final JComboBox<Condition> conditionEntryOption = new JComboBox<Condition>(Condition.values());

	public TrackerScreen() {
		combatFrame.setLocation(new Point(300, 300));
		combatFrame.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		combatFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});

		// Row 1 - Buttons
		JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		JButton addCombatantsBtn = new JButton("Add Creatures");
		JButton sortInitBtn = new JButton("Initiative Sort");
		addCombatantsBtn.addActionListener(new AddCombatantsListener());
		sortInitBtn.addActionListener(new SortCombatantsListener());
		nextTurnBtn.addActionListener(new NextTurnListener());
		nextTurnBtn.setVisible(false);
		nextTurnBtn.setEnabled(false);
		nextTurnBtn.setFocusable(false);
		controlPanel.add(addCombatantsBtn);
		controlPanel.add(sortInitBtn);
		controlPanel.add(nextTurnBtn);
		controlPanel.add(roundCounterLbl);
		controlPanel.add(timerLbl);
		gbc.gridy = 0;
		combatFrame.add(controlPanel, gbc);

		// Row 2 - Column header labels
		JPanel panHeaders = new JPanel(new CombatDataTableRowLayout());
		JLabel colHeadName = new JLabel("Name", JLabel.CENTER);
		JLabel colHeadInit = new JLabel("Initiative", JLabel.CENTER);
		JLabel colHeadAct = new JLabel("Action", JLabel.CENTER);
		JLabel colHeadBonAct = new JLabel("Bonus Action", JLabel.CENTER);
		JLabel colHeadReact = new JLabel("Reaction", JLabel.CENTER);
		JLabel colHeadCond = new JLabel("Condition", JLabel.CENTER);
		JLabel colHeadHPNum = new JLabel("HP", JLabel.CENTER);
		JLabel colHeadHPDesc = new JLabel("Health", JLabel.CENTER);
		panHeaders.add(colHeadName);
		panHeaders.add(colHeadInit);
		panHeaders.add(colHeadAct);
		panHeaders.add(colHeadBonAct);
		panHeaders.add(colHeadReact);
		panHeaders.add(colHeadCond);
		panHeaders.add(colHeadHPNum);
		panHeaders.add(colHeadHPDesc);
		gbc.gridy = 1;
		combatFrame.add(panHeaders, gbc);

		// Row 3 - Battle participants
		gbc.gridy = 2;
		combatFrame.add(combatantRows, gbc);
		combatFrame.setResizable(false);
		combatFrame.pack();
		combatFrame.setEnabled(true);
		combatFrame.setVisible(true);
		
		initAddCreatureFrame();
	}

	private void initAddCreatureFrame() {

		addCreatureFrame.setLocationRelativeTo(combatFrame);
		addCreatureFrame.setLayout(new GridLayout(3, 1, 10, 10));

		// Row 1 - Column header labels
		JPanel colHeaders = new JPanel(new GridLayout(1, 6));
		JLabel colHeadName = new JLabel("Name", JLabel.CENTER);
		JLabel colHeadClazz = new JLabel("Class", JLabel.CENTER);
		JLabel colHeadInit = new JLabel("Initiative", JLabel.CENTER);
		JLabel colHeadHPMax = new JLabel("HP Max", JLabel.CENTER);
		JLabel colHeadHPCur = new JLabel("HP Current", JLabel.CENTER);
		JLabel colHeadCond = new JLabel("Condition", JLabel.CENTER);
		colHeaders.add(colHeadName);
		colHeaders.add(colHeadClazz);
		colHeaders.add(colHeadInit);
		colHeaders.add(colHeadHPMax);
		colHeaders.add(colHeadHPCur);
		colHeaders.add(colHeadCond);
		addCreatureFrame.add(colHeaders);

		// Row 2 - Input fields
		JPanel addCreatureInputPanel = new JPanel(new GridLayout(1, 6));
		addCreatureInputPanel.add(nameEntryField);
		addCreatureInputPanel.add(clazzEntryOption);
		addCreatureInputPanel.add(initiativeEntryField);
		addCreatureInputPanel.add(hpMaxEntryField);
		addCreatureInputPanel.add(hpCurEntryField);
		addCreatureInputPanel.add(conditionEntryOption);
		addCreatureFrame.add(addCreatureInputPanel);

		// Row 3 - Buttons
		JPanel buttons = new JPanel(new GridLayout(1, 2));
		JButton addMoreBtn = new JButton("Add another");
		JButton doneBtn = new JButton("Done");
		addMoreBtn.addActionListener(new AddMoreButtonListener());
		doneBtn.addActionListener(new DoneButtonListener());
		buttons.add(addMoreBtn);
		buttons.add(doneBtn);
		addCreatureFrame.add(buttons);

		addCreatureFrame.setResizable(false);
		addCreatureFrame.pack();
		addCreatureFrame.setEnabled(false);
		addCreatureFrame.setVisible(false);
	}

	private class AddCombatantsListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			combatTracker.stopCombat();
			combatFrame.setFocusable(false);
			combatFrame.setEnabled(false);
			addCreatureFrame.setFocusable(true);
			addCreatureFrame.setEnabled(true);
			addCreatureFrame.setVisible(true);
		}
	}

	private class SortCombatantsListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			combatTracker.stopCombat();
			addCombatantRows();
			combatFrame.revalidate();
			combatFrame.repaint();
		}
	}

	private class AddMoreButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// TODO proper field validation
			saveCombatant();
			clearFields();
		}
	}

	private void saveCombatant() {
		CreatureView curCreature = new CreatureView(nameEntryField.getText(), (Clazz) clazzEntryOption.getSelectedItem(),
				initiativeEntryField.getText(), hpMaxEntryField.getText(), hpCurEntryField.getText(), (Condition) conditionEntryOption.getSelectedItem());
		combatTracker.addCombatant(curCreature);
	}

	private void clearFields() {
		nameEntryField.setText("");
		clazzEntryOption.setSelectedIndex(0);
		initiativeEntryField.setText("");
		hpMaxEntryField.setText("");
		hpCurEntryField.setText("");
		conditionEntryOption.setSelectedIndex(0);
	}

	private class DoneButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (!nameEntryField.getText().isEmpty()) {
				// TODO proper field validation
				saveCombatant();
			}
			clearFields();
			addCombatantRows();

			addCreatureFrame.setFocusable(false);
			addCreatureFrame.setEnabled(false);
			addCreatureFrame.setVisible(false);

			combatTracker.startCombat();
			nextTurnBtn.setVisible(true);
			nextTurnBtn.setEnabled(true);
			nextTurnBtn.setFocusable(true);

			combatFrame.revalidate();
			combatFrame.repaint();
			combatFrame.pack();
			combatFrame.setEnabled(true);
			combatFrame.setFocusable(true);
			combatFrame.toFront();
		}
	}

	private class NextTurnListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (combatTracker.getCombatStarted()) {
				combatTracker.nextTurn();
			} else {
				combatTracker.startCombat();
			}
			roundCounterLbl.setText("Rounds: " + combatTracker.getRoundCounter());
			timerLbl.setText("Time Elapsed: " + combatTracker.getTimer());

			for (int i = 0; i < combatTracker.getCombatants().size(); i++) {
				CreatureRowPanel curRow = (CreatureRowPanel)combatantRows.getComponent(i);
				if (i == combatTracker.getTurnCounter()) {
					curRow.setEnabled(true);
					curRow.setBackground(Color.LIGHT_GRAY);
					curRow.resetActvities();
					curRow.setEnabled(true);
				} else {
					curRow.setEnabled(false);
					curRow.setBackground(Color.GRAY);
					curRow.setEnabled(false);
				}
			}
		}
	}

	private void addCombatantRows() {
		combatantRows.removeAll();
		combatTracker.sortCreaturesByInitiative();
		combatantRows.setLayout(new GridLayout(combatTracker.getCombatants().size(), 8, 0, 0));

		combatTracker.getCombatants().forEach(c -> combatantRows.add(new CreatureRowPanel(c, combatTracker)));

		combatantRows.revalidate();
		combatantRows.repaint();
	}

}
