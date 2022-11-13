import java.io.*;
import java.util.Scanner;

public class Main {
    private static String filePath = "C:\\Users\\Morte\\Desktop\\MyFile.txt";
    public static void main(String[] args) {
        printFileContents();
        //addToFile("Some buffered line");
        addTextAtLine("Weird new buffered write", 2);
        printFileContents();
    }

    private static void addTextAtLine(String text, int lineNumber){
        try (FileReader fr = new FileReader(filePath);
             BufferedReader br = new BufferedReader(fr)) {
            String fileInput = "";
            int countLines = 0;

            String line;
            while ((line = br.readLine()) != null) {

                if (countLines == lineNumber)
                    fileInput += text + "\r\n";
                fileInput += line + "\r\n";
                countLines++;
                System.out.println(line);
            }
            try (FileWriter fw = new FileWriter(filePath);
                 BufferedWriter bw = new BufferedWriter(fw))
            {
                bw.append(fileInput);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addToFile(String text){
        try (FileWriter fw = new FileWriter(filePath, true);
             BufferedWriter bw = new BufferedWriter(fw);)
        {
            bw.append(text + "\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printFileContents(){
        System.out.println("--------");
        try (FileReader fr = new FileReader(filePath);
             BufferedReader br = new BufferedReader(fr);)
        {
            String line;
            while((line = br.readLine())!=null){
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}