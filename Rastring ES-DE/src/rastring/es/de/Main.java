/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rastring.es.de;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import metodo.EvolucaoDiferencial;
import metodo.EvolutionEstrategy;

/**
 *
 * @author lucasgomes
 */
public class Main {

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
        
        min = -5.12;
        max = 5.12; 
        int nvar = 100;
        int mu = 100; 
        int lambda = 1000; 
        int geracoes = 55; 
        double pMutacao = 1.0;
        
        
        EvolutionEstrategy es = new EvolutionEstrategy(min, max, nvar,mu , lambda, geracoes, pMutacao);
        
        Double result = 0.0;
        for (int c = 1; c <= 30; c++) {
            ArrayList<Integer> casos = new ArrayList<>(Arrays.asList(3, 4));
            Collections.shuffle(casos);

            for (Integer i : casos) {
                long startTime = System.currentTimeMillis();
                switch (i) {
                    case 3: // de
                        de.executar();
                        result = de.getMelhor();
                        break;
                    case 4:
                        es.executar();
                        result = es.getMelhor();
                        break;
                }

                long endTime = System.currentTimeMillis();
                long totalTime = endTime - startTime;

                System.out.println(c + ";" + i + ";" + result + ";" + totalTime);
                System.out.flush();

            }

        }
    }
    
}
