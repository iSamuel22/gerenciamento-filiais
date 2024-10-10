package controle;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import modelo.Filial;
import modelo.Funcionario;
import service.FilialService;
import sun.print.PeekGraphics;

@ViewScoped
@ManagedBean
public class FilialBean {

	@EJB
	private FilialService filialService;

	private Filial filial = new Filial();

	private List<Filial> filiais = new ArrayList<Filial>();

	private String filtro = "";

	private Boolean edicao = false;

	@PostConstruct
	public void iniciar() {
		listarFiliais();
	}

	public void listarFiliais() {
		filiais = filialService.retornarFiliaisNomeOrdenado();
	}

	public Long pegarNumFuncionario(Filial f) {
		filial.setNumFuncionario(filialService.retornarNumFuncionarios(f));
		Long resultado = filial.getNumFuncionario();

		return resultado;
	}

	public void filtrarFilial() {

		filiais = filialService.listarFiliaisPorNome(filtro);

		if (filiais.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage("Nenhuma filial encontrada para essa pesquisa."));
		}
	}

	public void gravar() {

		formatarCNPJ();

		String texto = "";

		if (filial.getId() == null) {
			filialService.create(filial);
			texto = "cadastrada";
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Filial " + texto + " com sucesso!"));
		} else {
			atualizar();
		}

		filial = new Filial();
		listarFiliais();
	}

	public void atualizar() {
		formatarCNPJ();
		filialService.merge(filial);
		filial = new Filial();
		FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Filial atualizada com sucesso!"));
		filiais = filialService.listAll();
		edicao = false;
	}

	public void excluir(Filial f) {
		
	    Long numFuncionarios = pegarNumFuncionario(f);
		
	    if (numFuncionarios == 0) {
	        filialService.remove(f);
	        FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Filial excluída com sucesso!"));
	    } else {
	        FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Não é possível excluir a filial com funcionários!"));
	    }

	    listarFiliais();
	}


	public void carregarFilial(Filial f) {
		filial = f;
		edicao = true;
	}

	private void formatarCNPJ() {
		if (filial.getCnpj() != null) {
			String cnpj = filial.getCnpj().replaceAll("\\D", "");
			if (cnpj.length() == 14) {
				filial.setCnpj(cnpj.replaceFirst("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5"));
			}
		}
	}

	public Filial getFilial() {
		return filial;
	}

	public void setFilial(Filial filial) {
		this.filial = filial;
	}

	public List<Filial> getFiliais() {
		return filiais;
	}

	public void setFiliais(List<Filial> filiais) {
		this.filiais = filiais;
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public Boolean getEdicao() {
		return edicao;
	}

	public void setEdicao(Boolean edicao) {
		this.edicao = edicao;
	}

}
