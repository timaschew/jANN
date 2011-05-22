package de.unikassel.ann.gen;

import org.apache.bcel.generic.*;
import org.apache.bcel.*;

import de.unikassel.ann.io.beans.TrainingBean;

import java.io.*;

/**
 * Creates a Bean for CSV import dynamicly<br>
 * Result looks like de.unikassel.ann.gen.DynamicBean
 * 
 * @author anton
 *
 */
public class DynamicBeanCreator implements Constants {
	private static String className;
	private InstructionFactory _factory;
	private ConstantPoolGen _cp;
	private ClassGen _cg;
	private int outputSize;
	private int inputSize;

	public DynamicBeanCreator(int inputSize, int outputSize) {
		String interfaceName = TrainingBean.class.getName();
		_cg = new ClassGen(className, "java.lang.Object", className + "java",
				ACC_PUBLIC | ACC_SUPER, new String[] { interfaceName });

		_cp = _cg.getConstantPool();
		_factory = new InstructionFactory(_cg, _cp);

		this.inputSize = inputSize;
		this.outputSize = outputSize;
	}

	public void create(OutputStream out) throws IOException {
		createFields();
		createStaticSizeSetter();
		createConstructor();
		for (int i = 0; i < inputSize; i++) {
			createGetterForInput(i);
			createSetterForInput(i);
		}
		for (int o = 0; o < outputSize; o++) {
			createGetterForOutput(o);
			createSetterForOutput(o);
		}
		createGetInputSize();
		createGetOutputSize();
		createGetInputWithIndex();
		createGetOutputWithIndex();
		createGetOutputArray();
		createGetInputArray();
		createToString();
		_cg.getJavaClass().dump(out);
	}

	private void createFields() {
		FieldGen field;
	    field = new FieldGen(ACC_PRIVATE | ACC_STATIC, Type.INT, "outputSize", _cp);
	    _cg.addField(field.getField());
	    field = new FieldGen(ACC_PRIVATE | ACC_STATIC, Type.INT, "inputSize", _cp);
	    _cg.addField(field.getField());
	    field = new FieldGen(ACC_PRIVATE, new ArrayType(new ObjectType("java.lang.Double"), 1), "input", _cp);
	    _cg.addField(field.getField());
	    field = new FieldGen(ACC_PRIVATE, new ArrayType(new ObjectType("java.lang.Double"), 1), "output", _cp);
	    _cg.addField(field.getField());
	}

	private void createConstructor() {
	    InstructionList il = new InstructionList();
	    MethodGen method = new MethodGen(ACC_PUBLIC, Type.VOID, Type.NO_ARGS, new String[] {  }, "<init>", className, il, _cp);

	    il.append(InstructionFactory.createLoad(Type.OBJECT, 0));
	    il.append(_factory.createInvoke("java.lang.Object", "<init>", Type.VOID, Type.NO_ARGS, Constants.INVOKESPECIAL));
	    il.append(InstructionFactory.createLoad(Type.OBJECT, 0));
	    il.append(_factory.createFieldAccess(className, "inputSize", Type.INT, Constants.GETSTATIC));
	    il.append(_factory.createNewArray(new ObjectType("java.lang.Double"), (short) 1));
	    il.append(_factory.createFieldAccess(className, "input", new ArrayType(new ObjectType("java.lang.Double"), 1), Constants.PUTFIELD));
	    il.append(InstructionFactory.createLoad(Type.OBJECT, 0));
	    il.append(_factory.createFieldAccess(className, "outputSize", Type.INT, Constants.GETSTATIC));
	    il.append(_factory.createNewArray(new ObjectType("java.lang.Double"), (short) 1));
	    il.append(_factory.createFieldAccess(className, "output", new ArrayType(new ObjectType("java.lang.Double"), 1), Constants.PUTFIELD));
	    il.append(InstructionFactory.createReturn(Type.VOID));
	    method.setMaxStack();
	    method.setMaxLocals();
	    _cg.addMethod(method.getMethod());
	    il.dispose();
	}
  
