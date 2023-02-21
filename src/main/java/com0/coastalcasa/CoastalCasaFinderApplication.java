package com0.coastalcasa;

import org.apache.ibatis.type.MappedTypes;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com0.coastalcasa.Models.Landlord;

@MappedTypes(Landlord.class)
@MapperScan("com0.coastalcasa.Mapper")
@SpringBootApplication
public class CoastalCasaFinderApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoastalCasaFinderApplication.class, args);
	}

}
