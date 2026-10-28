# E-Ticaret API (ecommerce2)

Java ve Spring Boot ile geliştirdiğim e-ticaret backend'i. Ürün ve kategori yönetimi, sepet işlemleri ve sepetten siparişe geçiş akışını içerir.

> **Durum:** Geliştirme aşamasında. Güvenlik katmanı (kimlik doğrulama ve yetkilendirme) üzerinde çalışıyorum.

## Özellikler

- Ürün ve kategori için tam CRUD
- Sepet oluşturma ve sepet kalemleri: ekleme, miktar güncelleme, silme
- Stok kontrolü: sepete eklerken, miktar güncellerken ve sipariş verirken stoğu aşan istekler reddedilir
- Sepetten sipariş oluşturma: sipariş ve sipariş kalemleri tek bir transaction içinde kaydedilir, sipariş anındaki birim fiyat saklanır, toplam hesaplanır ve stok düşülür
- Bean Validation ile istek doğrulama (ürün oluşturma ve güncelleme)
- `@RestControllerAdvice` ile merkezi hata yönetimi: ürün bulunamadı (404), doğrulama hataları ve stok aşımı (400)

## API Uç Noktaları

| Metot | Yol | Açıklama |
| --- | --- | --- |
| POST | `/products` | Ürün oluştur |
| GET | `/products` | Ürünleri listele |
| GET | `/products/{id}` | Ürün getir |
| PUT | `/products/{id}` | Ürün güncelle |
| DELETE | `/products/{id}` | Ürün sil |
| POST | `/categories` | Kategori oluştur |
| GET | `/categories` | Kategorileri listele |
| GET | `/categories/{id}` | Kategori getir |
| PUT | `/categories/{id}` | Kategori güncelle |
| DELETE | `/categories/{id}` | Kategori sil |
| POST | `/carts` | Sepet oluştur |
| GET | `/carts/{cartId}` | Sepeti getir |
| POST | `/carts/{cartId}/items` | Sepete ürün ekle |
| PATCH | `/carts/{cartId}/items/{itemId}` | Sepet kalemi miktarını güncelle |
| DELETE | `/carts/{cartId}/items/{itemId}` | Sepet kalemini sil |
| POST | `/orders/from-cart/{cartId}` | Sepetten sipariş oluştur |

## Veri Modeli

`Category` → `Product` → `CartItem` → `Cart`, `Product` → `OrderItem` → `Order`

| Entity | Açıklama |
| --- | --- |
| Category | Ürün kategorisi |
| Product | Ürün: ad, açıklama, fiyat, stok, kategori |
| Cart / CartItem | Sepet ve içindeki ürün + miktar kalemleri |
| Order / OrderItem | Sipariş ve kalemleri: miktar, birim fiyat, satır toplamı |

## Mimari

```
controller → service → repository → MySQL
     ↑          ↑
    DTO       mapper
```

| Paket | Görevi |
| --- | --- |
| `controller` | HTTP isteklerini karşılar, DTO alır ve döner |
| `service` | İş kuralları (stok kontrolü, sipariş oluşturma) |
| `repository` | Spring Data JPA ile veritabanı erişimi |
| `model` | JPA entity'leri |
| `dto` | İstek ve cevap modelleri (Java record) |
| `mapper` | Entity ile DTO arasında dönüşüm |
| `exception` | Özel exception'lar ve global hata yönetimi |

## Teknolojiler

| Araç | Sürüm / Not |
| --- | --- |
| Java | 21 |
| Spring Boot | 4.1.1 |
| Spring Web MVC | REST API |
| Spring Data JPA | Veritabanı erişimi |
| MySQL | Veritabanı |
| Bean Validation | İstek doğrulama |
| Lombok | Boilerplate azaltma |
| Maven | Maven Wrapper (`mvnw`) ile gelir |

## Kurulum

Gereksinimler: JDK 21 ve çalışan bir MySQL sunucusu.

1. Repoyu klonla:

   ```bash
   git clone https://github.com/Erenvclk/ecommerce2.git
   cd ecommerce2
   ```

2. MySQL'de veritabanını oluştur:

   ```sql
   CREATE DATABASE ecommerce2;
   ```

3. `src/main/resources/application.properties` dosyasını oluşturup kendi bağlantı bilgilerini yaz (dosya güvenlik nedeniyle repoda tutulmaz):

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce2
   spring.datasource.username=kullanici_adi
   spring.datasource.password=sifre
   spring.jpa.hibernate.ddl-auto=update
   ```

4. Uygulamayı başlat:

   ```bash
   ./mvnw spring-boot:run
   ```

   Windows'ta `mvnw.cmd spring-boot:run` komutunu kullan.

## Yol Haritası

- [ ] Kimlik doğrulama ve yetkilendirme (güvenlik katmanı)
