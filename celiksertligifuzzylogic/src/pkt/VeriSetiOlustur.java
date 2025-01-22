package pkt;
import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class VeriSetiOlustur {
	
	public static void main(String[] args) {
        Random rand = new Random();
        int toplamVeri = 4000;
        StringBuilder veriSeti = new StringBuilder();
        
        // Veri setinin başlık satırını ekleyelim
        veriSeti.append("sogumaHizi,sudaBeklemeSuresi,celikSertlik\n");
        
        // Veri setini oluştur
        for (int i = 0; i < toplamVeri; i++) {
            // Rastgele değerler üret
            double sogumaHizi = rand.nextDouble() * 120;  // 0-120°C/sn arası
            double sudaBeklemeSuresi = rand.nextDouble() * 60;  // 0-60 sn arası
            
            // CelikSertligi modelini kullanarak sertlik hesapla
            CelikSertligi celikSertligi = new CelikSertligi(sogumaHizi, sudaBeklemeSuresi);
            double sertlik = celikSertligi.getModel().getVariable("celikSertlik").getValue();
            
            // Veriyi CSV formatında kaydet
            veriSeti.append(sogumaHizi).append(",")
                    .append(sudaBeklemeSuresi).append(",")
                    .append(sertlik).append("\n");
        }
        
        // Dosya yolunu belirtelim ve veri setini kaydedelim
        File dosya = new File("veri_seti.csv");

        try (FileWriter writer = new FileWriter(dosya)) {
            // Veri setini dosyaya yaz
            writer.write(veriSeti.toString());
            System.out.println("Veri seti başarıyla oluşturuldu.");
            System.out.println("Dosya Yolu: " + dosya.getAbsolutePath());  // Dosya yolunu yazdır
        } catch (IOException e) {
            System.err.println("Hata: Veri seti kaydedilemedi.");
            e.printStackTrace();
        }
    }

}
