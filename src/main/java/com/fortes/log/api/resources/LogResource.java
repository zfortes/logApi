package com.fortes.log.api.resources;

import com.fortes.log.api.dtos.LogDTO;
import com.fortes.log.api.entidies.Log;
import com.fortes.log.api.services.LogDTOService;
import com.fortes.log.api.services.LogService;
import com.fortes.log.api.services.impl.LogDTOServiceImpl;
import com.fortes.log.api.services.impl.LogServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class LogResource {
	
	@Autowired
	private LogService service = new LogServiceImpl();
	
	
	private LogDTOService serviceDTO = new LogDTOServiceImpl();

	@GetMapping("/log")
	public ResponseEntity<?> receberLog(){

		try {
			return ResponseEntity.ok().body(service.recebeTodos());
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/log")
	public ResponseEntity<?> salvarLog(@Valid @RequestBody LogDTO logDto, BindingResult bindingResult){

		try {
			if (bindingResult.hasErrors()) {
				List<String> errorList = new ArrayList<String>();
				bindingResult.getAllErrors().forEach(
						error->errorList.add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(errorList);
			}
			
			return ResponseEntity.ok()
					.body(service.salvar(serviceDTO.converteLog(logDto)));
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}
	
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public ResponseEntity<Page<Log>> receberPaginaLog(int page, int tamanhoPage){
		
		return ResponseEntity.ok().body(service.recebePagina(page, tamanhoPage));
		
	}
}
