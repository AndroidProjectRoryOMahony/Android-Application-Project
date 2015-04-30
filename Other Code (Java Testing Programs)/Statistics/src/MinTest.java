import static org.junit.Assert.*;

import org.junit.Test;


public class MinTest {

	@Test
	public void test() {
		Statistics test = new Statistics();
		TestData data = new TestData();
		String result = Double.toString(test.calculateMinValue(data.dataValuesEvenNumber));
		assertEquals("1.1", result);
	}

}
