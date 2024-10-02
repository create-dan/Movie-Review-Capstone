package KarateRunner;

import com.intuit.karate.Runner;
import com.intuit.karate.junit5.Karate;

public class TestRunner {

    public static void main(String[] args) {
        Runner.path("src/test/java/ideas/movieReview/mr_data/features").parallel(2);
    }
}
