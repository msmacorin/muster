/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.local.service;

import br.com.muster.facade.servico.HitRatioDictionaryCacheFacade;
import br.com.muster.model.servico.HitRatioDictionaryCache;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.ScheduleExpression;
import javax.ejb.Stateless;
import javax.ejb.TimerConfig;
import javax.sql.DataSource;

/**
 *
 * @author macorin
 */
@Stateless
@LocalBean
public class HitRatioDictionaryCacheService implements Service {

    @EJB
    private HitRatioDictionaryCacheFacade hitRatioDictionaryCacheFacade;
    
    @Override
    public TimerConfig getTimerConfig() {
        return new TimerConfig(getName(), false);
    }

    @Override
    public List<ScheduleExpression> getSchedulesExpression() {
        List<ScheduleExpression> expressions = new LinkedList<>();
        expressions.add(new ScheduleExpression().hour("*").minute("*/10"));

        return expressions;
    }

    @Override
    public String getName() {
        return "HIT_RATIO_DICTIONARY_CACHE";
    }

    @Override
    public void processAgain() {
        for (HitRatioDictionaryCache h : hitRatioDictionaryCacheFacade.registrosPendentes()) {
            hitRatioDictionaryCacheFacade.doPost(h);
        }
    }

    @Override
    public void removeOlds() {
        hitRatioDictionaryCacheFacade.removerEnviadosAntigos();
    }
    
    @Override
    public void execute(DataSource dataSource) {
        Statement sta = null;
        ResultSet res = null;
        Connection con = null;

        try {
            con = dataSource.getConnection();
            sta = con.createStatement();
            res = sta.executeQuery("SELECT (1 - (SUM(getmisses) / SUM(gets)))*100 resultado FROM V$ROWCACHE");

            if (res.next()) {
                BigDecimal resultado = res.getBigDecimal("resultado");
                
                HitRatioDictionaryCache dic = new HitRatioDictionaryCache();
                dic.setResultado(resultado.setScale(4, RoundingMode.HALF_UP));
                dic.setData(new Date());
                
                hitRatioDictionaryCacheFacade.doPost(dic);
            }
        } catch (SQLException ex) {
            Logger.getLogger(HitRatioDictionaryCacheService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (res != null) {
                try {
                    res.close();
                } catch (SQLException ex) {
                    Logger.getLogger(HitRatioDictionaryCacheService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (sta != null) {
                try {
                    sta.close();
                } catch (SQLException ex) {
                    Logger.getLogger(HitRatioDictionaryCacheService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(HitRatioDictionaryCacheService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

}
