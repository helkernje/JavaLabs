package com.company;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class Lab5 {
    public static void main(String[] args) throws IOException {
        System.out.println(getMaxWordsString());
    }

    private static String getPath() {
        String path;
        Scanner sc = new Scanner(System.in);
        while (true){
            System.out.print("Путь к файду -> ");
            path = sc.nextLine();
            if(new File(path).exists() && new File(path).isFile()) {
                return path;
            }
            System.out.println("\nУказан несуществующий файл. Повторите попытку.");
        }
    }

    private static String getMaxWordsString() throws IOException {
        Scanner scanner = null;
        int maxLenght = 0;
        String result = "";
        ArrayList<String> lines = new ArrayList<>();
        try {
            scanner = new Scanner(new BufferedReader(new FileReader(getPath())));
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
                if(lines.get(lines.size() - 1).split(" ").length > maxLenght) {
                    maxLenght = lines.get(lines.size() - 1).split(" ").length;
                    result = lines.get(lines.size() - 1);
                }
            }
        }
        finally {
            if(scanner != null)
                scanner.close();
        }
        return result;
    }
}

