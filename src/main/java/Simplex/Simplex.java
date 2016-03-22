package Simplex;

/**
 * Executa o metodo simplex.
 */
public class Simplex{

	/**
	 * Enum com os resultados possívels do metodo Simplex.
	 */
	public enum ResultadoSimplex{
		SOLUCAO_OTIMA, SOLUCAO_ILIMITADA, MULTIPLAS_SOLUCOES, SEM_SOLUCAO
	}

	private Modelo model; // Modelo

	private double[][] matrizSup; // Parte superior da matriz
	private double[][] matrizInf; // Parte inferior da matriz

	private int[] varNaoBasicas; // Variaveis não basicas.
	private int[] varBasicas; // Variaveis basicas.

	private boolean fimPriemriaEtapa = false; // Indica se a primeira etapa acabou.
	private boolean fimSegundaEtapa = false; // Indica se a segunda etapa acabou

	private ResultadoSimplex resultado;

	public Simplex(Modelo model){

		/*
		 * Inicializa as variaveis.
		 */

		this.model = model;

		this.matrizSup = new double[model.getRestricoes().length + 1][model.getRestricoes()[0].length];
		this.matrizInf = new double[model.getRestricoes().length + 1][model.getRestricoes()[0].length];
		this.varNaoBasicas = new int[model.getNumVar()];
		this.varBasicas = new int[model.getRestricoes().length];

		// Preenche a matrix inicial com valores das restrições.
		this.matrizSup[0][0] = 0;
		System.arraycopy(model.getZ(), 0, matrizSup[0], 1, model.getZ().length);
		System.arraycopy(model.getRestricoes(), 0, matrizSup, 1, model.getRestricoes().length);

		// Preache arrays das variaveis.
		for(int i = 0; i < varNaoBasicas.length; i++){
			this.varNaoBasicas[i] = i;
		}
		for(int i = 0; i < varBasicas.length; i++){
			this.varBasicas[i] = i + model.getNumVar();
		}
	}

	/**
	 * Faz o processamento.
	 * <p>
	 * Para mais detalhes veja {@link #primeiraEtapa()} e {@link #segundaEtapa()}.
	 *
	 * @return enum equivalente ao resultado do processamento.
	 */
	public ResultadoSimplex processar(){
            
                Ferramentas.printMatriz(matrizSup);
                System.out.println("");
                
		while(!fimPriemriaEtapa){
			primeiraEtapa();
                        
                        Ferramentas.printMatriz(matrizSup);
                        System.out.println("");
		}
		while(!fimSegundaEtapa){
			segundaEtapa();
                        
                        Ferramentas.printMatriz(matrizSup);
                        System.out.println("");
		}
        // TODO: Retornar valor de z e das variáveis não basicas.
		return this.resultado;
	}

