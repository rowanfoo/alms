package com.mydana.alms.repo;

import com.mydana.alms.entity.CoreStock;
import com.mydana.alms.entity.TechTechstr;
import com.mydana.alms.util.FormatUtil;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaMetamodelEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DataRepoImpl implements IDataAlgo {

    @PersistenceContext
    private EntityManager entityManager;
    private SimpleJpaRepository<CoreStock, Long> repository;
    @PostConstruct
    public void init() {
        System.out.println( "----------------StockCustomRepoImpl");
        System.out.println( "----------------------------StockCustomRepoImpl ---"+ entityManager.getMetamodel().getEntities() );

        JpaEntityInformation<CoreStock, Long> contactEntityInfo = new JpaMetamodelEntityInformation<CoreStock, Long>(com.mydana.alms.entity.CoreStock.class, entityManager.getMetamodel());
        repository = new SimpleJpaRepository<com.mydana.alms.entity.CoreStock, Long>(contactEntityInfo, entityManager);
    }


//    @Override
//    public List<CoreStock> getMyId(String id) {
//        System.out.println("----------------my name ");
//        return repository.findAll(Arrays.asList(1L));
//    }

    @Override
    public List<Object[]>  breakRoundNumber(String code){
        String mysql =" SELECT A.CLOSE as today, B.CLOSE as yesterday  FROM  ( SELECT floor(close) as close from core_data where code=? ORDER BY date desc LIMIT 0,1) AS A, (SELECT floor(close) as close from core_data where code=? ORDER BY date desc LIMIT 1,1 ) AS B  WHERE A.CLOSE < B.CLOSE ";
//// TODO: 2/24/2018 have to test , change to use order by date rather than currdate()

        Query query = entityManager.createNativeQuery(mysql);
        query.setParameter(1, code);
        query.setParameter(1, code);

        return query.getResultList();
    }


    @Override
    public List<Object[]>  lowRsi(){
        String mysql =" select code,date,rsi from core_data where date=" +
                "(select date from core_data ORDER BY date desc LIMIT 1)" +
                "and rsi < 28";
//// TODO: 2/24/2018 have to test , change to use order by date rather than currdate()

        Query query = entityManager.createNativeQuery(mysql);

        return query.getResultList();
    }



    @Override
    public List<Object[]>  consequitveDayFallStr(String date){
        String mysql = "SELECT code, count(*) count, max(close) as mymax ,min(close)as " +
                "mymin FROM fortune.data where date >=? and changePercent<0 group by code  having count >=6";
        Query query = entityManager.createNativeQuery(mysql);

        query.setParameter(1, date);



        return query.getResultList();
    }




    @Override
    public List<Object[]>  volumeGreaterSixMonth(){
        //// TODO: 2/27/2018  not implemented in JSP to detech high volume
        String mysql = " select code,date,close,open,low,high,changepercent FROM  data  where "+
               "date=(select date from data order by date desc limit 1 ) and  volume > (Avg3mth * 4 )";
        Query query = entityManager.createNativeQuery(mysql);
        return query.getResultList();
    }


    public ArrayList <TechTechstr>   volumelargeandPriceReversal(){
        //// TODO: 2/27/2018  not implemented in JSP to detech high volume
        String mysql = " select code,date,close,open,low,high,changepercent FROM  data  where "+
                "date=(select date from data order by date desc limit 1 ) and  volume > (Avg3mth * 2 )";
        Query query = entityManager.createNativeQuery(mysql);
        List<Object[]>  coreData = query.getResultList();
        ArrayList <TechTechstr> arr = new ArrayList<>();
        for (Object[] result : coreData) {

            System.out.println ("Data Repo volumelargeandPriceReversal---------------> "+result[0] + " " + result[1] + " - " + result[2]);

            TechTechstr str = new TechTechstr((String)result[0] ,((java.sql.Date)result[1]).toLocalDate()  , 11);

            String mysql1 ="INSERT INTO techstr (code,date,mode,close,lowlow) VALUES (?,?,?,?,?)";

            double close=(Double) result[2];
            double low=(Double) result[3];
            double open=(Double) result[4];
            double high=(Double) result[5];
            double changepercent=(Double) result[6];

            if( close > ((low+high    )/2 )    ){

                str.setClose(close );
                str.setLowlow(""+low);
                arr.add(str);
            }


        }


        return arr;
    }

    @Override
    public List<Object[]>  down4Percent(){
        String mysql ="SELECT code,date,close,format(fifty,2) as fiftyd,changePercent , format(fiftychg*100,2) as fiftyDchg   "+
                "FROM  core_data  where date= (select date from core_data order by date desc limit 1 )  and changePercent < -0.04";

        Query query = entityManager.createNativeQuery(mysql);

        return query.getResultList();
    }



}
