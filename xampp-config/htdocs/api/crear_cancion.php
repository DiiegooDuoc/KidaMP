<?php
header('Content-Type: application/json');

// 1. Obtener los datos JSON que envía la app
$json = file_get_contents('php://input');

// 2. Decodificar el JSON a un objeto PHP
$datos = json_decode($json);

// 3. Comprobar que los datos necesarios existen
if (!isset($datos->nombre_cancion) || !isset($datos->autor_cancion) || !isset($datos->album_cancion)) {
    // Si faltan datos, enviar un error
    http_response_code(400); // Bad Request
    echo json_encode(['mensaje' => 'Error: Faltan datos.']);
    exit();
}

// --- Conexión a la base de datos (igual que antes) ---
$host = "localhost";
$user = "root";
$pass = "";
$db = "musica_db";

$conexion = new mysqli($host, $user, $pass, $db);

if ($conexion->connect_error) {
    http_response_code(500); // Internal Server Error
    die(json_encode(['mensaje' => 'Error de conexión a la base de datos.']));
}

// 4. Preparar la consulta para evitar inyección SQL (más seguro)
$stmt = $conexion->prepare("INSERT INTO cancion (nombre_cancion, autor_cancion, album_cancion) VALUES (?, ?, ?)");

// 5. Vincular los datos a la consulta
$stmt->bind_param("sss", $datos->nombre_cancion, $datos->autor_cancion, $datos->album_cancion);

// 6. Ejecutar la consulta y comprobar el resultado
if ($stmt->execute()) {
    http_response_code(201); // Created
    echo json_encode(['mensaje' => 'Canción creada exitosamente.']);
} else {
    http_response_code(500); // Internal Server Error
    echo json_encode(['mensaje' => 'Error al crear la canción.']);
}

// 7. Cerrar todo
$stmt->close();
$conexion->close();
?>