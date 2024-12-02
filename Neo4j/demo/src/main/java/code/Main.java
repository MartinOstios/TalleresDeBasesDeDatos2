package code;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        Neo4jConnection.connect();
        PersonaService personaService = new PersonaService();
        Persona persona = new Persona("123", "Juan", 25);
        //personaService.createPersona(persona);
        Persona persona2 = new Persona("456", "Pedro", 30);
        //personaService.createPersona(persona2);
        
        Persona personaObtenida = personaService.obtenerPersona("123");
        System.out.println(personaObtenida.getNombre());
        Neo4jConnection.disconnect();

    }
}