package com.mydana.alms;

import com.mydana.alms.admin.BaseAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

@SpringBootApplication
@EntityScan(
		basePackageClasses = {AlmsApplication.class, Jsr310JpaConverters.class}
)
public class AlmsApplication implements CommandLineRunner {
	@Value( "${asx.pathfile}" )
	private String asxpath;

	@Autowired
	private ApplicationContext context;
	@Bean
	public ArrayList<String> allasxcodes(){
		ArrayList allcodes = new ArrayList<String>();


		try {
//			H:\GIT\ASXCodes.txt
			System.out.println("-------------------------asx file path-->"+asxpath);

			Scanner scanner = new Scanner(new File(  asxpath ));
			scanner.useDelimiter(",");
			while(scanner.hasNext()){
				allcodes.add( scanner.next().replaceAll("\\r|\\n", "").trim().toUpperCase() );
			}
			scanner.close();
		} catch (Exception e) {
			System.out.println(" " + e);
		}

		System.out.println(" getAllCodeFileToRun codes " +allcodes.size() );
		return 	allcodes;



	}



//	@EnableWebMvc
//	@Configuration
//	public class SpringWebMvcConfig extends WebMvcConfigurerAdapter {
//
//		private int maxUploadSizeInMb = 5 * 1024 * 1024; // 5 MB
//
//		@Bean
//		public InternalResourceViewResolver viewResolver() {
//			InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//			//viewResolver.setViewClass(JstlView.class);
//			viewResolver.setPrefix("/WEB-INF/jsp/");
//			viewResolver.setSuffix(".jsp");
//			return viewResolver;
//		}
//
//
//		//upload.jsp
//		@Bean
//		public CommonsMultipartResolver multipartResolver() {
//
//			CommonsMultipartResolver cmr = new CommonsMultipartResolver();
//			cmr.setMaxUploadSize(maxUploadSizeInMb * 2);
//			cmr.setMaxUploadSizePerFile(maxUploadSizeInMb); //bytes
//			return cmr;
//
//		}
//
//	}

//
//	public class MyWebInitializer extends
//			AbstractAnnotationConfigDispatcherServletInitializer {
//
//		@Override
//		protected Class<?>[] getServletConfigClasses() {
//			return new Class[]{SpringWebMvcConfig.class};
//		}
//
//		@Override
//		protected String[] getServletMappings() {
//			return new String[]{"/"};
//		}
//
//		@Override
//		protected Class<?>[] getRootConfigClasses() {
//			return null;
//		}
//
//	}

	@Override
	public void run(String... args) throws Exception {

//		String names[] =context.getBeanDefinitionNames();
//		for (String name :names ) {
//		//	System.out.println("=======>"+name);
//			Object bean  = context.getBean(name);
//			if(bean.getClass().getPackage() != null){
//
//				if (bean.getClass().getPackage().getName().equals("com.mydana.alms.admin") ){
//					System.out.println("packageName =======>"+bean.getClass().getName());
//
//				}
//
//			}
//
//		}

//
//		ClassPathScanningCandidateComponentProvider scanner =
//				new ClassPathScanningCandidateComponentProvider(true);
//
//
//		for (BeanDefinition bd : scanner.findCandidateComponents("com.mydana.alms.admin")){
//			System.out.println("------packages------------------->"+bd.getBeanClassName());
//			System.out.println("------packages------------------->"+bd.  );
//
//			//System.out.println("------packages xx------------------->"+bd.get);
//
////			ConfigurableListableBeanFactory clbf = ((AbstractApplicationContext) context).getBeanFactory();
////			Object bean = clbf.getSingleton(bd.getBeanClassName());
////			System.out.println("------packages xx------------------->"+bean );
////			System.out.println("------packages xx------------------->"+bean.getClass().getName());
//		}


	}

	public static void main(String[] args) {

		SpringApplication.run(AlmsApplication.class, args);




	}
}



