package main;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import models.*;
import services.*;
import java.util.Scanner;
import java.util.Date;
import java.util.List;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static ProductoService productoService;
    private static PedidoService pedidoService;
    private static DetallePedidoService detallePedidoService;
    private static ReservaService reservaService;
    private static PersonaService personaService;

    public static void main(String[] args) {
        inicializarConexiones();

        while (true) {
            System.out.println("\n----PARCIAL----");
            System.out.println("1. Esquema Normalizado");
            System.out.println("2. Esquema Desnormalizado");
            System.out.println("3. Grafos");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    menuEsquemaNormalizado();
                    break;
                case 2:
                    menuEsquemaDesnormalizado();
                    break;
                case 3:
                    menuGrafos();
                    break;
                case 4:
                    cerrarConexiones();
                    System.out.println("¡Hasta luego!");
                    return;
            }
        }
    }

    private static void inicializarConexiones() {
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        database = mongoClient.getDatabase("parcial_db");
        productoService = new ProductoService(database);
        pedidoService = new PedidoService(database);
        detallePedidoService = new DetallePedidoService(database);
        reservaService = new ReservaService(database);
        try {
            personaService = new PersonaService("neo4j://localhost:7687", "neo4j", "martin123");
        } catch (Exception e) {
            System.err.println("Error al conectar con Neo4j: " + e.getMessage());
        }
    }

    private static void cerrarConexiones() {
        if (mongoClient != null) mongoClient.close();
        if (personaService != null) personaService.close();
    }

    private static void menuEsquemaNormalizado() {
        System.out.println("\nEsquema Normalizado");
        System.out.println("1. CRUD de Productos");
        System.out.println("2. CRUD de Pedidos");
        System.out.println("3. CRUD de Detalles de Pedidos");
        System.out.println("4. Obtener productos con precio mayor a 20");
        System.out.println("5. Obtener los pedidos que tengan un total mayor a 100 dolares");
        System.out.println("6. Obtener los pedidos en donde exista un detalle de pedido con el producto 010");
        System.out.print("Seleccione una opción: ");
        
        int opcion = scanner.nextInt();
        scanner.nextLine();

        switch (opcion) {
            case 1:
                menuCRUDProductos();
                break;
            case 2:
                menuCRUDPedidos();
                break;
            case 3:
                menuCRUDDetallesPedidos();
                break;
            case 4:
                obtenerProductosMayorA20();
                break;
            case 5:
                obtenerPedidosMayorA100();
                break;
            case 6:
                obtenerPedidosConProducto010();
                break;
        }
    }

    private static void menuCRUDProductos() {
        System.out.println("\nCRUD de Productos");
        System.out.println("1. Crear producto");
        System.out.println("2. Leer producto");
        System.out.println("3. Actualizar producto");
        System.out.println("4. Eliminar producto");
        System.out.print("Seleccione una opción: ");
        
        int opcion = scanner.nextInt();
        scanner.nextLine();

        switch (opcion) {
            case 1:
                System.out.println("Ingrese los datos del producto:");
                System.out.print("ID: ");
                String id = scanner.nextLine();
                System.out.print("Nombre: ");
                String nombre = scanner.nextLine();
                System.out.print("Descripción: ");
                String descripcion = scanner.nextLine();
                System.out.print("Precio: ");
                double precio = scanner.nextDouble();
                System.out.print("Stock: ");
                int stock = scanner.nextInt();

                Producto producto = new Producto(id, nombre, descripcion, precio, stock);
                productoService.create(producto);
                System.out.println("Producto creado exitosamente");
                break;

            case 2:
                System.out.print("Ingrese el ID del producto: ");
                String idBuscar = scanner.nextLine();
                Producto productoEncontrado = productoService.findById(idBuscar);
                if (productoEncontrado != null) {
                    System.out.println("Producto encontrado:");
                    System.out.println("ID: " + productoEncontrado.getId());
                    System.out.println("Nombre: " + productoEncontrado.getNombre());
                    System.out.println("Descripción: " + productoEncontrado.getDescripcion());
                    System.out.println("Precio: " + productoEncontrado.getPrecio());
                    System.out.println("Stock: " + productoEncontrado.getStock());
                } else {
                    System.out.println("Producto no encontrado");
                }
                break;

            case 3:
                break;

            case 4:
                System.out.print("Ingrese el ID del producto a eliminar: ");
                String idEliminar = scanner.nextLine();
                productoService.delete(idEliminar);
                System.out.println("Producto eliminado exitosamente");
                break;
        }
    }

    private static void menuCRUDPedidos() {
        System.out.println("\nCRUD de Pedidos");
        System.out.println("1. Crear pedido");
        System.out.println("2. Leer pedido");
        System.out.println("3. Actualizar pedido");
        System.out.println("4. Eliminar pedido");
        System.out.print("Seleccione una opción: ");
        
        int opcion = scanner.nextInt();
        scanner.nextLine();

        switch (opcion) {
            case 1:
                System.out.println("Ingrese los datos del pedido:");
                System.out.print("ID: ");
                String id = scanner.nextLine();
                System.out.print("Cliente: ");
                String cliente = scanner.nextLine();
                System.out.print("Estado (PENDIENTE/COMPLETADO): ");
                String estado = scanner.nextLine();
                System.out.print("Total: ");
                double total = scanner.nextDouble();

                Pedido pedido = new Pedido(id, cliente, new Date(), estado, total);
                pedidoService.create(pedido);
                System.out.println("Pedido creado exitosamente");
                break;

            case 2:
                System.out.print("Ingrese el ID del pedido: ");
                String idBuscar = scanner.nextLine();
                Pedido pedidoEncontrado = pedidoService.findById(idBuscar);
                if (pedidoEncontrado != null) {
                    System.out.println("Pedido encontrado:");
                    System.out.println("ID: " + pedidoEncontrado.getId());
                    System.out.println("Cliente: " + pedidoEncontrado.getCliente());
                    System.out.println("Fecha: " + pedidoEncontrado.getFechaPedido());
                    System.out.println("Estado: " + pedidoEncontrado.getEstado());
                    System.out.println("Total: $" + pedidoEncontrado.getTotal());
                } else {
                    System.out.println("Pedido no encontrado");
                }
                break;

            case 3:
                System.out.println("Ingrese el ID del pedido a actualizar:");
                String idActualizar = scanner.nextLine();
                Pedido pedidoExistente = pedidoService.findById(idActualizar);
                
                if (pedidoExistente != null) {
                    System.out.print("Nuevo cliente (Enter para mantener actual): ");
                    String nuevoCliente = scanner.nextLine();
                    if (!nuevoCliente.isEmpty()) {
                        pedidoExistente.setCliente(nuevoCliente);
                    }

                    System.out.print("Nuevo estado (Enter para mantener actual): ");
                    String nuevoEstado = scanner.nextLine();
                    if (!nuevoEstado.isEmpty()) {
                        pedidoExistente.setEstado(nuevoEstado);
                    }

                    System.out.print("Nuevo total (0 para mantener actual): ");
                    double nuevoTotal = scanner.nextDouble();
                    if (nuevoTotal > 0) {
                        pedidoExistente.setTotal(nuevoTotal);
                    }

                    pedidoService.update(pedidoExistente);
                    System.out.println("Pedido actualizado exitosamente");
                } else {
                    System.out.println("Pedido no encontrado");
                }
                break;

            case 4:
                System.out.print("Ingrese el ID del pedido a eliminar: ");
                String idEliminar = scanner.nextLine();
                pedidoService.delete(idEliminar);
                System.out.println("Pedido eliminado exitosamente");
                break;
        }
    }

    private static void menuCRUDDetallesPedidos() {
        System.out.println("\nCRUD de Detalles de Pedidos");
        System.out.println("1. Crear detalle de pedido");
        System.out.println("2. Leer detalle de pedido");
        System.out.println("3. Actualizar detalle de pedido");
        System.out.println("4. Eliminar detalle de pedido");
        System.out.print("Seleccione una opción: ");
        
        int opcion = scanner.nextInt();
        scanner.nextLine();

        switch (opcion) {
            case 1:
                System.out.println("Ingrese los datos del detalle de pedido:");
                System.out.print("ID: ");
                String id = scanner.nextLine();
                System.out.print("ID del pedido: ");
                String pedidoId = scanner.nextLine();
                System.out.print("ID del producto: ");
                String productoId = scanner.nextLine();
                System.out.print("Cantidad: ");
                int cantidad = scanner.nextInt();
                System.out.print("Precio unitario: ");
                double precioUnitario = scanner.nextDouble();

                DetallePedido detalle = new DetallePedido(id, pedidoId, productoId, cantidad, precioUnitario);
                detallePedidoService.create(detalle);
                System.out.println("Detalle de pedido creado exitosamente");
                break;

            case 2:
                System.out.print("Ingrese el ID del detalle de pedido: ");
                String idBuscar = scanner.nextLine();
                DetallePedido detalleEncontrado = detallePedidoService.findById(idBuscar);
                if (detalleEncontrado != null) {
                    System.out.println("Detalle de pedido encontrado:");
                    System.out.println("ID: " + detalleEncontrado.getId());
                    System.out.println("ID Pedido: " + detalleEncontrado.getPedidoId());
                    System.out.println("ID Producto: " + detalleEncontrado.getProductoId());
                    System.out.println("Cantidad: " + detalleEncontrado.getCantidad());
                    System.out.println("Precio unitario: $" + detalleEncontrado.getPrecioUnitario());
                } else {
                    System.out.println("Detalle de pedido no encontrado");
                }
                break;

            case 3:
                break;
            case 4:
                System.out.print("Ingrese el ID del detalle de pedido a eliminar: ");
                String idEliminar = scanner.nextLine();
                detallePedidoService.delete(idEliminar);
                System.out.println("Detalle de pedido eliminado exitosamente");
                break;
        }
    }

    private static void obtenerProductosMayorA20() {
        List<Producto> productos = productoService.findByPrecioMayorA(20);
        System.out.println("\nProductos con precio mayor a $20:");
        for (Producto p : productos) {
            System.out.println("ID: " + p.getId() + " - Nombre: " + p.getNombre() + " - Precio: $" + p.getPrecio());
        }
    }

    private static void obtenerPedidosMayorA100() {
        List<Pedido> pedidos = pedidoService.findByTotalMayorA(100);
        System.out.println("\nPedidos con total mayor a $100:");
        for (Pedido p : pedidos) {
            System.out.println("ID: " + p.getId() + " - Cliente: " + p.getCliente() + " - Total: $" + p.getTotal());
        }
    }

    private static void obtenerPedidosConProducto010() {
        List<DetallePedido> detalles = detallePedidoService.findByProductoId("producto010");
        System.out.println("\nPedidos con el producto010:");
        for (DetallePedido dp : detalles) {
            Pedido p = pedidoService.findById(dp.getPedidoId());
            if (p != null) {
                System.out.println("Pedido ID: " + p.getId() + " - Cliente: " + p.getCliente());
            }
        }
    }

    private static void menuEsquemaDesnormalizado() {
        System.out.println("\nEsquema Desnormalizado");
        System.out.println("1. CRUD de Reservas");
        System.out.println("2. Obtener reservas de tipo Sencilla");
        System.out.println("3. Obtener total de reservas pagadas");
        System.out.println("4. Obtener reservas con precio por noche mayor a 100");
        System.out.print("Seleccione una opción: ");
        
        int opcion = scanner.nextInt();
        scanner.nextLine();

        switch (opcion) {
            case 1:
                menuCRUDReservas();
                break;
            case 2:
                List<Reserva> reservasSencillas = reservaService.findByTipoHabitacion("Sencilla");
                for (Reserva r : reservasSencillas) {
                    System.out.println("Reserva ID: " + r.getId() + 
                                     " - Cliente: " + r.getCliente().getNombre() + 
                                     " - Habitación: " + r.getHabitacion().getNumero());
                }
                break;
            case 3:
                double total = reservaService.getSumaTotalReservasPagadas();
                System.out.println("Total de reservas pagadas: $" + total);
                break;
            case 4:
                List<Reserva> reservasCaras = reservaService.findByPrecioNocheMayorA(100.0);
                for (Reserva r : reservasCaras) {
                    System.out.println("Reserva ID: " + r.getId() + 
                                     " - Cliente: " + r.getCliente().getNombre() +
                                     " - Precio/Noche: $" + r.getHabitacion().getPrecioNoche());
                }
                break;
        }
    }

    private static void menuCRUDReservas() {
        System.out.println("\nCRUD de Reservas");
        System.out.println("1. Crear reserva");
        System.out.println("2. Leer reserva");
        System.out.println("3. Actualizar reserva");
        System.out.println("4. Eliminar reserva");
        System.out.print("Seleccione una opción: ");
        
        int opcion = scanner.nextInt();
        scanner.nextLine();

        switch (opcion) {
            case 1:
                System.out.println("Ingrese los datos del cliente:");
                System.out.print("Nombre: ");
                String nombre = scanner.nextLine();
                System.out.print("Correo: ");
                String correo = scanner.nextLine();
                System.out.print("Teléfono: ");
                String telefono = scanner.nextLine();
                System.out.print("Dirección: ");
                String direccion = scanner.nextLine();

                Cliente cliente = new Cliente(nombre, correo, telefono, direccion);

                System.out.println("\nIngrese los datos de la habitación:");
                System.out.print("Tipo: ");
                String tipo = scanner.nextLine();
                System.out.print("Número: ");
                int numero = scanner.nextInt();
                System.out.print("Precio por noche: ");
                double precioNoche = scanner.nextDouble();
                scanner.nextLine();
                System.out.print("Capacidad: ");
                int capacidad = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Descripción: ");
                String descripcionHab = scanner.nextLine();

                Habitacion habitacion = new Habitacion(tipo, numero, precioNoche, capacidad, descripcionHab);

                Reserva reserva = new Reserva(
                    "reserva" + System.currentTimeMillis(),
                    cliente,
                    habitacion,
                    new Date(),
                    new Date(),
                    precioNoche,
                    "PENDIENTE",
                    "EFECTIVO",
                    new Date()
                );

                reservaService.create(reserva);
                System.out.println("Reserva creada exitosamente");
                break;

            case 2:
                System.out.print("Ingrese el ID de la reserva: ");
                String idBuscar = scanner.nextLine();
                Reserva reservaEncontrada = reservaService.findById(idBuscar);
                if (reservaEncontrada != null) {
                    System.out.println("Reserva encontrada:");
                    System.out.println("ID: " + reservaEncontrada.getId());
                    System.out.println("Cliente: " + reservaEncontrada.getCliente().getNombre());
                    System.out.println("Habitación: " + reservaEncontrada.getHabitacion().getNumero());
                    System.out.println("Total: $" + reservaEncontrada.getTotal());
                } else {
                    System.out.println("Reserva no encontrada");
                }
                break;

            case 3:
                break;

            case 4:
                System.out.print("Ingrese el ID de la reserva a eliminar: ");
                String idEliminar = scanner.nextLine();
                reservaService.delete(idEliminar);
                System.out.println("Reserva eliminada exitosamente");
                break;
        }
    }

    private static void menuGrafos() {
        System.out.println("\nGrafos");
        System.out.println("1. Crear persona");
        System.out.println("2. Crear comentario entre personas");
        System.out.println("3. Ver comentarios de una persona");
        System.out.print("Seleccione una opción: ");
        
        int opcion = scanner.nextInt();
        scanner.nextLine();

        switch (opcion) {
            case 1:
                System.out.println("Ingrese los datos de la persona:");
                System.out.print("Nombre: ");
                String nombre = scanner.nextLine();
                System.out.print("Correo: ");
                String correo = scanner.nextLine();
                System.out.print("Edad: ");
                int edad = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Ciudad: ");
                String ciudad = scanner.nextLine();

                Persona persona = new Persona(nombre, correo, edad, ciudad);
                personaService.crearPersona(persona);
                System.out.println("Persona creada exitosamente");
                break;

            case 2:
                System.out.print("ID de la persona que comenta: ");
                String idOrigen = scanner.nextLine();
                System.out.print("ID de la persona que recibe el comentario: ");
                String idDestino = scanner.nextLine();
                System.out.print("Comentario: ");
                String comentario = scanner.nextLine();

                personaService.crearComentario(idOrigen, idDestino, comentario);
                System.out.println("Comentario creado exitosamente");
                break;

            case 3:
                System.out.print("ID de la persona: ");
                String idPersona = scanner.nextLine();
                System.out.println("\nComentarios realizados por la persona:");
                personaService.obtenerComentariosDePersona(idPersona);
                break;
        }
    }
}
