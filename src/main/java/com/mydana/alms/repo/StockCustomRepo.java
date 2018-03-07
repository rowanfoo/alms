package com.mydana.alms.repo;

import java.util.List;

public interface StockCustomRepo {

     List<com.mydana.alms.entity.CoreStock> getMyId(String name);
     List<com.mydana.alms.entity.CoreStock> geStockName(String name) ;
}
