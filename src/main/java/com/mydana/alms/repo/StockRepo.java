package com.mydana.alms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface StockRepo extends JpaRepository<com.mydana.alms.entity.CoreStock,Long> ,StockCustomRepo {


}
