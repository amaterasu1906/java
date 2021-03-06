package com.java.jpa.app.view.xlsx;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.java.jpa.app.models.entity.Factura;
import com.java.jpa.app.models.entity.ItemFactura;

@Component("factura/ver.xlsx")
public class FacturaXlsxView extends AbstractXlsxView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setHeader("Content-Disposition", "attachment; filename=\"factura_view.xlsx\"");
		Factura factura = (Factura) model.get("factura");
		Sheet sheet = workbook.createSheet("Factura");
		MessageSourceAccessor messages = getMessageSourceAccessor();
		Row row = sheet.createRow(0);
		Cell cell = row.createCell(0);
		cell.setCellValue("Datos del cliente");
		row = sheet.createRow(1);
		cell = row.createCell(0);
		cell.setCellValue(factura.getCliente().getNombre() + " " + factura.getCliente().getApellido());
		
		row = sheet.createRow(2);
		cell = row.createCell(0);
		cell.setCellValue(factura.getCliente().getEmail());
		
		sheet.createRow(4).createCell(0).setCellValue("Datos de la factura");
		sheet.createRow(5).createCell(0).setCellValue("Folio: " + factura.getId());
		sheet.createRow(6).createCell(0).setCellValue("Descripción: " + factura.getDescripcion());
		sheet.createRow(7).createCell(0).setCellValue("Fecha: " + factura.getCreateAt());
		
//		Estilos de celdas
		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setBorderBottom(BorderStyle.MEDIUM);
		headerStyle.setBorderTop(BorderStyle.MEDIUM);
		headerStyle.setBorderRight(BorderStyle.MEDIUM);
		headerStyle.setBorderLeft(BorderStyle.MEDIUM);
		headerStyle.setFillForegroundColor(IndexedColors.GOLD.index);
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		CellStyle bodyStyle = workbook.createCellStyle();
		bodyStyle.setBorderBottom(BorderStyle.THIN);
		bodyStyle.setBorderTop(BorderStyle.THIN);
		bodyStyle.setBorderRight(BorderStyle.THIN);
		bodyStyle.setBorderLeft(BorderStyle.THIN);
		
		
		Row header = sheet.createRow(9);
		header.createCell(0).setCellValue(messages.getMessage("text.factura.producto.producto"));
		header.createCell(1).setCellValue(messages.getMessage("text.factura.producto.precio"));
		header.createCell(2).setCellValue(messages.getMessage("text.factura.producto.cantidad"));
		header.createCell(3).setCellValue(messages.getMessage("text.factura.producto.total"));
		header.getCell(0).setCellStyle(headerStyle);
		header.getCell(1).setCellStyle(headerStyle);
		header.getCell(2).setCellStyle(headerStyle);
		header.getCell(3).setCellStyle(headerStyle);
		
		int rowNumber = 10;
		for (ItemFactura item : factura.getItems()) {
			Row fila = sheet.createRow(rowNumber++);
			cell = fila.createCell(0);
			cell.setCellValue(item.getProducto().getNombre());
			cell.setCellStyle(bodyStyle);
			
			cell = fila.createCell(1);
			cell.setCellValue(item.getProducto().getPrecio());
			cell.setCellStyle(bodyStyle);
			
			cell = fila.createCell(2);
			cell.setCellValue(item.getCantidad());
			cell.setCellStyle(bodyStyle);
			
			cell = fila.createCell(3);
			cell.setCellValue(item.calcularImporte());
			cell.setCellStyle(bodyStyle);
		}
		Row filaTotal = sheet.createRow(rowNumber);
		filaTotal.createCell(2).setCellValue("Gran Total:");
		filaTotal.createCell(3).setCellValue(factura.getTotal());
	}


}
