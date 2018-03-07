package com.mydana.alms;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mydana.alms.entity.CoreData;
import com.mydana.alms.entity.TechTechstr;
import com.mydana.alms.repo.DataRepo;
import com.mydana.alms.repo.StockRepo;
import com.mydana.alms.repo.TechStrRepo;
import com.mydana.alms.service.core.AlgoService;
import com.mydana.alms.util.FormatUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import java.net.URL;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
@EntityScan(
		basePackageClasses = {AlmsApplication.class, Jsr310JpaConverters.class}
)



public class AlmsApplicationTests {


	@Autowired
	StockRepo repo;

	@Autowired
	DataRepo datarepo;


	@Autowired
	TechStrRepo techStrRepo;

	@Autowired
	AlgoService algoService;

	@Autowired
	ArrayList<String > allasxcodes;


	@Test
	public void contextLoads() {
		System.out.println(" --------- contextLoads -----");
		com.mydana.alms.entity.CoreStock stk = repo.findOne(1L);
		System.out.println(" --------- stk -----"+stk.getName());

//		List<com.mydana.alms.entity.CoreStock> ls = repo.getMyId("");
//
//		ls.forEach(a->{
//			System.out.println(" stocks  " +a.getName());
//		});
//
//
//		List<com.mydana.alms.entity.CoreStock> lsa = repo.geStockName("A");
//		lsa.forEach(a->{
//			System.out.println(" stocks with A " +a.getName());
//		});



	}
	@Test
	public void saveDataDate() {
		System.out.println(" --------- saveDataDate -----");
		CoreData data  = new  CoreData() ;
		data.setCode("BHP.ax");
		data.setDate(LocalDate.now());

		datarepo.save(data);
		System.out.println(" ********************  DONE saveDataDate -----");
	}


	@Test
	public void findByDate() {
		System.out.println(" --------- findByDate -----");
		List<CoreData> coreData = datarepo.findByDate(LocalDate.now().minusDays(1)) ;
		System.out.println(" ********************  DONE findByDate -----" + coreData.size());
	}


	@Test
	public void findTopTwoToday() {
		System.out.println(" --------- findTopTwoToday -----");
		List<CoreData> coreData = datarepo.findTop2ByCodeOrderByDateDesc("RIO.AX") ;
		System.out.println(" ********************  DONE findTopTwoToday -----" + coreData.size());

        CoreData coreDataToday =coreData.get(0);
        CoreData coreDataYest =coreData.get(1);
        Double change  = coreDataToday.getClose() -  coreDataYest.getClose();
        coreDataToday.setChanges(change );
        coreDataToday.setChangepercent (change/ coreDataYest.getClose() );
        datarepo.save(coreDataToday);





    }

	@Test
	public void calcRSI() {
		int periodLength = 14;
		System.out.println(" --------- calcRSI -----");
		List<CoreData> coreData = datarepo.findTop2ByCodeOrderByDateDesc("RIO.AX") ;
		System.out.println(" ********************  DONE calcRSI -----" + coreData.size());

		CoreData coreDataToday =coreData.get(0);
		CoreData coreDataYest =coreData.get(1);

		Double today  = coreDataToday.getClose() ;
		Double yest =   coreDataYest.getClose();
		Double avgUp=coreDataYest.getAvgup();
		Double avgDown = coreDataYest.getAvgdown();
		Double change  = coreDataToday.getClose() -  coreDataYest.getClose();
		Double gains = Math.max(0, change);
		Double losses = Math.max(0, -change);

		avgUp = ((avgUp * (periodLength - 1)) + gains) / (periodLength);
		avgDown = ((avgDown * (periodLength - 1)) + losses) / (periodLength);
		Double rsi = 100 - (100 / (1 + (avgUp / avgDown)));


		coreDataToday.setAvgup(avgUp);
		coreDataToday.setAvgdown(avgDown);

		coreDataToday.setRsi(rsi );
		datarepo.save(coreDataToday);





	}


