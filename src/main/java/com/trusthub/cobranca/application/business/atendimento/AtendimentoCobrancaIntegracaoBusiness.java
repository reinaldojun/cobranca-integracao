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
import com.trusthub.cobranca.application.service.atendimento.AtendimentoCobrancaService;
import com.trusthub.cobranca.application.service.atendimento.AtendimentoTituloService;
import com.trusthub.cobranca.application.util.Constantes;
import com.trusthub.cobranca.application.util.Mensagens;
import com.trusthub.cobranca.configuration.validation.CobrancaIntegracaoError;
import com.trusthub.cobranca.configuration.validation.generic.layers.TrustHubServiceException;
import com.trusthub.cobranca.domain.arquivo.FileRequestDTO;
import com.trusthub.cobranca.domain.atendimento.AtendimentoArquivoIntegracaoDTO;
import com.trusthub.cobranca.domain.atendimento.AtendimentoCobrancaIntegracaoDTO;
import com.trusthub.cobranca.domain.atendimento.AtendimentoTitulosIntegracaoDTO;
import com.trusthub.cobranca.security.CobrancaIntegracaoSecurityException;

/**
 * Classe que irá controlar toda regra do atendimento integracao cobranca 
 * @author alan.franco
 */
@Component
public class AtendimentoCobrancaIntegracaoBusiness {

	@Autowired
	private ArquivoService arquivoService;
	@Autowired 
	private AtendimentoTituloService atendimentoTituloService;
	@Autowired
	private AtendimentoArquivoService atendimentoArquivoService;
	@Autowired
	private AtendimentoCobrancaService atendimentoCobrancaService;
	
