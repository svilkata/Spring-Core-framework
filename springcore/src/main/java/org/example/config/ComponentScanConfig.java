package org.example.config;

import com.somelibrary.core.SomeLibraryClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@ComponentScan("com.somelibrary.core")
public class ComponentScanConfig {

    // Когато някои от класовете в дадената библиотека не са готови бийнове, то можем да ги направим като такива ето така:
    @Bean
    @Primary
    public SomeLibraryClass createSomeLibraryClassBean() {
        return new SomeLibraryClass();
    }

    @Bean
    public SomeLibraryClass createSomeLibraryClassBean2() {
        return new SomeLibraryClass();
    }
}
