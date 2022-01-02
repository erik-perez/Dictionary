import java.util.ArrayList;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SubDictionary {
    static String word = null;
    static Scanner keyboard = new Scanner(System.in);
    static ArrayList<String> SubOriginal = new ArrayList<String>();
    static ArrayList<String> SubFinal = new ArrayList<String>();
    static String[] removableCharacters = {"?", ":", "�S", "�M", "'M", "'S", ",", "=", ";", "!", ".", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    static String[] alphabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    public static void inputToDictionary(String inputFileName) {
        try {
            Scanner input = new Scanner(new FileInputStream(inputFileName));
            while (input.hasNext()) {
                SubOriginal.add(input.next().toUpperCase());
            }

            for (int j = 0; j < SubOriginal.size(); j++) {
                word = SubOriginal.get(j);
                for (int i = 0; i < removableCharacters.length; i++) {
                    if (word.contains(removableCharacters[i])) {
                        word = word.substring(0, word.indexOf(removableCharacters[i]));
                    }
                }
                SubFinal.add(word);
            }

            int current = 0;
            while (current < SubFinal.size()) {
                int j = 0;
                boolean isRemoved = false;
                while (j < current) {
                    if (SubFinal.get(current).equals(SubFinal.get(j))) {
                        SubFinal.remove(current);
                        isRemoved = true;
                        break;
                    } else j++;
                }
                if (!isRemoved) current++;

                for (int i = 0; i < SubFinal.size(); i++) {
                    if (SubFinal.get(i).length() == 0) {
                        SubFinal.remove(i);
                    }
                }

                for (int i = 0; i < SubFinal.size(); i++) {
                    if (SubFinal.get(i).length() == 1) {
                        if (SubFinal.get(i) != "A" || SubFinal.get(i) != "I") {
                            SubFinal.remove(i);
                            break;
                        }
                    }
                }

                SubFinal.sort(String::compareTo);

            }

        } catch (FileNotFoundException e) {
            System.out.println("Unable to find input file. Program is stopping.");
            System.exit(0);
            e.printStackTrace();
        }
    }

    public static void main (String[]args){

            System.out.print("Welcome to the sub dictionary program, input the name of a .txt file and this program will find every word and speerate them alphabetically: ");
            inputToDictionary("/Users/erik/JavaProjects/Dictionary/PersonOfTheCentury.txt");
            displaySubDictionary();
            SubDictionaryToFile();
            System.out.println("\nProgram is now over, the original file text has been converted to a sub dictionary with the file name: subDictionary.txt");
        }

    public static void displaySubDictionary () {

        int size = SubFinal.size();
        System.out.println("The document produced this sub-dictionary, which includes " + SubFinal.size() + " entries.\n");
        for (int i = 0; i < alphabet.length; i++) {
            System.out.println(alphabet[i] + "\n==");
            for (int j = 0; j < SubFinal.size(); j++) {
                if (SubFinal.get(j).startsWith(alphabet[i])) {
                    System.out.println(SubFinal.get(j));
                }
            }
            System.out.println("\n");
        }
    }

    public static void SubDictionaryToFile () {
        /*
         * these loops format the output so that it prints a letter and prints all words starting with that letter
         */
        try {
            PrintWriter outputStream = new PrintWriter(new FileOutputStream("subDictionary.txt", false));
            outputStream.println("The document produced this sub-dictionary, which includes " + SubFinal.size() + " entries.\n");
            for (int i = 0; i < alphabet.length; i++) {
                outputStream.println(alphabet[i] + "\n==");
                for (int j = 0; j < SubFinal.size(); j++) {
                    if (SubFinal.get(j).startsWith(alphabet[i])) {
                        outputStream.println(SubFinal.get(j));
                    }
                }
                outputStream.println("\n");
                outputStream.flush();
            }
            outputStream.close();
        } catch (FileNotFoundException e) {
            System.out.println("File was not able to be created/opened!");
        }
    }

}