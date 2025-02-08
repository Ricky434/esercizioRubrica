package riccardo.rubrica;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * EditPersonaWindow
 */
public class EditPersonaWindow extends JFrame implements ActionListener {
    private static EditPersonaWindow instance;

    private Rubrica rubrica;
    private Persona personaEditing; // Persona che si sta editando, null se nuova persona

    private JButton salvaBtn;
    private JButton annullaBtn;

    private JTextField nomeInput;
    private JTextField cognomeInput;
    private JTextField indirizzoInput;
    private JTextField telefonoInput;
    private JTextField etaInput;

    private EditPersonaWindow() {
        this.setTitle("Editor Persona");
        this.setLayout(new BorderLayout());

        // Fields dati persona
        JPanel fieldsPane = new JPanel();
        fieldsPane.setLayout(new GridLayout(5, 2, 0, 20));
        fieldsPane.add(new JLabel("Nome"));
        nomeInput = new JTextField();
        fieldsPane.add(nomeInput);

        fieldsPane.add(new JLabel("Cognome"));
        cognomeInput = new JTextField();
        fieldsPane.add(cognomeInput);

        fieldsPane.add(new JLabel("Indirizzo"));
        indirizzoInput = new JTextField();
        fieldsPane.add(indirizzoInput);

        fieldsPane.add(new JLabel("Telefono"));
        telefonoInput = new JTextField();
        fieldsPane.add(telefonoInput);

        fieldsPane.add(new JLabel("Eta'"));
        etaInput = new JTextField();
        fieldsPane.add(etaInput);

        this.add(fieldsPane, BorderLayout.CENTER);

        // Bottoni
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        salvaBtn = new JButton("Salva");
        annullaBtn = new JButton("Annulla");
        salvaBtn.addActionListener(this);
        annullaBtn.addActionListener(this);
        buttonPane.add(salvaBtn);
        buttonPane.add(annullaBtn);

        // Inserisci bottoni in altro panel per centrarli
        JPanel bottomPane = new JPanel();
        bottomPane.add(buttonPane);
        buttonPane.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.add(bottomPane, BorderLayout.SOUTH);

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                clean();
            }
        });

        this.setVisible(true);
    }

    public static EditPersonaWindow getInstance() {
        if (instance == null) {
            instance = new EditPersonaWindow();
        }
        return instance;
    }

    public void loadPersona(Rubrica rubrica, Persona persona) throws IndexOutOfBoundsException {
        this.setSize(400, 300);
        this.setVisible(true);

        this.rubrica = rubrica;

        if (persona == null)
            return;
        this.personaEditing = persona;

        nomeInput.setText(persona.getNome());
        cognomeInput.setText(persona.getCognome());
        indirizzoInput.setText(persona.getIndirizzo());
        telefonoInput.setText(persona.getTelefono());
        etaInput.setText(String.valueOf(persona.getEta()));
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        if (ev.getSource() == salvaBtn) {
            String nome = nomeInput.getText();
            String cognome = cognomeInput.getText();
            String indirizzo = indirizzoInput.getText();
            String telefono = telefonoInput.getText();
            String etaStr = etaInput.getText();

            if (nome.equals("") || cognome.equals("") || indirizzo.equals("") || telefono.equals("")
                    || etaStr.equals("")) {
                JOptionPane.showMessageDialog(this, "Tutti i campi devono essere compilati");
                return;
            }

            int eta;
            try {
                eta = Integer.valueOf(etaStr);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Eta' deve essere un numero");
                return;
            }

            if (this.personaEditing == null) {
                rubrica.addContatto(nome, cognome, indirizzo, telefono, eta);
            } else {
                rubrica.modifyContatto(personaEditing.getId(), nome, cognome, indirizzo, telefono, eta);
            }
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        } else if (ev.getSource() == annullaBtn) {
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
    }

    private void clean() {
        rubrica = null;
        personaEditing = null;
        nomeInput.setText("");
        cognomeInput.setText("");
        indirizzoInput.setText("");
        telefonoInput.setText("");
        etaInput.setText("");
    }
}
