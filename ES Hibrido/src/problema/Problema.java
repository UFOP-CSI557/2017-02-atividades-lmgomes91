/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package problema;

import solucao.IndividuoDouble;

/**
 *
 * @author lucasgomes
 */
public class Problema {

    public static void calcularFuncaoObjetivo(IndividuoDouble individuo) {
        
        double schwefel = 0.0;
        
        for (Double cr : individuo.getCromossomos()) {
            try {
                schwefel += cr - Math.sin(Math.sqrt(Math.abs(cr)));
                
            } catch (Exception a) {
                System.out.println(a);
            }
        }
        
        individuo.setFuncaoObjetivo(schwefel);
    }
}
