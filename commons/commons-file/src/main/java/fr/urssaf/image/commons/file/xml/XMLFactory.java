package fr.urssaf.image.commons.file.xml;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Element;

public class XMLFactory<O> {

	private static final Logger log = Logger.getLogger(XMLFactory.class);

	private static final String PREFIX = "get";
	private O _instance;

	public XMLFactory(Element element, Class<O> classe) {

		init(element, classe);

	}

	@SuppressWarnings("unchecked")
	private void init(final Element element, Class<O> classe) {

		_instance = (O) Proxy.newProxyInstance(classe.getClassLoader(),
				new Class[] { classe }, new InvocationHandler() {

					@Override
					public Object invoke(Object proxy, Method method,
							Object[] args) throws Throwable {

						if (checkElement(method)) {

							Class type = method.getReturnType();
							String balise = getBalise(method);

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

	@SuppressWarnings("unchecked")
	private String xmlContent(Element element, Class type) {

		if (String.class.isAssignableFrom(type)) {

			return element.getValue();
		}

		log.warn("la méthode getValue() doit renvoyer une chaine de caractère");
		return null;
	}

	@SuppressWarnings("unchecked")
	private Object xmlArray(Element element, String balise, Class type) {

		List<Element> elements = element.getChildren(balise);

		Object array = Array.newInstance(type.getComponentType(), elements
				.size());

		int index = 0;
		for (Element elem : elements) {

			XMLFactory xmlFactory = new XMLFactory(elem, type
					.getComponentType());

			Array.set(array, index, xmlFactory.getInstance());
			index++;
		}

		return array;
	}

	@SuppressWarnings("unchecked")
	private Object xmlInterface(Element element, String balise, Class type) {

		Element elem = element.getChild(balise);

		XMLFactory xmlFactory = new XMLFactory(elem, type);
		return xmlFactory.getInstance();
	}

	@SuppressWarnings("unchecked")
	private Object xmlValue(Element element, String balise, Class type) {

		if (String.class.isAssignableFrom(type)) {

			return element.getAttributeValue(balise);
		}

		log.warn("l'attribut " + balise + " renvoie une chaine de caractère");

		return null;

	}

	private boolean checkElement(Method method) {

		if (void.class.equals(method.getReturnType())) {
			log.warn(method.getDeclaringClass() + ":" + method.getName()
					+ " ne respecte pas la convention not void");
			return false;
		}

		if (method.getParameterTypes().length > 0) {
			log.warn(method.getName()
					+ " ne respecte pas la convention no args");
			return false;
		}

		if (!method.getName().startsWith(PREFIX)) {

			log.warn(method.getDeclaringClass() + "." + method.getName()
					+ " ne respecte pas la convention get");

			return false;
		}

		return true;

	}

	private String getBalise(Method method) {

		return method.getName().substring(PREFIX.length()).toLowerCase();

	}

	public O getInstance() {
		return _instance;
	}

}
