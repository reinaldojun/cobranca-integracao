package com.trusthub.cobranca.application.util;


/**
 * Inteface que centraliza as as mensagens utilizada no integracao cobranca
 * @author alan.franco
 */
public interface Mensagens {
	
	
	//BUSINESS
	public static final String  BUSINESS_ERRO_CONSULTAR_DOCUMENTO_CLIENTE =	"Erro ao consultar documento cliente no método (DocumentoClientelntegracaoBusiness.consultarDocumentoCliente): ";
	public static final String  BUSINESS_ERRO_CONSULTAR_HISTORICO_COBRANCA_CEDENTE_SACADO =	"Erro ao consultar historico cobranca por cedente e sacado no método (AtendimentoCobrancaIntegracaoBusiness.consultarHistoricoCobrancaPorCedenteSacado): ";
	public static final String  BUSINESS_ERRO_CONSULTAR_AVISO_ATENDIMENTO_COBRANCA = "Erro ao consultar aviso atendimento cobranca no método (AvisoAtendimentoIntegracaoBusiness.consultarAvisoAtendimentoCobranca): ";
	public static final String  BUSINESS_ERRO_CONSULTAR_AVISO_ATENDIMENTO_JURIDICO = "Erro ao consultar aviso atendimento juridico no método (AvisoAtendimentoIntegracaoBusiness.consultarAvisoAtendimentoJuridico): ";
	public static final String  BUSINESS_ERRO_CONSULTAR_HISTORICO_JURIDICO_CEDENTE_SACADO =	"Erro ao consultar historico juridico por cedente e sacado no método (AtendimentoJuridicoIntegracaoBusiness.consultarHistoricoJuridicoPorCedenteSacado): ";
	public static final String  BUSINESS_ERRO_INSERIR_ATENDIMENTO_COBRANCA_ARQUIVO =	"Erro ao inserir atendimento cobranca arquivo no método (AtendimentoCobrancaIntegracaoBusiness.inserirAtendimentoCobrancaArquivo): ";
	public static final String  BUSINESS_ERRO_INSERIR_ATENDIMENTO_JURIDICO_ARQUIVO =	"Erro ao inserir atendimento juridico arquivo no método (AtendimentoJuridicoIntegracaoBusiness.inserirAtendimentoJuridicoArquivo): ";
	public static final String  BUSINESS_ERRO_COBRANCA_CONVERTER_ATENDIMENTO_DTO =	"Erro ao converter atendimento dto no método (AtendimentoCobrancaIntegracaoBusiness.converterAtendimentoDto): ";
	public static final String  BUSINESS_ERRO_COBRANCA_CONVERTER_ATENDIMENTO_DTO_IO = "Erro ao converter atendimento dto  I/O no método (AtendimentoCobrancaIntegracaoBusiness.converterAtendimentoDto): ";
	public static final String  BUSINESS_ERRO_JURIDICO_CONVERTER_ATENDIMENTO_DTO =	"Erro ao converter atendimento dto no método (AtendimentoJuridicoIntegracaoBusiness.converterAtendimentoDto): ";
	public static final String  BUSINESS_ERRO_JURIDICO_CONVERTER_ATENDIMENTO_DTO_IO = "Erro ao converter atendimento dto  I/O no método (AtendimentoJuridicoIntegracaoBusiness.converterAtendimentoDto): ";
	public static final String  BUSINESS_ERRO_COBRANCA_CADASTRAR_ATENDIMENTO_ARQUIVO = "Erro ao cadastrar atendimento arquivo no método (AtendimentoCobrancaIntegracaoBusiness.cadastrarAtendimentoArquivo) ";
	public static final String  BUSINESS_ERRO_JURIDICO_CADASTRAR_ATENDIMENTO_ARQUIVO = "Erro ao cadastrar atendimento arquivo no método (AtendimentoJuridicoIntegracaoBusiness.cadastrarAtendimentoArquivo) ";
	public static final String  BUSINESS_ERRO_COBRANCA_CADASTRAR_ATENDIMENTO_TITULOS = "Erro ao cadastrar atendimento titulos no método (AtendimentoCobrancaIntegracaoBusiness.cadastrarAtendimentoTitulos) ";
	public static final String  BUSINESS_ERRO_JURIDICO_CADASTRAR_ATENDIMENTO_TITULOS = "Erro ao cadastrar atendimento titulos no método (AtendimentoJuridicoIntegracaoBusiness.cadastrarAtendimentoTitulos) ";
	
