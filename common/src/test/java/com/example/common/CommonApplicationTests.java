package com.example.common;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = TestBootConfig.class)
class CommonApplicationTests { }

@SpringBootConfiguration
@ComponentScan("com.example.common")
class TestBootConfig { }
