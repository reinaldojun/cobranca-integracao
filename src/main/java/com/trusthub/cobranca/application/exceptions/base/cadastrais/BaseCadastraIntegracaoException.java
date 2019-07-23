package com.trusthub.cobranca.application.exceptions.base.cadastrais;

import org.springframework.http.HttpStatus;

import com.trusthub.cobranca.configuration.validation.generic.domain.TrustHubError;
import com.trusthub.cobranca.configuration.validation.generic.layers.TrustHubExternalIntegrationException;

/**
 *  Classe que representa exception base cadastrais integracao
 *  @author alan.franco
 */
public class BaseCadastraIntegracaoException extends TrustHubExternalIntegrationException {

	private static final long serialVersionUID = 1L;

	public BaseCadastraIntegracaoException(String msg, TrustHubError trustHubError, HttpStatus httpStatus,
			Throwable cause) {
		super(msg, trustHubError, httpStatus, cause);
	}

	public BaseCadastraIntegracaoException(String msg, TrustHubError trustHubError,
			HttpStatus httpStatus) {
		super(msg, trustHubError, httpStatus);
	}

	public BaseCadastraIntegracaoException(String msg, TrustHubError trustHubError, Throwable cause) {
		super(msg, trustHubError, cause);
	}

	public BaseCadastraIntegracaoException(String msg, TrustHubError trustHubError) {
		super(msg, trustHubError);
	}

}
