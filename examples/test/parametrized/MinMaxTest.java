package testing.tests.parametrized;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;

import testing.parametrized.MinMax;

class MinMaxTest {
    
    private final MinMax mm = new MinMax();

    // passage en paramétré + sélection de
    // la méthode génératrice
    @ParameterizedTest
    @Tag("Unit")
    @MethodSource("generator")
    //@ValueSource({{1, 3, 4}, 1, 4})
    void testMinMaxGenerator(int[] nums, int expectedMin, int expectedMax) {
        mm.find(nums);
        assertEquals(expectedMin, mm.getMin());
        assertEquals(expectedMax, mm.getMax());
    }
    
    // méthode génératice: fourni 
    // un flux d'arguments en retour
    private static Stream<Arguments> generator(){
        // critères d'acceptations: entrées et sorties attendues
        Arguments ac1 = Arguments.of(new int[]{2, 100, 33, -44}, -44, 100 );
        Arguments ac2 = Arguments.of(new int[]{7, 22, -7, 88}, -7, 88 );
        return Stream.of(ac1, ac2);
    }
    
    //@ParameterizedTest
    // fichiers dans src/test/resources
    // src/test/resources doit être dans le classpath
    // voir pom.xml -> build -> resources
    @CsvFileSource(resources = "/nums.csv")
    void testMinMaxCsv(int expectedMin, int expectedMax, String... nums) {
        int[] realNums = new int [nums.length];
        for (int i=0; i<nums.length; i++) {
            realNums[i] = Integer.parseInt(nums[i]);
        }
        for (int i : realNums) {
            System.out.println(i);
        }
        mm.find(realNums);
        assertEquals(Integer.valueOf(expectedMin), mm.getMin());
        assertEquals(Integer.valueOf(expectedMax), mm.getMax());
    }

}
