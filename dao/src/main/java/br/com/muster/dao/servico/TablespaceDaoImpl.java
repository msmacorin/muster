/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muster.dao.servico;

import br.com.muster.dao.Dao;
import br.com.muster.model.enums.EEnvio;
import br.com.muster.model.servico.Tablespace;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.ejb.Stateless;
import org.joda.time.LocalDate;

/**
 *
 * @author macorin
 */
@Stateless
public class TablespaceDaoImpl extends Dao<Tablespace, UUID> implements TablespaceDao {
    private static final long serialVersionUID = -3922183154209465023L;
    
    private final String SELECT_SITUACAO_ENVIO = "SELECT r FROM Tablespace r "
            + " WHERE r.envio = :envio";
    
    private final String SELECT_SITUACAO_ENVIO_DATA = "SELECT r FROM Tablespace r "
            + " WHERE r.envio = :envio "
            + " AND r.data < :data ";
    
    private final String SELECT_PERIOD="SELECT r "
            + "FROM Tablespace r "
            + "WHERE r.empresa.id = :idEmpresa "
            + "AND r.data BETWEEN :inicio AND :fim "
            + "ORDER BY r.data, r.tablespaceName";
    
    @Override
    public List<Tablespace> registrosPendentes() {
        try {
            params.put("envio", EEnvio.Pendente);
            return select(SELECT_SITUACAO_ENVIO);
        } finally {
            params.clear();
        }
    }

    @Override
    public List<Tablespace> registrosEnviadosAntigos() {
        try {
            params.put("envio", EEnvio.Enviado);
            
            LocalDate date = new LocalDate();
            date = date.minusMonths(1);
            
            params.put("data", date.toDate());
            
            return select(SELECT_SITUACAO_ENVIO_DATA);
        } finally {
            params.clear();
        }
    }

    @Override
    public List<Tablespace> selecionarPorPeriodo(UUID idEmpresa, Date inicio, Date fim) {
        try {
            params.put("idEmpresa", idEmpresa);
            params.put("inicio", inicio);
            params.put("fim", fim);
            
            return select(SELECT_PERIOD);
        } finally {
            params.clear();
        }
    }
}
