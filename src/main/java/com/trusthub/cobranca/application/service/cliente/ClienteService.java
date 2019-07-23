package com.trusthub.cobranca.application.service.cliente;

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

import com.trusthub.cobranca.application.exceptions.atendimento.AtendimentoCobrancaIntegracaoCobrancaOperacaoException;
import com.trusthub.cobranca.application.exceptions.cliente.ClienteIntegracaoCobrancaServiceException;
import com.trusthub.cobranca.application.util.ApiConstantes;
import com.trusthub.cobranca.application.util.Constantes;
import com.trusthub.cobranca.application.util.Mensagens;
import com.trusthub.cobranca.configuration.api.ClientDiscoveyServiceFactory;
import com.trusthub.cobranca.configuration.validation.CobrancaIntegracaoError;
import com.trusthub.cobranca.domain.cliente.ClienteDTO;
import com.trusthub.cobranca.security.CobrancaIntegracaoSecurityUtil;

/**
 * Classe de servico que contem médodos para acessar api cobranca operacao (cliente)
 * @author alan.franco
 */
@Component
public class ClienteService {
	
	@Autowired
	protected RestTemplate restTemplate;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private ClientDiscoveyServiceFactory discovey;
	
	/**
	 * Consultar dados do Cliente
	 * @param documento
	 * @return List<ClienteDTO> - lista com dados do cliente
	 */
	public List<ClienteDTO> consultarCliente(String documento){
		ResponseEntity<ClienteDTO[]> response = null;
		List<ClienteDTO> listaClienteDTO = new ArrayList<>();
		try {
			 	String uri = new StringBuilder(ApiConstantes.API_BAR)
			 			.append(ApiConstantes.API_ATENDIMENTO_CONSULTAR_CLIENTE).append(ApiConstantes.API_BAR)
			 			.append(documento).toString();
				response = restTemplate.exchange(discovey.url(discovey.OPERACAO_COBRANCA, discovey.CONTEXTO_OPERACAO_COBRANCA, uri),  
								HttpMethod.GET, CobrancaIntegracaoSecurityUtil.atribuirBodyAndHeader(null, request.getHeader(Constantes.AUTHORIZATION)),
									ClienteDTO[].class);
				listaClienteDTO = this.validarRespostaConsultarCliente(response);
		}catch (ClienteIntegracaoCobrancaServiceException ei) {
			throw ei;
		}catch (HttpClientErrorException ei) {
			throw new AtendimentoCobrancaIntegracaoCobrancaOperacaoException(new StringBuilder(Mensagens.SERVICE_ERRO_CONSULTAR_CLIENTE)
					.append(ei.getResponseBodyAsString()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE, HttpStatus.FORBIDDEN);
		}catch (HttpServerErrorException ei) {
			throw new AtendimentoCobrancaIntegracaoCobrancaOperacaoException(new StringBuilder(Mensagens.SERVICE_ERRO_CONSULTAR_CLIENTE)
					.append(ei.getResponseBodyAsString()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE);
		}catch (Exception e) {
			throw new AtendimentoCobrancaIntegracaoCobrancaOperacaoException(new StringBuilder(Mensagens.SERVICE_ERRO_CONSULTAR_CLIENTE)
				.append(e.getMessage()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE);
		}
		return listaClienteDTO;
	}
	
	/**
	 * Validar resposta do servico operação da API cobranca operacao
	 * @param response
	 * @return List<ClienteDTO> - Lista com dados do cliente.
	 */
	public List<ClienteDTO> validarRespostaConsultarCliente(ResponseEntity<ClienteDTO[]> response) {
		List<ClienteDTO> listaClienteDTO = new ArrayList<>();
		try {
			if(response.getStatusCodeValue() != HttpStatus.OK.value()) {
				throw new ClienteIntegracaoCobrancaServiceException(new StringBuilder(Mensagens.SERVICE_ERRO_VALIDAR_RESPOSTA_CONSULTAR_CLIENTE)
						.append(Constantes.CODIGO_ERRO).append(response.getStatusCodeValue()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE);
			}else if (response.getBody() != null && response.getBody().length > 0) {
				listaClienteDTO = Arrays.asList(response.getBody());
			}
		}catch (Exception e) {
			 throw new ClienteIntegracaoCobrancaServiceException(new StringBuilder(Mensagens.SERVICE_ERRO_VALIDAR_RESPOSTA_CONSULTAR_CLIENTE)
					 .append(e.getMessage()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE);	
		}				
		return listaClienteDTO;
	}
}
