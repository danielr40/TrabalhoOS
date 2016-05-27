package BranchAndBound;

import Simplex.Modelo;
import Simplex.Simplex;

import java.util.*;

/**
 * Created by Felipe on 2016-05-27.
 */
public class BranchAndBound{

	private Modelo base;

	private Nodo raiz;

	private Queue<Nodo> queue;

	public BranchAndBound(Modelo model){
		this.base = model;

		this.raiz = new Nodo();
		raiz.setModelo(model);

		this.queue = new LinkedList<Nodo>();
		queue.add(raiz);
	}

	public void processar(){
		while(!queue.isEmpty()){
			conquer(queue.poll());
		}
	}

	private void conquer(Nodo n){
		double aux;
		double[] restricao;
		List<Nodo> filhos = new ArrayList<Nodo>();
		Nodo nodoAux;
		Modelo modeloAux;
		boolean achou = false;

		Simplex s = new Simplex(n.getModelo());
		Simplex.ResultadoSimplex result = s.processar();

		if(result == Simplex.ResultadoSimplex.SOLUCAO_OTIMA){
			for(int i = 0; i < n.getModelo().getNumVar(); i++){
				aux = s.valoresVariaveisDescisao[i];
				if(((int) aux) != aux){
					achou = true;

					nodoAux = new Nodo();
					modeloAux = new Modelo();

					modeloAux.setMinmax(n.getModelo().getMinmax());
					modeloAux.setNumVar(n.getModelo().getNumVar());
					modeloAux.setZ(n.getModelo().getZ());
					restricao = new double[n.getModelo().getNumVar() + 1];
					restricao[0] = n.getModelo().getMinmax() * Math.floor(aux);
					for(int j = 1; i < restricao.length; i++){
						if((j-1) == i){
							restricao[j] = n.getModelo().getMinmax();
						}else{
							restricao[j] = 0;
						}
					}
					modeloAux.setRestricoes(copiaEAcrescentaRestricao(n.getModelo(), restricao));
					nodoAux.setModelo(modeloAux);

					filhos.add(nodoAux);

					nodoAux = new Nodo();
					modeloAux = new Modelo();

					modeloAux.setMinmax(n.getModelo().getMinmax());
					modeloAux.setNumVar(n.getModelo().getNumVar());
					modeloAux.setZ(n.getModelo().getZ());
					restricao = new double[n.getModelo().getNumVar() + 1];
					restricao[0] = n.getModelo().getMinmax() * Math.ceil(aux);
					for(int j = 1; i < restricao.length; i++){
						if((j-1) == i){
							restricao[j] = n.getModelo().getMinmax();
						}else{
							restricao[j] = 0;
						}
					}
					modeloAux.setRestricoes(copiaEAcrescentaRestricao(n.getModelo(), restricao));
					nodoAux.setModelo(modeloAux);

					filhos.add(nodoAux);
				}
			}

			if(!achou){
				n.setResult(Nodo.ResultadoBeB.TS_2);
			}else{
				n.setFilhos(filhos);
				queue.addAll(filhos);
			}
		}else{
			n.setResult(Nodo.ResultadoBeB.TS_1);
		}
	}

	private double[][] copiaEAcrescentaRestricao(Modelo m, double[] nova_restricao){
		double[][] ret = new double[m.getRestricoes().length +1][];
		int count = 0;
		for(double[] restr : m.getRestricoes()){
			ret[count++] = restr;
		}
		ret[count] = nova_restricao;
		return ret;
	}
}

