package com.example.proyectobolsaempleo.logic;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@Service("reporteService")
public class ServiceReporte {

    @Autowired
    private ServicePuesto servicePuesto;

    public byte[] generarReportePuestosPorMes(int mes, int anio) throws Exception {

        List<Puesto> puestos = servicePuesto.getPuestosPorMes(mes, anio);

        // Nombre del mes en español
        String nombreMes = Month.of(mes).getDisplayName(TextStyle.FULL, new Locale("es", "CR"));
        nombreMes = nombreMes.substring(0, 1).toUpperCase() + nombreMes.substring(1);

        // Colores
        DeviceRgb azulOscuro = new DeviceRgb(30, 60, 114);
        DeviceRgb azulClaro  = new DeviceRgb(220, 230, 245);
        DeviceRgb blanco     = new DeviceRgb(255, 255, 255);
        DeviceRgb grisClaro  = new DeviceRgb(245, 245, 245);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfDocument pdf = new PdfDocument(new PdfWriter(baos));
        Document doc   = new Document(pdf);
        doc.setMargins(40, 40, 40, 40);

        // ── Título ───────────────────────────────────────────────────────────
        doc.add(new Paragraph("Bolsa de Empleo")
                .setFontSize(22).setBold()
                .setFontColor(azulOscuro)
                .setTextAlignment(TextAlignment.CENTER));

        doc.add(new Paragraph("Reporte de Puestos — " + nombreMes + " " + anio)
                .setFontSize(14)
                .setFontColor(ColorConstants.DARK_GRAY)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(20));

        doc.add(new Paragraph("Total de puestos publicados: " + puestos.size())
                .setFontSize(11).setItalic()
                .setMarginBottom(10));

        // ── Tabla ────────────────────────────────────────────────────────────
        if (puestos.isEmpty()) {
            doc.add(new Paragraph("No se encontraron puestos publicados en este período.")
                    .setItalic()
                    .setFontColor(ColorConstants.GRAY)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginTop(30));
        } else {
            for (int i = 0; i < puestos.size(); i++) {
                Puesto p = puestos.get(i);
                DeviceRgb fondoFila = (i % 2 == 0) ? blanco : azulClaro;

                // Tabla por cada puesto (una fila con sus datos)
                float[] anchos = {40f, 180f, 90f, 70f, 120f, 45f, 85f};
                Table tabla = new Table(UnitValue.createPointArray(anchos));
                tabla.setWidth(UnitValue.createPercentValue(100));
                tabla.setMarginBottom(2);

                // Encabezados solo en el primer puesto
                if (i == 0) {
                    String[] headers = {"ID", "Descripción", "Salario (₡)", "Tipo", "Empresa", "Activo", "Fecha"};
                    for (String h : headers) {
                        tabla.addHeaderCell(
                                new Cell().add(new Paragraph(h).setBold().setFontColor(blanco))
                                        .setBackgroundColor(azulOscuro)
                                        .setTextAlignment(TextAlignment.CENTER)
                                        .setPadding(6));
                    }
                }

                // Fila del puesto
                tabla.addCell(celda(String.valueOf(p.getId()), fondoFila, TextAlignment.CENTER));
                tabla.addCell(celda(truncar(p.getDescripcion(), 60), fondoFila, TextAlignment.LEFT));
                tabla.addCell(celda(p.getSalario() != null ? String.format("%,.0f", p.getSalario()) : "—", fondoFila, TextAlignment.RIGHT));
                tabla.addCell(celda(nvl(p.getTipoPublicacion()), fondoFila, TextAlignment.CENTER));
                tabla.addCell(celda(p.getIdEmpresa() != null ? nvl(p.getIdEmpresa().getNombre()) : "—", fondoFila, TextAlignment.LEFT));
                tabla.addCell(celda(Boolean.TRUE.equals(p.getActivo()) ? "Sí" : "No", fondoFila, TextAlignment.CENTER));
                tabla.addCell(celda(p.getFechaPublicacion() != null ? p.getFechaPublicacion().toString() : "—", fondoFila, TextAlignment.CENTER));

                doc.add(tabla);

                // ── Fila de requisitos debajo del puesto ─────────────────────
                List<PuestoRequisito> requisitos = servicePuesto.getRequisitosDePuesto(p.getId());

                Table tablaReq = new Table(new float[]{515f});
                tablaReq.setWidth(UnitValue.createPercentValue(100));
                tablaReq.setMarginBottom(12);

                if (requisitos.isEmpty()) {
                    tablaReq.addCell(
                            new Cell().add(new Paragraph("   Sin requisitos registrados.")
                                            .setFontSize(8).setItalic().setFontColor(ColorConstants.GRAY))
                                    .setBackgroundColor(grisClaro)
                                    .setPaddingLeft(10).setPaddingTop(3).setPaddingBottom(3));
                } else {
                    StringBuilder sb = new StringBuilder("   Requisitos: ");
                    for (int j = 0; j < requisitos.size(); j++) {
                        PuestoRequisito pr = requisitos.get(j);
                        String nombreCar = pr.getIdCaracteristica() != null
                                ? pr.getIdCaracteristica().getNombre() : "—";
                        sb.append(nombreCar).append(" (Nivel ").append(pr.getNivel()).append(")");
                        if (j < requisitos.size() - 1) sb.append(",  ");
                    }
                    tablaReq.addCell(
                            new Cell().add(new Paragraph(sb.toString()).setFontSize(8))
                                    .setBackgroundColor(grisClaro)
                                    .setPaddingLeft(10).setPaddingTop(3).setPaddingBottom(3));
                }

                doc.add(tablaReq);
            }
        }

        // ── Pie de página ────────────────────────────────────────────────────
        doc.add(new Paragraph("Generado automáticamente por el sistema de Bolsa de Empleo")
                .setFontSize(8)
                .setFontColor(ColorConstants.GRAY)
                .setTextAlignment(TextAlignment.RIGHT)
                .setMarginTop(20));

        doc.close();
        return baos.toByteArray();
    }

    // ── Helpers ──────────────────────────────────────────────────────────────

    private Cell celda(String texto, DeviceRgb fondo, TextAlignment alineacion) {
        return new Cell()
                .add(new Paragraph(texto).setFontSize(9))
                .setBackgroundColor(fondo)
                .setTextAlignment(alineacion)
                .setPaddingTop(4).setPaddingBottom(4)
                .setPaddingLeft(5).setPaddingRight(5);
    }

    private String nvl(String valor) {
        return valor != null ? valor : "—";
    }

    private String truncar(String texto, int max) {
        if (texto == null) return "—";
        return texto.length() > max ? texto.substring(0, max) + "…" : texto;
    }
}