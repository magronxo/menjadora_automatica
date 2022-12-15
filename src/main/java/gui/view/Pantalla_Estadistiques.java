package gui.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FlowLayout;
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
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import server.data.Dades;

/**
 * Pantalla Estadístiques que crea i mostra les Gràfiques
 * @author Oriol Coll Salvia
 */

public class Pantalla_Estadistiques extends JFrame{
    
    private int width = 1300;
    private int height = 800;
    private JPanel inferior = new JPanel();
    private JPanel superior = new JPanel();
    
    private double[] gramsRaccio,limitDiari,gramsAcumulatsAvui,sensorPlat,sensorNivell,valorAlertaDiposit,valorDipositBuit;
    
    private Dades dades;
    private boolean dreta;
    private String nomGrafica = "";
    private int resultats; //Mostrem 72 hores simulades a la gràfica
    private double dies = 3;
    
    /**
     * Construeix la Pantalla Estadístiques passant-li les dades
     * Divideix el JFrame en dos panells per a les Gràfiques
     * @param dades instància de la Classe Dades
     */
    public Pantalla_Estadistiques(Dades dades){
        this.dades=dades;
        this.setSize(width, height);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.resultats = dades.getResultats();
        this.dies = resultats / 24;

        inferior.setSize(450, 750);
        inferior.setVisible(true);
    
        superior.setSize(300, 750);
        superior.setVisible(true);

        JSplitPane splitPane = new JSplitPane();
        splitPane.setSize(width, height);
        splitPane.setDividerSize(0);
        splitPane.setDividerLocation(350);
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitPane.setLeftComponent(superior);
        splitPane.setRightComponent(inferior);

        this.add(splitPane);
    }
    
    /**
     * Assigna les Gràfiques a cada panell.
     * Les esborra i les crea novament per a pintar-les actualitzades.
     */
    private void initUI() {
        inferior.removeAll();
        inferior.revalidate();
        
        superior.removeAll();
        superior.revalidate();

        //MENJADORA
        XYDataset datasetMenjadora = createDatasetMenjadora();
        JFreeChart chartMenjadora = createChart(datasetMenjadora);      
        nomGrafica(false);
        
        LegendTitle legend = chartMenjadora.getLegend();
        legend.setPosition(RectangleEdge.RIGHT);
                       
        ChartPanel chartPanelMenjadora = new ChartPanel(chartMenjadora);
        chartPanelMenjadora.setBorder(BorderFactory.createEmptyBorder(0, 0, 400, 0));
       
        //DIPOSIT
        XYDataset datasetDiposit = createDatasetDiposit();
        JFreeChart chartDiposit = createChart(datasetDiposit);
        nomGrafica(true);
        
        LegendTitle legend2 = chartDiposit.getLegend();
        legend2.setPosition(RectangleEdge.RIGHT);

        ChartPanel chartPanelDiposit = new ChartPanel(chartDiposit);
        chartPanelDiposit.setBorder(BorderFactory.createEmptyBorder(0, 0, 400, 0));
 
        //JPANEL
        if(dreta){
            setTitle("Menjadora Dreta. Últims "+dies+ " dies");
        }else if(!dreta){
            setTitle("Menjadora Esquerra. Últims "+dies+ " dies");
        }else{
            System.out.println("Menjadora: parametres incorrectes");  
        }
        setLocationRelativeTo(null);
        setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
        
        inferior.setLayout(new FlowLayout());
        inferior.add(chartPanelMenjadora);
        inferior.repaint();
        
        superior.setLayout(new FlowLayout());
        superior.add(chartPanelDiposit);
        superior.repaint();
    }

    /**
     * Crida la funció llegeixDades() de la Classe Dades per obtenir les dades de la Menjadora.
     * @param dreta booleà per escollir la Menjadora dreta o esquerra
     */
    public void creaGrafica(boolean dreta){
        this.dreta=dreta;
        if(dreta){
            gramsRaccio = dades.llegeixDades(true,"gramsRaccio");
            limitDiari = dades.llegeixDades(true,"limitDiari");
            gramsAcumulatsAvui = dades.llegeixDades(true,"gramsAcumulatsAvui");
            sensorPlat = dades.llegeixDades(true,"sensorPlat");

            sensorNivell = dades.llegeixDades(true,"sensorNivell");
            valorAlertaDiposit = dades.llegeixDades(true,"valorAlertaDiposit");
            valorDipositBuit = dades.llegeixDades(true,"valorDipositBuit");
        }else if(!dreta){
            gramsRaccio = dades.llegeixDades(false,"gramsRaccio");
            limitDiari = dades.llegeixDades(false,"limitDiari");
            gramsAcumulatsAvui = dades.llegeixDades(false,"gramsAcumulatsAvui");
            sensorPlat = dades.llegeixDades(false,"sensorPlat");

            sensorNivell = dades.llegeixDades(false,"sensorNivell");
            valorAlertaDiposit = dades.llegeixDades(false,"valorAlertaDiposit");
            valorDipositBuit = dades.llegeixDades(false,"valorDipositBuit");
        }else{
            System.out.println("Menjadora: parametres incorrectes");   
        }
        initUI();
    }    
    
