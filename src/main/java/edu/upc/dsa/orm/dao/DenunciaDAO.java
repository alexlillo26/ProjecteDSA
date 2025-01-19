package edu.upc.dsa.orm.dao;

import java.util.List;
import edu.upc.dsa.models.Denuncia;

public interface DenunciaDAO {
    public String addDenuncia(String title, String message, String sender);
    public void updateDenuncia(String id, String title, String message);
    public void deleteDenunciaByID(String id);
    public List<Denuncia> getDenuncias();
    public Denuncia getDenunciaByID(String id);
}