	private void createStaticSizeSetter() {
	    InstructionList il = new InstructionList();
	    MethodGen method = new MethodGen(ACC_PUBLIC | ACC_STATIC, Type.VOID, new Type[] { Type.INT, Type.INT }, new String[] { "inputSize", "outputSize" }, "setSize", className, il, _cp);

	    il.append(InstructionFactory.createLoad(Type.INT, 0));
	    il.append(_factory.createFieldAccess(className, "inputSize", Type.INT, Constants.PUTSTATIC));
	    il.append(InstructionFactory.createLoad(Type.INT, 1));
	    il.append(_factory.createFieldAccess(className, "outputSize", Type.INT, Constants.PUTSTATIC));
	    il.append(InstructionFactory.createReturn(Type.VOID));
	    method.setMaxStack();
	    method.setMaxLocals();
	    _cg.addMethod(method.getMethod());
	    il.dispose();
	}


	private void createGetInputSize() {
	    InstructionList il = new InstructionList();
	    MethodGen method = new MethodGen(ACC_PUBLIC, Type.INT, Type.NO_ARGS, new String[] {  }, "getInputSize", className, il, _cp);

	    il.append(_factory.createFieldAccess(className, "inputSize", Type.INT, Constants.GETSTATIC));
	    il.append(InstructionFactory.createReturn(Type.INT));
	    method.setMaxStack();
	    method.setMaxLocals();
	    _cg.addMethod(method.getMethod());
	    il.dispose();
	}

	private void createGetOutputSize() {
	    InstructionList il = new InstructionList();
	    MethodGen method = new MethodGen(ACC_PUBLIC, Type.INT, Type.NO_ARGS, new String[] {  }, "getOutputSize", className, il, _cp);

	    il.append(_factory.createFieldAccess(className, "outputSize", Type.INT, Constants.GETSTATIC));
	    il.append(InstructionFactory.createReturn(Type.INT));
	    method.setMaxStack();
	    method.setMaxLocals();
	    _cg.addMethod(method.getMethod());
	    il.dispose();
	}

	private void createGetInputWithIndex() {
		InstructionList il = new InstructionList();
	    MethodGen method = new MethodGen(ACC_PUBLIC, new ObjectType("java.lang.Double"), new Type[] { Type.INT }, new String[] { "index" }, "getInput", className, il, _cp);

	    il.append(InstructionFactory.createLoad(Type.OBJECT, 0));
	    il.append(_factory.createFieldAccess(className, "input", new ArrayType(new ObjectType("java.lang.Double"), 1), Constants.GETFIELD));
	    il.append(InstructionFactory.createLoad(Type.INT, 1));
	    il.append(InstructionConstants.AALOAD);
	    il.append(InstructionFactory.createReturn(Type.OBJECT));
	    method.setMaxStack();
	    method.setMaxLocals();
	    _cg.addMethod(method.getMethod());
	    il.dispose();
	}

	private void createGetOutputWithIndex() {
	    InstructionList il = new InstructionList();
	    MethodGen method = new MethodGen(ACC_PUBLIC, new ObjectType("java.lang.Double"), new Type[] { Type.INT }, new String[] { "index" }, "getOutput", className, il, _cp);

	    il.append(InstructionFactory.createLoad(Type.OBJECT, 0));
	    il.append(_factory.createFieldAccess(className, "output", new ArrayType(new ObjectType("java.lang.Double"), 1), Constants.GETFIELD));
	    il.append(InstructionFactory.createLoad(Type.INT, 1));
	    il.append(InstructionConstants.AALOAD);
	    il.append(InstructionFactory.createReturn(Type.OBJECT));
	    method.setMaxStack();
	    method.setMaxLocals();
	    _cg.addMethod(method.getMethod());
	    il.dispose();
	}
	  
	  
	private void createGetOutputArray() {
		InstructionList il = new InstructionList();
		MethodGen method = new MethodGen(ACC_PUBLIC, new ArrayType(new ObjectType("java.lang.Double"), 1), Type.NO_ARGS, new String[] {  }, "getInput", className, il, _cp);
		
		il.append(InstructionFactory.createLoad(Type.OBJECT, 0));
		il.append(_factory.createFieldAccess(className, "input", new ArrayType(new ObjectType("java.lang.Double"), 1), Constants.GETFIELD));
		il.append(InstructionFactory.createReturn(Type.OBJECT));
		method.setMaxStack();
		method.setMaxLocals();
		_cg.addMethod(method.getMethod());
		il.dispose();
	}

