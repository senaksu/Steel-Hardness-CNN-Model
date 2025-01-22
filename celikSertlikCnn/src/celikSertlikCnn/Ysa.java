package celikSertlikCnn;

import java.util.ArrayList;
import java.util.Collections;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import org.neuroph.core.data.DataSetRow;
import java.util.Scanner;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.BackPropagation;
import org.neuroph.nnet.learning.MomentumBackpropagation;
import org.neuroph.util.TransferFunctionType;

public class Ysa {

	
    public static final File egitimDosya = new File("C:\\java projeler\\celikSertlikCnn\\egitim.txt");
    public static final File testDosya = new File("C:\\java projeler\\celikSertlikCnn\\test.txt");

    public DataSet egitimVeriSeti;
    public DataSet testVeriSeti;
    private double[] maksimumlar;
    private double[] minimumlar;
    private int araKatmanNoronSayisi;
    public double momentum;
    private double ogrenmeKatsayisi;
    private double error;
    private int epoch;
  
    public List<Double> egitimHatalari = new ArrayList<>();
    public List<Double> testHatalari = new ArrayList<>();

    public Ysa(int araKatmanNoronSayisi, double momentum, double ogrenmeKatsayisi, double error, int epoch) throws FileNotFoundException {
        this(araKatmanNoronSayisi, ogrenmeKatsayisi, error, epoch);
        this.ogrenmeKatsayisi = ogrenmeKatsayisi;
        this.momentum = momentum;
        this.araKatmanNoronSayisi = araKatmanNoronSayisi;
        this.momentum = momentum;
        this.ogrenmeKatsayisi = ogrenmeKatsayisi;
        this.error = error;
        this.epoch = epoch;
    }

    public Ysa(int araKatmanNoronSayisi, double ogrenmeKatsayisi, double error, int epoch) throws FileNotFoundException {
        maksimumlar = new double[3];
        minimumlar = new double[3];

        for (int i = 0; i < 3; i++) {
            maksimumlar[i] = Double.MIN_VALUE;
            minimumlar[i] = Double.MAX_VALUE;
        }

        veriSetiMaks(egitimDosya);
        veriSetiMaks(testDosya);

        egitimVeriSeti = veriSetiOku(egitimDosya);
        testVeriSeti = veriSetiOku(testDosya);

        this.araKatmanNoronSayisi = araKatmanNoronSayisi;
        this.ogrenmeKatsayisi = ogrenmeKatsayisi;
        this.error = error;
        this.epoch = epoch;
    }

    private void veriSetiMaks(File veriSeti) throws FileNotFoundException {
        Scanner scanner = new Scanner(veriSeti);
        while (scanner.hasNextLine()) {
            String[] values = scanner.nextLine().split("\\s+");
            for (int i = 0; i < 2; i++) {
                double d = Double.parseDouble(values[i]);
                if (d > maksimumlar[i]) maksimumlar[i] = d;
                if (d < minimumlar[i]) minimumlar[i] = d;
            }
            double output = Double.parseDouble(values[2]);
            if (output > maksimumlar[2]) maksimumlar[2] = output;
            if (output < minimumlar[2]) minimumlar[2] = output;
        }
        scanner.close();
    }

    private DataSet veriSetiOku(File veriSeti) throws FileNotFoundException {
        Scanner scanner = new Scanner(veriSeti);
        DataSet ds = new DataSet(2, 1);

        while (scanner.hasNextLine()) {
            String[] values = scanner.nextLine().split("\\s+");
            double[] input = new double[2];
            double[] output = new double[1];
            for (int i = 0; i < 2; i++) {
                input[i] = minMax(Double.parseDouble(values[i]), minimumlar[i], maksimumlar[i]);
            }
            output[0] = minMax(Double.parseDouble(values[2]), minimumlar[2], maksimumlar[2]);
            ds.add(new DataSetRow(input, output));
        }
        scanner.close();
        return ds;
    }

    private double minMax(double x, double min, double max) {
        return (x - min) / (max - min);
    }
    
