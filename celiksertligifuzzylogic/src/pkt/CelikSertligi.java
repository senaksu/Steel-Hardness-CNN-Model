package pkt;

import java.io.File;
import java.net.URL;
import java.net.URISyntaxException;
import net.sourceforge.jFuzzyLogic.FIS;


public class CelikSertligi {
    private FIS fis;
    private double sogumaHizi;
    private double sudaBeklemeSuresi;

    // Constructor, soğuma hızı ve suda bekleme süresini alır
    public CelikSertligi(double sogumaHizi, double sudaBeklemeSuresi) {
        this.sogumaHizi = sogumaHizi;
        this.sudaBeklemeSuresi = sudaBeklemeSuresi;

        try {
            // FCL dosyasını yüklemek için dosya yolunu belirleyin
            URL resource = getClass().getClassLoader().getResource("pkt/CelikSertlikModel.fcl");

            if (resource == null) {
                System.err.println("FCL dosyası bulunamadı.");
            } else {
                File dosya = new File(resource.toURI());
                fis = FIS.load(dosya.getPath(), true);

                if (fis == null) {
                    System.err.println("Hata: FCL dosyası yüklenemedi.");
                } else {
                    // Girdi değerlerini FIS modeline gönder
                    fis.setVariable("sogumaHizi", sogumaHizi);
                    fis.setVariable("sudaBeklemeSuresi", sudaBeklemeSuresi);

                    // Hesaplamayı yap
                    fis.evaluate();
                }
            }
        } catch (URISyntaxException e) {
            System.err.println("Hata: FCL dosya yolunda sorun var.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Hata: FCL dosyası yüklenemedi.");
            e.printStackTrace();
        }
    }

    // Sertlik değerini döndüren metot
    @Override
    public String toString() {
        if (fis != null) {
            return "Çeliğin Sertliği: " + fis.getVariable("celikSertlik").getValue();
        } else {
            return "Hata: FIS modeli oluşturulamadı.";
        }
    }

    // FIS modelini döndüren metot (JFuzzyChart için)
    public FIS getModel() {
        return fis;
    }
}
