package vn.ecornomere.ecornomereAZ.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {
  @Bean
  public ViewResolver viewResolver() {
    final InternalResourceViewResolver bean = new InternalResourceViewResolver();
    bean.setViewClass(JstlView.class);
    bean.setPrefix("/WEB-INF/view/");
    bean.setSuffix(".jsp");
    return bean;
  }

  @SuppressWarnings("null")
  @Override
  public void configureViewResolvers(ViewResolverRegistry registry) {
    registry.viewResolver(viewResolver());
  }

  @SuppressWarnings("null")
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/css/**").addResourceLocations("/resources/css/");
    registry.addResourceHandler("/js/**").addResourceLocations("/resources/js/");
    registry.addResourceHandler("/client/**").addResourceLocations("/resources/client/");
    // Thêm dòng này để truy cập ảnh upload từ "uploads/avatars/"
    registry.addResourceHandler("/uploads/avatars/**")
        .addResourceLocations(
            "file:/D:/Ngominhhieu/Back_end_java/spring-mvc-ecornomere-laptopaz/myapp/uploads/avatars/");

    registry.addResourceHandler("/uploads/products/**")
        .addResourceLocations(
            "file:/D:/Ngominhhieu/Back_end_java/spring-mvc-ecornomere-laptopaz/myapp/uploads/products/");

  }

}