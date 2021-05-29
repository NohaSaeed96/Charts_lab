import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.style.Styler;


public class TitanicPassenger {
    private String name;
    private String pclass;
    private String survived;
    private String sex;
    private float age;

    public float getAge() {
        return age;
    }

    public String getSex() {
        return sex;
    }

    public String getPclass() {
        return pclass;
    }

    public String getName() {
        return name;
    }

    public String getSurvived() {
        return survived;
    }

    public List<TitanicPassenger> getPassengersFromJsonFile() throws IOException {
        List<TitanicPassenger> allPassengers = new ArrayList<TitanicPassenger>();
        ObjectMapper objectMapper = new ObjectMapper ();
        objectMapper.configure (DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        InputStream input = new FileInputStream("C:\\Users\\nohas\\IdeaProjects\\xchart_lab\\src\\main\\resources\\titanic_csv.json");
//Read JSON file
        allPassengers = objectMapper.readValue (input, new TypeReference<List<TitanicPassenger>>() { });
        return allPassengers;
    }
    public void graphPassengerAges(List<TitanicPassenger> passengerList) {
//filter to get an array of passenger ages
        List<Float> pAges = passengerList.stream ().map (TitanicPassenger::getAge).limit (8).collect (Collectors.toList ());
        List<String> pNames = passengerList.stream ().map (TitanicPassenger::getName).limit (8).collect (Collectors.toList ());
        //Using XCart to graph the Ages 1.Create Chart
        CategoryChart chart = new CategoryChartBuilder ().width (1024).height (768).title ("Age Histogram").xAxisTitle ("Names").yAxisTitle
                ("Age").build ();
// 2.Customize Chart
        chart.getStyler ().setLegendPosition (Styler.LegendPosition.InsideNW);
        chart.getStyler ().setHasAnnotations (true);
        chart.getStyler ().setStacked (true);
// 3.Series
        chart.addSeries ("Passenger's Ages", pNames, pAges);
// 4.Show it
        new SwingWrapper (chart).displayChart ();
    }
    public void graphPassengerClass(List<TitanicPassenger> passengerList) {
//filter to get a map of passenger class and total number of passengers in each class
        Map<String, Long> result =
                passengerList.stream ().collect (
                        Collectors.groupingBy (TitanicPassenger::getPclass, Collectors.counting () ) );
        // Create Chart
        PieChart chart = new PieChartBuilder ().width (800).height (600).title (getClass ().getSimpleName ()).build ();
// Customize Chart
        Color[] sliceColors = new Color[]{new Color (180, 68, 50), new Color (130, 105, 120), new Color (80, 143, 160)};
        chart.getStyler ().setSeriesColors (sliceColors);
// Series
        chart.addSeries ("First Class", result.get ("1"));
        chart.addSeries ("Second Class", result.get ("2"));
        chart.addSeries ("Third Class", result.get ("3"));
// Show it
        new SwingWrapper (chart).displayChart ();

    }
    public void graphPassengerSurvived(List<TitanicPassenger> passengerList) {
//filter to get a map of passenger survived or not survived
        Map <String, Long> result =
                passengerList.stream ().collect(Collectors.groupingBy(TitanicPassenger::getSurvived,Collectors.counting()));
        // Create Chart
        PieChart chart = new PieChartBuilder ().width (800).height (600).title (getClass ().getSimpleName ()).build ();
// Customize Chart
        Color[] sliceColors = new Color[]{new Color (180, 68, 50), new Color (130, 105, 120), new Color (80, 143, 160)};
        chart.getStyler ().setSeriesColors (sliceColors);
// Series
        chart.addSeries ("Not survived", result.get ("0"));
        chart.addSeries ("survived", result.get ("1"));

// Show it
        new SwingWrapper (chart).displayChart ();

    }
    public void graphPassengerSurvivedbygender(List<TitanicPassenger> passengerList) {
//filter to get a map of female and male passengers survived
        Map <String, Long> result =
                passengerList.stream ().filter(s->s.getSurvived().equals("1")).collect(Collectors.groupingBy(TitanicPassenger::getSex,Collectors.counting()));
        // Create Chart
        PieChart chart = new PieChartBuilder ().width (800).height (600).title (getClass ().getSimpleName ()).build ();
// Customize Chart
        Color[] sliceColors = new Color[]{new Color (180, 68, 50), new Color (130, 105, 120), new Color (80, 143, 160)};
        chart.getStyler ().setSeriesColors (sliceColors);
// Series
        chart.addSeries ("female survived", result.get ("female"));
        chart.addSeries ("male survived", result.get ("male"));

// Show it
        new SwingWrapper (chart).displayChart ();

    }
}
