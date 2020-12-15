package com.sistema.ventas.api;

import java.io.IOException;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sistema.ventas.bo.IUsuariosSistemaBO;
import com.sistema.ventas.dto.ConsultarUsuarioDTO;
import com.sistema.ventas.dto.ResponseOk;
import com.sistema.ventas.dto.UsuariosDTO;
import com.sistema.ventas.exceptions.BOException;
import com.sistema.ventas.exceptions.CustomExceptionHandler;
import com.sistema.ventas.util.MensajesUtil;

@RestController
@RequestMapping("/usuariosSistema")
public class UsuariosSistemaApi {
	
	@SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(UsuariosSistemaApi.class.getName());
	
	@Autowired
	private IUsuariosSistemaBO objIUsuariosSistemaBO;
	
	@RequestMapping(value="/crearUsuario",method = RequestMethod.POST)
	public ResponseEntity<?> crearUsuario(
			@RequestHeader(	value = "Accept-Language", 	required = false) String strLanguage, 
			@RequestBody UsuariosDTO objUsuariosDTO
			) throws BOException {
		
		try {

			Map<String,Object> objMap=objIUsuariosSistemaBO.crearUsuario(objUsuariosDTO);

			return new ResponseEntity<>(new ResponseOk(
					MensajesUtil.getMensaje("ven.response.ok", MensajesUtil.validateSupportedLocale(null)),
					objMap), HttpStatus.OK);
		} catch (BOException be) {
			logger.error(" ERROR => " + be.getTranslatedMessage(null));
			throw new CustomExceptionHandler(be.getTranslatedMessage(null), be.getData());
		}
	}
	
	@RequestMapping(value="/{idUsuario}/actualizarUsuario",method = RequestMethod.PUT)
	public ResponseEntity<?> actualizarUsuario(
			@RequestHeader(	value = "Accept-Language", 	required = false) String strLanguage, 
			@PathVariable(value="idUsuario", required = false)  Integer  intIdUsuario,
			@RequestBody UsuariosDTO objUsuariosDTO
			) throws BOException {
		
		try {

			objIUsuariosSistemaBO.actualizarUsuario(intIdUsuario,objUsuariosDTO);

			return new ResponseEntity<>(new ResponseOk(
					MensajesUtil.getMensaje("ven.response.ok", MensajesUtil.validateSupportedLocale(null)),
					null), HttpStatus.OK);
		} catch (BOException be) {
			logger.error(" ERROR => " + be.getTranslatedMessage(null));
			throw new CustomExceptionHandler(be.getTranslatedMessage(null), be.getData());
		}
	}
	
	@RequestMapping(value="/usuarios",method = RequestMethod.GET)
	public ResponseEntity<?> consultarUsuarios(
			@RequestHeader(	value = "Accept-Language", 	required = false) String strLanguage,
			@RequestParam(	value = "page", 	required = false) Integer intPage,
			@RequestParam(	value = "perPage", 	required = false) Integer intPerPage
			) throws BOException {
		
		try {

			Map<String,Object> mapConsultarUsuarioDTO=objIUsuariosSistemaBO.consultarUsuarios(intPage,intPerPage);

			System.out.println(mapConsultarUsuarioDTO.get("row"));
			System.out.println(mapConsultarUsuarioDTO.get("totalRow"));
			
			return new ResponseEntity<>(new ResponseOk(
					MensajesUtil.getMensaje("ven.response.ok", MensajesUtil.validateSupportedLocale(strLanguage)),
					mapConsultarUsuarioDTO), HttpStatus.OK);
		} catch (BOException be) {
			logger.error(" ERROR => " + be.getTranslatedMessage(null));
			throw new CustomExceptionHandler(be.getTranslatedMessage(null), be.getData());
		}
	}
	
	@RequestMapping(value="/{idUsuario}/usuarios",method = RequestMethod.DELETE)
	@Transactional 
	public ResponseEntity<?> eliminarUsuario(
			@RequestHeader(	value = "Accept-Language", 	required = false) String strLanguage,
			@PathVariable(value="idUsuario", required = false)  Integer  intIdUsuario
			) throws BOException {
		
		try {

			objIUsuariosSistemaBO.eliminarUsuario(intIdUsuario);

			return new ResponseEntity<>(new ResponseOk(
					MensajesUtil.getMensaje("ven.response.ok", MensajesUtil.validateSupportedLocale(strLanguage)),
					null), HttpStatus.OK);
		} catch (BOException be) {
			logger.error(" ERROR => " + be.getTranslatedMessage(null));
			throw new CustomExceptionHandler(be.getTranslatedMessage(null), be.getData());
		}
	}
	
	@RequestMapping(value="/{idUsuario}/basica",method = RequestMethod.GET)
	public ResponseEntity<?> consultarUsuarioXId(
			@RequestHeader(	value = "Accept-Language", 	required = false) String strLanguage,
			@PathVariable(value="idUsuario", required = false)  Integer  intIdUsuario
			) throws BOException {
		
		try {

			ConsultarUsuarioDTO objUsuario=objIUsuariosSistemaBO.consultarUsuarioXId(intIdUsuario);

			return new ResponseEntity<>(new ResponseOk(
					MensajesUtil.getMensaje("ven.response.ok", MensajesUtil.validateSupportedLocale(strLanguage)),
					objUsuario), HttpStatus.OK);
		} catch (BOException be) {
			logger.error(" ERROR => " + be.getTranslatedMessage(null));
			throw new CustomExceptionHandler(be.getTranslatedMessage(null), be.getData());
		}
	}
	
	@RequestMapping(value="/photo",method = RequestMethod.POST)
	public ResponseEntity<?> guardarPhoto(
			@RequestHeader(	value = "Accept-Language", 	required = false) String strLanguage,
			@RequestParam("photo") MultipartFile photo,
			@RequestParam(value="idPersona", required = false)  Integer  intIdPersona
			) throws BOException, IOException {
		
		try {

			objIUsuariosSistemaBO.guardarPhoto(photo,intIdPersona);

			return new ResponseEntity<>(new ResponseOk(
					MensajesUtil.getMensaje("ven.response.ok", MensajesUtil.validateSupportedLocale(strLanguage)),
					null), HttpStatus.OK);
		} catch (BOException be) {
			logger.error(" ERROR => " + be.getTranslatedMessage(null));
			throw new CustomExceptionHandler(be.getTranslatedMessage(null), be.getData());
		}
	}
	
}
