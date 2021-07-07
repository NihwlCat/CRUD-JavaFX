package iftm.pedro.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import iftm.pedro.entities.Client;
import iftm.pedro.entities.Glasses;
import iftm.pedro.entities.Order;
import iftm.pedro.services.Service;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.*;
import java.util.List;

public class PDFExport {
    private final Service service;
    private final String path;

    private static final Font helveticaHeaders = FontFactory.getFont(FontFactory.HELVETICA,8f, Font.BOLD, new BaseColor(BaseColor.BLACK.getRGB()));
    private static final Font helveticaRows = FontFactory.getFont(FontFactory.HELVETICA,6f, Font.NORMAL, new BaseColor(BaseColor.BLACK.getRGB()));

    public PDFExport(String path, Service service){
        this.service = service;
        this.path = path + "\\registros.pdf";
    }

    private void getPdfWriter(Document document){
        try {
            PdfWriter.getInstance(document, new FileOutputStream(path));
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setClientsToList(PdfPTable table, List<Client> clients){
        clients.forEach(c -> {
            table.addCell(new PdfPCell(new Paragraph(c.getClientCpf(),helveticaRows)));
            table.addCell(new PdfPCell(new Paragraph(c.getName(),helveticaRows)));
            table.addCell(new PdfPCell(new Paragraph(c.getEmail(),helveticaRows)));
            table.addCell(new PdfPCell(new Paragraph(c.getAddress(),helveticaRows)));
            table.addCell(new PdfPCell(new Paragraph(c.getPhone(),helveticaRows)));
            table.addCell(new PdfPCell(new Paragraph(c.getInstagram(),helveticaRows)));
            table.addCell(new PdfPCell(new Paragraph((c.getOrder() == null) ? "" : c.getOrder().getCod(),helveticaRows)));
        });
    }

    private void setOrderToList(PdfPTable table, List<Order> orders){
        orders.forEach(o -> {
            table.addCell(new PdfPCell(new Paragraph(o.getCod(),helveticaRows)));
            table.addCell(new PdfPCell(new Paragraph(Utils.formatDate(o.getOrderDate()),helveticaRows)));
            table.addCell(new PdfPCell(new Paragraph("R$ "+ o.getPrice(),helveticaRows)));
            table.addCell(new PdfPCell(new Paragraph("R$ " + o.getDiscount(),helveticaRows)));
            table.addCell(new PdfPCell(new Paragraph(o.getClient().getName(),helveticaRows)));
            table.addCell(new PdfPCell(new Paragraph(o.getGlasses().getId(),helveticaRows)));
        });
    }

    private void setGlassesToList(PdfPTable table, List<Glasses> glasses){

        glasses.forEach(g -> {
            table.addCell(new PdfPCell(new Paragraph(g.getId(),helveticaRows)));
            table.addCell(new PdfPCell(new Paragraph(g.getImageLink(),helveticaRows)));
            table.addCell(new PdfPCell(new Paragraph(g.getPrescription(),helveticaRows)));
        });


    }

    private void addCellHeader(PdfPTable table, String text){
        PdfPCell cell = new PdfPCell(new Paragraph(text,helveticaHeaders));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell);
    }

    private List<Element> getElements() {

        Font helveticaTitle = FontFactory.getFont(FontFactory.HELVETICA,12f,Font.BOLD,new BaseColor(BaseColor.BLUE.getRGB()));
        Font helveticaBody = FontFactory.getFont(FontFactory.HELVETICA,10f, Font.NORMAL, new BaseColor(BaseColor.BLACK.getRGB()));
        List<Element> elements = new ArrayList<>();

        Paragraph mainTitle = new Paragraph("RELATÓRIO DE INFORMAÇÕES",helveticaTitle);
        mainTitle.setAlignment(Element.ALIGN_CENTER);
        mainTitle.setSpacingAfter(80f);
        elements.add(mainTitle);

        Paragraph nameTile = new Paragraph("ALUNO: Pedro Rafael Pereira",helveticaBody);
        nameTile.setAlignment(Element.ALIGN_RIGHT);
        elements.add(nameTile);

        Paragraph classTile = new Paragraph("Programação orientada a objetos e visual",helveticaBody);
        classTile.setAlignment(Element.ALIGN_RIGHT);
        classTile.setSpacingAfter(30f);
        elements.add(classTile);

        elements.add(new LineSeparator());

        Paragraph clientsTitle = new Paragraph("Lista de Clientes",helveticaTitle);
        clientsTitle.setSpacingAfter(20f);
        clientsTitle.setSpacingBefore(15f);
        elements.add(clientsTitle);


        // Client table

        PdfPTable clientTable = new PdfPTable(7);
        clientTable.setTotalWidth(550);
        clientTable.setLockedWidth(true);
        clientTable.setHorizontalAlignment(Element.ALIGN_CENTER);


        addCellHeader(clientTable,"CPF");
        addCellHeader(clientTable,"NOME");
        addCellHeader(clientTable,"EMAIL");
        addCellHeader(clientTable,"ENDEREÇO");
        addCellHeader(clientTable,"TELEFONE");
        addCellHeader(clientTable,"INSTAGRAM");
        addCellHeader(clientTable,"PEDIDO");
        setClientsToList(clientTable,service.getClients());
        elements.add(clientTable);

        Paragraph glassesTitle = new Paragraph("Lista de Óculos",helveticaTitle);
        glassesTitle.setSpacingAfter(20f);
        glassesTitle.setSpacingBefore(15f);
        elements.add(glassesTitle);

        // Glasses table

        PdfPTable glassesTable = new PdfPTable(3);
        glassesTable.setTotalWidth(550);
        glassesTable.setLockedWidth(true);
        glassesTable.setHorizontalAlignment(Element.ALIGN_CENTER);

        try {
            glassesTable.setWidths(new int[]{50, 150, 350});
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        addCellHeader(glassesTable,"ID");
        addCellHeader(glassesTable,"LINK");
        addCellHeader(glassesTable,"PRESCRIÇÃO");
        setGlassesToList(glassesTable,service.getGlasses());
        elements.add(glassesTable);

        Paragraph orderTitle = new Paragraph("Lista de Pedidos",helveticaTitle);
        orderTitle.setSpacingAfter(20f);
        orderTitle.setSpacingBefore(15f);
        elements.add(orderTitle);

        // Order table

        PdfPTable orderTable = new PdfPTable(6);
        orderTable.setTotalWidth(550);
        orderTable.setLockedWidth(true);
        orderTable.setHorizontalAlignment(Element.ALIGN_CENTER);

        addCellHeader(orderTable, "CÓDIGO");
        addCellHeader(orderTable, "DATA");
        addCellHeader(orderTable, "PREÇO");
        addCellHeader(orderTable, "DESCONTO");
        addCellHeader(orderTable, "CLIENTE");
        addCellHeader(orderTable, "ÓCULOS");
        setOrderToList(orderTable,service.getOrders());
        elements.add(orderTable);

        Paragraph endTile = new Paragraph("Gerado em: " + Utils.formatDate(new Date()),helveticaBody);
        endTile.setAlignment(Element.ALIGN_RIGHT);
        endTile.setSpacingBefore(20f);
        elements.add(endTile);

        return elements;
    }

    public void exportDocument(){
        Document document = new Document();
        getPdfWriter(document);

        document.open();

        getElements().forEach(elem -> {
            try {
                document.add(elem);
            } catch (DocumentException e) {
                   e.printStackTrace();
            }
        });
        document.close();
    }
}
