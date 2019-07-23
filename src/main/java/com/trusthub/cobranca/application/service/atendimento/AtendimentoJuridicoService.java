package com.trusthub.cobranca.application.service.atendimento;

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
import com.trusthub.cobranca.application.exceptions.atendimento.AtendimentoCobrancaIntegracaoServiceException;
import com.trusthub.cobranca.application.util.ApiConstantes;
import com.trusthub.cobranca.application.util.Constantes;
import com.trusthub.cobranca.application.util.Mensagens;
import com.trusthub.cobranca.configuration.api.ClientDiscoveyServiceFactory;
import com.trusthub.cobranca.configuration.validation.CobrancaIntegracaoError;
import com.trusthub.cobranca.domain.atendimento.AtendimentoJuridicoIntegracaoDTO;
import com.trusthub.cobranca.domain.atendimento.AtendimentoStatusJuridicoDTO;
import com.trusthub.cobranca.security.CobrancaIntegracaoSecurityUtil;

/**
 * Classe de servico que contem médodos para acessar api cobranca operacao (atendimento juridico) 
 * @author alan.franco
 */
@Component
public class AtendimentoJuridicoService {
	
	@Autowired
	protected RestTemplate restTemplate;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private ClientDiscoveyServiceFactory discovey;
	
	/**
	 * Consultar historico juridico por cedente e sacado acessando api cobranca operacao.
	 * @param idCedente - Id Cedente
	 * @param idSacado - Id Sacado 
	 * @return List<AtendimentoJuridicoIntegracaoDTO> - Lista com dados do historico do atendimento juridico
	 */
	public List<AtendimentoJuridicoIntegracaoDTO> consultarHistoricoJuridicoPorCedenteSacado(String idCedente, String idSacado, Integer idTitulo){
		ResponseEntity<AtendimentoJuridicoIntegracaoDTO[]> response = null;
		List<AtendimentoJuridicoIntegracaoDTO> listaAtendimentoJuridicoIntegracaoDTO = new ArrayList<>();
		try {
		 		String uri = new StringBuilder(ApiConstantes.API_BAR)
		 				.append(ApiConstantes.API_ATENDIMENTO_CONSULTAR_JURIDICO).append(ApiConstantes.API_BAR)
		 				.append(ApiConstantes.API_CEDENTE).append(ApiConstantes.API_BAR)
		 				.append(idCedente).append(ApiConstantes.API_BAR)
		 				.append(ApiConstantes.API_SACADO).append(ApiConstantes.API_BAR)
		 				.append(idSacado).toString();
		 		
		 		if (idTitulo != null) {
			 		String uriTitulo = new StringBuilder(ApiConstantes.API_INTERROGACAO)
		 			                      .append(ApiConstantes.API_TITULO).append(ApiConstantes.API_IGUAL)
		 			                      .append(idTitulo).toString();
			 		uri = uri + uriTitulo;
			 	}
		 		
				response = restTemplate.exchange(discovey.url(discovey.OPERACAO_COBRANCA, discovey.CONTEXTO_OPERACAO_COBRANCA, uri),  
								HttpMethod.GET, CobrancaIntegracaoSecurityUtil.atribuirBodyAndHeader(null, request.getHeader(Constantes.AUTHORIZATION)),
									AtendimentoJuridicoIntegracaoDTO[].class);
				listaAtendimentoJuridicoIntegracaoDTO = this.validarRespostaConsultarHistoricoJuridicoPorCedenteSacado(response);
		}catch (AtendimentoCobrancaIntegracaoServiceException ei) {
			throw ei;
		}catch (HttpClientErrorException ei) {
			throw new AtendimentoCobrancaIntegracaoCobrancaOperacaoException(new StringBuilder(Mensagens.SERVICE_ERRO_CONSULTAR_HISTORICO_JURIDICO_CEDENTE_SACADO)
					.append(ei.getResponseBodyAsString()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE, HttpStatus.FORBIDDEN);
		}catch (HttpServerErrorException ei) {
			throw new AtendimentoCobrancaIntegracaoCobrancaOperacaoException(new StringBuilder(Mensagens.SERVICE_ERRO_CONSULTAR_HISTORICO_JURIDICO_CEDENTE_SACADO)
					.append(ei.getResponseBodyAsString()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE);
		}catch (Exception e) {
			throw new AtendimentoCobrancaIntegracaoCobrancaOperacaoException(new StringBuilder(Mensagens.SERVICE_ERRO_CONSULTAR_HISTORICO_JURIDICO_CEDENTE_SACADO)
				.append(e.getMessage()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE);
		}
		return listaAtendimentoJuridicoIntegracaoDTO;
	}
	
	/**
	 * Validar resposta do consultar historico juridico por cedente e sacado
	 * @param response - resposta da api cobranca operacao
	 * @return - List<AtendimentoJuridicoIntegracaoDTO> - lista com os dados do atendimento juridico
	 */
	public List<AtendimentoJuridicoIntegracaoDTO> validarRespostaConsultarHistoricoJuridicoPorCedenteSacado(ResponseEntity<AtendimentoJuridicoIntegracaoDTO[]> response) {
		List<AtendimentoJuridicoIntegracaoDTO> listaAtendimentoJuridicoIntegracaoDTO = new ArrayList<>();
		try {
			if(response.getStatusCodeValue() != HttpStatus.OK.value()) {
				throw new AtendimentoCobrancaIntegracaoServiceException(new StringBuilder(Mensagens.SERVICE_ERRO_VALIDAR_RESPOSTA_CONSULTAR_HISTORICO_JURIDICO_CEDENTE_SACADO)
						.append(Constantes.CODIGO_ERRO).append(response.getStatusCodeValue()).toString(), 
							CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE);
			}else if (response.getBody() != null && response.getBody().length > 0) {
				listaAtendimentoJuridicoIntegracaoDTO = Arrays.asList(response.getBody());
			}
		}catch (Exception e) {
			 throw new AtendimentoCobrancaIntegracaoServiceException(new StringBuilder(Mensagens.SERVICE_ERRO_VALIDAR_RESPOSTA_CONSULTAR_HISTORICO_JURIDICO_CEDENTE_SACADO)
					 .append(e.getMessage()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE);
		}
		return listaAtendimentoJuridicoIntegracaoDTO;
	}
	
	/**
	 * Cadastra atendimento juridico, acessa api do cobranca operacao de cadastro juridico
	 * @param atendimentoJuridicoRequestDto - Dados do atendimento para cadastro
	 * @return AtendimentoJuridicoIntegracaoDTO - Dados cadastrados
	 */
	public AtendimentoJuridicoIntegracaoDTO cadastrarAtendimentoJuridico(AtendimentoJuridicoIntegracaoDTO atendimentoJuridicoRequestDto) {
		ResponseEntity<AtendimentoJuridicoIntegracaoDTO> response = null;
		AtendimentoJuridicoIntegracaoDTO atendimentoJuridicoIntegracaoDTO = null;
		try {
				String uri = new StringBuilder(ApiConstantes.API_BAR).append(ApiConstantes.API_ATENDIMENTO_CADASTRAR_JURIDICO).toString();
				response =  restTemplate.exchange(discovey.url(discovey.OPERACAO_COBRANCA, discovey.CONTEXTO_OPERACAO_COBRANCA, uri), 
									HttpMethod.POST, CobrancaIntegracaoSecurityUtil.atribuirBodyAndHeader(atendimentoJuridicoRequestDto, request.getHeader(Constantes.AUTHORIZATION)),
										AtendimentoJuridicoIntegracaoDTO.class);
				atendimentoJuridicoIntegracaoDTO = validarRespostaCadastrarAtendimentoJuridico(response);
		}catch (AtendimentoCobrancaIntegracaoServiceException ei) {
			throw ei;
		}catch (HttpClientErrorException ei) {
			throw new AtendimentoCobrancaIntegracaoCobrancaOperacaoException(new StringBuilder(Mensagens.SERVICE_ERRO_CADASTRAR_ATENDIMENTO_JURIDICO)
					.append(ei.getResponseBodyAsString()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE, HttpStatus.FORBIDDEN);
		}catch (HttpServerErrorException ei) {
			throw new AtendimentoCobrancaIntegracaoCobrancaOperacaoException(new StringBuilder(Mensagens.SERVICE_ERRO_CADASTRAR_ATENDIMENTO_JURIDICO)
					.append(ei.getResponseBodyAsString()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE);
		}catch (Exception e) {
			throw new AtendimentoCobrancaIntegracaoCobrancaOperacaoException(new StringBuilder(Mensagens.SERVICE_ERRO_CADASTRAR_ATENDIMENTO_JURIDICO)
				.append(e.getMessage()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE);
		}
		return atendimentoJuridicoIntegracaoDTO;	
	}
	
	/**
	 * Validar resposta do cadastro atendimento juridico. 
	 * @param resposta da api cobranca operacao
	 * @return AtendimentoJuridicoIntegracaoDTO - dados cadastrados.
	 */
	public AtendimentoJuridicoIntegracaoDTO validarRespostaCadastrarAtendimentoJuridico(ResponseEntity<AtendimentoJuridicoIntegracaoDTO> response) {
		AtendimentoJuridicoIntegracaoDTO atendimentoJuridicoIntegracaoDTO = null;
		try {
			if(response.getStatusCodeValue() != HttpStatus.OK.value()) {
				throw new AtendimentoCobrancaIntegracaoServiceException(new StringBuilder(Mensagens.SERVICE_ERRO_VALIDAR_RESPOSTA_CADASTRAR_ATENDIMENTO_JURIDICO)
						.append(Constantes.CODIGO_ERRO).append(response.getStatusCodeValue()).toString(), 
							CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE);
			}else {
				atendimentoJuridicoIntegracaoDTO = response.getBody();
			}
		}catch (Exception e) {
			 throw new AtendimentoCobrancaIntegracaoServiceException(new StringBuilder(Mensagens.SERVICE_ERRO_VALIDAR_RESPOSTA_CADASTRAR_ATENDIMENTO_JURIDICO)
					 .append(e.getMessage()).toString(),  CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE);
		}
		return atendimentoJuridicoIntegracaoDTO;
	}
	
	/**
	 * Consultar status atendimento por id, acessando api de cobranca operacao
	 * @param idStatusAtendimento - Id Status Atendimento
	 * @return String - Status Atendimento
	 */
	public String consultarStatusAtendimentoPorId(Long idStatusAtendimento) {
		ResponseEntity<AtendimentoStatusJuridicoDTO> response = null;
		String status = null;
		try {
				String uri = new StringBuilder(ApiConstantes.API_BAR)
						.append(ApiConstantes.API_ATENDIMENTO_CONSULTAR_STATUS_JURIDICO_ID).append(ApiConstantes.API_BAR)
						.append(idStatusAtendimento).toString();
				response = restTemplate.exchange(discovey.url(discovey.OPERACAO_COBRANCA, discovey.CONTEXTO_OPERACAO_COBRANCA, uri),  
								HttpMethod.GET, CobrancaIntegracaoSecurityUtil.atribuirBodyAndHeader(null, request.getHeader(Constantes.AUTHORIZATION)),
									AtendimentoStatusJuridicoDTO.class);
				status = this.validarRespostaConsultarStatusAtendimentoPorId(response);
		}catch (AtendimentoCobrancaIntegracaoServiceException ei) {
			throw ei;
		}catch ( HttpClientErrorException ei) {
			throw new AtendimentoCobrancaIntegracaoCobrancaOperacaoException(new StringBuilder(Mensagens.SERVICE_ERRO_CONSULTAR_JURIDICO_STATUS_ATENDIMENTO_ID)
					.append(ei.getResponseBodyAsString()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE, HttpStatus.FORBIDDEN);
		}catch (HttpServerErrorException ei) {
			throw new AtendimentoCobrancaIntegracaoCobrancaOperacaoException(new StringBuilder(Mensagens.SERVICE_ERRO_CONSULTAR_JURIDICO_STATUS_ATENDIMENTO_ID)
					.append(ei.getResponseBodyAsString()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE);
		}catch (Exception e) {
			throw new AtendimentoCobrancaIntegracaoCobrancaOperacaoException(new StringBuilder(Mensagens.SERVICE_ERRO_CONSULTAR_JURIDICO_STATUS_ATENDIMENTO_ID)
				.append(e.getMessage()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE);
		}
		return status;
	}
	
	/**
	 * Validar resposta consultar status de atendimento por id
	 * @param response - resposta da api cobranca operacao
	 * @return String - Status do atendimento
	 */
	public String validarRespostaConsultarStatusAtendimentoPorId(ResponseEntity<AtendimentoStatusJuridicoDTO> response) {
		String status = null;
		try {
			if(response.getStatusCodeValue() != HttpStatus.OK.value()) {
				throw new AtendimentoCobrancaIntegracaoServiceException(new StringBuilder(Mensagens.SERVICE_ERRO_VALIDAR_RESPOSTA_CONSULTAR_JURIDICO_STATUS_ATENDIMENTO_ID)
						.append(Constantes.CODIGO_ERRO).append(response.getStatusCodeValue()).toString(), 
							CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE);
			}else if (response.getBody() != null) {
				status = response.getBody().getDescricao();
			}
		}catch (Exception e) {
			 throw new AtendimentoCobrancaIntegracaoServiceException(new StringBuilder(Mensagens.SERVICE_ERRO_VALIDAR_RESPOSTA_CONSULTAR_JURIDICO_STATUS_ATENDIMENTO_ID)
					 .append(e.getMessage()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE);
		}		
		return status;
	}
}