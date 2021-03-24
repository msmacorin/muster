/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.local.service;

import java.util.List;
import javax.ejb.ScheduleExpression;
import javax.ejb.TimerConfig;
import javax.sql.DataSource;

/**
 *
 * @author macorin
 */
public interface Service {
    
    TimerConfig getTimerConfig();
    List<ScheduleExpression> getSchedulesExpression();
    String getName();
    void execute(DataSource dataSource);
    void processAgain();
    void removeOlds();
    
}
