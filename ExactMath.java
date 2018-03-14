package myMath;

import java.math.BigInteger;
import java.util.ArrayList;

import myMath.Fraction.denominatorZeroException;

public class ExactMath {
	
	/**
	 * Computes the square root of a double by transforming it into a fraction and then computing the square root
	 * @param input double value
	 * @return A cube root object 
	 */
	public static SquareRoot exactSquareRoot(double input){
		Fraction a;
		try {
			a = Fraction.rationalize(input);
			return a.exactSquareRoot();
		} catch (denominatorZeroException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * Computes the cube root of a double by transforming it into a fraction and then computing the cube root
	 * @param input double value
	 * @return A cube root object 
	 */
	public static CubeRoot exactCubeRoot(double input) {
		Fraction a;
		try {
			a = Fraction.rationalize(input);
			return a.exactCubeRoot();
		} catch (denominatorZeroException e) {
			e.printStackTrace();
		}
		return null;
	}	
	/**
	 * Used when input already is a long computes the exact square root directly without fraction conversion 
	 * @param input
	 * @return square root of input
	 */
	public static SquareRoot exactSquareRoot(long input) {
		long out = 1, in = (long)input;
		long upper = (in>4)? in/2: in;
		for(int i=1; i<upper; i++) 
			if(in%(i*i) == 0) {
				in /= (i*i);
				out *= i;
				if(in==1)
					break;
			}
		return new SquareRoot(in, out);
	}
	/**
	 * Used when input already is a long computes the exact cube root directly without fraction conversion 
	 * @param input
	 * @return cube root of input
	 */
	public static CubeRoot exactCubeRoot(long input) {
		long out = 1, in = (long)input;
		long upper = in/2;
		for(int i=1; i<upper; i++) 
			if(in%(i*i*i) == 0) {
				in /= (i*i*i);
				out *= i;
				if(in==1)
					break;
			}
		return new CubeRoot(in, out);
	}	
	///Statistical methods
	/**
	 * Computes the factorial of input
	 * @param input 
	 * @return factorial of input
	 */
	public static BigInteger factorial(long input) {
		BigInteger temp = BigInteger.valueOf(input);
		for(long i=1; i<input; i++) 
			temp = temp.multiply(BigInteger.valueOf(i));
		return temp;
	}
	/**
	 * Permutation function uses ArrayLists to stop the program 
	 * from having to compute massive values far faster than normal factorial calculations
	 * @return
	 */
	public static BigInteger nPr(long n, long r) {
		ArrayList<Long> nFactorial = new ArrayList<>();
		ArrayList<Long> denominator = new ArrayList<>();
		BigInteger temp = BigInteger.ONE;
		long upper = n-r;
		for(long i=1; i<=n; i++) { 
			nFactorial.add(i);
			if(i<=upper) 
				denominator.add(i);
		}
		nFactorial.removeAll(denominator);
		for(long i: nFactorial) 
			temp = temp.multiply(BigInteger.valueOf(i));
		return temp;
	}
	/**
	 * Combination function uses ArrayLists to stop the program 
	 * from having to compute massive values far faster than normal factorial calculations
	 * @return
	 */
	public static BigInteger nCr(long n, long r) {
		ArrayList<Long> nFactorial = new ArrayList<>();
		ArrayList<Long> denominator = new ArrayList<>();
		ArrayList<Long> rFactorial = new ArrayList<>();
		BigInteger temp = BigInteger.ONE;
		long upper = n-r;
		for(long i=1; i<=n; i++) { 
			nFactorial.add(i);
			if(i<=upper) 
				denominator.add(i);
			if(i<=r)
				rFactorial.add(i);
		}
		//Removes whichever set is bigger
		if(denominator.size()>rFactorial.size()) {
			nFactorial.removeAll(denominator);
			for(long i: nFactorial) 
				temp = temp.multiply(BigInteger.valueOf(i));
			for(long i: rFactorial)
				temp = temp.divide(BigInteger.valueOf(i));
		}
		else {
			nFactorial.removeAll(rFactorial);
			for(long i: nFactorial) 
				temp = temp.multiply(BigInteger.valueOf(i));
			for(long i: denominator)
				temp = temp.divide(BigInteger.valueOf(i));
		}
		return temp;
	}
}
class SquareRoot extends MyNumber{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	double value;
	Fraction outsidePart;
	Fraction insidePart; 
	
	SquareRoot(long in, long out) {
		insidePart = new Fraction(in);
		outsidePart = new Fraction(out);
	}
	/**
	 * Constructor for use with fractional square roots
	 * @param in
	 * @param out
	 */
	public SquareRoot(Fraction in, Fraction out) throws denominatorZeroException {
		insidePart = in;
		outsidePart = out;
		
		long newInsideDenominator, newInsideNumerator, newOutsideDenominator;
		//Reduces the Fraction so the square never has a denominator
		if(insidePart.getDenominator() > 1) {
			long temp = insidePart.getDenominator();
			newInsideDenominator = 1;
			newInsideNumerator = insidePart.getNumerator() * temp;
			newOutsideDenominator = outsidePart.getDenominator() * temp;
			insidePart = new Fraction(newInsideNumerator, newInsideDenominator);
			outsidePart = new Fraction(outsidePart.getNumerator(), newOutsideDenominator);
		}
	}
	@Override
	public double getValue() {
		return value;
	}

	@Override
	public String toString() {
		if(insidePart.getValue() == 1)
			return outsidePart.toString();
		else if(outsidePart.getValue() == 1)
			return "root(" + insidePart + ")";
		else
			return "(" + outsidePart + ")*root(" + insidePart + ")";
	}

}
class CubeRoot extends MyNumber{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	double value;
	Fraction outsidePart, insidePart; 
	
	CubeRoot(long in, long out) {
		insidePart = new Fraction(in);
		outsidePart = new Fraction(out);
	}
	public CubeRoot(Fraction in, Fraction out) {
		insidePart = in;
		outsidePart = out;
	}
	@Override
	public double getValue() {
		return value;
	}

	@Override
	public String toString() {
		if(insidePart.getValue() == 1)
			return String.valueOf(outsidePart);
		else if(outsidePart.getValue() == 1)
			return "cubeRoot(" + insidePart + ")";
		else
			return "(" + outsidePart + ")*cubeRoot(" + insidePart + ")";
	}

}
