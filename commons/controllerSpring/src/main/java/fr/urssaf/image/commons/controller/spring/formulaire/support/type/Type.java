package fr.urssaf.image.commons.controller.spring.formulaire.support.type;



public class Type<T> {

	private Class<? extends T> classe;

	private AbstractType<T> abstractType;

	private String field;

	public Type(Class<? extends T> classe, AbstractType<T> type) {
		this.classe = classe;
		this.abstractType = type;
	}

	public Type(String field, AbstractType<T> type) {
		this.field = field;
		this.abstractType = type;
	}
	
	public Class<? extends T> getClasse(){
		return this.classe;
	}
	
	public AbstractType<T> getType(){
		return this.abstractType;
	}
	
	public String getField(){
		return this.field;
	}
}
