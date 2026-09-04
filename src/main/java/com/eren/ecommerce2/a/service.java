package com.eren.ecommerce2.a;
import org.springframework.stereotype.Service;
import com.eren.ecommerce2.a.repository;

import lombok.RequiredArgsConstructor;
import java.util.List;
import com.eren.ecommerce2.a.entity;
@RequiredArgsConstructor
@Service
// service kısmında ne yapıyorduk 
/*
şimdi once responser yazıyorusn. 
requesti parametre alan ürün üretici yaz
requestin enttiyye çevilirmiş halini entity nesnesinde tut.
entity nesnesini kaydet.
kaydedileen entity nesnesini responseye çevirip döndür.
*/
public class service {
        private final repository repository;

        public entityResponse crateEntity (entityRequest request){
            entity entity = mapper.toEntity(request);
            entity savedEntity = repository.save(entity);
            return mapper.toResponse(savedEntity);
        };
        //Neden request dtosundan üretiyoruz bu mapperı ? 

        /* servicei olay su: requestten servici baslatmanın nedenı:
        request geldi,
        mapper ile çevirdin
        çeviriyi kaydettin,
        kayıt ettiğin yenı hali responseye çeviridn
        response çevirisini dondurdun
        
        */
       // zaten requesti yanıtlamak için 
       // her zamman respons a ihtayacın var
    // istersen requesti veritabanına kaydeden ve kaydı respons'a 
    //çevirin iki fonksıyon da yazabilirsin. ama gerek yko
    //bu tek seferde ürün olustur fonkU işin tamamını tekde yapıyr.
    }
