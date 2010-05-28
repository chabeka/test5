package fr.urssaf.image.commons.controller.spring.formulaire.support.bean;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import org.apache.log4j.Logger;


import fr.urssaf.image.commons.controller.spring.formulaire.MyFormulaire;
import fr.urssaf.image.commons.util.object.ObjectUtil;

public class BeanFormulaire {

	protected static final Logger log = Logger.getLogger(BeanFormulaire.class);

	private Method methodWrite;

	private Method methodRead;

	public boolean isWrite() {
		return this.methodWrite != null;
	}

	public boolean isRead() {
		return this.methodRead != null;
	}

	public void setMethodWrite(Method methodWrite) {
		this.methodWrite = methodWrite;
	}

	public void setMethodRead(Method methodRead) {
		this.methodRead = methodRead;
	}

	public Object read(MyFormulaire form) throws Exception {
		return this.methodRead.invoke(form, new Object[0]);

	}

	public void write(MyFormulaire form, Object obj) throws Exception {

		if (obj == null) {
			this.methodWrite.invoke(form, new Object[] { ObjectUtil
					.getDefaultValue(getType()) });
		} else {
			this.methodWrite.invoke(form, new Object[] { obj });
		}

	}

	@SuppressWarnings("unchecked")
	public Class getType() {
		return this.methodWrite.getParameterTypes()[0];
	}

	public Type getGenericType() {
		return this.methodWrite.getGenericParameterTypes()[0];
	}
	
	public Annotation getAnnotation(Class<? extends Annotation> annotationClass){
		return this.methodRead.getAnnotation(annotationClass);
	}
	
}
