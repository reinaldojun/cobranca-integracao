package com.trusthub.cobranca.application.exceptions.arquivo;

import org.springframework.http.HttpStatus;

import com.trusthub.cobranca.configuration.validation.generic.domain.TrustHubError;
import com.trusthub.cobranca.configuration.validation.generic.layers.TrustHubServiceException;

/**
 *  Classe que representa exception da integracao service
 *  @author alan.franco
 */
public class ArquivoIntegracaoServiceException extends TrustHubServiceException {

	private static final long serialVersionUID = 1L;

	public ArquivoIntegracaoServiceException(String msg, TrustHubError trustHubError, HttpStatus httpStatus,
			Throwable cause) {
		super(msg, trustHubError, httpStatus, cause);
	}

	public ArquivoIntegracaoServiceException(String msg, TrustHubError trustHubError,
			HttpStatus httpStatus) {
		super(msg, trustHubError, httpStatus);
	}

	public ArquivoIntegracaoServiceException(String msg, TrustHubError trustHubError, Throwable cause) {
		super(msg, trustHubError, cause);
	}

	public ArquivoIntegracaoServiceException(String msg, TrustHubError trustHubError) {
		super(msg, trustHubError);
	}

}
