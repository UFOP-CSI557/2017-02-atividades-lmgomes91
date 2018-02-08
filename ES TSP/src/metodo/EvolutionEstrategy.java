/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metodo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import problema.Problema;
import solucao.Individuo;
import solucao.IndividuoDouble;
import solucao.IndividuoInteger;
import solucao.PopulacaoDouble;
import solucao.PopulacaoInteger;

/**
 *
 * @author lucasgomes
 */
public class EvolutionEstrategy implements Metodo {

    private double min;
    private double max;
    private int nvar;
    private int mu;
    private int lambda;
    private Problema problema;
    private int geracoes;
    private double pMutacao;
    private double melhor;

    public double getMelhor() {
        return melhor;
    }

    public void setMelhor(double melhor) {
        this.melhor = melhor;
    }
    
    

    public EvolutionEstrategy(double min, double max, int nvar, int mu, int lambda, Problema problema, int geracoes, double pMutacao) {
        this.min = min;
        this.max = max;
        this.nvar = nvar;
        this.mu = mu;
        this.lambda = lambda;
        this.problema = problema;
        this.geracoes = geracoes;
        this.pMutacao = pMutacao;
    }

    @Override
    public void executar() {
        //pop inicial
        PopulacaoDouble populacao = new PopulacaoDouble(problema, min, max, nvar, mu);
        populacao.criar();

        //pop para repressentar contexto combinatorio
        PopulacaoInteger popTSP = new PopulacaoInteger(mu, problema);
        converteRealParaInteiro(populacao, popTSP);

        //avaliar pop inicial
        popTSP.avaliar();
        
        for(int i=0;i<popTSP.getIndividuos().size();i++){
            populacao.getIndividuos().get(i).setFuncaoObjetivo(popTSP.getIndividuos().get(i).getFuncaoObjetivo());
        }
        

        //nova pop
        PopulacaoDouble novaPopulacao = new PopulacaoDouble();

        for (int g = 1; g <= geracoes; g++) {
            for (int p = 0; p < populacao.getIndividuos().size(); p++) {
                for (int i = 0; i < lambda / mu; i++) {
                    Random rnd = new Random();
                    double valor = rnd.nextDouble();
                    if (valor <= this.pMutacao) {
                        IndividuoDouble filho = (IndividuoDouble) populacao.getIndividuos().get(p).clone();
                        mutacaoPorBit(filho);
                        
                        //crossover 2 pontos  - AG                        
                        int pai = rnd.nextInt(populacao.getIndividuos().size());                                              
                        crossoverDoisPontos((IndividuoDouble) populacao.getIndividuos().get(pai), filho);
                        
                        
                        IndividuoInteger filhoTSP = converteRealParaInteiro(filho);
                        problema.calcularFuncaoObjetivo(filhoTSP);
                        
                                               
                        filho = converteInteiroParaReal(filhoTSP);
                        filho.setFuncaoObjetivo(filhoTSP.getFuncaoObjetivo());
                        
                                                
                        novaPopulacao.getIndividuos().add(filho);
                    }
                }
            }
            
            populacao.getIndividuos().addAll(novaPopulacao.getIndividuos());
            Collections.sort(populacao.getIndividuos());
            populacao.getIndividuos().subList(this.mu,populacao.getIndividuos().size()).clear();
            
            this.setMelhor(populacao.getMelhorIndividuo().getFuncaoObjetivo());
            System.out.println(this.getMelhor());
        }

    }

    private void mutacaoPorBit(IndividuoDouble filho) {

        Random rnd = new Random();

        double valor;
        for (int i = 0; i < filho.getCromossomos().size(); ++i) {
            if (rnd.nextDouble() <= this.pMutacao) {
                valor = filho.getCromossomos().get(i)
                        * rnd.nextDouble();

                if (rnd.nextBoolean() == false) {
                    valor = -valor;
                }
                valor = filho.getCromossomos().get(i)
                        + valor;
                if (valor >= this.min && valor <= this.max) {
                    filho.getCromossomos().set(i, valor);
                }
            }
        }

    }
    private void crossoverDoisPontos(IndividuoDouble pai, IndividuoDouble filho){
        Random rnd = new Random();
        
        int cInicial = rnd.nextInt(filho.getCromossomos().size()/2);
        int cFinal;
        
        do{
            cFinal = rnd.nextInt(filho.getCromossomos().size());
        }while(cFinal>cInicial);
        
        
        for(int i = cInicial;i<cFinal;i++){
            int idxPai = 0;
            while(true){
                
                if(!filho.getCromossomos().subList(0, cInicial).contains(pai.getCromossomos().get(idxPai)) && 
                        !filho.getCromossomos().subList(cFinal, filho.getCromossomos().size()).contains(pai.getCromossomos().get(idxPai))){
                    filho.getCromossomos().add(i, pai.getCromossomos().get(idxPai));
                    
                    break;
                }
                else{
                    if(idxPai < pai.getCromossomos().size())
                        idxPai++;
                    else
                        idxPai = 0;
                }
                    
            }
        }
        
    }

    private void converteRealParaInteiro(PopulacaoDouble populacao, PopulacaoInteger popTSP) {

        popTSP.getIndividuos().clear();

        for (Individuo ind : populacao.getIndividuos()) {

            IndividuoInteger indTSP = converteRealParaInteiro(ind);

            popTSP.getIndividuos().add(indTSP);
            ind.setFuncaoObjetivo(indTSP.getFuncaoObjetivo());

        }

    }

    private IndividuoInteger converteRealParaInteiro(Individuo ind) {

        IndividuoInteger indTSP = new IndividuoInteger(this.nvar);
        indTSP.setCromossomos(new ArrayList<>(Arrays.asList(new Integer[this.nvar])));
        // Copia dos cromossomos
        ArrayList<Double> crom = (ArrayList<Double>) ind.getCromossomos().clone();
        ArrayList<Double> valores = (ArrayList<Double>) ind.getCromossomos().clone();
        // Ordena
        Collections.sort(crom);

        int cliente = 1;

        for (int i = 0; i < crom.size(); i++) {
            int idx = valores.indexOf(crom.get(i));
            indTSP.getCromossomos().set(idx, cliente);
            valores.set(idx, Double.NaN);
            cliente++;
        }

        indTSP.setFuncaoObjetivo(ind.getFuncaoObjetivo());

        return indTSP;
    }

    private IndividuoDouble converteInteiroParaReal(Individuo ind) {

        IndividuoDouble indDouble = new IndividuoDouble(this.min, this.max, this.nvar);
        for (int i = 0; i < ind.getCromossomos().size(); i++) {

            Double dbl = Double.valueOf(ind.getCromossomos().get(i).toString());
            dbl /= this.nvar;

            indDouble.getCromossomos().add(dbl);
        }

        indDouble.setFuncaoObjetivo(ind.getFuncaoObjetivo());

        return indDouble;
    }
}
