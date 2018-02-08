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
public class EvolutionEstrategy {
    private double min;
    private double max;
    private int nvar;
    private int mu;
    private int lambda;  
    private int geracoes;
    private double pMutacao;
    private double melhor;

    public EvolutionEstrategy(double min, double max, int nvar, int mu, int lambda, int geracoes, double pMutacao) {
        this.min = min;
        this.max = max;
        this.nvar = nvar;
        this.mu = mu;
        this.lambda = lambda;
        this.geracoes = geracoes;
        this.pMutacao = pMutacao;
    }

    public double getMelhor() {
        return melhor;
    }

    public void setMelhor(double melhor) {
        this.melhor = melhor;
    }
    
    
    
    public void executar() {
        //gerar populacao inicial aleatoria
        PopulacaoDouble populacao = new PopulacaoDouble(min, max, nvar, lambda);
        populacao.criar();

        //avaliar a populacao
        populacao.avaliar();

        PopulacaoDouble novaPopulacao = new PopulacaoDouble();

        //criterio de parada -- gen
        for (int g = 1; g <= geracoes; g++) {
            //para cada pai, gerar lambda/mu filhos
            for (int p = 0; p < populacao.getIndividuos().size(); p++) {
                for (int i = 0; i < lambda / mu; i++) {
                    //mutacao
                    Random rnd = new Random();
                    double valor = rnd.nextDouble();
                    if (valor <= this.pMutacao) {
                        IndividuoDouble filho = (IndividuoDouble) populacao.getIndividuos().get(p).clone();
                        mutacaoPorBit(filho);
                        //avaliar filhos
                        Problema.calcularFuncaoObjetivo(filho);
                        //Adicionar
                        novaPopulacao.getIndividuos().add(filho);
                        //busca local                 
                    }

                }
            }
            //es(mu,lamda)
            populacao.getIndividuos().clear();

            //es(mu + lambda)
            populacao.getIndividuos().addAll(novaPopulacao.getIndividuos());
            Collections.sort(populacao.getIndividuos());
            populacao.getIndividuos().subList(this.mu, populacao.getIndividuos().size()).clear();

            //System.out.println(populacao.getMelhorIndividuo().getFuncaoObjetivo());
        
            this.setMelhor(populacao.getMelhorIndividuo().getFuncaoObjetivo());
        }

    }

    private void mutacaoPorBit(IndividuoDouble filho) {

        Random rnd = new Random();

        double valor;
        for (int i = 0; i < filho.getCromossomos().size(); ++i) {
            if (rnd.nextDouble() <= this.pMutacao) {
                valor = filho.getCromossomos().get(i) * rnd.nextDouble();

                if (rnd.nextBoolean() == false) {
                    valor = -valor;
                }
                valor = filho.getCromossomos().get(i) + valor;
                if (valor >= this.min && valor <= this.max) {
                    filho.getCromossomos().set(i, valor);
                }
            }
        }

    }
    
}
