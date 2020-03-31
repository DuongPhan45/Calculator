package project1;

//Name: Duong Phan
//NetID: dphan7
//Lab section: Gavett 244 TR 2-3.15pm
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Scanner;

public class Calculator {

	public QueueImplement<Object> queue = new QueueImplement<Object>();
	public StackImplement<Object> stack = new StackImplement<Object>();
	public static HashMap<Object, Integer> operator = new HashMap<Object, Integer>();// State the priority order of the
																						// operators

	// State the priority order of the operators
	public static void setOperator() {
		operator.put("&", 0);
		operator.put("|", 0);
		operator.put("!", 4);
		operator.put("<", 2);
		operator.put(">", 2);
		operator.put("=", 2);
		operator.put("+", 3);
		operator.put("-", 3);
		operator.put("*", 4);
		operator.put("/", 4);
		operator.put("%", 4);
		operator.put("^", 5);
		operator.put("sin", 6);
		operator.put("cos", 6);
		operator.put("tan", 6);
		operator.put("(", -1);

	}

	// Convert infix expressions to postfix expressions
	public void ShuntingYard(Object[] a) {
		setOperator();

		for (Object element : a) {
			if (element instanceof Number) {
				queue.enqueue((element));
			} else {
				if (element.equals(")")) {// pop all the stack elements and enqueue them one by one until an
											// open-parenthesis [‘(‘] is found

					while (!stack.peek().equals("(")) {
						queue.enqueue(stack.pop());
					}
					// Remove "("
					stack.pop();
				} // If the input is "("
				else if (element.equals("("))
					stack.push(element);
				// If the input is an operator
				else {
					if (stack.peek() == null)
						stack.push(element);
					else {
						while (stack.peek() != null && operator.get(stack.peek()) > operator.get(element))
							queue.enqueue(stack.pop());
						stack.push(element);
					}
				}
			}
		}
		// Push the remained operators from stack to queue
		while (stack.peek() != null)
			queue.enqueue(stack.pop());

	}

	// Evaluate the infix expressions
	public double evaluate() {
		while (!queue.isEmpty()) {
			// Get the token at the front of the queue
			Object first = queue.dequeue();
			// If the token is an operand, push it onto the stack
			if (first instanceof Number)
				stack.push(Double.valueOf(((Number) first).doubleValue()));
			// If the token is an operator, pop the appropriate number of operands from the
			// stack
			// Perform the operation on the popped operands, and push the resulting value
			// onto the stack
			else if (first.equals("+")) {
				Double operation = (Double) (stack.pop()) + (Double) (stack.pop());
				stack.push(operation);
			} else if (first.equals("-")) {
				Double temp = (Double) (stack.pop());
				Double operation = (Double) (stack.pop()) - temp;
				stack.push(operation);
			} else if (first.equals("*")) {
				Double operation = ((Double) (stack.pop()) * (Double) (stack.pop()));
				stack.push(operation);
			} else if (first.equals("/")) {
				Double temp = (Double) (stack.pop());
				if (temp == 0)
					throw new ArithmeticException("Cannot devide by zero");
				Double operation = (Double) (stack.pop()) / temp;
				stack.push(operation);
			} else if (first.equals("%")) {
				Double temp = (Double) (stack.pop());
				if (temp == 0)
					throw new ArithmeticException("Cannot define modulo 0");
				Double operation = (Double) (stack.pop()) % temp;
				stack.push(operation);
			} else if (first.equals("^")) {
				Double temp = (Double) (stack.pop());
				Double operation = Math.pow((Double) (stack.pop()), temp);
				stack.push(operation);
			} else if (first.equals("=")) {
				if (((Double) (stack.pop())).equals((Double) (stack.pop())))
					stack.push((Double) 1.0);
				else {
					stack.push((Double) 0.0);
				}
			} else if (first.equals("!")) {
				Double temp = (Double) (stack.pop());
				if (temp == 1.0)
					stack.push((Double) 0.0);
				else if (temp == 0.0) {
					stack.push((Double) 1.0);
				} else {
					throw new InvalidParameterException();
				}
			} else if (first.equals("<")) {
				if ((Double) (stack.pop()) > (Double) (stack.pop()))
					stack.push(1.0);
				else {
					stack.push(0.0);
				}
			} else if (first.equals(">")) {
				if ((Double) (stack.pop()) < (Double) (stack.pop()))
					stack.push(1.0);
				else {
					stack.push(0.0);
				}
			} else if (first.equals("&")) {
				if (toBoolean((Double) stack.pop()) && toBoolean((Double) stack.pop()))
					stack.push(1.0);
				else {
					stack.push(0.0);
				}
			} else if (first.equals("|")) {
				if (toBoolean((Double) stack.pop()) | toBoolean((Double) stack.pop()))
					stack.push(1.0);
				else {
					stack.push(0.0);
				}
			} else if (first.equals("sin")) {
				stack.push(Math.sin(Math.toRadians((Double) stack.pop())));
			} else if (first.equals("cos")) {
				stack.push(Math.cos(Math.toRadians((Double) stack.pop())));
			} else if (first.equals("tan")) {

				if (!(((Double) stack.peek() % 180) == 0) && ((Double) stack.peek() % 90) == 0)
					throw new ArithmeticException("Invalid tangent value");
				stack.push((Double) (Math.tan(Math.toRadians((Double) stack.pop()))));
			}
		}

		return (double) stack.peek();
	}

