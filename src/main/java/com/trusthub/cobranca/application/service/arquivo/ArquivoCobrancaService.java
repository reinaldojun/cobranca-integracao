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

import com.trusthub.cobranca.application.exceptions.arquivo.ArquivoCobrancaIntegracaoCobrancaOperacaoServiceException;
import com.trusthub.cobranca.application.exceptions.arquivo.ArquivoCobrancaIntegracaoServiceException;
import com.trusthub.cobranca.application.util.ApiConstantes;
import com.trusthub.cobranca.application.util.Constantes;
import com.trusthub.cobranca.application.util.Mensagens;
import com.trusthub.cobranca.configuration.api.ClientDiscoveyServiceFactory;
import com.trusthub.cobranca.configuration.validation.CobrancaIntegracaoError;
import com.trusthub.cobranca.domain.arquivo.ArquivoDTO;
import com.trusthub.cobranca.security.CobrancaIntegracaoSecurityUtil;


/**
 * Service que acessa servicos de arquivo do cobranca operacao 
 * @author alan.franco
 */
@Component
public class ArquivoCobrancaService {
	
	@Autowired
	protected RestTemplate restTemplate;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private ClientDiscoveyServiceFactory discovey;
	
	/**
	 * Consultar Arquivos
	 * @param idArquivo
	 * @return
	 */
	public ArquivoDTO consultarArquivo(Long idArquivo){
		ResponseEntity<ArquivoDTO> response = null;
		ArquivoDTO arquivoDTO = new ArquivoDTO();
		try {
			 	String uri = new StringBuilder(ApiConstantes.API_BAR)
			 			.append(ApiConstantes.API_ARQUIVO_CONSULTAR_ARQUIVO).append(ApiConstantes.API_BAR)
			 			.append(idArquivo).toString();
				response = restTemplate.exchange(discovey.url(discovey.OPERACAO_COBRANCA, discovey.CONTEXTO_OPERACAO_COBRANCA, uri),
												HttpMethod.GET, CobrancaIntegracaoSecurityUtil.atribuirBodyAndHeader(null, request.getHeader(Constantes.AUTHORIZATION)),
													ArquivoDTO.class);
				arquivoDTO = this.validarRespostaConsultarArquivo(response);
		
		}catch (ArquivoCobrancaIntegracaoServiceException ei) {
			throw ei;
		}catch (HttpClientErrorException ei) {
			throw new ArquivoCobrancaIntegracaoCobrancaOperacaoServiceException(new StringBuilder(Mensagens.SERVICE_ERRO_CONSULTAR_ARQUIVO)
					.append(ei.getResponseBodyAsString()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE, HttpStatus.FORBIDDEN);
		}catch (HttpServerErrorException ei) {
			throw new ArquivoCobrancaIntegracaoCobrancaOperacaoServiceException(new StringBuilder(Mensagens.SERVICE_ERRO_CONSULTAR_ARQUIVO)
					.append(ei.getResponseBodyAsString()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE);
		}catch (Exception e) {
			throw new ArquivoCobrancaIntegracaoCobrancaOperacaoServiceException(new StringBuilder(Mensagens.SERVICE_ERRO_CONSULTAR_ARQUIVO)
				.append(e.getMessage()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE);
		}
		return arquivoDTO;
	}
	
	/**
	 * Validar resposta consulta arquivo
	 * @param response
	 * @return
	 */
	public ArquivoDTO validarRespostaConsultarArquivo(ResponseEntity<ArquivoDTO> response) {
		ArquivoDTO arquivoDTO = null;
		try {
			if(response.getStatusCodeValue() != HttpStatus.OK.value()) {
				throw new ArquivoCobrancaIntegracaoServiceException(new StringBuilder(Mensagens.SERVICE_ERRO_VALIDAR_RESPOSTA_CONSULTAR_ARQUIVO)
						.append(Constantes.CODIGO_ERRO).append(response.getStatusCodeValue()).toString(), 
						CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE);
			}else {
				arquivoDTO = response.getBody();
			}
		}catch (Exception e) {
			 throw new ArquivoCobrancaIntegracaoServiceException(new StringBuilder(Mensagens.SERVICE_ERRO_VALIDAR_RESPOSTA_CONSULTAR_ARQUIVO)
					 .append(e.getMessage()).toString(),CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE);
		}
		return arquivoDTO;
	}
	

}