	private void createGetInputArray() {
		InstructionList il = new InstructionList();
		MethodGen method = new MethodGen(ACC_PUBLIC, new ArrayType(new ObjectType("java.lang.Double"), 1), Type.NO_ARGS, new String[] {  }, "getOutput", className, il, _cp);
		
		il.append(InstructionFactory.createLoad(Type.OBJECT, 0));
		il.append(_factory.createFieldAccess(className, "output", new ArrayType(new ObjectType("java.lang.Double"), 1), Constants.GETFIELD));
		il.append(InstructionFactory.createReturn(Type.OBJECT));
		method.setMaxStack();
		method.setMaxLocals();
		_cg.addMethod(method.getMethod());
		il.dispose();
	}
  
  
	private void createGetterForOutput(int i) {
		InstructionList il = new InstructionList();
		MethodGen method = new MethodGen(ACC_PUBLIC, new ObjectType("java.lang.Double"), Type.NO_ARGS, new String[] {  }, "getO"+i, className, il, _cp);
		
		il.append(InstructionFactory.createLoad(Type.OBJECT, 0));
		il.append(_factory.createFieldAccess(className, "output", new ArrayType(new ObjectType("java.lang.Double"), 1), Constants.GETFIELD));
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
	    MethodGen method = new MethodGen(ACC_PUBLIC, Type.VOID, new Type[] { new ObjectType("java.lang.Double") }, new String[] { "arg0" }, "setO"+i, className, il, _cp);
	
	    il.append(InstructionFactory.createLoad(Type.OBJECT, 0));
	    il.append(_factory.createFieldAccess(className, "output", new ArrayType(new ObjectType("java.lang.Double"), 1), Constants.GETFIELD));
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
	    MethodGen method = new MethodGen(ACC_PUBLIC, new ObjectType("java.lang.Double"), Type.NO_ARGS, new String[] {  }, "getI"+i, className, il, _cp);

	    il.append(InstructionFactory.createLoad(Type.OBJECT, 0));
	    il.append(_factory.createFieldAccess(className, "input", new ArrayType(new ObjectType("java.lang.Double"), 1), Constants.GETFIELD));
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
	    MethodGen method = new MethodGen(ACC_PUBLIC, Type.VOID, new Type[] { new ObjectType("java.lang.Double") }, new String[] { "arg0" }, "setI"+i, className, il, _cp);

	    il.append(InstructionFactory.createLoad(Type.OBJECT, 0));
	    il.append(_factory.createFieldAccess(className, "input", new ArrayType(new ObjectType("java.lang.Double"), 1), Constants.GETFIELD));
	    il.append(new PUSH(_cp, i));
	    il.append(InstructionFactory.createLoad(Type.OBJECT, 1));
	    il.append(InstructionConstants.AASTORE);
	    il.append(InstructionFactory.createReturn(Type.VOID));
	    method.setMaxStack();
	    method.setMaxLocals();
	    _cg.addMethod(method.getMethod());
	    il.dispose();
	}
  
