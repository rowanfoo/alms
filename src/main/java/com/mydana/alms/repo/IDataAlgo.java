package com.mydana.alms.repo;

import com.mydana.alms.entity.CoreStock;
import com.mydana.alms.entity.TechTechstr;

import java.util.ArrayList;
import java.util.List;


public interface IDataAlgo {

    List<Object[]>  breakRoundNumber(String code);
    List<Object[]>  lowRsi();
    List<Object[]>  consequitveDayFallStr(String date);
    List<Object[]>  volumeGreaterSixMonth();
    ArrayList<TechTechstr> volumelargeandPriceReversal();
    List<Object[]>  down4Percent();


}
