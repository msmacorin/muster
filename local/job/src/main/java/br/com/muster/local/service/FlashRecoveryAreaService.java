/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.local.service;

import br.com.muster.facade.servico.FlashRecoveryAreaFacade;
import br.com.muster.model.servico.FlashRecoveryArea;
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
public class FlashRecoveryAreaService implements Service {

    @EJB
    private FlashRecoveryAreaFacade flashRecoveryAreaFacade;

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
        return "FLASH_RECOVERY_AREA";
    }

    @Override
    public void removeOlds() {
        flashRecoveryAreaFacade.removerEnviadosAntigos();
    }

    @Override
    public void processAgain() {
        for (FlashRecoveryArea f : flashRecoveryAreaFacade.registrosPendentes()) {
            flashRecoveryAreaFacade.doPost(f);
        }
    }

    @Override
    public void execute(DataSource dataSource) {
        Statement sta = null;
        ResultSet res = null;
        Connection con = null;

        try {
            con = dataSource.getConnection();
            sta = con.createStatement();
            res = sta.executeQuery("SELECT Round(( A.space_limit / 1024 / 1024 / 1024 ), 2)       AS FLASH_IN_GB, "
                    + "       Round(( A.space_used / 1024 / 1024 / 1024 ), 2)        AS "
                    + "       FLASH_USED_IN_GB, "
                    + "       Round(( A.space_reclaimable / 1024 / 1024 / 1024 ), 2) AS "
                    + "       FLASH_RECLAIMABLE_GB, "
                    + "       Sum(B.percent_space_used)                              AS "
                    + "       PERCENT_OF_SPACE_USED "
                    + "FROM   v$recovery_file_dest A, "
                    + "       v$flash_recovery_area_usage B "
                    + "GROUP  BY space_limit, "
                    + "          space_used, "
                    + "          space_reclaimable");

            if (res.next()) {
                BigDecimal flashGb = res.getBigDecimal("FLASH_IN_GB");
                BigDecimal flashUsedGb = res.getBigDecimal("FLASH_USED_IN_GB");
                BigDecimal flashReclaimableGb = res.getBigDecimal("FLASH_RECLAIMABLE_GB");
                BigDecimal percentSpaceUsed = res.getBigDecimal("PERCENT_OF_SPACE_USED");
                
                FlashRecoveryArea flash = new FlashRecoveryArea();
                flash.setFlashInGb(flashGb.setScale(4, RoundingMode.HALF_UP));
                flash.setFlashUsedInGb(flashUsedGb.setScale(4, RoundingMode.HALF_UP));
                flash.setFlashReclaimableGb(flashReclaimableGb.setScale(4, RoundingMode.HALF_UP));
                flash.setPercentOfSpaceUsed(percentSpaceUsed.setScale(4, RoundingMode.HALF_UP));
                flash.setData(new Date());
                
                flashRecoveryAreaFacade.doPost(flash);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FlashRecoveryAreaService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (res != null) {
                try {
                    res.close();
                } catch (SQLException ex) {
                    Logger.getLogger(FlashRecoveryAreaService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (sta != null) {
                try {
                    sta.close();
                } catch (SQLException ex) {
                    Logger.getLogger(FlashRecoveryAreaService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(FlashRecoveryAreaService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

}
