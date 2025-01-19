package edu.upc.dsa.orm.dao;

import edu.upc.dsa.models.Denuncia;
import edu.upc.dsa.orm.dao.DenunciaDAO;
import edu.upc.dsa.orm.FactorySession;
import edu.upc.dsa.orm.Session;

import java.util.List;

public class DenunciaDAOImpl implements DenunciaDAO {

    public DenunciaDAOImpl(Session session) {
    }


    @Override
    public String addDenuncia(String title, String message, String sender) {
        Session session = null;
        int result = 0;
        try {
            session = FactorySession.openSession();
            Denuncia denuncia = new Denuncia();
            denuncia.setTitle(title);
            denuncia.setMessage(message);
            denuncia.setSender(sender);
            result = session.save(denuncia);
        } catch (Exception e) {
            return "Error";
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return result == -1 ? "Error" : "Success";
    }

    @Override
    public void updateDenuncia(String id, String title, String message) {
        Session session = null;
        try {
            session = FactorySession.openSession();
            Denuncia denuncia = (Denuncia) session.get(Denuncia.class, id);
            if (denuncia != null) {
                denuncia.setTitle(title);
                denuncia.setMessage(message);
                session.update(denuncia);
            }
        } catch (Exception e) {
            // LOG
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void deleteDenunciaByID(String id) {
        Session session = null;
        try {
            session = FactorySession.openSession();
            Denuncia denuncia = (Denuncia) session.get(Denuncia.class, id);
            if (denuncia != null) {
                session.delete(denuncia);
            }
        } catch (Exception e) {
            // LOG
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Denuncia> getDenuncias() {
        Session session = null;
        List<Denuncia> denuncias = null;
        try {
            session = FactorySession.openSession();
            denuncias = session.findAll(Denuncia.class);
        } catch (Exception e) {
            // LOG
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return denuncias;
    }

    @Override
    public Denuncia getDenunciaByID(String id) {
        Session session = null;
        Denuncia denuncia = null;
        try {
            session = FactorySession.openSession();
            denuncia = (Denuncia) session.get(Denuncia.class, id);
        } catch (Exception e) {
            // LOG
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return denuncia;
    }
}
