package com.trusthub.cobranca.application.exceptions.base.cadastrais;

import org.springframework.http.HttpStatus;

import com.trusthub.cobranca.configuration.validation.generic.domain.TrustHubError;
import com.trusthub.cobranca.configuration.validation.generic.layers.TrustHubBusinessException;

/**
 *  Classe que representa exception da integracao business
 *  @author alan.franco
 */
public class BaseCadastraisIntegracaoBusinessException extends TrustHubBusinessException {

	private static final long serialVersionUID = 1L;

	public BaseCadastraisIntegracaoBusinessException(String msg, TrustHubError trustHubError, HttpStatus httpStatus,
			Throwable cause) {
		super(msg, trustHubError, httpStatus, cause);
	}

	public BaseCadastraisIntegracaoBusinessException(String msg, TrustHubError trustHubError,
			HttpStatus httpStatus) {
		super(msg, trustHubError, httpStatus);
	}

	public BaseCadastraisIntegracaoBusinessException(String msg, TrustHubError trustHubError, Throwable cause) {
		super(msg, trustHubError, cause);
	}

	public BaseCadastraisIntegracaoBusinessException(String msg, TrustHubError trustHubError) {
		super(msg, trustHubError);
	}
	
}
