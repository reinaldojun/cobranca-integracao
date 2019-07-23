package com.trusthub.cobranca.configuration.validation;

import com.trusthub.cobranca.configuration.validation.generic.domain.TrustHubError;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Classe que representa o codigo e descricao das camadas ou integracoes
 * @author alan.franco
 */
@Getter
@AllArgsConstructor
public enum CobrancaIntegracaoError implements TrustHubError {

	ERROR_COBRANCA_BUSINESS(1, "ERRO NA API (COBRANCA INTEGRACAO) NA CAMADA BUSINESS"), 
	ERROR_COBRANCA_SERVICE(2, "ERRO NA API (COBRANCA INTEGRACAO) NA CAMADA SERVICE"),
	ERROR_COBRANCA_INTEGRACAO_COBRANCA_OPERACAO(3, "ERRO NA API (COBRANCA INTEGRACAO) NA INTEGRACAO COM O COBRANCA OPERACAO"),
	ERROR_COBRANCA_INTEGRACAO_ARQUIVO(4, "ERRO NA API (COBRANCA INTEGRACAO) NA INTEGRACAO COM O COMPONENTE DE ARQUIVOS"),
	ERROR_COBRANCA_INTEGRACAO_BASE_CADASTRAL(5, "ERRO NA API (COBRANCA INTEGRACAO) NA INTEGRACAO COM BASE CADASTRAIS"),
	ERROR_COBRANCA_SECURITY(6, "ERRO NA API (COBRANCA INTEGRACAO) ERRO - COBRANCA ACESSO");
	 
	Integer errorCode;
	String errorDescription;

	@Override
	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	@Override
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
}
