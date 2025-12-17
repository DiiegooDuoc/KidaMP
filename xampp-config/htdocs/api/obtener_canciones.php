<?php


//header para especificar a android que es json

header('Content-Type: application/json');


//datos conexion mysql

$host = "localhost";
$user = "root";
$pass = "";
$db = "musica_db";



//inicializar conexion ala base :D

$conexion = new mysqli($host,$user,$pass,$db);


//checar conexion:
//esta wa es opcional pero pa ver si caga o no
if($conexion -> connect_error){
	die("Error con la conexion a la base: " . $conexion-> connect_error);
}


//hacer las consultas pa despues echarlos en json

$sql = "SELECT * FROM CANCION";
$resultado = $conexion->query($sql);


$datos = array();

if ($resultado -> num_rows > 0){
	while($fila = $resultado -> fetch_assoc()) {
		$datos[] = $fila;
	}
}


//ahora convertir la wa a JSON dou dou

echo json_encode($datos);

$conexion -> close();
?>