package org.jesuitasrioja.productos.modelo.producto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDTO implements Serializable {
	
	private String nombre;
	private Double precio;
	
}
