package com.trusthub.cobranca.application.service.documento;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.trusthub.cobranca.application.exceptions.base.cadastrais.BaseCadastraIntegracaoException;
import com.trusthub.cobranca.application.exceptions.base.cadastrais.BaseCadastraisIntegracaoServiceException;
import com.trusthub.cobranca.application.util.ApiConstantes;
import com.trusthub.cobranca.application.util.Constantes;
import com.trusthub.cobranca.application.util.Mensagens;
import com.trusthub.cobranca.configuration.api.ClientDiscoveyServiceFactory;
import com.trusthub.cobranca.configuration.validation.CobrancaIntegracaoError;
import com.trusthub.cobranca.domain.documento.DocumentoDTO;
import com.trusthub.cobranca.security.CobrancaIntegracaoSecurityUtil;

@Component
public class ClienteDocumentoService {
	
	@Autowired
	protected RestTemplate restTemplate;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private ClientDiscoveyServiceFactory discovey;
	
	/**
	 * Consultar Documento Cliente
	 * @param idCliente
	 * @return List<DocumentoDTO> - Lista Documentos de cliente
	 */
	public List<DocumentoDTO> consultarDocumentoCliente(Integer idCliente){
		ResponseEntity<DocumentoDTO[]> response = null;
		List<DocumentoDTO> listaDocumentoDTO = new ArrayList<>();
		try {
			 	String uri = new StringBuilder(ApiConstantes.API_BAR)
			 			.append(ApiConstantes.API_ENDPOINT_CONSULTA_DOCUMENTOS_CLIENTE).append(ApiConstantes.API_BAR)
			 			.append(idCliente).toString();
				response = restTemplate.exchange(discovey.url(discovey.INTEGRACAO_BASE_CADASTRAL, discovey.CONTEXTO_INTEGRACAO_BASE_CADASTRAL, uri),  
								HttpMethod.GET, CobrancaIntegracaoSecurityUtil.atribuirBodyAndHeader(null, request.getHeader(Constantes.AUTHORIZATION)),
									DocumentoDTO[].class);
				listaDocumentoDTO = this.validarRespostaConsultarDocumentoCliente(response);
		}catch (BaseCadastraisIntegracaoServiceException ei) {
			throw ei;
		}catch (HttpClientErrorException ei) {
			throw new BaseCadastraIntegracaoException(new StringBuilder(Mensagens.SERVICE_ERRO_CONSULTAR_DOCUMENTO_CLIENTE)
					.append(ei.getResponseBodyAsString()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE, HttpStatus.FORBIDDEN);
		}catch (HttpServerErrorException ei) {
			throw new BaseCadastraIntegracaoException(new StringBuilder(Mensagens.SERVICE_ERRO_CONSULTAR_DOCUMENTO_CLIENTE)
					.append(ei.getResponseBodyAsString()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE);
		}catch (Exception e) {
			throw new BaseCadastraIntegracaoException(new StringBuilder(Mensagens.SERVICE_ERRO_CONSULTAR_DOCUMENTO_CLIENTE)
				.append(e.getMessage()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE);
		}
		return listaDocumentoDTO;
	}
	
	/**
	 * Validar resposta Consultar documento cliente
	 * @param response
	 * @return List<DocumentoDTO> - Lista com documentos
	 */
	public List<DocumentoDTO> validarRespostaConsultarDocumentoCliente(ResponseEntity<DocumentoDTO[]> response) {
		List<DocumentoDTO> listaDocumentoDTO = new ArrayList<>();
		try {
			if(response.getStatusCodeValue() != HttpStatus.OK.value()) {
				throw new BaseCadastraisIntegracaoServiceException(new StringBuilder(Mensagens.SERVICE_ERRO_VALIDAR_RESPOSTA_CONSULTAR_DOCUMENTO_CLIENTE)
						.append(Constantes.CODIGO_ERRO).append(response.getStatusCodeValue()).toString(), 
							CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE);
			}else if (response.getBody() != null && response.getBody().length > 0) {
				listaDocumentoDTO = Arrays.asList(response.getBody());
			}
		}catch (Exception e) {
			 throw new BaseCadastraisIntegracaoServiceException(new StringBuilder(Mensagens.SERVICE_ERRO_VALIDAR_RESPOSTA_CONSULTAR_DOCUMENTO_CLIENTE)
					 .append(e.getMessage()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE);	
		}				
		return listaDocumentoDTO;
	}

}
