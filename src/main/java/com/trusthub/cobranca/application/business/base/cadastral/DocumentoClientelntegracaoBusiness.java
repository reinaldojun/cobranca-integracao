package com.trusthub.cobranca.application.business.base.cadastral;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trusthub.cobranca.application.exceptions.atendimento.AtendimentoCobrancaIntegracaoCobrancaOperacaoException;
import com.trusthub.cobranca.application.exceptions.base.cadastrais.BaseCadastraIntegracaoException;
import com.trusthub.cobranca.application.exceptions.base.cadastrais.BaseCadastraisIntegracaoBusinessException;
import com.trusthub.cobranca.application.service.arquivo.ArquivoCobrancaService;
import com.trusthub.cobranca.application.service.cliente.ClienteService;
import com.trusthub.cobranca.application.service.documento.ClienteDocumentoService;
import com.trusthub.cobranca.application.util.Mensagens;
import com.trusthub.cobranca.configuration.validation.CobrancaIntegracaoError;
import com.trusthub.cobranca.configuration.validation.generic.layers.TrustHubServiceException;
import com.trusthub.cobranca.domain.cliente.ClienteDTO;
import com.trusthub.cobranca.domain.documento.DocumentoDTO;

/**
 * Classe que irá controlar toda regra do documento cliente
 * @author alan.franco
 */
@Component
public class DocumentoClientelntegracaoBusiness {
	
	@Autowired
	private ClienteService clienteService;

	@Autowired
	private ClienteDocumentoService clienteDocumentoService;
	
	@Autowired
	private ArquivoCobrancaService arquivoCobrancaService;
	
	/**
	 * Consultar documento cliente
	 * @param documento
	 * @return
	 */
	public List<DocumentoDTO> consultarDocumentoCliente(String documento) {
		List<DocumentoDTO> listaDocumentoDTO = new ArrayList<>();
		try {
			List<ClienteDTO> listClienteDTOs = clienteService.consultarCliente(documento);
			if (!listClienteDTOs.isEmpty()) {
			Integer idCliente = listClienteDTOs.get(0).getId();
			listaDocumentoDTO = clienteDocumentoService.consultarDocumentoCliente(idCliente);
			listaDocumentoDTO.forEach( dto -> { dto.setExtensao(arquivoCobrancaService.consultarArquivo(dto.getIdArquivo()).getExtensao()); });
			}
		} catch (TrustHubServiceException | AtendimentoCobrancaIntegracaoCobrancaOperacaoException | BaseCadastraIntegracaoException ea) {
			throw ea;
		} catch (Exception e) {
			throw new BaseCadastraisIntegracaoBusinessException(new StringBuilder(Mensagens.BUSINESS_ERRO_CONSULTAR_DOCUMENTO_CLIENTE)
					.append(e.getMessage()).toString(), CobrancaIntegracaoError.ERROR_COBRANCA_BUSINESS);
		}	
		return listaDocumentoDTO;
	}

}
