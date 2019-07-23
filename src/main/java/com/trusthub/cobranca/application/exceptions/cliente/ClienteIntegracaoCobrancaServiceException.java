package com.trusthub.cobranca.application.exceptions.cliente;

import org.springframework.http.HttpStatus;

import com.trusthub.cobranca.configuration.validation.generic.domain.TrustHubError;
import com.trusthub.cobranca.configuration.validation.generic.layers.TrustHubServiceException;

/**
 *  Classe que representa exception da integracao service
 *  @author alan.franco
 */
public class ClienteIntegracaoCobrancaServiceException extends TrustHubServiceException {

	private static final long serialVersionUID = 1L;

	public ClienteIntegracaoCobrancaServiceException(String msg, TrustHubError trustHubError, HttpStatus httpStatus,
			Throwable cause) {
		super(msg, trustHubError, httpStatus, cause);
	}

	public ClienteIntegracaoCobrancaServiceException(String msg, TrustHubError trustHubError,
			HttpStatus httpStatus) {
		super(msg, trustHubError, httpStatus);
	}

	public ClienteIntegracaoCobrancaServiceException(String msg, TrustHubError trustHubError, Throwable cause) {
		super(msg, trustHubError, cause);
	}

	public ClienteIntegracaoCobrancaServiceException(String msg, TrustHubError trustHubError) {
		super(msg, trustHubError);
	}

}
