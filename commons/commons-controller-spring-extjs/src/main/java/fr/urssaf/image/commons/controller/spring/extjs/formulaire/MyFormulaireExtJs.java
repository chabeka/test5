package fr.urssaf.image.commons.controller.spring.extjs.formulaire;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import fr.urssaf.image.commons.controller.spring.formulaire.MyFormulaire;

public class MyFormulaireExtJs extends MyFormulaire {

	private static String _FORMERRORFIELD_ = "formErrorField" ;
	
	private String formErrorField ;
	
	/**
	 * @param formErrorField the formErrorField to set
	 */
	public void initFormErrorField(String formErrorField) {
		this.formErrorField = formErrorField;
	}
	
	/**
	 * @return the list of form field managed automatically
	 */
	public Set<String> getAllFieldName()
	{
		return getAllFieldName( null );
	}
	
	/**
	 * @return the list of form field managed automatically
	 */
	public Set<String> getAllFieldName( List<String> excludedFiles )
	{
		// récupérayion des la liste des champs basés sur les getters/setters
		Set<String> fieldNameList = this.getMethodeNames();
		
		// exclusion de formErrorField : 
		// il n'est pas inclus car il n'y a pas getter ni setter (juste init)
		if( fieldNameList.contains( _FORMERRORFIELD_ ) )
			fieldNameList.remove( _FORMERRORFIELD_ ) ;
		
		// exclusion de la liste supplémentaire
		if( excludedFiles != null )
		{
			for( String s : excludedFiles )
			{
				if( fieldNameList.contains( s ) )
				{
					fieldNameList.remove( s ) ;
				}
			}
		}
		
		return fieldNameList;
	}

}
