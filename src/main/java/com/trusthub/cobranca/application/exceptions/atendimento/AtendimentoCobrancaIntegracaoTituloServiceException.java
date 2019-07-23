package com.trusthub.cobranca.application.exceptions.atendimento;

import org.springframework.http.HttpStatus;

import com.trusthub.cobranca.configuration.validation.generic.domain.TrustHubError;
import com.trusthub.cobranca.configuration.validation.generic.layers.TrustHubServiceException;


/**
 *  Classe que representa exception da integracao com arquivo
 *  @author alan.franco
 */
public class AtendimentoCobrancaIntegracaoTituloServiceException extends TrustHubServiceException {

	private static final long serialVersionUID = 1L;

	public AtendimentoCobrancaIntegracaoTituloServiceException(String msg, TrustHubError trustHubError, HttpStatus httpStatus,
			Throwable cause) {
		super(msg, trustHubError, httpStatus, cause);
	}

	public AtendimentoCobrancaIntegracaoTituloServiceException(String msg, TrustHubError trustHubError,
			HttpStatus httpStatus) {
		super(msg, trustHubError, httpStatus);
	}

	public AtendimentoCobrancaIntegracaoTituloServiceException(String msg, TrustHubError trustHubError, Throwable cause) {
		super(msg, trustHubError, cause);
	}

	public AtendimentoCobrancaIntegracaoTituloServiceException(String msg, TrustHubError trustHubError) {
		super(msg, trustHubError);
	}

}
