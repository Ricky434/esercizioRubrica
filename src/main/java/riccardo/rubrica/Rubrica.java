package riccardo.rubrica;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

/**
 * Rubrica
 */
public class Rubrica extends AbstractTableModel {

    private ArrayList<Persona> contatti;

    private String[] colonneTabella = {"Nome", "Cognome", "Telefono"};

    public Rubrica() {
        this.contatti = new ArrayList<>();
    }

    public Persona getContatto(int index) throws IndexOutOfBoundsException {
        return contatti.get(index);
    }

    // TODO: check if persona gia esiste?
    public void addContatto(Persona persona) {
        contatti.add(persona);
        this.fireTableDataChanged();
    }

    public void modifyContatto(Persona persona, int index) throws IndexOutOfBoundsException {
        contatti.set(index, persona);
        this.fireTableDataChanged();
    }

    public void removeContatto(int index) throws IndexOutOfBoundsException {
        contatti.remove(index);
        this.fireTableDataChanged();
    }

    @Override
    public int getRowCount() { return contatti.size(); }

    @Override
    public int getColumnCount() { return  colonneTabella.length; }

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
