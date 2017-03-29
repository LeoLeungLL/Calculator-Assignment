import java.util.Hashtable;

import org.apache.log4j.*;
public class Calculator {
	//use a hashtable to store variables for let statements
	Hashtable<String, Double> variables = new Hashtable<String, Double>();
	//create logger
	static Logger logger = Logger.getLogger(Calculator.class);
	
	public static void main(String[] args)
	{		
		BasicConfigurator.configure();
		String input = new String();
		//set verbosity level
		if(args[0].equals("-d"))
		{
			input = args[1];
			logger.setLevel(Level.DEBUG);
		}
		else if (args[0].equals("-i"))
		{
			 input = args[1];
			 logger.setLevel(Level.INFO);
		}
		else if(args[0].equals("-e"))
		{
			 input = args[1];
			 logger.setLevel(Level.ERROR);
		}
		else
		{
			logger.setLevel(Level.ALL);
			 input = args[0];
		}
		logger.info("Calculator Started.");
		
		logger.info("Input: " +input);
		logger.info("Answer: " + new Calculator().Evaluate(input));
		System.out.println("Input: " +input);
		System.out.println("Answer: " + new Calculator().Evaluate(input));
		
	}
	
	/*
	*********************************************************************
	using double as data type because division may not lead to integers.
	*********************************************************************
	*/
	
	public double add(double a, double b)
	{
		logger.debug("Result: " + (a+b));
		return a + b;
	}
	public double sub(double a, double b)
	{
		logger.debug("Result: " + (a-b));
		return a - b;
	}
	public double mult(double a, double b)
	{
		logger.debug("Result: " + (a*b));
		return a * b;
	}
	public double div(double a, double b)
	{
		if (b==0)
		{
			throw new NumberFormatException("Cannot divide by zero!");
		}
		else
		{
			logger.debug("Result: " + (a/b));
			return a / b;
		}
		
	}
	public double let(String name, Double value, String expression)
	{
		if(isValidVariable(name))
		{
			variables.put(name,  value);
			return Evaluate(expression);
		}
		else
		{
			throw new NumberFormatException ("invalid variable input: " + name);
		}
		
		
	}
	
	//function returns type double because division may not return an integer
	public double Evaluate(String expression)
	{
		
		try
		{
			//proper input only accepts integers as numbers
			if (isInteger(expression))
			{
				//input checking
				if (Integer.parseInt(expression)<Integer.MIN_VALUE || Integer.parseInt(expression)>Integer.MAX_VALUE)
				{
					throw new NumberFormatException("input outside bounds: " + expression);
				}
				else
				{					
					return Double.parseDouble(expression);
				}
			}
			else if (isValidVariable(expression))
			{
				try
				{
					logger.debug("Variable: " + expression + "=" + variables.get(expression));
					
					return variables.get(expression);
				}
				catch (Exception e)
				{
					throw new NumberFormatException("variable not found: " + expression);
				}
			}
			
			//function name always appears before the opening bracket
			try{
				logger.debug("Evaluating expression: " + expression);
				String[] split = expression.split("\\(", 2);
				String functionName = split[0];
				
				int closeBracketIndex = split[1].lastIndexOf(")");
				String arguments = split[1].substring(0, closeBracketIndex);
				
				logger.debug("function: " + functionName);
				logger.debug("arguments: " + arguments);
				
				//array of arguments to be passed in function
				String[] args = splitArgs(arguments);
				
				logger.debug("arg 1:"+ args[0]);
				logger.debug("arg 2:"+ args[1]);
				logger.debug("arg 3:"+ args[2]);

				//determine function chosen 
				switch(functionName){
				case "add": 
					return  add(Evaluate(args[0]), Evaluate(args[1]));
				case "sub":
					return sub(Evaluate(args[0]), Evaluate(args[1]));
				case "mult":
					return mult(Evaluate(args[0]), Evaluate(args[1]));
				case "div":
					return div(Evaluate(args[0]), Evaluate(args[1]));
				case "let":
					return let(args[0], Evaluate(args[1]), args[2]);
				
				default :
					throw new Exception("invalid function");
					
				}
				
				
				
				
			}
			catch(Exception e)
			{
				throw new NumberFormatException("invalid expression, check brackets");
			}
		}
		catch(NumberFormatException e)
		{
			logger.error("Invalid Input: " + e.getMessage());
		}
		return 0;
	}
	//split arguments into array of arguments to be passed, keeps nested functions together
	private String[] splitArgs(String arguments)
	{
		String[] split = arguments.split(",");
		int bracketCount = 0;
		String[] args = new String[3];
		
		int argCounter = 0;
		
		//split up arguments, checks for nested functions
		for (int i =0; i< split.length; i++)
		{
			
			//count brackets to isolate keep bracketed sections of the equation together
			//recreated bracketed section of string, and replace commas
			if (split[i].contains("("))
			{
				bracketCount++;	
				//first bracket, initialize string
				if (bracketCount ==1)
				{
					args[argCounter] = split[i] + ",";
				}
				else
				{
					args[argCounter] = args[argCounter] + split[i] + ",";
				}
			}
	
			else if (split[i].contains(")"))
			{				
				bracketCount = bracketCount - charCount(split[i], "\\)");
				//not end of bracketed section, replace commas
				if (bracketCount >0)
				{
					args[argCounter] = args[argCounter] + split[i] + ",";
				}
				//end of bracketed section, do not replace comma
				else
				{
					args[argCounter] = args[argCounter] + split[i];
					
				}
			}
			//numbers or variables in bracketed section, replace commas
			else if(bracketCount > 0)
			{
				args[argCounter] = args[argCounter] + split[i]+",";
			}
			//numbers or variables not in a bracket
			else
			{
				args[argCounter] = split[i];
			}
			//move to next argument
			if (bracketCount == 0)
			{
				argCounter++;
			}
			
		}
		return args;
	}
	protected int charCount(String expression, String character)
	{
		return expression.length() - expression.replaceAll(character, "").length();
	}
	protected boolean isInteger(String expression)
	{
		boolean ret = true;
		try{
			Integer.parseInt(expression);			
		}
		catch (final NumberFormatException e)
		{
			ret = false;
		}
		return ret;
	}
	protected boolean isValidVariable(String expression)
	{
		boolean ret = true;
		//variable is a single character
		if (expression.length()!=1)
		{
			ret = false;
		}
		else if (!expression.matches("[a-zA-Z]"))
		{
			ret = false;
		}
		return ret;
	}
}
