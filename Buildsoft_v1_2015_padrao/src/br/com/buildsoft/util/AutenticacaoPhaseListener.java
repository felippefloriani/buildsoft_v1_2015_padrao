package br.com.buildsoft.util;

import java.util.Map;

import javax.faces.application.Application;
import javax.faces.application.NavigationHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import br.com.buildsoft.bean.AutenticacaoBean;
import br.com.buildsoft.domain.Usuario;

public class AutenticacaoPhaseListener implements PhaseListener {

	@Override
	public void afterPhase(PhaseEvent event) {
		FacesContext facesContext = event.getFacesContext();
		UIViewRoot uiViewRoot = facesContext.getViewRoot();
		String paginaAtual = uiViewRoot.getViewId();

		boolean ehPaginaAutenticacao = paginaAtual
				.contains("autenticacao.xhtml");

		if (!ehPaginaAutenticacao) {
			ExternalContext externalContext = facesContext.getExternalContext();
			Map<String, Object> mapa = externalContext.getSessionMap();
			AutenticacaoBean autenticacaoBean = (AutenticacaoBean) mapa
					.get("autenticacaoBean");

			Usuario usuario = autenticacaoBean.getUsuarioLogado();

			if (usuario.getTipo() == null) {
				FacesUtil
						.adcionarMsgErro("Usu�rio n�o autenticado. Efetue o login de acesso!");

				Application application = facesContext.getApplication();
				NavigationHandler navigationHandler = application
						.getNavigationHandler();
				navigationHandler.handleNavigation(facesContext, null,
						"/pages/autenticacao.xhtml?faces-redirect=true");

			}

		}

	}

	@Override
	public void beforePhase(PhaseEvent arg0) {

	}

	@Override
	public PhaseId getPhaseId() {

		return PhaseId.RESTORE_VIEW;
	}

}
