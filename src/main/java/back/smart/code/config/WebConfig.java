package back.smart.code.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.File;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${server.file.gallery.path}")
    private String filePath;

    @Value("${server.file.editor.path}")
    private String editorPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        String imgPath = this.filePath ;

        registry.addResourceHandler("/img/gall/**")
                .addResourceLocations("file:///" + imgPath)
                .setCachePeriod(0)
                .resourceChain(true)
                .addResolver(new PathResourceResolver());

        registry.addResourceHandler("/img/editor/**")
                .addResourceLocations("file:///" + editorPath)
                .setCachePeriod(0)
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
    }
}
