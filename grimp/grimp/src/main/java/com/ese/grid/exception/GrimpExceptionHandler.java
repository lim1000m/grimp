package com.ese.grid.exception;
import com.ese.comm.util.log.COMLog;

@SuppressWarnings("serial")
public class GrimpExceptionHandler extends Exception{

	public enum GrimpError {CIE, CNFE, FNPE, IAE, NPE, NSFE, JPE, NSFEV}
	
	public GrimpExceptionHandler() {
		super("Error occurred while grimp axis grid assembler working!!");
	}
	
	public GrimpExceptionHandler(GrimpError ge, Exception e) {
		
		super("Error occurred while grimp axis grid assembler working!!");
		
		if(ge.equals(GrimpError.NPE)) {
			COMLog.error("Annotation setting not exist.");
			COMLog.error("Reason : Did you really define the annotation in class in function package ?");
			COMLog.error(e.getMessage());
			e.printStackTrace();
		} else if(ge.equals(GrimpError.NSFE)) {
			COMLog.error("Target field is not exist.");
			COMLog.error("Reason : Cannot find the target field the class in Function package.");
			e.printStackTrace();
		} else if(ge.equals(GrimpError.CNFE)) {
			COMLog.error("Could not find Class in function Package or Failed to instance the class.");
			COMLog.error("Reason : Check it out Class in Function package is exist and same class name with you choose.");
			COMLog.error(e.getMessage());
			e.printStackTrace();	
		} else if(ge.equals(GrimpError.FNPE)) {
			COMLog.error("Annotation setting not exist.");
			COMLog.error("Reason : Did you really define the annotation in class in function package ?");
			COMLog.error(e.getMessage());
			e.printStackTrace();
		} else if (ge.equals(GrimpError.CIE)) {
			COMLog.error("Could not find Class in function Package or Failed to instance the class.");
			COMLog.error("Reason : Check it out Class in Function package is exist and same class name with you choose.");
			COMLog.error(e.getMessage());
			e.printStackTrace();	
		} else if(ge.equals(GrimpError.IAE)) {
			COMLog.error("Not Well form Annotation and Definition variable.");
			COMLog.error(e.getMessage());
			e.printStackTrace();	
		} else if(ge.equals(GrimpError.JPE)) {
			e.printStackTrace();	
		}
	}
}
