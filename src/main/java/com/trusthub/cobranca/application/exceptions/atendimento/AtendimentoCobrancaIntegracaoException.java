package com.trusthub.cobranca.application.exceptions.atendimento;

import org.springframework.http.HttpStatus;

import com.trusthub.cobranca.configuration.validation.generic.TrustHubException;
import com.trusthub.cobranca.configuration.validation.generic.domain.TrustHubError;

/**
 *  Classe que representa exception da integracao 
 *  @author alan.franco
 */
public class AtendimentoCobrancaIntegracaoException extends TrustHubException {

	private static final long serialVersionUID = 1L;

	public AtendimentoCobrancaIntegracaoException(String msg, TrustHubError trustHubError, HttpStatus httpStatus,
			Throwable cause) {
		super(msg, trustHubError, httpStatus, cause);
	}

	public AtendimentoCobrancaIntegracaoException(String msg, TrustHubError trustHubError, HttpStatus httpStatus) {
		super(msg, trustHubError, httpStatus);
	}

	public AtendimentoCobrancaIntegracaoException(String msg, TrustHubError trustHubError, Throwable cause) {
		super(msg, trustHubError, cause);
	}

	public AtendimentoCobrancaIntegracaoException(String msg, TrustHubError trustHubError) {
		super(msg, trustHubError);
	}

}
