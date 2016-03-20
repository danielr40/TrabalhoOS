/**
 * Created by Felipe on 2016-03-19.
 */
public class Simplex{
    private int minmax; // 1 = Min, -1 = Max
    private double[] z;
    private double[][] restricoes;
    private int numVar;
   private double[][] matriz;    
   private double[][] matrizAux;
   
    private Simplex(int minmax, double[] z, double[][] rest, int n){
	this.minmax = minmax;
	this.z = z;
	this.restricoes = rest;
	this.numVar = n;
        this.matriz = new double [restricoes.length+1][restricoes[0].length];
        this.matrizAux = new double [restricoes.length+1][restricoes[0].length];
        matriz[0] = z;
        System.arraycopy(restricoes, 0, matriz, 1, restricoes.length);
    }
    
    public void primeiraEtapa(){
        int linha = -1, coluna = -1;
        //procura uma variável básica com membro livre negativo
        for(int i = 0; i < matriz.length; i++){
            if(matriz[i][0] < 0){
                linha = i;
                i = matriz.length;
            }
        }
        //se existe continua
        if(linha != -1){
            //Na linha que corresponde à variável com membro livre negativo, procuramos o elemento negativo
            for(int i = 1; i < matriz[0].length; i++)
                if(matriz[linha][i] < 0){
                    coluna = i;
                    i = matriz.length;
                 }
            //elemento negativo existe, então a coluna, onde está esse elemento, é escolhida como permissível.
            if(coluna != -1){
               double menor = Double.MAX_VALUE;
               //Busca-se a linha permitida a partir da identificação do Elemento Permitido (EP) 
               //que possuir o  menor quociente entre os membros livres que representam as variáveis básicas (VB)
               for(int i = 1; i < matriz.length; i++){
                    double aux;
                    //só é permitido elementos com o mesmo sinal
                    if((matriz[i][0]<0 && matriz[i][coluna]<0)||(matriz[i][0]>=0 && matriz[i][coluna]>=0)){
                        aux = matriz[i][0]/matriz[i][coluna];
                        if(aux < menor){
                            menor = aux;
                            linha = i;
                        } 
                    }
               }
               troca(linha, coluna);
            }else{
                //não existe solução
            }
            
        }else{
            //fazer segunda etapa
        
        }
        
    }
    
    public void troca(int linha, int coluna){
        double inverso = Math.pow(matriz[linha][coluna],-1);
       
    }
    
    
}
