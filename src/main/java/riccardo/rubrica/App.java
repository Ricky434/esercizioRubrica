package riccardo.rubrica;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        Rubrica rubrica = new Rubrica();
        MainWindow mainWindow = new MainWindow(rubrica);
    }
}


// Finestra base:
//  lista persone (nome, cognome, telefono)
//  bottoni in basso:
//      nuovo -> finestra edit persona, campi vuoti
//      modifica -> finestra edit (errore se nessuno selezionato)
//      elimina -> confirm dialog (errore se nessuno selezionato)
//
// Finestra edit:
//  Campi persona
//  Bottoni:
//      salva
//      annulla
//
//
//      SwingUtilities.updateComponentTreeUI(myJFrame);
