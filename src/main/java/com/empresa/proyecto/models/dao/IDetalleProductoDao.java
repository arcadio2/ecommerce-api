package com.empresa.proyecto.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.empresa.proyecto.models.entity.DetalleProducto;

public interface IDetalleProductoDao extends CrudRepository<DetalleProducto, Long>{

}