	/**
	 * Faz a primeira etapa do metodo simplex.
	 * <p>
	 * A primeira etapa envolve os seguintes passos:
	 * <ol>
	 *     <li>Procura um valor negativo entre os membros livres das variáveis básicas.
	 *          <ul>
	 *              <li>Se ele não é encontrado, então continua na segunda etapa.</li>
	 *          </ul>
	 *     </li>
	 *     <li>Procura um valor negativo nas colunas de variáveis não basicas na linha
	 *     encontrada no passo anterior.
	 *          <ul>
	 *              <li>Se ele não é encontrado, então o modelo não tem solução.</li>
	 *          </ul>
	 *     </li>
	 *     <li>A coluna encontrada é a coluna permitida. É calculado o quociente dos
	 *     elementos dos membros livres com os da coluna permitida, desde que tenham o
	 *     mesmo sinal e o denominador (isto é, o elemento da coluna permitida) não seja
	 *     zero. O menor quociente define a linha permitida.</li>
	 *     <li>Executa-se o algorítimo da troca.</li>
	 * </ol>
	 *
	 * @see #segundaEtapa() segundaEtapa()
	 * @see #troca(int, int) troca()
	 */
	private void primeiraEtapa(){
		int linha = -1, coluna = -1;

		// Procura uma variável básica com Membro Livre negativo.
		for(int i = 1; i < matrizSup.length; i++){
			if(matrizSup[i][0] < 0){
				linha = i;
				break;
			}
		}

		// Se existir, continua
		if(linha != -1){

			// Na linha que corresponde à variável com Membro Livre negativo,
			// procuramos uma coluna com um elemento negativo
			for(int i = 1; i < matrizSup[0].length; i++){
				if(matrizSup[linha][i] <= 0){
					coluna = i;
					break;
				 }
			}

			// Se um elemento negativo existe, então a coluna onde está esse
			// elemento é escolhida como permissível.
			if(coluna != -1){

				double menor = Double.MAX_VALUE;

				// Busca-se a linha permitida a partir da identificação do
				// Elemento Permitido (EP) que possuir o menor quociente entre
				// os membros livres que representam as variáveis basicas (VB)
				for(int i = 1; i < matrizSup.length; i++){

					double aux;

					// Só são permitidos elementos com o mesmo sinal e cujo denominador não seja 0.

					if( (matrizSup[i][0] < 0 && matrizSup[i][coluna] < 0) ||
						(matrizSup[i][0] >= 0 && matrizSup[i][coluna] > 0)){

						aux = matrizSup[i][0]/ matrizSup[i][coluna];
						if(aux < menor){
							menor = aux;
							linha = i;
						}
					}
				}

				// Realiza o algorítimo da troca.
				troca(linha, coluna);

			}else{
				// Não existe solução
				fimPriemriaEtapa = true;
				fimSegundaEtapa = true;
				this.resultado = ResultadoSimplex.SEM_SOLUCAO;
			}

		}else{
			// Todos os Membros Livers são positivos, continua na
			// segunda etapa.
			fimPriemriaEtapa = true;
		}
	}


	/**
	 * Faz a segunda etapa do metodo simplex.
	 * <p>
	 * A segunda etapa envolve os seguintes passos:
	 * <ol>
	 *     <li>Na linha da função objetivo, procura-se por membros positivos entre
	 *     as colunas das variáveis não basicas.
	 *          <ul>
	 *              <li>Se não é encontrado então a solução é ótima.</li>
	 *              <li>Se um zero é encontrado, então há multiplas soluções.</li>
	 *          </ul>
	 *     </li>
	 *     <li>A coluna permitida é definida. É calculado o quociente dos
	 *     elementos dos membros livres com os da coluna permitida, desde que tenham o
	 *     mesmo sinal e o denominador (isto é, o elemento da coluna permitida) não seja
	 *     zero. O menor quociente define a linha permitida.</li>
	 *     <li>É executado o algoritimo da troca.</li>
	 * </ol>
	 *
	 * @see #troca(int, int) troca()
	 */
	private void segundaEtapa(){

		int linha = -1, coluna = -1;

		// Procura elemento positivo na linha da função objetiva.
		for(int i = 1; i < matrizSup[0].length; i++){
			if(matrizSup[0][i] > 0){
				coluna = i;
				break;
			}else if(matrizSup[0][i] == 0){
				// Caso um desses elementos seja zero,
				// quer dizer que há multiplas soluções.
				this.resultado = ResultadoSimplex.MULTIPLAS_SOLUCOES;
				break;
			}
		}

		// Caso o elemento positivo exista...
		if(coluna != -1){
			// ...procuramos um elemento positivo na coluna.
			for(int i = 1; i < matrizSup.length; i++){
				if(matrizSup[i][coluna] >= 0){
					linha = i;
					break;
				}
			}

			// Se o elemento for encontrado...
			if(linha!=-1){

				double menor = Double.MAX_VALUE;

				// Busca-se a linha permitida a partir da identificação
				// do Elemento Permitido (EP) que possuir o menor quociente entre
				// os membros livres que representam as variáveis basicas (VB)
				for(int i = 1; i < matrizSup.length; i++){

					double aux;

					// Só são permitidos elementos com o mesmo sinal e que o denominador
					// seja diferente de zero.
					if( (matrizSup[i][0] < 0 && matrizSup[i][coluna] < 0) ||
						(matrizSup[i][0] >= 0 && matrizSup[i][coluna] > 0)){

						aux = matrizSup[i][0]/ matrizSup[i][coluna];

						if(aux < menor){
							menor = aux;
							linha = i;
						}
					}
				}

				// Executa o algorítimo da troca.
				troca(linha, coluna);
			}else{
				// Não foi encontrado um elemento positivo
				// na coluna, logo a solução é ilimitada
				this.resultado = ResultadoSimplex.SOLUCAO_ILIMITADA;
				fimSegundaEtapa = true;
			}
		}else{
			if(this.resultado != ResultadoSimplex.MULTIPLAS_SOLUCOES){
				// Não existem elementos positivos,
				// então solução ótima foi encontrada.
                this.resultado = ResultadoSimplex.SOLUCAO_OTIMA;
				fimSegundaEtapa = true;
			}
		}
	}