    public double[] eniyiag() {
        // Yapay sinir ağı (NN) modelini oluştur
        MultiLayerPerceptron nn = new MultiLayerPerceptron(TransferFunctionType.SIGMOID, 2, araKatmanNoronSayisi, 1);

        // Backpropagation öğrenme kuralı
        BackPropagation bp = new BackPropagation();
        bp.setLearningRate(ogrenmeKatsayisi); // Öğrenme oranı
        bp.setMaxError(error); // Maksimum hata oranı

        // Öğrenme kuralını sinir ağına ekle
        nn.setLearningRule(bp);

        // Hataları tutacak diziler
        double[] egitimHatalari = new double[epoch];
        double[] testHatalari = new double[epoch];

        // Eğitim süreci: Belirtilen epoch kadar öğrenme yap
        for (int i = 0; i < epoch; i++) {
            // Öğrenme işlemi başlat
            nn.learn(egitimVeriSeti);

            // Eğitim ve test hatalarını hesapla
            egitimHatalari[i] = toplamHataHesapla(nn, egitimVeriSeti);
            testHatalari[i] = toplamHataHesapla(nn, testVeriSeti);

            // Her epoch'taki hataları yazdır
          //  System.out.println("Epoch " + (i + 1) + " - Eğitim Hata: " + egitimHatalari[i] + ", Test Hata: " + testHatalari[i]);
        }

        // Sonuçları yazdır
       // System.out.println("Son Epoch sonrası Eğitim Hatası: " + egitimHatalari[epoch - 1] + ", Test Hatası: " + testHatalari[epoch - 1]);
        // Eğitim ve test hatalarını hesapla
        double egitimHata = toplamHataHesapla(nn, egitimVeriSeti);
        double testHata = toplamHataHesapla(nn, testVeriSeti);

        // Hataları ekrana yazdır
        System.out.println("Eğitim Hatası: " + egitimHata + ", Test Hatası: " + testHata);
        // Eğitim ve test hatalarını döndür
        return new double[]{egitimHatalari[epoch - 1], testHatalari[epoch - 1]};
    }



    public double[] egitMomentumlu() {
        // Momentumlu eğitim için Backpropagation nesnesi oluşturuluyor
        MomentumBackpropagation mbp = new MomentumBackpropagation();
        mbp.setMomentum(momentum); // Momentum değeri atanıyor
        mbp.setLearningRate(ogrenmeKatsayisi); // Öğrenme oranı atanıyor
        mbp.setMaxError(error); // Maksimum hata oranı
        mbp.setMaxIterations(epoch); // Epoch sayısı

        // Yapay Sinir Ağı (Neural Network) oluşturuluyor
        MultiLayerPerceptron nn = new MultiLayerPerceptron(TransferFunctionType.SIGMOID, 2, araKatmanNoronSayisi, 1);
        nn.setLearningRule(mbp); // Öğrenme kuralı atanıyor

        // Eğitim işlemi başlatılıyor
        nn.learn(egitimVeriSeti);

        // Eğitim ve test hatalarını hesapla
        double egitimHata = toplamHataHesapla(nn, egitimVeriSeti);
        double testHata = toplamHataHesapla(nn, testVeriSeti);

        // Hataları ekrana yazdır
        System.out.println("Eğitim Hatası: " + egitimHata + ", Test Hatası: " + testHata);

        // Test sonucu döndürülüyor
        return new double[]{egitimHata, testHata};
    }

    public double[] egitMomentumsuz() {
        // BackPropagation (momentumsuz) öğrenme kuralı
        BackPropagation bp = new BackPropagation();
        bp.setLearningRate(ogrenmeKatsayisi);
        bp.setMaxError(error);
        bp.setMaxIterations(epoch);

        // Yapay Sinir Ağı Modeli
        MultiLayerPerceptron nn = new MultiLayerPerceptron(TransferFunctionType.SIGMOID, 2, araKatmanNoronSayisi, 1);
        nn.setLearningRule(bp);

        // Eğitim başlatılıyor
        nn.learn(egitimVeriSeti);

        // Eğitim ve test hatalarını hesaplayın ve yazdırın
        double egitimHata = toplamHataHesapla(nn, egitimVeriSeti);
        double testHata = toplamHataHesapla(nn, testVeriSeti);

        System.out.println("Eğitim Hatası: " + egitimHata + ", Test Hatası: " + testHata);

        return new double[]{egitimHata, testHata};
    }

