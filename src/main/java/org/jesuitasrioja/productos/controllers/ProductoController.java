package org.jesuitasrioja.productos.controllers;

import java.util.Optional;
import java.util.Random;
import java.util.function.Function;

import org.jesuitasrioja.productos.modelo.producto.Producto;
import org.jesuitasrioja.productos.modelo.producto.ProductoDTO;
import org.jesuitasrioja.productos.modelo.producto.ProductoDTO2;
import org.jesuitasrioja.productos.modelo.producto.ProductoDTOConverter;
import org.jesuitasrioja.productos.modelo.producto.ProductoDTORequest;
import org.jesuitasrioja.productos.persistencia.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductoController {

	@Autowired
	private ProductoService ps;

	@Autowired
	private ProductoDTOConverter productoDTOConverter;

	
	//Creo un nuevo producto recibiendo la información como un DTO
	@PostMapping("/productoRandom")
	public String postProducto(@RequestBody ProductoDTORequest nuevoProducto) {
		//este codigo podría encontrarse también en ProductoDTOConverter.java
		Producto producto = new Producto();
		producto.setId(String.valueOf(new Random().nextInt()));
		producto.setNombre(nuevoProducto.getNombre());
		producto.setPrecio(new Random().nextDouble());
		
		return ps.save(producto).toString();
	}
	
	
	
	//OJO: aqui uso el ProductoDTO
	@GetMapping("/productos")
	public ResponseEntity<?> allProducts(@PageableDefault(size = 10, page = 0) Pageable pageable) {
		Page<Producto> pagina = ps.findAll(pageable);

		// transformar elementos de la pagina a DTO
		Page<ProductoDTO> paginaDTO = pagina.map(new Function<Producto, ProductoDTO>() {
			@Override
			public ProductoDTO apply(Producto t) {
				return productoDTOConverter.convertProductoToProductoDTO(t);
			}
		});

		return ResponseEntity.status(HttpStatus.OK).body(paginaDTO);
	}

	//OJO: aqui uso el ProductoDTO2 -- solo ofrece el nombre
	@GetMapping("/productoPorPrecio")
	public ResponseEntity<?> allProducts(@RequestParam(name = "from") Double from, @RequestParam(name = "to") Double to,
			Pageable pageable) {
		
		Page<Producto> paginaProducto = ps.rangoPrecio(from, to, pageable);
		
		// transformar elementos de la pagina a DTO
		Page<ProductoDTO2> paginaDTO2 = paginaProducto.map(new Function<Producto, ProductoDTO2>() {
			@Override
			public ProductoDTO2 apply(Producto t) {
				return productoDTOConverter.convertProductoToProductoDTO2(t);
			}
		});
		
		return ResponseEntity.status(HttpStatus.OK).body(paginaDTO2);
	}

	//OJO: aqui uso el ProductoDTO
	@GetMapping("/producto/{id}")
	public ResponseEntity<?> getProducto(@PathVariable String id) {
		Optional<Producto> productoOptional = ps.findById(id);
		if (productoOptional.isPresent()) {
			Producto producto = productoOptional.get();
			ProductoDTO productoDTO = productoDTOConverter.convertProductoToProductoDTO(producto);
			return ResponseEntity.status(HttpStatus.OK).body(productoDTO);
		} else {
			throw new ProductoNoEncontradoException(id);
		}
	}

	@GetMapping("/producto")
	public Producto getProducto2(@RequestParam String id) {
		return ps.findById(id).get();
	}

	@PostMapping("/producto")
	public String postProducto(@RequestBody Producto nuevoProducto) {
		return ps.save(nuevoProducto).toString();
	}

	@PutMapping("/producto")
	public String putProducto(@RequestBody Producto editadoProducto) {
		return ps.edit(editadoProducto).toString();
	}

	@DeleteMapping("/producto/{id}")
	public String deleteProducto(@PathVariable String id) {
		ps.deleteById(id);
		return "OK";
	}

}
