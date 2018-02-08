/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agrastrigin;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author lucasgomes
 */
public class Populacao {
    double min;
    double max;
    int nvar;
    int tamanho;

    public Populacao() {
    }
    
    public Populacao(double min, double max, int nvar, int tamanho) {
        this.min = min;
        this.max = max;
        this.nvar = nvar;
        this.tamanho = tamanho;
    }
    

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public int getNvar() {
        return nvar;
    }

    public void setNvar(int nvar) {
        this.nvar = nvar;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public ArrayList<Individuo> getIndividuos() {
        return individuos;
    }

    public void setIndividuos(ArrayList<Individuo> individuos) {
        this.individuos = individuos;
    }
    
    ArrayList<Individuo> individuos = new ArrayList<>();
    
    // Ranking
    // Sobrevivencia
    // Ordenacao
    
    // Criar a populacao inicial
    public void criarPopulacao() {
        
        individuos = new ArrayList<>();
        
        for (int i = 0; i < this.getTamanho(); i++) {
            Individuo individuo = new Individuo(min, max, nvar);
            individuo.criar();
            individuos.add(individuo);
            
        }
        
    }
    
    // Avaliar a populacao
    public void avaliar() {
        for(Individuo individuo : this.getIndividuos()) {
            Problema.calcularFuncaoObjetivo(individuo);
        }
    }
    
    public Individuo getMelhorIndividuo() {
        return Collections.min(individuos);
    }

    public Individuo getPiorIndividuo(){
        return Collections.max(individuos);
    }
}
