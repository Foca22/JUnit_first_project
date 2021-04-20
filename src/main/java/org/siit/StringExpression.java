package org.siit;

import java.util.ArrayList;
import java.util.List;

public class StringExpression implements Expression {
	
	private List<Object> elements;
	
	public StringExpression(String expression) {
		String[] tokens = expression.trim().split("\\s+");
		elements = new ArrayList<>();

		if (tokens.length % 2 == 0 || tokens[0].isEmpty()) {
			throw new ValidationException("Expression must have an even number of tokens.");
		}

		for (int i=0; i<tokens.length; ++i) {
			elements.add(i%2==0
					? readAsNumber(tokens[i]) 
					: readAsBinaryOperator(tokens[i]));
		}
	}
	
	public static Integer readAsNumber(String s) {
		if (s.length() == 0) {
			throw new ValidationException(
					"No value was entered");
		}
		if ( ! isNumber(s) ) {
			throw new ValidationException(
					"Value '" + s + "' is not a number");
		}

		return Integer.parseInt(s);
	}
	
	public static boolean isNumber(String str) {
		try {
			//the next commented line is intentionally incorrect 
			//so exception causes and re-throw can be tested 
			//for (int i=0; i<=str.length(); ++i) {
			for (int i=0; i<str.length(); ++i) {
				if ( ! Character.isDigit(str.charAt(i))) {
					return false;
				}
			}
			return true;
		} catch (RuntimeException e) {
			throw new RuntimeException(
					"Error checking if '" + str + "' is a digit", e);
		}
	}

	private BinaryOperator readAsBinaryOperator(String token) {
		for (BinaryOperator operator : BinaryOperator.values()) {
			try {
				if (operator.getSymbol().equals(token))
					return operator;
			} catch (NumberFormatException e) {
				throw new ValidationException("Operator '" + operator + "'is not an operator");
			}
		}
		return null;
	}

	public List<Object> getElements() {
		return elements;
	}
}
