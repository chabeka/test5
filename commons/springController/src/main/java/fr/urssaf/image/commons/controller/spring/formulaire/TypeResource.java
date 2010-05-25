package fr.urssaf.image.commons.controller.spring.formulaire;

import java.util.Date;

import fr.urssaf.image.commons.controller.spring.formulaire.support.type.Type;
import fr.urssaf.image.commons.controller.spring.formulaire.support.type.TypeFactory;
import fr.urssaf.image.commons.controller.spring.formulaire.type.BoolType;
import fr.urssaf.image.commons.controller.spring.formulaire.type.BooleanType;
import fr.urssaf.image.commons.controller.spring.formulaire.type.ByType;
import fr.urssaf.image.commons.controller.spring.formulaire.type.ByteType;
import fr.urssaf.image.commons.controller.spring.formulaire.type.DType;
import fr.urssaf.image.commons.controller.spring.formulaire.type.DateType;
import fr.urssaf.image.commons.controller.spring.formulaire.type.DoubleType;
import fr.urssaf.image.commons.controller.spring.formulaire.type.FType;
import fr.urssaf.image.commons.controller.spring.formulaire.type.FloatType;
import fr.urssaf.image.commons.controller.spring.formulaire.type.IntType;
import fr.urssaf.image.commons.controller.spring.formulaire.type.IntegerType;
import fr.urssaf.image.commons.controller.spring.formulaire.type.LType;
import fr.urssaf.image.commons.controller.spring.formulaire.type.LongType;
import fr.urssaf.image.commons.controller.spring.formulaire.type.ShType;
import fr.urssaf.image.commons.controller.spring.formulaire.type.ShortType;
import fr.urssaf.image.commons.controller.spring.formulaire.type.StringType;

public final class TypeResource extends TypeFactory {

	private static TypeResource _instance;

	public static TypeResource getInstance() {
		synchronized (new Object()) {
			if (_instance == null) {
				_instance = new TypeResource();
			}
		}
		return _instance;
	}

	private TypeResource() {

		Type<Date> typeDate = new Type<Date>(Date.class, new DateType());
		Type<Integer> typeInteger = new Type<Integer>(Integer.class,
				new IntegerType());
		Type<String> typeString = new Type<String>(String.class,
				new StringType());
		Type<Boolean> typeBoolean = new Type<Boolean>(Boolean.class,
				new BooleanType());
		Type<Float> typeFloat = new Type<Float>(Float.class, new FloatType());
		Type<Double> typeDouble = new Type<Double>(Double.class,
				new DoubleType());
		Type<Byte> byteType = new Type<Byte>(Byte.class, new ByteType());
		Type<Short> shortType = new Type<Short>(Short.class, new ShortType());
		Type<Long> longType = new Type<Long>(Long.class, new LongType());

		this.addTypeFormulaire(typeDate);
		this.addTypeFormulaire(typeInteger);
		this.addTypeFormulaire(typeString);
		this.addTypeFormulaire(typeBoolean);
		this.addTypeFormulaire(typeFloat);
		this.addTypeFormulaire(typeDouble);
		this.addTypeFormulaire(byteType);
		this.addTypeFormulaire(shortType);
		this.addTypeFormulaire(longType);

		this.addTypeFormulaire(Integer.class, new IntType());
		this.addTypeFormulaire(Boolean.class, new BoolType());
		this.addTypeFormulaire(Float.class, new FType());
		this.addTypeFormulaire(Double.class, new DType());
		this.addTypeFormulaire(Short.class, new ShType());
		this.addTypeFormulaire(Long.class, new LType());
		this.addTypeFormulaire(Byte.class, new ByType());

	}

}
