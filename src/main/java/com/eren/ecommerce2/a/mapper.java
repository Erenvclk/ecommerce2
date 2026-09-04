package com.eren.ecommerce2.a;
//dto ları import et. 
import com.eren.ecommerce2.a.entityRequest;
import com.eren.ecommerce2.a.entityResponse; 

//entity yi import et.
import com.eren.ecommerce2.a.entity;


//toEntity yaz. dto dan gelen veriyi veri
//  tabanına işlenecek hjale çevirsin

//toResppnse yaz. entityden geleni dto ya çevirsin. 

public class mapper {
    // burada çevirici fonksıyonlar static yazılıyor. 
    // NEeden static yazılıyor? 

    // static fonksıyon: bir kere yazılıp hep kulanılacak fonksıyonlar için yazılıyç 
    // projede tek toEntity ua da ToResponse yazıcaksın
    // baska olmıcak

    //static olmazsa baska sayfada aynı ısımlı fonk yazabilirsin
    //pacakge belirttiğinde hanıg sayfada yazdıysan onu kullanırsın.
    public static entity toEntity(entityRequest request){
        entity entity = new entity();
        entity.setName(request.name());
        entity.setDescription(request.description());
        entity.setPrice(request.price());
        return entity;
    }

    public static entityResponse toResponse(entity entity){
        return new entityResponse(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice().doubleValue(),
                entity.getStock()
        );
    }
}