    public void egitEpochGoster() {
        MultiLayerPerceptron nn = new MultiLayerPerceptron(TransferFunctionType.SIGMOID, 2, araKatmanNoronSayisi, 1);
        BackPropagation bp = new BackPropagation();
        bp.setLearningRate(ogrenmeKatsayisi);
        bp.setMaxError(error);

        nn.setLearningRule(bp);

        for (int i = 0; i < epoch; i++) {
            nn.learn(egitimVeriSeti);

            double egitimHata = toplamHataHesapla(nn, egitimVeriSeti);
            double testHata = toplamHataHesapla(nn, testVeriSeti);

            System.out.println("Epoch " + (i + 1) + " - Eğitim Hata: " + egitimHata + ", Test Hata: " + testHata);
        }
    }

    public void kFoldCrossValidation(int k) {
        List<DataSetRow> veri = new ArrayList<DataSetRow>(egitimVeriSeti.getRows());
        Collections.shuffle(veri);

        int foldSize = veri.size() / k;
        double toplamEgitimHata = 0;
        double toplamTestHata = 0;

        for (int i = 0; i < k; i++) {
            int startIndex = i * foldSize;
            int endIndex = (i == k - 1) ? veri.size() : (i + 1) * foldSize;

            List<DataSetRow> testVeri = veri.subList(startIndex, endIndex);
            List<DataSetRow> egitimVeri = new ArrayList<DataSetRow>(veri);
            egitimVeri.removeAll(testVeri);

            DataSet egitimSeti = new DataSet(egitimVeri.get(0).getInput().length, 1);
            DataSet testSeti = new DataSet(testVeri.get(0).getInput().length, 1);

            for (DataSetRow row : egitimVeri) {
                egitimSeti.add(row);
            }

            for (DataSetRow row : testVeri) {
                testSeti.add(row);
            }

            MultiLayerPerceptron nn = new MultiLayerPerceptron(TransferFunctionType.SIGMOID, 2, araKatmanNoronSayisi, 1);
            BackPropagation bp = new BackPropagation();
            bp.setLearningRate(ogrenmeKatsayisi);
            bp.setMaxError(error);
            nn.setLearningRule(bp);

            nn.learn(egitimSeti);

            double egitimHata = toplamHataHesapla(nn, egitimSeti);
            double testHata = toplamHataHesapla(nn, testSeti);

            toplamEgitimHata += egitimHata;
            toplamTestHata += testHata;
        }

        System.out.println("K-Fold Cross Validation Sonuçları:");
        System.out.println("Ortalama Eğitim Hatası: " + (toplamEgitimHata / k));
        System.out.println("Ortalama Test Hatası: " + (toplamTestHata / k));
    }

    private double toplamHataHesapla(MultiLayerPerceptron nn, DataSet veriSeti) {
        double toplamHata = 0;
        for (DataSetRow row : veriSeti) {
            nn.setInput(row.getInput());
            nn.calculate();
            toplamHata += mse(row.getDesiredOutput(), nn.getOutput());
        }
        return toplamHata / veriSeti.size();
    }

    private double mse(double[] beklenen, double[] cikti) {
        double toplamHata = 0;
        for (int i = 0; i < beklenen.length; i++) {
            toplamHata += Math.pow(beklenen[i] - cikti[i], 2);
        }
        return toplamHata / beklenen.length;
    }

    private double[] testSonucHesapla(MultiLayerPerceptron nn) {
        double egitimHata = toplamHataHesapla(nn, egitimVeriSeti);
        double testHata = toplamHataHesapla(nn, testVeriSeti);

        return new double[]{egitimHata, testHata};
    }

    public double tekTest(double[] inputs) {
        for (int i = 0; i < inputs.length; i++) {
            inputs[i] = minMax(inputs[i], minimumlar[i], maksimumlar[i]);
        }
        MultiLayerPerceptron nn = new MultiLayerPerceptron(TransferFunctionType.SIGMOID, 2, araKatmanNoronSayisi, 1);
        nn.learn(egitimVeriSeti);
        nn.setInput(inputs);
        nn.calculate();
        return nn.getOutput()[0];
    }
}
