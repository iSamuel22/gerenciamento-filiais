package controle;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import javafx.scene.paint.Stop;
import modelo.Filial;
import modelo.Funcionario;
import service.FilialService;
import service.FuncionarioService;

@ViewScoped
@ManagedBean
public class FuncionarioBean {

	@EJB
	private FuncionarioService funcionarioService;

	@EJB
	private FilialService filialService;

	private Funcionario funcionario = new Funcionario();

	private List<Funcionario> listaFuncionarios = new ArrayList<Funcionario>();

	private List<Funcionario> listaFuncionariosSalarioOrdenado = new ArrayList<Funcionario>();

	private List<Filial> filiais = new ArrayList<Filial>();

	private Long idFilialX = 0L;

	private String filtro = "";

	private String filtroCidade = "";

	private Double salarioInicial;

	private Double salarioFinal;

	private Boolean edicao = false;

	@PostConstruct
	public void iniciar() {
		filiais = filialService.listAll();
		listarFuncionarios();
		carregarListaFuncionariosListadosSalario();
	}

	private void listarFuncionarios() {
		listaFuncionarios = funcionarioService.retornarFuncionariosNome();
	}

	private void carregarListaFuncionariosListadosSalario() {
		listaFuncionariosSalarioOrdenado = funcionarioService.retornarFuncionariosSalarioOrdenado();
	}

	public void filtrarFuncionario() {

		listaFuncionarios = funcionarioService.listarFuncionariosPorNome(filtro);

		if (listaFuncionarios.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage("Nenhum funcionário encontrado para essa pesquisa."));
		}

	}

	public void filtrarFuncionarioPorCidade() {
		listaFuncionarios = funcionarioService.listarFuncionariosPorCidade(filtroCidade);

		if (listaFuncionarios.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage("Nenhum cidade encontrada para essa pesquisa."));
		}
	}
	
	public void filtrarFuncionariosFaixaSalarial(Filial filial, Double salarioInicial, Double salarioFinal) {

		listaFuncionariosSalarioOrdenado = funcionarioService.retornarFuncionariosFiltradosSalarioFilial(idFilialX,
				salarioInicial, salarioFinal);

		if (listaFuncionariosSalarioOrdenado.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage("Nenhum funcionário encontrado com essa faixa salarial."));
		}
	}

	public void gravar() {

		formatarCPF();

		Long idAux = 0L;

		try {
			idAux = funcionario.getFilial().getId();
		} catch (Exception e) {
			// TODO: handle exception
		}

		funcionario.setFilial(this.filialService.obtemPorId(idFilialX));

		if (funcionario.getId() == null) {
			funcionarioService.create(funcionario);
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Funcionário cadastrado sucesso!"));
			funcionario = new Funcionario();
			this.idFilialX = 0L;

		} else {
			if (idFilialX == idAux) {
				idFilialX = 0L;
				atualizar();
			} else {
				FacesContext.getCurrentInstance().addMessage("", new FacesMessage("A Filial não pode ser alterada!"));
				funcionario = new Funcionario();
				this.idFilialX = 0L;
			}
		}

		listarFuncionarios();
	}

	public void atualizar() {

		String cpfOriginal = funcionarioService.obtemPorId(funcionario.getId()).getCpf();

		formatarCPF();

		if (!funcionario.getCpf().equals(cpfOriginal)) {
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("CPF não pode ser alterado!"));
			funcionario = new Funcionario();
			idFilialX = 0L;
		} else {
			funcionarioService.merge(funcionario);
			funcionario = new Funcionario();
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Funcionário atualizado com sucesso!"));
			listaFuncionarios = funcionarioService.listAll();
			edicao = false;
		}
	}

	public void formatarCPF() {
		if (funcionario.getCpf() != null) {
			String cpf = funcionario.getCpf().replaceAll("\\D", "");
			if (cpf.length() == 11) {
				funcionario.setCpf(cpf.replaceFirst("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4"));
			}
		}
	}

	public void excluir(Funcionario f) {
		funcionarioService.remove(f);
		listarFuncionarios();
		FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Funcionário excluído com sucesso!"));
	}

	public void carregarFuncionario(Funcionario f) {
		funcionario = f;
		idFilialX = funcionario.getFilial().getId();
		edicao = true;
	}

	public FuncionarioService getFuncionarioService() {
		return funcionarioService;
	}

	public void setFuncionarioService(FuncionarioService funcionarioService) {
		this.funcionarioService = funcionarioService;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public List<Funcionario> getListaFuncionarios() {
		return listaFuncionarios;
	}

	public void setListaFuncionarios(List<Funcionario> listaFuncionarios) {
		this.listaFuncionarios = listaFuncionarios;
	}

	public FilialService getFilialService() {
		return filialService;
	}

	public void setFilialService(FilialService filialService) {
		this.filialService = filialService;
	}

	public List<Filial> getFiliais() {
		return filiais;
	}

	public void setFiliais(List<Filial> filiais) {
		this.filiais = filiais;
	}

	public Long getIdFilialSelecionada() {
		return idFilialX;
	}

	public void setIdFilialSelecionada(Long idFilialSelecionada) {
		this.idFilialX = idFilialSelecionada;
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public String getFiltroCidade() {
		return filtroCidade;
	}

	public void setFiltroCidade(String filtroCidade) {
		this.filtroCidade = filtroCidade;
	}

	public Double getSalarioInicial() {
		return salarioInicial;
	}

	public void setSalarioInicial(Double salarioInicial) {
		this.salarioInicial = salarioInicial;
	}

	public Double getSalarioFinal() {
		return salarioFinal;
	}

	public void setSalarioFinal(Double salarioFinal) {
		this.salarioFinal = salarioFinal;
	}

	public List<Funcionario> getListaFuncionariosSalarioOrdenado() {
		return listaFuncionariosSalarioOrdenado;
	}

	public void setListaFuncionariosSalarioOrdenado(List<Funcionario> listaFuncionariosSalarioOrdenado) {
		this.listaFuncionariosSalarioOrdenado = listaFuncionariosSalarioOrdenado;
	}

	public Boolean getEdicao() {
		return edicao;
	}

	public void setEdicao(Boolean edicao) {
		this.edicao = edicao;
	}

}