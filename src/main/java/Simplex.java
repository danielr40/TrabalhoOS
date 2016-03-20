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
        //procura uma vari�vel b�sica com membro livre negativo
        for(int i = 0; i < matriz.length; i++){
            if(matriz[i][0] < 0){
                linha = i;
                i = matriz.length;
            }
        }
        //se existe continua
        if(linha != -1){
            //Na linha que corresponde � vari�vel com membro livre negativo, procuramos o elemento negativo
            for(int i = 1; i < matriz[0].length; i++)
                if(matriz[linha][i] < 0){
                    coluna = i;
                    i = matriz.length;
                 }
            //elemento negativo existe, ent�o a coluna, onde est� esse elemento, � escolhida como permiss�vel.
            if(coluna != -1){
               double menor = Double.MAX_VALUE;
               //Busca-se a linha permitida a partir da identifica��o do Elemento Permitido (EP) 
               //que possuir o  menor quociente entre os membros livres que representam as vari�veis b�sicas (VB)
               for(int i = 1; i < matriz.length; i++){
                    double aux;
                    //s� � permitido elementos com o mesmo sinal
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
                //n�o existe solu��o
            }
            
        }else{
            //fazer segunda etapa
        
        }
        
    }
    
    public void troca(int linha, int coluna){
        double inverso = Math.pow(matriz[linha][coluna],-1);
       
    }
    
    
}
