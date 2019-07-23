package com.trusthub.cobranca.application.business.atendimento;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trusthub.cobranca.application.exceptions.atendimento.AtendimentoCobrancaIntegracaoBusinessException;
import com.trusthub.cobranca.application.exceptions.atendimento.AtendimentoCobrancaIntegracaoCobrancaOperacaoException;
import com.trusthub.cobranca.application.service.arquivo.ArquivoService;
import com.trusthub.cobranca.application.service.atendimento.AtendimentoArquivoService;
import com.trusthub.cobranca.application.service.atendimento.AtendimentoJuridicoService;
import com.trusthub.cobranca.application.service.atendimento.AtendimentoTituloService;
import com.trusthub.cobranca.application.util.Constantes;
import com.trusthub.cobranca.application.util.Mensagens;
import com.trusthub.cobranca.configuration.validation.CobrancaIntegracaoError;
import com.trusthub.cobranca.configuration.validation.generic.layers.TrustHubServiceException;
import com.trusthub.cobranca.domain.arquivo.FileRequestDTO;
import com.trusthub.cobranca.domain.atendimento.AtendimentoArquivoIntegracaoDTO;
import com.trusthub.cobranca.domain.atendimento.AtendimentoJuridicoIntegracaoDTO;
import com.trusthub.cobranca.domain.atendimento.AtendimentoTitulosIntegracaoDTO;

/**
 * Classe que irá controlar toda regra do atendimento integracao juridico 
 * @author alan.franco
 */
@Component
public class AtendimentoJuridicoIntegracaoBusiness{
	
	@Autowired
	private ArquivoService arquivoService;
	@Autowired 
	private AtendimentoTituloService atendimentoTituloService;
	@Autowired
	private AtendimentoArquivoService atendimentoArquivoService;
	@Autowired
	private AtendimentoJuridicoService atendimentoJuridicoService;
	
