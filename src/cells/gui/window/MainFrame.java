package cells.gui.window;

import cells.gui.panel.ECAPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author mzak
 */
public class MainFrame extends JFrame{

    public MainFrame() {
        super();
        JMenuBar menuBar = new JMenuBar();
        JMenu menuProgram = new JMenu("Program");
        JMenuItem itemECA = new JMenuItem("Elementary Cellular Automaton");
        itemECA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().removeAll();
                ECAPanel t = new ECAPanel();
                getContentPane().add(t, BorderLayout.CENTER);
                JMenuBar tmpBar = new JMenuBar();

                tmpBar.add(new JLabel(" PATTERN: "));
                SpinnerNumberModel sNMPatttern = new SpinnerNumberModel(0, 0, 255, 1);
                final JSpinner spinPattern = new JSpinner(sNMPatttern);
                final JComboBox<String> modeBox = new JComboBox<>(new String[]{
                    ECAPanel.DESC_MODE_BLANK,
                    ECAPanel.DESC_MODE_SINGLE,
                    ECAPanel.DESC_MODE_RANDOM});

                spinPattern.addChangeListener(new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        int val = (Integer) spinPattern.getValue();
                        if (val >= 0 && val <= 255) {
                            t.reset(modeBox.getSelectedIndex(), val);
                        }
                    }
                });

                tmpBar.add(spinPattern);

                modeBox.addItemListener(new ItemListener() {
                    @Override
                    public void itemStateChanged(ItemEvent e) {
                        int val = (Integer) spinPattern.getValue();
                        if (val >= 0 && val <= 255) {
                            t.reset(modeBox.getSelectedIndex(), val);
                        }
                    }
                });
                tmpBar.add(new JLabel("  MODE: "));
                tmpBar.add(modeBox);

                getContentPane().add(tmpBar, BorderLayout.SOUTH);

                setVisible(true);
                repaint();
                t.setVisible(true);
                t.reset(0, 0);
            }
        });
        menuProgram.add(itemECA);
        menuProgram.addSeparator();
        JMenuItem itemExit = new JMenuItem("Exit program");
        itemExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(rootPane, "Are you sure?", "Exit program", JOptionPane.YES_NO_OPTION) == 0) {
                    System.exit(0);
                }
            }
        });
        menuProgram.add(itemExit);
        menuBar.add(menuProgram);
        setJMenuBar(menuBar);
    }

}
