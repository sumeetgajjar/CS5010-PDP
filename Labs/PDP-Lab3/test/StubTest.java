import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    for (int i = 0; i < firstNames.size(); i++) {
      String expectedStudentName = String.format("%s %s", firstNames.get(i), lastNames.get(i));
      Assert.assertEquals(expectedStudentName, actualStudentNames.get(i));
    }
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
          , "0.920833333"
          , "0.8"
          , "0.656410256"
          , "0.218181818"
          , "70.8548951"
          , "C-"
          , "Clark"
          , "Freifeld"
          , "1"
          , "0.888888889"
          , "0.9"
          , "0.987012987"
          , "92.53679654"
          , "A-"
          , "Aniruddha"
          , "Tapas"
          , "0.891666667"
          , "0.566666667"
          , "0.711111111"
          , "0.566233766"
          , "68.94011544"
          , "D+"
          , "Aditya"
          , "Sathyanarayan"
          , "0.783333333"
          , "0.8"
          , "0.333333333"
          , "0"
          , "53"
          , "F"
          , "Ritika"
          , "Nair"
          , "1"
          , "0.911111111"
          , "0.955555556"
          , "0.92987013"
          , "94.85425685"
          , "A"
          , "Rohan"
          , "Chitnis"
          , "0.933333333"
          , "1"
          , "0.977777778"
          , "0.745454545"
          , "95.23232323"
          , "A"
  };

}