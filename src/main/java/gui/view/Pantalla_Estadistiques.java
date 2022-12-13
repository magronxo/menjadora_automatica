/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.WindowConstants;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import server.data.Dades;

/**
 *
 * @author oriol
 */

public class Pantalla_Estadistiques extends JFrame{
    
    private int width = 1300;
    private int height = 700;
    
    JPanel glass = new JPanel();
    JPanel controls = new JPanel();
    
    Dades dades;
    boolean dreta;
    
    public Pantalla_Estadistiques(Dades dades){
        this.setSize(width, height);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.dades=dades;

        
        glass.setSize(450, 750);
        //glass.setBackground(Color.BLUE);
        glass.setVisible(true);

        
        controls.setSize(150, 750);
        //controls.setBackground(Color.RED);
        controls.setVisible(true);

        JSplitPane splitPane = new JSplitPane();
        splitPane.setSize(width, height);
        splitPane.setDividerSize(0);
        splitPane.setDividerLocation(350);
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitPane.setLeftComponent(controls);
        splitPane.setRightComponent(glass);

        this.add(splitPane);
        initUI();
        
    }
    
        private void initUI() {

        XYDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        
        if(dades.isDreta()){
            //dades.llegeix menjadora i diposit Dreta
        }else if(!dades.isDreta()){
            //dades.llegeix menjadora i diposit Esquerra
        }else{
            
        }

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 400, 0));
        //chartPanel.setBackground(Color.white);
        //add(chartPanel);
        //pack();
        ChartPanel chartPanel2 = new ChartPanel(chart);
        chartPanel2.setBorder(BorderFactory.createEmptyBorder(0, 0, 400, 0));
        //chartPanel2.setBackground(Color.white);
        //pack();
        setTitle("Menjadora + menjadoraString");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
        
        glass.add(chartPanel);
        controls.add(chartPanel2);
        
    }
        
    /*Funcions que es puguin cridar des de la classe menjadora
        */    
        
        
    private XYDataset createDataset() {

        var series = new XYSeries("2016");
        var series2 = new XYSeries("2018");
        var series3 = new XYSeries("2017");
        var series4 = new XYSeries("2019");
        series.add(18, 567);
        series.add(20, 612);
        series.add(25, 800);
        series.add(30, 980);
        series.add(40, 1410);
        series.add(50, 2350);

        series2.add(50, 567);
        series2.add(40, 612);
        series2.add(30, 800);
        series2.add(25, 980);
        series2.add(20, 1410);
        series2.add(18, 2350);

        series3.add(18, 567);
        series3.add(20, 700);
        series3.add(25, 800);
        series3.add(30, 1000);
        series3.add(40, 1410);
        series3.add(50, 2350);

        series4.add(168, 700);
        series4.add(140, 750);
        series4.add(120, 800);
        series4.add(75, 980);
        series4.add(50, 1000);
        series4.add(18, 1350);

        var dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        dataset.addSeries(series2);
        dataset.addSeries(series3);
        dataset.addSeries(series4);
        return dataset;
    }

    private JFreeChart createChart(XYDataset dataset) {

        JFreeChart chart = ChartFactory.createXYLineChart(
                //"Menjadora dreta " + periode (setmanal o mensual)
                "Average salary per age",
                "Temps (h)",
                "Grams de pinso",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();

        var renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.red);
        renderer.setSeriesPaint(1, Color.blue);
        renderer.setSeriesPaint(2, Color.green);
        renderer.setSeriesPaint(3, Color.MAGENTA);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));
        renderer.setSeriesStroke(1, new BasicStroke(3.0f));
        renderer.setSeriesStroke(2, new BasicStroke(5.0f));

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);

        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

        chart.getLegend().setFrame(BlockBorder.NONE);

        chart.setTitle(new TextTitle("Average Salary per Age",
                new Font("Serif", java.awt.Font.BOLD, 18)
        )
        );

        return chart;
    }
    
}
