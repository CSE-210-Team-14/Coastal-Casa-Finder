package com.costalcasafinder.costalcasafinder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.costalcasafinder.costalcasafinder.mapper.MapppedTypes;
import com.costalcasafinder.costalcasafinder.model.Users;

@MapppedTypes(Users.class)
@MapperScan("com.costalcasafinder.costalcasafinder.mapper")
@SpringBootApplication
public class CostalcasafinderApplication {

	public static void main(String[] args) {
		SpringApplication.run(CostalcasafinderApplication.class, args);
	}

}
