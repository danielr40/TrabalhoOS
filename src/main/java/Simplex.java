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
   private int [] vnb;
   private int [] vb;
   private boolean fimPE = true;
   private boolean fimSE = true;
   private boolean SO = false;//otima
   private boolean SI = false;//ilimitada
   private boolean MS = false;//multiplas
   private boolean SS = false;//SemSolução
   
    private Simplex(int minmax, double[] z, double[][] rest, int n){
	this.minmax = minmax;
	this.z = z;
	this.restricoes = rest;
	this.numVar = n;
        this.matriz = new double [restricoes.length+1][restricoes[0].length];
        this.matrizAux = new double [restricoes.length+1][restricoes[0].length];
        matriz[0][0] = 0;
        System.arraycopy(z, 0, matriz[0], 1, z.length);
        System.arraycopy(restricoes, 0, matriz, 1, restricoes.length);
        vnb = new int [numVar];
        vb = new int [restricoes.length];
        for(int i = 0; i < vnb.length; i++){
            vnb[i] = i;
        }
        for(int i = 0; i < vb.length; i++){
            vb[i] = i+numVar;
        }
        while(fimPE){
            primeiraEtapa();
        }
        while(fimSE&&SS){
            primeiraEtapa();
        }
        
    }
    
    public void primeiraEtapa(){
        int linha = -1, coluna = -1;
        //procura uma variável básica com membro livre negativo
        for(int i = 1; i < matriz.length; i++){
            if(matriz[i][0] <= 0){
                linha = i;
                i = matriz.length;
            }
        }
        //se existe continua
        if(linha != -1){
            //Na linha que corresponde à variável com membro livre negativo, procuramos o elemento negativo
            for(int i = 1; i < matriz[0].length; i++){
                if(matriz[linha][i] <= 0){
                    coluna = i;
                    i = matriz.length;
                 }
            }
            //elemento negativo existe, então a coluna, onde está esse elemento, é escolhida como permissível.
            if(coluna != -1){
               double menor = Double.MAX_VALUE;
               //Busca-se a linha permitida a partir da identificação do Elemento Permitido (EP) 
               //que possuir o  menor quociente entre os membros livres que representam as variáveis básicas (VB)
               for(int i = 1; i < matriz.length; i++){
                    double aux;
                    //só é permitido elementos com o mesmo sinal
                    if((matriz[i][0]<0 && matriz[i][coluna]<0)||(matriz[i][0]>=0 && matriz[i][coluna]>0)){
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
                fimPE = false;
                SS = true;
            }
            
        }else{
            //fazer segunda etapa
            fimPE = false;
        
        }
        
    }
    
    public void troca(int linha, int coluna){
        //Calcula-se o inverso do Elemento Permitido
        double inverso = Math.pow(matriz[linha][coluna],-1);
        //Multiplica-se toda a linha pelo EP Inverso
        for(int i = 0; i < matriz[linha].length;i++){
            if(i!=coluna){
                matrizAux[linha][i] =  matriz[linha][i]*inverso;
            }else{
                matrizAux[linha][i] = inverso;
            }
        }
        //Multiplica-se toda a coluna pelo - (EP Inverso)
        for(int i = 0; i < matriz.length;i++){
            if(i!=linha){
                matrizAux[i][coluna] =  matriz[linha][i]*inverso*-1;
            }
        }
        //Nas (SCI) vazias, multiplica-se a (SCS) marcada em sua respectiva 
        //coluna com a (SCI) marcada de sua respectiva linha
        for(int i = 0; i < matriz.length;i++){
            for(int j = 0; j < matriz[linha].length;j++){
                 if(i!=linha&&coluna!=j){
                     matrizAux[i][j] = matrizAux[i][coluna]*matriz[linha][j];   
                 }
            }
        }
        double [] [] novaMatriz = new double [restricoes.length+1][restricoes[0].length];
        int aux = vnb[coluna];
        vnb[coluna] = vb[linha];
        vb[linha] = aux;
        for(int i = 0; i < matriz.length;i++){
            for(int j = 0; j < matriz[linha].length;j++){
                 if(i!=linha&&coluna!=j){
                     novaMatriz[i][j] = matrizAux[i][j]+matriz[i][j];   
                 }else {
                     novaMatriz[i][j] = matrizAux[i][j];
                 }
            }
        }
        matriz = novaMatriz;
        
    }
    
    public void segundaEtapa(){
        int linha = -1, coluna = -1;
        for(int i = 1; i < matriz[0].length; i++){
            if(matriz[0][i] >= 0){
                coluna = i;
                i = matriz.length;
                MS = true;
            }
        }
        if(coluna != -1){
            for(int i = 1; i < matriz.length; i++){
                if(matriz[i][coluna] >= 0){
                    linha = i;
                    i = matriz.length;
                 }
            }
            if(linha!=-1){
                double menor = Double.MAX_VALUE;
               //Busca-se a linha permitida a partir da identificação do Elemento Permitido (EP) 
               //que possuir o  menor quociente entre os membros livres que representam as variáveis básicas (VB)
               for(int i = 1; i < matriz.length; i++){
                    double aux;
                    //só é permitido elementos com o mesmo sinal
                    if((matriz[i][0]<0 && matriz[i][coluna]<0)||(matriz[i][0]>=0 && matriz[i][coluna]>0)){
                        aux = matriz[i][0]/matriz[i][coluna];
                        if(aux < menor){
                            menor = aux;
                            linha = i;
                        } 
                    }
               }
                troca(linha, coluna);
            }else{
                //solução ilimitada
                SI = true;
            fimSE = false;
            }
        }else{
            //Solução encontrada
            SO = true;
            fimSE = false;
        }
    }
    
    
}
