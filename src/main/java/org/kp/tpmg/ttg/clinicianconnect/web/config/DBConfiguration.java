package org.kp.tpmg.ttg.clinicianconnect.web.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.descriptor.web.ContextResource;
import org.kp.tpmg.ttg.clinicianconnect.OncallservicesApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jndi.JndiObjectFactoryBean;

@Configuration
public class DBConfiguration {
	private static final Logger LOG = LogManager.getLogger(OncallservicesApplication.class);
	
	@Autowired
	Environment environment;
	
	@Value("${db.conf.path}")
	private String filePath;
	
		
	@Bean
    public TomcatServletWebServerFactory tomcatFactory() {
		LOG.info("initializing tomcat factory... ");
		System.out.println("file path ++++"+filePath);
        return new TomcatServletWebServerFactory() {
            @Override
            protected TomcatWebServer getTomcatWebServer(Tomcat tomcat) {
                tomcat.enableNaming();
                return super.getTomcatWebServer(tomcat);
            }

            @Override
            protected void postProcessContext(Context context) {
				LOG.info("initializing tomcat factory JNDI ... ");
				Properties pros=getDataSourceConfig();
                ContextResource resource = new ContextResource(); 
                resource.setType(DataSource.class.getName());
                resource.setProperty("factory", "org.apache.tomcat.dbcp.dbcp2.BasicDataSourceFactory");
                resource.setName(pros.getProperty("jndiName"));
                resource.setProperty("driverClassName", pros.get("driverClassName"));
                 resource.setProperty("url", pros.get("url"));
                resource.setProperty("username", pros.get("username"));
                resource.setProperty("password", pros.get("password"));
                System.out.println("props--------"+pros.getProperty("jndiName"));
                context.getNamingResources().addResource(resource);
            }

			private Properties getDataSourceConfig() {
				Properties props=new Properties();
				
				try {
					//String filePath="C://Users/bchandan/Desktop/springboot/conf.properties";
					InputStream input=new FileInputStream(filePath);
					props.load(input);
				} catch (IOException e) {
					
				}
				return props;
			}
        };
    }
	
	@Bean
	public DataSource jndiDataSource() throws IllegalArgumentException, NamingException {
		JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
		bean.setJndiName("java:/comp/env/jdbc/clinicianConnectSQL");
		//bean.setJndiName("jdbc/clinicianConnectSQL");
		bean.setProxyInterface(DataSource.class);
		bean.setLookupOnStartup(true);
		bean.afterPropertiesSet();
		return (DataSource)bean.getObject();
	}

	@Bean
	public JdbcTemplate jdbcTemplate() throws IllegalArgumentException, NamingException {
		return new JdbcTemplate(jndiDataSource());
	}
}
