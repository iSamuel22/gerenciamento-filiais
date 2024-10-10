package service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import modelo.Filial;
import modelo.Funcionario;

@Stateless
public class FilialService extends GenericService<Filial> {

	public FilialService() {
		super(Filial.class);
	}
	
	public Long retornarNumFuncionarios(Filial f) {
		final CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();
		
		final CriteriaQuery<Long> cQuery = cBuilder.createQuery(Long.class);

		final Root<Funcionario> funcionarioRoot = cQuery.from(Funcionario.class);

		Expression<Filial> expFilial = funcionarioRoot.get("filial");

		cQuery.select(cBuilder.count(expFilial)).where(cBuilder.equal(expFilial, f));

		Long resultado = getEntityManager().createQuery(cQuery).getSingleResult();

		return resultado;
	}

	public List<Filial> retornarFiliaisNomeOrdenado() {

		final CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();

		/* consulta do criteria começa aqui */
		final CriteriaQuery<Filial> cQuery = cBuilder.createQuery(Filial.class);

		final Root<Filial> filialRoot = cQuery.from(Filial.class);

		Expression<String> expNome = filialRoot.get("nome");

		cQuery.orderBy(cBuilder.asc(expNome));

		List<Filial> resultado = getEntityManager().createQuery(cQuery).getResultList();

		return resultado;
	}
	
	public List<Filial> listarFiliaisPorNome(String nome) {

		final CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();

		final CriteriaQuery<Filial> cQuery = cBuilder.createQuery(Filial.class);

		final Root<Filial> filialRoot = cQuery.from(Filial.class);

		final Expression<String> expNome = filialRoot.get("nome");

		cQuery.select(filialRoot);

		cQuery.where(cBuilder.like(expNome, "%" + nome + "%"));

		cQuery.orderBy(cBuilder.asc(expNome));

		List<Filial> resultado = getEntityManager().createQuery(cQuery).getResultList();

		return resultado;

	}
}