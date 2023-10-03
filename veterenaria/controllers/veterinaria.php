<?php

  require_once '../models/veterenaria.php';

  if (isset($_POST['operacion'])){
   
    $veterinaria = new Veterinaria();

    if($_POST['operacion'] == 'registrarcliente'){
      $datosGuardar = [
        "apellidos"     => $_POST['apellidos'],
        "nombres"    => $_POST['nombres'],
        "dni"    => $_POST['dni'],
        "claveacceso"    => $_POST['claveacceso']
      ];
  
      $respuesta = $veterinaria->registrarcliente($datosGuardar);
      echo json_encode($respuesta);
    }

    switch($_POST['operacion']){
      case 'buscarcliente':
        $parametro = ["dni" => $_POST['dni']];
        echo json_encode ($veterinaria->buscarcliente($parametro));
        break;
    }

    if($_POST['operacion'] == 'registrarmascota'){
      $datosGuardar = [
        "idcliente"     => $_POST['idcliente'],
        "idraza"    => $_POST['idraza'],
        "nombre"    => $_POST['nombre'],
        "fotografia"    => $_POST['fotografia'],
        "color"    => $_POST['color'],
        "genero"    => $_POST['genero'],
        "tamaño"    => $_POST['tamaño'],
        "peso"    => $_POST['peso']
      ];
  
      $respuesta = $veterinaria->registrarmascota($datosGuardar);
      echo json_encode($respuesta);
    }
  }