
import Simplex.Simplex;
import Simplex.Modelo;

/**
 * Created by Felipe on 2016-03-20.
 */
public class Teste{

	public static void main(String[] args){
		int min = 1;
		int n = 2;
		double[] z = new double[]{ 80.0, 60.0 };
		double[][] r = new double[][]{
				{-24.0, -4.0, -6.0}, {16.0, 4.0, 2.0}, {3.0, 0.0, 1.0}
		};

		Simplex s = new Simplex(new Modelo(min, z, r, n));
		//Simplex.ResultadoSimplex resp = s.processar();

		//System.out.println(resp);
	}
}
