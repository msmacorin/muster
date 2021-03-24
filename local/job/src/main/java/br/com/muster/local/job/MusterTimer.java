/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.local.job;

import br.com.muster.facade.admin.ConfiguracaoLocalFacade;
import br.com.muster.local.ejb.ConnectionFactory;
import br.com.muster.local.ejb.ServiceFactory;
import br.com.muster.local.service.Service;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.ScheduleExpression;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;

/**
 *
 * @author macorin
 */
@Singleton
@Startup
public class MusterTimer {

    @EJB
    private ConnectionFactory connectionFactory;

    @EJB
    private ServiceFactory serviceFactory;

    @EJB
    private ConfiguracaoLocalFacade configuracaoLocalFacade;

    @Resource
    private TimerService timerService;

    @PostConstruct
    public void construct() {
//        final TimerConfig plantTheCorn = new TimerConfig("plantTheCorn", false);
//        timerService.createCalendarTimer(new ScheduleExpression().month(5).dayOfMonth("20-Last").minute(0).hour(8), plantTheCorn);
//        timerService.createCalendarTimer(new ScheduleExpression().month(6).dayOfMonth("1-10").minute(0).hour(8), plantTheCorn);
//
//        final TimerConfig harvestTheCorn = new TimerConfig("harvestTheCorn", false);
//        timerService.createCalendarTimer(new ScheduleExpression().month(9).dayOfMonth("20-Last").minute(0).hour(8), harvestTheCorn);
//        timerService.createCalendarTimer(new ScheduleExpression().month(10).dayOfMonth("1-10").minute(0).hour(8), harvestTheCorn);
//
//        final TimerConfig checkOnTheDaughters = new TimerConfig("checkOnTheDaughters", false);
//        timerService.createCalendarTimer(new ScheduleExpression().second("*").minute("*").hour("*"), checkOnTheDaughters);

        for (Service service : serviceFactory.getServices()) {
            final TimerConfig timerConfig = service.getTimerConfig();

            for (ScheduleExpression scheduleExpression : service.getSchedulesExpression()) {
                timerService.createCalendarTimer(scheduleExpression, timerConfig);
            }
        }

    }

    @Timeout
    public void doExecute(Timer timer) {
        if (configuracaoLocalFacade.executarServicos()) {
            for (final Service service : serviceFactory.getServices()) {
                if (timer.getInfo().equals(service.getName())) {
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            service.execute(connectionFactory.getDataSource());
                            service.processAgain();
                        }
                    }).start();
                }
            }
        }
    }

}
