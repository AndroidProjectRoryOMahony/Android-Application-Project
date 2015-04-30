import static org.junit.Assert.*;

import org.junit.Test;


public class MeanTest {

	@Test
	public void test() {
		Statistics test = new Statistics();
		TestData data = new TestData();
		String result = Double.toString(test.calculateMean(data.dataValuesEvenNumber));
		assertEquals("10.195", result);
		//assertEquals("11.195", result);
	}

}
