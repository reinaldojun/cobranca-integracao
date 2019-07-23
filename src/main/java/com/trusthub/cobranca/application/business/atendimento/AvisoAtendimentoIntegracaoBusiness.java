package com.trusthub.cobranca.application.business.atendimento;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trusthub.cobranca.application.exceptions.atendimento.AtendimentoCobrancaIntegracaoBusinessException;
import com.trusthub.cobranca.application.exceptions.atendimento.AtendimentoCobrancaIntegracaoCobrancaOperacaoException;
import com.trusthub.cobranca.application.service.atendimento.AvisoAtendimentoService;
import com.trusthub.cobranca.application.util.Mensagens;
import com.trusthub.cobranca.configuration.validation.CobrancaIntegracaoError;
import com.trusthub.cobranca.configuration.validation.generic.layers.TrustHubServiceException;
import com.trusthub.cobranca.domain.atendimento.AvisoAtendimentoIntegracaoDTO;

/**
 * Classe que irá controlar toda regra dos avisos atendimentos de cobranca 
 * @author jose.viana
 */
@Component
public class AvisoAtendimentoIntegracaoBusiness {

	@Autowired
	private AvisoAtendimentoService avisoAtendimentoService;
	
	/**
	 * Consultar aviso atendimento cobranca por usuario,
	 * @param idUsuario - Identificador de Usuario
	 * @return List<AvisoAtendimentoIntegracaoDTO> - Lista de avisdo atendimentos de Cobranca
	 */
	public List<AvisoAtendimentoIntegracaoDTO> consultarAvisoAtendimento(String idUsuario) {
		List<AvisoAtendimentoIntegracaoDTO> listaAvisoAtendimentoIntegracaoDTO = new ArrayList<>();
		try {
			listaAvisoAtendimentoIntegracaoDTO = avisoAtendimentoService.consultarAvisoAtendimentoCobranca(idUsuario);
		} catch (TrustHubServiceException | AtendimentoCobrancaIntegracaoCobrancaOperacaoException ea) {
			throw ea;
		} catch (Exception e) {
			throw new AtendimentoCobrancaIntegracaoBusinessException(new StringBuilder(Mensagens.BUSINESS_ERRO_CONSULTAR_AVISO_ATENDIMENTO_COBRANCA)
					.append(e.getMessage()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_BUSINESS);
		}			
		return listaAvisoAtendimentoIntegracaoDTO;
	}
	
	
}
