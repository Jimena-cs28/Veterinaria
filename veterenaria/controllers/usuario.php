<?php

require_once '../models/veterenaria.php';

if (isset($_POST['operacion'])){
   
 $veterinaria = new Veterinaria();

  if($_POST['operacion'] == 'login'){
    //buscamos al usuario a traves de su nombre
    $datosObtenidos = $veterinaria->login($_POST['dni']);
    //arreglo que contiene datos de login
    $resultado = [
      "status" => false,
      "nombres" => "",
      "mensaje" => ""
    ];

    if($datosObtenidos) {
      //encontramos el registro
      $claveEncriptada = $datosObtenidos['claveacceso'];
      if(password_verify($_POST['claveIngresada'], $claveEncriptada)){
        //clave correcta
        $resultado["status"] = true;
        $resultado["dni"] = $datosObtenidos["dni"];
      } else {
        //clave incorrecta
        $resultado["mensaje"] = "contraseña incorrecta";
      }
    } else {
      //usuario no encontrado
      $resultado["mensaje"] = "No se encuentra el usuario";
    }

    //Actualizando la informacion en la variable de sesion
    $_SESSION["login"] = $resultado;
    //enviando informacion de la sesion a la vista
    echo json_encode($resultado);
  }
}
?>