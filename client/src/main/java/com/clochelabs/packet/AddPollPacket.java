package com.clochelabs.packet;

public class AddPollPacket extends Packet{
    private String choix1;
    private String choix2;
    private String intitule;
    private String dateFin;
    private String dateDebut;


    public AddPollPacket(String choix1, String choix2, String intitule, String dateFin, String dateDebut) {
        super(PacketType.ADDPOLL);
        this.choix1 = choix1;
        this.choix2 = choix2;
        this.intitule = intitule;
        this.dateFin = dateFin;
        this.dateDebut = dateDebut;
    }

    public String getChoix1() {
        return choix1;
    }

    public String getChoix2() {
        return choix2;
    }

    public String getIntitule() {
        return intitule;
    }

    public String getDateFin() {
        return dateFin;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    @Override
    public String toString() {
        return "AddPollPacket{" +
                "choix1='" + choix1 + '\'' +
                ", choix2='" + choix2 + '\'' +
                ", intitule='" + intitule + '\'' +
                ", dateFin=" + dateFin +
                ", dateDebut=" + dateDebut +
                '}';
    }
}
