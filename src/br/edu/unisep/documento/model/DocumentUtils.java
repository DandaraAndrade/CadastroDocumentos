package br.edu.unisep.documento.model;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DocumentUtils {
    private static final String FILE_PATH = "documents.csv";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    private static void ensureFileExists() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("Erro ao criar o arquivo: " + e.getMessage());
            }
        }
    }

    public static boolean saveDocument(Document document) {
        ensureFileExists();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(document.toString());
            writer.newLine();
            return true;
        } catch (IOException e) {
            System.err.println("Erro ao salvar o documento: " + e.getMessage());
        }
        return false;
    }

    public static List<Document> loadDocuments() {
        ensureFileExists();
        List<Document> documents = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                documents.add(Document.fromString(line));
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar documentos: " + e.getMessage());
        }
        return documents;
    }
    public static boolean deleteDocument(String title) {
        List<Document> documents = loadDocuments();
        boolean removed = documents.removeIf(doc -> doc.getTitle().equals(title));

        if (removed) {
            saveAllDocuments(documents);
            return true;
        }
        return false;
    }

    public static boolean updateDocument(String title, Document newDocument) {
        List<Document> documents = loadDocuments();

        for (int i = 0; i < documents.size(); i++) {
            Document doc = documents.get(i);
            if (doc.getTitle().equals(title)) {
                documents.set(i, newDocument);
                saveAllDocuments(documents);  // Regrava todos os documentos
                return true;
            }
        }
        return false;
    }

    private static void saveAllDocuments(List<Document> documents) {
        ensureFileExists();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Document doc : documents) {
                writer.write(doc.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar todos os documentos: " + e.getMessage());
        }
    }

    public static boolean isValidDate(String date) {
        try {
            DATE_FORMAT.setLenient(false);
            DATE_FORMAT.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static String documentToString(Document document) {
        return document.getTitle() + ";" + document.getAuthor() + ";" + document.getDate();
    }

    public static Document fromString(String line) {
        String[] parts = line.split(";");
        if (parts.length == 3) {
            return new Document(parts[0], parts[1], parts[2]);
        }
        return null; // Caso a linha não seja válida
    }
}
