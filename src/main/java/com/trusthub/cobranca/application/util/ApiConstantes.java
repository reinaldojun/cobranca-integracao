package com.trusthub.cobranca.application.util;

/**
 * Constantes utilizadas nos servicos chamados 
 * @author alan.franco
 */
public interface ApiConstantes {
	
	public static final String API_BAR = "/";
	public static final String API_INTERROGACAO = "?";
	public static final String API_IGUAL = "=";
	public static final String API_ATENDIMENTO_CADASTRAR_COBRANCA = "atendimento/cobranca";
	public static final String API_ATENDIMENTO_CONSULTAR_COBRANCA = "atendimento/cobranca";
	public static final String API_ATENDIMENTO_CADASTRAR_JURIDICO = "atendimento/juridico";
	public static final String API_ATENDIMENTO_CONSULTAR_JURIDICO = "atendimento/juridico";
	public static final String API_ATENDIMENTO_CADASTRAR_ARQUIVO = "atendimento/arquivo";
	public static final String API_ATENDIMENTO_CONSULTAR_ARQUIVO = "atendimento/arquivo";
	public static final String API_ATENDIMENTO_CADASTRAR_TITULO = "atendimento/titulo";
	public static final String API_ATENDIMENTO_CONSULTAR_TITULO = "atendimento/titulo";
	public static final String API_ATENDIMENTO_CONSULTAR_CLIENTE = "cliente/cliente";
	public static final String API_ARQUIVO_CONSULTAR_ARQUIVO = "arquivo";
	public static final String API_ATENDIMENTO_CONSULTAR_STATUS_COBRANCA_ID = "atendimento/status/cobranca";
	public static final String API_ATENDIMENTO_CONSULTAR_STATUS_JURIDICO_ID = "atendimento/status/juridico";
	public static final String API_AVISO_ATENDIMENTO_CONSULTAR = "atendimento/aviso";
	public static final String API_CONTEXTO_ARQUIVO = "comumArquivo";
	public static final String API_ENDPOINT_SAVE_FILE = "inserirArquivo";
	public static final String API_ENDPOINT_FIND_FILE = "buscarConteudoArquivoPorId";
	public static final String API_ENDPOINT_CONSULTA_DOCUMENTOS_CLIENTE = "consulta-documentos-cliente";
	public static final String API_CEDENTE = "cedente";
	public static final String API_SACADO = "sacado";
	public static final String API_TITULO = "idTitulo";
	public static final String API_USUARIO = "usuario";
	public static final String API_TIPO_ARQUIVO = "tipoArquivo";
	public static final String API_ATENDIMENTO = "atendimento";
	public static final String API_VALIDAR_TOKEN = "validar/token";
	public static final String API_CONSULTAR_USUARIO_AUDITORIA_ID = "usuario/auditoria";
	public static final String API_CONSULTAR_EMPRESA_AUDITORIA_ID = "empresa/auditoria";
	
	
	
	

}
