<?php
require_once 'Conexion.php';

class Veterinaria extends Conexion{

  private $acesso;

  public function __CONSTRUCT()
  {
    $this->acesso = parent::getConexion();
  }

  public function registrarcliente($datos = []){
    $respuesta = [
      "status" => false,
      "message" =>""
    ];
    
    try{
      $consulta = $this->acesso->prepare("CALL spu_register_cliente(?,?,?,?)");
      $respuesta["status"] = $consulta->execute(
        array(
          $datos["apellidos"],
          $datos["nombres"],
          $datos["dni"],
          $datos["claveacceso"]
        )
      );
    }
    catch(Exception $e){
      $respuesta["message"] = "No se pudo completar". $e->getCode();
    }
    return $respuesta;
  }

  public function buscarcliente($data = []){
    try{
      $query = $this->acesso->prepare("CALL spu_buscar_mascota(?)");
      $query->execute(
        array(
          $data['dni']
        )
      );
      return $query->fetchAll(PDO::FETCH_ASSOC);
    }
    catch(Exception $e){
      die($e->getCode());
    }
  }

  public function registrarmascota($datos = []){
    $respuesta = [
      "status" => false,
      "message" =>""
    ];
    
    try{
      $consulta = $this->acesso->prepare("CALL spu_register_cliente(?,?,?,?,?,?,?,?)");
      $respuesta["status"] = $consulta->execute(
        array(
          $datos["idcliente"],
          $datos["idraza"],
          $datos["nombre"],
          $datos["fotografia"],
          $datos["color"],
          $datos["genero"],
          $datos["tamaño"],
          $datos["peso"]
        )
      );
    }
    catch(Exception $e){
      $respuesta["message"] = "No se pudo completar". $e->getCode();
    }
    return $respuesta;
  }

  public function login($dni = '') {
    try {
      $consulta = $this->acesso->prepare("CALL spu_login(?)");
      $consulta->execute(array($dni));
      return $consulta->fetch(PDO::FETCH_ASSOC);
    } catch (Exception $e) {
      die($e->getMessage());
    }
  }
}