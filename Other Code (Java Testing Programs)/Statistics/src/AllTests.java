import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
//The following websites were used to aid the development of the project's code. Parts of the project code have been adapted from these websites:
//YouTube. (N.D) JUnit Testing in Eclipse Available from https://www.youtube.com/watch?v=v2F49zLLj-8 [Accessed 16/04/2015] For creating Junit tests

@RunWith(Suite.class)
@SuiteClasses({ MaxTest.class, MeanTest.class, MedianTest.class, MinTest.class,
		ModeTest.class })
public class AllTests {

}
