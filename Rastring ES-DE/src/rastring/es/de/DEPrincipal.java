/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rastring.es.de;

import metodo.EvolucaoDiferencial;

/**
 *
 * @author lucasgomes
 */
public class DEPrincipal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        double min = -5.12;
        double max = 5.12;
        int gmax = 300; //criterio de parada
        int D = 100; //nuumero de variaveis
        int Np = 5000; // tamanho da populacao
        double F = 0.05; //coeficiente de mutacao
        double Cr = 0.45; //coeficiente de crossover
        

        EvolucaoDiferencial de = new EvolucaoDiferencial(min, max, gmax, D, Np, F, Cr);
        
        de.executar();
    }
    
}