	/**
	 * Consultar historico juridico por cedente e sacado,
	 * consulta lista de atendimento por cedente e sacado com essa lista busca status por id, dados do arquivo e dados do titulo
	 * @param idCedente - Id Cedente
	 * @param idSacado - Id Sacado
	 * @return List<AtendimentoJuridicoIntegracaoDTO> - Lista de Atendimento Juridico
	 */
	public List<AtendimentoJuridicoIntegracaoDTO> consultarHistoricoJuridicoPorCedenteSacado(String idCedente, String idSacado, Integer idTitulo) {
		List<AtendimentoJuridicoIntegracaoDTO> listaAtendimentoJuridico = new ArrayList<>();
		try {
			listaAtendimentoJuridico =	atendimentoJuridicoService.consultarHistoricoJuridicoPorCedenteSacado(idCedente, idSacado, idTitulo);
			listaAtendimentoJuridico.forEach(atendimentoJuridico -> {
				atendimentoJuridico.setStatusJuridico(atendimentoJuridicoService.consultarStatusAtendimentoPorId(atendimentoJuridico.getIdStatusJuridico()));
                atendimentoJuridico.setArquivos(atendimentoArquivoService.consultarDadosArquivo(idCedente, idSacado, atendimentoJuridico.getId(), Constantes.JURIDICO));
				atendimentoJuridico.setIdsTitulos(atendimentoTituloService.consultarDadosTituloStr(atendimentoJuridico.getId(), Constantes.JURIDICO));
			});
		} catch (TrustHubServiceException | AtendimentoCobrancaIntegracaoCobrancaOperacaoException ea) {
			throw ea;
		} catch (Exception e) {
			throw new AtendimentoCobrancaIntegracaoBusinessException(new StringBuilder(Mensagens.BUSINESS_ERRO_CONSULTAR_HISTORICO_JURIDICO_CEDENTE_SACADO)
					.append(e.getMessage()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_BUSINESS);
		}	
		return listaAtendimentoJuridico;
	}

	/**
	 * Inserir Arquivos por atendimento Juridico
	 * @param atendimento - id do atendimento
	 * @param files - arquivos
	 * @ruturn  void
	 */
	public void inserirAtendimentoJuridicoArquivo(String atendimento, List<MultipartFile> files) {
		inserirAtendimentoJuridicoArquivo(converterAtendimentoDto(atendimento, files));
	}
	
	/**
	 * Insere o atendimento juridico arquivo,
	 * Cadastra o atendimento juridico, cadastra os arquivos e os titulos
	 * @param atendimentoJuridicoRequestDto
	 * 			- Dados do atendimento juridico
	 * 			- Lista de arquivo 
	 * 			- Lista Titulos
	 * @return void
	 */
	private void inserirAtendimentoJuridicoArquivo(AtendimentoJuridicoIntegracaoDTO atendimentoJuridicoRequestDto) {
		try {
			AtendimentoJuridicoIntegracaoDTO atendimentoJuridico =	atendimentoJuridicoService.cadastrarAtendimentoJuridico(atendimentoJuridicoRequestDto);
			this.cadastrarAtendimentoArquivo(atendimentoJuridico, atendimentoJuridicoRequestDto.getArquivos());
			this.cadastrarAtendimentoTitulos(atendimentoJuridico, atendimentoJuridicoRequestDto.getIdsTitulos());
		} catch (TrustHubServiceException | AtendimentoCobrancaIntegracaoCobrancaOperacaoException ea) {
			throw ea;
		} catch (Exception e) {
			throw new AtendimentoCobrancaIntegracaoBusinessException(new StringBuilder(Mensagens.BUSINESS_ERRO_INSERIR_ATENDIMENTO_JURIDICO_ARQUIVO)
					.append(e.getMessage()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_BUSINESS);
		}	
	}
	
	/**
	 * Cadatrar o atendimento arquivo.
	 * @param atendimentoJuridico
	 * 		- Id Atendimento
	 * 		- Id Arquivo
	 * 		- Tipo Arquivo (Juridico)
	 * @param listaArquivos
	 * 		- Dados do arquivo
	 * @return void
	 */
	private void cadastrarAtendimentoArquivo(AtendimentoJuridicoIntegracaoDTO atendimentoJuridico, List<FileRequestDTO> listaArquivos) {
		try {
			listaArquivos.forEach(dto -> {
				Long idArq = arquivoService.cadastrarArquivo(dto);
				AtendimentoArquivoIntegracaoDTO atendimentoArquivoDTO = new AtendimentoArquivoIntegracaoDTO();
				atendimentoArquivoDTO.setId(atendimentoJuridico.getId());
				atendimentoArquivoDTO.setIdArquivo(idArq);
				atendimentoArquivoDTO.setTipoArquivo(Constantes.JURIDICO);
				atendimentoArquivoService.cadastrarAtendimentoArquivo(atendimentoArquivoDTO);
			});
		} catch (TrustHubServiceException | AtendimentoCobrancaIntegracaoCobrancaOperacaoException ea) {
			throw ea;
		} catch (Exception e) {
			throw new AtendimentoCobrancaIntegracaoBusinessException(new StringBuilder(Mensagens.BUSINESS_ERRO_JURIDICO_CADASTRAR_ATENDIMENTO_ARQUIVO)
					.append(e.getMessage()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_BUSINESS);
		}	
	}
	
	/**
	 * Cadastrar atendimento titulo
	 * @param atendimentoJuridico
	 * 		- Id Atendimento
	 * 		- Id Arquivo
	 * 		- Tipo Arquivo (Juridico)
	 * @param listaTitulos
	 * 		- Dados dos titulos
	 * @return void
	 */
	private void cadastrarAtendimentoTitulos(AtendimentoJuridicoIntegracaoDTO atendimentoJuridico, List<Long> listaTitulos) {
		try {
			listaTitulos.forEach(dto -> {
				AtendimentoTitulosIntegracaoDTO atendimentoTitulosIntegracaoDTO = new AtendimentoTitulosIntegracaoDTO();
				atendimentoTitulosIntegracaoDTO.setId(atendimentoJuridico.getId());
				atendimentoTitulosIntegracaoDTO.setIdTitulo(dto);
				atendimentoTitulosIntegracaoDTO.setTipoArquivo(Constantes.JURIDICO);
				atendimentoTituloService.cadastrarAtendimentoTitulo(atendimentoTitulosIntegracaoDTO);
			});
		} catch (TrustHubServiceException | AtendimentoCobrancaIntegracaoCobrancaOperacaoException ea) {
			throw ea;
		} catch (Exception e) {
			throw new AtendimentoCobrancaIntegracaoBusinessException(new StringBuilder(Mensagens.BUSINESS_ERRO_JURIDICO_CADASTRAR_ATENDIMENTO_TITULOS)
					.append(e.getMessage()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_BUSINESS);
		}	
	}
	
	/**
	 * Converter atendimento (multipartFile para DTO)
	 * @param atendimento - Id Atendimento
	 * @param files - Dados do arquivo no formato (MultipartFile)
	 * @return AtendimentoJuridicoIntegracaoDTO 
	 */
	public AtendimentoJuridicoIntegracaoDTO converterAtendimentoDto(String atendimento, List<MultipartFile> files) {
		AtendimentoJuridicoIntegracaoDTO dto = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			dto = objectMapper.readValue(atendimento, AtendimentoJuridicoIntegracaoDTO.class);
			List<FileRequestDTO> listaArquivos = new ArrayList<>();
			files.forEach(file -> {
				try {
					FileRequestDTO fileRequestDTO = new FileRequestDTO();
					fileRequestDTO.setId(3);
					fileRequestDTO.setFile(file.getBytes());
					fileRequestDTO.setName(file.getOriginalFilename());
					fileRequestDTO.setExtension(FilenameUtils.getExtension(file.getOriginalFilename()));
					listaArquivos.add(fileRequestDTO);
				}catch (IOException e) {
					throw new AtendimentoCobrancaIntegracaoBusinessException(new StringBuilder(Mensagens.BUSINESS_ERRO_JURIDICO_CONVERTER_ATENDIMENTO_DTO_IO)
							.append(e.getMessage()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_BUSINESS);
				}
			});
			dto.setArquivos(listaArquivos.isEmpty() ? new ArrayList<FileRequestDTO>() : listaArquivos);
		} catch (Exception e) {
			throw new AtendimentoCobrancaIntegracaoBusinessException(new StringBuilder(Mensagens.BUSINESS_ERRO_JURIDICO_CONVERTER_ATENDIMENTO_DTO)
					.append(e.getMessage()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_BUSINESS);
		}
		return dto;
	}

}
