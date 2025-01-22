package celikSertlikCnn;

import java.io.*;
import java.util.*;

public class VeriIsleme {

    private List<double[]> dataSet; // Veri seti
    private List<double[]> trainingSet; // Eğitim seti
    private List<double[]> testSet; // Test seti

    public VeriIsleme() {
        dataSet = new ArrayList<>();
        trainingSet = new ArrayList<>();
        testSet = new ArrayList<>();
    }

    // Veri setini oku
    public void loadData(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        
        // İlk satır başlık satırıdır, atlıyoruz
        br.readLine(); 

        // Veri satırlarını okuyarak listeye ekliyoruz
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(","); // CSV formatına göre virgül ile ayrılıyor
            double[] row = new double[parts.length];
            for (int i = 0; i < parts.length; i++) {
                row[i] = Double.parseDouble(parts[i]);
            }
            dataSet.add(row);
        }
        br.close();
    }

    // Veriyi rastgele karıştır ve eğitim/test setlerini ayır
    public void splitData(double trainRatio) {
        Collections.shuffle(dataSet); // Veriyi karıştır
        int trainSize = (int) (dataSet.size() * trainRatio);
        for (int i = 0; i < dataSet.size(); i++) {
            if (i < trainSize) {
                trainingSet.add(dataSet.get(i));
            } else {
                testSet.add(dataSet.get(i));
            }
        }
    }

    public List<double[]> getTrainingSet() {
        return trainingSet;
    }

    public List<double[]> getTestSet() {
        return testSet;
    }

    // Test metodu
    public static void main(String[] args) {
        VeriIsleme veriIsleme = new VeriIsleme();
        try {
            veriIsleme.loadData("C:\\java projeler\\celikSertlikCnn\\veri_seti.csv"); // Veri setini yükle
            veriIsleme.splitData(0.75); // %75 eğitim, %25 test

            // Eğitim ve test setlerinin boyutlarını yazdır
            System.out.println("Eğitim seti boyutu: " + veriIsleme.getTrainingSet().size());
            System.out.println("Test seti boyutu: " + veriIsleme.getTestSet().size());
            
            // Eğitim ve test setlerini txt dosyasına kaydet
            saveDataToFile("egitim.txt", veriIsleme.getTrainingSet());
            saveDataToFile("test.txt", veriIsleme.getTestSet());
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Veriyi txt dosyasına kaydetme işlemi
    private static void saveDataToFile(String fileName, List<double[]> dataSet) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        for (double[] row : dataSet) {
            StringBuilder sb = new StringBuilder();
            for (double value : row) {
                sb.append(value).append(" "); // Virgül yerine boşluk ekliyoruz
            }
            sb.setLength(sb.length() - 1); // Son boşluğu kaldır
            writer.write(sb.toString());
            writer.newLine();
        }
        writer.close();
    }
}