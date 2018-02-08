/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agrastrigin;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author lucasgomes
 */
public class Individuo implements Comparable<Individuo>{
    
    private ArrayList<Double> cromossomos; //genotipo e fenotipo 
    Double funcaoObjetivo;
    
    double min;
    double max;

    int nvar;

    public Individuo(double min, double max, int nvar) {
        this.min = min;
        this.max = max;
        this.nvar = nvar;
    }
        
    public int getNvar() {
        return nvar;
    }

    public void setNvar(int nvar) {
        this.nvar = nvar;
    }       
    
    public ArrayList<Double> getCromossomos() {
        return cromossomos;
    }

    public void setCromossomos(ArrayList<Double> cromossomos) {
        this.cromossomos = cromossomos;
    }

    public Double getFuncaoObjetivo() {
        return funcaoObjetivo;
    }

    public void setFuncaoObjetivo(Double funcaoObjetivo) {
        this.funcaoObjetivo = funcaoObjetivo;
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
    
    public void criar(){
        
        this.cromossomos = new ArrayList<>();
        
        Random rnd = new Random();
        double valor;
        
        for(int i = 0; i < this.getNvar(); i++) {
            valor = this.getMin() + ( this.getMax() - this.getMin() )* rnd.nextDouble();
            this.cromossomos.add(valor);
        }
        
    }
        
    // Clone

    @Override
    public String toString() {
        return "Individuo{" + "cromossomos=" + cromossomos + ", funcaoObjetivo=" + funcaoObjetivo + '}';
    }

    @Override
    public int compareTo(Individuo o) {
        return this.getFuncaoObjetivo()
                .compareTo(o.getFuncaoObjetivo());
    }

    /**
     *
     * @return
     * @throws CloneNotSupportedException
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        Individuo individuo = null;
        //super.clone();
        individuo = new Individuo(this.getMin(), this.getMax(),this.getNvar());
        individuo.setCromossomos(new ArrayList<>(this.getCromossomos()));
        individuo.setFuncaoObjetivo(this.getFuncaoObjetivo());
        return individuo;        
    }
    
    
    
    
}
