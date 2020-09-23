package com.mycompany.app;

import java.util.*;

/**
 * Projeto: POC Pipeline portal
 * ANO: 2018
 * Empresa:  Group
 */
public class App 
{
    public static void main( String[] args )
    {
    	Calendar c = Calendar.getInstance();
    	System.out.println("Hello World! " + c.get(Calendar.DATE));


    	System.out.println("Controle de qualidade e Pipeline " + c.get(Calendar.DAY_OF_WEEK));
    	
        System.out.println(" Group " + c.get(Calendar.YEAR));
        
    }
}
