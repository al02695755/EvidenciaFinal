import java.util.Date;


public class Cita {

    int id;
    Date fechaCita;
    int idDoctor;
    int idPaciente;
    String motivoCita;

    public Cita (int idC, Date fecha, int doctorId, int pacienteId, String motivo){
        id = idC;
        fechaCita = fecha;
        idDoctor = doctorId;
        idPaciente = pacienteId;
        motivoCita = motivo;
    }


}