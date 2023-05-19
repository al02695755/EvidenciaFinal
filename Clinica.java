import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.Iterator;
import java.util.HashMap;


public class Clinica {

    public static Scanner leer = new Scanner(System.in);
    //creamos las variables para tener acceso a los archivos csv
    public static String outputDoctores = "C:\\Users\\JAIR\\Documents\\4to semestre universidad\\Computacion en java\\ProyectoFinal\\src\\Doctores.csv";
    public static String outputPacientes = "C:\\Users\\JAIR\\Documents\\4to semestre universidad\\Computacion en java\\ProyectoFinal\\src\\Pacientes.csv";
    public static String outputCitas = "C:\\Users\\JAIR\\Documents\\4to semestre universidad\\Computacion en java\\ProyectoFinal\\src\\Citas.csv";
    public static String outputAdministradores = "C:\\Users\\JAIR\\Documents\\4to semestre universidad\\Computacion en java\\ProyectoFinal\\src\\Administradores.csv";

    //Creamos un arraylist para el paciente y doctor
    public static ArrayList <Doctor> aDoc = new ArrayList<Doctor>();
    public static ArrayList <Paciente> aPac = new ArrayList<Paciente>();


    public static void main(String[] args) throws IOException, ParseException{


        //Llamamos la clase bufferwritter para poder leer los archivos
        BufferedWriter bwDoctores = new BufferedWriter(new FileWriter(outputDoctores, true));
        BufferedWriter bwPacientes = new BufferedWriter(new FileWriter(outputPacientes, true));
        BufferedWriter bwCitas = new BufferedWriter(new FileWriter(outputCitas, true));
        BufferedWriter bwAdmin = new BufferedWriter(new FileWriter(outputAdministradores, true));

        leer.useDelimiter("\n");
        //creamos variables de apoyo
        int opc, ban = 0, ban1 = 0;
        boolean acceso = false;

        //creamos el hash map para los administradores
        HashMap<String, String> mapa = new HashMap<String, String>();

        System.out.println("**** Bienvenido al sistema de Clinica ****\n");

        do{     // bucle do-while para verificar que el usuario sea un administrador del sistema
            ban1 = archivV("C:\\Users\\JAIR\\Documents\\4to semestre universidad\\Computacion en java\\ProyectoFinal\\src\\Administradores.csv"); //llamado a metodo que rectifica que el archivo no esté vacio
            if(ban1 == 1)
            {
                System.out.println("****Ingreso para administradores****\n");
                System.out.println("Ingrese su ID: ");
                String id = leer.next();
                System.out.println("Ingrese su contraseña: ");
                String pass = leer.next();

                // metodo que carga los datos del archivo a un hashmap
                load(mapa);

                //la variable acceso recibe el valor que devuelve el metodo que evalúa el id y la contraseña
                acceso = contrasena(mapa,id,pass);

            }
            else/// si el archivo de administradores esta vacío se da de alta uno por default
            {
                System.out.println("No hay Administradores dados de alta");
                System.out.println("**** Alta de Administrador ****\n");
                System.out.println("Ingrese su ID: ");
                String id = leer.next();
                System.out.println("Ingrese su contraseña: ");
                String pass = leer.next();

                // llamado a metodo que da de alta administrador y lo guarda en el archivo
                creaAdministrador(mapa, id, pass, bwAdmin);

            }
            // el ciclo continúa hasta que la contraseña y id sean correctos
        }while ( acceso == false );

        do          // ciclo do while para control del menu, es controlado con la variable ban
        {
            //Try para iniciar excepcion de errores
            try{

                // menu de sistema
                System.out.println("\nSeleccione la opción deseada:\n");
                System.out.println("[1] Alta de Administrador");
                System.out.println("[2] Alta de Doctor ");
                System.out.println("[3] Alta Paciente");
                System.out.println("[4] Agendar Cita");
                System.out.println("[5] Verificar citas");

                System.out.println("[0] Salida");

                opc = leer.nextInt();

                /// Menu de opciones
                switch(opc)
                {
                    case 1:

                        System.out.println("**** Alta de Administrador ****\n");
                        System.out.println("Ingrese su ID: ");
                        String id = leer.next();
                        System.out.println("Ingrese su contraseña: ");
                        String pass = leer.next();
                        // llamado a metodo que da de alta administrador y lo guarda en el archivo
                        creaAdministrador(mapa, id, pass, bwAdmin);

                        break;

                    case 2:
                        creaDoctor(bwDoctores);
                        break;

                    case 3:

                        creaPaciente(bwPacientes);

                        break;

                    case 4:

                        creaCita(bwCitas);

                        break;
                    case 5:

                        break;

                    case 0:

                        // opcion de salida del programa
                        System.out.println("Saliendo");
                        // se cambia el valor de la bandera que controla el ciclo do-while del men
                        ban = 1;
                        break;
                    default:
                        //default que muestra un mensaje de error al introducir una opcion incorrecta
                        System.out.println("Opción incorrecta\n");
                        break;


                }
            }
            // catch que obtiene el error y nos muestra un mensaje de error en caso de que se presente uno
            catch (Exception e)
            {
                System.out.println("ERROR !!\n");
                break;

            }
        }while(ban == 0);

    }

