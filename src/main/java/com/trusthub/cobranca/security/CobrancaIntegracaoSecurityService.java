package com.trusthub.cobranca.security;

import java.util.Base64;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.trusthub.cobranca.application.util.ApiConstantes;
import com.trusthub.cobranca.application.util.Constantes;
import com.trusthub.cobranca.application.util.Mensagens;
import com.trusthub.cobranca.configuration.api.ClientDiscoveyServiceFactory;
import com.trusthub.cobranca.configuration.validation.CobrancaIntegracaoError;

/**
 * Classe de servico que contem médodos para acessar api cobranca acesso
 * @author alan.franco
 */
@Component
public class CobrancaIntegracaoSecurityService {
	
	@Autowired
	protected RestTemplate restTemplate;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private ClientDiscoveyServiceFactory discovey;

	/**
	 * Acessa o servico do cobranca acesso para verificar se o token esta valido ou perfil autorizado
	 * @return
	 */
	public String validarToken(String urlExecutada){
		ResponseEntity<String> response = null;
		String retorno = null;
		try {
				String encodedUrlExecutada = Base64.getEncoder().encodeToString(urlExecutada.getBytes());
			 	String uri = new StringBuilder(ApiConstantes.API_BAR).append(ApiConstantes.API_VALIDAR_TOKEN)
			 			            .append(ApiConstantes.API_BAR).append(encodedUrlExecutada).toString();
				response = restTemplate.exchange(discovey.url(discovey.INTEGRACAO_COBRANCA_ACESSO, discovey.CONTEXTO_INTEGRACAO_COBRANCA_ACESSO, uri),  
													HttpMethod.GET, CobrancaIntegracaoSecurityUtil.atribuirBodyAndHeader(null, request.getHeader(Constantes.AUTHORIZATION)), 
														String.class);
				retorno = this.validarRespostaValidarToken(response);
		}catch (HttpServerErrorException | HttpClientErrorException e) {
			 throw new CobrancaIntegracaoSecurityException(new StringBuilder(Mensagens.SECURITY_VALIDAR_TOKEN)
						.append(e.getResponseBodyAsString()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_SECURITY, HttpStatus.FORBIDDEN);
		}catch (Exception e) {
			throw new CobrancaIntegracaoSecurityException(new StringBuilder(Mensagens.SECURITY_VALIDAR_TOKEN)
				.append(e.getMessage()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_SECURITY);
		}
		return retorno;
	}
	
	/**
	 * Valida o retorno com as opcoes (Token Valido ou Invalido, Autorizado ou Nao Autorizado)
	 * @param response
	 * @return
	 */
	private String validarRespostaValidarToken(ResponseEntity<String> response) {
		String retorno = null;
		try {
			if(response.getStatusCodeValue() != HttpStatus.OK.value()) {
				throw new CobrancaIntegracaoSecurityException(new StringBuilder(Mensagens.SECURITY_ERRO_VALIDAR_RESPOSTA_VALIDAR_TOKEN)
						.append(Constantes.CODIGO_ERRO).append(response.getStatusCodeValue()).toString(), 
						 CobrancaIntegracaoError.ERROR_COBRANCA_SECURITY);
			}else if (response.getBody() != null ) {
				retorno = response.getBody();
			}
		}catch (Exception e) {
			 throw new CobrancaIntegracaoSecurityException(new StringBuilder(Mensagens.SECURITY_ERRO_VALIDAR_RESPOSTA_VALIDAR_TOKEN)
					 .append(e.getMessage()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_SECURITY);	
		}				
		return retorno;
	}
	
}
