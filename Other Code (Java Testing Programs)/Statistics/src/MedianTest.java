import static org.junit.Assert.*;

import org.junit.Test;


public class MedianTest {

	@Test
	public void test() {
		Statistics test = new Statistics();
		TestData data = new TestData();
		String resultEven = Double.toString(test.calculateMedian(data.dataValuesEvenNumber));
		String resultOdd = Double.toString(test.calculateMedian(data.dataValuesOddNumber));
		assertEquals("10.5", resultEven);
		assertEquals("11.0", resultOdd);
	}

}
