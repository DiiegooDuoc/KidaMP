<?php
header('Content-Type: application/json');

//1. Obtener los datos JSON que envía la app
$json = file_get_contents('php://input');
$datos = json_decode($json);

// 2. Comprobar que todos los datos necesarios existen
if (!isset($datos->id_cancion) || !isset($datos->nombre_cancion) || !isset($datos->autor_cancion) || !isset($datos->album_cancion)) {
    http_response_code(400); // Bad Request
    echo json_encode(['mensaje' => 'Error: Faltan datos para la actualización.']);
    exit();
}

// --- Conexión a la base de datos ---
$host = "localhost";
$user = "root";
$pass = "";
$db = "musica_db";

$conexion = new mysqli($host, $user, $pass, $db);

if ($conexion->connect_error) {
    http_response_code(500); // Internal Server Error
    die(json_encode(['mensaje' => 'Error de conexión a la base de datos.']));
}

// 3. Preparar la consulta para actualizar (más seguro)
$stmt = $conexion->prepare("UPDATE cancion SET nombre_cancion = ?, autor_cancion = ?, album_cancion = ? WHERE id_cancion = ?");

// 4. Vincular los datos a la consulta
// El orden es importante: nombre, autor, album, y al final el ID para el WHERE
$stmt->bind_param("ssss", $datos->nombre_cancion, $datos->autor_cancion, $datos->album_cancion, $datos->id_cancion);

// 5. Ejecutar la consulta y comprobar el resultado
if ($stmt->execute()) {
    if ($stmt->affected_rows > 0) {
        http_response_code(200); // OK
        echo json_encode(['mensaje' => 'Canción actualizada exitosamente.']);
    } else {
        // Esto puede pasar si los datos enviados son idénticos a los que ya estaban en la BD
        http_response_code(200); // OK
        echo json_encode(['mensaje' => 'No se realizaron cambios, los datos eran los mismos.']);
    }
} else {
    http_response_code(500); // Internal Server Error
    echo json_encode(['mensaje' => 'Error al actualizar la canción.']);
}

// 6. Cerrar todo
$stmt->close();
$conexion->close();
?>