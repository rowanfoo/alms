package com.mydana.alms.repo;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaMetamodelEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Arrays;
import java.util.List;
@Repository
public class StockRepoImpl implements StockCustomRepo {

    @PersistenceContext
    private EntityManager entityManager;
    private SimpleJpaRepository<com.mydana.alms.entity.CoreStock, Long> repository;
    @PostConstruct
    public void init() {
        System.out.println( "----------------StockCustomRepoImpl");
        System.out.println( "----------------------------StockCustomRepoImpl ---"+ entityManager.getMetamodel().getEntities() );

        JpaEntityInformation<com.mydana.alms.entity.CoreStock, Long> contactEntityInfo = new JpaMetamodelEntityInformation<com.mydana.alms.entity.CoreStock, Long>(com.mydana.alms.entity.CoreStock.class, entityManager.getMetamodel());
        repository = new SimpleJpaRepository<com.mydana.alms.entity.CoreStock, Long>(contactEntityInfo, entityManager);
    }


    @Override
    public List<com.mydana.alms.entity.CoreStock> getMyId(String id) {
        System.out.println("----------------my name ");
        return repository.findAll(Arrays.asList(1L));
    }

    @Override
    public List<com.mydana.alms.entity.CoreStock> geStockName(String name) {

        Query query = entityManager.createNativeQuery("SELECT * FROM core_stock " +
                "WHERE name LIKE ?", com.mydana.alms.entity.CoreStock.class);
        query.setParameter(1, "%"+name + "%");
        return query.getResultList();
    }


}
