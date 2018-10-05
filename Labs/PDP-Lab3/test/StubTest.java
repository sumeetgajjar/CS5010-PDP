import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import grades.Gradebook;
import grades.StudentRecord;

import static org.junit.Assert.assertEquals;


public class StubTest {

  private Gradebook records;
  private List<Double> weights;
  private List<Double> finalScores;
  private List<String> letterGrades;
  private List<String> firstNames, lastNames;
  private final int NumAssignments = 4;
  private List<String> letters;
  private List<Double> thresholds;

  @Before
  public void setup() {

    letters = Arrays.asList(new String[]{"F", "D-", "D", "D+", "C-", "C", "C+",
            "B-", "B", "B+", "A-", "A"});
    thresholds = Arrays.asList(new Double[]{60.0, 63.0, 66.0, 70.0, 73.0, 76.0,
            80.0, 83.0, 86.0, 90.0, 93.0, 100.0});
    records = new Gradebook(letters, thresholds);
    finalScores = new ArrayList<Double>();
    letterGrades = new ArrayList<String>();
    firstNames = new ArrayList<String>();
    lastNames = new ArrayList<String>();
    int i = 0;
    while (i < input.length) {
      String fName = input[i];
      String lName = input[i + 1];
      double[] points = new double[NumAssignments];
      for (int j = 0; j < NumAssignments; j++) {
        points[j] = 100 * Double.parseDouble(input[i + 2 + j]);
      }

      finalScores.add(Double.parseDouble(input[i + 2 + NumAssignments]));
      letterGrades.add(input[i + 2 + NumAssignments + 1]);
      firstNames.add(fName);
      lastNames.add(lName);

      i = i + 4 + NumAssignments;
      records.addStudent(new StudentRecord(fName, lName, points));
    }

    weights = new ArrayList<Double>();
    weights.add(20.0);
    weights.add(30.0);
    weights.add(40.0);
    weights.add(10.0);

  }

  @Test
  public void testIndividualGrades() {
    List<Double> finals = records.getFinalScores(weights);
    for (int i = 0; i < finalScores.size(); i++) {
      assertEquals(finalScores.get(i), finals.get(i), 0.001);
    }
  }

  @Test
  public void testIndividualStudentNames() {
    List<String> actualStudentNames = records.getStudentNames();

    IntStream.range(0, firstNames.size()).forEach(i -> {
      String expectedStudentName = String.format("%s %s", firstNames.get(i), lastNames.get(i));
      Assert.assertEquals(expectedStudentName, actualStudentNames.get(i));
    });
  }

  @Test
  public void testAverageScoreForStudentNotFoundInGradeBook() {
    Double actualAvg = records.averageScoreForName("Student who is not present", weights);
    Assert.assertEquals(0D, actualAvg, 0D);
  }

  @Test
  public void testAverageScoreForGivenFirstName() {
    for (String fName : new String[]{"Ritika", "Amit", "Clark", "Fname3"}) {
      int count = 0;
      double sum = 0D;

      for (int i = 0; i < firstNames.size(); i++) {
        if (fName.equals(firstNames.get(i))) {
          sum += finalScores.get(i);
          count++;
        }
      }

      double expectedAvg = 0D;
      if (count != 0) {
        expectedAvg = sum / count;
      }

      Assert.assertEquals(expectedAvg,
              records.averageScoreForName(fName, weights), 0.001D);
    }
  }

  @Test
  public void testCountOfStudentsAboveAverage() {
    Double expectedAverage = finalScores.stream()
            .mapToDouble(score -> score).average().orElse(0D);

    long expectedStudentsAboveAvg = finalScores.stream()
            .filter(score -> score > expectedAverage).count();
    Assert.assertEquals(expectedStudentsAboveAvg, records.countAboveAverage(weights));
  }

  @Test
  public void testNumberOfStudentsWithGivenGrade() {
    Map<String, Integer> expectedGradeCount = letterGrades.stream()
            .collect(Collectors
                    .toMap(gradeLetter -> gradeLetter,
                            gradeLetter -> 1,
                            (currentValue, newValue) -> currentValue + newValue));

    for (String gradeLetter : letters) {
      int expectedCount = expectedGradeCount.getOrDefault(gradeLetter, 0);
      int actualCount = records.countLetterGrade(gradeLetter, weights);
      Assert.assertEquals(expectedCount, actualCount);
    }

    Assert.assertEquals(0, records.countLetterGrade("Random", weights));
  }

