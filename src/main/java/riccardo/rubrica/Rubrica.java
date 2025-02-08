package riccardo.rubrica;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import javax.swing.table.AbstractTableModel;

/**
 * Rubrica
 */
public class Rubrica extends AbstractTableModel {

    private ArrayList<Persona> contatti;
    private FileSaver fileSaver;

    private int latestId;

    private String[] colonneTabella = { "Nome", "Cognome", "Telefono" };

    public Rubrica() {
        this.fileSaver = new FileSaver("informazioni");
        this.latestId = 0;

        contatti = fileSaver.personeFromFiles();
        // Salva l'id piu grande trovato tra i contatti salvati,
        // l'id dei nuovi contatti incrementera' a partire da questo
        contatti.stream().forEach(x -> {latestId = x.getId() > latestId ? x.getId() : latestId;});
    }

    public Persona getContatto(int index) throws IndexOutOfBoundsException {
        return contatti.get(index);
    }

    public void addContatto(String nome, String cognome, String indirizzo, String telefono, int eta) {
        Persona persona = new Persona(++latestId, nome, cognome, indirizzo, telefono, eta);
        contatti.add(persona);
        fileSaver.savePersona(persona);
        this.fireTableDataChanged();
    }

    public void modifyContatto(int id, String nome, String cognome, String indirizzo, String telefono, int eta)
            throws NoSuchElementException {
        Persona persona = contatti.stream().filter(x -> x.getId() == id).findFirst().get();
        persona.setNome(nome);
        persona.setCognome(cognome);
        persona.setIndirizzo(indirizzo);
        persona.setTelefono(telefono);
        persona.setEta(eta);

        fileSaver.savePersona(persona);
        this.fireTableDataChanged();
    }

    public void removeContatto(Persona persona) throws IndexOutOfBoundsException {
        fileSaver.deletePersona(persona);
        contatti.remove(contatti.indexOf(persona));
        this.fireTableDataChanged();
    }

    public void removeContatto(int index) throws IndexOutOfBoundsException {
        fileSaver.deletePersona(this.getContatto(index));
        contatti.remove(index);
        this.fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return contatti.size();
    }

    @Override
    public int getColumnCount() {
        return colonneTabella.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        if (columnIndex < 0 || columnIndex >= colonneTabella.length)
            return "";

        return colonneTabella[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) throws IndexOutOfBoundsException {
        switch (columnIndex) {
            case 0:
                return contatti.get(rowIndex).getNome();
            case 1:
                return contatti.get(rowIndex).getCognome();
            case 2:
                return contatti.get(rowIndex).getTelefono();
            default:
                throw new IndexOutOfBoundsException();
        }
    }
}
