import static org.junit.Assert.*;

import org.junit.Test;


public class ModeTest {

	@Test
	public void test() {
		Statistics test = new Statistics();
		TestData data = new TestData();
		String result = Double.toString(test.calculateMode(data.dataValuesEvenNumber));
		assertEquals("14.4", result);
	}

}
