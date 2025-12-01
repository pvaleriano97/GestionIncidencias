package com.ticketsystem.export;

import com.ticketsystem.model.Incidencia;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ExportarExcel {

    public void historial(List<Incidencia> lista, HttpServletResponse resp) throws IOException {

        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("Historial Incidencias");

        // Cabeceras
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Fecha");
        header.createCell(2).setCellValue("Descripción");
        header.createCell(3).setCellValue("Estado");
        header.createCell(4).setCellValue("Usuario");
        header.createCell(5).setCellValue("Equipo");
        header.createCell(6).setCellValue("Tipo Equipo");
        header.createCell(7).setCellValue("Técnico");

        int row = 1;
        for (Incidencia i : lista) {
            Row r = sheet.createRow(row++);
            r.createCell(0).setCellValue(i.getIdIncidencia());
            r.createCell(1).setCellValue(i.getFechaRegistroStr());
            r.createCell(2).setCellValue(i.getDescripcion());
            r.createCell(3).setCellValue(i.getEstado());
            r.createCell(4).setCellValue(i.getNombreUsuario());
            r.createCell(5).setCellValue(i.getCodigoEquipo());
            r.createCell(6).setCellValue(i.getTipoEquipo());
            r.createCell(7).setCellValue(i.getNombreTecnico() == null ? "—" : i.getNombreTecnico());
        }

        descargar(resp, wb, "historial_incidencias.xlsx");
    }

    private void descargar(HttpServletResponse resp, Workbook wb, String nombre) throws IOException {

        // TIPO DE ARCHIVO CORRECTO
        resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        // NOMBRE REAL DEL ARCHIVO
        resp.setHeader("Content-Disposition", "attachment; filename=\"" + nombre + "\"");

        // Escritura
        wb.write(resp.getOutputStream());
        resp.getOutputStream().flush();

        wb.close();
    }
}
