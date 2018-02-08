/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metodo;

import java.util.Collections;
import java.util.Random;
import problema.Problema;
import solucao.IndividuoDouble;
import solucao.PopulacaoDouble;

/**
 *
 * @author lucasgomes
 */
public class EvolucaoDiferencial {
    private double min;
    private double max;
    private int gmax; //criterio de parada
    private int D; //nuumero de variaveis
    private int Np; // tamanho da populacao
    private double F; //coeficiente de mutacao
    private double Cr; //coeficiente de crossover
    private double melhor;
    

    public EvolucaoDiferencial(double min, double max, int gmax, int D, int Np, double F, double Cr) {
        this.min = min;
        this.max = max;
        this.gmax = gmax;
        this.D = D;
        this.Np = Np;
        this.F = F;
        this.Cr = Cr;
        
    }

    public double getMelhor() {
        return melhor;
    }

    public void setMelhor(double melhor) {
        this.melhor = melhor;
    }
    
    
    
        
    public void executar() {
        
        //Criacao da populacao inicial - X
        PopulacaoDouble populacao = new PopulacaoDouble(min, max, D, Np);
        populacao.criar();
        
        
        //Avalicao da populacao inicial
        populacao.avaliar();
        
        //Nova populacao - Y
        PopulacaoDouble novaPopulacao = new PopulacaoDouble();
        
        //Enquanto o criterio de parada nao for atigindo...
        for(int g=1; g<gmax; g++){
            //Para cada vetor da populacao
            for(int i=0;i<Np;i++){
                //Selecionar aleatoriamente r0,r1,r2
                Random rnd = new Random();
                int r0 = rnd.nextInt(Np);
                int r1,r2;
                do{
                    r1 = rnd.nextInt(Np);
                }while(r1 == r0);
                
                do{
                    r2 = rnd.nextInt(Np);
                }while(r2 == r1 || r2 == r0);
                
                IndividuoDouble trial = new IndividuoDouble(min, max, D);
                IndividuoDouble xr0 = (IndividuoDouble) populacao.getIndividuos().get(r0);
                IndividuoDouble xr1 = (IndividuoDouble) populacao.getIndividuos().get(r1);
                IndividuoDouble xr2 = (IndividuoDouble) populacao.getIndividuos().get(r2);        
                
                pertubacao(trial, xr1, xr2);
                mutacao(trial,xr0);
                
                IndividuoDouble target = (IndividuoDouble) populacao.getIndividuos().get(i);
                
                crossover(trial, target);
                
                //Selecao
                Problema.calcularFuncaoObjetivo(trial);
                
                if(trial.getFuncaoObjetivo() <= target.getFuncaoObjetivo()){
                    novaPopulacao.getIndividuos().add(trial);
                }
                else{
                    novaPopulacao.getIndividuos().add(target.clone());
                }
                
            }
            
            populacao.getIndividuos().clear();
            populacao.getIndividuos().addAll(novaPopulacao.getIndividuos());
            Collections.sort(populacao.getIndividuos());
            
            //System.out.println(populacao.getMelhorIndividuo().getFuncaoObjetivo());
            
            this.setMelhor(populacao.getMelhorIndividuo().getFuncaoObjetivo());
            
            
        }
         
    }
    
    private void pertubacao(IndividuoDouble trial, IndividuoDouble xr1, IndividuoDouble xr2 ){
        
        //Diferenca entre r1 e r2
        for(int i=0; i< D;i++){
            double diferenca =xr1.getCromossomos().get(i) - xr2.getCromossomos().get(i);
            trial.getCromossomos().add(diferenca);
            
        }
        
    }
    
    private void mutacao(IndividuoDouble trial, IndividuoDouble xr0){
        //Multiplicar por F a diferença e somar com xr0
        for(int i=0;i<D;i++){
            double valor = xr0.getCromossomos().get(i) + this.F *(trial.getCromossomos().get(i));
            
            trial.getCromossomos().set(i, valor);
            
        }
    }
    
    private void crossover(IndividuoDouble trial, IndividuoDouble target){
        Random rnd = new Random();
        int jRand = rnd.nextInt();
        
        for(int i = 0; i<this.D; i++){
            if(!(rnd.nextDouble() <= this.Cr || i == jRand)){
                trial.getCromossomos().set(i, target.getCromossomos().get(i));
            }    
        }
        
    }
    
    
}