	private void createToString() {
	    InstructionList il = new InstructionList();
	    MethodGen method = new MethodGen(ACC_PUBLIC, Type.STRING, Type.NO_ARGS, new String[] {  }, "toString", className, il, _cp);

	    InstructionHandle ih_0 = il.append(_factory.createNew("java.text.DecimalFormat"));
	    il.append(InstructionConstants.DUP);
	    il.append(new PUSH(_cp, "\"#.######\""));
	    il.append(_factory.createInvoke("java.text.DecimalFormat", "<init>", Type.VOID, new Type[] { Type.STRING }, Constants.INVOKESPECIAL));
	    il.append(_factory.createStore(Type.OBJECT, 1));
	    InstructionHandle ih_10 = il.append(_factory.createNew("java.lang.StringBuilder"));
	    il.append(InstructionConstants.DUP);
	    il.append(_factory.createInvoke("java.lang.StringBuilder", "<init>", Type.VOID, Type.NO_ARGS, Constants.INVOKESPECIAL));
	    il.append(_factory.createStore(Type.OBJECT, 2));
	    InstructionHandle ih_18 = il.append(new PUSH(_cp, 0));
	    il.append(_factory.createStore(Type.INT, 3));
	    InstructionHandle ih_20;
	    BranchInstruction goto_20 = _factory.createBranchInstruction(Constants.GOTO, null);
	    ih_20 = il.append(goto_20);
	    InstructionHandle ih_23 = il.append(_factory.createLoad(Type.OBJECT, 2));
	    il.append(_factory.createLoad(Type.OBJECT, 1));
	    il.append(_factory.createLoad(Type.OBJECT, 0));
	    il.append(_factory.createFieldAccess(className, "input", new ArrayType(new ObjectType("java.lang.Double"), 1), Constants.GETFIELD));
	    il.append(_factory.createLoad(Type.INT, 3));
	    il.append(InstructionConstants.AALOAD);
	    il.append(_factory.createInvoke("java.text.NumberFormat", "format", Type.STRING, new Type[] { Type.OBJECT }, Constants.INVOKEVIRTUAL));
	    il.append(_factory.createInvoke("java.lang.StringBuilder", "append", new ObjectType("java.lang.StringBuilder"), new Type[] { Type.STRING }, Constants.INVOKEVIRTUAL));
	    il.append(InstructionConstants.POP);
	    InstructionHandle ih_38 = il.append(_factory.createLoad(Type.OBJECT, 2));
	    il.append(new PUSH(_cp, ";"));
	    il.append(_factory.createInvoke("java.lang.StringBuilder", "append", new ObjectType("java.lang.StringBuilder"), new Type[] { Type.STRING }, Constants.INVOKEVIRTUAL));
	    il.append(InstructionConstants.POP);
	    InstructionHandle ih_45 = il.append(new IINC(3, 1));
	    InstructionHandle ih_48 = il.append(_factory.createLoad(Type.INT, 3));
	    il.append(_factory.createLoad(Type.OBJECT, 0));
	    il.append(_factory.createFieldAccess(className, "input", new ArrayType(new ObjectType("java.lang.Double"), 1), Constants.GETFIELD));
	    il.append(InstructionConstants.ARRAYLENGTH);
	        BranchInstruction if_icmplt_54 = _factory.createBranchInstruction(Constants.IF_ICMPLT, ih_23);
	    il.append(if_icmplt_54);
	    InstructionHandle ih_57 = il.append(new PUSH(_cp, 0));
	    il.append(_factory.createStore(Type.INT, 3));
	    InstructionHandle ih_59;
	    BranchInstruction goto_59 = _factory.createBranchInstruction(Constants.GOTO, null);
	    ih_59 = il.append(goto_59);
	    InstructionHandle ih_62 = il.append(_factory.createLoad(Type.OBJECT, 2));
	    il.append(_factory.createLoad(Type.OBJECT, 1));
	    il.append(_factory.createLoad(Type.OBJECT, 0));
	    il.append(_factory.createFieldAccess(className, "output", new ArrayType(new ObjectType("java.lang.Double"), 1), Constants.GETFIELD));
	    il.append(_factory.createLoad(Type.INT, 3));
	    il.append(InstructionConstants.AALOAD);
	    il.append(_factory.createInvoke("java.text.NumberFormat", "format", Type.STRING, new Type[] { Type.OBJECT }, Constants.INVOKEVIRTUAL));
	    il.append(_factory.createInvoke("java.lang.StringBuilder", "append", new ObjectType("java.lang.StringBuilder"), new Type[] { Type.STRING }, Constants.INVOKEVIRTUAL));
	    il.append(InstructionConstants.POP);
	    InstructionHandle ih_77 = il.append(_factory.createLoad(Type.OBJECT, 2));
	    il.append(new PUSH(_cp, ";"));
	    il.append(_factory.createInvoke("java.lang.StringBuilder", "append", new ObjectType("java.lang.StringBuilder"), new Type[] { Type.STRING }, Constants.INVOKEVIRTUAL));
	    il.append(InstructionConstants.POP);
	    InstructionHandle ih_84 = il.append(new IINC(3, 1));
	    InstructionHandle ih_87 = il.append(_factory.createLoad(Type.INT, 3));
	    il.append(_factory.createLoad(Type.OBJECT, 0));
	    il.append(_factory.createFieldAccess(className, "output", new ArrayType(new ObjectType("java.lang.Double"), 1), Constants.GETFIELD));
	    il.append(InstructionConstants.ARRAYLENGTH);
	    il.append(new PUSH(_cp, 1));
	    il.append(InstructionConstants.ISUB);
	        BranchInstruction if_icmplt_95 = _factory.createBranchInstruction(Constants.IF_ICMPLT, ih_62);
	    il.append(if_icmplt_95);
	    InstructionHandle ih_98 = il.append(_factory.createLoad(Type.OBJECT, 2));
	    il.append(_factory.createLoad(Type.OBJECT, 1));
	    il.append(_factory.createLoad(Type.OBJECT, 0));
	    il.append(_factory.createFieldAccess(className, "output", new ArrayType(new ObjectType("java.lang.Double"), 1), Constants.GETFIELD));
	    il.append(_factory.createLoad(Type.OBJECT, 0));
	    il.append(_factory.createFieldAccess(className, "output", new ArrayType(new ObjectType("java.lang.Double"), 1), Constants.GETFIELD));
	    il.append(InstructionConstants.ARRAYLENGTH);
	    il.append(new PUSH(_cp, 1));
	    il.append(InstructionConstants.ISUB);
	    il.append(InstructionConstants.AALOAD);
	    il.append(_factory.createInvoke("java.text.NumberFormat", "format", Type.STRING, new Type[] { Type.OBJECT }, Constants.INVOKEVIRTUAL));
	    il.append(_factory.createInvoke("java.lang.StringBuilder", "append", new ObjectType("java.lang.StringBuilder"), new Type[] { Type.STRING }, Constants.INVOKEVIRTUAL));
	    il.append(InstructionConstants.POP);
	    InstructionHandle ih_119 = il.append(_factory.createLoad(Type.OBJECT, 2));
	    il.append(new PUSH(_cp, "\n"));
	    il.append(_factory.createInvoke("java.lang.StringBuilder", "append", new ObjectType("java.lang.StringBuilder"), new Type[] { Type.STRING }, Constants.INVOKEVIRTUAL));
	    il.append(InstructionConstants.POP);
	    InstructionHandle ih_126 = il.append(_factory.createLoad(Type.OBJECT, 2));
	    il.append(_factory.createInvoke("java.lang.StringBuilder", "toString", Type.STRING, Type.NO_ARGS, Constants.INVOKEVIRTUAL));
	    InstructionHandle ih_130 = il.append(_factory.createReturn(Type.OBJECT));
	    goto_20.setTarget(ih_48);
	    goto_59.setTarget(ih_87);
	    method.setMaxStack();
	    method.setMaxLocals();
	    _cg.addMethod(method.getMethod());
	    il.dispose();
	}

  public static String createAndWriteClass(int inputSize, int outputSize) throws FileNotFoundException, IOException {
	className = "pBean";
	String targetPath = "target/classes/";
	//TODO: does not work with package, class won't be found
	String packag = "de/unikassel/ann/gen/";
	DynamicBeanCreator creator = new DynamicBeanCreator(inputSize, outputSize);
    
    creator.create(new FileOutputStream(targetPath+className+".class"));
//    return (packag+className).replaceAll("/", ".");
    return className;
  }
}
