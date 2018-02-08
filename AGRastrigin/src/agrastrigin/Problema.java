 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agrastrigin;

/**
 *
 * @author lucasgomes
 */
public class Problema {
    public static void calcularFuncaoObjetivo(Individuo individuo) {
        
        Double rastring;               
        Double soma = 0.0;
        
        rastring = individuo.getNvar() * 10.0;
        
        for(int i =0; i < individuo.nvar; i++)//executar sem conversao direta
            soma += Math.pow(individuo.getCromossomos().get(i),2) - 10*Math.cos(2*Math.PI*individuo.getCromossomos().get(i));
        
        rastring += soma;
        
        individuo.setFuncaoObjetivo(rastring);        
        
    }
    
}
