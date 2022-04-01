package studSystem;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.ZoneId;
import java.util.ArrayList;

import static javax.swing.JOptionPane.showMessageDialog;

public class Excel {
    private final String fileName;

    public Excel(String fileName) {
        this.fileName = fileName;
    }

    public void importFromXLSX() {
        try {
            FileInputStream file = new FileInputStream(fileName);
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                ArrayList<String> data = new ArrayList<>();
                for (Cell cell : row)
                    switch (cell.getCellTypeEnum()) {
                        case STRING:
                            data.add(cell.getRichStringCellValue().getString());
                            break;
                        case NUMERIC:
                            if (DateUtil.isCellDateFormatted(cell))
                                data.add(cell.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString());
                            else
                                data.add(cell.getNumericCellValue() + "");
                    }
                Lists.manageImport(data);
            }
        } catch (Exception fExc) {
            showMessageDialog(null, "File not found", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    public void importFromXLS() {
        try {
            FileInputStream file = new FileInputStream(fileName);
            Workbook workbook = new HSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                ArrayList<String> data = new ArrayList<>();
                for (Cell cell : row)
                    switch (cell.getCellTypeEnum()) {
                        case STRING:
                            data.add(cell.getRichStringCellValue().getString());
                            break;
                        case NUMERIC:
                            if (DateUtil.isCellDateFormatted(cell))
                                data.add(cell.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString());
                            else
                                data.add(cell.getNumericCellValue() + "");
                    }
                Lists.manageImport(data);
            }
        } catch (Exception fExc) {
            showMessageDialog(null, "File not found", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    public void export2XLS() {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("Student list");
        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);
        for (int i = 0; i < Lists.getStudents().size(); i++) {
            Row row = sheet.createRow(i);
            Cell cell = row.createCell(0);
            cell.setCellValue(Lists.getStudents().get(i).getFirstName());
            cell.setCellStyle(style);
            cell = row.createCell(1);
            cell.setCellValue(Lists.getStudents().get(i).getLastName());
            cell.setCellStyle(style);
            cell = row.createCell(2);
            cell.setCellValue(Lists.getStudents().get(i).getGroup());
            cell.setCellStyle(style);
            int columnIndex = 3;
            for (int j = 0; j < Lists.getStudents().get(i).getDates().size(); j++) {
                cell = row.createCell(columnIndex++);
                cell.setCellValue(Lists.getStudents().get(i).getDates().get(j));
                cell.setCellStyle(style);
            }
        }
        try {
            FileOutputStream outputStream = new FileOutputStream(fileName);
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        } catch (Exception e) {
            showMessageDialog(null, "There was an error while exporting student list.", "File export error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    public void export2XLSX() {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Student list");
        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);
        for (int i = 0; i < Lists.getStudents().size(); i++) {
            Row row = sheet.createRow(i);
            Cell cell = row.createCell(0);
            cell.setCellValue(Lists.getStudents().get(i).getFirstName());
            cell.setCellStyle(style);
            cell = row.createCell(1);
            cell.setCellValue(Lists.getStudents().get(i).getLastName());
            cell.setCellStyle(style);
            cell = row.createCell(2);
            cell.setCellValue(Lists.getStudents().get(i).getGroup());
            cell.setCellStyle(style);
            int columnIndex = 3;
            for (int j = 0; j < Lists.getStudents().get(i).getDates().size(); j++) {
                cell = row.createCell(columnIndex++);
                cell.setCellValue(Lists.getStudents().get(i).getDates().get(j));
                cell.setCellStyle(style);
            }
        }
        try {
            FileOutputStream outputStream = new FileOutputStream(fileName);
            workbook.write(outputStream);
            workbook.close();
        } catch (Exception e) {
            showMessageDialog(null, "There was an error while exporting student list.", "File export error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }
}