	@Test
	public void getAverage() {
		System.out.println(" --------- getAverage -----");

		List<CoreData> coreData = datarepo.findTop2ByCodeOrderByDateDesc("RIO.AX") ;
		CoreData coreDataToday =coreData.get(0);


		//List<Object[]> data = datarepo.findAverage("BHP.AX","2018-01-01" );

		String code ="RIO.AX";

		Double close  = coreDataToday.getClose();
		LocalDate date75 = FormatUtil.getWorkDay(coreDataToday.getDate(), 75);
		LocalDate date20 = FormatUtil.getWorkDay(coreDataToday.getDate(), 20);
		LocalDate date40 = FormatUtil.getWorkDay(coreDataToday.getDate(), 40);
		LocalDate date150 = FormatUtil.getWorkDay(coreDataToday.getDate(), 150);
		LocalDate date400 = FormatUtil.getWorkDay(coreDataToday.getDate(), 400);
		LocalDate date200 = FormatUtil.getWorkDay(coreDataToday.getDate(), 200);
		LocalDate date50 = FormatUtil.getWorkDay(coreDataToday.getDate(), 50);
		LocalDate date60 = FormatUtil.getWorkDay(coreDataToday.getDate(), 60);

		//Double  data = datarepo.findAveragePrice(code,date20.toString() ).get(0);

       // System.out.println(" --------- getAverage -----: "+ data[0]);


//		System.out.println(" --------- getAverage -----: "+ datarepo.findAveragePrice(code,date20.toString() ).get(0)[0] );


		Double twenty =(Double)datarepo.findAveragePrice(code,date20.toString() ).get(0);
		Double fourty =(Double)datarepo.findAveragePrice(code,date40.toString() ).get(0);
		Double fifty =(Double)datarepo.findAveragePrice(code,date50.toString() ).get(0);
		Double sevenfive =(Double)datarepo.findAveragePrice(code,date75.toString() ).get(0);
		Double onehundredfifty =(Double)datarepo.findAveragePrice(code,date150.toString() ).get(0);
		Double twohundred =(Double)datarepo.findAveragePrice(code,date200.toString() ).get(0);
		Double fourhundred =(Double)datarepo.findAveragePrice(code,date400.toString() ).get(0);





		coreDataToday.setTwenty( twenty);
		coreDataToday.setTwentychg((close-twenty)/twenty   );

		coreDataToday.setFourty(fourty );
		coreDataToday.setFourtychg((close-fourty)/fourty   );

		coreDataToday.setFifty(fifty);
		coreDataToday.setFiftychg((close-fifty)/fifty   );


		coreDataToday.setSevenfive(sevenfive);
		coreDataToday.setSevenfivechg((close-sevenfive)/sevenfive   );

		coreDataToday.setOnehundredfifty(onehundredfifty );
		coreDataToday.setOnehundredfiftychg((close-onehundredfifty)/onehundredfifty   );

		coreDataToday.setTwohundred( twohundred);
		coreDataToday.setTwohundredchg((close-twohundred)/twohundred   );

		coreDataToday.setFourhundred( fourhundred);
		coreDataToday.setFourhundredchg ((close-fourhundred)/fourhundred   );

		coreDataToday.setAvg3mth((Double)datarepo.findAverageVolume(code,date60.toString() ).get(0)  );


		datarepo.save(coreDataToday);
		//ps1.setString(15, date60.toString());
		//ps1.setString(16, obj.getCode() );



		//System.out.println(" --------- getAverage -----: "+ data);


		System.out.println(" ********************  getAverage  DONE ****************");

	}

	@Test
	public void findByfiftychgfileFromFile () {
		System.out.println(" --------- findByfiftychgfileFromFile -----");
		List<CoreData> coreData = datarepo.findByfiftychgfile(40.0d) ;

		System.out.println(" ********************  DONE findTopTwoToday -----" + coreData.size());






	}


	@Test
	public void findlowRSI () {
		System.out.println(" --------- findlowRSI -----");
		List<Object[]> coreData = datarepo.lowRsi();




		for (Object[] result : coreData) {



			System.out.println ("LOW RSI---------------> "+result[0] + " " + result[1] + " - " + result[2]);


			TechTechstr str = new TechTechstr((String)result[0] , ((java.sql.Date)result[1]).toLocalDate(), 25);
			str.setChangepercent((Double) result[2]);
			techStrRepo.save(str);
		}



		System.out.println(" ********************  DONE findlowRSI -----" + coreData.size());






	}




	@Test
	public void consequitveDayFallStr () {
		algoService.consequitveDayFallStr();
	}

    @Test
    public void  down4PercenteEst() {

        List<Object[]> coreData = datarepo.down4Percent();
        for (Object[] result : coreData) {
           System.out.println ("down4PercenteEst ---------------> "+result[0] + " " + result[1] + " - " + result[2]);




            TechTechstr str = new TechTechstr((String)result[0] , ((java.sql.Date)result[1]).toLocalDate(), 9);
            str.setClose((Double) result[2]);
            str.setFifty(   Double.parseDouble ((String) result[3]));
            str.setFiftychg((Float) result[4]);
            str.setChangepercent( Double.parseDouble ( result[5]+"" ));

       //     techStrRepo.save(str);
        }


    }



	@Test
	public void  importData() {
				final String uri = "https://www.asx.com.au/asx/1/share/fmg";

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			CoreData node = mapper.readValue(new URL(uri), CoreData.class);
			System.out.println("---------importData-------->  "+node );
		//	datarepo.save(node);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void  importAllData() {

		//allasxcodes.forEach((a)-> System.out.println("----codes--:"+a));

		allasxcodes.stream()
				.limit(10)
		.forEach((a)->{

//			importcode(a);
			try {
				TimeUnit.SECONDS.sleep(10);
				System.out.println("----codes--:"+a);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		});

	}

	private void  importcode(String code) {
		final String uri = "https://www.asx.com.au/asx/1/share/"+code;

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			CoreData node = mapper.readValue(new URL(uri), CoreData.class);
			System.out.println("---------importData-------->  "+node );
			//	datarepo.save(node);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


}
