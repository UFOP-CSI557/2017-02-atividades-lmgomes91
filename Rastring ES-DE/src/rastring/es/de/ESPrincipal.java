/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rastring.es.de;

import metodo.EvolutionEstrategy;

/**
 *
 * @author lucasgomes
 */
public class ESPrincipal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       double min = -5.12;
        double max = 5.12; 
        int nvar = 100;
        int mu = 100; 
        int lambda = 1000; 
        int geracoes = 58; 
        double pMutacao = 1.0;
        
        
        EvolutionEstrategy es = new EvolutionEstrategy(min, max, nvar,mu , lambda, geracoes, pMutacao);
        
        for(int i=0; i<100;i++){
            System.out.println("------------ Execucao: "+i+" ----------------");
            es.executar();
        }
            
    }
    
}
