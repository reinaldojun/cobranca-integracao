package com.trusthub.cobranca.application.exceptions.cliente;

import org.springframework.http.HttpStatus;

import com.trusthub.cobranca.configuration.validation.generic.domain.TrustHubError;
import com.trusthub.cobranca.configuration.validation.generic.layers.TrustHubInternalIntegrationException;

/**
 *  Classe que representa exception da integracao com o cobranca operacao cliente
 *  @author alan.franco
 */
public class ClienteIntegracaoCobrancaOperacaoException extends TrustHubInternalIntegrationException {

	private static final long serialVersionUID = 1L;

	public ClienteIntegracaoCobrancaOperacaoException(String msg, TrustHubError trustHubError, HttpStatus httpStatus,
			Throwable cause) {
		super(msg, trustHubError, httpStatus, cause);
	}

	public ClienteIntegracaoCobrancaOperacaoException(String msg, TrustHubError trustHubError,
			HttpStatus httpStatus) {
		super(msg, trustHubError, httpStatus);
	}

	public ClienteIntegracaoCobrancaOperacaoException(String msg, TrustHubError trustHubError, Throwable cause) {
		super(msg, trustHubError, cause);
	}

	public ClienteIntegracaoCobrancaOperacaoException(String msg, TrustHubError trustHubError) {
		super(msg, trustHubError);
	}

}
