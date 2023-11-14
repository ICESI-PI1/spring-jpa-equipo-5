/* import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Especifica las rutas a las que se aplicarán las políticas de CORS
                .allowedOrigins("http://localhost:5173") // Orígenes permitidos (puedes configurar múltiples orígenes separados por comas)
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Métodos permitidos
                .allowedHeaders("*") // Encabezados permitidos
                .allowCredentials(true); // Permite el uso de credenciales (como cookies)
    }
}*/
