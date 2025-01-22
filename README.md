# Çelik Sertliği Tahmin Modeli - Yapay Sinir Ağı (CNN)

Bu proje, çelik sertliğini tahmin etmek amacıyla **Yapay Sinir Ağı (YSA)** ve **Convolutional Neural Networks (CNN)** teknolojilerini kullanarak geliştirilen bir modeldir. Model, soğuma hızı ve suda bekletme süresi gibi fiziksel parametreleri göz önünde bulundurarak çelik sertliğini (Vickers Hardness) tahmin etmektedir. Model, geleneksel sertlik tahmin yöntemlerinden daha hızlı ve etkili bir çözüm sunmayı amaçlamaktadır.

---

## **Proje Hedefi ve Kapsamı**

Geleneksel sertlik testlerine alternatif olarak yapay sinir ağı (CNN) tabanlı bir model geliştirilmiştir. Modelin amacı, kullanıcılardan alınan **soğuma hızı** ve **suda bekletme süresi** girdilerini işleyerek, çelik sertliğini doğru bir şekilde tahmin etmektir.

### **Proje Kapsamı:**
- **Soğuma Hızı (Δ°C/sn)**: Çeliğin sıvıdan katı hale dönüşme hızıdır.
- **Suda Bekletme Süresi (sn)**: Çeliğin su içerisinde bekletildiği süredir.
- **Çelik Sertliği (Vickers Hardness)**: Çeliğin sertlik derecesini belirler.

Model, kullanıcılardan alınan bu verilerle, çelik sertliğini tahmin eder ve endüstriyel süreçlerde hızlı bir çözüm sunar.

---

## **Kullanılan Teknolojiler ve Kütüphaneler**

- **Java**: Proje geliştirme dili.
- **Neuroph (Neuroph Core)**: Yapay sinir ağı modelleme ve eğitimi için kullanılan Java kütüphanesi.
- **Java.util.Random**: Rastgele değer üretimi için kullanılan kütüphane.
- **Convolutional Neural Networks (CNN)**: Modelin doğruluğunu artıran derin öğrenme yöntemi.

---

## **Proje Yapısı**

Proje, aşağıdaki dört ana sınıftan oluşmaktadır:

### 1. `verisleme.java`
Veri işleme, normalizasyon ve eğitim/test verilerinin hazırlanmasını sağlar. Bu sınıf, **CSV formatındaki verileri okur** ve uygun hale getirir.

#### **Temel Özellikler:**
- Veri setini yükler ve işleme tabii tutar.
- Eğitim ve test verilerini ayırır (%75 eğitim, %25 test).
- Verileri **`egitim.txt`** ve **`test.txt`** dosyalarına kaydeder.

### 2. `agSec.java`
Bu sınıf, farklı yapay sinir ağı yapılandırmalarını test eder ve bu ağların eğitim ve test hatalarını hesaplar. En iyi ağı seçmek için ağlar arasındaki test hatalarını karşılaştırır.

#### **Temel Özellikler:**
- YSA ağ yapılandırmalarının tanımlanması.
- Eğitim ve test hatalarının görselleştirilmesi.
- En düşük test hatasına sahip ağın seçilmesi.

### 3. `ysa.java`
Yapay sinir ağı modelini tanımlar, eğitir ve test eder. Eğitim sırasında modelin doğruluğunu artırmak için çeşitli yöntemler kullanılır.

#### **Temel Özellikler:**
- **Veri Okuma ve Normalizasyon**: Eğitim ve test verilerini okur ve normalleştirir.
- **CNN Modeli**: MultiLayerPerceptron sınıfı kullanılarak oluşturulur.
- **Eğitim ve Test**: Model eğitilir ve doğruluğu test edilir.
- **Çapraz Doğrulama (K-Fold)**: Modelin doğruluğu çapraz doğrulama ile ölçülür.

### 4. `main.java`
Ana sınıftır. Kullanıcı etkileşimi sağlar ve YSA modelinin eğitim ve test edilmesini sağlar.

#### **Temel Özellikler:**
- Kullanıcıya eğitim ve test seçenekleri sunar.
- **YSA Eğitimi**: Momentumlu veya momentumsuz eğitim seçenekleri ile ağ eğitilir.
- **Epoch Gösterimi**: Eğitim ve test hataları her epoch sonunda gösterilir.
- **K-Fold Çapraz Doğrulama**: Kullanıcıya doğruluk testini yapma imkanı sunar.

---

## **Veri Seti**

Bu projede kullanılan veri seti, **4000 satırdan** oluşmaktadır ve çelik sertliğini tahmin etmek için gerekli olan **soğuma hızı** ve **suda bekletme süresi** verilerini içermektedir. Veriler **CSV formatında** sağlanmıştır ve bu verilerle model eğitilmektedir.

---

## **Modelin Eğitim ve Testi**

Modelin doğruluğunu artırmak için çeşitli YSA yapılandırmaları test edilmiştir. Bu ağlar arasındaki eğitim ve test hataları görselleştirilmiş ve en iyi ağ seçilmiştir.
