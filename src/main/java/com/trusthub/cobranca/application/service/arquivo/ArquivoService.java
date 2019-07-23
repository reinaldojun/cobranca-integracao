package com.trusthub.cobranca.application.service.arquivo;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.trusthub.cobranca.application.exceptions.arquivo.ArquivoIntegracaoException;
import com.trusthub.cobranca.application.exceptions.arquivo.ArquivoIntegracaoServiceException;
import com.trusthub.cobranca.application.util.ApiConstantes;
import com.trusthub.cobranca.application.util.Constantes;
import com.trusthub.cobranca.application.util.Mensagens;
import com.trusthub.cobranca.configuration.api.ClientDiscoveyServiceFactory;
import com.trusthub.cobranca.configuration.validation.CobrancaIntegracaoError;
import com.trusthub.cobranca.domain.arquivo.BaseCadastralRequestDTO;
import com.trusthub.cobranca.domain.arquivo.FileRequestDTO;
import com.trusthub.cobranca.domain.arquivo.ResponseDTO;
import com.trusthub.cobranca.security.CobrancaIntegracaoSecurityUtil;

/**
 * Servico que ira acessar a api para manipulacao de arquivo
 * @author alan.franco
 */
@Component
public class ArquivoService{

	@Autowired
	protected RestTemplate restTemplate;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private ClientDiscoveyServiceFactory discovey;

	/**
	 * Chama servico de integracao para salvar dados do arquivo
	 * @param dadosArquivo - Dados do arquivo
	 * @return String
	 */
	public Long cadastrarArquivo(FileRequestDTO dadosArquivo) {
		ResponseEntity<String> response = null;
		Long idArquivo = null;
		try {
			String uri = ApiConstantes.API_BAR + ApiConstantes.API_ENDPOINT_SAVE_FILE;
			response = restTemplate.exchange(discovey.url(discovey.INTEGRACAO_ARQUIVO, discovey.CONTEXTO_INTEGRACAO_ARQUIVO, uri), 
					HttpMethod.POST, CobrancaIntegracaoSecurityUtil.atribuirBodyAndHeader(dadosArquivo, request.getHeader(Constantes.AUTHORIZATION)), 
					  String.class);
			idArquivo = this.validarRespostaCadastrarArquivo(response);
		}catch (ArquivoIntegracaoServiceException ei) {
			throw ei;
		}catch (HttpClientErrorException ei) {
			throw new  ArquivoIntegracaoException(new StringBuilder(Mensagens.SERVICE_ERRO_CADASTRAR_ARQUIVO)
					.append(ei.getResponseBodyAsString()).toString(),CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE, HttpStatus.FORBIDDEN);
		}catch (HttpServerErrorException ei) {
			throw new  ArquivoIntegracaoException(new StringBuilder(Mensagens.SERVICE_ERRO_CADASTRAR_ARQUIVO)
					.append(ei.getResponseBodyAsString()).toString(),CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE);
		}catch (Exception e) {
			throw new ArquivoIntegracaoException(new StringBuilder(Mensagens.SERVICE_ERRO_CADASTRAR_ARQUIVO)
				.append(e.getMessage()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE);
		}
		return idArquivo;
	}
	
	/**
	 * Validar resposta cadastrar arquivo
	 * @param response
	 * @return Long - Id do arquivo
	 */
	public Long validarRespostaCadastrarArquivo(ResponseEntity<String> response) {
		Long idArquivo = null;
		try {
			if(response.getStatusCodeValue() != HttpStatus.OK.value()) {
				throw new ArquivoIntegracaoServiceException(new StringBuilder(Mensagens.SERVICE_ERRO_VALIDAR_RESPOSTA_CADASTRAR_ARQUIVO)
						.append(Constantes.CODIGO_ERRO).append(response.getStatusCodeValue()).toString(), 
						CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE);
			}else if (response.getBody() != null) {
				idArquivo = Long.valueOf(response.getBody());
			}
		}catch (Exception e) {
			 throw new ArquivoIntegracaoServiceException(new StringBuilder(Mensagens.SERVICE_ERRO_VALIDAR_RESPOSTA_CADASTRAR_ARQUIVO)
					 .append(e.getMessage()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE);	
		}				
		return idArquivo;
	}
	
	/**
	 * Buscar conteudo do arquivo por id.
	 * @param arquivoId
	 * @return ResponseDTO
	 */
	public ResponseDTO consultarConteudoArquivoId(String arquivoId) {
		ResponseEntity<ResponseDTO> response = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			BaseCadastralRequestDTO entrada = new BaseCadastralRequestDTO();
			entrada.setFileId(arquivoId);
			String uri = ApiConstantes.API_BAR + ApiConstantes.API_ENDPOINT_FIND_FILE;
			response = restTemplate.exchange(discovey.url(discovey.INTEGRACAO_ARQUIVO, discovey.CONTEXTO_INTEGRACAO_ARQUIVO, uri), 
					 					HttpMethod.POST, CobrancaIntegracaoSecurityUtil.atribuirBodyAndHeader(entrada, request.getHeader(Constantes.AUTHORIZATION)), 
					 						ResponseDTO.class);
			responseDTO = this.validarRespostaConsultarConteudoArquivoId(response);
		}catch (ArquivoIntegracaoServiceException ei) {
			throw ei;
		}catch (HttpClientErrorException ei) {
			throw new  ArquivoIntegracaoException(new StringBuilder(Mensagens.SERVICE_ERRO_CONSULTAR_CONTEUDO_ARQUIVO_ID)
					.append(ei.getResponseBodyAsString()).toString(),CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE, HttpStatus.FORBIDDEN);
		}catch (HttpServerErrorException ei) {
			throw new  ArquivoIntegracaoException(new StringBuilder(Mensagens.SERVICE_ERRO_CONSULTAR_CONTEUDO_ARQUIVO_ID)
					.append(ei.getResponseBodyAsString()).toString(),CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE);
		}catch (Exception e) {
			throw new ArquivoIntegracaoException(new StringBuilder(Mensagens.SERVICE_ERRO_CONSULTAR_CONTEUDO_ARQUIVO_ID)
				.append(e.getMessage()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE);
		}
		return responseDTO;
	}
	
	/**
	 * Validar resposta Consultar Conteudo arquivo id
	 * @param response
	 * @return
	 */
	public ResponseDTO validarRespostaConsultarConteudoArquivoId(ResponseEntity<ResponseDTO> response) {
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			if(response.getStatusCodeValue() != HttpStatus.OK.value()) {
				throw new ArquivoIntegracaoServiceException(new StringBuilder(Mensagens.SERVICE_ERRO_VALIDAR_RESPOSTA_CONSULTAR_CONTEUDO_ARQUIVO_ID)
						.append(Constantes.CODIGO_ERRO).append(response.getStatusCodeValue()).toString(), 
						CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE);
			}else if (response.getBody() != null) {
				responseDTO = response.getBody();
			}
		}catch (Exception e) {
			 throw new ArquivoIntegracaoServiceException(new StringBuilder(Mensagens.SERVICE_ERRO_VALIDAR_RESPOSTA_CONSULTAR_CONTEUDO_ARQUIVO_ID)
					 .append(e.getMessage()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE);	
		}				
		return responseDTO;
	}

	
}
