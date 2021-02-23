package com.forms.web.app.interceptors;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component("TiempoInterceptor")
public class TiempoInterceptor implements HandlerInterceptor{

	private static final Logger logger = LoggerFactory.getLogger(TiempoInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.info("TiempoInterceptor: /preHandle() entrando...");
		long tiempoInicio = System.currentTimeMillis();
		request.setAttribute("tiempoInicio", tiempoInicio);
		Integer demora = new Random().nextInt(1000);
		Thread.sleep(demora);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		long tiempoFin = System.currentTimeMillis();
		long tiempoInicio = (Long) request.getAttribute("tiempoInicio");
		long tiempoTranscurrido = tiempoFin - tiempoInicio;
		
		if( modelAndView != null) {
			modelAndView.addObject("tiempoTranscurrido", tiempoTranscurrido);
		}
		
		logger.info("tiempoTranscurrido: {} milisegundos", tiempoTranscurrido);
		logger.info("TiempoInterceptor: /postHandle() saliendo...");
	}

}
