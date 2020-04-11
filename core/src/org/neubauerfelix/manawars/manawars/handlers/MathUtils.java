package org.neubauerfelix.manawars.manawars.handlers;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class MathUtils {

	public static double calc(String s) {
		if (s.length() == 0) {
			return 0;
		}
		try {
			Expression e = new ExpressionBuilder(s).build();
			return e.evaluate();
		} catch (Exception e) {
			throw new RuntimeException("Invalid expression '" + s + "'.");
		}
	}
	public static int calcInt(String s) {
		if (s.length() == 0) {
			return 0;
		}
		return (int) calc(s);
	}

	public static double calc(String s, String variable, double value){
		Expression e = new ExpressionBuilder(s).variable(variable).build();
		e.setVariable(variable, value);
		return e.evaluate();
	}
	public static int calcInt(String s, String variable, double value){
		return (int) calc(s, variable, value);
	}

	public static double calc(String s, String[] variables, double[] values){
		if (s.length() == 0) {
			return 0;
		}
		if(variables.length != values.length){
			throw new RuntimeException("Invalid calculation variables. Variable count: " + values.length+". Value count: " + values.length+".");
		}
		Expression e = new ExpressionBuilder(s).build();
		for(int i = 0; i<variables.length; i++){
			e.setVariable(variables[i], values[i]);
		}
		return e.evaluate();
	}
	public static int calcInt(String s, String[] variables, double[] values){
		return (int) calc(s, variables, values);
	}
	
	
	public static double calculateMovementTime(double speed, double acceleration, double distance_to_move){
		if(acceleration != 0){
			double a = acceleration;
			double b = speed;
			double c = -distance_to_move;
			double temp1 = Math.sqrt(b * b - 4 * a * c);        
			double root1 = (-b +  temp1) / (2*a) ;
			double root2 = (-b -  temp1) / (2*a) ;
			if(root1 < 0) root1 = Double.POSITIVE_INFINITY;
			if(root2 < 0) root2 = Double.POSITIVE_INFINITY;
			return Math.min(root1, root2); //in seconds
		}else if(speed != 0){
			return distance_to_move / speed;
		}else{
			return (distance_to_move == 0 ? 0 : Double.POSITIVE_INFINITY);
		}
	}
}
