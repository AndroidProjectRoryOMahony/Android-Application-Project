import static org.junit.Assert.*;

import org.junit.Test;


public class MaxTest {

	@Test
	public void test() {
		Statistics test = new Statistics();
		TestData data = new TestData();
		String result = Double.toString(test.calculateMaxValue(data.dataValuesEvenNumber));
		assertEquals("20.0", result);
	}

}
