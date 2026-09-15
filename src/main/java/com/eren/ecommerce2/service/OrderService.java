package com.eren.ecommerce2.service;

import java.math.BigDecimal;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eren.ecommerce2.dto.OrderResponse;
import com.eren.ecommerce2.dto.UpdateOrderStatusRequest;
import com.eren.ecommerce2.exception.EmptyCartException;
import com.eren.ecommerce2.exception.InvalidOrderStatusTransitionException;
import com.eren.ecommerce2.exception.OrderNotFoundException;
import com.eren.ecommerce2.exception.StockExceededException;
import com.eren.ecommerce2.mapper.OrderMapper;
import com.eren.ecommerce2.model.CartItem;
import com.eren.ecommerce2.model.Order;
import com.eren.ecommerce2.model.OrderItem;
import com.eren.ecommerce2.model.OrderStatus;
import com.eren.ecommerce2.model.Product;
import com.eren.ecommerce2.repository.CartItemRepository;
import com.eren.ecommerce2.repository.OrderItemRepository;
import com.eren.ecommerce2.repository.OrderRepository;
import com.eren.ecommerce2.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

        /*
         * Order nesnelerini DB'ye kaydetmek için.
         */
        private final OrderRepository orderRepository;

        /*
         * OrderItem nesnelerini DB'ye kaydetmek için.
         */
        private final OrderItemRepository orderItemRepository;

        /*
         * Siparişe çevireceğimiz sepetin içindeki
         * CartItem kayıtlarını bulmak için.
         */
        private final CartItemRepository cartItemRepository;

        /*
         * Sipariş verildiğinde Product stoklarını
         * güncellemek için.
         */
        private final ProductRepository productRepository;

        /*
         * @Transactional:
         *
         * Bu metodun içindeki DB işlemlerini
         * tek bir işlem paketi gibi düşün.
         *
         * Örneğin:
         *
         * Order kaydedildi
         * OrderItem kaydedildi
         * stok düşürüldü
         * ama sonra hata çıktı
         *
         * Böyle yarım bir sipariş istemiyoruz.
         *
         * Hata olursa yapılan DB değişiklikleri
         * geri alınabilir.
         */
        @Transactional
        public OrderResponse createOrder(Long cartId) {

                // ------------------------------------------------
                // 1. PSEUDOCODE:
                // URL'den gelen cartId'ye ait bütün CartItem'ları
                // DB'den getir.
                // ------------------------------------------------

                List<CartItem> cartItems = cartItemRepository.findByCartId(cartId);

                /*
                 * Örneğin cartId = 1 için:
                 *
                 * cartItems:
                 *
                 * [
                 * Mouse x2,
                 * Keyboard x1
                 * ]
                 *
                 * gibi bir liste gelebilir.
                 */

                // ------------------------------------------------
                // 2. PSEUDOCODE:
                // Sepet boşsa sipariş oluşturma.
                // ------------------------------------------------

                if (cartItems.isEmpty()) {

                        throw new EmptyCartException(cartId);
                }

                // ------------------------------------------------
                // 3. PSEUDOCODE:
                // Yeni bir Order oluştur.
                // ------------------------------------------------

                Order order = new Order();

                /*
                 * Sipariş yeni oluşturuldu.
                 *
                 * Henüz tamamlanmış/kargolanmış değil.
                 */
                order.setStatus(OrderStatus.PENDING);

                /*
                 * Siparişin oluşturulduğu zamanı kaydet.
                 *
                 * LocalDateTime.now()
                 * = şu anki tarih + saat
                 */
                order.setCreatedAt(LocalDateTime.now());

                /*
                 * Sipariş toplamını başlangıçta 0 yapıyoruz.
                 *
                 * Birazdan her OrderItem'ın lineTotal değerini
                 * bunun üzerine ekleyeceğiz.
                 */
                order.setTotalPrice(BigDecimal.ZERO);

                // ------------------------------------------------
                // 4. PSEUDOCODE:
                // Önce Order'ı DB'ye kaydet.
                // ------------------------------------------------

                Order savedOrder = orderRepository.save(order);

                /*
                 * Neden şimdiden kaydettik?
                 *
                 * Çünkü birazdan oluşturacağımız OrderItem'ların
                 * order_id değeri bu Order'a bağlanacak.
                 *
                 * save sonrası savedOrder artık DB tarafından
                 * oluşturulmuş bir id'ye sahip.
                 */

                // ------------------------------------------------
                // 5. PSEUDOCODE:
                // Oluşturacağımız bütün OrderItem'ları
                // bu listede tutacağız.
                // ------------------------------------------------

                List<OrderItem> orderItems = new ArrayList<>();

                /*
                 * Siparişin genel toplamını burada hesaplayacağız.
                 *
                 * Başlangıç:
                 *
                 * totalPrice = 0
                 */
                BigDecimal totalPrice = BigDecimal.ZERO;

                // ------------------------------------------------
                // 6. PSEUDOCODE:
                //
                // Sepetteki bütün CartItem'ları tek tek gez.
                //
                // Her CartItem'dan bir OrderItem üret.
                // ------------------------------------------------

                for (CartItem cartItem : cartItems) {

                        // --------------------------------------------
                        // 6.1
                        // Bu CartItem hangi ürüne ait?
                        // --------------------------------------------

                        Product product = cartItem.getProduct();

                        // --------------------------------------------
                        // 6.2
                        // Kullanıcı bu üründen kaç tane sipariş ediyor?
                        // --------------------------------------------

                        Integer quantity = cartItem.getQuantity();

                        // --------------------------------------------
                        // 6.3
                        // Sipariş verilmeden hemen önce
                        // stok hâlâ yeterli mi kontrol et.
                        // --------------------------------------------

                        if (quantity > product.getStock()) {

                                throw new StockExceededException(
                                                product.getStock());
                        }

                        /*
                         * Neden burada tekrar stok kontrolü yapıyoruz?
                         *
                         * Sepete eklenirken stok yeterli olabilir.
                         *
                         * Ama kullanıcı siparişi verene kadar
                         * başka bir sipariş stok tüketmiş olabilir.
                         *
                         * Bu yüzden checkout sırasında tekrar kontrol.
                         */

                        // --------------------------------------------
                        // 6.4
                        // Sipariş ANINDAKİ ürün fiyatını al.
                        // --------------------------------------------

                        BigDecimal unitPrice = product.getPrice();

                        // --------------------------------------------
                        // 6.5
                        // Bu sipariş satırının toplamını hesapla.
                        //
                        // lineTotal = fiyat × quantity
                        // --------------------------------------------

                        BigDecimal lineTotal = unitPrice.multiply(
                                        BigDecimal.valueOf(quantity));

                        /*
                         * Örnek:
                         *
                         * unitPrice = 1000
                         * quantity = 3
                         *
                         * lineTotal = 3000
                         */

                        // --------------------------------------------
                        // 6.6
                        // CartItem'dan yeni bir OrderItem üret.
                        // --------------------------------------------

                        OrderItem orderItem = new OrderItem();

                        // Bu OrderItem hangi siparişe ait?
                        orderItem.setOrder(savedOrder);

                        // Hangi ürün sipariş edildi?
                        orderItem.setProduct(product);

                        // Kaç tane sipariş edildi?
                        orderItem.setQuantity(quantity);

                        /*
                         * Product'ın bugünkü fiyatını OrderItem'a
                         * KOPYALIYORUZ.
                         *
                         * Product fiyatı ileride değişse bile
                         * geçmiş sipariş değişmesin.
                         */
                        orderItem.setUnitPrice(unitPrice);

                        // Bu satırın toplam fiyatı.
                        orderItem.setLineTotal(lineTotal);

                        // --------------------------------------------
                        // 6.7
                        // Oluşturduğumuz OrderItem'ı listeye ekle.
                        // --------------------------------------------

                        orderItems.add(orderItem);

                        // --------------------------------------------
                        // 6.8
                        // Bu satırın toplamını sipariş toplamına ekle.
                        // --------------------------------------------

                        totalPrice = totalPrice.add(lineTotal);

                        /*
                         * Örneğin:
                         *
                         * Başlangıç:
                         * totalPrice = 0
                         *
                         * Mouse:
                         * lineTotal = 2000
                         *
                         * totalPrice = 2000
                         *
                         * Keyboard:
                         * lineTotal = 3000
                         *
                         * totalPrice = 5000
                         */

                        // --------------------------------------------
                        // 6.9
                        // Sipariş verildiği için ürün stokunu azalt.
                        // --------------------------------------------

                        product.setStock(
                                        product.getStock() - quantity);

                        /*
                         * Örnek:
                         *
                         * stock = 20
                         * quantity = 3
                         *
                         * yeni stock = 17
                         */

                        // --------------------------------------------
                        // 6.10
                        // Güncellenen Product stokunu DB'ye kaydet.
                        // --------------------------------------------

                        productRepository.save(product);
                }

                // ------------------------------------------------
                // 7. PSEUDOCODE:
                // Hesapladığımız genel toplamı Order'a koy.
                // ------------------------------------------------

                savedOrder.setTotalPrice(totalPrice);

                // ------------------------------------------------
                // 8. PSEUDOCODE:
                // Güncellenmiş Order'ı tekrar kaydet.
                // ------------------------------------------------

                savedOrder = orderRepository.save(savedOrder);

                /*
                 * İlk save:
                 *
                 * Order'a id kazandırmak için.
                 *
                 * İkinci save:
                 *
                 * hesapladığımız totalPrice'ı kaydetmek için.
                 */

                // ------------------------------------------------
                // 9. PSEUDOCODE:
                // Oluşturduğumuz bütün OrderItem'ları DB'ye kaydet.
                // ------------------------------------------------

                List<OrderItem> savedOrderItems = orderItemRepository.saveAll(orderItems);

                /*
                 * saveAll:
                 *
                 * Bir liste içindeki birden fazla entity'yi
                 * kaydetmek için kullanılır.
                 *
                 * Elimizde:
                 *
                 * [
                 * OrderItem(Mouse),
                 * OrderItem(Keyboard)
                 * ]
                 *
                 * varsa ikisini birlikte kaydeder.
                 */

                // ------------------------------------------------
                // 10. PSEUDOCODE:
                // Sipariş başarıyla oluştu.
                // Artık geçici CartItem kayıtlarını temizle.
                // ------------------------------------------------

                cartItemRepository.deleteAll(cartItems);

                /*
                 * CartItem'ları silebiliriz çünkü:
                 * CartItem = geçici sepet verisi
                 * OrderItem = artık kalıcı sipariş geçmişi
                 * CartItem silinse bile:
                 * Order
                 * └── OrderItem
                 * kayıtları DB'de kalır.
                 */

                // ------------------------------------------------
                // 11. PSEUDOCODE:
                // Kaydedilmiş Order + OrderItem listesini
                // API response'una çevir.
                // ------------------------------------------------

                return OrderMapper.toResponse(
                                savedOrder,
                                savedOrderItems);
        }

        public OrderResponse getOrder(Long orderId) {

                // ------------------------------------------------
                // 1. PSEUDOCODE:
                //
                // URL'den gelen orderId ile Order'ı DB'den bul.
                // ------------------------------------------------

                Order order = orderRepository
                                .findById(orderId)
                                .orElseThrow(
                                                () -> new OrderNotFoundException(orderId)
                                );

                /*
                 * Örneğin:
                 *
                 * orderId = 1
                 *
                 * DB'de Order id=1 varsa:
                 *
                 * order değişkeninin içine o Order gelir.
                 */

                // ------------------------------------------------
                // 2. PSEUDOCODE:
                //
                // Bu Order'a ait bütün OrderItem'ları DB'den getir.
                // ------------------------------------------------

                List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);

                /*
                 * Örneğin:
                 *
                 * orderId = 1
                 *
                 * orderItems:
                 *
                 * [
                 * Mouse x2,
                 * Keyboard x1
                 * ]
                 */

                // ------------------------------------------------
                // 3. PSEUDOCODE:
                //
                // Order + OrderItem listesini
                // OrderResponse'a çevir.
                // ------------------------------------------------

                return OrderMapper.toResponse(
                                order,
                                orderItems);
        }

        public OrderResponse updateStatus(
                        Long orderId,
                        UpdateOrderStatusRequest request) {

                // ------------------------------------------------
                // 1. PSEUDOCODE:
                //
                // URL'den gelen orderId ile Order'ı DB'den bul.
                // ------------------------------------------------

                Order order = orderRepository
                                .findById(orderId)
                                .orElseThrow(
                                         () -> new OrderNotFoundException(orderId)
                                );

                /*
                 * Örnek:
                 *
                 * orderId = 1
                 *
                 * DB'de Order id=1 varsa:
                 * order değişkenine o sipariş gelir.
                 */

                // ------------------------------------------------
                // 2. PSEUDOCODE:
                //
                // Request'ten yeni status değerini al.
                // ------------------------------------------------

                OrderStatus newStatus = request.status();

                /*
                 * Örneğin body:
                 *
                 * {
                 * "status": "PAID"
                 * }
                 *
                 * Spring bunu:
                 *
                 * OrderStatus.PAID
                 *
                 * olarak oluşturur.
                 */

                // ------------------------------------------------
                // 3. PSEUDOCODE:
                //
                // Siparişin mevcut status değerini al.
                // ------------------------------------------------

                OrderStatus currentStatus = order.getStatus();

                /*
                 * Örneğin:
                 *
                 * currentStatus = PENDING
                 * newStatus = PAID
                 */

                // ------------------------------------------------
                // 4. PSEUDOCODE:
                //
                // Şimdilik sadece doğru sıradaki geçişlere izin ver.
                //
                // PENDING -> PAID
                // PAID -> SHIPPED
                // SHIPPED -> DELIVERED
                //
                // Başka geçiş varsa hata ver.
                // ------------------------------------------------

                boolean validTransition =

                                (currentStatus == OrderStatus.PENDING
                                                && newStatus == OrderStatus.PAID)

                                                ||

                                                (currentStatus == OrderStatus.PAID
                                                                && newStatus == OrderStatus.SHIPPED)

                                                ||

                                                (currentStatus == OrderStatus.SHIPPED
                                                                && newStatus == OrderStatus.DELIVERED);

                // ------------------------------------------------
                // 5. PSEUDOCODE:
                //
                // Geçiş geçerli değilse status değiştirme.
                // ------------------------------------------------

                if (!validTransition) {

                        throw new InvalidOrderStatusTransitionException(currentStatus, newStatus);
                }

                // ------------------------------------------------
                // 6. PSEUDOCODE:
                //
                // Geçiş uygunsa Order'ın status değerini değiştir.
                // ------------------------------------------------

                order.setStatus(newStatus);

                // ------------------------------------------------
                // 7. PSEUDOCODE:
                //
                // Güncellenmiş Order'ı DB'ye kaydet.
                // ------------------------------------------------

                Order savedOrder = orderRepository.save(order);

                // ------------------------------------------------
                // 8. PSEUDOCODE:
                //
                // Response oluşturabilmek için bu siparişe ait
                // OrderItem'ları getir.
                // ------------------------------------------------

                List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);

                // ------------------------------------------------
                // 9. PSEUDOCODE:
                //
                // Güncellenmiş Order + OrderItem listesini
                // OrderResponse'a çevir.
                // ------------------------------------------------

                return OrderMapper.toResponse(
                                savedOrder,
                                orderItems);
        }

        public List<OrderResponse> getAllOrders() {

                // ------------------------------------------------
                // 1. PSEUDOCODE:
                //
                // DB'deki bütün Order kayıtlarını getir.
                // ------------------------------------------------

                List<Order> orders = orderRepository.findAll();

                // ------------------------------------------------
                // 2. PSEUDOCODE:
                //
                // Her Order için:
                // - ona ait OrderItem'ları bul
                // - OrderResponse oluştur
                //
                // Sonuçta List<OrderResponse> üret.
                // ------------------------------------------------

                return orders.stream()
                                .map(order -> {

                                        // Bu siparişe ait ürün satırlarını DB'den getir.
                                        List<OrderItem> orderItems = orderItemRepository.findByOrderId(
                                                        order.getId());

                                        // Order + OrderItem listesini response'a çevir.
                                        return OrderMapper.toResponse(
                                                        order,
                                                        orderItems);
                                })
                                .toList();
        }
}
