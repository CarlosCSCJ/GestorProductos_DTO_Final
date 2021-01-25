package org.jesuitasrioja.productos.modelo.producto;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductoDTOConverter {
	
	@Autowired
	private final ModelMapper modelMapper;

	public ProductoDTOConverter(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}
	
	public ProductoDTO convertProductoToProductoDTO(Producto producto) {
		
		ProductoDTO dto = modelMapper.map(producto, ProductoDTO.class);
		
		return dto;
	}
	
	public ProductoDTO2 convertProductoToProductoDTO2(Producto producto) {
		
		ProductoDTO2 dto = modelMapper.map(producto, ProductoDTO2.class);
		
		return dto;
	}
	
}
