package riccardo.rubrica;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.table.AbstractTableModel;

/**
 * Rubrica
 */
public class Rubrica extends AbstractTableModel {

    private ArrayList<Persona> contatti;
    private File saveFile;

    private String[] colonneTabella = { "Nome", "Cognome", "Telefono" };

    public Rubrica() {
        this.contatti = new ArrayList<>();

        this.saveFile = new File("informazioni.txt");

        loadFromFile();
    }

    public Persona getContatto(int index) throws IndexOutOfBoundsException {
        return contatti.get(index);
    }

    // TODO: check if persona gia esiste?
    public void addContatto(Persona persona) {
        contatti.add(persona);
        saveToFile();
        this.fireTableDataChanged();
    }

    public void modifyContatto(Persona persona, int index) throws IndexOutOfBoundsException {
        contatti.set(index, persona);
        saveToFile();
        this.fireTableDataChanged();
    }

    public void removeContatto(int index) throws IndexOutOfBoundsException {
        contatti.remove(index);
        saveToFile();
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

    private void saveToFile() {
        ArrayList<String> lines = new ArrayList<>();
        for (int i = 0; i < contatti.size(); i++) {
            Persona persona = contatti.get(i);
            String line = String.format("%s;%s;%s;%s;%d", persona.getNome(), persona.getCognome(),
                    persona.getIndirizzo(), persona.getTelefono(), persona.getEta());
            lines.add(line);
        }

        FileWriter fileWriter = null;
        try {
            saveFile.createNewFile();
            fileWriter = new FileWriter(saveFile);
            fileWriter.write(String.join("\n", lines));
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Can't open save file");
            System.exit(1);
        }

    }

    private void loadFromFile() {
        Scanner fileReader = null;
        try {
            fileReader = new Scanner(saveFile);
        } catch (FileNotFoundException e) {
            System.out.println("Save file does not exist");
            return;
        }

        while (fileReader.hasNextLine()) {
            String line = fileReader.nextLine();

            String[] fields = line.split(";");

            try {
                Persona contatto = new Persona(fields[0], fields[1], fields[2], fields[3], Integer.valueOf(fields[4]));
                contatti.add(contatto);
            } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                System.out.println("Save file malformed");
                System.exit(1);
            }
        }

        fileReader.close();
    }
}