	public static boolean toBoolean(Double a) {
		if (a.equals(0.0))
			return false;
		if (a.equals(1.0))
			return true;
		throw new InvalidParameterException();
	}

	// Method recognize operators
	public static boolean isOperator(String s) {
		String[] operator = { "(", ")", "+", "-", "*", "/", "^", "&", "|", "%", "=", "<", ">", "!" };
		for (String a : operator) {
			if ((s.equals(a)))
				return true;
		}
		return false;
	}

	public static void main(String[] args) throws FileNotFoundException {
		// Pass in input and output file names
		String in = args[0];
		String out = args[1];

		File input = new File(in);
		PrintWriter output = new PrintWriter(out);

		// Create a Scanner to read the input file
		Scanner s = new Scanner(input);

		// For each line of the file, read the infix expression, evaluate it and print
		// it to the terminal file
		while (s.hasNextLine()) {

			String nextLine = s.nextLine();
			if (nextLine != null) {

				// Split the line into an array of Strings
				String[] array = nextLine.split("");
				// Convert the characters in the String array into Object array which contains
				// operands and operators
				URArrayList<Object> charList = new URArrayList<Object>();

				for (int i = 0; i < array.length; i++) {
					// Categorize a string into its type if not a white space
					if (!array[i].equals(" ")) {
						boolean isTrig = (array[i].equals("s") || array[i].equals("c") || array[i].equals("t"));
						// If operator, add to the array as String
						if (isOperator(array[i])) {
							charList.add(array[i]);
						} // If trig function, add to the array as String
						else if (isTrig) {
							charList.add(array[i] + array[i + 1] + array[i + 2]);
							i += 2;
						} // If number, add the the array as Double
						else {
							String doubleS = "";
							while (i < array.length && !isTrig && !isOperator(array[i]) && !array[i].equals(" ")) {
								doubleS += array[i];
								i++;
							}
							i--;
							charList.add(Double.parseDouble(doubleS));
						}

					}
				}
				Object[] infixArray = charList.toArray();

				// Create a calculator, read the infix from the array
				Calculator calculator = new Calculator();
				// Convert to postfix
				calculator.ShuntingYard(infixArray);
				// Evaluate the expression, print the result to the terminal file and the
				// console window
				System.out.printf("%.2f\n", calculator.evaluate());
				output.printf("%.2f\n", calculator.evaluate());
			}
		}
		output.close();
		s.close();
	}

}
