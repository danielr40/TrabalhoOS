package Simplex;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Daniel
 */
public class Ferramentas {

    /**
     * Método que imprime um vetor
     *
     * @param Vetor a ser impresso
     */
    public static void printVetor(double[] vetor) {
        System.out.print("| ");
        for (int i = 0; i < vetor.length; i++) {
            System.out.print(vetor[i] + " ");
        }
        System.out.print(" |\n");
    }
    
    /**
     * Método que imprime um vetor
     *
     * @param Vetor a ser impresso
     */
    public static void printVetor(int[] vetor) {
        System.out.print("| ");
        for (int i = 0; i < vetor.length; i++) {
            System.out.print(vetor[i] + " ");
        }
        System.out.print(" |\n");
    }
    
    /**
     * Método que imprime uma matriz
     *
     * @param Matriz a ser impressa
     */
    public static void printMatriz(double[][] m) {
        for (int i = 0; i < m.length; i++) {
            System.out.print("| ");
            for (int j = 0; j < m[i].length; j++) {
                System.out.print(m[i][j] + " ");
            }
            System.out.print(" |\n");
        }
    }
    
    /**
     * Método que imprime uma matriz
     *
     * @param Matriz a ser impressa
     */
    public static void printMatriz(int[][] m) {
        for (int i = 0; i < m.length; i++) {
            System.out.print("| ");
            for (int j = 0; j < m[i].length; j++) {
                System.out.print(m[i][j] + " ");
            }
            System.out.print(" |\n");
        }
    }
}
