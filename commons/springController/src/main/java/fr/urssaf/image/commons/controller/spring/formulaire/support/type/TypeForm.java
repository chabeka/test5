package fr.urssaf.image.commons.controller.spring.formulaire.support.type;

import org.apache.log4j.Logger;

import fr.urssaf.image.commons.controller.spring.formulaire.TypeResource;
import fr.urssaf.image.commons.controller.spring.formulaire.support.exception.TypeFactoryException;
import fr.urssaf.image.commons.controller.spring.formulaire.support.exception.TypeFormulaireException;


public class TypeForm extends TypeFactory{

	protected static final Logger log = Logger.getLogger(TypeForm.class);

	private TypeResource resource;
	
	public TypeForm() {
		resource = TypeResource.getInstance();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public AbstractType getTypeFormulaires(String field, Class classe) {
		
		AbstractType type  = super.getTypeFormulaires(field, classe);
		if(type != null){
			return type;
		}
		
		 type = resource.getTypeFormulaires(field, classe);
		 if(type != null){
				return type;
		 }
		
		 
		 throw new TypeFactoryException(field, classe);
	}
	
	@SuppressWarnings("unchecked")
	public Object getObject(String field,Class classe,String value) throws TypeFormulaireException{
		return this.getTypeFormulaires(field, classe).getObject(value);
	}
	
	@SuppressWarnings("unchecked")
	public Object getObject(Class classe,String value) throws TypeFormulaireException{
		return this.getTypeFormulaires(null, classe).getObject(value);
	}
	
	@SuppressWarnings("unchecked")
	public String getValue(String field,Class classe,Object object){
		return this.getTypeFormulaires(field, classe).getValue(object);
	}
	
	@SuppressWarnings("unchecked")
	public String getValue(Class classe,Object object){
		return this.getTypeFormulaires(null, classe).getValue(object);
	}
	
	


	
}
