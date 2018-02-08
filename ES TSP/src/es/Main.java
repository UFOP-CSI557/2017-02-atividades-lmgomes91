/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import metodo.EvolutionEstrategy;
import problema.Problema;
import problema.ProblemaTSP;

/**
 *
 * @author lucasgomes
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        File arquivo = new File("saida.csv");
        arquivo.createNewFile();
        FileWriter fw = new FileWriter( arquivo );
        BufferedWriter bw = new BufferedWriter( fw );
        
        Problema problema = new ProblemaTSP("/home/lucasgomes/GitHub/CSI557-Computacao-Evolucionaria/Codes/instances/tsplib/att48.tsp");
        
        double min = 0.0;
        double max = 1.0;
        int nvar = problema.getDimensao();
        int mu = 100;
        int lambda = 400;
        int geracoes = 400;
        double pMutacao = 0.1;
        
        
        EvolutionEstrategy es1 = new EvolutionEstrategy(min, max, nvar, mu, lambda, problema, geracoes, pMutacao);        
        lambda = 200;
        pMutacao = 0.2;
        
        EvolutionEstrategy es2 = new EvolutionEstrategy(min, max, nvar, mu, lambda, problema, geracoes, pMutacao);
        
        Double result = 0.0;
        for (int c = 1; c <= 60; c++) {
            ArrayList<Integer> casos = new ArrayList<>(Arrays.asList(1, 2));
            Collections.shuffle(casos);

            for (Integer i : casos) {
                long startTime = System.currentTimeMillis();
                switch (i) {
                    case 1: 
                        es1.executar();
                        result = es1.getMelhor();
                        break;
                    case 2:
                        es2.executar();
                        result = es2.getMelhor();
                        break;
                }

                long endTime = System.currentTimeMillis();
                long totalTime = endTime - startTime;

                bw.write(c + ";" + i + ";" + result + ";" + totalTime);
                bw.newLine();

            }

        }
    
        bw.close();
        fw.close();
    
    }
    
}
