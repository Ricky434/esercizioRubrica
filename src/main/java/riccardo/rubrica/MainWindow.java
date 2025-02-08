package riccardo.rubrica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * MainWindow
 */
public class MainWindow extends JFrame implements ActionListener {
    private Rubrica rubrica;

    private JTable contattiTable;
    private JButton nuovoBtn;
    private JButton modificaBtn;
    private JButton eliminaBtn;

    public MainWindow(Rubrica rubrica) {
        this.setTitle("Rubrica app");
        this.setSize(600, 720);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        // Carica tabella
        this.rubrica = rubrica;
        contattiTable = new JTable(rubrica);

        // Aggiungi tabella a finestra in scrollpane
        JScrollPane scrollPane = new JScrollPane(contattiTable);
        scrollPane.setBackground(Color.red);
        this.add(scrollPane, BorderLayout.CENTER);

        // Bottoni
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        nuovoBtn = new JButton("Nuovo");
        modificaBtn = new JButton("Modifica");
        eliminaBtn = new JButton("Elimina");
        nuovoBtn.addActionListener(this);
        modificaBtn.addActionListener(this);
        eliminaBtn.addActionListener(this);
        buttonPane.add(nuovoBtn);
        buttonPane.add(modificaBtn);
        buttonPane.add(eliminaBtn);

        // Inserisci bottoni in altro panel per centrarli
        JPanel bottomPane = new JPanel();
        bottomPane.add(buttonPane);
        buttonPane.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.add(bottomPane, BorderLayout.SOUTH);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == nuovoBtn) {
            EditPersonaWindow editWindow = EditPersonaWindow.getInstance();
            editWindow.loadPersona(rubrica, null);
        } else if (e.getSource() == modificaBtn) {
            int row = contattiTable.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Per modificare e' necessario prima selezionare una persona");
                return;
            }

            EditPersonaWindow editWindow = EditPersonaWindow.getInstance();
            editWindow.loadPersona(rubrica, rubrica.getContatto(row));
        } else if (e.getSource() == eliminaBtn) {
            int row = contattiTable.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Per eliminare e' necessario prima selezionare una persona");
                return;
            }

            rubrica.removeContatto(row);
        }
    }
}
