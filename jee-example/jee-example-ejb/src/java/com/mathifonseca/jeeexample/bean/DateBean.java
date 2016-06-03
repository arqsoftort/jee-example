package com.mathifonseca.jeeexample.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

@Stateless
@LocalBean
public class DateBean {

    public Date toDate(String str) {
        Date parsed = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            parsed = sdf.parse(str);
        } catch (ParseException ex) {
            Logger.getLogger(DateBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return parsed;
    }
    
    public String toString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

}