	/**
	 * Consultar historico cobranca por cedente e sacado,
	 * consulta lista de atendimento por cedente e sacado com essa lista busca status por id, dados do arquivo e dados do titulo
	 * @param idCedente - Id Cedente
	 * @param idSacado - Id Sacado
	 * @return List<AtendimentoCobrancaIntegracaoDTO> - Lista de Atendimento Cobranca
	 */
	public List<AtendimentoCobrancaIntegracaoDTO> consultarHistoricoCobrancaPorCedenteSacado(String idCedente, String idSacado, Integer idTitulo) {
		List<AtendimentoCobrancaIntegracaoDTO> listaAtendimentoCobranca = new ArrayList<>();
		try {
			listaAtendimentoCobranca = atendimentoCobrancaService.consultarHistoricoCobrancaPorCedenteSacado(idCedente, idSacado, idTitulo);
			listaAtendimentoCobranca.forEach(atendimentoCobranca -> {
				atendimentoCobranca.setStatusAtendimento(atendimentoCobrancaService.consultarStatusAtendimentoPorId(atendimentoCobranca.getIdStatusAtendimento()));
				atendimentoCobranca.setArquivos(atendimentoArquivoService.consultarDadosArquivo(idCedente, idSacado, atendimentoCobranca.getId(), Constantes.COBRANCA));
				atendimentoCobranca.setIdsTitulos(atendimentoTituloService.consultarDadosTituloStr(atendimentoCobranca.getId(), Constantes.COBRANCA));
			});
		} catch (TrustHubServiceException | AtendimentoCobrancaIntegracaoCobrancaOperacaoException | CobrancaIntegracaoSecurityException ea) {
			throw ea;
		} catch (Exception e) {
			throw new AtendimentoCobrancaIntegracaoBusinessException(new StringBuilder(Mensagens.BUSINESS_ERRO_CONSULTAR_HISTORICO_COBRANCA_CEDENTE_SACADO)
					.append(e.getMessage()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_BUSINESS);
		}			
		return listaAtendimentoCobranca;
	}
	
	
	/**
	 * Inserir Arquivos por atendimento Cobranca
	 * @param atendimento - id do atendimento
	 * @param files - arquivos
	 * @ruturn  void
	 */
	public void inserirAtendimentoCobrancaArquivo(String atendimento, List<MultipartFile> files) {
		inserirAtendimentoCobrancaArquivo(converterAtendimentoDto(atendimento, files));
	}

	/**
	 * Insere o atendimento cobranca arquivo,
	 * Cadastra o atendimento cobranca, cadastra os arquivos e os titulos
	 * @param atendimentoCobrancaRequestDto
	 * 			- Dados do atendimento cobranca
	 * 			- Lista de arquivo 
	 * 			- Lista Titulos
	 * @return void
	 */
	public void inserirAtendimentoCobrancaArquivo(AtendimentoCobrancaIntegracaoDTO atendimentoCobrancaRequestDto) {
		try {
			AtendimentoCobrancaIntegracaoDTO atendimentoCobranca = atendimentoCobrancaService.cadastrarAtendimentoCobranca(atendimentoCobrancaRequestDto);
			this.cadastrarAtendimentoArquivo(atendimentoCobranca, atendimentoCobrancaRequestDto.getArquivos());
			this.cadastrarAtendimentoTitulos(atendimentoCobranca, atendimentoCobrancaRequestDto.getIdsTitulos());
		} catch (TrustHubServiceException | AtendimentoCobrancaIntegracaoCobrancaOperacaoException ea) {
			throw ea;
		} catch (Exception e) {
			throw new AtendimentoCobrancaIntegracaoBusinessException(new StringBuilder(Mensagens.BUSINESS_ERRO_INSERIR_ATENDIMENTO_COBRANCA_ARQUIVO)
					.append(e.getMessage()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_BUSINESS);
		}	
	}
	
	/**
	 * Cadatrar o atendimento arquivo.
	 * @param atendimentoCobranca
	 * 		- Id Atendimento
	 * 		- Id Arquivo
	 * 		- Tipo Arquivo (Cobranca)
	 * @param listaArquivos
	 * 		- Dados do arquivo
	 * @return void
	 */
	private void cadastrarAtendimentoArquivo(AtendimentoCobrancaIntegracaoDTO atendimentoCobranca, List<FileRequestDTO> listaArquivos) {
		try {
			listaArquivos.forEach(dto -> {
				Long idArq = arquivoService.cadastrarArquivo(dto);
				AtendimentoArquivoIntegracaoDTO atendimentoArquivoDTO = new AtendimentoArquivoIntegracaoDTO();
				atendimentoArquivoDTO.setId(atendimentoCobranca.getId());
				atendimentoArquivoDTO.setIdArquivo(idArq);
				atendimentoArquivoDTO.setTipoArquivo(Constantes.COBRANCA);
				atendimentoArquivoService.cadastrarAtendimentoArquivo(atendimentoArquivoDTO);
			});
		} catch (TrustHubServiceException | AtendimentoCobrancaIntegracaoCobrancaOperacaoException ea) {
			throw ea;
		} catch (Exception e) {
			throw new AtendimentoCobrancaIntegracaoBusinessException(new StringBuilder(Mensagens.BUSINESS_ERRO_COBRANCA_CADASTRAR_ATENDIMENTO_ARQUIVO)
					.append(e.getMessage()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_BUSINESS);
		}	
	}
	
	/**
	 * Cadastrar atendimento titulo
	 * @param atendimentoCobranca
	 * 		- Id Atendimento
	 * 		- Id Arquivo
	 * 		- Tipo Arquivo (Cobranca)
	 * @param listaTitulos
	 * 		- Dados dos titulos
	 * @return void
	 */
	private void cadastrarAtendimentoTitulos(AtendimentoCobrancaIntegracaoDTO atendimentoCobranca, List<Long> listaTitulos) {
		try {
			listaTitulos.forEach(dto -> {
				AtendimentoTitulosIntegracaoDTO atendimentoTitulosIntegracaoDTO = new AtendimentoTitulosIntegracaoDTO();
				atendimentoTitulosIntegracaoDTO.setId(atendimentoCobranca.getId());
				atendimentoTitulosIntegracaoDTO.setIdTitulo(Long.valueOf(dto));
				atendimentoTitulosIntegracaoDTO.setTipoArquivo(Constantes.COBRANCA);
				atendimentoTituloService.cadastrarAtendimentoTitulo(atendimentoTitulosIntegracaoDTO);
			});
		} catch (TrustHubServiceException | AtendimentoCobrancaIntegracaoCobrancaOperacaoException ea) {
			throw ea;
		} catch (Exception e) {
			throw new AtendimentoCobrancaIntegracaoBusinessException(new StringBuilder(Mensagens.BUSINESS_ERRO_COBRANCA_CADASTRAR_ATENDIMENTO_TITULOS)
					.append(e.getMessage()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_BUSINESS);
		}	
	}
	
	/**
	 * Converter atendimento (multipartFile para DTO)
	 * @param atendimento - Id Atendimento
	 * @param files - Dados do arquivo no formato (MultipartFile)
	 * @return AtendimentoCobrancaIntegracaoDTO 
	 */
	public AtendimentoCobrancaIntegracaoDTO converterAtendimentoDto(String atendimento, List<MultipartFile> files) {
		AtendimentoCobrancaIntegracaoDTO dto = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			dto = objectMapper.readValue(atendimento, AtendimentoCobrancaIntegracaoDTO.class);
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
					throw new AtendimentoCobrancaIntegracaoBusinessException(new StringBuilder(Mensagens.BUSINESS_ERRO_COBRANCA_CONVERTER_ATENDIMENTO_DTO_IO)
							.append(e.getMessage()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_BUSINESS);
				}
			});
			dto.setArquivos(listaArquivos.isEmpty() ? new ArrayList<FileRequestDTO>() : listaArquivos);
		} catch (Exception e) {
			throw new AtendimentoCobrancaIntegracaoBusinessException(new StringBuilder(Mensagens.BUSINESS_ERRO_COBRANCA_CONVERTER_ATENDIMENTO_DTO)
					.append(e.getMessage()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_BUSINESS);
		}
		return dto;
	}

}
