/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.hibrido;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import metodo.EvolutionEstrategy;

/**
 *
 * @author lucasgomes
 */
public class ESHibrido {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {

        File arquivo = new File("saida.txt");
        arquivo.createNewFile();
        FileWriter fw = new FileWriter( arquivo );
        BufferedWriter bw = new BufferedWriter( fw );
        
        double min = -500;//fixo
        double max = 500; //fixo
        int nvar = 40;//fixo
        int mu = 100; //fixo
        int lambda = 1000; 
        int geracoes = 300; //foxp
        double pMutacao = 0.5;
        
        
        EvolutionEstrategy es1 = new EvolutionEstrategy(min, max, nvar,mu , lambda, geracoes, pMutacao);
        
        lambda = 500;
        pMutacao = 0.3;
        
        EvolutionEstrategy es2 = new EvolutionEstrategy(min, max, nvar,mu , lambda, geracoes, pMutacao);
        
        Double result = 0.0;
        for (int c = 1; c <= 60; c++) {
            ArrayList<Integer> casos = new ArrayList<>(Arrays.asList(3, 4));
            Collections.shuffle(casos);

            for (Integer i : casos) {
                long startTime = System.currentTimeMillis();
                switch (i) {
                    case 3: 
                        es1.executar();
                        result = es1.getMelhor();
                        break;
                    case 4:
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
    

