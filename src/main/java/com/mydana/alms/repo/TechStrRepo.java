package com.mydana.alms.repo;

import com.mydana.alms.entity.CoreData;
import com.mydana.alms.entity.TechTechstr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface TechStrRepo extends JpaRepository<TechTechstr,Long> {

}
