package com.trusthub.cobranca.configuration.api;

import org.springframework.beans.factory.annotation.Value;

/**
 * Interface que fica o contexto e uri das aplicacoes acessadas 
 * @author alan.franco
 */
public class Integracao {
	
	@Value("${app.cobranca.integracao.operacao.cobranca.contexto}")
	public String CONTEXTO_OPERACAO_COBRANCA;
	
	@Value("${app.cobranca.integracao.operacao.cobranca.uri}")
	public String OPERACAO_COBRANCA;

	@Value("${app.cobranca.integracao.operacao.cobranca.contexto}")
	public String CONTEXTO_INTEGRACAO_COBRANCA;
	
	@Value("${app.cobranca.integracao.cobranca.uri}")
	public String INTEGRACAO_COBRANCA;
	
	@Value("${app.cobranca.integracao.arquivo.contexto}")
	public String CONTEXTO_INTEGRACAO_ARQUIVO;
	
	@Value("${app.cobranca.integracao.arquivo.uri}")
	public String INTEGRACAO_ARQUIVO;
	
	@Value("${app.cobranca.integracao.base.cadastral.contexto}")
	public String CONTEXTO_INTEGRACAO_BASE_CADASTRAL;
	
	@Value("${app.cobranca.integracao.base.cadastral.uri}")
	public String INTEGRACAO_BASE_CADASTRAL;
	
	@Value("${app.cobranca.integracao.cobranca.acesso.contexto}")
	public String CONTEXTO_INTEGRACAO_COBRANCA_ACESSO;
	
	@Value("${app.cobranca.integracao.cobranca.acesso.uri}")
	public String INTEGRACAO_COBRANCA_ACESSO;
	
}
