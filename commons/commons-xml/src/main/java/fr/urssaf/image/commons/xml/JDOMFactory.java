package fr.urssaf.image.commons.xml;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Element;

public class JDOMFactory<O> {

	private static final Logger LOGGER = Logger.getLogger(JDOMFactory.class);

	private static final String PREFIX = "get";
	private O instance;
	
	private static final String UNCHECKED = "unchecked";

	public JDOMFactory(Element element, Class<O> classe) {

		init(element, classe);

	}

	@SuppressWarnings(UNCHECKED)
	private void init(final Element element, Class<O> classe) {

		instance = (O) Proxy.newProxyInstance(classe.getClassLoader(),
				new Class[] { classe }, new InvocationHandler() {

					@Override
					public Object invoke(Object proxy, Method method,
							Object[] args) throws Throwable {

						if (checkElement(method)) {

							Class type = method.getReturnType();
							
							String balise = getBalise(method); // NOPMD

							if ("getValue".equals(method.getName())) {

								return xmlContent(element, type);
							}

							else if (type.isArray()) {

								return xmlArray(element, balise, type);

							} else if (type.isInterface()) {

								return xmlInterface(element, balise, type);

							} else {

								return xmlValue(element, balise, type);

							}

						}

						return null;
					}
				});
	}

	@SuppressWarnings(UNCHECKED)
	private String xmlContent(Element element, Class type) {

	   String result = null; //NOPMD
	   
		if (String.class.isAssignableFrom(type)) 
		{
		   result = element.getValue();
		}
		else
		{
		   LOGGER.warn("la méthode getValue() doit renvoyer une chaine de caractère");
	   }
		
		return result;
		
	}

	@SuppressWarnings(UNCHECKED)
	private Object xmlArray(Element element, String balise, Class type) {

		List<Element> elements = element.getChildren(balise);

		Object array = Array.newInstance(type.getComponentType(), elements
				.size());

		int index = 0; //NOPMD
		for (Element elem : elements) {
			JDOMFactory xmlFactory = new JDOMFactory(elem, type.getComponentType()); //NOPMD
			Array.set(array, index, xmlFactory.getInstance());
			index++; //NOPMD
		}

		return array;
	}

	@SuppressWarnings(UNCHECKED)
	private Object xmlInterface(Element element, String balise, Class type) {

		Element elem = element.getChild(balise);

		JDOMFactory xmlFactory = new JDOMFactory(elem, type);
		return xmlFactory.getInstance();
	}

	@SuppressWarnings(UNCHECKED)
	private Object xmlValue(Element element, String balise, Class type) {
	   
	   Object result = null ; //NOPMD
	   if (String.class.isAssignableFrom(type))
	   {
	      result = element.getAttributeValue(balise);
		}
	   else
	   {
	      LOGGER.warn("l'attribut " + balise + " renvoie une chaine de caractère");
	   }
	   return result;

	}

	protected static boolean checkElement(Method method) {

	   boolean result ;
	   
		if (void.class.equals(method.getReturnType())) {
			LOGGER.warn(method.getDeclaringClass() + ":" + method.getName()
					+ " ne respecte pas la convention not void");
			result = false;
		}
		
		else if (method.getParameterTypes().length > 0) {
			LOGGER.warn(method.getName()
					+ " ne respecte pas la convention no args");
			result = false;
		}
		
		else if (!method.getName().startsWith(PREFIX)) //NOPMD
		{ 
		   LOGGER.warn(method.getDeclaringClass() + "." + method.getName()
					+ " ne respecte pas la convention get");

			result = false;
		}
		else
		{
		   result = true;
		}

		return result;
		

	}

	private String getBalise(Method method) {

		return method.getName().substring(PREFIX.length()).toLowerCase();

	}

	public O getInstance() {
		return instance;
	}

}