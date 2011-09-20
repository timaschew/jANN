/**
 * Projekt ANNtool 
 *
 * Copyright (c) 2011 github.com/timaschew/jANN
 * 
 * anton
 */
package de.unikassel.ann.model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import de.unikassel.ann.config.NetConfig;
import de.unikassel.ann.strategy.MaxLearnIterationsStrategy;
import de.unikassel.ann.strategy.MinErrorStrategy;
import de.unikassel.ann.strategy.RestartErrorStrategy;
import de.unikassel.ann.strategy.RestartImprovementStrategy;
import de.unikassel.ann.strategy.Strategy;

/**
 * @author anton
 * 
 */
public class StrategyReflectionTest {

	public class ParamAndType {
		public String paramName;
		public Class type;

		public ParamAndType(final String name, final Class type) {
			// remove the '_'
			if (name.startsWith("_")) {
				paramName = name.substring(1);
			} else {
				paramName = name;
			}
			this.type = type;
		}

		@Override
		public String toString() {
			return paramName + " (" + type.getSimpleName() + ")";
		}
	}

	@Test
	public void testCreateNameAndTypes() {
		// 1.
		// extract the class fields and types and save it into the map

		Class[] strategiesClasses = new Class[] { MaxLearnIterationsStrategy.class, MinErrorStrategy.class, RestartErrorStrategy.class,
				RestartImprovementStrategy.class };

		Map<Class, List<ParamAndType>> map = new HashMap<Class, List<ParamAndType>>();

		for (Class c : strategiesClasses) {
			ArrayList<ParamAndType> keyList = new ArrayList<ParamAndType>();
			map.put(c, keyList);
			Field[] fields = c.getDeclaredFields();
			for (Field f : fields) {
				if (f.getName().startsWith("_")) {
					// this is a parameter which should be set in the gui
					ParamAndType paramAndType = new ParamAndType(f.getName(), f.getType());
					keyList.add(paramAndType);
				}
			}
		}

		// 2.
		// create the swing components

		NetConfig cfg = new NetConfig();
		// 3.
		// set the fields with value from the swing components
		// and add it to the NetConfig
		for (Entry<Class, List<StrategyReflectionTest.ParamAndType>> e : map.entrySet()) {
			Class clazz = e.getKey();
			Object obj = null;
			try {
				obj = clazz.newInstance();
				cfg.addOrUpdateExisting((Strategy) obj);
			} catch (InstantiationException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (IllegalAccessException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			List<ParamAndType> paramAndTypeList = e.getValue();
			for (ParamAndType pat : paramAndTypeList) {
				try {
					Field f = clazz.getDeclaredField("_" + pat.paramName);
					f.setAccessible(true);
					// primitive types
					if (pat.type.equals(Integer.TYPE)) {
						f.setInt(obj, 12); // value from the swing component
					} else if (pat.type.equals(Double.TYPE)) {
						f.setDouble(obj, 12.34); // value from the swing component

						// object types
					} else if (pat.type.equals(Integer.class)) {
						f.set(obj, new Integer(12)); // value from the swing component
					} else if (pat.type.equals(Double.class)) {
						f.set(obj, new Double(12.34)); // value from the swing component
					} else {
						System.err.println("type not supported: " + f.getType());
					}
				} catch (SecurityException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NoSuchFieldException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalArgumentException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		}

	}

	@Test
	public void test() {

		Field[] fields = MaxLearnIterationsStrategy.class.getDeclaredFields();
		for (Field f : fields) {
			Class<?> type = f.getType();
			System.out.println(f.getName() + " / " + type.getSimpleName());
			try {
				Class cls = Class.forName(MaxLearnIterationsStrategy.class.getName());
				Field fld = cls.getDeclaredField(f.getName());
				fld.setAccessible(true);
				Object obj = cls.newInstance();
				// first is the Integer object, 2nd the primitive type
				if (f.getType().equals(Integer.class) || f.getType().equals(Integer.TYPE)) {
					fld.setInt(obj, 12);
				} else if (f.getType().equals(Double.class) || f.getType().equals(Double.TYPE)) {
					fld.setDouble(obj, 12.34);
				} else {
					System.err.println("type not supported: " + f.getType());
				}

			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
