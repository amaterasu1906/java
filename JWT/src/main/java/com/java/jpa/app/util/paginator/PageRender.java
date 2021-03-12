package com.java.jpa.app.util.paginator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

public class PageRender<T> {
	private String url;
	private Page<T> page;
	
	private Integer totalPaginas;
	private Integer numElementosPorPagina;
	private Integer paginaActual;
	
	private List<PageItem> paginas;
	
	public PageRender(String url, Page<T> page) {
		this.url = url;
		this.page = page;
		this.totalPaginas = page.getTotalPages();
		this.numElementosPorPagina = page.getSize();
		this.paginaActual = page.getNumber()+1;
		this.paginas = new ArrayList<>();
		int desde, hasta;
		if( this.totalPaginas <= this.numElementosPorPagina) {
			desde = 1;
			hasta = this.totalPaginas;
		}else {
			if( this.paginaActual <= this.numElementosPorPagina/2) {
				desde = 1;
				hasta = this.numElementosPorPagina;
			}else {
				if( this.paginaActual >= this.totalPaginas - this.numElementosPorPagina/2) {
					desde = this.totalPaginas - numElementosPorPagina + 1;
					hasta = this.numElementosPorPagina;
				}else {
					desde = this.paginaActual - this.numElementosPorPagina/2;
					hasta = this.numElementosPorPagina;
				}
			}
		}
		for (int i = 0; i < hasta; i++) {
			paginas.add(new PageItem(desde + i, paginaActual == desde+i));
		}
	}

	public String getUrl() {
		return url;
	}

	public Integer getTotalPaginas() {
		return totalPaginas;
	}

	public Integer getPaginaActual() {
		return paginaActual;
	}

	public List<PageItem> getPaginas() {
		return paginas;
	}
	public boolean isFirst() {
		return page.isFirst();
	}
	public boolean isLast() {
		return page.isLast();
	}
	public boolean isHasNext() {
		return page.hasNext();
	}
	public boolean isHasPrevious() {
		return page.hasPrevious();
	}
}
