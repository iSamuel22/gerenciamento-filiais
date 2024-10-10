package service;

import java.util.List;

import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import modelo.Filial;
import modelo.Funcionario;

@Stateless
public class FuncionarioService extends GenericService<Funcionario> {

	public FuncionarioService() {
		super(Funcionario.class);
	}

	public List<Funcionario> retornarFuncionariosNome() {

		final CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();

		final CriteriaQuery<Funcionario> cQuery = cBuilder.createQuery(Funcionario.class);

		final Root<Funcionario> funcionarioRoot = cQuery.from(Funcionario.class);

		Expression<String> expNome = funcionarioRoot.get("nome");

		cQuery.orderBy(cBuilder.asc(expNome));

		List<Funcionario> resultado = getEntityManager().createQuery(cQuery).getResultList();

		return resultado;
	}

	public List<Funcionario> retornarFuncionariosSalarioOrdenado() {

		final CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();

		final CriteriaQuery<Funcionario> cQuery = cBuilder.createQuery(Funcionario.class);

		final Root<Funcionario> funcionarioRoot = cQuery.from(Funcionario.class);

		cQuery.orderBy(cBuilder.desc(funcionarioRoot.get("salario")));

		List<Funcionario> resultado = getEntityManager().createQuery(cQuery).getResultList();

		return resultado;
	}

	public List<Funcionario> retornarFuncionariosFiltradosSalarioFilial(Long idFilial, Double salarioInicial,
			Double salarioFinal) {

		CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();

		CriteriaQuery<Funcionario> cQuery = cBuilder.createQuery(Funcionario.class);

		Root<Funcionario> funcionarioRoot = cQuery.from(Funcionario.class);

		if ((salarioInicial == 0.0 && salarioFinal == 0.0) || (salarioInicial < 0.0 || salarioFinal < 0.0)
				|| (salarioInicial > salarioFinal)) {

			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Insira um intervalo válido!"));

			retornarFuncionariosSalarioOrdenado();

		} else {
			if (idFilial == -1) {
				cQuery.select(funcionarioRoot)
						.where(cBuilder.between(funcionarioRoot.<Double>get("salario"), salarioInicial, salarioFinal));
			} else {
				cQuery.select(funcionarioRoot).where(cBuilder.equal(funcionarioRoot.get("filial"), idFilial),
						cBuilder.between(funcionarioRoot.<Double>get("salario"), salarioInicial, salarioFinal));
			}
		}

		cQuery.orderBy(cBuilder.desc(funcionarioRoot.get("salario")));

		List<Funcionario> resultado = getEntityManager().createQuery(cQuery).getResultList();

		return resultado;
	}

	public List<Funcionario> listarFuncionariosPorNome(String nome) {

		final CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();

		final CriteriaQuery<Funcionario> cQuery = cBuilder.createQuery(Funcionario.class);

		final Root<Funcionario> funcionarioRoot = cQuery.from(Funcionario.class);

		final Expression<String> expNome = funcionarioRoot.get("nome");

		cQuery.select(funcionarioRoot);

		cQuery.where(cBuilder.like(expNome, "%" + nome + "%"));

		cQuery.orderBy(cBuilder.asc(expNome));

		List<Funcionario> resultado = getEntityManager().createQuery(cQuery).getResultList();

		return resultado;

	}

	public List<Funcionario> listarFuncionariosPorCidade(String cidade) {

		final CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();

		final CriteriaQuery<Funcionario> cQuery = cBuilder.createQuery(Funcionario.class);

		final Root<Funcionario> funcionarioRoot = cQuery.from(Funcionario.class);

		final Expression<String> expCidade = funcionarioRoot.get("endereco").get("cidade");

		final Expression<String> expNome = funcionarioRoot.get("nome");

		cQuery.select(funcionarioRoot).where(cBuilder.equal(expCidade, cidade));

		cQuery.orderBy(cBuilder.asc(expNome));

		List<Funcionario> resultado = getEntityManager().createQuery(cQuery).getResultList();

		return resultado;

	}

}