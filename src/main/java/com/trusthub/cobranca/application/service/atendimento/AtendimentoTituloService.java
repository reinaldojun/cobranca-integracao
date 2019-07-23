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
import com.trusthub.cobranca.application.exceptions.atendimento.AtendimentoCobrancaIntegracaoTituloServiceException;
import com.trusthub.cobranca.application.util.ApiConstantes;
import com.trusthub.cobranca.application.util.Constantes;
import com.trusthub.cobranca.application.util.Mensagens;
import com.trusthub.cobranca.configuration.api.ClientDiscoveyServiceFactory;
import com.trusthub.cobranca.configuration.validation.CobrancaIntegracaoError;
import com.trusthub.cobranca.domain.atendimento.AtendimentoTitulosIntegracaoDTO;
import com.trusthub.cobranca.security.CobrancaIntegracaoSecurityUtil;

/**
 * Classe de servico que contem médodos para acessar api cobranca operacao (atendimento titulo)
 * @author alan.franco
 */
@Component
public class AtendimentoTituloService {
	
	@Autowired
	protected RestTemplate restTemplate;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private ClientDiscoveyServiceFactory discovey;
	
	/**
	 * Consultar dados dos titulos, acessando api cobranca operacao (titulos)
	 * @param idAtendimento - Id Atendimento
	 * @param tipoArquivo - Tipo do Arquivo
	 * @return List<String> - lista de titulo como string
	 */
	public List<Long> consultarDadosTituloStr(Integer idAtendimento, String tipoArquivo) {
		List<Long> titulos = new ArrayList<>();
		List<AtendimentoTitulosIntegracaoDTO> listaAtendimentoTitulosIntegracaoDTO = new ArrayList<>();
		try {
			listaAtendimentoTitulosIntegracaoDTO = consultarDadosTitulo(idAtendimento, tipoArquivo);
			listaAtendimentoTitulosIntegracaoDTO.forEach(tituloConsulta -> {
				titulos.add(tituloConsulta.getIdTitulo());
			});
		}catch (AtendimentoCobrancaIntegracaoTituloServiceException | AtendimentoCobrancaIntegracaoCobrancaOperacaoException ei) {
			throw ei;
		}catch (Exception e) {
			throw new AtendimentoCobrancaIntegracaoCobrancaOperacaoException(new StringBuilder(Mensagens.SERVICE_ERRO_CONSULTAR_HISTORICO_COBRANCA_CEDENTE_SACADO)
				.append(e.getMessage()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE);
		}
		return titulos;
	}
	
	/**
	 * Consultar dados dos titulos, acessando api cobranca operacao (titulos)
	 * @param idAtendimento - Id atendimento
	 * @param tipoArquivo - Tipo Arquivo
	 * @return List<AtendimentoTitulosIntegracaoDTO> - Lista de titulos atendimento.
	 */
	public List<AtendimentoTitulosIntegracaoDTO> consultarDadosTitulo(Integer idAtendimento, String tipoArquivo) {
		ResponseEntity<AtendimentoTitulosIntegracaoDTO[]> response = null;
		List<AtendimentoTitulosIntegracaoDTO> listaAtendimentoTitulosIntegracaoDTO = new ArrayList<>();
		try {
			String uri = new StringBuilder(ApiConstantes.API_BAR)
					.append(ApiConstantes.API_ATENDIMENTO_CONSULTAR_TITULO).append(ApiConstantes.API_BAR)
					.append(ApiConstantes.API_ATENDIMENTO).append(ApiConstantes.API_BAR)
					.append(idAtendimento).append(ApiConstantes.API_BAR)
					.append(ApiConstantes.API_TIPO_ARQUIVO).append(ApiConstantes.API_BAR)
					.append(tipoArquivo).toString();
		    response = restTemplate.exchange(discovey.url(discovey.OPERACAO_COBRANCA, discovey.CONTEXTO_OPERACAO_COBRANCA, uri), 
		    			HttpMethod.GET, CobrancaIntegracaoSecurityUtil.atribuirBodyAndHeader(null, request.getHeader(Constantes.AUTHORIZATION)),
		    				AtendimentoTitulosIntegracaoDTO[].class);	
		    listaAtendimentoTitulosIntegracaoDTO = this.validarRespostaConsultarDadosTitulo(response);
		}catch (AtendimentoCobrancaIntegracaoTituloServiceException ei) {
			throw ei;
		}catch (HttpClientErrorException ei) {
			throw new AtendimentoCobrancaIntegracaoCobrancaOperacaoException(new StringBuilder(Mensagens.SERVICE_ERRO_CONSULTAR_DADOS_TITULO)
					.append(ei.getResponseBodyAsString()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE, HttpStatus.FORBIDDEN);
		}catch (HttpServerErrorException ei) {
			throw new AtendimentoCobrancaIntegracaoCobrancaOperacaoException(new StringBuilder(Mensagens.SERVICE_ERRO_CONSULTAR_DADOS_TITULO)
					.append(ei.getResponseBodyAsString()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE);
		}catch (Exception e) {
			throw new AtendimentoCobrancaIntegracaoCobrancaOperacaoException(new StringBuilder(Mensagens.SERVICE_ERRO_CONSULTAR_DADOS_TITULO)
				.append(e.getMessage()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE);
		}
		return listaAtendimentoTitulosIntegracaoDTO;
	}
	
	/**
	 * Validar resposta consultar dados do titulo
	 * @param response - resposta da api cobranca operacao
	 * @return List<AtendimentoTitulosIntegracaoDTO> - lista de titulos atendimento.
	 */
	public List<AtendimentoTitulosIntegracaoDTO> validarRespostaConsultarDadosTitulo(ResponseEntity<AtendimentoTitulosIntegracaoDTO[]> response) {
		List<AtendimentoTitulosIntegracaoDTO> listaAtendimentoTitulosIntegracaoDTO = new ArrayList<>();
		try {
			if(response.getStatusCodeValue() != HttpStatus.OK.value() && response.getBody().length <= 0) {
				throw new AtendimentoCobrancaIntegracaoTituloServiceException(new StringBuilder(Mensagens.SERVICE_ERRO_VALIDAR_RESPOSTA_CONSULTAR_DADOS_TITULO)
						.append(Constantes.CODIGO_ERRO).append(response.getStatusCodeValue()).toString(), 
							CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE);
			}else {
				listaAtendimentoTitulosIntegracaoDTO = Arrays.asList(response.getBody());
			}
		}catch(Exception e) {
			 throw new AtendimentoCobrancaIntegracaoTituloServiceException(new StringBuilder(Mensagens.SERVICE_ERRO_VALIDAR_RESPOSTA_CONSULTAR_DADOS_TITULO)
					 .append(e.getMessage()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE);
		}			
		return listaAtendimentoTitulosIntegracaoDTO;
	}

	/**
	 * Cadastrar atendimento titulo acessando api cobranca operacao (titulos)
	 * @param atendimentoTitulosDTO - Dados do titulo para cadastro
	 * @return AtendimentoTitulosIntegracaoDTO - Titulo cadastrado
	 */
	public AtendimentoTitulosIntegracaoDTO cadastrarAtendimentoTitulo(AtendimentoTitulosIntegracaoDTO atendimentoTitulosDTO) {
		ResponseEntity<AtendimentoTitulosIntegracaoDTO> response = null;
		AtendimentoTitulosIntegracaoDTO atendimentoTituloIntegracaoDTO = null;
		try {
			String uri = new StringBuilder(ApiConstantes.API_BAR).append(ApiConstantes.API_ATENDIMENTO_CADASTRAR_TITULO).toString();
			response = restTemplate.exchange(discovey.url(discovey.OPERACAO_COBRANCA, discovey.CONTEXTO_OPERACAO_COBRANCA, uri), 
							HttpMethod.POST, CobrancaIntegracaoSecurityUtil.atribuirBodyAndHeader(atendimentoTitulosDTO, request.getHeader(Constantes.AUTHORIZATION)), 
								AtendimentoTitulosIntegracaoDTO.class);
			atendimentoTituloIntegracaoDTO = this.validarRespostaCadastrarAtendimentoTitulo(response);
		}catch (AtendimentoCobrancaIntegracaoTituloServiceException ei) {
			throw ei;
		}catch (HttpClientErrorException ei) {
			throw new AtendimentoCobrancaIntegracaoCobrancaOperacaoException(new StringBuilder(Mensagens.SERVICE_ERRO_CADASTRAR_ATENDIMENTO_TITULO)
					.append(ei.getResponseBodyAsString()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE, HttpStatus.FORBIDDEN);
		}catch (HttpServerErrorException ei) {
			throw new AtendimentoCobrancaIntegracaoCobrancaOperacaoException(new StringBuilder(Mensagens.SERVICE_ERRO_CADASTRAR_ATENDIMENTO_TITULO)
					.append(ei.getResponseBodyAsString()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE);
		}catch (Exception e) {
			throw new AtendimentoCobrancaIntegracaoCobrancaOperacaoException(new StringBuilder(Mensagens.SERVICE_ERRO_CADASTRAR_ATENDIMENTO_TITULO)
				.append(e.getMessage()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE);
		}
		return atendimentoTituloIntegracaoDTO;
	}
	
	/**
	 * Validar resposta do cadastro atendimento titulo
	 * @param response - dados de resposta da api cobranca operacao (titulos)
	 * @return AtendimentoTitulosIntegracaoDTO - Dados do titulo
	 */
	public AtendimentoTitulosIntegracaoDTO validarRespostaCadastrarAtendimentoTitulo(ResponseEntity<AtendimentoTitulosIntegracaoDTO> response) {
		AtendimentoTitulosIntegracaoDTO atendimentoTitulosIntegracaoDTO = null;
		try {
			if(response.getStatusCodeValue() != HttpStatus.OK.value()) {
				throw new AtendimentoCobrancaIntegracaoTituloServiceException(new StringBuilder(Mensagens.SERVICE_ERRO_VALIDAR_RESPOSTA_CADASTRAR_ATENDIMENTO_TITULO)
						.append(Constantes.CODIGO_ERRO).append(response.getStatusCodeValue()).toString(), 
						 	CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE);
			}else {
				atendimentoTitulosIntegracaoDTO = response.getBody();
			}
		}catch (Exception e) {
			 throw new AtendimentoCobrancaIntegracaoTituloServiceException(new StringBuilder(Mensagens.SERVICE_ERRO_VALIDAR_RESPOSTA_CADASTRAR_ATENDIMENTO_TITULO)
					 .append(e.getMessage()).toString(),  CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE);
		}			
		return atendimentoTitulosIntegracaoDTO;
	}

}
