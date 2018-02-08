/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agrastrigin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 *
 * @author lucasgomes
 */
public class AGRastrigin {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // Caso 1 ->
        AlgoritmoGenetico ag1 = new AlgoritmoGenetico(400,0.9,0.08,300,-5.12,5.12,100,1);
        // Caso 2 ->
        AlgoritmoGenetico ag2 = new AlgoritmoGenetico(400,0.9,0.08,300,-5.12,5.12,100,2);

        Double result = 0.0;
        for (int c = 1; c <= 30; c++) {
            ArrayList<Integer> casos = new ArrayList<>(Arrays.asList(1, 2));
            Collections.shuffle(casos);

            for (Integer i : casos) {
                long startTime = System.currentTimeMillis();
                switch (i) {
                    case 1:
                        ag1.executar();
                        result = ag1.getMelhorSolucao().getFuncaoObjetivo();
                        break;
                    case 2:
                        ag2.executar();
                        result = ag2.getMelhorSolucao().getFuncaoObjetivo();
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
