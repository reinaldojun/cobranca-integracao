package com.trusthub.cobranca.application.service.atendimento;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
import com.trusthub.cobranca.configuration.util.Helper;
import com.trusthub.cobranca.configuration.validation.CobrancaIntegracaoError;
import com.trusthub.cobranca.domain.atendimento.AvisoAtendimentoIntegracaoDTO;
import com.trusthub.cobranca.security.CobrancaIntegracaoSecurityUtil;

/**
 * Classe de servico que contem médodos para acessar api cobranca operacao (aviso atendimento cobranca/juridico)
 * @author jose.viana
 */
@Component
public class AvisoAtendimentoService {
	
	@Autowired
	protected RestTemplate restTemplate;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private ClientDiscoveyServiceFactory discovey;

	/**
	 * Consultar aviso atendimento cobranca
	 * @param idUsuario - Identificador do Usuario
	 * @return List<AvisoAtendimentoIntegracaoDTO> - Lista de avisos atendimentos cobranca
	 */
	public List<AvisoAtendimentoIntegracaoDTO> consultarAvisoAtendimentoCobranca(String idUsuario){
		ResponseEntity<AvisoAtendimentoIntegracaoDTO[]> response = null;
		List<AvisoAtendimentoIntegracaoDTO> listaAvisoAtendimento = new ArrayList<>();
		try {
			 	String uri = new StringBuilder(ApiConstantes.API_BAR)
			 			.append(ApiConstantes.API_AVISO_ATENDIMENTO_CONSULTAR).append(ApiConstantes.API_BAR)
			 			.append(ApiConstantes.API_USUARIO).append(ApiConstantes.API_BAR)
			 			.append(idUsuario).toString();
				response = restTemplate.exchange(discovey.url(discovey.OPERACAO_COBRANCA, discovey.CONTEXTO_OPERACAO_COBRANCA, uri),  
								HttpMethod.GET, CobrancaIntegracaoSecurityUtil.atribuirBodyAndHeader(null, request.getHeader(Constantes.AUTHORIZATION)),
									AvisoAtendimentoIntegracaoDTO[].class);
				List<AvisoAtendimentoIntegracaoDTO> listaAvisoAtendimentoIntegracaoDTO = this.validarRespostaConsultarAvisoAtendimento(response);
			    listaAvisoAtendimento = listaAvisoAtendimentoIntegracaoDTO.stream()
													                        .filter( Helper.distinctByKeys(AvisoAtendimentoIntegracaoDTO :: getIdAtendimento, AvisoAtendimentoIntegracaoDTO :: getTipoAtendimento))
													                        .collect( Collectors.toList() );
				 for(AvisoAtendimentoIntegracaoDTO atendimento : listaAvisoAtendimentoIntegracaoDTO) {
					 listaAvisoAtendimento.forEach(atendimentoDisinct -> {
						 if (atendimentoDisinct.getIdAtendimento()   == atendimento.getIdAtendimento() && 
						     atendimentoDisinct.getTipoAtendimento().equals(atendimento.getTipoAtendimento())) {
							 atendimentoDisinct.getIdsTitulos().add(atendimento.getIdTitulo());
						 }
					 });
					 atendimento.setIdTitulo(0);
				 }
		}catch (AtendimentoCobrancaIntegracaoServiceException ei) {
			throw ei;
		}catch (HttpClientErrorException ei) {
			throw new AtendimentoCobrancaIntegracaoCobrancaOperacaoException(new StringBuilder(Mensagens.SERVICE_ERRO_CONSULTAR_AVISO_ATENDIMENTO_COBRANCA)
					.append(ei.getResponseBodyAsString()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE, HttpStatus.FORBIDDEN);
		}catch (HttpServerErrorException ei) {
			throw new AtendimentoCobrancaIntegracaoCobrancaOperacaoException(new StringBuilder(Mensagens.SERVICE_ERRO_CONSULTAR_AVISO_ATENDIMENTO_COBRANCA)
					.append(ei.getResponseBodyAsString()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE);
		}catch (Exception e) {
			throw new AtendimentoCobrancaIntegracaoCobrancaOperacaoException(new StringBuilder(Mensagens.SERVICE_ERRO_CONSULTAR_AVISO_ATENDIMENTO_COBRANCA)
				.append(e.getMessage()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE);
		}
		return listaAvisoAtendimento;
	}
	
		
	/**
	 * Validar resposta da consulta aviso atendimento cobranca/juridico
	 * @param response - resposta da api cobranca operacao
	 * @return - List<AtendimentoCobrancaIntegracaoDTO> - lista com os dados do atendimento cobranca
	 */
	public List<AvisoAtendimentoIntegracaoDTO> validarRespostaConsultarAvisoAtendimento(ResponseEntity<AvisoAtendimentoIntegracaoDTO[]> response) {
		List<AvisoAtendimentoIntegracaoDTO> listaAvisoAtendimentoIntegracaoDTO = new ArrayList<>();
		try {
			if(response.getStatusCodeValue() != HttpStatus.OK.value()) {
				throw new AtendimentoCobrancaIntegracaoServiceException(new StringBuilder(Mensagens.SERVICE_ERRO_VALIDAR_RESPOSTA_CONSULTAR_AVISO_ATENDIMENTO_COBRANCA_JURIDICO)
						.append(Constantes.CODIGO_ERRO).append(response.getStatusCodeValue()).toString(), 
						 CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE);
			}else if (response.getBody() != null && response.getBody().length > 0) {
				listaAvisoAtendimentoIntegracaoDTO = Arrays.asList(response.getBody());
			}
		}catch (Exception e) {
			 throw new AtendimentoCobrancaIntegracaoServiceException(new StringBuilder(Mensagens.SERVICE_ERRO_VALIDAR_RESPOSTA_CONSULTAR_AVISO_ATENDIMENTO_COBRANCA_JURIDICO)
					 .append(e.getMessage()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE);	
		}				
		return listaAvisoAtendimentoIntegracaoDTO;
	}
	
}
