package studSystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

import static javax.swing.JOptionPane.showMessageDialog;

public class CSV {
    private static final char defaultSeparator = ';';
    private final String fileName;

    public CSV(String fileName) {
        this.fileName = fileName;
    }

    public static ArrayList<String> parseLine(String cvsLine) {
        ArrayList<String> result = new ArrayList<>();
        if (cvsLine == null || cvsLine.isEmpty()) {
            return null;
        }

        StringBuilder curVal = new StringBuilder();
        char[] chars = cvsLine.toCharArray();
        for (char ch : chars) {
            if (ch == defaultSeparator) {
                result.add(curVal.toString());
                curVal = new StringBuilder();
            } else if (ch == '\r') {
            } else if (ch == '\n') {
                break;
            } else {
                curVal.append(ch);
            }
        }
        result.add(curVal.toString());
        return result;
    }

    public void importFromCSV() {
        try {
            File inputFile = new File(fileName);
            Scanner scanner = new Scanner(inputFile);
            while (scanner.hasNext()) {
                ArrayList<String> line = parseLine(scanner.nextLine());
                assert line != null;
                Lists.manageImport(line);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            showMessageDialog(null, "File not found", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    public void export2CSV() {
        try {
            File output = new File(fileName);
            try {
                output.createNewFile();
                PrintWriter outputWriter = new PrintWriter(fileName, StandardCharsets.UTF_8);
                for (int i = 0; i < Lists.getStudents().size(); i++) {
                    outputWriter.write(Lists.getStudents().get(i).getFirstName() + ";" + Lists.getStudents().get(i).getLastName() + ";" + Lists.getStudents().get(i).getGroup());
                    for (int j = 0; j < Lists.getStudents().get(i).getDates().size(); j++)
                        outputWriter.write(";" + Lists.getStudents().get(i).getDates().get(j));
                    outputWriter.write("\r\n");
                }
                outputWriter.close();
            } catch (IOException e) {
                throw new FileNotFoundException();
            }
        } catch (FileNotFoundException ignored) {
        }

    }
}