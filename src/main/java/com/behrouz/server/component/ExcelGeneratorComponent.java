package com.behrouz.server.component;

import com.behrouz.server.rest.response.TicketDetailRestResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Component
public class ExcelGeneratorComponent {
    private static final String FONT_LABEL = "B Nazanin";

    public byte[] createTicketExcel(List<TicketDetailRestResponse> response) {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        Workbook workbook = new XSSFWorkbook();
        writeTicketExcl( workbook, response );
        try {
            workbook.write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toByteArray();
    }

    public void writeTicketExcl(Workbook workbook, List<TicketDetailRestResponse> tickets){
        Sheet sheet = workbook.createSheet("لیست");
        Font headerBoldFont = workbook.createFont();
        headerBoldFont.setFontHeightInPoints((short) 10);
        headerBoldFont.setColor(IndexedColors.BLACK.getIndex());
        headerBoldFont.setFontName(FONT_LABEL);

        CellStyle headerDetailStyle = workbook.createCellStyle();
        headerDetailStyle.setFont(headerBoldFont);
        headerDetailStyle.setAlignment(HorizontalAlignment.CENTER);
        headerDetailStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        CellStyle valueStyle = workbook.createCellStyle();
        valueStyle.setAlignment(HorizontalAlignment.CENTER);

        Row row_header = sheet.createRow(0);
        int col_head = 0;

//        createCell(row_header, col_head++, headerDetailStyle, "پروژه");
        createCell(row_header, col_head++, headerDetailStyle, "کدرهگیری");
        createCell(row_header, col_head++, headerDetailStyle, "موضوع");
        createCell(row_header, col_head++, headerDetailStyle, "تاریخ ثبت");
        createCell(row_header, col_head++, headerDetailStyle, "تاریخ ثبت آخرین پیام");
        createCell(row_header, col_head++, headerDetailStyle, "اهمیت");
        createCell(row_header, col_head, headerDetailStyle, "وضعیت تیکت");

        int row_data_counter = 1;
        for (TicketDetailRestResponse c : tickets) {
            Row row_data = sheet.createRow(row_data_counter ++);
            int col_data = 0;
//            createCell(row_data, col_data ++, headerDetailStyle, c.getProjectName());
            createCell(row_data, col_data ++, headerDetailStyle, c.getTrackingCode());
            createCell(row_data, col_data ++, headerDetailStyle, c.getTicketSubject());
            createCell(row_data, col_data ++, headerDetailStyle, c.getInsertDateStr());
            createCell(row_data, col_data ++, headerDetailStyle, c.getLastTicketMessageDateStr());
            createCell(row_data, col_data ++, headerDetailStyle, c.getImportance().getName());
            createCell(row_data, col_data, headerDetailStyle, c.isClosed() ? "بسته شده" : "باز" );
        }
    }

    public Cell createCell(Row row, int col_data, CellStyle style, String colData){
        Cell field = row.createCell(col_data);
        field.setCellStyle(style);
        field.setCellValue(colData);
        return field;
    }
}
