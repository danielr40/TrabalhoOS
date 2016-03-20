/**
 * Classe de modelo.
 */
public class Modelo{
	private int minmax; // 1 = Min, -1 = Max
	private double[] z;
	private double[][] restricoes;
	private int numVar;

	public Modelo(int minmax, double[] z, double[][] rest, int n){
		this.minmax = minmax;
		this.z = z;
		this.restricoes = rest;
		this.numVar = n;
	}

	/**
	 * Retorna se é problema de minimização ou de maximização.
	 *
	 * @return <tt>-1</tt> se for maximização, <tt>1</tt> se for minimização
	 */
	public int getMinmax(){
		return minmax;
	}

	/**
	 * Define se é problema de minimização ou de maximização
	 *
	 * @param minmax  <tt>-1</tt> se for maximização, <tt>1</tt> se for minimização
	 */
	public void setMinmax(int minmax){
		this.minmax = minmax;
	}

	/**
	 * Retorna os valores que multiplicam as variaveis na
	 * @return array com os valores da equação, na ordem das variaveis
	 */
	public double[] getZ(){
		return z;
	}

	/**
	 * Define os valores que multiplicam as variaveis
	 * @param z array com os valores da equação, na ordem das variaveis
	 */
	public void setZ(double[] z){
		this.z = z;
	}

	public double[][] getRestricoes(){
		return restricoes;
	}

	public void setRestricoes(double[][] restricoes){
		this.restricoes = restricoes;
	}

	/**
	 * Retorna o número de variaveis de decisão.
	 *
	 * @return número de variaveis
	 */
	public int getNumVar(){
		return numVar;
	}

	/**
	 * Define o número de variaveis de decisão.
	 *
	 * @param numVar número de variaveis
	 */
	public void setNumVar(int numVar){
		this.numVar = numVar;
	}

	/**
	 * Prepara o modelo para ser colocado na matriz do
	 * metodo Simplex.
	 * <p>
	 * Faz as seguintes operações:
	 * <ul>
	 *     <li>Multiplica <tt>minmax</tt> pelos valores de <tt>z</tt>.</li>
	 *     <li></li>
	 * </ul>
	 *
	 * @return Modelo preparado
	 */
	public static Modelo prepareModelo(){
		int minmax = 0, numVar = 0, numRest = 0;
		int[] ineqRest = new int[numRest];
		double[] z = new double[numVar];
		double[][] restricoes = new double[numRest][numVar+1];

		return new Modelo(minmax, z, restricoes, numVar);
	}
}
