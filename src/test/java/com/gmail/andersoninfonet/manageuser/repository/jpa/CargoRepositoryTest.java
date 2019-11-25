package com.gmail.andersoninfonet.manageuser.repository.jpa;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;

import com.gmail.andersoninfonet.manageuser.config.DataSourceJPAConfig;
import com.gmail.andersoninfonet.manageuser.model.Cargo;

@SpringBootTest
@ContextHierarchy(value = @ContextConfiguration(classes = DataSourceJPAConfig.class))
public class CargoRepositoryTest {

	@Autowired
	private CargoRepository cargoRepository;
	
	@Test
	public void deveBuscarTodosCargos() {
		List<Cargo> cargos = cargoRepository.findAll();
		assertNotNull(cargos);
	}
}
