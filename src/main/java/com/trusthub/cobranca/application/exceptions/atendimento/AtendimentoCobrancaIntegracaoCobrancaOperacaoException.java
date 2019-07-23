package com.trusthub.cobranca.application.exceptions.atendimento;

import org.springframework.http.HttpStatus;

import com.trusthub.cobranca.configuration.validation.generic.domain.TrustHubError;
import com.trusthub.cobranca.configuration.validation.generic.layers.TrustHubInternalIntegrationException;

/**
 *  Classe que representa exception da integracao com cobranca operacao
 *  @author alan.franco
 */
public class AtendimentoCobrancaIntegracaoCobrancaOperacaoException extends TrustHubInternalIntegrationException {

	private static final long serialVersionUID = 1L;

	public AtendimentoCobrancaIntegracaoCobrancaOperacaoException(String msg, TrustHubError trustHubError,
			HttpStatus httpStatus, Throwable cause) {
		super(msg, trustHubError, httpStatus, cause);
	}

	public AtendimentoCobrancaIntegracaoCobrancaOperacaoException(String msg, TrustHubError trustHubError,
			HttpStatus httpStatus) {
		super(msg, trustHubError, httpStatus);
	}

	public AtendimentoCobrancaIntegracaoCobrancaOperacaoException(String msg, TrustHubError trustHubError,
			Throwable cause) {
		super(msg, trustHubError, cause);
	}

	public AtendimentoCobrancaIntegracaoCobrancaOperacaoException(String msg, TrustHubError trustHubError) {
		super(msg, trustHubError);
	}

}
