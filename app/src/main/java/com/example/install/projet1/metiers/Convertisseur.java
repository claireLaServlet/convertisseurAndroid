package com.example.install.projet1.metiers;

import java.util.*;

public class Convertisseur
{
	private static Map conversionTable = new HashMap();
	
	static                                                                                                     
   	{                                                                                                                                               
   		conversionTable.put("Livre", new Double(0.680987));                                           
   		conversionTable.put("Dollars canadien", new Double(1.51353));                                          
   		conversionTable.put("Riyals", new Double(3.75050));                                      
   		conversionTable.put("Yen", new Double(117.247));                                              
   		conversionTable.put("Ringits malaisien", new Double(3.79900));                                         
   		conversionTable.put("Dollars US", new Double(1.0));                                                    
   	}                                                                                                          
   	public static double convertir(String source, String cible, double montant)                                                                                          
   	{                                                                                                          
   		//The constants should probably be defined somewhere else                                              
   		double tauxSource = ((Double)conversionTable.get(source)).doubleValue() ;                    
   		double tauxCible = ((Double)conversionTable.get(cible)).doubleValue() ;                    
   		double tauxConversion = tauxCible/tauxSource;	                                                       
   		return (montant * tauxConversion) ;                                                               
   	}           
   	
   	public static Map getConversionTable()
   	{
   		return conversionTable;	
   	}                                                                                               
}                                                                                                         