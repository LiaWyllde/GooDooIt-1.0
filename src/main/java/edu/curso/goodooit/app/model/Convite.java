package edu.curso.goodooit.app.model;

public class Convite {
    private Integer remetenteID;
    private Integer projetoID;
    private Integer destinatarioID;

    public Convite() {}

    public Convite(Integer remetenteID, Integer projetoID, Integer destinatarioID) {
        this.remetenteID = remetenteID;
        this.projetoID = projetoID;
        this.destinatarioID = destinatarioID;
    }

    public Integer getRemetenteID() {
        return remetenteID;
    }

    public void setRemetenteID(Integer remetenteID) {
        this.remetenteID = remetenteID;
    }

    public Integer getProjetoID() {
        return projetoID;
    }

    public void setProjetoID(Integer projetoID) {
        this.projetoID = projetoID;
    }

    public Integer getDestinatarioID() {
        return destinatarioID;
    }

    public void setDestinatarioID(Integer destinatarioID) {
        this.destinatarioID = destinatarioID;
    }

    @Override
    public String toString() {
        return "Convite{" +
                "remetenteID=" + remetenteID +
                ", projetoID=" + projetoID +
                ", destinatarioID=" + destinatarioID +
                '}';
    }
}
