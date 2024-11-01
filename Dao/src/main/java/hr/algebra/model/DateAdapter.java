/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author lorenababic
 */
public class DateAdapter extends XmlAdapter<String, LocalDate>{

    @Override
    public LocalDate unmarshal(String vt) throws Exception {
        return LocalDate.parse(vt, DateTimeFormatter.ISO_LOCAL_DATE);
    }

    /**
     *
     * @param bt
     * @return
     * @throws Exception
     */
    @Override
    public String marshal(LocalDate bt) throws Exception {
        return bt.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }   
}
