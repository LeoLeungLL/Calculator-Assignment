import static org.junit.Assert.*;


import org.junit.Test;


public class TestCalculator {

	@Test
	public void testIsInteger() {
		Calculator c = new Calculator();
		assertTrue(c.isInteger("5"));
		assertTrue(c.isInteger("-1"));
		assertTrue(c.isInteger("0"));
		assertFalse(c.isInteger("0.5"));
		assertFalse(c.isInteger("a"));
		assertFalse(c.isInteger("Z"));
		assertFalse(c.isInteger("test"));
		assertFalse(c.isInteger(""));
	}
	
	@Test
	public void testIsValidVariable() {
		Calculator c = new Calculator();
		assertTrue(c.isValidVariable("m"));
		assertTrue(c.isValidVariable("T"));
		assertFalse(c.isValidVariable("Test"));
		assertFalse(c.isValidVariable(""));
	}
	
	@Test
	public void testAddition()
	{
		Calculator c = new Calculator();
		assertEquals(c.Evaluate("add(4,3)"), 7, 0);
		
	}
	
	@Test
	public void testNestedAddition()
	{
		Calculator c = new Calculator();
		assertEquals(c.Evaluate("add(3,add(4,5))"), 12, 0);
		assertEquals(c.Evaluate("add(add(4,5),3)"), 12, 0);
	}
	@Test
	public void testSubtraction()
	{
		Calculator c = new Calculator();
		assertEquals(c.Evaluate("sub(5,3)"), 2, 0);
		assertEquals(c.Evaluate("sub(3,5)"),-2,0);
		
		
	}
	@Test
	public void testNestedSubtraction()
	{
		Calculator c = new Calculator();
		assertEquals(c.Evaluate("sub(3,sub(4,5))"), 4, 0);

		assertEquals(c.Evaluate("sub(sub(4,5),3)"), -4, 0);
	}
	@Test
	public void testMultiplication()
	{
		Calculator c = new Calculator();
		assertEquals(c.Evaluate("mult(5,3)"), 15, 0);
		assertEquals(c.Evaluate("mult(2,-5)"),-10,0);
		assertEquals(c.Evaluate("mult(-2,-3)"),6,0);
		
		
	}
	@Test
	public void testNestedMultiplication()
	{
		Calculator c = new Calculator();
		assertEquals(c.Evaluate("mult(3,mult(4,5))"), 60, 0);
		assertEquals(c.Evaluate("mult(mult(4,5),3)"), 60, 0);
	}
	@Test
	public void testDivision()
	{
		Calculator c = new Calculator();
		assertEquals(c.Evaluate("div(6,3)"), 2, 0);
		assertEquals(c.Evaluate("div(3,4)"),0.75,0);
		assertEquals(c.Evaluate("div(6,-2)"),-3,0);
		assertEquals(c.Evaluate("div(-8,-4)"),2,0);
		
		
	}
	@Test
	public void testNestedDivision()
	{
		Calculator c = new Calculator();
		assertEquals(c.Evaluate("div(6,div(4,2))"), 3, 0);
		assertEquals(c.Evaluate("div(div(8,2),2)"), 2, 0);
		
	}
	@Test
	public void testLet()
	{
		Calculator c = new Calculator();
		assertEquals(c.Evaluate("let(a,5,a)"),5,0);
		assertEquals(c.Evaluate("let(A,2,add(A,3))"),5,0);
		assertEquals(c.Evaluate("let(b,add(3,4),b)"),7,0);
		
	}
	@Test
	public void testCharCount()
	{
		Calculator c = new Calculator();
		assertEquals(c.charCount("a)))","\\)"), 3);
	}
	@Test
	public void providedTestCases()
	{
		Calculator c = new Calculator();
		assertEquals(c.Evaluate("add(1,2)"),3,0);
		assertEquals(c.Evaluate("add(1,mult(2,3))"),7,0);
		assertEquals(c.Evaluate("mult(add(2,2),div(9,3))"),12,0);
		assertEquals(c.Evaluate("let(a,5,add(a,a))"),10,0);
		assertEquals(c.Evaluate("let(a,5,let(b,mult(a,10),add(b,a)))"),55,0);
		assertEquals(c.Evaluate("let(a,let(b,10,add(b,b)),let(b,20,add(a,b)))"),40,0);
	}
}
