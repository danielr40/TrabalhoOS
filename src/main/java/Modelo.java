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
	 * Retorna os valores que multiplicam as variaveis na função objetivo.
	 *
	 * @return array com os valores da equação, na ordem das variaveis
	 */
	public double[] getZ(){
		return z;
	}

	/**
	 * Define os valores que multiplicam as variaveis na função objetivo.
	 *
	 * @param z array com os valores da equação, na ordem das variaveis
	 */
	public void setZ(double[] z){
		this.z = z;
	}

	/**
	 * Retorna os valores das equações das restrições.
	 * <p>
	 * É uma matriz N x M, aonde N é o numero de restrições e M o número
	 * de variaveis + 2 (O resultado da inequação). Ou seja, uma equação
	 * <tt>2x + 3y >= 16</tt> se transofrma em <tt>[2, 3, 16]</tt>.
	 *
	 * @return matriz das restrições
	 */
	public double[][] getRestricoes(){
		return restricoes;
	}

	/**
	 * Define os valores das equações das restrições.
	 * <p>
	 * É uma matriz N x M, aonde N é o numero de restrições e M o número
	 * de variaveis + 1 (O resultado da inequação). Ou seja, uma equação
	 * <tt>2x + 3y >= 16</tt> se transofrma em <tt>[2, 3, 16]</tt>.
	 *
	 * @param restricoes
	 */
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
	 * Prepara o modelo para ser colocado na matriz do metodo Simplex.
	 * <p>
	 * Faz as seguintes operações:
	 * <ol>
	 *   <li>Multiplica <tt>minmax</tt> pelos valores de <tt>z</tt>.</li>
	 *   <li>Multiplica o valor da inaquação de uma restrição pelo resto da restrição.</li>
	 *   <li>Coloca os valores na ordem que serão postos na matriz do Simplex.</li>
	 * </ol>
	 *
	 * @return Modelo preparado
	 */
	public static Modelo prepararModelo(){
		int minmax = 0, numVar = 0, numRest = 0;
		int[] ineqRest = new int[numRest];
		double[] z = new double[numVar];
		double[][] restricoes = new double[numRest][numVar+1];

		/*
		 * TODO: Receber modelo da interfaçe, parsear os valores, montar objeto modelo.
		 */

		return new Modelo(minmax, z, restricoes, numVar);
	}
}