    // Metodo para crear un doctor y guardarlo en el archivo Doctores
    public static void creaDoctor(BufferedWriter bw) throws IOException {

        // Se piden los datos del objeto
        System.out.print("Ingresa el nombre del doctor\n");
        String nombreDoctor = leer.next();
        System.out.print("Ingresa la especialidad del doctor\n");
        String especialidadDoctor = leer.next();
        System.out.print("Ingresa el Id del doctor\n");
        String idDoctor = leer.next();
        // se crea el objeto doctor con los datos ingresados por usuario
        Doctor doctorInfo = new Doctor(idDoctor, nombreDoctor, especialidadDoctor);


        // se crean variables auxiliares
        try(FileWriter fw = new FileWriter(outputDoctores, true);
            BufferedWriter bww = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bww)){

            // se escriben los datos en el archivo
            out.print(doctorInfo.id);
            out.print(",");
            out.print(doctorInfo.nombreDoctor);
            out.print(",");
            out.println(doctorInfo.esp);

        }

        catch(IOException e) {
            System.out.println("IOException catched while writing: " + e.getMessage());
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                    System.out.println("\nCambios guardados");
                }
            } catch (IOException e) {
                System.out.println("IOException catched while closing: " + e.getMessage());
            }
        }

    }
    //Metodo crear paciente
    public static void creaPaciente(BufferedWriter bw) throws IOException {

        // Se piden los datos del objeto
        System.out.print("Ingresa el nombre del Paciente\n");
        String nombrePaciente = leer.next();
        System.out.print("Ingresa el Id del paciente\n");
        String idPaciente = leer.next();
        // se crea el objeto doctor con los datos ingresados por usuario
        Paciente PacienteInfo = new Paciente(nombrePaciente,idPaciente);

        // se crean variables auxiliares
        try(FileWriter fw = new FileWriter(outputPacientes, true);
            BufferedWriter bww = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bww)){


            // se escriben los datos en el archivo
            out.print(PacienteInfo.id);
            out.print(",");
            out.println(PacienteInfo.nombrePaciente);


        }
        catch(IOException e) {
            System.out.println("IOException catched while writing: " + e.getMessage());
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                    System.out.println("\nCambios guardados");
                }
            } catch (IOException e) {
                System.out.println("IOException catched while closing: " + e.getMessage());
            }
        }

    }
    //Metodo crear cita
    public static void creaCita(BufferedWriter bw) throws IOException {

        try{
            System.out.println("Ingrese el id de la cita:");
            int idC = leer.nextInt();
            System.out.print("Ingresa la fecha de tu cita\n");
            String fechaCitaString = leer.next();
            Date fechaCita = new SimpleDateFormat("dd/MM/yyyy").parse(fechaCitaString);
            System.out.print("Ingresa el motivo de tu cita\n");
            leer.nextLine();
            String motivoCita = leer.nextLine();
            System.out.print("Ingresa el id del doctor\n");
            String idDoctor = leer.next();
            System.out.print("Ingresa el id del paciente\n");
            String idPaciente = leer.next();
            Cita citaInfo = new Cita(idC, fechaCita, Integer.parseInt(idDoctor), Integer.parseInt(idPaciente), motivoCita);


            // se crean variables auxiliares
            try(FileWriter fw = new FileWriter(outputCitas, true);
                BufferedWriter bww = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bww)){


                // se escriben los datos en el archivo
                out.print(citaInfo.id);
                out.print(",");
                out.println(citaInfo.fechaCita);
                out.print(",");
                out.print(citaInfo.idDoctor);
                out.print(",");
                out.print(citaInfo.idPaciente);
                out.print(",");
                out.println(citaInfo.motivoCita);



            }
            catch(IOException e) {
                System.out.println("IOException catched while writing: " + e.getMessage());
            } finally {
                try {
                    if (bw != null) {
                        bw.close();
                        System.out.println("\nCambios guardados");
                    }
                } catch (IOException e) {
                    System.out.println("IOException catched while closing: " + e.getMessage());
                }
            }


        }
        catch(ParseException e) {
            System.out.println("ParseException catched while writing: " + e.getMessage());
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                System.out.println("IOException catched while closing: " + e.getMessage());
            }
        }

    }

    public static int archivV(String archivo)
    {
        int i = 0;

        try
        {
            BufferedReader br = new BufferedReader(new FileReader(archivo));
            if (br.readLine() == null)
            {
                System.out.println("file empty");
            }

            else i = 1;
        }
        catch(IOException e) {
            System.out.println("IOException catched while writing: " + e.getMessage());
        }
        return i;
    }



    //Metodo para crear y guardar administrador
    public static void creaAdministrador(HashMap <String,String> mapa, String id, String pw, BufferedWriter bw)throws IOException{


        if(mapa.containsKey(id))
        {
            // mensaje si el administrador ya existe
            System.out.println("\nError!\nNo se puede registrar dos veces el mismo Administrador\n");
        }

        else{
            //si no existe se crea el administrador
            mapa.put(id, pw);
            System.out.println("\nAdministrador agregado");
        }

        Iterator<String> iterator = mapa.keySet().iterator();
        String inputFilename = "C:\\Users\\JAIR\\Documents\\4to semestre universidad\\Computacion en java\\ProyectoFinal\\src\\Administradores.csv";

        BufferedWriter bufferedWriter = null;

        try {
            bufferedWriter = new BufferedWriter(new FileWriter(inputFilename));

            //en un ciclo recorremos el hashmap
            while(iterator.hasNext())
            {
                // el iterador se llama llave que esta funcionando como referencia en el key del hashmap
                String llave = iterator.next();


                // Se escribe el hashmap en el archivo input
                bufferedWriter.write(llave+","+mapa.get(llave)+"\n");
            }

        }
        catch(IOException e) {
            System.out.println("IOException catched while writing: " + e.getMessage());
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                    System.out.println("\nCambios guardados");
                }
            } catch (IOException e) {
                System.out.println("IOException catched while closing: " + e.getMessage());
            }
        }

    }

    //Metodo para validar contraseña
    public static boolean contrasena(HashMap <String,String> mapa, String id, String pw)
    {

        boolean i ;

        if(mapa.containsKey(id))
        {
            if(mapa.get(id).equals(pw))
                i = true;

            else
            {
                System.out.println("La contraseña es incorrecta!\n");

                i = false;
            }
        }

        else
        {
            System.out.println("El administrador no existe!\n");
            i = false;
        }

        return i;
    }


