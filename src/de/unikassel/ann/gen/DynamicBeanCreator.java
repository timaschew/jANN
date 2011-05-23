package de.unikassel.ann.gen;

import org.apache.bcel.generic.*;
import org.apache.bcel.*;
import org.apache.commons.lang.StringUtils;

import de.unikassel.ann.io.beans.TrainingBean;

import java.io.*;

/**
 * Creates a Bean for CSV import dynamically<br>
 * Result looks like de.unikassel.ann.gen.GeneratedXorBeanExample
 * 
 * @author anton
 *
 */
public class DynamicBeanCreator implements Constants {
	/**
	 * with package, example de.misc.MyClass
	 */
	private static String fullClassName;
	private static int classNameCounter = 0;
	private InstructionFactory _factory;
	private ConstantPoolGen _cp;
	private ClassGen _cg;
	private int outputSize;
	private int inputSize;

	public DynamicBeanCreator(int inputSize, int outputSize) {
		_cg = new ClassGen(fullClassName, TrainingBean.class.getName(), fullClassName + "java", ACC_PUBLIC | ACC_SUPER, new String[] {});

		_cp = _cg.getConstantPool();
		_factory = new InstructionFactory(_cg, _cp);

		this.inputSize = inputSize;
		this.outputSize = outputSize;
	}

	public void create(OutputStream out) throws IOException {
		createInit();
		for (int i = 0; i < inputSize; i++) {
			createGetterForInput(i);
			createSetterForInput(i);
		}
		for (int o = 0; o < outputSize; o++) {
			createGetterForOutput(o);
			createSetterForOutput(o);
		}

		_cg.getJavaClass().dump(out);
		out.flush();
		out.close();
	}

	private void createInit() {
	    InstructionList il = new InstructionList();
	    MethodGen method = new MethodGen(ACC_PUBLIC, Type.VOID, Type.NO_ARGS, new String[] {  }, "<init>", fullClassName, il, _cp);

		il.append(InstructionFactory.createLoad(Type.OBJECT, 0));
	    il.append(_factory.createInvoke(TrainingBean.class.getName(), "<init>", Type.VOID, Type.NO_ARGS, Constants.INVOKESPECIAL));
	    il.append(InstructionFactory.createReturn(Type.VOID));
	    method.setMaxStack();
	    method.setMaxLocals();
	    _cg.addMethod(method.getMethod());
	    il.dispose();	    
	}
	
	
  
	private void createGetterForOutput(int i) {
		InstructionList il = new InstructionList();
		MethodGen method = new MethodGen(ACC_PUBLIC, new ObjectType("java.lang.Double"), Type.NO_ARGS, new String[] {  }, "getO"+i, fullClassName, il, _cp);
		
		il.append(InstructionFactory.createLoad(Type.OBJECT, 0));
		il.append(_factory.createFieldAccess(fullClassName, "output", new ArrayType(new ObjectType("java.lang.Double"), 1), Constants.GETFIELD));
		il.append(new PUSH(_cp, i));
		il.append(InstructionConstants.AALOAD);
		il.append(InstructionFactory.createReturn(Type.OBJECT));
		method.setMaxStack();
		method.setMaxLocals();
		_cg.addMethod(method.getMethod());
		il.dispose();
	}

	private void createSetterForOutput(int i) {
	    InstructionList il = new InstructionList();
	    MethodGen method = new MethodGen(ACC_PUBLIC, Type.VOID, new Type[] { new ObjectType("java.lang.Double") }, new String[] { "value" }, "setO"+i, fullClassName, il, _cp);
	
	    il.append(InstructionFactory.createLoad(Type.OBJECT, 0));
	    il.append(_factory.createFieldAccess(fullClassName, "output", new ArrayType(new ObjectType("java.lang.Double"), 1), Constants.GETFIELD));
	    il.append(new PUSH(_cp, i));
	    il.append(InstructionFactory.createLoad(Type.OBJECT, 1));
	    il.append(InstructionConstants.AASTORE);
	    il.append(InstructionFactory.createReturn(Type.VOID));
	    method.setMaxStack();
	    method.setMaxLocals();
	    _cg.addMethod(method.getMethod());
	    il.dispose();
	}
  
	private void createGetterForInput(int i) {
		InstructionList il = new InstructionList();
	    MethodGen method = new MethodGen(ACC_PUBLIC, new ObjectType("java.lang.Double"), Type.NO_ARGS, new String[] {  }, "getI"+i, fullClassName, il, _cp);

	    il.append(InstructionFactory.createLoad(Type.OBJECT, 0));
	    il.append(_factory.createFieldAccess(fullClassName, "input", new ArrayType(new ObjectType("java.lang.Double"), 1), Constants.GETFIELD));
	    il.append(new PUSH(_cp, i));
	    il.append(InstructionConstants.AALOAD);
	    il.append(InstructionFactory.createReturn(Type.OBJECT));
	    method.setMaxStack();
	    method.setMaxLocals();
	    _cg.addMethod(method.getMethod());
	    il.dispose();
	}
  
	private void createSetterForInput(int i) {
		InstructionList il = new InstructionList();
	    MethodGen method = new MethodGen(ACC_PUBLIC, Type.VOID, new Type[] { new ObjectType("java.lang.Double") }, new String[] { "value" }, "setI"+i, fullClassName, il, _cp);

	    il.append(InstructionFactory.createLoad(Type.OBJECT, 0));
	    il.append(_factory.createFieldAccess(fullClassName, "input", new ArrayType(new ObjectType("java.lang.Double"), 1), Constants.GETFIELD));
	    il.append(new PUSH(_cp, i));
	    il.append(InstructionFactory.createLoad(Type.OBJECT, 1));
	    il.append(InstructionConstants.AASTORE);
	    il.append(InstructionFactory.createReturn(Type.VOID));
	    method.setMaxStack();
	    method.setMaxLocals();
	    _cg.addMethod(method.getMethod());
	    il.dispose();
	}
  
	public static String createAndWriteClass(int inputSize, int outputSize, String className) throws FileNotFoundException, IOException {
		String packageName = DynamicBeanCreator.class.getPackage().getName();
		
		if (StringUtils.isBlank(className)) {
			className = "DynamicBean";
		}
		fullClassName = packageName + "." + className + classNameCounter;

		DynamicBeanCreator creator = new DynamicBeanCreator(inputSize, outputSize);
		
		// replace dots with slash
		String filePath = packageName.replaceAll("\\.", "/"); 
		filePath = "target/classes/" + filePath + "/" + className + classNameCounter;
		creator.create(new FileOutputStream(filePath + ".class"));
		classNameCounter++;

		return fullClassName;
  }
}