	/**
     * Faz o algoritimo da troca.
     * <p>
     * O algoritimo envolve os seguintes passos:
     * <ol>
     *     <li>Inverte-se o Elemento Permitido.</li>
     *     <li>Multiplica-se a linha permitida pelo Elemento Permitido.</li>
     *     <li>Multiplica-se a coluna permitido pelo negativo do Elemento Permitido.</li>
     *     <li>Preenche as células restantes multiplicando-se o inferior da coluna permitida
     *     com o inferior da linha permitida referentes a linha e coluna atual, respectivamente.
     *     Em outras palavras para uma celula M(i,j) o seu valor inferior será M(i, CP) * M(LP, j).</li>
     *     <li>Troca-se de lugar a linha permitida com a coluna permitida. Os valores na nova matriz
     *     são permitidos com os inversos calculados.</li>
     *     <li>Preenchem-se as células restantes somando o valor superior com o inferior delas.</li>
     * </ol>
     *
     * @param linha Linha Permitida
     * @param coluna Coluna Permitida
     */
    private void troca(int linha, int coluna){

		// Calcula-se o inverso do Elemento Permitido
		double inverso = Math.pow(matrizSup[linha][coluna], -1);

		// Multiplica-se toda a linha pelo EP Inverso
		for(int i = 0; i < matrizSup[linha].length; i++){
			if(i!=coluna){
				matrizInf[linha][i] =  matrizSup[linha][i] * inverso;
			}else{
				matrizInf[linha][i] = inverso;
			}
		}

		// Multiplica-se toda a coluna pelo negativo do EP Inverso
		for(int i = 0; i < matrizSup.length && i != linha; i++){
			matrizInf[i][coluna] =  matrizSup[i][coluna] * (-1 * inverso);
		}

		// Nas (SCI) vazias, multiplica-se a (SCS) marcada em sua respectiva
		// coluna com a (SCI) marcada de sua respectiva linha
		for(int i = 0; i < matrizSup.length; i++){
			for(int j = 0; j < matrizSup[linha].length; j++){
                            if(j != coluna&&i != linha)	
				matrizInf[i][j] = matrizInf[i][coluna] * matrizSup[linha][j];
			}
		}

		// Cria-se uma nova matrix.
		double[][] novaMatriz = new double[model.getRestricoes().length+1][model.getRestricoes()[0].length];

		// Altera a posição das variaveis basicas/não basicas.
		int aux = varNaoBasicas[coluna-1];
		varNaoBasicas[coluna-1] = varBasicas[linha-1];
		varBasicas[linha-1] = aux;

		// Preenche a nova matriz.
		for(int i = 0; i < matrizSup.length; i++){
			for(int j = 0; j < matrizSup[linha].length; j++){
				if(i != linha && j != coluna){
                                   	novaMatriz[i][j] = matrizInf[i][j] + matrizSup[i][j];
				}else {
					novaMatriz[i][j] = matrizInf[i][j];
				}
			}
		}
		matrizSup = novaMatriz;
	}
}
