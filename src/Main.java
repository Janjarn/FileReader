import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static String filePath = "C:\\Users\\Morte\\Desktop\\MyFile.txt";
    public static void main(String[] args) {
        printFileContents();
        //addToFile("Some line");
        addTextAtLine("Weird new one", 2);
        printFileContents();
    }

    private static void addTextAtLine(String text, int lineNumber){
        FileReader fr = null;
        try {
            String fileInput = "";
            fr = new FileReader(filePath);
            Scanner scanner = new Scanner(fr);
            int countLines= 0;
            while(scanner.hasNext()) {
                fileInput += scanner.nextLine() + "\r\n";
                countLines++;
                if (countLines==lineNumber)
                    fileInput+=text + "\r\n";
            }
            fr.close();
            FileWriter fw = new FileWriter(filePath);
            fw.append(fileInput);
            fw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void addToFile(String text){
        try {
            FileWriter fw = new FileWriter(filePath, true);
            fw.append(text + "\r\n");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printFileContents(){
        System.out.println("--------");
        try {
            FileReader fr = new FileReader(filePath);
            Scanner scanner = new Scanner(fr);
            while(scanner.hasNext()){
                System.out.println(scanner.nextLine());
            }
            fr.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}