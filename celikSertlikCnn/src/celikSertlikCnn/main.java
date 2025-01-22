package celikSertlikCnn;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(System.in);
        int sec;
        Ysa ysa = null;

        // Ağ 10'un parametreleri
        int araKatmanNoronSayisi = 50; // Nöron sayısı
        double momentum = 0.2; // Momentum
        double ogrenmeKatsayisi = 0.6; // Öğrenme katsayısı
        double error = 0.001; // Hata toleransı
        int epoch = 500; // Epoch sayısı

        do {
            System.out.println("1. Ağı Eğit ve Test Et (Momentumlu)");
            System.out.println("2. Ağı Eğit ve Test Et (Momentumsuz)");
            System.out.println("3. Ağı Eğit Epoch Göster");
            System.out.println("4. Ağı Eğit ve Tekli Test (Momentumlu)");
            System.out.println("5. K-Fold Test");
            System.out.println("6. Çıkış");
      
            System.out.print("?=> ");
            sec = in.nextInt();

            switch (sec) {
                case 1:
                    // Ağ 10 parametreleriyle eğitim başlat
                    ysa = new Ysa(araKatmanNoronSayisi, momentum, ogrenmeKatsayisi, error, epoch);
                    ysa.egitMomentumlu(); // Momentumsuz eğitim
                    break;

                case 2:
                    // Ağ 10 parametreleriyle eğitim başlat
                    ysa = new Ysa(araKatmanNoronSayisi, ogrenmeKatsayisi, error, epoch);
                    ysa.egitMomentumsuz(); // Momentumsuz eğitim
                    break;

                case 3:
                    // Ağ 10 parametreleriyle eğitim başlat
                    ysa = new Ysa(araKatmanNoronSayisi, momentum, ogrenmeKatsayisi, error, epoch);
                    ysa.egitEpochGoster();
                    break;

                case 4:
                    if (ysa == null) {
                        System.out.println("Önce Eğitim Yapınız.");
                        break;
                    }
                    double[] inputs = new double[2];
                    System.out.print("Soğuma Hızı: ");
                    inputs[0] = in.nextDouble();
                    System.out.print("Bekletme Süresi: ");
                    inputs[1] = in.nextDouble();
                    System.out.println("Çelik Sertliği: " + ysa.tekTest(inputs));
                    break;

                case 5:
                    // Ağ 10 parametreleriyle eğitim başlat
                    ysa = new Ysa(araKatmanNoronSayisi, ogrenmeKatsayisi, error, epoch);
                    System.out.print("K Değeri: ");
                    int k = in.nextInt();
                    ysa.kFoldCrossValidation(k);
                    break;

                case 6:
                    System.out.println("Çıkılıyor...");
                    break;

                default:
                    System.out.println("Geçersiz seçenek.");
            }
        } while (sec != 6);

        in.close();
    }

    private static int validateIntInput(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Lütfen geçerli bir tamsayı giriniz.");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static double validateDoubleInput(Scanner scanner) {
        while (!scanner.hasNextDouble()) {
            System.out.println("Lütfen geçerli bir ondalık sayı giriniz.");
            scanner.next();
        }
        return scanner.nextDouble();
    }
}