  @Test
  public void testEmptyGradeBook() {
    Gradebook gradebook = new Gradebook(letterGrades, thresholds);

    Assert.assertEquals(0, gradebook.getFinalScores(weights).size());
    Assert.assertEquals(0, gradebook.getStudentNames().size());
    Assert.assertEquals(0D, gradebook.averageScoreForName("Sumeet", weights), 0D);
    Assert.assertEquals(0, gradebook.countAboveAverage(weights));
    Assert.assertEquals(0, gradebook.countLetterGrade("A", weights));
  }

  // Data from the Excel file, to be used for testing
  String[] input = {"Amit"
          , "Shesh"
          , "0.920833333333333"
          , "0.8"
          , "0.656410256410256"
          , "0.218181818181818"
          , "70.8548951048951"
          , "C-"
          , "Clark"
          , "Freifeld"
          , "1"
          , "0.888888888888889"
          , "0.9"
          , "0.987012987012987"
          , "92.5367965367965"
          , "A-"
          , "Aniruddha"
          , "Tapas"
          , "0.891666666666667"
          , "0.566666666666667"
          , "0.711111111111111"
          , "0.566233766233766"
          , "68.9401154401155"
          , "D+"
          , "Aditya"
          , "Sathyanarayan"
          , "0.783333333333333"
          , "0.8"
          , "0.333333333333333"
          , "0"
          , "53"
          , "F"
          , "Ritika"
          , "Nair"
          , "1"
          , "0.911111111111111"
          , "0.955555555555556"
          , "0.92987012987013"
          , "94.8542568542569"
          , "A"
          , "Rohan"
          , "Chitnis"
          , "0.933333333333333"
          , "1"
          , "0.977777777777778"
          , "0.745454545454546"
          , "95.2323232323232"
          , "A"
          , "Amit"
          , "Freifeld"
          , "1"
          , "1"
          , "0.9"
          , "1"
          , "96"
          , "A"
          , "Clark"
          , "Shesh"
          , "1"
          , "1"
          , "0.9"
          , "1"
          , "96"
          , "A"
          , "Bob"
          , "Builder"
          , "0"
          , "0"
          , "0"
          , "0"
          , "0"
          , "F"
          , "Fname1"
          , "Lname1"
          , "1"
          , "0"
          , "0"
          , "0"
          , "20"
          , "F"
          , "Fname2"
          , "Lname2"
          , "0"
          , "1"
          , "0"
          , "0"
          , "30"
          , "F"
          , "Fname3"
          , "Lname3"
          , "0"
          , "0"
          , "1"
          , "0"
          , "40"
          , "F"
          , "Fname4"
          , "Lname4"
          , "0"
          , "0"
          , "0"
          , "1"
          , "10"
          , "F"
          , "Fname5"
          , "Lname5"
          , "0.2"
          , "1"
          , "1"
          , "1"
          , "84"
          , "B"
          , "Fname6"
          , "Lname6"
          , "1"
          , "0.4"
          , "1"
          , "1"
          , "82"
          , "B-"
          , "Fname7"
          , "Lname7"
          , "1"
          , "1"
          , "0.4"
          , "1"
          , "76"
          , "C+"
          , "Fname8"
          , "Lname8"
          , "1"
          , "0.7"
          , "1"
          , "0.1"
          , "82"
          , "B-"
          , "Fname9"
          , "Lname9"
          , "0.34"
          , "1"
          , "0.4"
          , "1"
          , "62.8"
          , "D-"
          , "Fname10"
          , "Lname10"
          , "1"
          , "0.34"
          , "1"
          , "1"
          , "80.2"
          , "B-"
          , "Clark"
          , "Nair"
          , "1"
          , "1"
          , "0.9"
          , "1"
          , "96"
          , "A"
          , "Clark"
          , "Sharma"
          , "1"
          , "1"
          , "0.9"
          , "0.2756"
          , "88.756"
          , "B+"
          , "Fname3"
          , "Lname11"
          , "0"
          , "0.4"
          , "1"
          , "0"
          , "52"
          , "F"
          , "Fname3"
          , "Lname12"
          , "0.2345"
          , "0"
          , "1"
          , "0.2567"
          , "47.257"
          , "F"
          , "Fname3"
          , "Lname13"
          , "0"
          , "0.67"
          , "1"
          , "0.234"
          , "62.44"
          , "D-"
          , "Fname3"
          , "Lname14"
          , "0.4564"
          , "0.97"
          , "1"
          , "0.56"
          , "83.828"
          , "B"
  };
}