    /**
     * Crea el DataSet de la Menjadora que són les coordenades per a la gràfica 
     * @return 
     */
    private XYDataset createDatasetMenjadora() {
        var gramsRaccio1 = new XYSeries("Grams/Raccio");
        var limitDiari1 = new XYSeries("Limit Diari");
        var gramsAcumulatsAvui1 = new XYSeries("Grams Acumulats Avui");
        var sensorPlat1 = new XYSeries("Sensor Plat");
                
        try{
            for (int i=0; i<resultats;i++){
                gramsRaccio1.add(i,this.gramsRaccio[i]);
            }
            for (int i=0; i<resultats;i++){
                limitDiari1.add(i,this.limitDiari[i]);               
            }
            for (int i=0; i<resultats;i++){
                gramsAcumulatsAvui1.add(i,this.gramsAcumulatsAvui[i]);              
            }
            for (int i=0; i<resultats;i++){
                sensorPlat1.add(i,this.sensorPlat[i]);    
            }

            var dataset = new XYSeriesCollection();
            dataset.addSeries(gramsRaccio1);
            dataset.addSeries(limitDiari1);
            dataset.addSeries(gramsAcumulatsAvui1);
            dataset.addSeries(sensorPlat1);
            return dataset;
        }catch(Exception e){
             System.out.println("Menjadora Chart Array Error");     
        }
        return null;
    }
    
    /**
     * Crea el DataSet del Diposit que són les coordenades per a la gràfica
     * @return 
     */
    private XYDataset createDatasetDiposit() {
           
        var sensorNivell = new XYSeries("Sensor Nivell");
        var valorAlertaDiposit = new XYSeries("Valor Alerta Diposit");
        var valorDipositBuit = new XYSeries("Valor Diposit Buit");
        
        try{     
            for (int i=0; i<resultats;i++){
                sensorNivell.add(i,this.sensorNivell[i]);               
            }
            for (int i=0; i<resultats;i++){
                valorAlertaDiposit.add(i,this.valorAlertaDiposit[i]);               
            }
            for (int i=0; i<resultats;i++){
                valorDipositBuit.add(i,this.valorDipositBuit[i]);               
            }
      
        var dataset1 = new XYSeriesCollection();
        dataset1.addSeries(sensorNivell);
        dataset1.addSeries(valorAlertaDiposit);
        dataset1.addSeries(valorDipositBuit);
        return dataset1;
        
        }catch(Exception e){
             System.out.println("Diposit Chart Array Error");     
        }
        return null;
    }
    
    /**
     * Funció per triar el nom de la gràfica.
     * Es posa a false quan ha escrit el nom "menjadora"
     * @param menjadora booleà que defineix Menjadora o Diposit
     */
    private void nomGrafica(boolean menjadora){
        if(menjadora){
            this.nomGrafica = "Menjadora";
        }else{
            this.nomGrafica = "Diposit";
        }
    }
    
    /**
     * Crea la gràfica a partir del DataSet.
     * Li afegeix el nom, la llegenda, els colors, etc.
     * @param dataset coordenades del punts de la gràfica
     * @return gràfica (chart) renderitzada
     */
    private JFreeChart createChart(XYDataset dataset) {
        JFreeChart chart= null;
        chart = ChartFactory.createXYLineChart(
                nomGrafica,
                "Temps (h)",
                "Grams de pinso",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        
        chart.getXYPlot().setDataset(chart.getXYPlot().getDataset());////////////
        XYPlot plot = chart.getXYPlot();

        var renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.red);
        renderer.setSeriesPaint(1, Color.blue);
        renderer.setSeriesPaint(2, Color.green);
        renderer.setSeriesPaint(3, Color.MAGENTA);
        renderer.setSeriesStroke(0, new BasicStroke(1.0f));
        renderer.setSeriesStroke(1, new BasicStroke(1.2f));
        renderer.setSeriesStroke(2, new BasicStroke(1.5f));

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);

        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

        chart.getLegend().setFrame(BlockBorder.NONE);

        return chart;
    }
}