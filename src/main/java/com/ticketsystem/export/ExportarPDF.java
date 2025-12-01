package com.ticketsystem.export;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.ticketsystem.model.Incidencia;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ExportarPDF {

    public void historial(List<Incidencia> lista, HttpServletResponse resp) throws IOException {

        resp.setContentType("application/pdf");
        resp.setHeader("Content-Disposition", "attachment; filename=\"historial_incidencias.pdf\"");

        Document doc = new Document(PageSize.A4.rotate());

        try {
            PdfWriter.getInstance(doc, resp.getOutputStream());
            doc.open();

            // Título
            Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph titulo = new Paragraph("HISTORIAL DE INCIDENCIAS", fontTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            doc.add(titulo);
            doc.add(new Paragraph("\n"));

            // Tabla
            PdfPTable table = new PdfPTable(8);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{2f, 4f, 7f, 4f, 5f, 4f, 4f, 4f});

            addHeader(table, "ID");
            addHeader(table, "Fecha");
            addHeader(table, "Descripción");
            addHeader(table, "Estado");
            addHeader(table, "Usuario");
            addHeader(table, "Equipo");
            addHeader(table, "Tipo Equipo");
            addHeader(table, "Técnico");

            for (Incidencia h : lista) {
                table.addCell(String.valueOf(h.getIdIncidencia()));
                table.addCell(h.getFechaRegistroStr());
                table.addCell(h.getDescripcion());
                table.addCell(h.getEstado());
                table.addCell(h.getNombreUsuario());
                table.addCell(h.getCodigoEquipo());
                table.addCell(h.getTipoEquipo());
                table.addCell(h.getNombreTecnico() == null ? "—" : h.getNombreTecnico());
            }

            doc.add(table);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            doc.close(); // OBLIGATORIO
            resp.getOutputStream().flush();
        }
    }

    private void addHeader(PdfPTable table, String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text,
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE)));

        cell.setBackgroundColor(BaseColor.GRAY);
        cell.setPadding(7);

        table.addCell(cell);
    }
}