//Metodo para cargar Hashmap que controla los auxiliares desde archivo csv
    public static void load(HashMap<String, String> m)
    {
        String inputFilename = "C:\\Users\\JAIR\\Documents\\4to semestre universidad\\Computacion en java\\ProyectoFinal\\src" +
                "\\Administradores.csv";
        String a [];                /// array auxiliar

        BufferedReader bufferedReader = null;

        try {
            bufferedReader = new BufferedReader(new FileReader(inputFilename));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                // se llena el array con los datos separados por la ","
                a = line.split(",");
                // se llena el hashmap con los datos del array
                m.put(a[0],a[1]);
            }
        } catch(IOException e) {
            System.out.println("IOException catched while reading: " + e.getMessage());
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                System.out.println("IOException catched while closing: " + e.getMessage());
            }
        }

    }

    //Metodo para mostrar pacientes
    public static void loadPaciente(ArrayList Pac)
    {
        String inputFilename = outputPacientes;
        String a [];

        BufferedReader bufferedReader = null;

        try {
            bufferedReader = new BufferedReader(new FileReader(inputFilename));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                // se llena el array con los datos separados por la ","
                a = line.split(",");
                // se llena el arraylist con los datos del array
                Pac.add(a[0]);
                Pac.add(a[1]);
            }
        } catch(IOException e) {
            System.out.println("IOException catched while reading: " + e.getMessage());
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                System.out.println("IOException catched while closing: " + e.getMessage());
            }
        }
    }

    public static void loadDoctor(ArrayList Doc)
    {
        String inputFilename = outputDoctores;
        String a [];

        BufferedReader bufferedReader = null;

        try {
            bufferedReader = new BufferedReader(new FileReader(inputFilename));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                // se llena el array con los datos separados por la ","
                a = line.split(",");
                // se llena el arraylist con los datos del array
                Doc.add(a[0]);
                Doc.add(a[1]);
                Doc.add(a[2]);
            }
        } catch(IOException e) {
            System.out.println("IOException catched while reading: " + e.getMessage());
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                System.out.println("IOException catched while closing: " + e.getMessage());
            }
        }
    }

}