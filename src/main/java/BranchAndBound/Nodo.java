package BranchAndBound;

import Simplex.Modelo;
import Simplex.Simplex;

import java.util.List;

/**
 * Created by Felipe on 2016-05-27.
 */
public class Nodo{
	public enum ResultadoBeB{
		TS_1, TS_2, TS_3
	}
	private Modelo modelo;

	private List<Nodo> filhos;

	private ResultadoBeB result;

	private Simplex resultadoSimplex;

	public Modelo getModelo(){
		return modelo;
	}

	public void setModelo(Modelo modelo){
		this.modelo = modelo;
	}

	public List<Nodo> getFilhos(){
		return filhos;
	}

	public void setFilhos(List<Nodo> filhos){
		this.filhos = filhos;
	}

	public Nodo.ResultadoBeB getResult(){
		return result;
	}

	public void setResult(Nodo.ResultadoBeB result){
		this.result = result;
	}

	public Simplex getResultadoSimplex(){
		return resultadoSimplex;
	}

	public void setResultadoSimplex(Simplex resultadoSimplex){
		this.resultadoSimplex = resultadoSimplex;
	}
}
