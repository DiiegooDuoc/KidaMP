<?php
header('Content-Type: application/json');

// 1. Obtener los datos JSON que envía la app (esperamos un objeto con "id_cancion")
$json = file_get_contents('php://input');$datos = json_decode($json);

// 2. Comprobar que hemos recibido el ID
if (!isset($datos->id_cancion)) {
    http_response_code(400); // Bad Request
    echo json_encode(['mensaje' => 'Error: No se proporcionó el ID de la canción.']);
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

// 3. Preparar la consulta para eliminar (más seguro)
$stmt = $conexion->prepare("DELETE FROM cancion WHERE id_cancion = ?");

// 4. Vincular el ID a la consulta
$stmt->bind_param("s", $datos->id_cancion); // "s" porque el ID lo estamos manejando como String

// 5. Ejecutar y comprobar el resultado
if ($stmt->execute()) {
    // Comprobar si realmente se borró una fila
    if ($stmt->affected_rows > 0) {
        http_response_code(200); // OK
        echo json_encode(['mensaje' => 'Canción eliminada exitosamente.']);
    } else {
        http_response_code(404); // Not Found
        echo json_encode(['mensaje' => 'Error: No se encontró una canción con ese ID.']);
    }
} else {
    http_response_code(500); // Internal Server Error
    echo json_encode(['mensaje' => 'Error al eliminar la canción.']);
}

// 6. Cerrar todo
$stmt->close();
$conexion->close();
?>