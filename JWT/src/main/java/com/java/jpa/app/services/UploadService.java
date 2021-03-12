package com.java.jpa.app.services;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadService implements IUploadService {

	private static final String RUTA_IMAGENES = "uploads";
	private static final Logger logger = LoggerFactory.getLogger(UploadService.class);
	
	@Override
	public Resource load(String filename) throws MalformedURLException {
		Path pathFoto = getPath(filename);
		logger.info("pathFoto: ".concat(pathFoto.toString()));
		Resource recurso = null;
			recurso = new UrlResource(pathFoto.toUri());
			if( !recurso.isReadable() && !recurso.exists()) {
				throw new RuntimeException("Error no se puede cargar la imagen: ".concat(pathFoto.toString()));
			}
		return recurso;
	}

	@Override
	public String copy(MultipartFile file) throws IOException {
		String uniqueFileName = UUID.randomUUID().toString().concat("_").concat(file.getOriginalFilename());
		Path rootPath =getPath(uniqueFileName);
//		Path absoluteRootPath = rootPath.toAbsolutePath();
//		try {
//			byte[] bytes = foto.getBytes();
//			Path rutaFinal = Paths.get(rutaAbsoluta+"//"+foto.getOriginalFilename());
//			Files.write(rutaFinal, bytes);
			logger.info("rootPath : ".concat(rootPath.toString()));
//			logger.info("absoluteRootPath : ".concat(absoluteRootPath.toString()));
			Files.copy(file.getInputStream(), rootPath);
			
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		return uniqueFileName;
	}

	@Override
	public boolean delete(String filename) {
		Path rootPath = getPath(filename);
		File archivo = rootPath.toFile();
		if( archivo.exists() && archivo.canRead())
			if( archivo.delete() )
				return true;
		return false;
	}

	public Path getPath(String filename) {
		return Paths.get(RUTA_IMAGENES).resolve(filename).toAbsolutePath();
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(Paths.get(RUTA_IMAGENES).toFile());
	}

	@Override
	public void init() throws IOException {
		Files.createDirectory(Paths.get(RUTA_IMAGENES));
	}
}
