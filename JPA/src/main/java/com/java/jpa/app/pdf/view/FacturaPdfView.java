package com.java.jpa.app.pdf.view;

import java.awt.Color;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.java.jpa.app.models.entity.Factura;
import com.java.jpa.app.models.entity.ItemFactura;
import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
/**
 * Revisar documentacion de IText
 * @author amaterasu1906
 *
 */

@Component("factura/ver")
public class FacturaPdfView extends AbstractPdfView{

	@Autowired
	private MessageSource messageSource;
	@Autowired
	private LocaleResolver localeResolver;
	
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		Factura factura = (Factura) model.get("factura");
//		Primera forma
		Locale locale = localeResolver.resolveLocale(request);
//		Segunda forma
		MessageSourceAccessor messages = getMessageSourceAccessor();
		PdfPTable tabla = new PdfPTable(1);
		tabla.setSpacingAfter(20);
		
		PdfPCell cell = null;
		cell = new PdfPCell(new Phrase("Datos del cliente"));
		cell.setBackgroundColor(new Color(184, 218, 255));
		cell.setPadding(8f);
		tabla.addCell(cell);
		
		tabla.addCell(factura.getCliente().getNombre().concat(" ").concat(factura.getCliente().getApellido()));
		tabla.addCell(factura.getCliente().getEmail());

		PdfPTable tabla2 = new PdfPTable(1);
		tabla2.setSpacingAfter(20);
		
		cell = new PdfPCell(new Phrase("Datos de la factura"));
		cell.setBackgroundColor(new Color(192, 230, 144));
		cell.setPadding(8f);
		tabla2.addCell(cell);
		
		tabla2.addCell("Folio: ".concat(factura.getId().toString()));
		tabla2.addCell("Descripción: ".concat(factura.getDescripcion()));
		tabla2.addCell("Fecha: ".concat(factura.getCreateAt().toString()));
		
		PdfPTable tabla3 = new PdfPTable(4);
		tabla3.setWidths(new float[] {2.5f, 1, 1, 1}); //tamaño de la columna
		tabla3.addCell(messageSource.getMessage("text.factura.producto.producto", null, locale));
		tabla3.addCell(messageSource.getMessage("text.factura.producto.precio", null, locale));
		tabla3.addCell(messageSource.getMessage("text.factura.producto.cantidad", null, locale));
		tabla3.addCell(messages.getMessage("text.factura.producto.total"));
		for (ItemFactura item : factura.getItems()) {
			tabla3.addCell(item.getProducto().getNombre());
			tabla3.addCell(item.getProducto().getPrecio().toString());
			
			cell = new PdfPCell(new Phrase(item.getCantidad().toString()));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			tabla3.addCell(cell);
			
			tabla3.addCell(item.calcularImporte().toString());
		}
		cell = new PdfPCell( new Phrase("Total: "));
		cell.setColspan(3);
		cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		tabla3.addCell(cell);
		tabla3.addCell(factura.getTotal().toString());
		
		document.add(tabla);
		document.add(tabla2);
		document.add(tabla3);
	}

}
