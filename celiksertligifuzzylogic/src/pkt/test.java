package pkt;

import java.util.Scanner;
import net.sourceforge.jFuzzyLogic.rule.Rule;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;

public class test {
    public static void main(String[] args) {
        // Kullanıcıdan veri almak için Scanner nesnesi oluşturuluyor
        Scanner in = new Scanner(System.in);

        try {
            // Kullanıcıdan soğuma hızı ve suda bekleme süresi alınır
            System.out.print("Soğuma Hızı: ");
            double sogumaHizi = in.nextDouble();

            System.out.print("Suda Bekleme Süresi: ");
            double sudaBeklemeSuresi = in.nextDouble();

            // CelikSertligi nesnesi oluşturuluyor
            CelikSertligi celikSertligi = new CelikSertligi(sogumaHizi, sudaBeklemeSuresi);
            System.out.println(celikSertligi);

            // JFuzzyChart ile grafiği çizmek için nesne üzerinden getModel() çağırıyoruz
            // JFuzzyChart.get().chart(celikSertligi.getModel()); // Bu şekilde nesne üzerinden çağırıyoruz

            // Kurallar üzerinde işlem yapılıyor
            for (Rule r : celikSertligi.getModel().getFunctionBlock("celiginSertligiModel").getFuzzyRuleBlock("kural").getRules()) {
                if (r.getDegreeOfSupport() > 0) {
                    System.out.println(r);
                }
            }

        } catch (Exception ex) {
            System.out.println("Hata: " + ex.getMessage());
        } finally {
            // Scanner kapanması unutulmamalı
            in.close();
        }
    }
}
