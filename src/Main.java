
import java.util.Arrays;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 * Java programma om Yahtzee te spelen. Gemaakt voor de leerlijn Inleiding
 * Programmeren van NOVI Hogeschool. 24-06-2019
 *
 * @author Caren Groenhuijzen
 */
public class Main {

    public static byte aantalGooien = 0;

    /*Welkomstscherm, start het spel door beginnen() aan te roepen.*/
    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null, "Welkom bij Yahtzee." + "\n" + "Dit spel"
                + " wordt gespeeld met vijf dobbelstenen. "
                + "In totaal mag je drie keer gooien." + "\n"
                + "Eerst gooi je met alle dobbelstenen en daarna mag je "
                + "twee keer één of meerdere dobbelstenen opnieuw gooien. " + "\n"
                + "Je hoeft niet alle drie de keren te gebruiken, "
                + "stoppen na de eerste of tweede keer gooien kan ook." + "\n"
                + "Je hebt alleen nog een scoreblok nodig om Yahtzee te spelen."
                + "\n" + "Veel succes en plezier!",
                "Yahtzee", JOptionPane.INFORMATION_MESSAGE);
        beginnen();
    }

    /*Vraagt de gebruiker of dobbelstenen gegooid moeten worden.
    Zo ja, roept dobbel() aan en vervolgens toonWorp().*/
    public static void beginnen() {
        int roll = JOptionPane.showConfirmDialog(null, "Dobbelstenen gooien?",
                "Yahtzee", JOptionPane.YES_NO_OPTION);
        int[] worp;
        if (roll == 0) {
            worp = dobbel();
        } else {
            JOptionPane.showMessageDialog(null, "Dobbelstenen niet geworpen, "
                    + "probeer opnieuw.", "Afgebroken", JOptionPane.ERROR_MESSAGE);
            return;
        }
        toonWorp(worp);
    }

    /*Gooit de dobbelstenen door willekeurige getallen tussen
    1 en 6 te genereren en deze op te slaan in een array van integers.
    Deze array wordt teruggegeven.*/
    public static int[] dobbel() {
        byte aantalDobbelstenen = 5;

        Random rand = new Random();
        int[] worp = new int[aantalDobbelstenen];
        for (int i = 0; i < worp.length; i++) {
            worp[i] = rand.nextInt(6) + 1;
        }
        aantalGooien++;
        return worp;
    }

    /*Laat de array worp zien en checkt of er al 3x gegooid is.
    Vraagt of de gebruiker één of meerdere dobbelstenen opnieuw wil gooien,
    indien ja: roept wijzigWorp() aan
    indien nee: einde().*/
    public static void toonWorp(int[] worp) {
        String beurt = Arrays.toString(worp);
        JOptionPane.showMessageDialog(null, "Je hebt " + beurt + " gegooid.",
                "Worp", JOptionPane.INFORMATION_MESSAGE);

        if (aantalGooien < 3) {
            int wijzigen = JOptionPane.showConfirmDialog(null,
                    "Gegooid: " + beurt + "\n"
                    + "Wil je één of meerdere dobbelstenen opnieuw gooien?",
                    "Yahtzee", JOptionPane.YES_NO_OPTION);
            if (wijzigen == 0) {
                aantalGooien++;
                wijzigWorp(worp);
            } else {
                einde(beurt, aantalGooien);
            }
        } else {
            einde(beurt);
        }
    }

    /*Vraagt gebruikersinvoer, verandert de gekozen dobbelstenen in de array worp,
    foutieve invoer wordt opgevangen en er wordt opnieuw naar invoer gevraagd.
    Als laatste wordt toonWorp() aangeroepen.*/
    public static void wijzigWorp(int[] worp) {
        boolean correct;

        do {
            correct = true;
            String beurt = Arrays.toString(worp);
            String input = JOptionPane.showInputDialog(null, "Gegooid: " + beurt
                    + "\n" + "Kies welke dobbelstenen je opnieuw wil gooien. "
                    + "\n" + "(Keuze uit 1-5, gebruik komma's tussen de getallen, "
                    + "bijvoorbeeld: 1,3,5)", "Yahtzee", JOptionPane.PLAIN_MESSAGE);
            if (input == null) {
                einde(beurt, aantalGooien - 1);
                return;
            }

            String[] inputOpnieuw = input.split(",");
            int intOpnieuw;
            for (String opnieuw : inputOpnieuw) {
                try {
                    intOpnieuw = Integer.parseInt(opnieuw);
                } catch (NumberFormatException exception) {
                    intOpnieuw = 0;
                }
                if (intOpnieuw > 5 || intOpnieuw < 1) {
                    JOptionPane.showMessageDialog(null, "Foutieve invoer. " + "\n"
                            + "Getallen moeten tussen 1 en 5 zijn."
                            + " Ook moeten de getallen worden gescheiden met komma's."
                            + "\n" + "Dobbelstenen 1, 2 en 5 opnieuw gooien?" + "\n"
                            + "Correcte invoer: 1,2,5 (dus ook zonder spaties)",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    correct = false;
                    break;
                }
                Random rand = new Random();
                worp[intOpnieuw - 1] = rand.nextInt(6) + 1;
            }
        } while (!correct);
        toonWorp(worp);
    }

    /*Einde beurt na 3 worpen, roept einde(String, int) aan.*/
    public static void einde(String beurt) {
        einde(beurt, 3);
    }

    /*Toont de worp aan het einde van de beurt, samen met het aantal keer gegooid.
    Start indien gewenst een nieuwe beurt door beginnen() aan te roepen.*/
    public static void einde(String beurt, int aantal) {
        JOptionPane.showMessageDialog(null, "Einde beurt: " + beurt + "\n"
                + "Aantal keer geworpen: " + aantal, "Yahtzee",
                JOptionPane.INFORMATION_MESSAGE);

        int volgendeBeurt = JOptionPane.showConfirmDialog(null, "Einde beurt: "
                + beurt + "\n" + "Deze beurt is afgelopen, wil je een nieuwe beurt "
                + "beginnen?", "Yahtzee", JOptionPane.YES_NO_OPTION);
        if (volgendeBeurt == 0) {
            aantalGooien = 0;
            beginnen();
        }
    }
}
