import java.io.*;
import java.nio.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.APPEND;

public class Main {
    private static String fileStringPath = "C:\\Users\\Morte\\Desktop\\MyFile.txt";
    private static Path filePath = Path.of(fileStringPath);
    public static void main(String[] args) {
        printFileContents();
        //addToFile("Some nio line");
        addTextAtLine("Weird new nio line write", 4);
        printFileContents();
    }

    private static void addTextAtLine(String text, int lineNumber){
        String fileInput = "";
        try {
            // Read the old file
            List<String> allLines = Files.readAllLines(filePath);
            allLines.add(lineNumber, text);

            // Create new temp file
            Path tempFilePath = Path.of(fileStringPath+"_TEMP");
            Files.createFile(tempFilePath);

            // Add the all lines including new line to temp file
            int countLines = 0;
            for (String line: allLines) {
                Files.write(tempFilePath, (line + "\r\n").getBytes(), APPEND);
                countLines++;
            }

            // Overwrite old file with temp file and delete temp
            Files.copy(tempFilePath, filePath, StandardCopyOption.REPLACE_EXISTING);
            Files.deleteIfExists(tempFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addToFile(String text){
        try{
            Files.write(filePath, text.getBytes(), APPEND);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printFileContents(){
        System.out.println("--------");
        try {
            System.out.println(Files.readString(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}