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

import com.trusthub.cobranca.application.exceptions.atendimento.AtendimentoCobrancaIntegracaoArquivoServiceException;
import com.trusthub.cobranca.application.exceptions.atendimento.AtendimentoCobrancaIntegracaoCobrancaOperacaoException;
import com.trusthub.cobranca.application.util.ApiConstantes;
import com.trusthub.cobranca.application.util.Constantes;
import com.trusthub.cobranca.application.util.Mensagens;
import com.trusthub.cobranca.configuration.api.ClientDiscoveyServiceFactory;
import com.trusthub.cobranca.configuration.validation.CobrancaIntegracaoError;
import com.trusthub.cobranca.domain.arquivo.FileRequestDTO;
import com.trusthub.cobranca.domain.atendimento.AtendimentoArquivoIntegracaoDTO;
import com.trusthub.cobranca.security.CobrancaIntegracaoSecurityUtil;

/**
 * Classe de servico que contem médodos para acessar api cobranca operacao (Arquivo)
 * @author alan.franco
 */
@Component
public class AtendimentoArquivoService {
	
	@Autowired
	protected RestTemplate restTemplate;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private ClientDiscoveyServiceFactory discovey;
	
	/**
	 * Consultar dados do arquivo acessando api de cobranca operacao
	 * @param idCedente - Id Cedente
	 * @param idSacado - Id Sacado
	 * @param idAtendimento - Id Atendimento
	 * @param tipoArquivo - Tipo Arquivo
	 * @return - List<FileRequestDTO> - Lista com dados do arquivo
	 */
	public List<FileRequestDTO> consultarDadosArquivo(String idCedente, String idSacado, Integer idAtendimento, String tipoArquivo) {
		ResponseEntity<AtendimentoArquivoIntegracaoDTO[]> response = null;
		List<FileRequestDTO> listaFileRequestDTO = new ArrayList<>();
		try {
		   String uri = new StringBuilder(ApiConstantes.API_BAR)
					.append(ApiConstantes.API_ATENDIMENTO_CONSULTAR_ARQUIVO).append(ApiConstantes.API_BAR)
				    .append(ApiConstantes.API_CEDENTE).append(ApiConstantes.API_BAR)
				    .append(idCedente).append(ApiConstantes.API_BAR)
				    .append(ApiConstantes.API_SACADO).append(ApiConstantes.API_BAR)
				    .append(idSacado).append(ApiConstantes.API_BAR)
				    .append(ApiConstantes.API_ATENDIMENTO).append(ApiConstantes.API_BAR)
				    .append(idAtendimento).append(ApiConstantes.API_BAR)
				    .append(ApiConstantes.API_TIPO_ARQUIVO).append(ApiConstantes.API_BAR)
				    .append(tipoArquivo).toString();
		    response = restTemplate.exchange(discovey.url(discovey.OPERACAO_COBRANCA, discovey.CONTEXTO_OPERACAO_COBRANCA, uri),
		    							HttpMethod.GET, CobrancaIntegracaoSecurityUtil.atribuirBodyAndHeader(null, request.getHeader(Constantes.AUTHORIZATION)),
		    								AtendimentoArquivoIntegracaoDTO[].class);	
			listaFileRequestDTO = this.validarRespostaConsultarDadosArquivo(response);
		}catch (AtendimentoCobrancaIntegracaoArquivoServiceException ei) {
			throw ei;
		}catch (HttpClientErrorException ei) {
			throw new AtendimentoCobrancaIntegracaoCobrancaOperacaoException(new StringBuilder(Mensagens.SERVICE_ERRO_CONSULTAR_DADOS_ARQUIVO)
					.append(ei.getResponseBodyAsString()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE, HttpStatus.FORBIDDEN);
		}catch (HttpServerErrorException ei) {
			throw new AtendimentoCobrancaIntegracaoCobrancaOperacaoException(new StringBuilder(Mensagens.SERVICE_ERRO_CONSULTAR_DADOS_ARQUIVO)
					.append(ei.getResponseBodyAsString()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE);
		}catch (Exception e) {
			throw new AtendimentoCobrancaIntegracaoCobrancaOperacaoException(new StringBuilder(Mensagens.SERVICE_ERRO_CONSULTAR_DADOS_ARQUIVO)
				.append(e.getMessage()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE);
		}
		return listaFileRequestDTO;
	}

	/**
	 * Validar resposta consultar dados arquivo
	 * @param response - reposta da api cobranca operacao 
	 * @return List<FileRequestDTO> - Dados validados
	 */
	public List<FileRequestDTO> validarRespostaConsultarDadosArquivo(ResponseEntity<AtendimentoArquivoIntegracaoDTO[]> response) {
		List<FileRequestDTO> listaFileRequestDTO = new ArrayList<>();
		try {
			if(response.getStatusCodeValue() != HttpStatus.OK.value() && response.getBody().length <= 0) {
				throw new AtendimentoCobrancaIntegracaoArquivoServiceException(new StringBuilder(Mensagens.SERVICE_ERRO_VALIDAR_RESPOSTA_CONSULTAR_DADOS_ARQUIVO)
						.append(Constantes.CODIGO_ERRO).append(response.getStatusCodeValue()).toString(), 
							CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE);
			}else {
				Arrays.asList(response.getBody()).forEach(dto -> {
					FileRequestDTO fileRequestDTO = new FileRequestDTO();
					fileRequestDTO.setId(dto.getId().intValue());
					fileRequestDTO.setIdArquivo(dto.getIdArquivo());
					fileRequestDTO.setName(dto.getNome());
					fileRequestDTO.setExtension(dto.getExtensao());
					listaFileRequestDTO.add(fileRequestDTO);
				});
			}
		}catch(Exception e) {
			 throw new AtendimentoCobrancaIntegracaoArquivoServiceException(new StringBuilder(Mensagens.SERVICE_ERRO_VALIDAR_RESPOSTA_CONSULTAR_DADOS_ARQUIVO)
					 .append(e.getMessage()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE);
		}			
		return listaFileRequestDTO;
	}
	
	/**
	 * Cadastra atendimento arquivo, acessa api do cobranca operacao de cadastro atendimento
	 * @param atendimentoArquivoDTO - Dados para do arquivo para cadastro
	 * @return AtendimentoArquivoIntegracaoDTO - Dados cadastrados do atendimento arquivo
	 */
	public AtendimentoArquivoIntegracaoDTO cadastrarAtendimentoArquivo(AtendimentoArquivoIntegracaoDTO atendimentoArquivoDTO) {
		ResponseEntity<AtendimentoArquivoIntegracaoDTO> response = null;
		AtendimentoArquivoIntegracaoDTO atendimentoArquivoIntegracaoDTO = null;
		try {
			 StringBuilder uri = new StringBuilder(ApiConstantes.API_BAR).append(ApiConstantes.API_ATENDIMENTO_CADASTRAR_ARQUIVO);
			 response = restTemplate.exchange(discovey.url(discovey.OPERACAO_COBRANCA, discovey.CONTEXTO_OPERACAO_COBRANCA, uri.toString()),
					 					HttpMethod.POST, CobrancaIntegracaoSecurityUtil.atribuirBodyAndHeader(atendimentoArquivoDTO, request.getHeader(Constantes.AUTHORIZATION)), 
					 						AtendimentoArquivoIntegracaoDTO.class);
			 atendimentoArquivoIntegracaoDTO = this.validarRespostaCadastrarAtendimentoArquivo(response);
		}catch (AtendimentoCobrancaIntegracaoArquivoServiceException ei) {
			throw ei;
		}catch (HttpClientErrorException ei) {
			throw new AtendimentoCobrancaIntegracaoCobrancaOperacaoException(new StringBuilder(Mensagens.SERVICE_ERRO_CADASTRAR_ATENDIMENTO_ARQUIVO)
					.append(ei.getResponseBodyAsString()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE, HttpStatus.FORBIDDEN);
		}catch (HttpServerErrorException ei) {
			throw new AtendimentoCobrancaIntegracaoCobrancaOperacaoException(new StringBuilder(Mensagens.SERVICE_ERRO_CADASTRAR_ATENDIMENTO_ARQUIVO)
					.append(ei.getResponseBodyAsString()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE);
		}catch (Exception e) {
			throw new AtendimentoCobrancaIntegracaoCobrancaOperacaoException(new StringBuilder(Mensagens.SERVICE_ERRO_CADASTRAR_ATENDIMENTO_ARQUIVO)
				.append(e.getMessage()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE);
		}
		return atendimentoArquivoIntegracaoDTO;
	}
	
	/**
	 * Validar resposta do cadastro atendimento arquivo.
	 * @param response - reposta da api cobranca operacao 
	 * @return AtendimentoArquivoIntegracaoDTO - retorno do cadastra atendimento arquivo.
	 */
	public AtendimentoArquivoIntegracaoDTO validarRespostaCadastrarAtendimentoArquivo(ResponseEntity<AtendimentoArquivoIntegracaoDTO> response) {
		AtendimentoArquivoIntegracaoDTO atendimentoArquivoIntegracaoDTO = null;
		try {
			if(response.getStatusCodeValue() != HttpStatus.OK.value()) {
				throw new AtendimentoCobrancaIntegracaoArquivoServiceException(new StringBuilder(Mensagens.SERVICE_ERRO_VALIDAR_REPOSTA_CADASTRAR_ATENDIMENTO_ARQUIVO)
						.append(Constantes.CODIGO_ERRO).append(response.getStatusCodeValue()).toString(), 
						 	CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE);
			}else {
				atendimentoArquivoIntegracaoDTO = response.getBody();
			}
		}catch (Exception e) {
			 throw new AtendimentoCobrancaIntegracaoArquivoServiceException(new StringBuilder(Mensagens.SERVICE_ERRO_VALIDAR_REPOSTA_CADASTRAR_ATENDIMENTO_ARQUIVO)
					 .append(e.getMessage()).toString(),  CobrancaIntegracaoError.ERROR_COBRANCA_SERVICE);
		}			
		return atendimentoArquivoIntegracaoDTO;
	}

}
