<?php

require_once '../models/veterenaria.php';
 $veterinaria = new Veterinaria();
if (isset($_POST['operacion'])){

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
if(isset($_GET['operacion'])){
  if($_GET['operacion'] == 'login'){
    //buscamos al usuario a traves de su nombre
    $datosObtenidos = $veterinaria->login($_GET['dni']);
    //arreglo que contiene datos de login
    $resultado = [
      "status" => false,
      "nombres" => "",
      "mensaje" => "",
      "idcliente" => 0
    ];

    if($datosObtenidos) {
      //encontramos el registro
      $claveEncriptada = $datosObtenidos['claveacceso'];
      if(password_verify($_GET['claveIngresada'], $claveEncriptada)){
        //clave correcta
        $resultado["status"] = true;
        $resultado["dni"] = $datosObtenidos["dni"];
        $resultado["idcliente"] = $datosObtenidos["idcliente"];
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