/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agrastrigin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lucasgomes
 */
public class AlgoritmoGenetico {

    int tamPop;
    double pCrossover;
    double pMutacao;
    int geracoes;
    int caso;

    double min;
    double max;
    int nvar;
    
    
    Individuo melhorSolucao;
    Populacao populacao;
    Populacao novaPopulacao;
    

    public AlgoritmoGenetico(int tamPop, double pCrossover, double pMutacao, int geracoes, double min, double max, int nvar,int caso) {
        this.tamPop = tamPop;
        this.pCrossover = pCrossover;
        this.pMutacao = pMutacao;
        this.geracoes = geracoes;
        this.min = min;
        this.max = max;
        this.nvar = nvar;
        this.caso = caso;
        
    }

    public Individuo getMelhorSolucao() {
        return melhorSolucao;
    }

    public void setMelhorSolucao(Individuo melhorSolucao) {
        this.melhorSolucao = melhorSolucao;
    }

       

    public void executar() {

        populacao = new Populacao(min, max, nvar, tamPop);
        novaPopulacao = new Populacao(min, max, nvar, tamPop);

        // Criar a populacao
        populacao.criarPopulacao();
        // Avaliacao da populacao inicial
        populacao.avaliar();

        try {
            // Recuperar o melhor individuo
            melhorSolucao = (Individuo) populacao.getMelhorIndividuo().clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(AlgoritmoGenetico.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        

        //System.out.println("Solucao inicial: " + melhorSolucao.getFuncaoObjetivo());

        novaPopulacao.getIndividuos().clear();

        Random rnd = new Random();

        int pai1, pai2;
        int numPais = (int) ((int) tamPop * 0.2);
        
        // Geracoes - criterio de parada
        for (int gen = 1; gen <= geracoes; gen++) {
            novaPopulacao.getIndividuos().clear();    
                      
            
            // Percorrer a quantidade de individuos 
            // para a reproducao
            for (int j = 0; j < this.tamPop - numPais; ++j) {
                // Reproducao
                // - Avaliar a aplicação do operador
                if (rnd.nextDouble() <= this.pCrossover) {
                    // - Selecionar pais
                    pai1 = rnd.nextInt(this.tamPop);
                    pai2 = rnd.nextInt(this.tamPop);
                    

                    Individuo ind1 = null;
                    
                    try {
                        // Copiar os pais nos descendentes:
                        ind1 = (Individuo) populacao.getIndividuos().get(pai1).clone();
                        
                    } catch (CloneNotSupportedException ex) {
                        Logger.getLogger(AlgoritmoGenetico.class.getName()).log(Level.SEVERE, null, ex);
                    }

//                    System.out.println(populacao.getIndividuos().get(pai1).getCromossomos().size());
//                    System.out.println(populacao.getIndividuos().get(pai2).getCromossomos().size());
                    // - Operar - crossover
                    if(caso == 1)
                        this.crossoverUmPonto(populacao.getIndividuos().get(pai1), populacao.getIndividuos().get(pai2), ind1);
                    else if(caso == 2)
                        this.crossoverDoisPontos(populacao.getIndividuos().get(pai1), populacao.getIndividuos().get(pai2), ind1);

                    // - Operar - mutacao
                    mutacaoPorBit(ind1);
                    

                    // Avaliar descendentes
                    Problema.calcularFuncaoObjetivo(ind1);
                    

                    novaPopulacao.getIndividuos().add(ind1);
                    

                }
            }
            //Selecionando os melhores individuos da populacao 
            //e passando para a nova populacao
            ArrayList<Individuo> pais = new ArrayList<>();

            for (int i = 0; i < numPais; i++) {
                populacao.avaliar();
                pais.add(melhorSolucao);
                populacao.getIndividuos().remove(melhorSolucao);
                
            }
            
            novaPopulacao.getIndividuos().addAll(pais);
            
            // Definir sobrevivencia - pop + descendentes
            // Combinar Pop + NovaPop
            populacao.getIndividuos().addAll(novaPopulacao.getIndividuos());
            Collections.sort(populacao.getIndividuos());
            //populacao.getIndividuos().ensureCapacity(tamPop);
            //populacao.getIndividuos().trimToSize();
            populacao.getIndividuos().subList(tamPop, populacao.getIndividuos().size()).clear();
            pais.clear();
            //System.out.println("\ntamNovaPop: "+novaPopulacao.getIndividuos().size());
            //System.out.println("\ntamPop: "+populacao.getIndividuos().size());

            if (populacao.getMelhorIndividuo().getFuncaoObjetivo() < this.getMelhorSolucao().getFuncaoObjetivo()) {
                try {
                    this.setMelhorSolucao((Individuo) populacao.getMelhorIndividuo().clone());
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(AlgoritmoGenetico.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            

            //System.out.println("Gen = " + gen + "\tFO = " + this.getMelhorSolucao().getFuncaoObjetivo() + "\tPop = " + populacao.getIndividuos().size());

        }

    }

    private void crossoverUmPonto(Individuo pai1, Individuo pai2, Individuo filho) {

        Random rnd = new Random();
        int corte = rnd.nextInt(pai1.getCromossomos().size());

//        System.out.println(filho.getCromossomos().size());
//        System.out.println(pai1.getCromossomos().size());        
        filho.getCromossomos().clear();

//        System.out.println(filho.getCromossomos().size());
//        System.out.println(pai1.getCromossomos().size());        
        filho.getCromossomos().addAll(pai1.getCromossomos().subList(0, corte));
        filho.getCromossomos().addAll(pai2.getCromossomos().subList(corte, pai2.getCromossomos().size()));

    }
    
    private void crossoverDoisPontos(Individuo pai1, Individuo pai2, Individuo filho){
        Random rnd = new Random();
        int cInicial = rnd.nextInt(pai1.getCromossomos().size()/2);
        int cFinal;
        
        do{
            cFinal = rnd.nextInt(pai1.getCromossomos().size());
        }while(cFinal>cInicial);
        
        filho.getCromossomos().clear();
        filho.getCromossomos().addAll(pai1.getCromossomos());
        
        for(int i = cInicial;i<cFinal;i++){
            filho.getCromossomos().set(i, pai2.getCromossomos().get(i));
        }
        
    }

    private void mutacaoPorBit(Individuo filho) {

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
