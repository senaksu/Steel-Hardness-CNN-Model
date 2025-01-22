package celikSertlikCnn;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import javax.swing.*;

public class agSec {

    public static void main(String[] args) throws Exception {
        // Ağ yapılandırmaları
        Ysa[] ysaAglar = {
            new Ysa(5, 0.9, 0.1, 0.01, 500),
            new Ysa(10, 0.8, 0.2, 0.005, 500),
            new Ysa(15, 0.7, 0.05, 0.001, 500),
            new Ysa(20, 0.6, 0.1, 0.01, 500),
            new Ysa(25, 0.9, 0.15, 0.005, 500),
            new Ysa(30, 0.5, 0.01, 0.001, 500),
            new Ysa(35, 0.8, 0.2, 0.01, 500),
            new Ysa(40, 0.7, 0.05, 0.005, 500),
            new Ysa(45, 0.9, 0.1, 0.01, 500),
            new Ysa(50, 0.6, 0.2, 0.001, 500)
        };

        // En düşük hata oranına sahip ağı bulmak için değişkenler
        double enDüşükHata = Double.MAX_VALUE;
        int enDüşükHataAğı = -1;

        // Grafik için seri oluştur
        XYSeriesCollection dataset = new XYSeriesCollection();

        // Ağı tek tek test et
        for (int i = 0; i < ysaAglar.length; i++) {
            Ysa ysa = ysaAglar[i];
            System.out.println("\nTesting Network " + (i + 1));

            // Eğitim ve test hatalarını her epoch için topla
            double[] hatalar = ysa.eniyiag();
            XYSeries egitimSerisi = new XYSeries("Ağ " + (i + 1) + " Eğitim");
            XYSeries testSerisi = new XYSeries("Ağ " + (i + 1) + " Test");

            // Hataları seriye ekle
            for (int epoch = 0; epoch < 500; epoch++) {
                egitimSerisi.add(epoch + 1, hatalar[0]);
                testSerisi.add(epoch + 1, hatalar[1]);
            }

            // Grafik veri setine serileri ekle
            dataset.addSeries(egitimSerisi);
            dataset.addSeries(testSerisi);

            // En düşük hata oranını bul
            if (hatalar[1] < enDüşükHata) {
                enDüşükHata = hatalar[1];
                enDüşükHataAğı = i + 1;  // Ağ numarasını kaydet
            }
        }

        // En düşük hata oranına sahip ağı ekrana yazdır
        System.out.println("\nEn düşük test hatasına sahip ağ: Ağ " + enDüşükHataAğı + " Hata: " + enDüşükHata);

        // Grafik oluştur
        JFreeChart chart = ChartFactory.createXYLineChart(
            "Eğitim ve Test Hataları Karşılaştırması",
            "Epoch",
            "Hata",
            dataset,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        );

        // Grafiği göster
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(chartPanel);
        frame.pack();
        frame.setVisible(true);
    }
}
