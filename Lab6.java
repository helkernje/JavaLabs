package com.company;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


public class Lab6 {

    public static void main(String[] args) throws IOException {
        System.out.println("Кодирование файла");
        Coder.encodeFile(1);
        System.out.println("Декодирование файла");
        Coder.decodeFile(1);
    }
}
class Coder {
    private Coder(){}

    static void encodeFile(int key) throws IOException {
        String path = getPath();
        EncodingFilterWriter out = null;
        InputStreamReader in = null;
        try {
            in = new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8);
            int[] c = new int[1000];
            int i = 0;
            while ((c[i] = in.read()) > -1) {
                i++;
            }
            in.close();
            out = new EncodingFilterWriter(new FileWriter(path));
            for (int j = 0; j < i; j++) {
                out.write(c[j], key);
            }
        }
        finally {
            if (out != null)
                out.close();
            if (in != null)
                in.close();
        }
    }

    static void decodeFile(int key) throws IOException {
        String path = getPath();
        try (DecodingFilterReader in = new DecodingFilterReader(new InputStreamReader(new FileInputStream(path),
                StandardCharsets.UTF_8))) {
            int c;
            while ((c = in.read(key)) > -1) {
                System.out.print((char) c);
            }
        }
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
}

class EncodingFilterWriter extends FilterWriter {


    EncodingFilterWriter(Writer out) {
        super(out);
    }

    void write(int b, int key) throws IOException {
        super.write(b + key);
    }
}

class DecodingFilterReader extends FilterReader {

    DecodingFilterReader(Reader in) {
        super(in);
    }

    int read(int key) throws IOException {
        return super.read() - key;
    }
}