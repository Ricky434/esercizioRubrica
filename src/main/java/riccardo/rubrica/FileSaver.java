package riccardo.rubrica;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * FileSaver
 */
public class FileSaver {

    private File folder;

    public FileSaver(String path) {
        folder = new File("informazioni");
        folder.mkdir();
    }

    public ArrayList<Persona> personeFromFiles() {
        ArrayList<Persona> persone = new ArrayList<>();

        if (!folder.isDirectory()) {
            System.err.println("'informazioni' is not a folder.");
            System.exit(1);
        }

        // Scorri files in folder "informazioni"
        File[] fileList = folder.listFiles();
        for (int i = 0; i < fileList.length; i++) {
            File file = fileList[i];

            // Utilizza solo files con nome "Persona<id>.txt"
            if (!file.getName().matches("Persona([0-9]+)\\.txt")) {
                continue;
            }

            // Apri file
            Scanner fileReader = null;
            try {
                fileReader = new Scanner(file);
            } catch (FileNotFoundException e) {
                System.err.println("Save file does not exist");
                continue;
            }

            // Se file contiene informazioni su persona non malformate, aggiungi persona
            // altrimenti ignora file
            if (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();

                String[] fields = line.split(";");

                try {
                    int id = Integer.valueOf(file.getName().substring(7).split("\\.")[0]);
                    Persona persona = new Persona(id, fields[0], fields[1], fields[2], fields[3],
                            Integer.valueOf(fields[4]));
                    persone.add(persona);
                } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                    System.err.println("Save file malformed");
                    continue;
                }
            }
            fileReader.close();
        }

        // Ordina lista per id di persona
        persone.sort((x, y) -> x.getId() - y.getId());;
        return persone;
    }

    public void savePersona(Persona persona) {
        String line = String.format("%s;%s;%s;%s;%d", persona.getNome(), persona.getCognome(),
                persona.getIndirizzo(), persona.getTelefono(), persona.getEta());

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(new File(folder, String.format("Persona%d.txt", persona.getId())));
            fileWriter.write(line);
            fileWriter.close();
        } catch (IOException e) {
            System.err.println("Can't open save file");
            System.exit(1);
        }

    }

    public void deletePersona(Persona persona) {
        new File(folder, String.format("Persona%d.txt", persona.getId())).delete();
    }
}
