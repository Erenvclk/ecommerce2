//package belirt
//enttityi import et
//jpa repositoryyi import et 
package com.eren.ecommerce2.a;
import org.springframework.data.jpa.repository.JpaRepository;
import com.eren.ecommerce2.a.entity;


/**
 * repository
 */
public interface repository extends JpaRepository<entity, Long> {
    
}
// repositorynın işlevi entity için hazır veritabanı işlemleri sağlar. 
// lazım olursa ek sql sorgularını da buraya eklersın.