	//SERVICE
	public static final String  SERVICE_ERRO_CONSULTAR_CONTEUDO_ARQUIVO_ID =	"Erro ao consultar conteudo do arquivo por id no método (ArquivoService.consultarConteudoArquivoId): ";
	public static final String  SERVICE_ERRO_CONSULTAR_DADOS_ARQUIVO =	"Erro consultar dados a arquivo no método (AtendimentoArquivoService.consultarDadosArquivo): ";
	public static final String  SERVICE_ERRO_CONSULTAR_HISTORICO_COBRANCA_CEDENTE_SACADO = "Erro ao consultar historico cobranca por cedente e sacado no método (AtendimentoCobrancaService.consultarHistoricoCobrancaPorCedenteSacado): ";
	public static final String  SERVICE_ERRO_CONSULTAR_JURIDICO_STATUS_ATENDIMENTO_ID = "Erro consultar status atendimento juridico por id no método (AtendimentoJuridicoService.consultarStatusAtendimentoPorId): ";
	public static final String  SERVICE_ERRO_CONSULTAR_COBRANCA_STATUS_ATENDIMENTO_ID = "Erro consultar status atendimento cobranca por id no método (AtendimentoCobrancaService.consultarStatusAtendimentoPorId): ";
	public static final String  SERVICE_ERRO_CONSULTAR_DOCUMENTO_CLIENTE =	"Erro ao consultar documento cleinte no método (ClienteDocumentoService.consultarDocumentoCliente): ";
	public static final String  SERVICE_ERRO_CONSULTAR_HISTORICO_JURIDICO_CEDENTE_SACADO = "Erro ao consultar historico juridico por cedente e sacado no método (AtendimentoJuridicoService.consultarHistoricoJuridicoPorCedenteSacado): ";
	public static final String  SERVICE_ERRO_CONSULTAR_DADOS_TITULO = "Erro ao consultar dados do titulo no método (AtendimentoTituloService.consultarDadosTitulo): ";
	public static final String  SERVICE_ERRO_CONSULTAR_CLIENTE = "Erro ao consultar clientes no método (ClienteService.consultarCliente): ";
	public static final String  SERVICE_ERRO_CONSULTAR_ARQUIVO = "Erro ao consultar arquivos no método (ArquivoCobrancaService.consultarArquivo): ";
	public static final String  SERVICE_ERRO_VALIDAR_RESPOSTA_CONSULTAR_ARQUIVO = "Erro ao validar resposta consultar arquivo no método (ArquivoCobrancaService.validarRespostaconsultarArquivo): ";
	public static final String  SERVICE_ERRO_VALIDAR_RESPOSTA_CADASTRAR_ARQUIVO = "Erro ao validar resposta cadastrar arquivo no método (ArquivoService.validarRespostaCadastrarArquivo): ";
	public static final String  SERVICE_ERRO_VALIDAR_RESPOSTA_CONSULTAR_CONTEUDO_ARQUIVO_ID = "Erro ao validar resposta consultar conteudo arquivo por id no método (ArquivoService.validarRespostaConsultarConteudoArquivoId): ";
	public static final String  SERVICE_ERRO_VALIDAR_RESPOSTA_CONSULTAR_CLIENTE = "Erro ao validar resposta consultar cliente no método (ClienteService.validarRespostaConsultarCliente): ";
	public static final String  SERVICE_ERRO_VALIDAR_RESPOSTA_CONSULTAR_DOCUMENTO_CLIENTE =	"Erro ao validar resposta da consultar documento cliente no método (ClienteDocumentoService.validarRespostaConsultarDocumentoCliente): ";
	public static final String  SERVICE_ERRO_VALIDAR_RESPOSTA_CONSULTAR_DADOS_ARQUIVO =	"Erro ao validar resposta da consulta dados arquivo no método (AtendimentoArquivoService.validarRespostaConsultarDadosArquivo): ";
	public static final String  SERVICE_ERRO_VALIDAR_REPOSTA_CADASTRAR_ATENDIMENTO_ARQUIVO = "Erro ao validar resposta cadatrar atendimento arquivo no método (AtendimentoArquivoService.validarRespostaCadastrarAtendimentoArquivo): ";
	public static final String  SERVICE_ERRO_VALIDAR_RESPOSTA_CONSULTAR_HISTORICO_COBRANCA_CEDENTE_SACADO = "Erro ao validar resposta consultar historico cobranca por cedente e sacado no método (AtendimentoCobrancaService.validarRespostaConsultarHistoricoCobrancaPorCedenteSacado): ";
    public static final String  SERVICE_ERRO_VALIDAR_RESPOSTA_CADASTRAR_ATENDIMENTO_COBRANCA = "Erro ao validar resposta cadastro atendimento cobranca no método (AtendimentoCobrancaService.validarRespostaCadastrarAtendimentoCobranca); ";
	public static final String  SERVICE_ERRO_VALIDAR_RESPOSTA_CONSULTAR_JURIDICO_STATUS_ATENDIMENTO_ID = "Erro ao validar resposta da consulta juridico por status do atendimento por id no método (AtendimentoJuridicoService.validarRespostaConsultarStatusAtendimentoPorId): ";
	public static final String  SERVICE_ERRO_VALIDAR_RESPOSTA_CONSULTA_COBRANCA_STATUS_ATENDIMENTO_ID = "Erro ao validar resposta da consulta cobranca por status do atendimento por id no método (AtendimentoCobrancaService.validarRespostaConsultarStatusAtendimentoPorId): ";
	public static final String  SERVICE_ERRO_VALIDAR_RESPOSTA_CONSULTAR_HISTORICO_JURIDICO_CEDENTE_SACADO = "Erro ao validar resposta consultar historico juridico por cedente e sacado no método (AtendimentoJuridicoService.validarRespostaConsultarHistoricoJuridicoPorCedenteSacado): ";
	public static final String  SERVICE_ERRO_VALIDAR_RESPOSTA_CADASTRAR_ATENDIMENTO_JURIDICO = "Erro ao validar resposta cadastro atendimento juridico no método (AtendimentoJuridicoService.validarRespostaCadastrarAtendimentoJuridico): ";
	public static final String  SERVICE_ERRO_VALIDAR_RESPOSTA_CONSULTAR_DADOS_TITULO = "Erro ao validar resposta consultar dados do titulo no método (AtendimentoTituloService.validarRespostaConsultarDadosTitulo): ";
    public static final String  SERVICE_ERRO_VALIDAR_RESPOSTA_CADASTRAR_ATENDIMENTO_TITULO = "Erro ao validar resposta cadastrar atendimento titulo no método (AtendimentoTituloService.validarRespostaCadastrarAtendimentoTitulo): ";
	public static final String  SERVICE_ERRO_CADASTRAR_ATENDIMENTO_ARQUIVO = "Erro cadastrar atendimento arquivo no método (AtendimentoArquivoService.cadastrarAtendimentoArquivo): ";
	public static final String  SERVICE_ERRO_CADASTRAR_ATENDIMENTO_COBRANCA = "Erro ao cadastrar atendimento cobranca no método (AtendimentoCobrancaService.cadastrarAtendimentoCobranca): ";
	public static final String  SERVICE_ERRO_CADASTRAR_ARQUIVO =	"Erro ao cadastrar arquivos no método (ArquivoService.cadastrarArquivo): ";
	public static final String  SERVICE_ERRO_CADASTRAR_ATENDIMENTO_JURIDICO = "Erro ao cadastrar atendimento juridico no método (AtendimentoJuridicoService.cadastrarAtendimentoJuridico): ";
	public static final String  SERVICE_ERRO_CADASTRAR_ATENDIMENTO_TITULO = "Erro cadastrar atendimento titulo no método (AtendimentoTituloService.cadastrarAtendimentoTitulo): ";
	public static final String  SERVICE_ERRO_CONSULTAR_AVISO_ATENDIMENTO_COBRANCA = "Erro ao consultar aviso atendimento cobranca no método (AvisoAtendimentoService.consultarAvisoAtendimentoCobranca): ";
	public static final String  SERVICE_ERRO_CONSULTAR_AVISO_ATENDIMENTO_JURIDICO = "Erro ao consultar aviso atendimento jurídico no método (AvisoAtendimentoService.consultarAvisoAtendimentoJuridico): ";
	public static final String  SERVICE_ERRO_VALIDAR_RESPOSTA_CONSULTAR_AVISO_ATENDIMENTO_COBRANCA_JURIDICO = "Erro ao validar resposta consultar aviso atendimentos (cobranca/juridico) no método (AvisoAtendimentoService.validarRespostaConsultarAvisoAtendimento): ";
	
	//SECURITY
	public static final String  SECURITY_NO_AUTHORIZATION = "Authorization nulo ou vazio.";
	public static final String  SECURITY_TOKEN_INVALID = "Token Invalido.";
	public static final String  SECURITY_PERFIL_NO_AUTHORIZATION = "Perfil não autorizado.";
	public static final String  SECURITY_VALIDAR_TOKEN = "Erro no na validação do token no método: (CobrancaIntegracaoSecurityService.validarToken)";
	public static final String  SECURITY_ERRO_VALIDAR_RESPOSTA_VALIDAR_TOKEN = "Erro ao validar resposta da validacao do token no metodo: (CobrancaIntegracaoSecurityService.validarRespostaValidarToken)";
	
}
